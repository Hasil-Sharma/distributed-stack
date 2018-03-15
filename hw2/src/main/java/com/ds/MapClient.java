package com.ds;

import com.ds.commands.GetQuery;
import com.ds.commands.PutCommand;
import io.atomix.catalyst.transport.Address;
import io.atomix.catalyst.transport.netty.NettyTransport;
import io.atomix.copycat.client.CopycatClient;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public class MapClient {
    public static void main(String[] args) {
        CopycatClient client = CopycatClient.builder()
                .withTransport(NettyTransport.builder()
                        .withThreads(2)
                        .build())
                .build();

        client.serializer().register(PutCommand.class);
        client.serializer().register(GetQuery.class);

        Collection<Address> cluster = Arrays.asList(
                new Address("127.0.0.1", 5000),
                new Address("127.0.0.1", 5001),
                new Address("127.0.0.1", 5002),
                new Address("127.0.0.1", 5003)
        );

        CompletableFuture<CopycatClient> future = client.connect(cluster);
        future.join();

        CompletableFuture[] futures = new CompletableFuture[3];
        futures[0] = client.submit(new PutCommand("foo", "Hello world!"));
        futures[1] = client.submit(new PutCommand("bar", "Hello world!"));
        futures[2] = client.submit(new PutCommand("baz", "Hello world!"));

        CompletableFuture.allOf(futures).thenRun(() -> System.out.println("Commands completed!"));

        client.submit(new GetQuery("foo")).thenAccept(result -> {
            System.out.println("foo is: " + result);
        });
    }
}
