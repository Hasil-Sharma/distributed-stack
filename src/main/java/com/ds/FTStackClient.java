package com.ds;

import com.ds.commands.*;
import io.atomix.catalyst.transport.Address;
import io.atomix.catalyst.transport.netty.NettyTransport;
import io.atomix.copycat.client.CopycatClient;
import org.slf4j.Logger;

import java.util.Arrays;
import java.util.Collection;
import java.util.Scanner;
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
                new Address("127.0.0.1", 5003),
                new Address("127.0.0.1", 5004)
        );

        CompletableFuture<CopycatClient> future = client.connect(cluster);
        future.join();

        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.println("\tEnter the operation number you want to execute:");
            System.out.println("=> Create a stack : 1");
            System.out.println("=> Get label of stack given id : 2");
            System.out.println("=> Pop the stack of given id : 3");
            System.out.println("=> Push the element on stack given id : 4");
            System.out.println("=> Get size of stack given id : 5");
            System.out.println("=> Get top element of stack given id : 6");
            System.out.println("=> Exit : 7");

            int command = in.nextInt();

            FTStackResult ftStackResult;
            String outputString;
            if (command == 1) {
                System.out.println("Enter the label of the stack: ");
                int label = in.nextInt();
                ftStackResult = client.submit(new SCreateCommand(label)).join();
                outputString = "<= Created stack with id: ";
            } else if (command == 2) {
                System.out.println("Enter the id of the stack: ");
                int id = in.nextInt();
                ftStackResult = client.submit(new SIdCommand(id)).join();
                outputString = "<= Label of the stack is: ";
            } else if (command == 3) {
                System.out.println("Enter the id of the stack: ");
                int id = in.nextInt();
                ftStackResult = client.submit(new SPopCommand(id)).join();
                outputString = "<= Popped element from stack is: ";
            } else if (command == 4) {
                System.out.println("Enter the id of the stack: ");
                int id = in.nextInt();
                System.out.println("Enter the element to push: ");
                int element = in.nextInt();
                ftStackResult = client.submit(new SPushCommand(id, element)).join();
                outputString = "<= Element pushed on to stack: ";
            } else if (command == 5) {
                System.out.println("Enter the id of the stack: ");
                int id = in.nextInt();
                ftStackResult = client.submit(new SSizeCommand(id)).join();
                outputString = "<= Size of the stack is: ";
            } else if (command == 6) {
                System.out.println("Enter the id of the stack: ");
                int id = in.nextInt();
                ftStackResult = client.submit(new STopCommand(id)).join();
                outputString = "<= Top of the stack is: ";
            } else break;

            if (ftStackResult.getError())
                System.out.println(ftStackResult.getErrorString());
            else
                System.out.println(outputString + ftStackResult.getResult());
        }

        client.close();

    }
}
