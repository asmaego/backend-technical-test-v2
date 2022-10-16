package com.tui.proof.service.validator.custom.order;

import com.tui.proof.config.constants.ValidatorConst;
import com.tui.proof.dto.order.OrderDTO;
import com.tui.proof.dto.order.OrderItemDTO;
import com.tui.proof.exception.BusinessException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderUpdateValidatorTest {

    @Autowired
    Map<String, CustomOrderValidator> customOrderValidatorMap;

    @Test
    public void validate() throws BusinessException {
        UUID rand = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();
        List<OrderItemDTO> orderItemsDtos = new ArrayList<>();
        orderItemsDtos.add(new OrderItemDTO(5L, 1, "0001", 9L, 110.0));
        OrderDTO dto = new OrderDTO(10L, rand.toString(), now, 10.9, 1L, 1L, orderItemsDtos);

        CustomOrderValidator validator = customOrderValidatorMap.get(ValidatorConst.COMPONENT_PREFIX + ValidatorConst.ORDER_UPDATE);

        assertNotNull(validator);
        validator.validate(dto);
    }

    @Test()
    public void validate_expect_exception(){
        UUID rand = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now().minusMinutes(6);
        List<OrderItemDTO> orderItemsDtos = new ArrayList<>();
        orderItemsDtos.add(new OrderItemDTO(5L, 1, "0001", 9L, 110.0));
        OrderDTO dto = new OrderDTO(10L, rand.toString(), now, 10.9, 1L, 1L, orderItemsDtos);

        CustomOrderValidator validator = customOrderValidatorMap.get(ValidatorConst.COMPONENT_PREFIX + ValidatorConst.ORDER_UPDATE);

        assertNotNull(validator);

        Throwable exception = assertThrows(BusinessException.class, () -> validator.validate(dto));
        assertEquals("You can no longer update this order", exception.getMessage());
    }
}