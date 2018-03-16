package com.ds;

import com.ds.commands.*;
import io.atomix.copycat.server.Commit;
import io.atomix.copycat.server.Snapshottable;
import io.atomix.copycat.server.StateMachine;
import io.atomix.copycat.server.storage.snapshot.SnapshotReader;
import io.atomix.copycat.server.storage.snapshot.SnapshotWriter;
import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

public class FTStackStateMachine extends StateMachine implements Snapshottable {
    private FTStack ftStack = new FTStack();
    private final Logger log = getLogger(getClass());
    public Object sCreate(Commit<SCreateCommand> commit) {
        try {
            return ftStack.sCreate(commit.operation().getLabel());

        } finally {
            commit.close();
        }
    }

    public Object sId(Commit<SIdCommand> commit) {
        try {
            return ftStack.sId(commit.operation().getLabel());
        } finally {
            commit.close();
        }
    }

    public Object sPush(Commit<SPushCommand> commit) {
        try {
            return ftStack.sPush(commit.operation().getStackId(), commit.operation().getItem());
        } finally {
            commit.close();
        }
    }

    public Object sPop(Commit<SPopCommand> commit) {
        try {
            log.info("In sPop method: Element to pop: " + ftStack.sTop(commit.operation().getStackId()));
            Object poppedItem = ftStack.sPop(commit.operation().getStackId());
            log.info("In sPop method: Element popped: " + poppedItem);
            return poppedItem;

        } finally {
            commit.close();
        }
    }

    public Object sTop(Commit<STopCommand> commit) {
        try {
            return ftStack.sTop(commit.operation().getStackId());
        } finally {
            commit.close();
        }
    }

    public Object sSize(Commit<SSizeCommand> commit) {
        try {
            return ftStack.sSize(commit.operation().getStackId());
        } finally {
            commit.close();
        }
    }

    @Override
    public void snapshot(SnapshotWriter writer) {
        writer.writeObject(ftStack);
    }


    @Override
    public void install(SnapshotReader reader) {
        ftStack = reader.readObject();
    }
}
