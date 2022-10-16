package com.tui.proof.service.validator;

import com.tui.proof.config.constants.ValidatorConst;
import com.tui.proof.dto.meal.MealDTO;
import com.tui.proof.dto.order.OrderDTO;
import com.tui.proof.dto.order.OrderItemDTO;
import com.tui.proof.exception.BusinessException;
import com.tui.proof.model.OrderItem;
import com.tui.proof.service.AddressService;
import com.tui.proof.service.ClientService;
import com.tui.proof.service.MealService;
import com.tui.proof.service.OrderService;
import com.tui.proof.service.validator.custom.order.CustomOrderValidator;
import com.tui.proof.service.validator.custom.orderItem.CustomOrderItemValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

@Component
public class OrderValidator {

    private final Map<String, CustomOrderItemValidator> customOrderItemValidatorMap;
    private final Map<String, CustomOrderValidator> customOrderValidatorMap;
    private MealService mealService;
    private ClientService clientService;
    private AddressService addressService;
    private OrderService orderService;

    @Autowired
    public OrderValidator(Map<String, CustomOrderItemValidator> customOrderItemValidatorMap, Map<String, CustomOrderValidator> customOrderValidatorMap, @Lazy MealService mealService,@Lazy ClientService clientService,@Lazy AddressService addressService,@Lazy OrderService orderService) {
        this.customOrderItemValidatorMap = customOrderItemValidatorMap;
        this.customOrderValidatorMap = customOrderValidatorMap;
        this.mealService = mealService;
        this.clientService = clientService;
        this.addressService = addressService;
        this.orderService = orderService;
    }


    public void validateCreation(OrderDTO orderDto) throws BusinessException {
        validateClientExists(orderDto);
        validateAddressExists(orderDto);
        validateMeals(orderDto);
        performCustomCreationValidation(orderDto);
    }

    public void validateUpdate(String orderNumber, OrderDTO orderDto) throws BusinessException {
        validateOrderExistsByNumber(orderNumber);
        performCustomUpdateValidation(orderService.getOrderDtoByNumber(orderNumber));
        validateCreation(orderDto);
    }

    private void performCustomCreationValidation(OrderDTO orderDto) throws BusinessException {
        for(OrderItemDTO item:orderDto.getOrderItems()){
            CustomOrderItemValidator validator = customOrderItemValidatorMap.get(ValidatorConst.COMPONENT_PREFIX + item.getMealCode());

            if (Objects.nonNull(validator)) {
                validator.validate(item);
            }
        };
    }

    private void performCustomUpdateValidation(OrderDTO orderDto) throws BusinessException {
        CustomOrderValidator validator = customOrderValidatorMap.get(ValidatorConst.COMPONENT_PREFIX + ValidatorConst.ORDER_UPDATE);
        if(Objects.nonNull(validator)){
            validator.validate(orderDto);
        }
    }

    private void validateMeals(OrderDTO orderDto) throws  BusinessException {
        for(OrderItemDTO item:orderDto.getOrderItems()) {
            if (Objects.isNull(mealService.getMealByCode(item.getMealCode()))) {
                throw new BusinessException("Meal with code : " + item.getMealCode() + "does not exist");
            }
        };
    }

    private void validateClientExists(OrderDTO orderDto) throws  BusinessException {
        if (Objects.isNull(clientService.getClient(orderDto.getClientId()))) {
            throw new BusinessException("Client is not valid");
        }
    }

    private void validateAddressExists(OrderDTO orderDto) throws  BusinessException {
        if (Objects.isNull(addressService.getAddress(orderDto.getDeliveryAddressId()))) {
            throw new BusinessException("Address is not valid");
        }
    }

    private void validateOrderExistsByNumber(String number) throws BusinessException {
        if (Objects.isNull(orderService.getOrderByNumber(number))) {
            throw new BusinessException("Order does not exist");
        }
    }

}
