package com.project.shopapp.controllers;

import com.project.shopapp.dtos.OrderDTO;
import com.project.shopapp.models.Order;
import com.project.shopapp.responses.OrderResponse;
import com.project.shopapp.services.impl.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.prefix}/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    @PostMapping("")
    public ResponseEntity<?> createOrder(
        @RequestBody @Valid OrderDTO orderDTO,
        BindingResult result
    ) {
        try {
            if (result.hasErrors()) {
                List<String> erorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
                return ResponseEntity.badRequest().body(erorMessages);
            }
            Order order = orderService.createOrder(orderDTO);
            
            return ResponseEntity.ok(OrderResponse.fromOrder(order));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/user/{user_id}")
    public ResponseEntity<?> getOrders(
        @Valid @PathVariable("user_id") Long userId
    ) {
        try {
            List<Order> orders = orderService.getAllOrders(userId);
            List<OrderResponse> orderResponses = orders.stream().map(OrderResponse::fromOrder).collect(
                Collectors.toList());
            return ResponseEntity.ok(orderResponses);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrder(
        @Valid @PathVariable("id") Long id
    ){
        try {
            Order existingOrder = orderService.getOrder(id);
            return ResponseEntity.ok(existingOrder);
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(
        @Valid @PathVariable("id") Long id,
        @Valid @RequestBody OrderDTO orderDTO
    ) {
        try {
            Order order = orderService.updateOrder(id, orderDTO);
            return ResponseEntity.ok(OrderResponse.fromOrder(order));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder (@Valid @PathVariable("id") long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.ok("Delete order id = "+id+" sucessfully");
    }
    
    
}
