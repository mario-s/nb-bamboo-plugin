package org.netbeans.modules.bamboo.convert;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.java.Log;
import org.netbeans.modules.bamboo.model.ChangeVo;
import org.netbeans.modules.bamboo.model.PlanVo;
import org.netbeans.modules.bamboo.model.ProjectVo;
import org.netbeans.modules.bamboo.model.ResultVo;
import org.netbeans.modules.bamboo.model.VersionInfo;
import org.netbeans.modules.bamboo.model.rest.Change;
import org.netbeans.modules.bamboo.model.rest.Changes;
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




}
