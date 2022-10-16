package com.tui.proof.service.validator.custom.order;

import com.tui.proof.config.constants.OrderConst;
import com.tui.proof.config.constants.ValidatorConst;
import com.tui.proof.dto.order.OrderDTO;
import com.tui.proof.exception.BusinessException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component(ValidatorConst.COMPONENT_PREFIX + ValidatorConst.ORDER_UPDATE)
public class OrderUpdateValidator implements CustomOrderValidator {

    @Override
    public void validate(OrderDTO orderDTO) throws BusinessException {
        if (orderDTO.getCreationDate().until(LocalDateTime.now(), ChronoUnit.SECONDS)> OrderConst.ORDER_MAX_TIME_UPDATE_SECONDS){
            throw new BusinessException("You can no longer update this order");
        }
    }
}
