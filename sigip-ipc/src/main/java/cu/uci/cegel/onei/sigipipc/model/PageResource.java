package cu.uci.cegel.onei.sigipipc.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class PageResource {
    Integer page;
    Integer pageSize;
    String orderBy;
    String order;
    String establishment;
    Long dpaId;
    Long varietyId;
    Long typologyId;
    Long marketId;
    String year;
    String mes;
    String variedadName;
    Integer semanaCaptacion;
}
