package com.poly.bookingapi.auth;

import com.poly.bookingapi.entity.RoleAccount;
import jakarta.persistence.Column;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RegisterRequest {
    private String sdt;
    private String password;
    private String fullname;

    public static class Builder {
        private String sdt;
        private String password;
        private String fullname;

        public Builder setSdt(String sdt) {
            this.sdt = sdt;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setFullname(String fullname) {
            this.fullname = fullname;
            return this;
        }

        public RegisterRequest build() {
            RegisterRequest request = new RegisterRequest();
            request.sdt = this.sdt;
            request.password = this.password;
            request.fullname = this.fullname;
            return request;
        }
    }
}
