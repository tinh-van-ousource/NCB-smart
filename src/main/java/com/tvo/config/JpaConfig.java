/**
 * 
 */
package com.tvo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.tvo.service.AuditorAwareImpl;

/**
 * @author Ace
 *
 */
//@Configuration
//@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class JpaConfig {
//	@Bean
//	public AuditorAware<Long> auditorAware() {
//		return new AuditorAwareImpl();
//	}
}
