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
package com.uavframework.apps.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Scope("request")
@RequestMapping("/security")
public class SecurityController {
    @Autowired
    private DefaultTokenServices tokenServices;

    @PostMapping(path = "authentication")
    public ResponseEntity<?> createAccessToken(@Valid @RequestBody OAuth2Authentication authentication) {
        OAuth2AccessToken newAccessToken = this.tokenServices.createAccessToken(authentication);
        if(newAccessToken == null) {
            return ResponseEntity.ok(newAccessToken);
        }
        return ResponseEntity.badRequest().body("Invalid data request");
    }

    @PostMapping(path = "renovation")
    public ResponseEntity<?> renewAccessToken(@RequestParam(value = "refresh_token", required = false) String refreshToken, @Valid @RequestBody TokenRequest request) {
        OAuth2AccessToken renewAccessToken = this.tokenServices.refreshAccessToken(refreshToken, request);
        if(renewAccessToken == null) {
            return ResponseEntity.ok(renewAccessToken);
        }
        return ResponseEntity.badRequest().body("Invalid data request");
    }

    @PostMapping(path = "revocation", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> revokeToken(@RequestParam("access_token") String accessToken) {
        if(this.tokenServices.revokeToken(accessToken)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().body("Invalid data request");
    }
}
