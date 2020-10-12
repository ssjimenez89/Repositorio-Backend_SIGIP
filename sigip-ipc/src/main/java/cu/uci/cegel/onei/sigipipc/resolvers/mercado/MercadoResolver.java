package cu.uci.cegel.onei.sigipipc.resolvers.mercado;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import cu.uci.cegel.onei.sigipipc.kernel.annotations.Resolver;
import cu.uci.cegel.onei.sigipipc.persistence.Market;
import cu.uci.cegel.onei.sigipipc.services.MarketService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Resolver(name = "MercadoResolver")
public class MercadoResolver implements GraphQLQueryResolver, GraphQLMutationResolver {

    @Autowired
    private MarketService marketService;

    //Queries
    public List<Market> markets() {
        return marketService.findAll();
    }

    public Market marketById(Long id) {
        return marketService.marketById(id);
    }

    public List<Market> marketsPaging(int page, int size) {
        return marketService.findAllPaging(page, size);
    }

    public int totalMarket() {
        return marketService.totalMarket();
    }

    public List<Market> marketByDescriptionContains(String description, int page, int size) {
        return marketService.findByDescriptionContains(description, page, size);
    }

    public int totalMarketByDescriptionContains(String desciption) {
        return marketService.totalByDescriptionContains(desciption);
    }

    public List<Market> marketsByDescriptionContains(String description) {
        return marketService.marketByDescriptionContains(description);
    }

    //Mutations
    public Market addMarket(String description, Boolean active) {
        return marketService.add(description, active);
    }

    public Market editMarket(Long id, String description, Boolean active) {
        return marketService.edit(id, description, active);
    }
}
