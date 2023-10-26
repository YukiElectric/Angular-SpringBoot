package com.project.shopapp.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.shopapp.models.Order;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderResponse{
    private Long id;

    @JsonProperty("user_id")
    private Long userId;
    
    @JsonProperty("fullname")
    private String fullName;

    private String email;
    
    @JsonProperty("phone_number")
    private String phoneNumber;
    
    private String address;
    private String note;
    
    @JsonProperty("order_date")
    private LocalDate orderDate;
    
    @JsonProperty("shipping_date")
    private LocalDate shippingDate;
    
    private String status;
    
    @JsonProperty("total_money")
    private Float totalMoney;
    
    @JsonProperty("shipping_method")
    private String shippingMethod;
    
    @JsonProperty("shipping_address")
    private String shippingAddress;
    
    @JsonProperty("tracking_number")
    private String trackingNumber;
    
    @JsonProperty("payment_method")
    private String paymentMethod;
    
    private boolean active;
    
    public static OrderResponse fromOrder(Order order) {
        OrderResponse orderResponse = OrderResponse.builder()
            .id(order.getId())
            .userId(order.getUser().getId())
            .fullName(order.getFullName())
            .email(order.getEmail())
            .address(order.getAddress())
            .phoneNumber(order.getPhoneNumber())
            .paymentMethod(order.getPaymentMethod())
            .shippingAddress(order.getShippingAddress())
            .note(order.getNote())
            .orderDate(order.getOrderDate())
            .shippingDate(order.getShippingDate())
            .active(order.isActive())
            .totalMoney(order.getTotalMoney())
            .trackingNumber(order.getTrackingNumber())
            .status(order.getStatus())
            .shippingMethod(order.getShippingMethod())
            .build();
        return orderResponse;
    }
}
