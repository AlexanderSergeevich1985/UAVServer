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
}
