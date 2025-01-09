package com.b2c.prototype.authentification;

import org.springframework.web.filter.GenericFilterBean;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class AuthFilter extends GenericFilterBean {

    @Override
    public void doFilter(final ServletRequest req, final ServletResponse res, final FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) req;
        final HttpServletResponse response = (HttpServletResponse) res;
        final String authHeader = request.getHeader("authorization");
        final String url = request.getRequestURL().toString();
        StringBuilder logBuilder = new StringBuilder();

        try {
            logBuilder.append("[AuthFilter_doFilter] ## URL: ").append(url);

//            APIServletRequestWrapper requestWrapper = new APIServletRequestWrapper(payload);
//            APIServletResponseWrapper responseWrapper = new APIServletResponseWrapper(response);

//            chain.doFilter(requestWrapper, responseWrapper);
        } finally {
//            log.debug(logBuilder.toString());
        }
    }

    private boolean isNotSwaggerRequest(final HttpServletRequest req) {
        String requestUrl = req.getRequestURL().toString();
        return !(requestUrl.contains("swagger") || requestUrl.contains("api-docs") || requestUrl.contains("actuator")
                || requestUrl.contains("cloudfoundryapplication"));
    }

}
