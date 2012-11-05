package com.marabout.lang;

import com.marabout.lang.Version;

import junit.framework.TestCase;

/**
 * @author <a href="mailto:gmarabout@gmail.com">Grégoire Marabout</a>
 */
public class VersionTest extends TestCase {
    public void testGetVersion() {
        String version = Version.instance.getVersion();

        System.out.println("VERSION : " + version);

        assertNotNull(version);
    }

    public void testGetBuild() {
        String build = Version.instance.getBuild();

        System.out.println("Build: " + build);

        assertNotNull(build);
    }
}
