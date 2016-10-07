package org.netbeans.modules.bamboo.ui.nodes;

import java.beans.PropertyEditor;
import java.lang.reflect.InvocationTargetException;
import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
import org.openide.util.NbBundle;
import org.netbeans.modules.bamboo.model.ResultVo;
import org.netbeans.modules.bamboo.util.TextExtractor;

import static org.netbeans.modules.bamboo.ui.nodes.Bundle.DESC_Plan_Prop_Result_Reason;
import static org.netbeans.modules.bamboo.ui.nodes.Bundle.TXT_Plan_Prop_Result_Reason;

/**
 *
 * @author spindizzy
 */
@Log
@NbBundle.Messages({
    "TXT_Plan_Prop_Result_Reason=Build Reason",
    "DESC_Plan_Prop_Result_Reason=The reason why this plan was built"
})
public class BuildReasonEditorSupport extends StringReadPropertySupport {

    private static final String BUILD_REASON = "buildReason";

    private final TextExtractor textExtractor;

    private final ResultVo result;

    public BuildReasonEditorSupport(ResultVo result) {
        super(BUILD_REASON, TXT_Plan_Prop_Result_Reason(), DESC_Plan_Prop_Result_Reason());

        textExtractor = new TextExtractor();

        this.result = result;
    }

    @Override
    public PropertyEditor getPropertyEditor() {
        String val = getBuildReason();
        if (textExtractor.containsLink(val)) {
            return new BuildReasonEditor();
        } else {
            return super.getPropertyEditor();
        }
    }

    @Override
    public String getValue() throws IllegalAccessException, InvocationTargetException {
        return getBuildReason();
    }

    private String getBuildReason() {
        String buildReason = result.getBuildReason();
        return (buildReason == null) ? StringUtils.EMPTY : buildReason;
    }

}
