package web.config;

import io.sentry.Sentry;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Cross-service session correlation: reads the {@code X-Session-Id}
 * header (set by the parent page's network tap, see
 * {@code PersonalPortfolio/src/lib/debug-network.ts}) off every incoming
 * request and stamps it on the request-scoped Sentry scope so backend
 * events join the same session as frontend / iframe events.
 *
 * Sentry's Spring Boot starter automatically scopes per-request hubs,
 * so calling {@code Sentry.setTag} inside this filter is safe — the tag
 * does not leak across concurrent requests.
 *
 * Registered as a {@link Component} so Spring Boot picks it up via
 * component scanning; placing it in {@code web.config} keeps it next to
 * the existing {@link GlobalModelAttributes}.
 */
@Component
public class SessionIdFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (request instanceof HttpServletRequest httpRequest) {
            String sessionId = httpRequest.getHeader("X-Session-Id");
            if (sessionId != null && !sessionId.isEmpty()) {
                Sentry.setTag("session_id", sessionId);
            }
        }
        chain.doFilter(request, response);
    }
}
