package org.netbeans.modules.bamboo.model.rcp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import lombok.Getter;
import lombok.Setter;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;
import static lombok.AccessLevel.NONE;

/**
 * A class which represent the project.
 *
 * @author spindizzy
 */
@Getter
@Setter
public class ProjectVo extends AbstractOpenInBrowserVo implements TraverseDown<PlanVo>, TraverseUp<BambooInstance> {

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

    /**
     * Returns all the plans for this project.
     *
     * @return a collection where no element can be added or removed.
     */
    @Override
    public Collection<PlanVo> getChildren() {
        return (children == null) ? emptyList() : asList(children.toArray(new PlanVo[children.size()]));
    }

    @Override
    public void setChildren(Collection<PlanVo> plans) {
        Collection<PlanVo> old = this.children;
        this.children = plans;
        this.children.parallelStream().forEach(p -> p.setParent(this));
        firePropertyChange(ModelChangedValues.Plans.toString(), old, plans);
    }

    @Override
    public boolean isAvailable() {
        return AvailabilityVerifier.isAvailable(this);
    }
}
