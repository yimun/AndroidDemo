package me.yimu.demo.binderpool;

import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView mLogTextView;

    ISecurityCenter mSecurityCenter;
    ICompute mCompute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLogTextView = (TextView) findViewById(R.id.log);
        doWork();
    }

    void doWork() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                BinderPool binderPool = BinderPool.getInstance(MainActivity.this);
                IBinder securityBinder = binderPool.queryBinder(BinderPool.BINDER_SECURITY_CENTER);
                mSecurityCenter = SecurityCenterImpl.asInterface(securityBinder);
                log("visit security center");
                String msg = "hello, 安卓Binder";
                log("origin:" + msg);
                try {
                    String password = mSecurityCenter.encrypt(msg);
                    log("encrypted:" + password);
                    log("decrypted:" + mSecurityCenter.decrypt(password));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                IBinder computeBinder = binderPool.queryBinder(BinderPool.BINDER_COMPUTE);
                log("visit compute center");
                mCompute = ComputeImpl.asInterface(computeBinder);
                try {
                    log("3+5=" + mCompute.add(3, 5));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    void log(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mLogTextView.setText(mLogTextView.getText() + "\n" + msg);
            }
        });
    }
}
