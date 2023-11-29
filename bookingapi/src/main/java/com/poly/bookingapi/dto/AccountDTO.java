package com.poly.bookingapi.dto;

import com.poly.bookingapi.entity.RoleAccount;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AccountDTO {
    private String sdt;
    private String password;
    private RoleAccount role;
    private String fullname;
}
