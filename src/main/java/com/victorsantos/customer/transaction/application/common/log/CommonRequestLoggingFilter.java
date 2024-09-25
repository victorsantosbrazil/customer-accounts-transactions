package com.victorsantos.customer.transaction.application.common.log;

import static com.victorsantos.customer.transaction.application.common.util.IpUtil.getClientIpAddress;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.victorsantos.customer.transaction.application.common.util.CachedHttpServletRequest;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StreamUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

@RequiredArgsConstructor
@Slf4j
public class CommonRequestLoggingFilter extends OncePerRequestFilter {
    private final ObjectMapper objectMapper;

    private static final Set<String> LOGGED_REQUEST_HEADERS = Set.of("user-agent");

    private static final Set<String> IGNORED_URIS =
            Set.of("/swagger-ui", "/v3/api-docs", "/swagger-resources", "/webjars/springfox-swagger-ui");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (shouldIgnore(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        String requestId = UUID.randomUUID().toString();
        CachedHttpServletRequest cachedRequest = new CachedHttpServletRequest(request);
        ContentCachingResponseWrapper cachedResponse = new ContentCachingResponseWrapper(response);

        beforeRequest(requestId, cachedRequest);

        filterChain.doFilter(cachedRequest, cachedResponse);

        afterRequest(requestId, cachedResponse);

        // Respond to the client with the cached data.
        cachedResponse.copyBodyToResponse();
    }

    private boolean shouldIgnore(HttpServletRequest request) {
        String uri = request.getRequestURI();
        return IGNORED_URIS.stream().anyMatch(uri::startsWith);
    }

    private void beforeRequest(String requestId, CachedHttpServletRequest cachedRequest) {

        var user = Objects.requireNonNullElse(cachedRequest.getRemoteUser(), "unknown");

        Request requestLog = Request.builder()
                .id(requestId)
                .uri(cachedRequest.getRequestURI())
                .method(cachedRequest.getMethod())
                .user(user)
                .from(getClientIpAddress(cachedRequest))
                .headers(getRequestHeaders(cachedRequest))
                .body(getRequestBody(cachedRequest))
                .build();

        log.info("Request received. {}", requestLog);
    }

    private Map<String, String> getRequestHeaders(CachedHttpServletRequest cachedRequest) {
        Map<String, String> headers = new HashMap<>();
        var headerNames = cachedRequest.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            var headerName = headerNames.nextElement();
            if (LOGGED_REQUEST_HEADERS.contains(headerName.toLowerCase())) {
                headers.put(headerName, cachedRequest.getHeader(headerName));
            }
        }
        return headers;
    }

    private String getRequestBody(CachedHttpServletRequest request) {
        try {
            if (request.getInputStream().available() != 0) {
                return "";
            }
            if (request.getContentType() == null) {
                return "";
            }
            var json = StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);
            return compactJson(json);
        } catch (IOException e) {
            return "";
        }
    }

    private void afterRequest(String requestId, ContentCachingResponseWrapper cachedResponse) {
        var responseLog = Response.builder()
                .requestId(requestId)
                .statusCode(cachedResponse.getStatus())
                .body(getResponseBody(cachedResponse))
                .build();
        log.info("Response sent. {}", responseLog);
    }

    private String getResponseBody(ContentCachingResponseWrapper response) {
        if (response.getContentAsByteArray().length == 0) {
            return "";
        }
        var json = new String(response.getContentAsByteArray(), StandardCharsets.UTF_8);
        return compactJson(json);
    }

    private String compactJson(String json) {
        try {
            var node = objectMapper.readTree(json);
            return objectMapper.writeValueAsString(node);
        } catch (Exception e) {
            return json;
        }
    }
}
