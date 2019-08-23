/**
 * 
 */
package com.tvo.config;

import com.tvo.model.User;
import com.tvo.service.AuditorAwareImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * @author Ace
 *
 */
@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class JpaConfig {
	@Bean
	public AuditorAware<User> auditorAware() {
		return new AuditorAwareImpl();
	}
}
