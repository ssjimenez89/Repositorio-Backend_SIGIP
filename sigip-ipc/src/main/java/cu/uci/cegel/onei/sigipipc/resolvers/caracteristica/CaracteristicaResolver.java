package cu.uci.cegel.onei.sigipipc.resolvers.caracteristica;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import cu.uci.cegel.onei.sigipipc.kernel.annotations.Resolver;
import cu.uci.cegel.onei.sigipipc.model.CharacteristicInput;
import cu.uci.cegel.onei.sigipipc.model.CharacteristicSpecificationResource;
import cu.uci.cegel.onei.sigipipc.persistence.Characteristic;
import cu.uci.cegel.onei.sigipipc.services.CharacteristicService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Resolver(name = "CaracteristicaResolver")
public class CaracteristicaResolver implements GraphQLQueryResolver, GraphQLMutationResolver {

    @Autowired
    private CharacteristicService characteristicService;

    //Queries
    public List<Characteristic> characteristics() {
        return characteristicService.findAll();
    }

    public Characteristic charateristicByDescription(String description) {
        return characteristicService.characteristicByDescription(description);
    }

    public List<Characteristic> charateristicByDescriptionContaining(String description) {
        return characteristicService.characteristicByDescriptionContaining(description);
    }

    public CharacteristicSpecificationResource charateristicById(Long id) {
        return characteristicService.obtain(id);
    }

    public List<CharacteristicSpecificationResource> charateristicByDescriptionPage(String description, int page, int size) {
        return characteristicService.list(description, page, size);
    }

    public int totalcharateristicByDescriptionPage(String description) {
        return characteristicService.size(description);
    }

    //Mutations
    public Characteristic addCharacteristic(String description) {
        return characteristicService.add(description);
    }

    public Characteristic addCharacteristicSpecific(CharacteristicInput characteristicInput) {
        return characteristicService.add(characteristicInput);
    }

    public Characteristic editCharacteristicSpecific(Long id, CharacteristicInput characteristicInput) {
        return characteristicService.edit(id, characteristicInput);
    }

    public Boolean deleteCharacteristicSpecific(Long id) {
        return characteristicService.delete(id);
    }

    public Boolean changeStateCharacteristicSpecific(Long id) {
        return characteristicService.changeState(id);
    }
}
