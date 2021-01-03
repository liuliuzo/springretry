package com.liuliu.demo.springretry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.CircuitBreaker;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.annotation.Recover;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableRetry
public class CircuitBreakerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CircuitBreakerApplication.class, args);
	}
}

@RestController
class ShakyRestController {
	private final ShakyBusinessService shakyBusinessService;

	@Autowired
	public ShakyRestController(ShakyBusinessService shakyBusinessService) {
		this.shakyBusinessService = shakyBusinessService;
	}

	@GetMapping("/boom")
	public int boom() throws Exception {
		return this.shakyBusinessService.desireNumber();
	}

}

class BoomException extends RuntimeException {
	public BoomException(String message) {
		super(message);
	}
}

@Service
class ShakyBusinessService {

	@Recover
	public int fallback(BoomException ex) {
		return 2;
	}

	@CircuitBreaker(include = BoomException.class, openTimeout = 20000L, resetTimeout = 5000L, maxAttempts = 1)
	public int desireNumber() throws Exception {
		System.out.println("calling desireNumber()");
		if (Math.random() > .5) {
			throw new BoomException("Boom");
		}
		return 1;
	}
}