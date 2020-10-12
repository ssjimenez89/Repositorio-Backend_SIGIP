package cu.uci.cegel.onei.sigipbase.web.cargo;

import cu.uci.cegel.onei.sigipbase.domain.cargo.Cargo;
import cu.uci.cegel.onei.sigipbase.domain.cargo.CargoService;
import cu.uci.cegel.onei.sigipbase.infrastructure.util.UtilValidacion;
import cu.uci.cegel.onei.sigipbase.kernel.CONSTANTS;
import cu.uci.cegel.onei.sigipbase.web.cargo.dto.CargoResource;
import cu.uci.cegel.onei.sigipbase.web.cargo.dto.CargoResourceAssembler;
import cu.uci.cegel.security.utils.HeaderUtil;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = CONSTANTS.ENTITY_API, produces = "application/json")
public class CargoController {

    private final @NonNull
    CargoService cargoService;
    private final @NonNull
    CargoResourceAssembler cargoResourceAssembler;

    private static final String ENTITY_URI = "/cargo";
    private static final String ENTITY_NAME = "cargo";


    @GetMapping(path = ENTITY_URI + "/all")
    public ResponseEntity<List<CargoResource>> obtenerTodosCargos() {
        try {
            List<Cargo> dcargo = cargoService.listarTodosCargos();
            return ResponseEntity.ok().body(cargoResourceAssembler.toResources(dcargo));
        } catch (Exception ex) {
            return ResponseEntity.badRequest()
                    .headers(HeaderUtil.badRequestAlert("Error obteniendo los " + ENTITY_NAME + "s en el servidor.")).build();
        }
    }

    @GetMapping(path = ENTITY_URI + "/{id}")
    public ResponseEntity<CargoResource> obtenerCargo(@PathVariable Long id) {
        try {
            if (!UtilValidacion.isAValidIdentification(id)) {
                return ResponseEntity.badRequest()
                        .headers(HeaderUtil.badRequestAlert("El " + ENTITY_NAME + " no puede tener un id con valor " + id + ".")).build();
            }
            return cargoService.obtenerCargoNew(id).map(cargo -> ResponseEntity.ok()
                    .body(cargoResourceAssembler.toResource(cargo)))
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .headers(HeaderUtil.errorAlert("El " + ENTITY_NAME + " no se encuentra en el servidor.")).build());
        } catch (Exception ex) {
            return ResponseEntity.badRequest()
                    .headers(HeaderUtil.badRequestAlert("El " + ENTITY_NAME + " no se puede obtener.")).build();
        }
    }
}
