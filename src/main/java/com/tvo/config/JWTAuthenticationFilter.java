package com.tvo.config;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.GenericFilterBean;

import com.tvo.common.AppConstant;
import com.tvo.model.UserDetailsImpl;

import io.jsonwebtoken.Jwts;

public class JWTAuthenticationFilter extends GenericFilterBean {

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {

		System.out.println("JWTAuthenticationFilter.doFilter");

		// Check JWT
		Authentication authentication = TokenAuthenticationService
				.getAuthentication((HttpServletRequest) servletRequest);
		// Authentication\
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		try {
			String jwt;
			jwt = extractAndDecodeJwt(request);
			Authentication auth = buildAuthenticationFromJwt(jwt, request);
			SecurityContextHolder.getContext().setAuthentication(auth);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		filterChain.doFilter(servletRequest, servletResponse);
	}

	private Authentication buildAuthenticationFromJwt(String jwt, HttpServletRequest request) throws ParseException {

		// jwt from request-- > lay ra user name, role, time expired.
		String userName = Jwts.parser().setSigningKey(AppConstant.SECRET).parseClaimsJws(jwt).getBody().getSubject();
		
		List<GrantedAuthority> authorities = null;// JwtUtils.getRoles(jwt); Role tu jwt
		Date creationDate = null;// getIssueTime(jwt); // Thoi gian expired tu jwt

		UserDetailsImpl userDetails = new UserDetailsImpl(userName, creationDate, authorities);
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
				authorities);
		
		authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		return authentication;
	}

	private String extractAndDecodeJwt(HttpServletRequest request) throws ParseException {
		String authHeader = request.getHeader(AppConstant.HEADER_STRING);
		String token = authHeader.substring("Bearer ".length());
		return token;
	}

}
