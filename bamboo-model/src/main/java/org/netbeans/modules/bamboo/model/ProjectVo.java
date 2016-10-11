package org.netbeans.modules.bamboo.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import lombok.Getter;
import lombok.Setter;
import org.netbeans.modules.bamboo.glue.TraverseDown;
import org.netbeans.modules.bamboo.glue.TraverseUp;

import static java.util.Optional.ofNullable;
import static lombok.AccessLevel.NONE;

/**
 * A class which represent the project.
 *
 * @author spindizzy
 */
@Getter
@Setter
public class ProjectVo extends AbstractOpenInBrowserVo implements TraverseDown<PlanVo>, TraverseUp<BambooInstance>{

    private String name;
    private Collection<PlanVo> children;
    @Getter(NONE)
    private BambooInstance parent;

    public ProjectVo(String key) {
        super(key);
        children = new ArrayList<>();
    }

    @Override
    public Optional<BambooInstance> getParent() {
        return ofNullable(parent);
    } 
    
    @Override
    public void setChildren(Collection<PlanVo> plans) {
        Collection<PlanVo> old = this.children;
        this.children = plans;
        this.children.parallelStream().forEach(p -> p.setParent(this));
        firePropertyChange(ChangeEvents.Plans.toString(), old, plans);
    }

}
