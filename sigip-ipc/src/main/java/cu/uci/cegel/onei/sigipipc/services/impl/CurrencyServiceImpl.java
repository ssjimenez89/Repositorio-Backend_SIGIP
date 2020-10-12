package cu.uci.cegel.onei.sigipipc.services.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import cu.uci.cegel.onei.sigipipc.persistence.Currency;
import cu.uci.cegel.onei.sigipipc.persistence.QCurrency;
import cu.uci.cegel.onei.sigipipc.repository.CurrencyRepository;
import cu.uci.cegel.onei.sigipipc.services.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurrencyServiceImpl implements CurrencyService {

    @Autowired
    CurrencyRepository currencyRepository;

    @Override
    public List<Currency> findAll() {
        return (List<Currency>) currencyRepository.findAll();
    }

    @Override
    public Currency findByDescription(String description) {
        return currencyRepository.findByDescriptionEquals(description);
    }

    @Override
    public Currency findByAcronym(String acronym) {
        return currencyRepository.findByAcronymEquals(acronym);
    }

    @Override
    public List<Currency> currencyActives() {
        return currencyRepository.findByActiveIsTrue();
    }


    @Override
    public List<Currency> currencyPaging(int paging, int size) {
        if (size != -1) {
            Pageable pageable = PageRequest.of(paging, size);
            Page<Currency> page = currencyRepository.findAll(pageable);
            List<Currency> currencies = page.getContent();
            return currencies;
        } else {
            return findAll();
        }
    }

    @Override
    public int totalCurrency() {
        List<Currency> currencies = (List<Currency>) currencyRepository.findAll();
        return currencies.size();
    }

    @Override
    public Currency currencyById(Long id) {
        Currency currency = currencyRepository.findById(id).get();
        return currency;
    }

    @Override
    public List<Currency> findByDescriptionContains(String description, int paging, int size) {
        if (size != -1) {
            Pageable pageable = PageRequest.of(paging, size, Sort.by("description"));
            Page<Currency> page = currencyRepository.findAll(getPredicate(description), pageable);
            List<Currency> currencies = page.getContent();
            return currencies;
        } else {
            List<Currency> currencies = (List<Currency>) currencyRepository.findAll(getPredicate(description), Sort.by("description"));
            return currencies;
        }
    }

    @Override
    public int totalByDescriptionContains(String description) {
        List<Currency> currencies = (List<Currency>) currencyRepository.findAll(getPredicate(description), Sort.by("description"));
        return currencies.size();
    }

    public BooleanExpression getPredicate(String description) {
        return QCurrency.currency.description.toLowerCase().contains(description.toLowerCase());
    }


    @Override
    public Currency save(Currency curr) {
        return currencyRepository.save(curr);
    }

    public Currency edit(Currency curr) {
        return currencyRepository.save(curr);
    }

}
