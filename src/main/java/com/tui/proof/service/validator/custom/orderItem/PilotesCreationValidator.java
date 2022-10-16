package com.tui.proof.service.validator.custom.orderItem;

import com.tui.proof.config.constants.MealConst;
import com.tui.proof.config.constants.ValidatorConst;
import com.tui.proof.dto.order.OrderItemDTO;
import com.tui.proof.exception.BusinessException;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component(ValidatorConst.COMPONENT_PREFIX + MealConst.PILOTES_CODE)
public class PilotesCreationValidator implements CustomOrderItemValidator {

    @Override
    public void validate(OrderItemDTO orderItem) throws BusinessException {
        if (Arrays.stream(MealConst.PILOTES_POSSIBLE_QUANTITIES).boxed().noneMatch(value -> value.equals(orderItem.getQuantity()))) {
            throw new BusinessException("Pilotes quantity must be 5, 10 or 15. You provided : " + orderItem.getQuantity());
        }
    }
}
