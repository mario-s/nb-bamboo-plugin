package org.netbeans.modules.bamboo.mock;

import org.netbeans.modules.bamboo.model.Project;
import org.netbeans.modules.bamboo.glue.InstanceValues;
import org.netbeans.modules.bamboo.glue.VersionInfo;
import org.netbeans.modules.bamboo.rest.BambooServiceAccessable;

import org.openide.util.lookup.ServiceProvider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * @author spindizzy
 */
@ServiceProvider(service = BambooServiceAccessable.class, position = 10)
public class MockRestClient implements BambooServiceAccessable {
    @Override
    public Collection<Project> getProjects(final InstanceValues values) {
        List<Project> projects = new ArrayList<>();
        projects.add(new Project());

        return projects;
    }

    @Override
    public VersionInfo getVersionInfo(final InstanceValues values) {
        return new VersionInfo();
    }
}
