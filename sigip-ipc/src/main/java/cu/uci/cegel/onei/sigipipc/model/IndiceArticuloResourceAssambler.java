package cu.uci.cegel.onei.sigipipc.model;

import cu.uci.cegel.onei.sigipipc.persistence.Classifier;
import cu.uci.cegel.onei.sigipipc.persistence.IndiceArticulo;
import cu.uci.cegel.onei.sigipipc.persistence.VarietyCharactSpecific;
import cu.uci.cegel.onei.sigipipc.repository.DPARepository;
import cu.uci.cegel.onei.sigipipc.services.DPAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class IndiceArticuloResourceAssambler {
    @Autowired
    CaracteristicaEspecificacionResourceAssambler caracteristicaEspecificacionResourceAssambler;
    @Autowired
    DPARepository dpaRepository;

    public IndiceArticuloResource toResource(IndiceArticulo indiceArticulo){
        List<CaracteristicaEspecificacionResource> specs = caracteristicaEspecificacionResourceAssambler.toResource(indiceArticulo.getDVarCaracEspec());
        VarietyCharactSpecific varCarEsp = indiceArticulo.getDVarCaracEspec().get(0);
        Classifier variedad = varCarEsp.getClassifier();
        return IndiceArticuloResource.builder()
                .id(indiceArticulo.getId())
                .indice(indiceArticulo.getIndice())
                .idvariedad(indiceArticulo.getIdvariedad())
                .fecha(indiceArticulo.getFecha().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                .varCaracEsp(specs)
                .mes(indiceArticulo.getFecha().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getMonth().toString())
                .nombreVariedad(variedad.getDescription())
                .dpa(indiceArticulo.getIdmunicipio() != null ? dpaRepository.findById(indiceArticulo.getIdmunicipio()).get() : (indiceArticulo.getIdprovincia() != null ? dpaRepository.findById(indiceArticulo.getIdprovincia()).get() : null))
                .build();
    }

    public List<IndiceArticuloResource> toResource(List<IndiceArticulo> indiceArticulos) {
        return indiceArticulos.stream().map(this::toResource).collect(Collectors.toList());
    }

    public IndiceArticuloViewResourcePage toResource(List<IndiceArticuloResource> resources, long totalItems) {
        return IndiceArticuloViewResourcePage.builder()
                .resources(resources)
                .totalItems(totalItems)
                .build();
    }

    public IndiceArticuloViewResourcePage toResource(Page<IndiceArticulo> indiceArticulos, long totalItems) {
        List<IndiceArticuloResource> collect = indiceArticulos.stream()
                .map(indiceArticulo -> toResource(indiceArticulo))
                .collect(Collectors.toList());

        return IndiceArticuloViewResourcePage.builder()
                .resources(collect)
                .totalItems(totalItems)
                .build();
    }
}
