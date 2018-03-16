package com.ds.commands;

import io.atomix.copycat.Command;

public class SPopCommand implements Command<FTStackResult>{
    private final Object stackId;

    public SPopCommand(Object stackId) {
        this.stackId = stackId;
    }

    public Object getStackId() {
        return stackId;
    }
}
