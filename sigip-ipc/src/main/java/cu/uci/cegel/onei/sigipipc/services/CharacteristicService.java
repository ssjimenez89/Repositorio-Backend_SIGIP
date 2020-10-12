package cu.uci.cegel.onei.sigipipc.services;

import cu.uci.cegel.onei.sigipipc.model.CharacteristicInput;
import cu.uci.cegel.onei.sigipipc.model.CharacteristicSpecificationResource;
import cu.uci.cegel.onei.sigipipc.persistence.Characteristic;

import java.util.List;

public interface CharacteristicService {

    List<Characteristic> findAll();

    Characteristic add(String description);

    Characteristic characteristicByDescription(String description);

    List<Characteristic> characteristicByDescriptionContaining(String description);

    Characteristic add(CharacteristicInput characteristicInput);

    Characteristic edit(Long id, CharacteristicInput characteristicInput);

    CharacteristicSpecificationResource obtain(Long id);

    Boolean delete(Long id);

    Boolean changeState(Long id);

    int size(String description);

    List<CharacteristicSpecificationResource> list(String description, int page, int size);


}
