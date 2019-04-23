package com.izayacity.rabbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * Author:  Xirui Yang
 * Date:    2019-04-23
 * Time:    17:38
 * Version: 1.0
 * Email:   xirui.yang@happyelements.com
 * Description: com.izayacity.rabbitmq
 */
public class ConsistentHash2 {
    public static final String EXCHANGE = "e2";
    private static String EXCHANGE_TYPE = "x-consistent-hash";

    public static void main(String[] argv) throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory cf = new ConnectionFactory();
        Connection conn = cf.newConnection();
        Channel ch = conn.createChannel();

        for (String q : Arrays.asList("q1", "q2", "q3", "q4")) {
            ch.queueDeclare(q, true, false, false, null);
            ch.queuePurge(q);
        }

        Map<String, Object> args = new HashMap<>();
        args.put("hash-header", "hash-on");
        ch.exchangeDeclare(EXCHANGE, EXCHANGE_TYPE, true, false, args);

        for (String q : Arrays.asList("q1", "q2")) {
            ch.queueBind(q, EXCHANGE, "1");
        }

        for (String q : Arrays.asList("q3", "q4")) {
            ch.queueBind(q, EXCHANGE, "2");
        }

        ch.confirmSelect();


        for (int i = 0; i < 100000; i++) {
            AMQP.BasicProperties.Builder bldr = new AMQP.BasicProperties.Builder();
            Map<String, Object> hdrs = new HashMap<>();
            hdrs.put("hash-on", String.valueOf(i));
            ch.basicPublish(EXCHANGE, "", bldr.headers(hdrs).build(), "".getBytes("UTF-8"));
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
