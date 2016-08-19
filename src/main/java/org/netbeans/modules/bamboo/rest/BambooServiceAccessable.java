package org.netbeans.modules.bamboo.rest;

import org.netbeans.modules.bamboo.glue.BuildProject;
import org.netbeans.modules.bamboo.glue.InstanceValues;
import org.netbeans.modules.bamboo.glue.VersionInfo;

import java.util.Collection;


/**
 * This interface defines methods to access the Bamboo server.
 *
 * @author spindizzy
 */
public interface BambooServiceAccessable {
    Collection<BuildProject> getProjects(InstanceValues values);

    VersionInfo getVersion(InstanceValues values);
}
