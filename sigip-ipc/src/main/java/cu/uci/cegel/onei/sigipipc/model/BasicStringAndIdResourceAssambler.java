package cu.uci.cegel.onei.sigipipc.model;

import org.springframework.stereotype.Component;

@Component
public class BasicStringAndIdResourceAssambler {
    public BasicStringAndIdResource toResource(String desc, Long id, Long tipoUM) {
        return BasicStringAndIdResource.builder().desc(desc).id(id).tipoUM(tipoUM).build();
    }

    public BasicStringAndIdResource toResource(String desc, Long id) {
        return BasicStringAndIdResource.builder().desc(desc).id(id).build();
    }
}
