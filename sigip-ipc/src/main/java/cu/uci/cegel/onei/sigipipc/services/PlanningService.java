package cu.uci.cegel.onei.sigipipc.services;

import cu.uci.cegel.onei.sigipipc.model.PlanningInput;
import cu.uci.cegel.onei.sigipipc.persistence.Planning;

import java.util.List;


public interface PlanningService {

    List<Planning> findAll();

    List<Planning> findAllPaging(int page, int size);

    Planning addPlaning(PlanningInput planningInput);

    Planning editPlaning(PlanningInput planningInput);

    Planning planningById(long id);

    int totalPlannings();

    List<Planning> planningsDayXWeek(int week1, int week2, int week3, int week4);


}
