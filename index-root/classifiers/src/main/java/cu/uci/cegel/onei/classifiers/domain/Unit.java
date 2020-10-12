package cu.uci.cegel.onei.classifiers.domain;


import java.math.BigDecimal;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Unit {
    private String groupingElementCode;
    private List<RelatedPrices> relatedPrices;

    private Unit(String groupingElementCode, List<RelatedPrices> relatedPrices) {
        this.groupingElementCode = groupingElementCode;
        this.relatedPrices = relatedPrices;
    }

    public static Unit of(String groupingElementCode, List<RelatedPrices> relatedPrices) {
        return new Unit(groupingElementCode, relatedPrices);
    }

    public BigDecimal priceRelative(BinaryOperator<Double> relationFunction,
                                    Function<List<BigDecimal>, BigDecimal> reduction) {
        return reduction.apply(
                relatedPrices.stream()
                        .map(rPrices -> rPrices.applyRelationFunction(relationFunction))
                        .collect(Collectors.toList())
        );
    }
}
