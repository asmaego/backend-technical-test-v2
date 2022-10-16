package com.tui.proof.service.validator.custom.orderItem;

import com.tui.proof.config.constants.MealConst;
import com.tui.proof.config.constants.ValidatorConst;
import com.tui.proof.dto.order.OrderItemDTO;
import com.tui.proof.exception.BusinessException;
import com.tui.proof.service.validator.custom.order.CustomOrderValidator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PilotesCreationValidatorTest {

    @Autowired
    Map<String, CustomOrderItemValidator> customOrderItemValidatorMap;

    @Test
    void validate() throws BusinessException {
        OrderItemDTO orderItemDTO = new OrderItemDTO(5L, 5, "0001", 9L, 110.0);

        CustomOrderItemValidator validator = customOrderItemValidatorMap.get(ValidatorConst.COMPONENT_PREFIX + MealConst.PILOTES_CODE);

        assertNotNull(validator);
        validator.validate(orderItemDTO);
    }

    @Test
    void validate_expect_exception() {
        OrderItemDTO orderItemDTO = new OrderItemDTO(5L, 1, "0001", 9L, 110.0);

        CustomOrderItemValidator validator = customOrderItemValidatorMap.get(ValidatorConst.COMPONENT_PREFIX + MealConst.PILOTES_CODE);

        assertNotNull(validator);
        Throwable exception = assertThrows(BusinessException.class, () -> validator.validate(orderItemDTO));
        assertEquals("Pilotes quantity must be 5, 10 or 15. You provided : " + orderItemDTO.getQuantity(), exception.getMessage());
    }
}