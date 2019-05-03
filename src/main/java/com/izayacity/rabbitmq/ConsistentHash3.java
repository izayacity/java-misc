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
public class ConsistentHash3 {
    public static final String EXCHANGE = "e3";
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
        args.put("hash-property", "message_id");
        ch.exchangeDeclare(EXCHANGE, EXCHANGE_TYPE, true, false, args);

        for (String q : Arrays.asList("q1", "q2")) {
            ch.queueBind(q, EXCHANGE, "1");
        }

        for (String q : Arrays.asList("q3", "q4")) {
            ch.queueBind(q, EXCHANGE, "2");
        }

        ch.confirmSelect();

        long time1 = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            AMQP.BasicProperties.Builder bldr = new AMQP.BasicProperties.Builder();
            ch.basicPublish(EXCHANGE, "", bldr.messageId(String.valueOf(i)).build(), "".getBytes("UTF-8"));
        }
        long time2 = System.currentTimeMillis();
        System.out.println("Solution 3 used " + (time2 - time1) + " ms");

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
