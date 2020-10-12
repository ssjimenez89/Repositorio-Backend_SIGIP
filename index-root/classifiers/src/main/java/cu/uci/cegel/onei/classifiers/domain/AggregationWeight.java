package cu.uci.cegel.onei.classifiers.domain;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class AggregationWeight {
    private BigDecimal value;
    private WeightType weightType;
    private Currency currency;

    public boolean satisfySpec(WeightSpec weightSpec) {
        return this.weightType.equals(weightSpec.getWeightType()) && this.currency.equals(weightSpec.getCurrency());
    }

    public static AggregationWeight with(BigDecimal value, WeightType weightType, Currency currency) {
        return new AggregationWeight(value, weightType, currency);
    }

    public boolean equals(Object obj) {
        return obj instanceof AggregationWeight
                && ((AggregationWeight) obj).weightType.equals(this.weightType)
                && ((AggregationWeight) obj).currency.equals(this.currency);

    }

    @Override
    public int hashCode() {
        return 31 * currency.hashCode() * weightType.hashCode();
    }
}
