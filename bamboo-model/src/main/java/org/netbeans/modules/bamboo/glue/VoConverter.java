package org.netbeans.modules.bamboo.glue;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import lombok.extern.java.Log;
import org.netbeans.modules.bamboo.model.PlanVo;
import org.netbeans.modules.bamboo.model.ProjectVo;
import org.netbeans.modules.bamboo.model.ResultVo;
import org.netbeans.modules.bamboo.model.VersionInfo;
import org.netbeans.modules.bamboo.model.rest.Info;
import org.netbeans.modules.bamboo.model.rest.Plan;
import org.netbeans.modules.bamboo.model.rest.Project;
import org.netbeans.modules.bamboo.model.rest.Result;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * Interface for class which convert class from the rest model to class for model to be used for the view.
 *
 * @param <S> the target to convert into
 * @param <T> the source to convert from
 *
 * @author spindizzy
 */
public interface VoConverter<S, T> {

    T convert(S src);

    public static class ProjectVoConverter implements VoConverter<Project, ProjectVo> {

        private final String serverUrl;

        public ProjectVoConverter(String serverUrl) {
            this.serverUrl = serverUrl;
        }

        @Override
        public ProjectVo convert(Project src) {
            ProjectVo target = new ProjectVo(src.getKey());
            target.setName(src.getName());
            target.setServerUrl(serverUrl);
            return target;
        }
    }

    public static class PlanVoConverter implements VoConverter<Plan, PlanVo> {

        private final String serverUrl;

        public PlanVoConverter(String serverUrl) {
            this.serverUrl = serverUrl;
        }

        @Override
        public PlanVo convert(Plan src) {
            PlanVo target = new PlanVo(src.getKey(), src.getName());
            target.setShortKey(src.getShortKey());
            target.setShortName(src.getShortName());
            target.setEnabled(src.isEnabled());
            target.setType(src.getType());
            target.setServerUrl(serverUrl);
            return target;
        }
    }

    static class ResultVoConverter implements VoConverter<Result, ResultVo> {

        @Override
        public ResultVo convert(Result src) {
            ResultVo target = new ResultVo(src.getKey());
            target.setNumber(src.getNumber());
            target.setBuildReason(src.getBuildReason());
            target.setState(src.getState());
            target.setLifeCycleState(src.getLifeCycleState());
            return target;
        }
    }

    static class VersionInfoConverter implements VoConverter<Info, VersionInfo> {

        @Override
        public VersionInfo convert(Info src) {

            VersionInfo target = VersionInfo.builder().version(src.getVersion()).buildNumber(src.getBuildNumber()).build();

            final String buildDate = src.getBuildDate();
            DateConverter dateConverter = new DateConverter();
            dateConverter.convert(buildDate).ifPresent(date -> target.setBuildDate(date));

            return target;
        }

    }

    /**
     * Convert a a string to a {@link LocalDate}. The result will be empty if convert failed.
     */
    @Log
    static class DateConverter implements VoConverter<String, Optional<LocalDate>> {

        private static final String DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS";

        @Override
        public Optional<LocalDate> convert(String src) {
            Optional<LocalDate> opt = empty();
            if (isNotBlank(src)) {
                try {
                    DateTimeFormatter formater = new DateTimeFormatterBuilder()
                            .appendPattern(DATE_PATTERN)
                            .appendOffset("+HH:MM", "+00:00")
                            .toFormatter();
                    opt = of(LocalDate.parse(src, formater));
                } catch (DateTimeParseException ex) {
                    log.fine(ex.getMessage());
                }
            }
            return opt;
        }

    }
}
