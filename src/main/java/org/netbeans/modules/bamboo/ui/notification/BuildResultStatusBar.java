package org.netbeans.modules.bamboo.ui.notification;

import java.awt.Component;
import org.openide.awt.StatusLineElementProvider;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author spindizzy
 */
//@ServiceProvider(service = StatusLineElementProvider.class, position=1)
public class BuildResultStatusBar implements StatusLineElementProvider{

    @Override
    public Component getStatusLineElement() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
}
