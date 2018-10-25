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
package uav.Security.Utils;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class BaseRequestMatcher implements RequestMatcher {
    private Pattern allowedMethods = Pattern.compile("^(GET|PUT|HEAD|TRACE|OPTIONS)$");
    private List<RequestMatcher> paths = new ArrayList<>();

    public void setAllowedMethods(Pattern allowedMethods) {
        this.allowedMethods = allowedMethods;
    }

    public void addPath(String pattern) {
        paths.add(new AntPathRequestMatcher(pattern));
    }

    public void removePath(String pattern) {
        paths.remove(new AntPathRequestMatcher(pattern));
    }

    @Override
    public boolean matches(HttpServletRequest request) {
        if(!request.isUserInRole("ROLE_ADMIN")) {
            if(!allowedMethods.matcher(request.getMethod()).matches()) {
                return false;
            }
        }
        return paths.stream().anyMatch(m -> m.matches(request));
    }

    public static Builder newBuilder() {
        return new BaseRequestMatcher().new Builder();
    }

    public class Builder {
        private Builder() {}

        public Builder setAllowedMethods(Pattern allowedMethods) {
            BaseRequestMatcher.this.allowedMethods = allowedMethods;
            return this;
        }

        public Builder addPath(String pattern) {
            BaseRequestMatcher.this.paths.add(new AntPathRequestMatcher(pattern));
            return this;
        }

        public BaseRequestMatcher build() {
            return BaseRequestMatcher.this;
        }
    }
}
