package cu.uci.cegel.onei.classifiers.domain;

import java.math.BigDecimal;

public interface AggregationComponent {

    BigDecimal indexValue(WeightSpec weightSpec);
    BigDecimal weightValueForSpec(WeightSpec weightSpec);
    String code();
    String code_Dpa();
    String code_Variety();


}
