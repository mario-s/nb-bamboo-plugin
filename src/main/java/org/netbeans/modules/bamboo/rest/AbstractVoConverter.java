package org.netbeans.modules.bamboo.rest;

import org.netbeans.modules.bamboo.model.AbstractVo;
import org.netbeans.modules.bamboo.model.PlanVo;
import org.netbeans.modules.bamboo.model.ProjectVo;
import org.netbeans.modules.bamboo.model.ResultVo;
import org.netbeans.modules.bamboo.model.rest.Plan;
import org.netbeans.modules.bamboo.model.rest.Project;
import org.netbeans.modules.bamboo.model.rest.Result;

/**
 *
 * @author spindizzy
 */
abstract class AbstractVoConverter<S, T extends AbstractVo> {

    abstract T convert(S src);

    static class ProjectVoConverter extends AbstractVoConverter<Project, ProjectVo> {
        private final String serverUrl;

        public ProjectVoConverter(String serverUrl) {
            this.serverUrl = serverUrl;
        }
        
        @Override
        ProjectVo convert(Project src) {
            ProjectVo target = new ProjectVo();
            target.setKey(src.getKey());
            target.setName(src.getName());
            target.setServerUrl(serverUrl);
            return target;
        }

    }

    static class PlanVoConverter extends AbstractVoConverter<Plan, PlanVo> {
        
        private final String serverUrl;

        public PlanVoConverter(String serverUrl) {
            this.serverUrl = serverUrl;
        }
        
        @Override
        PlanVo convert(Plan src) {
            PlanVo target = new PlanVo();
            target.setKey(src.getKey());
            target.setShortKey(src.getShortKey());
            target.setName(src.getName());
            target.setShortName(src.getShortName());
            target.setEnabled(src.isEnabled());
            target.setType(src.getType());
            target.setServerUrl(serverUrl);
            return target;
        }

    }

    static class ResultVoConverter extends AbstractVoConverter<Result, ResultVo> {

        @Override
        ResultVo convert(Result src) {
            ResultVo target = new ResultVo();
            target.setKey(src.getKey());
            target.setNumber(src.getNumber());
            target.setBuildReason(src.getBuildReason());
            target.setState(src.getState());
            target.setLifeCycleState(src.getLifeCycleState());
            return target;
        }

    }
}
