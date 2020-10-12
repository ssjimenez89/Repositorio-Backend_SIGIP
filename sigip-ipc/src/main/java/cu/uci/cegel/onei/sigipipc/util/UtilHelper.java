package cu.uci.cegel.onei.sigipipc.util;

import com.sun.istack.NotNull;
import cu.uci.cegel.onei.sigipipc.model.PageResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class UtilHelper {
    public Pageable buildPage(@NotNull PageResource pageResource) {
        final Sort.Direction direction = (pageResource.getOrder() != null && pageResource.getOrder().equals("DESC")) ? Sort.Direction.DESC : Sort.Direction.ASC;
        String orderBy = pageResource.getOrderBy();
        if (Objects.isNull(orderBy))
            return PageRequest.of(pageResource.getPage(), pageResource.getPageSize(), Sort.by(direction, "id"));
        else {
            return PageRequest.of(pageResource.getPage(), pageResource.getPageSize(), Sort.by(direction, orderBy));
        }
    }
}
