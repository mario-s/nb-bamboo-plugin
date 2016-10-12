package org.netbeans.modules.bamboo.rest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import org.netbeans.modules.bamboo.model.InstanceValues;

import static java.util.Optional.empty;
import static java.util.Optional.of;

import org.netbeans.modules.bamboo.model.PlanVo;
import org.netbeans.modules.bamboo.model.ProjectVo;
import org.netbeans.modules.bamboo.model.ResultVo;
import org.netbeans.modules.bamboo.model.rest.Plan;
import org.netbeans.modules.bamboo.model.rest.Project;
import org.netbeans.modules.bamboo.model.rest.Result;
import org.netbeans.modules.bamboo.glue.VoConverter.PlanVoConverter;
import org.netbeans.modules.bamboo.glue.VoConverter.ProjectVoConverter;
import org.netbeans.modules.bamboo.glue.VoConverter.ResultVoConverter;

/**
 * This class constructs a new {@link ProjectVo} with all the children.
 *
 * @author spindizzy
 */
final class ProjectsFactory {

    private final ProjectVoConverter projectConverter;
    private final PlanVoConverter planConverter;
    private final ResultVoConverter resultConverter;

    private Collection<Project> projects;

    private Collection<Plan> plans;

    ProjectsFactory(InstanceValues values) {
        String serverUrl = values.getUrl();
        projectConverter = new ProjectVoConverter(serverUrl);
        planConverter = new PlanVoConverter(serverUrl);
        resultConverter = new ResultVoConverter();
    }

    Collection<ProjectVo> create() {
        Collection<ProjectVo> vos = new ArrayList<>();

        if (projects != null && plans != null) {

            projects.forEach(project -> {
                addProjectVo(vos, project);
            });
        }

        return vos;
    }

    private void addProjectVo(Collection<ProjectVo> vos, Project project) {
        ProjectVo projectVo = projectConverter.convert(project);

        List<PlanVo> planVos = new ArrayList<>();
        Collection<Plan> plansFromProject = project.plansAsCollection();

        plansFromProject.forEach(plan -> {
            createPlanVo(plan).ifPresent(planVo -> {
                planVo.setParent(projectVo);
                planVos.add(planVo);
            });
        });

        projectVo.setChildren(planVos);

        vos.add(projectVo);
    }

    private Optional<PlanVo> createPlanVo(Plan plan) {
        String planKey = plan.getKey();
        return createPlanVo(planKey, plans);
    }

    private Optional<PlanVo> createPlanVo(String planKey, Collection<Plan> plans) {
        Optional<PlanVo> vo = empty();
        Iterator<Plan> itPlans = plans.iterator();
        while (itPlans.hasNext()) {
            Plan plan = itPlans.next();
            if (planKey.equals(plan.getKey())) {
                PlanVo planVo = planConverter.convert(plan);

                Result result = plan.getResult();
                if (result != null) {
                    ResultVo resultVo = resultConverter.convert(result);
                    planVo.setResult(resultVo);
                }

                vo = of(planVo);
                itPlans.remove();
                break;
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
