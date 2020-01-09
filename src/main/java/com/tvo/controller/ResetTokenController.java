package com.tvo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tvo.common.AppConstant;
import com.tvo.config.TokenAuthenticationService;
import com.tvo.dto.UserResDto;
import com.tvo.response.ResponeData;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

import java.util.Collections;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import com.tvo.model.UserDetailsImpl;

@RestController
@RequestMapping(value = "/token")
public class ResetTokenController {
	@Autowired
	TokenAuthenticationService tokenAuthenticationService;
	
	@GetMapping(value = "/reset")
    public static String reset(@RequestParam String token) {
//		String token = request.getHeader(AppConstant.HEADER_STRING);
        if (token != null) {
            // parse the token.
            try {
                String user = Jwts.parser()
                        .setSigningKey(AppConstant.SECRET)
                        .parseClaimsJws(token)
                        .getBody()
                        .getSubject();
                
                String JWT = Jwts.builder().setSubject(user)
                        .setExpiration(new Date(System.currentTimeMillis() + AppConstant.EXPIRATION_TIME_MS))
                        .signWith(SignatureAlgorithm.HS512, AppConstant.SECRET).compact();
                UserResDto userDto = new UserResDto();
                userDto.setToken(JWT);
                
                
                return JWT;
            } catch (ExpiredJwtException e) {
                System.out.println("ExpiredJwtException");
            } catch (SignatureException e) {
                System.out.println("SignatureException");
            } catch (Exception e) {
                System.out.println("JWT parsing error");
            }
            
            
        }
        return null;
        
    }
}
