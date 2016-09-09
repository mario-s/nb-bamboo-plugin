package org.netbeans.modules.bamboo.model;

/**
 *
 * @author spindizzy
 */
public enum ModelProperties {
    
    Projects("projects"), Plans("plans"), Result("result");

    private ModelProperties(String value){
        this.value = value;
                
    }
    
    private String value;

    @Override
    public String toString() {
        return value;
    }
    
}
