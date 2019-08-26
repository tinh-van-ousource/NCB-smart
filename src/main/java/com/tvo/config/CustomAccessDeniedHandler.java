/**
 * 
 */
package com.tvo.config;

import com.google.gson.Gson;
import com.tvo.common.AppConstant;
import com.tvo.response.ResponeData;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author Ace
 *
 */
public class CustomAccessDeniedHandler  implements AccessDeniedHandler {
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
		try {
			Gson gson= new Gson();
			ResponeData<String> respLogin = new ResponeData<String>(AppConstant.ACCESS_DENIED_CODE, AppConstant.ACCESS_DENIED_STATUS);
			response.getWriter().write(gson.toJson(respLogin));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
