package cu.uci.cegel.onei.classifiers.domain;

import lombok.Value;

@Value
public class WeightSpec {
    private WeightType weightType;
    private Currency currency;

    public static WeightSpec of(WeightType weightType, Currency currency) {
        return new WeightSpec(weightType, currency);
    }
}
