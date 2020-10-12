package cu.uci.cegel.onei.sigipipc.resolvers.categoria;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import cu.uci.cegel.onei.sigipipc.kernel.annotations.Resolver;
import cu.uci.cegel.onei.sigipipc.persistence.Category;
import cu.uci.cegel.onei.sigipipc.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Resolver(name = "CategoriaResolver")
public class CategoriaResolver implements GraphQLQueryResolver, GraphQLMutationResolver {

    @Autowired
    private CategoryService categoryService;

    //Queries
    public List<Category> categories() {
        return categoryService.findAll();
    }

    public List<Category> categoriesPaging(int page, int size) {
        return categoryService.findAllPaging(page, size);
    }

    public int totalCategories() {
        return categoryService.totalCategories();
    }

    public Category categoryById(Long id) {
        return categoryService.categoryById(id);
    }

    public List<Category> categoryByDescriptionContains(String description, int page, int size) {
        return categoryService.findByDescriptionContains(description, page, size);
    }

    public int totalCategoryByDescriptionContains(String desciption) {
        return categoryService.totalByDescriptionContains(desciption);
    }

    //Mutations
    public Category addCategory(String description, Boolean active) {
        return categoryService.add(description, active);
    }

    public Category editCategory(Long id, String description, Boolean active) {
        return categoryService.edit(id, description, active);
    }

}
