// IUserManager.aidl
package me.yimu.demo.binder;

// Declare any non-default types here with import statements
import me.yimu.demo.binder.User;

interface IUserManager {
    List<User> getUserList();
    void addUser(in User user);
}
