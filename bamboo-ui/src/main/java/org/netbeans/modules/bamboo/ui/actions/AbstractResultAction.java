/* 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.netbeans.modules.bamboo.ui.actions;

import java.awt.event.ActionEvent;
import java.util.Collection;
import java.util.Optional;
import static org.apache.commons.lang3.StringUtils.isBlank;


import org.netbeans.api.io.OutputWriter;
import org.netbeans.modules.bamboo.model.rcp.InstanceInvokeable;
import org.netbeans.modules.bamboo.model.rcp.PlanVo;
import org.netbeans.modules.bamboo.model.rcp.ResultVo;
import org.netbeans.modules.bamboo.ui.util.TextExtractor;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.NbBundle;
import static org.openide.util.NbBundle.getMessage;
import org.openide.util.RequestProcessor;

/**
 *
 * @author Mario Schroeder
 */
@NbBundle.Messages({
    "No_Changes=No changes. Build reason: {0}",
    "No_Issues=No issues. Build reason: {0}"
})
abstract class AbstractResultAction extends AbstractContextAction {

    protected static final String SPC = ": ";

    private Lookup.Result<InstanceInvokeable> result;

    public AbstractResultAction() {
    }

    public AbstractResultAction(String name, Lookup context) {
        super(name, context);
        init();
    }

    private void init() {
        result = getContext().lookupResult(InstanceInvokeable.class);
        result.addLookupListener(this);
        resultChanged(null);
    }

    @Override
    public void resultChanged(LookupEvent ev) {
        enableIfAvailable(allInstances());
    }

    protected Collection<? extends InstanceInvokeable> allInstances() {
        return result.allInstances();
    }

    protected Optional<PlanVo> findFirst() {
        return (Optional<PlanVo>) allInstances().stream().findFirst();
    }

    protected OutputWriter getOut(String name) {
        InputOutputProvider provider = new InputOutputProvider();
        return provider.getOut(name);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        findFirst().ifPresent(p -> new RequestProcessor(getClass()).post(() -> {
            doRun(p.getResult());
        }));
    }
    
    protected abstract void doRun(ResultVo res);

    /**
     * Prints only the build reason
     * @param name name for the output tab
     * @param result result with build reason
     */
    protected void printBuildReason(String name, String messageKey, ResultVo result) {
        TextExtractor extractor = new TextExtractor();
        String reason = result.getBuildReason();
        String normalized = (!isBlank(reason)) ? extractor.removeTags(reason) : "";
        String msg = getMessage(AbstractResultAction.class, messageKey, new Object[]{normalized});
        
        try(OutputWriter out = getOut(name)){
            out.println(msg);
        }
    }


}
