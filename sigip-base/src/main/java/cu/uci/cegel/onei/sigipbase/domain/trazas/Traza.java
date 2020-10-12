package cu.uci.cegel.onei.sigipbase.domain.trazas;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "trazas")
public class Traza {

    @Id
    private String id;
    private String descripcion;
    private String fecha;
    private String usuario;
    private String ip;
    private String tipoDeTraza;
    private String tipoOperacion;
}
