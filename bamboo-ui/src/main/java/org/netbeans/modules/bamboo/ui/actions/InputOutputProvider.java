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
