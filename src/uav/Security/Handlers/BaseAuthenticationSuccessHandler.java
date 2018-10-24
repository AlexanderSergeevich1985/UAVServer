package uav.Security.Handlers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BaseAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private static Logger logger = Logger.getLogger(BaseAuthenticationSuccessHandler.class.getName());

    private RequestCache requestCache = new HttpSessionRequestCache();
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        boolean isAdmin = authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        if(isAdmin) {
            String contextPath = request.getContextPath();
            String requestURI = request.getRequestURI();
            return requestURI.substring(requestURI.indexOf(contextPath) + contextPath.length());
        }
        else {
            return "/apps_selector";
        }
    }

    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response,
                                        final Authentication authentication) throws IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        String url = determineTargetUrl(request, response);
        if(response.isCommitted()) {
            if(this.logger.isLoggable(Level.INFO)) {
                this.logger.log(Level.INFO, "Response has been committed yet. Unable to redirect to " + url);
            }
            return;
        }
        redirectStrategy.sendRedirect(request, response, url);
        clearAuthenticationAttributes(request);
    }

    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }

    public RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }

    public void setRequestCache(final RequestCache requestCache) {
        this.requestCache = requestCache;
    }
}
