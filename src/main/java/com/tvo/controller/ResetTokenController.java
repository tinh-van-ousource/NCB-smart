package com.tvo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.tvo.common.AppConstant;
import com.tvo.common.ModelMapperUtils;
import com.tvo.config.TokenAuthenticationService;
import com.tvo.dao.UserRepo;
import com.tvo.dto.UserResDto;
import com.tvo.response.ResponeData;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import com.tvo.model.User;
import com.tvo.model.UserDetailsImpl;

@RestController
@RequestMapping(value = "/token")
public class ResetTokenController {
	@Autowired
	TokenAuthenticationService tokenAuthenticationService;

	private static UserRepo userRepo;

	private static Gson gson;

	public ResetTokenController(ApplicationContext ctx) {
	        userRepo = ctx.getBean(UserRepo.class);
	    }

	@GetMapping(value = "/reset")
	public static UserResDto reset(@RequestParam String token) {
//		String token = request.getHeader(AppConstant.HEADER_STRING);
		if (token != null) {
			// parse the token.
			try {
				String userName = Jwts.parser().setSigningKey(AppConstant.SECRET).parseClaimsJws(token).getBody()
						.getSubject();

				String JWT = Jwts.builder().setSubject(userName)
						.setExpiration(new Date(System.currentTimeMillis() + AppConstant.EXPIRATION_TIME_MS))
						.signWith(SignatureAlgorithm.HS512, AppConstant.SECRET).compact();
				User user = userRepo.findByUserName(userName.toString());
				user.setCountLoginFail(0);
				user = userRepo.save(user);
				 System.out.println("JWT new = " + JWT);

				gson = new Gson();
				UserResDto userDto = ModelMapperUtils.map(user, UserResDto.class);
				userDto.setToken(JWT);
				ResponeData respLogin = new ResponeData(AppConstant.SYSTEM_SUCCESS_CODE,
						AppConstant.LOGIN_SUCCESSFUL_MESSAGE, userDto);
				return userDto;

//                return JWT;
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
