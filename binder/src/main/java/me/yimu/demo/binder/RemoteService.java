package me.yimu.demo.binder;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.SystemClock;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class RemoteService extends Service {

    private final CopyOnWriteArrayList<User> mUserList = new CopyOnWriteArrayList<>();

    private final RemoteCallbackList<IUserChangeListener> mListeners = new RemoteCallbackList<>();

    public static final int TYPE_ADD = 1;
    public static final int TYPE_DELETE = 2;


    public RemoteService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    private final IUserManager.Stub mBinder = new IUserManager.Stub() {
        @Override
        public List<User> getUserList() throws RemoteException {
            SystemClock.sleep(2000);
            return mUserList;
        }

        @Override
        public void addUser(User user) throws RemoteException {
            synchronized (mUserList) {
                mUserList.add(user);
                notifyChange(TYPE_ADD);
            }
        }

        @Override
        public boolean deleteUser() throws RemoteException {
            synchronized (mUserList) {
                if (!mUserList.isEmpty()) {
                    mUserList.remove(0);
                    notifyChange(TYPE_DELETE);
                    return true;
                }
            }
            return false;
        }

        @Override
        public void registerListener(IUserChangeListener l) throws RemoteException {
            mListeners.register(l);
        }

        @Override
        public void unregisterListener(IUserChangeListener l) throws RemoteException {
            mListeners.unregister(l);
        }

        public void notifyChange(int type) {
            int n = mListeners.beginBroadcast();
            for (int i = 0; i < n; i++) {
                try {
                    mListeners.getBroadcastItem(i).onUserChange(type);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            mListeners.finishBroadcast();
        }
    };
}
