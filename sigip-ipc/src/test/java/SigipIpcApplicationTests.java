
import com.querydsl.core.types.dsl.BooleanExpression;
import cu.uci.cegel.onei.sigipipc.SigipIpcApplication;
import cu.uci.cegel.onei.sigipipc.repository.PlanningDateRepository;
import cu.uci.cegel.onei.sigipipc.services.EstablishmentService;
import cu.uci.cegel.onei.sigipipc.services.PlanningDateService;
import cu.uci.cegel.onei.sigipipc.services.VarietyEstablishmentService;
import cu.uci.cegel.onei.sigipipc.services.impl.EstablishmentServiceImpl;
import cu.uci.cegel.onei.sigipipc.services.impl.VarietyEstablishmentServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SigipIpcApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SigipIpcApplicationTests {
    @Autowired
    VarietyEstablishmentService varietyEstablishmentService;
    @Autowired
    EstablishmentService establishmentService;

//    @Test
//    public void TestChino() {
//        LocalDate start = LocalDate.of(2019, 12, 1);
//        LocalDate end = LocalDate.of(2019, 12, 4);
//        int a = 0;
//        assert end != null;
//
//    }
//
//    @Test
//    public void TestisEstaEstablecimientoCaptado() {
//        assert establishmentService.isCatched(5095L);
//    }
//
//    @Test
//    public void TestAllVarietiesCathed() {
//        LocalDate end = LocalDate.now();
//        LocalDate start = LocalDate.of(end.getYear(), end.getMonth(), 1);
//
//        assert varietyEstablishmentService.CantvariedadesCaptadas(start, end) != -1;
//    }

    @Test
    public void test() {
        assert true;
    }
}
