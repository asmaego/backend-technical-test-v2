package com.tui.proof.service;

import com.tui.proof.dto.order.OrderCreateDTO;
import com.tui.proof.dto.order.OrderDTO;
import com.tui.proof.dto.order.SearchDTO;
import com.tui.proof.exception.BusinessException;
import com.tui.proof.mappers.OrderItemMapper;
import com.tui.proof.mappers.OrderMapper;
import com.tui.proof.model.Meal;
import com.tui.proof.model.Order;
import com.tui.proof.model.OrderItem;
import com.tui.proof.repository.OrderItemRepository;
import com.tui.proof.repository.OrderRepository;
import com.tui.proof.service.validator.OrderValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class OrderService {

    private OrderRepository orderRepository;
    private OrderItemRepository orderItemRepository;
    private AddressService addressService;
    private ClientService clientService;
    private MealService mealService;
    private OrderValidator orderValidator;
    private OrderMapper orderMapper;
    private OrderItemMapper orderItemMapper;

    @Autowired
    public OrderService(OrderRepository orderRepository, OrderItemRepository orderItemRepository, AddressService addressService, ClientService clientService, MealService mealService, OrderValidator orderValidator, OrderMapper orderMapper, OrderItemMapper orderItemMapper) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.addressService = addressService;
        this.clientService = clientService;
        this.mealService = mealService;
        this.orderValidator = orderValidator;
        this.orderMapper = orderMapper;
        this.orderItemMapper = orderItemMapper;
    }

    @Transactional
    public String createOrder(OrderCreateDTO orderCreateDTO) throws BusinessException {

        OrderDTO orderDTO = orderMapper.orderCreateDtoToOrderDto(orderCreateDTO);
        orderValidator.validateCreation(orderDTO);

        Order order = new Order();
        order.setNumber(UUID.randomUUID());
        order.setCreationDate(LocalDateTime.now());
        order.setClient(clientService.getClient(orderDTO.getClientId()));
        order.setDeliveryAddress(addressService.getAddress(orderDTO.getDeliveryAddressId()));
        order.setTotalPrice(orderDTO.getOrderItems().stream().reduce(0.00, (subtotal, orderItemDto) ->
                        subtotal + (orderItemDto.getQuantity() * mealService.getMealByCode(orderItemDto.getMealCode()).getPrice())
                , Double::sum));
        orderRepository.save(order);

        orderDTO.getOrderItems().forEach(orderItemDto -> {
            Meal meal = mealService.getMealByCode(orderItemDto.getMealCode());
            orderItemRepository.save(OrderItem.builder()
                    .meal(meal)
                    .order(order)
                    .quantity(orderItemDto.getQuantity())
                    .itemPrice(orderItemDto.getQuantity() * meal.getPrice())
                    .build());
        });
        orderRepository.flush();
        orderItemRepository.flush();
        return order.getNumber().toString();
    }

    public OrderDTO getOrderDto(String id) {
        return orderMapper.orderToOrderDto(orderRepository.findById(Long.valueOf(id)).orElse(null));
    }

    public Order getOrderByNumber(String number) {
        return orderRepository.findByNumber(UUID.fromString(number)).orElse(null);
    }

    public OrderDTO getOrderDtoByNumber(String number) {
        return orderMapper.orderToOrderDto(orderRepository.findByNumber(UUID.fromString(number)).orElse(null));
    }

    @Transactional
    public void updateOrder(String orderNumber, OrderCreateDTO orderCreateDTO) throws BusinessException {

        OrderDTO orderDTO = orderMapper.orderCreateDtoToOrderDto(orderCreateDTO);
        orderValidator.validateUpdate(orderNumber, orderDTO);

        Order order = getOrderByNumber(orderNumber);
        order.setClient(clientService.getClient(orderDTO.getClientId()));
        order.setDeliveryAddress(addressService.getAddress(orderDTO.getDeliveryAddressId()));
        order.setTotalPrice(orderDTO.getOrderItems().stream().reduce(0.00, (subtotal, orderItemDto) ->
                        subtotal + (orderItemDto.getQuantity() * mealService.getMealByCode(orderItemDto.getMealCode()).getPrice())
                , Double::sum));
        orderRepository.save(order);

        List<OrderItem> orderItemsToDelete = new ArrayList<>(order.getOrderItems());
        order.setOrderItems(new ArrayList<>());
        orderItemRepository.deleteAll(orderItemsToDelete);

        orderDTO.getOrderItems().forEach(orderItemDto -> {
            Meal meal = mealService.getMealByCode(orderItemDto.getMealCode());
            orderItemRepository.save(OrderItem.builder()
                    .meal(meal)
                    .order(order)
                    .quantity(orderItemDto.getQuantity())
                    .itemPrice(orderItemDto.getQuantity() * meal.getPrice())
                    .build());
        });
        orderRepository.flush();
        orderItemRepository.flush();

    }

    public List<OrderDTO> searchOrder(SearchDTO searchDto) {
        return orderMapper.orderListToOrderDtoList(orderRepository.findByKeyword(searchDto.getKeyword()));
    }
}
