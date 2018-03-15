package com.ds;

import com.ds.commands.*;
import io.atomix.catalyst.transport.Address;
import io.atomix.catalyst.transport.netty.NettyTransport;
import io.atomix.copycat.client.CopycatClient;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public class FTStackClient {
    public static void main(String[] args) {
        CopycatClient client = CopycatClient.builder()
                .withTransport(NettyTransport.builder()
                        .withThreads(2)
                        .build())
                .build();

        client.serializer().register(SCreateCommand.class);
        client.serializer().register(SIdCommand.class);
        client.serializer().register(SPushCommand.class);
        client.serializer().register(SPopCommand.class);
        client.serializer().register(STopCommand.class);
        client.serializer().register(SSizeCommand.class);

        Collection<Address> cluster = Arrays.asList(
                new Address("127.0.0.1", 5000),
                new Address("127.0.0.1", 5001),
                new Address("127.0.0.1", 5002),
                new Address("127.0.0.1", 5003)
        );

        CompletableFuture<CopycatClient> future = client.connect(cluster);
        future.join();
        int label = 2;
        client.submit(new SCreateCommand(label)).thenAccept(result -> {
            System.out.println("Created Stack with ID: " + result);
        });

        client.submit(new SPushCommand(label, 10)).join();
        System.out.println("Done with pushing element on stack");

        client.submit(new STopCommand(label)).thenAccept(result -> {
            System.out.println("Top element on stack with id " + label + ": " + result);
        });

        client.submit(new SSizeCommand(label)).thenAccept(result -> {
            System.out.println("Size of stack with id " + label + ": " + result);
        });

        client.submit(new SPopCommand(label)).thenAccept(result -> {
            System.out.println("Popped out from stack with id " + label + ": " + result);
        });

        client.submit(new SSizeCommand(label)).thenAccept(result -> {
            System.out.println("Size of stack with id " + label + ": " + result);
        });

        CompletableFuture pushFuture2 = client.submit(new SPushCommand(label, 20));
        CompletableFuture.allOf(pushFuture2).thenRun(() -> System.out.println("Done with pushing element on stack"));

        client.submit(new STopCommand(label)).thenAccept(result -> {
            System.out.println("Top element on stack with id " + label + ": " + result);
        });

        client.submit(new SSizeCommand(label)).thenAccept(result -> {
            System.out.println("Size of stack with id " + label + ": " + result);
        });

        client.submit(new SPopCommand(label)).thenAccept(result -> {
            System.out.println("Popped out from stack with id " + label + ": " + result);
        });

        client.submit(new SSizeCommand(label)).thenAccept(result -> {
            System.out.println("Size of stack with id " + label + ": " + result);
        });

    }
}
