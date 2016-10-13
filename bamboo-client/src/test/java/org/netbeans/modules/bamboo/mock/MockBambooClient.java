package org.netbeans.modules.bamboo.mock;

import org.netbeans.modules.bamboo.model.InstanceValues;
import org.netbeans.modules.bamboo.model.VersionInfo;
import org.netbeans.modules.bamboo.glue.BambooServiceAccessable;

import org.openide.util.lookup.ServiceProvider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.netbeans.modules.bamboo.model.ProjectVo;

/**
 * @author spindizzy
 */
@ServiceProvider(service = BambooServiceAccessable.class, position = 10)
public class MockBambooClient implements BambooServiceAccessable {
    private BambooServiceAccessable delegate;

    @Override
    public boolean existsService() {
        if(delegate != null){
            return delegate.existsService();
        }
        return true;
    }

    @Override
    public Collection<ProjectVo> getProjects() {
        if(delegate != null){
            return delegate.getProjects();
        }
        List<ProjectVo> projects = new ArrayList<>();
        projects.add(new ProjectVo(""));

        return projects;
    }

    @Override
    public VersionInfo getVersionInfo() {
       if(delegate != null){
            return delegate.getVersionInfo();
        }
        return new VersionInfo();
    }

    @Override
    public void updateProjects(Collection<ProjectVo> projects) {
       if(delegate != null){
            delegate.updateProjects(projects);
        }
    }
    
    public void setDelegate(BambooServiceAccessable delegate) {
        this.delegate = delegate;
    }
}
