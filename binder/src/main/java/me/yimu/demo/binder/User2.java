package me.yimu.demo.binder;

import java.io.Serializable;

/**
 * Created by linwei on 2017/3/18.
 */

public class User2 implements Serializable {

    /**
     * 序列化的时候会把该值写入序列化的文件中，当反序列化的时候检测该值时候匹配
     * 一致时可以序列化成功，不一致时说明类发生了变化
     */
    private static final long serialVersionUID = 123456789;

    public String name;
}
