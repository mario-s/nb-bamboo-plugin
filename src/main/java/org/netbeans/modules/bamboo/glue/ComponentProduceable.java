package org.netbeans.modules.bamboo.glue;

import javax.swing.JComponent;

/**
 * this interfaces describes a way to create a {@link JComponent}.
 * @author spindizzy
 */
public interface ComponentProduceable {
    JComponent create(String text);
}
