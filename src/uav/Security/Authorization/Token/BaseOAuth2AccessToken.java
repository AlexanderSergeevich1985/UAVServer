/**The MIT License (MIT)
 Copyright (c) 2018 by AleksanderSergeevich
 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:
 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.
 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
 */
package uav.Security.Authorization.Token;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import uav.Common.DataModels.BaseEntity;
import uav.Utils.DateTimeHelper;

import javax.annotation.Nonnull;
import javax.persistence.*;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.*;

@Entity
@Table(name = "ISSUED_TOKENS")
public class BaseOAuth2AccessToken extends BaseEntity implements OAuth2AccessToken {
    @Column(name = "acc_token_value", length = 500, nullable = false)
    private String value;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "acc_token_exp_date", nullable = false)
    private Date expiration;

    @Column(name = "token_type", length = 50, nullable = false)
    private String tokenType;

    @Embedded
    private OAuth2RefreshToken refreshToken;

    @ElementCollection
    private Set<String> scope;

    @Transient
    private Map<String, Object> additionalInformation;

    protected BaseOAuth2AccessToken() {
        this((String) null);
    }

    public BaseOAuth2AccessToken(String value) {
        this.tokenType = "Bearer".toLowerCase();
        this.additionalInformation = Collections.emptyMap();
        this.value = value;
    }

    public BaseOAuth2AccessToken(OAuth2AccessToken accessToken) {
        this(accessToken.getValue());
        this.setAdditionalInformation(accessToken.getAdditionalInformation());
        this.setRefreshToken(accessToken.getRefreshToken());
        this.setExpiration(accessToken.getExpiration());
        this.setScope(accessToken.getScope());
        this.setTokenType(accessToken.getTokenType());
    }

    public Map<String, Object> getAdditionalInformation() {
        return this.additionalInformation;
    }

    public void setAdditionalInformation(Map<String, Object> additionalInformation) {
        this.additionalInformation = new LinkedHashMap(additionalInformation);
    }

    public Set<String> getScope() {
        return this.scope;
    }

    public void setScope(Set<String> scope) {
        this.scope = scope;
    }

    @Nonnull
    public OAuth2RefreshToken getRefreshToken() {
        return this.refreshToken;
    }

    public void setRefreshToken(@Nonnull OAuth2RefreshToken refreshToken) {
        this.refreshToken = refreshToken;
    }

    @Nonnull
    public String getTokenType() {
        return this.tokenType;
    }

    public void setTokenType(@Nonnull String tokenType) {
        this.tokenType = tokenType;
    }

    public boolean isExpired() {
        return this.expiration != null && this.expiration.before(Date.from(DateTimeHelper.getLocalCurrentTime().toInstant(ZoneOffset.UTC)));
    }

    @Nonnull
    public Date getExpiration() {
        return this.expiration;
    }

    public void setExpiration(@Nonnull Date expiration) {
        this.expiration = expiration;
    }

    public int getExpiresIn() {
        return this.expiration != null ? Long.valueOf(Duration.between(this.expiration.toInstant(), Instant.now()).toMillis() / 1000L).intValue() : 0;
    }

    public void setExpiresIn(final long interval) {
        this.setExpiration(Date.from(DateTimeHelper.calcExpirationDate(Duration.ofSeconds(interval))));
    }

    @Nonnull
    public String getValue() {
        return this.value;
    }

    public void setValue(@Nonnull String value) {
        this.value = value;
    }
}
