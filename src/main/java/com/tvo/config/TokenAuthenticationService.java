/**
 *
 */
package com.tvo.config;

import com.google.gson.Gson;
import com.tvo.common.AppConstant;
import com.tvo.common.ModelMapperUtils;
import com.tvo.dao.UserRepo;
import com.tvo.dto.UserResDto;
import com.tvo.model.User;
import com.tvo.model.UserDetailsImpl;
import com.tvo.response.ResponeData;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Date;

/**
 * @author Ace
 *
 */
@Service
public class TokenAuthenticationService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
    private static UserRepo userRepo;

    private static Gson gson;

    public TokenAuthenticationService(ApplicationContext ctx) {
        userRepo = ctx.getBean(UserRepo.class);
    }

    public static void unsuccessfulAuthentication(HttpServletRequest req, HttpServletResponse res,
                                                  AuthenticationException failed) {
        res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        res.setContentType(MediaType.APPLICATION_JSON_VALUE);
        res.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        try {
            gson = new Gson();
            ResponeData respLogin;

            if (failed instanceof DisabledException) {
                respLogin = new ResponeData(AppConstant.ACCOUNT_DEACTIVATED_CODE, failed.getMessage());
            } else {
                respLogin = new ResponeData(AppConstant.LOGIN_FAILURE_CODE, failed.getMessage());
            }

            res.getWriter().write(gson.toJson(respLogin));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addAuthentication(HttpServletResponse res, UserDetailsImpl userDetails, Authentication authResult) {
        String JWT = Jwts.builder().setSubject(userDetails.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + AppConstant.EXPIRATION_TIME_MS))
                .signWith(SignatureAlgorithm.HS512, AppConstant.SECRET).compact();
        
        try {
            String userName = ((UserDetails) authResult.getPrincipal()).getUsername();
            User user = userRepo.findByUserName(userName);
            user.setCountLoginFail(0);
            user = userRepo.save(user);
            
            Flag.userFlag = user;
            
            gson = new Gson();
            UserResDto userDto = ModelMapperUtils.map(user, UserResDto.class);
//            logger.info("Xóa Chi nhánh");
            userDto.setToken(JWT);
            ResponeData respLogin = new ResponeData(
                    AppConstant.SYSTEM_SUCCESS_CODE,
                    AppConstant.LOGIN_SUCCESSFUL_MESSAGE,
                    userDto);

            res.setContentType("application/json");
            res.setCharacterEncoding("UTF-8");
            res.getWriter().write(gson.toJson(respLogin));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(AppConstant.HEADER_STRING);
        if (token != null) {
            // parse the token.
            try {
                String user = Jwts.parser()
                        .setSigningKey(AppConstant.SECRET)
                        .parseClaimsJws(token)
                        .getBody()
                        .getSubject();

                return user != null
                        ? new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList())
                        : null;
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
