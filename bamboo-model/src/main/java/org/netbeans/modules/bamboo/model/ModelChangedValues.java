package org.netbeans.modules.bamboo.model;

/**
 * This enum contains values for properties which change during an update of the model.
 * 
 * @author spindizzy
 */
public enum ModelChangedValues {
    
    /**
     * This value indicates that the availability of the server has changed.
     */
    Available("available"),
    
    /**
     * This value indicates that sychronizing just started or stoped.
     */
    Synchronizing("synchronizing"), 
    /**
     * This value is associated with a change of all projects.
     */
    Projects("projects"), 
    /**
     * This value is associated with a change of all plans.
     */
    Plans("plans"), 
    /**
     * This value is associated with a change of one plan.
     */
    Plan("plan"), 
    /**
     * This value is associated with a change of the result of one plan.
     */
    Result("result");

    private ModelChangedValues(String value){
        this.value = value;
                
    }
    
    private final String value;

    @Override
    public String toString() {
        return value;
    }
    
}