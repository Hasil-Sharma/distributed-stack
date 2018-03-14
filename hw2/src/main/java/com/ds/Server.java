package com.ds;

import com.ds.commands.GetQuery;
import com.ds.commands.PutCommand;
import io.atomix.catalyst.transport.Address;
import io.atomix.catalyst.transport.netty.NettyTransport;
import io.atomix.copycat.server.CopycatServer;
import io.atomix.copycat.server.storage.Storage;
import io.atomix.copycat.server.storage.StorageLevel;

import java.io.File;
import java.util.concurrent.CompletableFuture;

public class Server {
    public static void main(String[] args) {
        Address address = new Address("127.0.0.1", 5000);
        CopycatServer server = CopycatServer.builder(address)
                .withStateMachine(MapStateMachine::new)
                .withTransport(NettyTransport.builder()
                        .withThreads(4)
                        .build())
                .withStorage(Storage.builder()
                        .withDirectory(new File("logs"))
                        .withStorageLevel(StorageLevel.DISK)
                        .build())
                .build();
        server.serializer().register(PutCommand.class);
        server.serializer().register(GetQuery.class);

        CompletableFuture<CopycatServer> future = server.bootstrap();
        future.join();

    }
}
