package org.project.userManagement;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.project.userManagement.controllers.HelloWorld;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AppTest
{
    @Autowired
    private HelloWorld helloWorld;

    @Test
    public void testHelloWorld ()
    {
        Assertions.assertEquals("Hello World!", helloWorld.printHelloWorld());
    }
}
