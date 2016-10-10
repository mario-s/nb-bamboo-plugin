package org.netbeans.modules.bamboo.rest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
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
import org.netbeans.modules.bamboo.rest.VoConverter.PlanVoConverter;
import org.netbeans.modules.bamboo.rest.VoConverter.ProjectVoConverter;
import org.netbeans.modules.bamboo.rest.VoConverter.ResultVoConverter;

/**
 * This class constructs a new {@link ProjectVo} with all the children.
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
        Iterator<Plan> itPlans = plans.iterator();
        while(itPlans.hasNext()){
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
