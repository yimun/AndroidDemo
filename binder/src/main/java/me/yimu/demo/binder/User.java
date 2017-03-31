package me.yimu.demo.binder;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * Created by linwei on 2017/3/18.
 */

public class User implements Serializable, Parcelable {

    public String id;
    public String name;
    public int age;

    public User(String id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    /**
     * 从序列化中的对象中创建原始对象
     * @param in
     */
    protected User(Parcel in) {
        id = in.readString();
        name = in.readString();
        age = in.readInt();
    }

    /**
     * 返回当前对象的内容描述，如果含有文件描述符，返回1，否则返回0，几乎所有的情况都返回0
     * @return
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * 完成序列化工作
     *
     * 将当前对象写入序列化结构当中，其中flags有两种标识，0或者1
     * 为1是标识当前对象需要作为返回值返回，不能立即释放资源，几乎所有情况都为0
     * @param dest
     * @param flags
     */
    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeInt(age);
    }

    /**
     * 完成反序列化的工作
     */
    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
