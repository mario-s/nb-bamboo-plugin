package org.netbeans.modules.bamboo.mock;

import org.netbeans.modules.bamboo.glue.InstanceValues;
import org.netbeans.modules.bamboo.glue.VersionInfo;
import org.netbeans.modules.bamboo.rest.BambooServiceAccessable;

import org.openide.util.lookup.ServiceProvider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.netbeans.modules.bamboo.model.ProjectVo;


/**
 * @author spindizzy
 */
@ServiceProvider(service = BambooServiceAccessable.class, position = 10)
public class MockRestClient implements BambooServiceAccessable {
    private BambooServiceAccessable delegate;
    
    @Override
    public Collection<ProjectVo> getProjects(final InstanceValues values) {
        if(delegate != null){
            return delegate.getProjects(values);
        }
        List<ProjectVo> projects = new ArrayList<>();
        projects.add(new ProjectVo());

        return projects;
    }

    @Override
    public VersionInfo getVersionInfo(final InstanceValues values) {
        if(delegate != null){
            return delegate.getVersionInfo(values);
        }
        return new VersionInfo();
    }

    @Override
    public void updateProjects(Collection<ProjectVo> projects, InstanceValues values) {
        if(delegate != null){
            delegate.updateProjects(projects, values);
        }
    }

    public void setDelegate(BambooServiceAccessable delegate) {
        this.delegate = delegate;
    }
    
    
}
