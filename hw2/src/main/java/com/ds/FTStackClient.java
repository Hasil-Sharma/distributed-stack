package com.ds;

import com.ds.commands.*;
import io.atomix.catalyst.transport.Address;
import io.atomix.catalyst.transport.netty.NettyTransport;
import io.atomix.copycat.client.CopycatClient;
import org.slf4j.Logger;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

import static org.slf4j.LoggerFactory.getLogger;

public class FTStackClient {
    public static void main(String[] args) {
        Logger log = getLogger("client");
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
        client.serializer().register(FTStackResult.class);


        Collection<Address> cluster = Arrays.asList(
                new Address("127.0.0.1", 5000),
                new Address("127.0.0.1", 5001),
                new Address("127.0.0.1", 5002),
                new Address("127.0.0.1", 5003)
        );

        CompletableFuture<CopycatClient> future = client.connect(cluster);
        future.join();

        // Making two stacks
//        Object stackId1 = 10, stackId2 = 20;

        FTStackResult stackIdResult1 = client.submit(new SCreateCommand(10)).join();
        log.info("Created Stack with id: " + stackIdResult1);
        Object stackId1 = stackIdResult1.getResult();

        FTStackResult stackIdResult2 = client.submit(new SCreateCommand(20)).join();
        log.info("Created Stack with id: " + stackIdResult2);
        Object stackId2 = stackIdResult2.getResult();

        // Checking label corresponding to stackId(s)
        FTStackResult label1 = client.submit(new SIdCommand(stackId1)).join();
        log.info("Stack with id " + stackId1 + " has label: " + label1);

        FTStackResult label2 = client.submit(new SIdCommand(stackId2)).join();
        log.info("Stack with id " + stackId2 + " has label: " + label2);

         // Pushing elements on stack
        client.submit(new SPushCommand(stackId1, 123)).join();
        log.info("Pushed 123 on stack with id: " + stackId1);

        client.submit(new SPushCommand(stackId2, 345)).join();
        log.info("Pushed 345 on stack with id: " + stackId2);


        // Getting top elements on stack
        FTStackResult top1 = client.submit(new STopCommand(stackId1)).join();
        log.info("Stack with id " + stackId1 + " has top element: " + top1);

        FTStackResult top2 = client.submit(new STopCommand(stackId2)).join();
        log.info("Stack with id " + stackId2 + " has top element: " + top2);


        // Pushing more elements on top
        client.submit(new SPushCommand(stackId1, 124)).join();
        log.info("Pushed 124 on stack with id: " + stackId1);

        top1 = client.submit(new STopCommand(stackId1)).join();
        log.info("Stack with id " + stackId1 + " has top element: " + top1);

        client.submit(new SPushCommand(stackId2, 346)).join();
        log.info("Pushed 346 on stack with id: " + stackId2);

        top2 = client.submit(new STopCommand(stackId2)).join();
        log.info("Stack with id " + stackId2 + " has top element: " + top2);

        // Checking size of the stack

        FTStackResult size1 = client.submit(new SSizeCommand(stackId1)).join();
        log.info("Stack with id " + stackId1 + " has size: " + size1);

        FTStackResult size2 = client.submit(new SSizeCommand(stackId2)).join();
        log.info("Stack with id " + stackId2 + " has size: " + size2);

        // Popping Elements of the stack

        FTStackResult pop1 = client.submit(new SPopCommand(stackId1)).join();
        log.info("Stack with id " + stackId1 + " popped element: " + pop1);

        FTStackResult pop2 = client.submit(new SPopCommand(stackId2)).join();
        log.info("Stack with id " + stackId2 + " popped element: " + pop2);

        // Checking size of the stacks

        size1 = client.submit(new SSizeCommand(stackId1)).join();
        log.info("Stack with id " + stackId1 + " has size: " + size1);

        size2 = client.submit(new SSizeCommand(stackId2)).join();
        log.info("Stack with id " + stackId2 + " has size: " + size2);

        // Checking the top elements in stacks

        top1 = client.submit(new STopCommand(stackId1)).join();
        log.info("Stack with id " + stackId1 + " has top element: " + top1);

        top2 = client.submit(new STopCommand(stackId2)).join();
        log.info("Stack with id " + stackId2 + " has top element: " + top2);

        // Popping again

        pop1 = client.submit(new SPopCommand(stackId1)).join();
        log.info("Stack with id " + stackId1 + " popped element: " + pop1);

        pop2 = client.submit(new SPopCommand(stackId2)).join();
        log.info("Stack with id " + stackId2 + " popped element: " + pop2);


        // Checking the top elements in stacks

        top1 = client.submit(new STopCommand(stackId1)).join();
        log.info("Stack with id " + stackId1 + " has top element: " + top1);

        top2 = client.submit(new STopCommand(stackId2)).join();
        log.info("Stack with id " + stackId2 + " has top element: " + top2);

        stackIdResult1 = client.submit(new SCreateCommand(10)).join();
        log.info("Created Stack with id: " + stackIdResult1);

        client.close();

    }
}
