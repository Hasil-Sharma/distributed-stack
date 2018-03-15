package com.ds;

import com.ds.commands.*;
import io.atomix.catalyst.transport.Address;
import io.atomix.catalyst.transport.netty.NettyTransport;
import io.atomix.copycat.server.CopycatServer;
import io.atomix.copycat.server.storage.Storage;
import io.atomix.copycat.server.storage.StorageLevel;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;

public class FTStackServer {
    public static void main(String[] args) {
        int port = 5000;
        int leaderPort = 5000;
        Boolean bootStrap = false;
        String host = "127.0.0.1";
        if (args.length >= 1) {
            port = Integer.parseInt(args[0]);
            bootStrap = (args.length >= 2) ? Boolean.valueOf(args[1]) : bootStrap;
            leaderPort = (args.length == 3) ? Integer.parseInt(args[2]) : leaderPort;
        }

        Address address = new Address(host, port);
        CopycatServer server = CopycatServer.builder(address)
                .withStateMachine(FTStackStateMachine::new)
                .withTransport(NettyTransport.builder()
                        .withThreads(4)
                        .build())
                .withStorage(Storage.builder()
                        .withDirectory(new File("Logs" + Integer.toString(port)))
                        .withStorageLevel(StorageLevel.DISK)
                        .build())
                .build();
        server.serializer().register(SCreateCommand.class);
        server.serializer().register(SIdCommand.class);
        server.serializer().register(SPushCommand.class);
        server.serializer().register(SPopCommand.class);
        server.serializer().register(STopCommand.class);
        server.serializer().register(SSizeCommand.class);

        if (bootStrap) {
            CompletableFuture<CopycatServer> future = server.bootstrap();
            future.join();
        } else {
            Collection<Address> cluster = Collections.singleton(new Address(host, leaderPort));
            server.join(cluster).join();
        }
    }
}
