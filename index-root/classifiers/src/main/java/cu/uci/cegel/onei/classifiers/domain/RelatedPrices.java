package cu.uci.cegel.onei.classifiers.domain;

import java.math.BigDecimal;
import java.util.function.BinaryOperator;

public class RelatedPrices {
    private double currentPrice;
    private double previousPrice;

    private RelatedPrices(double currentPrice, double previousPrice) {
        this.currentPrice = currentPrice;
        this.previousPrice = previousPrice;
    }

    public static RelatedPrices of(double currentPrice, double previousPrice) {
        return new RelatedPrices(currentPrice, previousPrice);
    }

    public BigDecimal applyRelationFunction(BinaryOperator<Double> relationFunction) {
        return BigDecimal.valueOf(relationFunction.apply(currentPrice, previousPrice));
    }
}
