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
package uav.Security.Authorization;

import org.springframework.stereotype.Component;
import uav.Security.Authentification.DataModel.UserPrincipal;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.Map;

@Component
public class PermissionResolver {
    class RelationRoleResourceType {
        public Long roleId;
        public String resourceType;
        private int hashCode = -1;

        public RelationRoleResourceType(Long roleId, String resourceType) {
            this.roleId = roleId;
            this.resourceType = resourceType;
        }

        public void resetHashCode() {
            this.hashCode = -1;
        }

        @Override
        public int hashCode() {
            if(hashCode == -1) {
                if(this.resourceType != null && (this.resourceType = this.resourceType.trim()).length() > 0) {
                    hashCode = 0;
                    for(int i = 0; i < this.resourceType.length(); ++i) {
                        hashCode += (this.resourceType.charAt(i) * i);
                    }
                    hashCode = (int)(((long)hashCode + roleId.longValue()) % (Integer.MAX_VALUE));
                }
            }
            return hashCode;
        }

        @Override
        public boolean equals(Object obj) {
            if(obj == null || !(obj instanceof RelationRoleResourceType)) return false;
            RelationRoleResourceType other = (RelationRoleResourceType)obj;
            if(this.roleId == null || this.resourceType == null || other.roleId == null || other.resourceType == null) return false;
            if(!this.roleId.equals(other.roleId) || !this.resourceType.equals(other.resourceType)) return false;
            return true;
        }
    }
    private Map<RelationRoleResourceType, PermissionType> userPrincipalIds;

    public boolean resolve(@Nonnull UserPrincipal principal, @Nullable Serializable entityId, @Nonnull String targetType, @Nonnull String permission) {
        RelationRoleResourceType rrrt = new RelationRoleResourceType(0l, "");
        return principal.getRoles().stream().anyMatch(r -> {
            rrrt.resetHashCode();
            rrrt.roleId = (long)r.getId();
            rrrt.resourceType = targetType;
            return userPrincipalIds.get(rrrt).equals(permission);
        });
    }
}
