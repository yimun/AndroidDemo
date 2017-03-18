package me.yimu.demo.statusbar;

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

/**
 * User: mcxiaoke
 * Date: 15/5/19
 * Time: 11:06
 */
public class BuildProperties {
    private static final String TAG = BuildProperties.class.getSimpleName();

    static class SingletonHolder {
        static BuildProperties INSTANCE = new BuildProperties();
    }

    public static BuildProperties getDefault() {
        return SingletonHolder.INSTANCE;
    }

    private final Properties properties;

    public BuildProperties() {
        properties = new Properties();
        FileInputStream fis = null;
        try {
            File file = new File(Environment.getRootDirectory(), "build.prop");
            fis = new FileInputStream(file);
            properties.load(fis);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    public boolean containsKey(final Object key) {
        return properties.containsKey(key);
    }

    public boolean containsValue(final Object value) {
        return properties.containsValue(value);
    }

    public Set<Entry<Object, Object>> entrySet() {
        return properties.entrySet();
    }

    public String getProperty(final String name) {
        return properties.getProperty(name);
    }

    public String getProperty(final String name, final String defaultValue) {
        return properties.getProperty(name, defaultValue);
    }

    public boolean isEmpty() {
        return properties.isEmpty();
    }

    public Enumeration<Object> keys() {
        return properties.keys();
    }

    public Set<Object> keySet() {
        return properties.keySet();
    }

    public int size() {
        return properties.size();
    }

    public Collection<Object> values() {
        return properties.values();
    }

}
