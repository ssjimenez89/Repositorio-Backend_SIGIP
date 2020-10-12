package cu.uci.cegel.onei.sigipipc.services.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import cu.uci.cegel.onei.sigipipc.persistence.Category;
import cu.uci.cegel.onei.sigipipc.persistence.QTypology;
import cu.uci.cegel.onei.sigipipc.persistence.Typology;
import cu.uci.cegel.onei.sigipipc.repository.CategoryRepository;
import cu.uci.cegel.onei.sigipipc.repository.TypologyRepository;
import cu.uci.cegel.onei.sigipipc.services.TypologyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypologyServiceImpl implements TypologyService {
    @Autowired
    private TypologyRepository typologyRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Typology> findAll() {
        return (List<Typology>) typologyRepository.findAll(Sort.by("description"));
    }

    @Override
    public Typology add(String description, Long categoryID, Boolean active, Boolean imputed) {
        Category category = categoryRepository.findById(categoryID).get();
        Typology typology = new Typology(description, category, active, imputed);
        return typologyRepository.save(typology);
    }

    @Override
    public Typology edit(Long id, String description, Long categoryID, Boolean active, Boolean imputed) {
        Category category = categoryRepository.findById(categoryID).get();
        Typology typology = typologyRepository.findById(id).get();
        typology.setDescription(description);
        typology.setCategory(category);
        typology.setActive(active);
        typology.setImputed(imputed);
        return typologyRepository.save(typology);
    }

    @Override
    public List<Typology> findAllPaging(int paging, int size) {
        if (size != -1) {
            Pageable pageable = PageRequest.of(paging, size, Sort.by("description"));
            Page<Typology> page = typologyRepository.findAll(pageable);
            List<Typology> typologies = page.getContent();
            return typologies;
        } else {
            return findAll();
        }
    }

    @Override
    public int totalTypology() {
        List<Typology> typologies = (List<Typology>) typologyRepository.findAll();
        return typologies.size();
    }

    @Override
    public Typology typologyById(Long id) {
        Typology typology = typologyRepository.findById(id).get();
        return typology;
    }

    @Override
    public List<Typology> findByDescriptionContains(String description, int paging, int size) {
        if (size != -1) {
            Pageable pageable = PageRequest.of(paging, size, Sort.by("description"));
            Page<Typology> page = typologyRepository.findAll(getPredicate(description), pageable);
            List<Typology> typologies = page.getContent();
            return typologies;
        } else {
            List<Typology> typologies = (List<Typology>) typologyRepository.findAll(getPredicate(description), Sort.by("description"));
            return typologies;
        }
    }

    @Override
    public int totalByDescriptionContains(String description) {
        List<Typology> typologies = (List<Typology>) typologyRepository.findAll(getPredicate(description), Sort.by("description"));
        return typologies.size();
    }

    public BooleanExpression getPredicate(String description) {
        return QTypology.typology.description.toLowerCase().contains(description.toLowerCase());
    }
}
