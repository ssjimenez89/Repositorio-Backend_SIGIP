package cu.uci.cegel.onei.sigipipc.services.impl;

import cu.uci.cegel.onei.sigipipc.model.Nomenclator;
import cu.uci.cegel.onei.sigipipc.persistence.*;
import cu.uci.cegel.onei.sigipipc.repository.*;
import cu.uci.cegel.onei.sigipipc.services.NomenclatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NomenclatorServiceImpl implements NomenclatorService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private MarketRepository marketRepository;
    @Autowired
    private IncidenceRepository incidenceRepository;
    @Autowired
    private ObservationRepository observationRepository;
    @Autowired
    private TypologyRepository typologyRepository;
    @Autowired
    private DPARepository dpaRepository;
    @Autowired
    private CurrencyRepository currencyRepository;
    @Autowired
    private WeighingTypeRepository weighingTypeRepository;
    @Autowired
    private VarietyTypeRepository varietyTypeRepository;
    @Autowired
    private ClassifierTypeRepository classifierTypeRepository;
    @Autowired
    private MeasurementUnitTypeRepository measurementUnitTypeRepository;
    @Autowired
    private CargoRepository cargoRepository;
    @Autowired
    private RegionRepository regionRepository;


    @Autowired
    private EntityManager entityManager;

    private String sqlFindAll="Select id_ntipo_categoria,descripcion,'categoria' as table_name,activo from ntipo_categoria " + "Union" + " select id_nobservacion,descripcion,'observacion',activo from nobservacion " + "Union" + " select id_nincidencia,descripcion,'incidencia',activo from nincidencia " + "Union" + " select id_ntipologia,descripcion,'tipologia',activo from ntipologia " + "Union" + " select id_nmercado,descripcion,'mercado',activo from nmercado " + "Union" + " select id_nd_p_a,descripcion,'dpa',activo from nd_p_a " + "Union" + " select id_nmoneda,descripcion,'moneda',activo from nmoneda " + "Union" + " select id_ntipo_ponderacion,descripcion,'tipo_ponderacion',activo from ntipo_ponderacion " + "Union" + " select id_ntipo_variedad,descripcion,'tipo_variedad',activo from ntipo_variedad " + "Union" + " select id_ntipo_clasific,descripcion,'tipo_clasificador',activo from ntipo_clasific " + "Union" + " select id_ntipo_unidad_medida,descripcion,'tipo_unidad_medida',activo from ntipo_unidad_medida " + "Union" + " select id_ncargo,descripcion,'cargo',activo from ncargo " + "Union" + " select id_nregion,descripcion,'region',activo from nregion order by descripcion ";

    private String sqlFindAllFilter="Select id_ntipo_categoria,descripcion,'categoria' as table_name,activo from ntipo_categoria Where descripcion ILIKE ?" +"Union" +
            " select id_nobservacion,descripcion,'observacion',activo from nobservacion Where descripcion ILIKE ?" +"Union" +
            " select id_nincidencia,descripcion,'incidencia',activo from nincidencia Where descripcion ILIKE ?" + "Union" +
            " select id_ntipologia,descripcion,'tipologia',activo from ntipologia Where descripcion ILIKE ?" + "Union" +
            " select id_nmercado,descripcion,'mercado',activo from nmercado Where descripcion ILIKE ?" + "Union" +
            " select id_nd_p_a,descripcion,'dpa',activo from nd_p_a Where descripcion ILIKE ?" + "Union" +
            " select id_nmoneda,descripcion,'moneda',activo from nmoneda Where descripcion ILIKE ?" + "Union" +
            " select id_ntipo_ponderacion,descripcion,'tipo_ponderacion',activo from ntipo_ponderacion Where descripcion ILIKE ?" + "Union" +
            " select id_ntipo_variedad,descripcion,'tipo_variedad',activo from ntipo_variedad Where descripcion ILIKE ?" + "Union" +
            " select id_ntipo_clasific,descripcion,'tipo_clasificador',activo from ntipo_clasific Where descripcion ILIKE ?" + "Union" +
            " select id_ntipo_unidad_medida,descripcion,'tipo_unidad_medida',activo from ntipo_unidad_medida Where descripcion ILIKE ?" + "Union" +
            " select id_ncargo,descripcion,'cargo',activo from ncargo Where descripcion ILIKE ?" + "Union" +
            " select id_nregion,descripcion,'region',activo from nregion Where descripcion ILIKE ? order by descripcion";



    @Override
    public List<Nomenclator> findAllPaging(int page, int size) {
        if(size!=-1){
            int limit = size;
            int offset = page*size;
            String sql=this.sqlFindAll+" limit ? offset ?";
            Query query = entityManager.createNativeQuery(sql);
            query.setParameter(1, limit);
            query.setParameter(2, offset);
            List<Object[]> objects = query.getResultList();
            List<Nomenclator> nomenclators = objects.stream().map(objects1 -> new Nomenclator((Integer) objects1[0],(String) objects1[1],(String) objects1[2],(Boolean) objects1[3])).collect(Collectors.toList());
            return nomenclators;
        }else{
            return findAll();
        }
    }

    private List<Nomenclator> findAll(){
        String sql=this.sqlFindAll;
        Query query = entityManager.createNativeQuery(sql);
        List<Object[]> objects = query.getResultList();
        List<Nomenclator> nomenclators = objects.stream().map(objects1 -> new Nomenclator((Integer) objects1[0],(String) objects1[1],(String) objects1[2],(Boolean) objects1[3])).collect(Collectors.toList());
        return nomenclators;
    }


    @Override
    public int totalNomenclators() {
       // return ((List<Category>)categoryRepository.findAll()).size()+((List<Market>) marketRepository.findAll()).size()+((List<Incidence>) incidenceRepository.findAll()).size()+((List<Observation>) observationRepository.findAll()).size()+((List<Typology>) typologyRepository.findAll()).size()+((List<DPA>)dpaRepository.findAll()).size()+((List<Currency>)currencyRepository.findAll()).size()+((List<WeighingType>)weighingTypeRepository.findAll()).size()+((List<VarietyType>)varietyTypeRepository.findAll()).size()+((List<ClassifierType>)classifierTypeRepository.findAll()).size()+((List<MeasurementUnitType>)measurementUnitTypeRepository.findAll()).size()+((List<Cargo>)cargoRepository.findAll()).size()+((List<Region>)regionRepository.findAll()).size();
        return (int) ( categoryRepository.count() + marketRepository.count() + incidenceRepository.count() + observationRepository.count() + typologyRepository.count() + dpaRepository.count() + currencyRepository.count() + weighingTypeRepository.count() + varietyTypeRepository.count() + classifierTypeRepository.count() + measurementUnitTypeRepository.count() + cargoRepository.count() + regionRepository.count() );
    }

    @Override
    public List<Nomenclator> findByDescriptionContains(String description, int page, int size) {
        if(size!=-1){
            int limit = size;
            int offset = page*size;
            String sql = this.sqlFindAllFilter+" limit ? offset ?";

            Query query = entityManager.createNativeQuery(sql);
            String subString="%"+description.toLowerCase()+"%";
            query.setParameter(1, subString);
            query.setParameter(2, subString);
            query.setParameter(3, subString);
            query.setParameter(4, subString);
            query.setParameter(5, subString);
            query.setParameter(6, subString);
            query.setParameter(7, subString);
            query.setParameter(8, subString);
            query.setParameter(9, subString);
            query.setParameter(10, subString);
            query.setParameter(11, subString);
            query.setParameter(12, subString);
            query.setParameter(13, subString);
            query.setParameter(14, limit);
            query.setParameter(15, offset);
            List<Object[]> objects = query.getResultList();
            List<Nomenclator> nomenclators = objects.stream().map(objects1 -> new Nomenclator((Integer) objects1[0],(String) objects1[1],(String) objects1[2],(Boolean) objects1[3])).collect(Collectors.toList());
            return nomenclators;
        }else{
            String sql = this.sqlFindAllFilter;

            Query query = entityManager.createNativeQuery(sql);
            String subString="%"+description.toLowerCase()+"%";
            query.setParameter(1, subString);
            query.setParameter(2, subString);
            query.setParameter(3, subString);
            query.setParameter(4, subString);
            query.setParameter(5, subString);
            query.setParameter(6, subString);
            query.setParameter(7, subString);
            query.setParameter(8, subString);
            query.setParameter(9, subString);
            query.setParameter(10, subString);
            query.setParameter(11, subString);
            query.setParameter(12, subString);
            query.setParameter(13, subString);
            List<Object[]> objects = query.getResultList();
            List<Nomenclator> nomenclators = objects.stream().map(objects1 -> new Nomenclator((Integer) objects1[0],(String) objects1[1],(String) objects1[2],(Boolean) objects1[3])).collect(Collectors.toList());
            return nomenclators;
        }
    }

    @Override
    public int totalByDescriptionContains(String description) {
        String sql = this.sqlFindAllFilter;

        Query query = entityManager.createNativeQuery(sql);
        String subString="%"+description.toLowerCase()+"%";
        query.setParameter(1, subString);
        query.setParameter(2, subString);
        query.setParameter(3, subString);
        query.setParameter(4, subString);
        query.setParameter(5, subString);
        query.setParameter(6, subString);
        query.setParameter(7, subString);
        query.setParameter(8, subString);
        query.setParameter(9, subString);
        query.setParameter(10, subString);
        query.setParameter(11, subString);
        query.setParameter(12, subString);
        query.setParameter(13, subString);
        List<Object[]> objects = query.getResultList();
        int total=objects.size();
        return total;
    }

    @Override
    public Nomenclator nomenclatorById(Long id) {
        return null;
    }
}
