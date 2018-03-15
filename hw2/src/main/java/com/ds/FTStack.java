package com.ds;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class FTStack implements Serializable {
    private Map<Object, Stack<Object>> stackIdStackMap = new HashMap<>();
    private Map<Object, Object> labelStackIdMap = new HashMap<>();

    public Object sCreate(Object label) {
        labelStackIdMap.put(label, label);
        stackIdStackMap.put(label, new Stack<>());
        return label;
    }

    public Object sId(Object label) {
        return labelStackIdMap.getOrDefault(label, null);
    }

    public void sPush(Object stackId, Object item) {
        Stack<Object> stack = stackIdStackMap.getOrDefault(stackId, null);
        if (stack != null) stack.push(item);
    }

    public Object sPop(Object stackId) {
        Stack<Object> stack = stackIdStackMap.getOrDefault(stackId, null);
        return (stack != null) ? stack.pop() : null;
    }

    public Object sTop(Object stackId) {
        Stack<Object> stack = stackIdStackMap.getOrDefault(stackId, null);
        return (stack != null) ? stack.peek() : null;
    }

    public Object sSize(Object stackId){
        Stack<Object> stack = stackIdStackMap.getOrDefault(stackId, null);
        return (stack != null) ? stack.size() : null;
    }
}
