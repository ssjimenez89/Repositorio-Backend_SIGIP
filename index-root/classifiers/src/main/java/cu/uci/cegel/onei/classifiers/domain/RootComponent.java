package cu.uci.cegel.onei.classifiers.domain;

import cu.uci.cegel.onei.classifiers.infrastructure.data.FixedCollection;
import cu.uci.cegel.onei.classifiers.infrastructure.function.ArithmeticFunctions;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Set;
import java.util.function.Function;

import static cu.uci.cegel.onei.classifiers.domain.IndexFunctions.WEIGHT_INDEX_PRODUCT;
import static cu.uci.cegel.onei.classifiers.domain.IndexFunctions.WEIGHT_SUM_FUNCTION;

public final class RootComponent implements AggregationComponent{
    private String code;
    private Set<AggregationComponent> components;
    private FixedCollection<AggregationWeight> weights;

    public RootComponent(String code, FixedCollection<AggregationWeight> weights, Set<AggregationComponent> components) {
        this.code = code;
        this.weights = weights;
        this.components = components;
    }

    public BigDecimal indexValue(WeightSpec weightSpec) {
        Function<AggregationComponent, BigDecimal> toWeightIndexProduct = WEIGHT_INDEX_PRODUCT.apply(weightSpec);
        BigDecimal weightsSumByType = WEIGHT_SUM_FUNCTION
                .apply(weightSpec)
                .apply(components);

        return components.parallelStream()
                .map(toWeightIndexProduct)
                .sequential()
                .reduce(ArithmeticFunctions.SCALED_SUM)
                .map(sum -> sum.divide(weightsSumByType, 30, RoundingMode.HALF_EVEN))
                .orElse(BigDecimal.ZERO);
    }

    public BigDecimal weightValueForSpec(WeightSpec weightSpec) {
        return weights.filter(weight -> weight.satisfySpec(weightSpec))
                .map(AggregationWeight::getValue)
                .orElse(BigDecimal.ZERO);
    }

    @Override
    public String code() {
        return code;
    }

    @Override
    public String code_Dpa() {
        return null;
    }

    @Override
    public String code_Variety() {
        return null;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof AggregationComponent && ((AggregationComponent) obj).code().equals(this.code);
    }
}
