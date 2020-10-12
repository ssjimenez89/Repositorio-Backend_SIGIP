package cu.uci.cegel.onei.sigipipc.services.impl;

import cu.uci.cegel.onei.sigipipc.model.CharacteristicInput;
import cu.uci.cegel.onei.sigipipc.model.CharacteristicSpecificationResource;
import cu.uci.cegel.onei.sigipipc.persistence.Characteristic;
import cu.uci.cegel.onei.sigipipc.persistence.Specification;
import cu.uci.cegel.onei.sigipipc.repository.CharacteristicRepository;
import cu.uci.cegel.onei.sigipipc.repository.SpecificationRepository;
import cu.uci.cegel.onei.sigipipc.repository.VarietyCharactSpecificRepository;
import cu.uci.cegel.onei.sigipipc.services.CharacteristicService;
import cu.uci.cegel.onei.sigipipc.services.SpecificationService;
import cu.uci.cegel.onei.sigipipc.util.CharacteristicSpecificationResourceAssembler;
import cu.uci.cegel.onei.sigipipc.util.CharacteristicSpecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CharacteristicServiceImpl implements CharacteristicService {

    @Autowired
    CharacteristicRepository characteristicRepository;
    @Autowired
    SpecificationService specificationService;
    @Autowired
    VarietyCharactSpecificRepository varietyCharactSpecificRepository;
    @Autowired
    SpecificationRepository specificationRepository;
    @Autowired
    CharacteristicSpecificationResourceAssembler characteristicSpecificationResourceAssembler;


    @Override
    public List<Characteristic> findAll() {
        return (List<Characteristic>) characteristicRepository.findAll();
    }

    @Override
    public Characteristic add(String description) {
        return characteristicRepository.save(Characteristic.builder()
                .description(description)
                .active(true)
                .build());
    }

    @Override
    public Characteristic characteristicByDescription(String description) {
        return characteristicRepository.findByDescriptionEquals(description);
    }

    @Override
    public List<Characteristic> characteristicByDescriptionContaining(String description) {
        return characteristicRepository.findByDescriptionContaining(description);
    }

    @Override
    public List<CharacteristicSpecificationResource> list(String description, int page, int size) {
        if (size > 0) {
            Pageable pageable = PageRequest.of(page, size, Sort.by("description"));
            Page<Characteristic> characteristicPage = characteristicRepository
                    .findAll(CharacteristicSpecs.searchCharacteristic(description), pageable);

            List<CharacteristicSpecificationResource> specificationResourceList = characteristicSpecificationResourceAssembler.toResources(characteristicPage.getContent());
            return specificationResourceList;

        }
        return characteristicSpecificationResourceAssembler.toResources(characteristicRepository
                .findAll(CharacteristicSpecs.searchCharacteristic(description)));
    }

    @Transactional
    @Override
    public Characteristic add(CharacteristicInput characteristicInput) {
        Characteristic characteristic = characteristicRepository.save(Characteristic.builder()
                .description(characteristicInput.getDescription())
                .active(characteristicInput.isActive()).build());

        if (!characteristicInput.getEspecifications().isEmpty()) {
            characteristicInput.getEspecifications().forEach(specification -> {
                //Specification add = specificationService.add(specification.getDescription());
                //if (add != null) {
                specificationRepository.save(Specification.builder()
                        .description(specification.getDescription())
                        .active(specification.getActive())
                        .characteristic(characteristic).build());
                // }

            });
        }
        return characteristic;
    }

    @Override
    public Characteristic edit(Long id, CharacteristicInput characteristicInput) {
        Optional<Characteristic> optionalCharacteristic = characteristicRepository.findById(id);

        if (optionalCharacteristic.isPresent()) {
            Characteristic characteristic = characteristicRepository.save(Characteristic.builder()
                    .description(characteristicInput.getDescription())
                    .active(characteristicInput.isActive())
                    .id(id).build());

            if (!characteristicInput.getEspecifications().isEmpty()) {
                characteristicInput.getEspecifications().forEach(specification -> {
                    if (!Objects.isNull(specification.getId())) {
                        specificationService.edit(specification.getId(), specification.getDescription(), specification.getActive(), characteristic);
                    } else {
                        specificationRepository.save(Specification.builder()
                                .description(specification.getDescription())
                                .active(specification.getActive())
                                .characteristic(characteristic).build());

                    }
                });
            }
            List<Specification> specifications = specificationRepository.findByCharacteristicEquals(characteristic);
            characteristic.setSpecifications(specifications);
               /* byCharacteristic
                        .stream()
                        .forEach(specification -> {
                            List<Long> collect = characteristicInput.getEspecifications()
                                    .stream()
                                    .map(SpecificationInput::getId)
                                    .collect(Collectors.toList());

                            if (!collect.contains(specification.getId())) {
                                specificationService.delete(specification.getId());
                            }
                        });*/
            return characteristic;
        }
        return null;
    }

    @Override
    public CharacteristicSpecificationResource obtain(Long id) {
        Optional<Characteristic> characteristicOptional = characteristicRepository.findById(id);
        if (characteristicOptional.isPresent()) {
            return characteristicSpecificationResourceAssembler.toResourceConEspecificaciones(characteristicOptional.get());
        }
        return null;
    }

    @Transactional
    @Override
    public Boolean delete(Long id) {
        Optional<Characteristic> characteristicOptional = characteristicRepository.findById(id);
        if (characteristicOptional.isPresent()) {
            List<Specification> byCharacteristic = specificationRepository.findByCharacteristicEquals(characteristicOptional.get());
            List<Long> idSpecificationList = byCharacteristic
                    .stream()
                    .map(Specification::getId)
                    .collect(Collectors.toList());
            idSpecificationList
                    .stream()
                    .forEach(idSp -> specificationService.delete(idSp));
            characteristicRepository.delete(characteristicOptional.get());
            return true;
        }
        return false;
    }

    @Override
    public Boolean changeState(Long id) {
        Optional<Characteristic> characteristicOptional = characteristicRepository.findById(id);
        if (characteristicOptional.isPresent()) {
            Characteristic characteristic = characteristicOptional.get();
            characteristic.setActive(!characteristic.getActive());
            characteristicRepository.save(characteristic);
            return true;
        }
        return false;
    }

    @Override
    public int size(String description) {
        return characteristicRepository.findAll(CharacteristicSpecs.searchCharacteristic(description)).size();
    }


}
