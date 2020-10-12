package cu.uci.cegel.onei.classifiers.domain;

import cu.uci.cegel.onei.classifiers.infrastructure.data.Set4;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.function.Function;

public final class ClassifierComponents {

    public static AggregationComponent newRootComponent(String code, Set4<AggregationWeight> weights,
                                                        Set<AggregationComponent> subComponents) {
        return new RootComponent(code, weights, subComponents);
    }

    public static AggregationComponent newArticleComponent(String code, Set4<AggregationWeight> weights,
                                                        Set<AggregationComponent> subComponents, BigDecimal indexPrevious) {
        return new ArticleComponent(code, weights, subComponents,indexPrevious);
    }

    public static AggregationComponent newBaseComponent(String code, List<Unit> units, String codeDpa, String codeVariety) {
        return new BaseComponent(code, units, codeDpa, codeVariety);
    }
}
