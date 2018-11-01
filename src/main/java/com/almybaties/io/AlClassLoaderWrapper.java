package com.almybaties.io;

import java.io.InputStream;

/**
 * a class to wrap access multipart class loaders making them working as one
 *  @author  adonai
 */
public class AlClassLoaderWrapper {

    ClassLoader defaultClassLoader;
    ClassLoader systemClassLoader;

    AlClassLoaderWrapper(){
        systemClassLoader = ClassLoader.getSystemClassLoader();
    }

    public InputStream getResourceAsStream(String resource , ClassLoader classloader){
        return getResourceAsStream(resource,getClassLoaders(classloader));
    }

    InputStream getResourceAsStream(String resource , ClassLoader[] classLoader){
        for (ClassLoader cl: classLoader) {
            if(null != cl){
                //try to find the resource as passed
                InputStream in = cl.getResourceAsStream(resource);
                //now, some class loaders want this leading "/", so we'll add it and try again if we didn't find the resource
                if(null == in){
                    in = cl.getResourceAsStream("/" + resource);
                }

                if(in != null){
                    return in;
                }
            }
        }
        return null;
    }


    ClassLoader[] getClassLoaders(ClassLoader classLoader){
        return new ClassLoader[]{
           classLoader,
           defaultClassLoader,
           systemClassLoader,
           Thread.currentThread().getContextClassLoader(),
           getClass().getClassLoader()
        };
    }

}
