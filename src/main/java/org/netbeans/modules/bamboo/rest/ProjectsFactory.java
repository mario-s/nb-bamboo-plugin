package org.netbeans.modules.bamboo.rest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import org.netbeans.modules.bamboo.model.PlanVo;
import org.netbeans.modules.bamboo.model.ProjectVo;
import org.netbeans.modules.bamboo.model.ResultVo;
import org.netbeans.modules.bamboo.model.rest.Plan;
import org.netbeans.modules.bamboo.model.rest.Project;
import org.netbeans.modules.bamboo.model.rest.Result;
import org.netbeans.modules.bamboo.rest.AbstractVoConverter.PlanVoConverter;
import org.netbeans.modules.bamboo.rest.AbstractVoConverter.ProjectVoConverter;
import org.netbeans.modules.bamboo.rest.AbstractVoConverter.ResultVoConverter;

/**
 *
 * @author spindizzy
 */
final class ProjectsFactory {

    private final ProjectVoConverter projectConverter;
    private final PlanVoConverter planConverter;
    private final ResultVoConverter resultConverter;

    private Collection<Project> projects;

    private Collection<Plan> plans;

    ProjectsFactory(String serverUrl) {
        projectConverter = new ProjectVoConverter(serverUrl);
        planConverter = new PlanVoConverter(serverUrl);
        resultConverter = new ResultVoConverter();
    }

    Collection<ProjectVo> create() {
        Collection<ProjectVo> vos = new ArrayList<>();

        if (projects != null && plans != null) {

            projects.forEach(project -> {

                ProjectVo projectVo = projectConverter.convert(project);

                List<PlanVo> planVos = new ArrayList<>();

                project.plansAsCollection().forEach(projectPlan -> {
                    String planKey = projectPlan.getKey();
                    Optional<PlanVo> extracted = extractPlan(planKey, plans);
                    if (extracted.isPresent()) {
                        PlanVo planVo = extracted.get();
                        planVos.add(planVo);
                    }
                });

                projectVo.setPlans(planVos);

                vos.add(projectVo);

            });
        }

        return vos;
    }

    private Optional<PlanVo> extractPlan(String planKey, Collection<Plan> plans) {
        Optional<PlanVo> vo = empty();
        for (Plan plan : plans) {
            if (planKey.equals(plan.getKey())) {
                PlanVo planVo = planConverter.convert(plan);

                Result result = plan.getResult();
                if (result != null) {
                    ResultVo resultVo = resultConverter.convert(result);
                    planVo.setResult(resultVo);
                }

                vo = of(planVo);
            }
        }
        return vo;

    }

    void setProjects(Collection<Project> projects) {
        this.projects = projects;
    }

    void setPlans(Collection<Plan> plans) {
        this.plans = plans;
    }

}
