package cu.uci.cegel.onei.classifiers;


import cu.uci.cegel.onei.classifiers.domain.*;
import cu.uci.cegel.onei.classifiers.infrastructure.data.Set4;
import cu.uci.cegel.onei.classifiers.infrastructure.function.ArithmeticFunctions;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static cu.uci.cegel.onei.classifiers.domain.ClassifierComponents.newRootComponent;
import static cu.uci.cegel.onei.classifiers.domain.Currency.CUC;
import static cu.uci.cegel.onei.classifiers.domain.Currency.CUP;
import static cu.uci.cegel.onei.classifiers.domain.WeightType.RUBRO;
import static cu.uci.cegel.onei.classifiers.domain.WeightType.TOTAL;
import static java.util.Collections.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class Tests {

    private WeightSpec defaultWeightSpec = WeightSpec.of(RUBRO, CUP);
    //private WeightSpec defaultWeightSpec = WeightSpec.of(TOTAL, CUP);

    @Test
    public void testIndexToClassAfterFixedIndexArticle(){
        AggregationComponent variety1=ClassifierComponents.newBaseComponent
                ("02.1.1.1.01.01",Arrays.asList(
                        Unit.of("",
                                singletonList(
                                        RelatedPrices.of(55.00,60.00)))),
                        "1","1");

        AggregationComponent variety2=ClassifierComponents.newBaseComponent
                ("02.1.1.1.01.02",Arrays.asList(
                        Unit.of("",
                                singletonList(
                                        RelatedPrices.of(100.00,100.00)))),
                        "1","1");

        AggregationComponent variety3=ClassifierComponents.newBaseComponent
                ("02.1.1.1.01.03",Arrays.asList(
                        Unit.of("",
                                singletonList(
                                        RelatedPrices.of(25.00,25.00)))),
                        "1","1");

        AggregationComponent variety4=ClassifierComponents.newBaseComponent
                ("02.1.1.1.01.04",Arrays.asList(
                        Unit.of("",
                                singletonList(
                                        RelatedPrices.of(23.00,25.00)))),
                        "1","2");

        //dpa2
        AggregationComponent variety5=ClassifierComponents.newBaseComponent
                ("02.1.1.1.01.05",Arrays.asList(
                        Unit.of("",
                                singletonList(
                                        RelatedPrices.of(40.00,60.00)))),
                        "2","1");

        AggregationComponent variety6=ClassifierComponents.newBaseComponent
                ("02.1.1.1.01.06",Arrays.asList(
                        Unit.of("",
                                singletonList(
                                        RelatedPrices.of(90.00,100.00)))),
                        "2","1");

        AggregationComponent variety7=ClassifierComponents.newBaseComponent
                ("02.1.1.1.01.07",Arrays.asList(
                        Unit.of("",
                                singletonList(
                                        RelatedPrices.of(25.00,25.00)))),
                        "2","3");

        //dpa3
        AggregationComponent variety8=ClassifierComponents.newBaseComponent
                ("02.1.1.1.01.08",Arrays.asList(
                        Unit.of("",
                                singletonList(
                                        RelatedPrices.of(23.00,25.00)))),
                        "3","4");

        AggregationComponent variety9=ClassifierComponents.newBaseComponent
                ("02.1.1.1.01.09",Arrays.asList(
                        Unit.of("",
                                singletonList(
                                        RelatedPrices.of(20.00,25.00)))),
                        "3","5");

        AggregationComponent article=ClassifierComponents.newArticleComponent(
                "02.1.1.1.01",
                Set4.of(
                        AggregationWeight.with(BigDecimal.valueOf(0.040), TOTAL, CUP),
                        AggregationWeight.with(BigDecimal.valueOf(0.000), TOTAL, CUC),
                        AggregationWeight.with(BigDecimal.valueOf(0.414), RUBRO, CUP),
                        AggregationWeight.with(BigDecimal.valueOf(0.000), RUBRO, CUC)),
                new HashSet<>(Arrays.asList(variety9,variety1,variety8,variety2,variety7,variety4,variety3,variety6,variety5)),
                BigDecimal.valueOf(2)
        );

        BigDecimal indexValue = article.indexValue(defaultWeightSpec);
        System.out.println("Index final value article = " + indexValue);
        //assertThat(indexValue).isGreaterThanOrEqualTo(BigDecimal.ONE);
    }


}
