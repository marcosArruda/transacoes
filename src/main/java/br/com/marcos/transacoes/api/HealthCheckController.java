package br.com.marcos.transacoes.api;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/health-check")
public class HealthCheckController {

	@GetMapping
	private Mono<ResponseEntity<String>> showHealth(){
		return Mono.just(new ResponseEntity<>("OK", HttpStatus.OK));
	}
}
