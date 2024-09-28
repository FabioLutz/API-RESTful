package org.project.userManagement;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AppTest
{
    @Test
    public void testHelloWorld ()
    {
        HelloWorld helloWorld = new HelloWorld();

        Assertions.assertEquals("Hello World!", helloWorld.printHelloWorld());
    }
}
