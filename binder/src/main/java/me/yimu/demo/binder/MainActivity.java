package me.yimu.demo.binder;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    IUserManager mUserManager;

    Button mAddUser;
    Button mDeleteUser;
    TextView mLog;

    private int id = 10000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAddUser = (Button) findViewById(R.id.add);
        mDeleteUser = (Button) findViewById(R.id.delete);
        mLog = (TextView) findViewById(R.id.log);
        mAddUser.setOnClickListener(this);
        mDeleteUser.setOnClickListener(this);
        bindService(new Intent(this, RemoteService.class), mConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        unbindService(mConnection);
        super.onDestroy();
    }

    private final ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mUserManager = IUserManager.Stub.asInterface(service);
            try {
                mUserManager.registerListener(mListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            try {
                mUserManager.unregisterListener(mListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            mUserManager = null;
        }
    };

    private final IUserChangeListener.Stub mListener = new IUserChangeListener.Stub() {

        @Override
        public void onUserChange(int type) throws RemoteException {
            // 这个方法运行在Binder线程池中，无法直接修改UI
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        updateUserList(mUserManager.getUserList());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            });

        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add: {
                if (mUserManager != null) {
                    try {
                        mUserManager.addUser(new User(String.valueOf(id++), "linwei", 18));
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                break;
            }
            case R.id.delete: {
                if (mUserManager != null) {
                    try {
                        mUserManager.deleteUser();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                break;
            }
        }
    }

    private void updateUserList(List<User> userList) {
        StringBuilder sb = new StringBuilder();
        for (User user : userList) {
            sb.append(user.id);
            sb.append("/");
            sb.append(user.name);
            sb.append("/");
            sb.append(user.age);
            sb.append("\n");
        }
        mLog.setText(sb.toString());
    }

}
