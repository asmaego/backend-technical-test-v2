package com.tui.proof.controller;

import com.tui.proof.dto.ApiResponseDTO;
import com.tui.proof.dto.order.OrderCreateDTO;
import com.tui.proof.dto.order.OrderDTO;
import com.tui.proof.dto.order.OrderListDTO;
import com.tui.proof.dto.order.SearchDTO;
import com.tui.proof.exception.BusinessException;
import com.tui.proof.service.OrderService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("${com.tui.api}${com.tui.api.orders}")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping(path = "/create")
    public ResponseEntity<ApiResponseDTO> createOrder(@NonNull @RequestBody OrderCreateDTO orderCreateDTO) throws BusinessException {
        String orderNumber = orderService.createOrder(orderCreateDTO);
        return new ResponseEntity<>(new ApiResponseDTO("Order has been created, number : " + orderNumber), HttpStatus.CREATED);
    }

    @PutMapping(path = "/{orderNumber}")
    public ResponseEntity<ApiResponseDTO> updateOrder(
            @NonNull @PathVariable(value = "orderNumber") String orderNumber,
            @NonNull @RequestBody OrderCreateDTO orderCreateDTO
    ) throws BusinessException {
        orderService.updateOrder(orderNumber, orderCreateDTO);
        return new ResponseEntity<>(new ApiResponseDTO("Order has been updated"), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping(path = "/search")
    public ResponseEntity<OrderListDTO> searchOrder(@NonNull @RequestBody SearchDTO searchDto) {
        OrderListDTO orderListDTO = new OrderListDTO(orderService.searchOrder(searchDto));
        return new ResponseEntity<>(orderListDTO, HttpStatus.OK);
    }

    @GetMapping(path = "/byNumber/{number}")
    public ResponseEntity<OrderDTO> getOrderByNumber(@NonNull @PathVariable(value = "number") String number) {
        return new ResponseEntity<>(orderService.getOrderDtoByNumber(number), HttpStatus.OK);
    }
}
