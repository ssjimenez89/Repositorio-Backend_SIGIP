package cu.uci.cegel.onei.sigipipc.services;

import cu.uci.cegel.onei.sigipipc.model.Nomenclator;

import java.util.List;

public interface NomenclatorService {

    List<Nomenclator> findAllPaging(int page, int size);

    int totalNomenclators();

    List<Nomenclator> findByDescriptionContains(String description, int page, int size);

    int totalByDescriptionContains(String desciption);

    Nomenclator nomenclatorById(Long id);
}
