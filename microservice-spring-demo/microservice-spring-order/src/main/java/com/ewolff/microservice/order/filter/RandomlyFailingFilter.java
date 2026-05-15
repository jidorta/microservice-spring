package com.ewolff.microservice.order.filter;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class RandomlyFailingFilter implements Filter {

	@Value("${failrandomly:false}")
	private boolean failRandomly = false;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

        boolean shouldFail = ThreadLocalRandom.current().nextDouble() > 0.5D;

		if ((shouldFail) || (!failRandomly)) {
			chain.doFilter(request, response);
		} else {
			log.trace("Made HTTP Request fail with 500");
			((HttpServletResponse) response).sendError(500);
		}
	}

}
