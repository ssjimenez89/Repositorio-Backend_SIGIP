package cu.uci.cegel.onei.classifiers.domain;

import cu.uci.cegel.onei.classifiers.infrastructure.function.ArithmeticFunctions;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

import cu.uci.cegel.onei.classifiers.infrastructure.function.ArithmeticFunctions;

public final class BaseComponent implements AggregationComponent {

    private final String code;
    private final BinaryOperator<Double> priceRelationFunction;
    private final Function<List<BigDecimal>, BigDecimal> relativeReduction;
    private final Function<List<BigDecimal>, BigDecimal> baseIndexFunction;
    private final List<Unit> variations;

    private final String codeDpa;
    private final String codeVariety;


    public BaseComponent(String code,List<Unit> variations,String codeDpa, String codeVariety) {
        this.code = code;
        this.priceRelationFunction = ArithmeticFunctions.RATIO;
        this.relativeReduction = ArithmeticFunctions.ARITHMETIC_MEDIA;
        this.baseIndexFunction = ArithmeticFunctions.GEOMETRIC_MEDIA;
        this.variations = variations;
        this.codeDpa = codeDpa;
        this.codeVariety=codeVariety;
    }

    @Override
    public BigDecimal indexValue(WeightSpec weightSpec) {
        return baseIndexFunction.apply(
                variations.stream()
                        .map(unit -> unit.priceRelative(priceRelationFunction, relativeReduction))
                        .collect(Collectors.toList())
        );
    }

    @Override
    public BigDecimal weightValueForSpec(WeightSpec weightSpec) {
        return BigDecimal.ONE;
    }

    @Override
    public String code() {
        return code;
    }

    @Override
    public String code_Dpa() {
        return codeDpa;
    }

    @Override
    public String code_Variety() {
        return codeVariety;
    }
}
