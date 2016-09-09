package com.trubuzz.trubuzz;

import org.junit.Test;

import java.util.Stack;

import static org.junit.Assert.assertEquals;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);

        String a= new String("1");
        String b= new String("2");
        String c= new String("3");
        Stack<String> stack = new Stack<>();
        stack.push(a);
        stack.push(b);
        stack.push(c);

        System.out.println(stack.remove(b));
        System.out.println(stack.toString());

    }
}