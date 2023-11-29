package com.poly.bookingapi.entity;

import com.poly.bookingapi.dto.TokenType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "token")
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "stoken")
    private String stoken;

    @Column(name = "expired")
    private Boolean expired;

    @Column(name = "revoked")
    private Boolean revoked;

    @Column(name = "token_type")
    @Enumerated(EnumType.STRING)
    private TokenType tokenType = TokenType.BEARER;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

    public static class Builder {
        private Integer id;
        private String stoken;
        private Boolean expired;
        private Boolean revoked;
        private TokenType tokenType = TokenType.BEARER;
        private Account account;

        // Setter methods for all fields

        public Builder setId(Integer id) {
            this.id = id;
            return this;
        }

        public Builder setStoken(String stoken) {
            this.stoken = stoken;
            return this;
        }

        public Builder setExpired(Boolean expired) {
            this.expired = expired;
            return this;
        }

        public Builder setRevoked(Boolean revoked) {
            this.revoked = revoked;
            return this;
        }

        public Builder setTokenType(TokenType tokenType) {
            this.tokenType = tokenType;
            return this;
        }

        public Builder setAccount(Account account) {
            this.account = account;
            return this;
        }

        public Token build() {
            Token token = new Token();
            token.id = this.id;
            token.stoken = this.stoken;
            token.expired = this.expired;
            token.revoked = this.revoked;
            token.tokenType = this.tokenType;
            token.account = this.account;
            return token;
        }
    }
}
