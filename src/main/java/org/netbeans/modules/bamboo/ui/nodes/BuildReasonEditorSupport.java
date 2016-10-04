package org.netbeans.modules.bamboo.ui.nodes;

import java.beans.PropertyEditor;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
import org.openide.util.NbBundle;
import org.netbeans.modules.bamboo.glue.TextExtractable;
import org.netbeans.modules.bamboo.model.ResultVo;
import static org.netbeans.modules.bamboo.ui.nodes.Bundle.DESC_Plan_Prop_Result_Reason;
import static org.netbeans.modules.bamboo.ui.nodes.Bundle.TXT_Plan_Prop_Result_Reason;
import static org.openide.util.Lookup.getDefault;

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

    private final TextExtractable textExtractor;

    private final ResultVo result;

    public BuildReasonEditorSupport(ResultVo result) {
        super(BUILD_REASON, TXT_Plan_Prop_Result_Reason(), DESC_Plan_Prop_Result_Reason());

        textExtractor = getDefault().lookup(TextExtractable.class);

        this.result = result;
    }

    @Override
    public String getValue() throws IllegalAccessException, InvocationTargetException {
        String buildReason = result.getBuildReason();
        return (buildReason == null) ? StringUtils.EMPTY : buildReason;
    }

    @Override
    public PropertyEditor getPropertyEditor() {
        try {
            String val = getValue();
            if (textExtractor.containsLink(val)) {
                return new BuildReasonEditor();
            } else {
                return super.getPropertyEditor();
            }
        } catch (IllegalAccessException | InvocationTargetException ex) {
            log.log(Level.INFO, ex.getMessage(), ex);
            return super.getPropertyEditor();
        }

    }

}
