package me.yimu.demo.binderpool;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import java.util.concurrent.CountDownLatch;

/**
 * Created by linwei on 2017/5/1.
 */

public class BinderPool {
    public static final String TAG = "BinderPool";

    private Context mContext;
    private static volatile BinderPool sInstance;

    private CountDownLatch mConnectBinderPollCountDownLatch;
    private IBinderPool mBinderPool;

    // binder code register
    public static final int BINDER_NONE = -1;
    public static final int BINDER_SECURITY_CENTER = 0;
    public static final int BINDER_COMPUTE = 1;

    private BinderPool(Context context) {
        mContext = context.getApplicationContext();
        connectBinderPoolService();
    }

    public static BinderPool getInstance(Context context) {
        if (sInstance == null) {
            synchronized (BinderPool.class) {
                if (sInstance == null) {
                    sInstance = new BinderPool(context);
                }
            }
        }
        return sInstance;
    }

    private synchronized void connectBinderPoolService() {
        mConnectBinderPollCountDownLatch = new CountDownLatch(1);
        Intent intent = new Intent(mContext, BinderPoolService.class);
        mContext.bindService(intent, mBinderPoolConnection, Context.BIND_AUTO_CREATE);
        try {
            mConnectBinderPollCountDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public IBinder queryBinder(int binderCode) {
        IBinder binder = null;
        if (mBinderPool != null) {
            try {
                binder = mBinderPool.queryBinder(binderCode);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return binder;
    }

    private IBinder.DeathRecipient mBinderPollDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            mBinderPool.asBinder().unlinkToDeath(mBinderPollDeathRecipient, 0);
            mBinderPool = null;
            connectBinderPoolService();
        }
    };

    private ServiceConnection mBinderPoolConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBinderPool = IBinderPool.Stub.asInterface(service);
            try {
                mBinderPool.asBinder().linkToDeath(mBinderPollDeathRecipient, 0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            mConnectBinderPollCountDownLatch.countDown();

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            // ignored
        }
    };

    public static class BinderPoolImpl extends IBinderPool.Stub {

        public BinderPoolImpl() {
            super();
        }

        @Override
        public IBinder queryBinder(int binderCode) throws RemoteException {
            switch (binderCode) {
                case BINDER_COMPUTE:
                    return new ComputeImpl();
                case BINDER_SECURITY_CENTER:
                    return new SecurityCenterImpl();
                default:
                    return null;
            }
        }
    }
}
