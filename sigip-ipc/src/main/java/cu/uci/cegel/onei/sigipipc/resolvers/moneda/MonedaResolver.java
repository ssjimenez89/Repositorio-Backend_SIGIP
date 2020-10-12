package cu.uci.cegel.onei.sigipipc.resolvers.moneda;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import cu.uci.cegel.onei.sigipipc.kernel.annotations.Resolver;
import cu.uci.cegel.onei.sigipipc.persistence.Currency;
import cu.uci.cegel.onei.sigipipc.services.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Resolver(name = "MonedaResolver")
public class MonedaResolver implements GraphQLQueryResolver, GraphQLMutationResolver {

    @Autowired
    private CurrencyService currencyService;

    //Queries
    public List<Currency> currencies() {
        return currencyService.findAll();
    }

    public Currency currencyByDescrip(String description) {
        return currencyService.findByDescription(description);
    }

    public Currency currencyByAcronym(String acronym) {
        return currencyService.findByAcronym(acronym);
    }

    public List<Currency> currencyActives() {
        return currencyService.currencyActives();
    }

    public List<Currency> currencyPaging(int page, int size) {
        return currencyService.currencyPaging(page, size);
    }

    public int totalCurrency() {
        return currencyService.totalCurrency();
    }

    public Currency currencyById(Long id) {
        return currencyService.currencyById(id);
    }

    public List<Currency> currencyByDescriptionContains(String description, int page, int size) {
        return currencyService.findByDescriptionContains(description, page, size);
    }

    public int totalCurrencyByDescriptionContains(String desciption) {
        return currencyService.totalByDescriptionContains(desciption);
    }

    //Mutations
    public Currency addCurrency(Currency currency) {
        return currencyService.save(currency);
    }

    public Currency editCurrency(Currency currency) {
        return currencyService.save(currency);
    }
}
