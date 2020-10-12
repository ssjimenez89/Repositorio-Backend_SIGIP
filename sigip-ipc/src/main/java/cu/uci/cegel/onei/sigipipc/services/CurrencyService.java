package cu.uci.cegel.onei.sigipipc.services;

import cu.uci.cegel.onei.sigipipc.persistence.Currency;

import java.util.List;

public interface CurrencyService {

    List<Currency> findAll();

    Currency findByDescription(String description);

    Currency findByAcronym(String acronym);

    List<Currency> currencyActives();

    List<Currency> currencyPaging(int page, int size);

    int totalCurrency();

    Currency currencyById(Long id);

    List<Currency> findByDescriptionContains(String description, int page, int size);

    int totalByDescriptionContains(String description);

    Currency save(Currency currency);
}
