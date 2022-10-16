package com.tui.proof.service.validator.custom.order;

import com.tui.proof.dto.order.OrderDTO;
import com.tui.proof.exception.BusinessException;

public interface CustomOrderValidator {
    void validate(OrderDTO orderDTO) throws BusinessException;
}
