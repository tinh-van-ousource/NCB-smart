/**
 *
 */
package com.tvo.config;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.google.gson.Gson;
import com.tvo.model.User;
import com.tvo.model.UserDetailsImpl;

/**
 * @author Ace
 *
 */
public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	InetAddress ip;
    String hostname;
    
    public JWTLoginFilter(String url, AuthenticationManager authManager) {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException {
        User user = new Gson().fromJson(request.getReader(), User.class);
        String userName = user.getUserName();
        String password = user.getPassword();

        System.out.println("JWTLoginFilter.attemptAuthentication: username = " + userName);
       
        // Execute authenticate then call UserDetailsServiceImpl.loadUserByUsername
        return getAuthenticationManager()
                .authenticate(new UsernamePasswordAuthenticationToken(userName, password, Collections.emptyList()));
    }
    
    
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException {

        UserDetailsImpl userDetails = (UserDetailsImpl) authResult.getPrincipal();

        System.out.println("JWTLoginFilter.successfulAuthentication: username = " + userDetails.getUsername());

        // Write Authorization to Headers of Response.
        TokenAuthenticationService.addAuthentication(response, userDetails, authResult);
        
        ip = InetAddress.getLocalHost();
        hostname = ip.getHostName();
        logger.info(" \n Người dùng:" +userDetails.getUser().getFullName().toString()+ 
        		"\n Account :"+userDetails.getUsername().toString()+
        		"\n Role :"+userDetails.getUser().getRole().getRoleName().toString()+
        		" \n Địa chỉ IP đăng nhập : " + ip+
        		" \n Hostname : " + hostname+
        		" \n Login thành công" );
        
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) {
        TokenAuthenticationService.unsuccessfulAuthentication(request, response, failed);
//        logger.info("Logout");
    }

}
