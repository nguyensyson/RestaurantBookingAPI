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

    private Integer id;
    private String username;
    private Integer roleId;

    public static class Builder {
        private String accessToken;
        private String refreshToken;
        private Integer id;
        private String username;
        private Integer roleId;

        // Setter methods for all fields

        public Builder setAccessToken(String accessToken) {
            this.accessToken = accessToken;
            return this;
        }
        public Builder setRefreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
            return this;
        }
        public Builder setId(Integer id) {
            this.id = id;
            return this;
        }
        public Builder setUsername(String username) {
            this.username = username;
            return this;
        }
        public Builder setRoleId(Integer roleId) {
            this.roleId = roleId;
            return this;
        }

        // Build method to create the AuthenticationResponse instance
        public AuthenticationResponse build() {
            AuthenticationResponse response = new AuthenticationResponse();
            response.accessToken = this.accessToken;
            response.refreshToken = this.refreshToken;
            response.id = this.id;
            response.roleId = this.roleId;
            response.username = this.username;

            return response;
        }
    }
}
