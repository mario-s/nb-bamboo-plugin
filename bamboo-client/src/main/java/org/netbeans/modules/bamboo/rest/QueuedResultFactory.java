package org.netbeans.modules.bamboo.rest;

import org.netbeans.modules.bamboo.model.LifeCycleState;
import org.netbeans.modules.bamboo.model.ResultVo;
import org.openide.util.NbBundle.Messages;

import static org.netbeans.modules.bamboo.rest.Bundle.By_User;

/**
 * Creates a new Result that indicated a queued plan.
 *
 * @author spindizzy
 */
@Messages("By_User=The plan was invoked by the user.")
final class QueuedResultFactory {

    private QueuedResultFactory() {
    }

    /**
     * This method creates a new {@link ResultVo} based on the current.
     * The number will be increased by one, and the {@link LifeCycleState} will be queued.
     * @param current the origin reason
     * @return the new expected result, which is similar to the result from the server.
     */
    static ResultVo newResult(ResultVo current) {
        ResultVo next = new ResultVo(current.getKey());
        int number = current.getNumber();
        int nextNum = ++number;
        next.setNumber(nextNum);
        next.setLifeCycleState(LifeCycleState.Queued);
        next.setBuildReason(By_User());
        return next;
    }

}
