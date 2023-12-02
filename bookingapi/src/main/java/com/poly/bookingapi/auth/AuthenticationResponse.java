package com.poly.bookingapi.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthenticationResponse {

    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("refresh_token")
    private String refreshToken;
    private Integer userId;
    private Integer roleId;

    public static class Builder {
        private String accessToken;
        private String refreshToken;
        private Integer userId;
        private Integer roleId;

        public Builder setAccessToken(String accessToken) {
            this.accessToken = accessToken;
            return this;
        }
        public Builder setRefreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
            return this;
        }
        public Builder setUserId(Integer userId) {
            this.userId = userId;
            return this;
        }
        public Builder setRoleId(Integer roleId) {
            this.roleId = roleId;
            return this;
        }

        public AuthenticationResponse build() {
            AuthenticationResponse response = new AuthenticationResponse();
            response.accessToken = this.accessToken;
            response.refreshToken = this.refreshToken;
            response.roleId = this.roleId;
            response.userId = this.userId;

            return response;
        }
    }
}
