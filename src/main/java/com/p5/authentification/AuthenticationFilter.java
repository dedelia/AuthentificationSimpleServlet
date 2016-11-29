package com.p5.authentification;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dtristu on 29.11.2016.
 */
public class AuthenticationFilter implements Filter {

    Map<String,String> tokens = new HashMap<String, String>();

    public void init(FilterConfig filterConfig) throws ServletException {
        tokens.put("token1","user1");
        tokens.put("token2","user2");
        tokens.put("token3","user3");
    }


    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String authHeader = request.getHeader("token");
        if (authHeader != null && tokens.containsKey(authHeader)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            unauthorized(response);
        }
    }

    public void destroy() {
        // Skip for now
    }

    private void unauthorized(HttpServletResponse response) throws IOException {
        response.sendError(401, "Unauthorized");
    }
}
