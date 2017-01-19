package org.netbeans.modules.bamboo.ui.actions;

import java.io.IOException;
import lombok.extern.java.Log;
import org.netbeans.api.io.InputOutput;
import org.netbeans.api.io.OutputWriter;
import org.openide.windows.IOProvider;

/**
 *
 * @author Mario Schroeder
 */
@Log
class InputOutputProvider {
    
    OutputWriter getOut(String name) {
        InputOutput io = getInputOutput(name);
        io.show();
        return io.getOut();
    }
    
    private InputOutput getInputOutput(String name) {
        reset(name);
        return InputOutput.get(name, false);
    }
    
    private void reset(String name) {
        try {
            IOProvider.getDefault().getIO(name, false).getOut().reset();
        } catch (IOException ex) {
            log.info(ex.getMessage());
        }
    }
    
}
