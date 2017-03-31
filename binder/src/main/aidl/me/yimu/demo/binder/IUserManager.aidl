// IUserManager.aidl
package me.yimu.demo.binder;

// Declare any non-default types here with import statements
import me.yimu.demo.binder.User;
import me.yimu.demo.binder.IUserChangeListener;

interface IUserManager {
    List<User> getUserList();
    void addUser(in User user);
    boolean deleteUser();

    void registerListener(IUserChangeListener l);
    void unregisterListener(IUserChangeListener l);

}
