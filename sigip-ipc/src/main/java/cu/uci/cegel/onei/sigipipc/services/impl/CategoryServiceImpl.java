package cu.uci.cegel.onei.sigipipc.services.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import cu.uci.cegel.onei.sigipipc.persistence.Category;
import cu.uci.cegel.onei.sigipipc.persistence.QCategory;
import cu.uci.cegel.onei.sigipipc.repository.CategoryRepository;
import cu.uci.cegel.onei.sigipipc.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public List<Category> findAll() {
        return (List<Category>) categoryRepository.findAll(Sort.by("description"));
    }

    @Override
    public List<Category> findAllPaging(int paging, int size) {
        if (size != -1) {
            Pageable pageable = PageRequest.of(paging, size, Sort.by("description"));
            Page<Category> page = categoryRepository.findAll(pageable);
            List<Category> categories = page.getContent();
            return categories;
        } else {
            return findAll();
        }
    }

    @Override
    public Category add(String description, Boolean active) {
        return categoryRepository.save(new Category(description, active));
    }

    @Override
    public Category edit(Long id, String description, Boolean active) {
        Category category = categoryRepository.findById(id).get();
        category.setDescription(description);
        category.setActive(active);
        return categoryRepository.save(category);
    }

    @Override
    public int totalCategories() {
        List<Category> categories = (List<Category>) categoryRepository.findAll();
        return categories.size();
    }

    @Override
    public Category categoryById(Long id) {
        Category category = categoryRepository.findById(id).get();
        return category;
    }

    @Override
    public List<Category> findByDescriptionContains(String description, int paging, int size) {
        QCategory qCategory = QCategory.category;
        BooleanExpression predicate = qCategory.description.toLowerCase().contains(description.toLowerCase());

        if (size != -1) {
            Pageable pageable = PageRequest.of(paging, size, Sort.by("description"));
            Page<Category> page = categoryRepository.findAll(predicate, pageable);
            List<Category> categories = page.getContent();
            return categories;
        } else {
            List<Category> categories = (List<Category>) categoryRepository.findAll(predicate, Sort.by("description"));
            return categories;
        }
    }

    @Override
    public int totalByDescriptionContains(String description) {
        QCategory qCategory = QCategory.category;
        BooleanExpression predicate = qCategory.description.toLowerCase().contains(description.toLowerCase());
        List<Category> categories = (List<Category>) categoryRepository.findAll(predicate);
        return categories.size();
    }


}
