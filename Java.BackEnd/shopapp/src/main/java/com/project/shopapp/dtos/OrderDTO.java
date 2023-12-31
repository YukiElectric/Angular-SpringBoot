package com.project.shopapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    @Min(value = 1, message = "User's ID must be > 0")
    @JsonProperty("user_id")
    private Long userId;
    
    @JsonProperty("fullname")
    private String fullName;
    
    private String email;
    
    @NotBlank(message = "Phone number is required")
    @JsonProperty("phone_number")
    private String phoneNumber;
    
    private String address;
    private String note;
    
    @Min(value = 0, message = "Total money must be greater than or equal 0")
    @JsonProperty("total_money")
    private Float totalMoney;
    
    @JsonProperty("shipping_method")
    private String shippingMethod;
    
    @JsonProperty("shipping_date")
    private LocalDate shippingDate;
    
    @JsonProperty("shipping_address")
    private String shippingAddress;
    
    @JsonProperty("payment_method")
    private String paymentMethod;
}
