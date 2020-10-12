package cu.uci.cegel.onei.sigipipc.model;

import lombok.Data;

import java.util.List;

@Data
public class PlanningInput {
    Long id;
    int week;
    String day;
    Long indexId;
    List<Long> establishmentsId;

}


