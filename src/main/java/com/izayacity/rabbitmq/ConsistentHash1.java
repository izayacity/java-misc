package com.izayacity.rabbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.TimeoutException;

/**
 * Author:  Xirui Yang
 * Date:    2019-04-23
 * Time:    17:37
 * Version: 1.0
 * Email:   xirui.yang@happyelements.com
 * Description: com.izayacity.rabbitmq
 */
public class ConsistentHash1 {
    private static String CONSISTENT_HASH_EXCHANGE_TYPE = "x-consistent-hash";

    public static void main(String[] argv) throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory cf = new ConnectionFactory();
        Connection conn = cf.newConnection();
        Channel ch = conn.createChannel();

        for (String q : Arrays.asList("q1", "q2", "q3", "q4")) {
            ch.queueDeclare(q, true, false, false, null);
            ch.queuePurge(q);
        }

        ch.exchangeDeclare("e1", CONSISTENT_HASH_EXCHANGE_TYPE, true, false, null);

        for (String q : Arrays.asList("q1", "q2")) {
            ch.queueBind(q, "e1", "1");
        }

        for (String q : Arrays.asList("q3", "q4")) {
            ch.queueBind(q, "e1", "2");
        }

        ch.confirmSelect();

        AMQP.BasicProperties.Builder bldr = new AMQP.BasicProperties.Builder();
        for (int i = 0; i < 100000; i++) {
            ch.basicPublish("e1", String.valueOf(i), bldr.build(), "".getBytes("UTF-8"));
        }

        ch.waitForConfirmsOrDie(10000);

        System.out.println("Done publishing!");
        System.out.println("Evaluating results...");
        // wait for one stats emission interval so that queue counters
        // are up-to-date in the management UI
        Thread.sleep(5);

        System.out.println("Done.");
        conn.close();
    }
}
