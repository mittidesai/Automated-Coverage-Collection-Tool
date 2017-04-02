package edu.utdallas;

import java.lang.instrument.Instrumentation;

/**
 * @author: Miti Desai
 * Create Time: 03/15/17
 */
public class Agent {
    public static void premain(String agentArgs, Instrumentation inst) {

        System.out.println("My Java Agent is now executing");

        // registers the transformer
        inst.addTransformer(new MyClassFileTransform());

    }
}

