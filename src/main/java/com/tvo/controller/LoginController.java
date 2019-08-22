/**
 * 
 */
package com.tvo.controller;

import com.tvo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tvo.model.User;
import com.tvo.service.UserServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author Ace
 *
 */
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "login Controller")
public class LoginController {
	@Autowired
	UserService userService;
	
	@ApiOperation(value = "login api")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successssss", response = User.class),
			@ApiResponse(code = 422, message = "Student not found"),
			@ApiResponse(code = 417, message = "Exception failed") })
	@PostMapping(value = "/login")
	public void loginUrl(@RequestBody User user) {
	}
}
