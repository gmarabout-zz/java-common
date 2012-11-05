package com.marabout.lang;

import java.io.IOException;
import java.net.URL;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

/**
 * This class provides version and build numbers.
 *
 * @author <a href="mailto:gmarabout@gmail.com">Grégoire Marabout</a>
 */
public final class Version {

    public static final Version instance = new Version();
    private String version;
    private String build;

    private Version() {
    }

    public String getVersion() {
        if (version == null)
            retrieveVersion();
        return version;
    }

    public String getBuild() {
        if (build == null)
            retrieveVersion();
        return build;
    }

    private void retrieveVersion() {
        URL jarURL = getClass().getProtectionDomain().getCodeSource().getLocation();
        JarFile jarFile;
        try {
            jarFile = new JarFile(jarURL.getFile());
        } catch (IOException e) {
            version = "DEV";
            build = "0";
            return;
        }
        Manifest manifest;
        try {
            manifest = jarFile.getManifest();
        } catch (IOException e) {
            throw new RuntimeException("No MANIFEST.MF file found!");
        }
        version = manifest.getMainAttributes().getValue("Implementation-Version");
        if (version == null)
            version = "NOT FOUND";
        build = manifest.getMainAttributes().getValue("Implementation-Build");
        if (build == null)
            build = "0";
    }

}
