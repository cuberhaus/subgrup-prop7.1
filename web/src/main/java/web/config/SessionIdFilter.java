package web.config;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Cross-service session correlation: reads the {@code X-Session-Id}
 * header (set by the parent page's network tap, see
 * {@code PersonalPortfolio/src/lib/debug-network.ts}) off every incoming
 * request and stamps it on the SLF4J MDC so the JSON-encoded log lines
 * carry a {@code session_id} field per request.
 *
 * <p>obs-experiment-coroot branch: this filter previously called
 * {@code Sentry.setTag("session_id", ...)}. With Sentry removed (Phase 2
 * of the Coroot PoC), correlation now flows via the log pipeline:
 * Coroot's container-log scraper indexes structured fields out of the
 * JSON-encoded stdout (see {@code logback-spring.xml}) so a session-
 * scoped log query in the Coroot UI light up the same way.
 *
 * <p>The MDC value is cleared in a {@code finally} block so it does not
 * leak across requests on a recycled servlet thread.
 *
 * <p>Registered as a {@link Component} so Spring Boot picks it up via
 * component scanning; placing it in {@code web.config} keeps it next to
 * the existing {@link GlobalModelAttributes}.
 */
@Component
public class SessionIdFilter implements Filter {

    private static final String MDC_KEY = "session_id";
    private static final String HEADER_NAME = "X-Session-Id";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        boolean mdcSet = false;
        try {
            if (request instanceof HttpServletRequest httpRequest) {
                String sessionId = httpRequest.getHeader(HEADER_NAME);
                if (sessionId != null && !sessionId.isEmpty()) {
                    MDC.put(MDC_KEY, sessionId);
                    mdcSet = true;
                }
            }
            chain.doFilter(request, response);
        } finally {
            if (mdcSet) {
                MDC.remove(MDC_KEY);
            }
        }
    }
}
