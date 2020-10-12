package cu.uci.cegel.onei.classifiers.infrastructure.function;

import ch.obermuhlner.math.big.BigDecimalMath;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;

public final class ArithmeticFunctions {

    public final static BinaryOperator<BigDecimal> SCALED_PRODUCT = (bigDecimalA, bigDecimalB) -> bigDecimalA.multiply(bigDecimalB, MathContext.DECIMAL128);
    public final static BinaryOperator<BigDecimal> SCALED_SUM = (bigDecimalA, bigDecimalB) -> bigDecimalA.add(bigDecimalB, MathContext.DECIMAL128);
    public final static BinaryOperator<Double> RATIO = (doubleA, doubleB) -> doubleA / doubleB;

    public final static Function<List<BigDecimal>, BigDecimal> ARITHMETIC_MEDIA = (elements) -> elements.stream()
            .reduce(SCALED_SUM)
            .map(sum -> sum.divide(BigDecimal.valueOf(elements.size()), 30, RoundingMode.HALF_EVEN))
            .orElse(BigDecimal.ZERO);

    public final static Function<List<BigDecimal>, BigDecimal> GEOMETRIC_MEDIA = elements -> elements.stream()
            .reduce(SCALED_PRODUCT)
            .map(product -> BigDecimalMath.root(product, BigDecimal.valueOf(elements.size()), MathContext.DECIMAL128).setScale(30, RoundingMode.HALF_EVEN))
            .orElse(BigDecimal.ZERO);


}
