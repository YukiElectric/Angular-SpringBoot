package com.project.shopapp.controllers;

import com.project.shopapp.dtos.OrderDetailDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("${api.prefix}/order_details")
@RestController
public class OrderDetailController {
    @PostMapping("")
    public ResponseEntity<?> createOrderDetail (
        @Valid @RequestBody OrderDetailDTO orderDetailDTO
    ) {
        return ResponseEntity.ok("Create order detail successfully");
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderDetail (
        @Valid @PathVariable("id") Long id
    ) {
        return ResponseEntity.ok("Get orderdetail id = "+id+" successfully");
    }
    
    @GetMapping("/order/{orderId}")
    public ResponseEntity<?> getOrderDetails(
        @Valid @PathVariable("orderId") Long orderId
    ) {
        return ResponseEntity.ok("get orderdetail with orderid = "+orderId+" sucessfully");
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrderDetail(
        @Valid @PathVariable("id") Long id,
        @RequestBody OrderDetailDTO newOrderDetailData
    ) {
        return ResponseEntity.ok("update orderdetail with id="+id+" successfully");
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrderDetail (
        @Valid @PathVariable("id") Long id
    ) {
        return ResponseEntity.ok("Delete orderdetail id = "+id+" successfully");
    }
}
