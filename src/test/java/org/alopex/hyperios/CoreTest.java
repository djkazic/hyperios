package org.alopex.hyperios;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class CoreTest 
    extends TestCase {

    public CoreTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(CoreTest.class);
    }
}
