package com.p5.authentification;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dtristu on 01.12.2016.
 */
public class SecondAuthentificationFilter implements Filter {

    Map<String, String> tokens = new HashMap<String, String>();

    public void init(FilterConfig filterConfig) throws ServletException {
        tokens.put("ASDXCZ", "user1");
    }


    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if( request.getMethod().equalsIgnoreCase("GET")) {
            String authHeader = request.getHeader("token");

            if (authHeader != null) {
                String chunk = authHeader.substring(authHeader.indexOf(":") + 1, authHeader.length());
                chunk = chunk.substring(0, chunk.indexOf(":"));

                if (tokens.containsKey(chunk)) {

                    filterChain.doFilter(request, response);
                } else {
                    unauthorized((HttpServletResponse) servletResponse);
                }
            } else {
                unauthorizedHeader((HttpServletResponse) servletResponse);
            }
        }
        else{
            filterChain.doFilter(request, response);
        }
    }


    public void destroy() {
        // Skip for now
    }

    private void unauthorized(HttpServletResponse response) throws IOException {
        response.sendError(401, "Unauthorized");
    }

    private void unauthorizedHeader(HttpServletResponse response) throws IOException {
        response.sendError(401, "Header missing");
    }
}
