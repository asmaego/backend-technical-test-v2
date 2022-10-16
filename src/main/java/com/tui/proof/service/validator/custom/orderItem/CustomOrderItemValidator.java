package com.tui.proof.service.validator.custom.orderItem;


import com.tui.proof.dto.order.OrderItemDTO;
import com.tui.proof.exception.BusinessException;

public interface CustomOrderItemValidator {
    void validate(OrderItemDTO orderItem) throws BusinessException;
}
