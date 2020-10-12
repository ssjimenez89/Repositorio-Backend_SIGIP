package cu.uci.cegel.onei.sigipbase.domain.dpa;

import cu.uci.cegel.onei.sigipbase.web.dpa.dto.DpaDTO;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DpaFactory {

    public Dpa convertir(DpaDTO dpaDTO) {
        return Dpa.builder()
                .id(dpaDTO.getId())
                .descripcion(dpaDTO.getDescripcion())
                .activo(dpaDTO.getActivo())
                .codigodpa(dpaDTO.getCodigodpa())
                .idpadre(dpaDTO.getIdpadre())
                .build();
    }

    public Dpa convertir(Optional<Dpa> dpa, DpaDTO dpaDTO) {
        if (dpaDTO!=null) {
            Dpa dpa1 = dpa.get();
            dpa1.setId(dpaDTO.getId());
            dpa1.setActivo(dpaDTO.getActivo());
            dpa1.setDescripcion(dpaDTO.getDescripcion());
            dpa1.setCodigodpa(dpaDTO.getCodigodpa());
            dpa1.setIdpadre(dpaDTO.getIdpadre());
            return dpa1;
        }
        return null;
    }
}
