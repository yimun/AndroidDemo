package me.yimu.demo.binderpool;

import android.os.RemoteException;

/**
 * Created by linwei on 2017/5/1.
 */

public class ComputeImpl extends ICompute.Stub {
    @Override
    public int add(int a, int b) throws RemoteException {
        return a + b;
    }
}
