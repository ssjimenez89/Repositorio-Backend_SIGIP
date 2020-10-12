package cu.uci.cegel.onei.sigipipc.services;

import cu.uci.cegel.onei.sigipipc.persistence.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findAll();

    List<Category> findAllPaging(int page, int size);

    Category add(String description, Boolean active);

    Category edit(Long id, String description, Boolean active);

    int totalCategories();

    Category categoryById(Long id);

    List<Category> findByDescriptionContains(String description, int page, int size);

    int totalByDescriptionContains(String description);
}
