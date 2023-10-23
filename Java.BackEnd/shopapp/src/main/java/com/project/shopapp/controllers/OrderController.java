package com.project.shopapp.controllers;

import com.project.shopapp.dtos.OrderDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/orders")
public class OrderController {
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
            return ResponseEntity.ok("Create oder successfuly");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/{user_id}")
    public ResponseEntity<?> getOrders(
        @Valid @PathVariable("user_id") Long userId
    ) {
        try {
            return ResponseEntity.ok("Get order list from user_id = "+userId+" successfuly");
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(
        @Valid @PathVariable("id") Long id,
        @Valid @RequestBody OrderDTO orderDTO
    ) {
        return ResponseEntity.ok("Update order id = "+id+" successfuly");
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder (@Valid @PathVariable("id") long id) {
        return ResponseEntity.ok("Delete order id = "+id+" sucessfully");
    }
    
    
}
