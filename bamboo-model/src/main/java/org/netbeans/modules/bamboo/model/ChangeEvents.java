package org.netbeans.modules.bamboo.model;

/**
 * This enum contains possible events fired during an update of the model.
 * @author spindizzy
 */
public enum ChangeEvents {
    
    Projects("projects"), Plans("plans"), Result("result");

    private ChangeEvents(String value){
        this.value = value;
                
    }
    
    private final String value;

    @Override
    public String toString() {
        return value;
    }
    
}
