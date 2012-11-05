package com.marabout.lang;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Properties management class.
 * <p/>
 * Basically, it will look for a Java properties file corresponding to the specified
 * object class name, put this properties file in cache, and use it to get the
 * required properties.
 * For example, if the requiring object class is <code>com.foo.Bar</code>, then
 * the following properties file will be looked for: <code>com/foo/Bar.properties</code>.
 * <p/>
 * The different methods to get the properties are all static, then it is a good
 * idea to statically import them:
 * <p/>
 * <code>
 * import static com.marabout.lang.PropertiesManager.*;
 * </code>
 * <p/>
 * then, here is how the methods can be used:
 * <p/>
 * <code>
 * String value = getString("my_property", this);
 * </code>
 *
 * @author <a href="mailto:gmarabout@gmail.com">Grégoire Marabout</a>
 */
public class ClassPropertiesManager {
    private static final ClassPropertiesManager INSTANCE = new ClassPropertiesManager();
    private static final Properties emptyProperties = new Properties() {
        @Override
        public Object setProperty(String key, String value) {
            throw new UnsupportedOperationException("Immutable properties");
        }
    };
    private ThreadLocal<Map<Class, Properties>> threadLocalPropertiesMap;
    private Logger logger = Logger.getLogger(ClassPropertiesManager.class.getName());

    /**
     * This class cannot be instantiated directly.
     */
    private ClassPropertiesManager() {
    }

    private Map<Class, Properties> getPropertiesMap() {
        if (threadLocalPropertiesMap == null) {
            threadLocalPropertiesMap = new ThreadLocal<Map<Class, Properties>>();
            threadLocalPropertiesMap.set(new HashMap<Class, Properties>());
        }
        return threadLocalPropertiesMap.get();
    }

    private Properties getProperties(Object obj) {
        Properties props;
        Class cls = (obj instanceof Class) ? (Class) obj : obj.getClass();
        Map<Class, Properties> propertiesMap = getPropertiesMap();
        if (!propertiesMap.keySet().contains(cls)) {
            props = loadProperties(cls);
            propertiesMap.put(cls, props);
        } else {
            props = propertiesMap.get(cls);
        }
        return props;
    }

    private Properties loadProperties(Class cls) {
        String propertiesFileName = cls.getSimpleName() + ".properties";
        logger.info("Loading: " + propertiesFileName);
        InputStream istream = null;
        try {
            istream = cls.getResourceAsStream(propertiesFileName);

            if (istream == null)
                return emptyProperties;
            Properties props = new Properties();
            try {
                props.load(istream);
            } catch (IOException ioe) {
                logger.log(Level.WARNING, ioe.getMessage());
                props = emptyProperties;
            }
            return props;
        } finally {
            if (istream != null)
                try {
                    istream.close();
                } catch (IOException e) {
                    logger.log(Level.WARNING, e.getMessage(), e);
                }
        }
    }

    /**
     * Returns a string value of the property named <i>propertyName</i>,
     * and for object <i>obj</i>.
     *
     * @param propertyName the name of the property.
     * @param obj          the object target.
     * @return The string value of the property or <code>null</code> if the property
     *         is not found.
     */
    public static String getString(String propertyName, Object obj) {
        return INSTANCE.getProperties(obj).getProperty(propertyName);
    }

    /**
     * Returns a string value of the property named <i>propertyName</i>,
     * and for object <i>obj</i>. If the property does not exist it will return 0,
     * and if the prioperty is not an int, then a NumberFormatException is likely.
     *
     * @param propertyName the name of the property.
     * @param obj          the object target.
     * @return the int property.
     */
    public static int getInt(String propertyName, Object obj) {
        String value = getString(propertyName, obj);
        if (value == null)
            return 0;
        return Integer.valueOf(value);
    }

    /**
     * Same as {@link #getString(String, Object)} but using a default value, if the
     * requested one is not found.
     *
     * @param propertyName The name of the property.
     * @param obj          the object target.
     * @param defaultValue The default value returned if propertyName has not value.
     * @return the string value.
     */
    public static String getString(String propertyName, Object obj, String defaultValue) {
        String found = getString(propertyName, obj);
        return found != null ? found : defaultValue;
    }

    /**
     * Same as {@link #getInt(String, Object)} but using a default value, if the
     * requested one is not found.
     *
     * @param propertyName The name of the property.
     * @param obj          the object target.
     * @param defaultValue The default value returned if propertyName has not value.
     * @return the int value.
     */
    public static int getInt(String propertyName, Object obj, int defaultValue) {
        String value = getString(propertyName, obj);
        return value != null ? Integer.valueOf(value) : defaultValue;
    }
}
