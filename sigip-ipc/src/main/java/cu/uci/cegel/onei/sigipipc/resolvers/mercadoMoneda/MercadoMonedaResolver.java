package cu.uci.cegel.onei.sigipipc.resolvers.mercadoMoneda;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import cu.uci.cegel.onei.sigipipc.kernel.annotations.Resolver;
import cu.uci.cegel.onei.sigipipc.model.MercadoMonedaResource;
import cu.uci.cegel.onei.sigipipc.services.MarketCurrencyService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Resolver(name = "MercadoMonedaResolver")
public class MercadoMonedaResolver implements GraphQLQueryResolver, GraphQLMutationResolver {

    @Autowired
    MarketCurrencyService marketCurrencyService;

    //Queries
    public List<MercadoMonedaResource> marketsCurrencies(){
        return marketCurrencyService.getAllMarketCurrency();
    }

    //Mutations

}
