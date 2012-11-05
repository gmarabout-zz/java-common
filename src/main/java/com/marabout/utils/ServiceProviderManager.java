package com.marabout.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;

/**
 * Generic Manager class for Service providers.
 * 
 * @author <a href="mailto:gmarabout@gmail.com">Grégoire Marabout</a>
 * 
 */
public final class ServiceProviderManager {

    private static final String META_INF_SERVICES = "META-INF/services/";
    private static Logger logger = Logger.getLogger( ServiceProviderManager.class.getName() );

    public <T> Collection<Class<? extends T>> findServiceProviders(Class<T> serviceInterface) {
        String spiFileName = serviceInterface.getName();
        List<Class<? extends T>> result = new ArrayList<Class<? extends T>>();

        ClassLoader cloader = serviceInterface.getClassLoader();
        // let's search for all files called after this name:
        try {
            String name = META_INF_SERVICES + spiFileName;
            Enumeration<URL> resources = cloader.getResources( name );
            while ( resources.hasMoreElements() ) {
                URL url = resources.nextElement();
                InputStream istream = url.openStream();
                LineIterator lineIter = IOUtils.lineIterator( new InputStreamReader( istream ) );
                while ( lineIter.hasNext() ) {
                    String line = lineIter.nextLine();
                    if ( !line.trim().startsWith( "#" ) ) {
                        try {
                            result.add( (Class<T>) Class.forName( line ) );
                        } catch ( ClassNotFoundException e ) {
                            logger.log( Level.SEVERE, "Could not load service provider class: " + line, e );
                        }
                    }
                }
            }
        } catch ( IOException e ) {
            logger.log( Level.WARNING, "Error while loading spi files: " + spiFileName, e );
        }
        return result;
    }
}
