package com.ds;

import com.ds.commands.FTStackResult;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class FTStack implements Serializable {
    private Map<Object, Stack<Object>> stackIdStackMap = new HashMap<>();
    private Map<Object, Object> labelStackIdMap = new HashMap<>();

    public FTStackResult sCreate(Object label) {
        Object stackId = labelStackIdMap.getOrDefault(label, null);
        if (stackId != null) {
            return new FTStackResult(true, "Stack with label: " + label + " already exists");
        } else {
            labelStackIdMap.put(label, label);
            stackIdStackMap.put(label, new Stack<>());
            return new FTStackResult(label);
        }
    }

    public FTStackResult sId(Object label) {
        Object id = labelStackIdMap.getOrDefault(label, null);
        return (id != null) ? new FTStackResult(id) : new FTStackResult(true, "Stack with label: "
                + label + " does not exist");
    }

    public void sPush(Object stackId, Object item) {
        Stack<Object> stack = stackIdStackMap.getOrDefault(stackId, null);
        if (stack != null) stack.push(item);
    }

    public FTStackResult sPop(Object stackId) {
        Stack<Object> stack = stackIdStackMap.getOrDefault(stackId, null);
        if (stack != null) {
            if (stack.size() > 0) {
                return new FTStackResult(stack.pop());
            } else {
                return new FTStackResult(true, "Stack with id: " + stackId + " cannot be popped (Size = 0)");
            }
        } else {
            return new FTStackResult(true, "Stack with id: " + stackId + " do not exist");
        }
    }

    public FTStackResult sTop(Object stackId) {
        Stack<Object> stack = stackIdStackMap.getOrDefault(stackId, null);
        if (stack != null) {
            if (stack.size() > 0) {
                return new FTStackResult(stack.peek());
            } else {
                return new FTStackResult(true, "Stack with id: " + stackId + " has no top element (Size = 0)");
            }
        } else {
            return new FTStackResult(true, "Stack with id: " + stackId + " do not exist");
        }
    }

    public FTStackResult sSize(Object stackId) {
        Stack<Object> stack = stackIdStackMap.getOrDefault(stackId, null);
        if (stack != null) {
            return new FTStackResult(stack.size());
        } else {
            return new FTStackResult(true, "Stack with id: " + stackId + "do not exist");
        }
    }
}
