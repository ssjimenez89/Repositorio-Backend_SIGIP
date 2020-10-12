package cu.uci.cegel.onei.classifiers.domain;

import cu.uci.cegel.onei.classifiers.infrastructure.data.FixedCollection;
import cu.uci.cegel.onei.classifiers.infrastructure.function.ArithmeticFunctions;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static cu.uci.cegel.onei.classifiers.domain.IndexFunctions.WEIGHT_INDEX_PRODUCT;


public final class ArticleComponent implements AggregationComponent{
    private String code;
    private Set<AggregationComponent> components;
    private FixedCollection<AggregationWeight> weights;
    private BigDecimal indexPrevious;
    private final Function<List<BigDecimal>, BigDecimal> geometricMedia;
    private final Function<List<BigDecimal>, BigDecimal> arithmetic_media;

    public ArticleComponent(String code, FixedCollection<AggregationWeight> weights, Set<AggregationComponent> components,BigDecimal indexPrevious) {
        this.code = code;
        this.weights = weights;
        this.components = components;
        this.indexPrevious=indexPrevious;
        this.geometricMedia=ArithmeticFunctions.GEOMETRIC_MEDIA;
        this.arithmetic_media=ArithmeticFunctions.ARITHMETIC_MEDIA;
    }

    public BigDecimal indexValue(WeightSpec weightSpec) {
        Function<AggregationComponent, BigDecimal> toWeightIndexProduct = WEIGHT_INDEX_PRODUCT.apply(weightSpec);
        Map<String, List<AggregationComponent>> groupByDpa = components.stream().collect(Collectors.groupingBy(AggregationComponent::code_Dpa));

        List<BigDecimal> microIndexsByDPA=new ArrayList<>();

        groupByDpa.forEach((codeDpa, aggregationComponents) -> {
            System.out.print(codeDpa+":"+aggregationComponents.size()+"--id:cant{");
            Map<String, List<AggregationComponent>> groupByVariety = aggregationComponents.stream().collect(Collectors.groupingBy(AggregationComponent::code_Variety));

            List<BigDecimal> indexsVarietyInsideDpa=new ArrayList<>();
            groupByVariety.forEach((codeVariety, varieties) -> {
                System.out.print(codeVariety+":"+varieties.size()+", ");

                BigDecimal indexVarietyInsideDpa=geometricMedia.apply(varieties.parallelStream().map(toWeightIndexProduct).collect(Collectors.toList()));
                indexsVarietyInsideDpa.add(indexVarietyInsideDpa);
            });
            BigDecimal indexArticleMunicipal=geometricMedia.apply(indexsVarietyInsideDpa);
            microIndexsByDPA.add(indexArticleMunicipal);

            System.out.println(" indexDPA: "+indexArticleMunicipal+"}");
        });

        BigDecimal microIndexArticleNational=arithmetic_media.apply(microIndexsByDPA);
        System.out.println("microIndexsByDPA :"+microIndexsByDPA);
        System.out.println("microIndexArticleNational :"+microIndexArticleNational);



        /*BigDecimal microIndex=geometricMedia.apply(
                                components.parallelStream()
                                .map(toWeightIndexProduct)
                                .collect(Collectors.toList()));*/

        return microIndexArticleNational.multiply(indexPrevious);
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
