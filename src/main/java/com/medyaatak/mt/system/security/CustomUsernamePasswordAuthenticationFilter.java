package com.medyaatak.mt.system.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Ginnun
 * Date: 28.09.2011
 * Time: 21:31
 * To change this template use File | Settings | File Templates.
 */

public class CustomUsernamePasswordAuthenticationFilter extends
        UsernamePasswordAuthenticationFilter {


    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response, Authentication authResult)
            throws IOException, ServletException {
        super.successfulAuthentication(request, response, authResult);

request.getSession().setAttribute("userName", authResult.getCredentials());

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response, AuthenticationException failed)
            throws IOException, ServletException {
        super.unsuccessfulAuthentication(request, response, failed);

        System.out.println("==failed login==");
    }

}
