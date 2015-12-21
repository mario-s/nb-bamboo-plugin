package org.netbeans.modules.bamboo.model;

import java.util.Arrays;
import java.util.List;

public enum LifeCycleState {
    Queued,
    Pending,
    NotBuilt,
    InProgress,
    Finished;

    public static List<LifeCycleState> getSuccessfulBambooLifeCycle() {
        return Arrays.asList(Queued, Pending, InProgress, Finished);
    }

    public static List<LifeCycleState> getFailedBambooLifeCycle() {
        return Arrays.asList(Queued, Pending, InProgress, NotBuilt);
    }
}
