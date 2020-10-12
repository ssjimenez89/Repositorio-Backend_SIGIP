package cu.uci.cegel.onei.classifiers.domain;

import cu.uci.cegel.onei.classifiers.domain.AggregationComponent;
import cu.uci.cegel.onei.classifiers.domain.WeightSpec;
import cu.uci.cegel.onei.classifiers.domain.WeightType;
import cu.uci.cegel.onei.classifiers.infrastructure.function.ArithmeticFunctions;

import java.math.BigDecimal;
import java.util.Set;
import java.util.function.Function;

public final class IndexFunctions {

    public static Function<WeightSpec, Function<Set<AggregationComponent>, BigDecimal>> WEIGHT_SUM_FUNCTION =
            weightSpec -> {
                WeightType weightType = weightSpec.getWeightType();
                switch (weightType) {
                    case TOTAL:
                        return aggregationComponents -> aggregationComponents.stream()
                                .map(aggregationComponent -> aggregationComponent.weightValueForSpec(weightSpec))
                                .reduce(ArithmeticFunctions.SCALED_SUM)
                                .orElse(BigDecimal.ONE);
                    case RUBRO:
                        return aggregationComponents -> BigDecimal.ONE;
                    default:
                        return aggregationComponents -> BigDecimal.ONE;
                }
            };


    public static Function<WeightSpec, Function<AggregationComponent, BigDecimal>> WEIGHT_INDEX_PRODUCT =
            weightSpec -> aggregationComponent -> aggregationComponent.indexValue(weightSpec)
                    .multiply(aggregationComponent.weightValueForSpec(weightSpec));


}
