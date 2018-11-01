package com.almybaties.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * a class to simplify access to resources through the classloader
 * @author adonai
 */
public class AlResources {

     private static AlClassLoaderWrapper classLoaderWrapper = new AlClassLoaderWrapper();

    public static InputStream getResourcesAsStream(String resource) throws IOException {
        return getResourcesAsStream(null,resource);
    }

    public static InputStream getResourcesAsStream(ClassLoader loader , String resource) throws IOException {
        InputStream in = classLoaderWrapper.getResourceAsStream(resource, loader);
        if(in == null){
           throw new IOException("could not find resource " + resource);
        }
        return in;
    }



}
