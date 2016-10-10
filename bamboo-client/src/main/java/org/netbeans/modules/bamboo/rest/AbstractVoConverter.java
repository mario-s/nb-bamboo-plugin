package org.netbeans.modules.bamboo.rest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import lombok.extern.java.Log;
import org.netbeans.modules.bamboo.model.AbstractVo;
import org.netbeans.modules.bamboo.model.PlanVo;
import org.netbeans.modules.bamboo.model.ProjectVo;
import org.netbeans.modules.bamboo.model.ResultVo;
import org.netbeans.modules.bamboo.model.VersionInfo;
import org.netbeans.modules.bamboo.model.rest.Info;
import org.netbeans.modules.bamboo.model.rest.Plan;
import org.netbeans.modules.bamboo.model.rest.Project;
import org.netbeans.modules.bamboo.model.rest.Result;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 *
 * @author spindizzy
 */
@Log
abstract class AbstractVoConverter<S, T> {

    abstract T convert(S src);

    static class ProjectVoConverter extends AbstractVoConverter<Project, ProjectVo> {
        private final String serverUrl;

        public ProjectVoConverter(String serverUrl) {
            this.serverUrl = serverUrl;
        }
        
        @Override
        ProjectVo convert(Project src) {
            ProjectVo target = new ProjectVo(src.getKey());
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
            PlanVo target = new PlanVo(src.getKey(), src.getName());
            target.setShortKey(src.getShortKey());
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
            ResultVo target = new ResultVo(src.getKey());
            target.setNumber(src.getNumber());
            target.setBuildReason(src.getBuildReason());
            target.setState(src.getState());
            target.setLifeCycleState(src.getLifeCycleState());
            return target;
        }
    }
    
    static class VersionInfoConverter extends AbstractVoConverter<Info, VersionInfo> {
        
        private static final String BUILD_DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS";

        @Override
        VersionInfo convert(Info src) {
            VersionInfo target = new VersionInfo();
            target.setVersion(src.getVersion());
            target.setBuildNumber(src.getBuildNumber());

            final String buildDate = src.getBuildDate();

            if (isNotBlank(buildDate)) {
                try {
                    DateTimeFormatter formater = new DateTimeFormatterBuilder()
                            .appendPattern(BUILD_DATE_PATTERN)
                            .appendOffset("+HH:MM", "+00:00")
                            .toFormatter();
                    target.setBuildDate(LocalDate.parse(buildDate, formater));
                } catch (DateTimeParseException ex) {
                    log.fine(ex.getMessage());
                }
            }
            return target;
        }
        
    }
}
