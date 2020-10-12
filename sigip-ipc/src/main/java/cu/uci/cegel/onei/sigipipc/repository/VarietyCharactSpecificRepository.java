package cu.uci.cegel.onei.sigipipc.repository;

import cu.uci.cegel.onei.sigipipc.persistence.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface VarietyCharactSpecificRepository extends PagingAndSortingRepository<VarietyCharactSpecific, Long> {

    //VarietyCharactSpecific findByCharacteristicAndSpecificationAndClassifier(Characteristic characteristic, Specification specification, Classifier classifier);

    List<VarietyCharactSpecific> findBySpecification_Characteristic(Characteristic characteristic);

    List<VarietyCharactSpecific> findBySpecification(Specification specification);

    @Query("SELECT e FROM VarietyCharactSpecific e WHERE e.id IN :ids")
    List<VarietyCharactSpecific> obtenerVarietyCaracEspecByIdIn(@Param("ids") List<Long> ids);

    List<VarietyCharactSpecific> findAllByClassifier(Classifier classifier);

    List<VarietyCharactSpecific> findAllByClassifierAndActiveTrue(Classifier classifier);

    VarietyCharactSpecific findByClassifierAndSpecificationAndSpecification_Characteristic(Classifier classifier, Specification specification, Characteristic characteristic);

    List<VarietyCharactSpecific> findAllByClassifierIdAndActiveTrue(Long classifier);



}