package com.ds.commands;

import io.atomix.copycat.Query;

public class SIdCommand implements Query<FTStackResult> {
    private final Object label;

    public SIdCommand(Object label){
        this.label = label;
    }

    public Object getLabel() {
        return label;
    }
}
