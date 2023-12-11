package com.poly.bookingapi.auth;

import com.poly.bookingapi.entity.RoleAccount;
import jakarta.persistence.Column;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class RegisterRequest {
    private String sdt;
    private String password;
    private String fullname;
}
