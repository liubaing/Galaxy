package com.liubaing.galaxy.container;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExtensionLoader<T> {

    private static final Logger logger = LoggerFactory.getLogger(ExtensionLoader.class);
    
    private static final String EXTENSION_DIR = "META-INF/extension/";
    
    private static final ConcurrentMap<Class<?>, ExtensionLoader<?>> EXTENSION_LOADERS = new ConcurrentHashMap<Class<?>, ExtensionLoader<?>>();
    
    private final ConcurrentMap<String, Object> EXTENSION_INSTANCES = new ConcurrentHashMap<String, Object>();

    private final Class<?> type;
    
    @SuppressWarnings("unchecked")
    public static <T> ExtensionLoader<T> getExtensionLoader(Class<T> type) {
        if (type == null)
            throw new IllegalArgumentException("Extension type == null");
        if(!type.isInterface()) {
            throw new IllegalArgumentException("Extension type(" + type + ") is not interface!");
        }
        ExtensionLoader<T> loader = (ExtensionLoader<T>) EXTENSION_LOADERS.get(type);
        if (loader == null) {
            EXTENSION_LOADERS.putIfAbsent(type, new ExtensionLoader<T>(type));
            loader = (ExtensionLoader<T>) EXTENSION_LOADERS.get(type);
        }
        return loader;
    }
    
    @SuppressWarnings("unchecked")
	public T getExtension(String name) {
        if (name == null || name.length() == 0)
            throw new IllegalArgumentException("Extension name == null");
        return (T) EXTENSION_INSTANCES.get(name);
    }
    
    public Set<String> getSupportedExtensions() {
        return EXTENSION_INSTANCES.keySet();
    }
    
    private ExtensionLoader(Class<?> type) {
        this.type = type;
        EXTENSION_INSTANCES.putAll(loadExtensionClasses());
    }
    
    private static ClassLoader findClassLoader() {
        return  ExtensionLoader.class.getClassLoader();
    }
    
    private Map<String, Object> loadExtensionClasses() {
        Map<String, Object> extensionClasses = new HashMap<String, Object>();
        try {
        	Enumeration<java.net.URL> urls;
            ClassLoader classLoader = findClassLoader();
            String fileName = EXTENSION_DIR + type.getName();
            if (classLoader != null) {
                urls = classLoader.getResources(fileName);
            } else {
                urls = ClassLoader.getSystemResources(fileName);
            }
            if (urls != null) {
                while (urls.hasMoreElements()) {
                    java.net.URL url = urls.nextElement();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "utf-8"));
                    try {
                        String line = null;
                        while ((line = reader.readLine()) != null) {
                            final int ci = line.indexOf('#');
                            if (ci >= 0) line = line.substring(0, ci);
                            line = line.trim();
                            if (line.length() > 0) {
                                String name = null;
                                int i = line.indexOf('=');
                                if (i > 0) {
                                    name = line.substring(0, i).trim();
                                    line = line.substring(i + 1).trim();
                                }
                                if (line.length() > 0) {
                                    Class<?> clazz = Class.forName(line, true, classLoader);
                                    extensionClasses.put(name, clazz.newInstance());
                                }
                            }
                        }
                    }catch(IOException e){
                    	logger.error("读取文件失败，路径 ： "+url);
                    }finally{
                    	reader.close();
                    }
                }
            }
		} catch (Throwable e) {
			logger.error("Exception when load extension class(interface: "+type);
		}
        return extensionClasses;
    }
    
    @Override
    public String toString() {
        return this.getClass().getName() + "[" + type.getName() + "]";
    }
    
}
