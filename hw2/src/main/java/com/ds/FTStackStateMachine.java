package com.ds;

import com.ds.commands.*;
import io.atomix.copycat.Command;
import io.atomix.copycat.server.Commit;
import io.atomix.copycat.server.Snapshottable;
import io.atomix.copycat.server.StateMachine;
import io.atomix.copycat.server.storage.snapshot.SnapshotReader;
import io.atomix.copycat.server.storage.snapshot.SnapshotWriter;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class FTStackStateMachine extends StateMachine implements Snapshottable{
    private Map<Object, Stack<Object>> stackIdStackMap = new HashMap<>();
    private Map<Object, Object> labelStackIdMap = new HashMap<>();

    public Object sCreate(Commit<SCreateCommand> commit){
        try {
            Object label = commit.operation().getLabel();
            labelStackIdMap.put(label, label); // adding label and stack id
            stackIdStackMap.put(label, new Stack<>()); // adding a new stack corresponding to stack id
            return label;

        } finally {
            commit.close();
        }
    }

    public Object sId(Commit<SIdCommand> commit){
        try {
            Object label = commit.operation().getLabel();
            return labelStackIdMap.getOrDefault(label, null);
        } finally {
            commit.close();
        }
    }
    public void sPush(Commit<SPushCommand> commit){
        try {
            Object key = commit.operation().getStackId();
            Stack<Object> stack = stackIdStackMap.getOrDefault(key, null);
            if (stack != null) stack.push(commit.operation().getItem());
        } finally {
            commit.close();
        }
    }

    public Object sPop(Commit<SPopCommand> commit){
        try {
            Object key = commit.operation().getStackId();
            Stack<Object> stack = stackIdStackMap.getOrDefault(key, null);
            return (stack != null) ? stack.pop() : null;
        } finally {
            commit.close();
        }
    }

    public Object sTop(Commit<STopCommand> commit){
        try {
            Object key = commit.operation().getStackId();
            Stack<Object> stack = stackIdStackMap.getOrDefault(key, null);
            return (stack != null) ? stack.peek() : null;
        } finally {
            commit.close();
        }
    }

    public Object sSize(Commit<SSizeCommand> commit){
        try {
            Object key = commit.operation().getStackId();
            Stack<Object> stack = stackIdStackMap.getOrDefault(key, null);
            return (stack != null) ? stack.size() : null;
        } finally {
            commit.close();
        }
    }
    @Override
    public void snapshot(SnapshotWriter writer) {
        writer.writeObject(stackIdStackMap);
    }


    @Override
    public void install(SnapshotReader reader) {
        stackIdStackMap = reader.readObject();
    }
}
