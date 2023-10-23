package com.project.shopapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@Data
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    @JsonProperty("full_name")
    private String fullName;
    
    @NotBlank(message = "Phone number is required")
    @JsonProperty("phone_number")
    private String phoneNumber;
    
    private String address;
    
    @NotNull(message = "Password cannot be blank")
    private String password;
    
    @JsonProperty(namespace = "retype_password")
    private String retypePassword;
    
    @JsonProperty("date_of_birth")
    private Date dateOfBirth;
    
    @JsonProperty("facebook_account_id")
    private int facebookAccountId;
    
    @JsonProperty("google_account_id")
    private int googleAccountId;
    
    @JsonProperty("role_id")
    @NotNull(message = "Role ID is required")
    private Long roleId;
}
