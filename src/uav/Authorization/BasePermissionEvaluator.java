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
package uav.Authorization;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import uav.Authentification.DataModel.UserPrincipal;

import javax.annotation.Nonnull;
import java.io.Serializable;

@Component
public class BasePermissionEvaluator implements PermissionEvaluator {
    private Logger logger = LoggerFactory.getLogger(BasePermissionEvaluator.class);
    private PermissionResolver resolver;

    public BasePermissionEvaluator(@Nonnull PermissionResolver resolver) {
        this.resolver = resolver;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        if((authentication == null) || (targetDomainObject == null) || !(permission instanceof String)){
            return false;
        }
        if(!authentication.isAuthenticated()) return false;
        String targetType = targetDomainObject.getClass().getSimpleName().toUpperCase();
        UserPrincipal principal = (UserPrincipal)authentication.getPrincipal();
        Long targetId = 0l;
        if(targetDomainObject instanceof ProtectedResource) {
            targetId = ((ProtectedResource) targetDomainObject).getId();
        }
        return this.resolver.resolve(principal, targetId, targetType.toUpperCase(), permission.toString().toUpperCase());
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        if(authentication == null || targetType == null || !(permission instanceof String)) {
            return false;
        }
        if(!authentication.isAuthenticated()) return false;
        UserPrincipal principal = (UserPrincipal)authentication.getPrincipal();
        return this.resolver.resolve(principal, targetId, targetType.toUpperCase(), permission.toString().toUpperCase());
    }
}
