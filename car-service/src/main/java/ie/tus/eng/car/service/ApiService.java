package ie.tus.eng.car.service;

import java.time.Duration;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import ie.tus.eng.car.dealership.Dealership;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

@Service
public class ApiService {
	private final WebClient webClient;
	public ApiService(WebClient webClient) {
		this.webClient = webClient;
	}
	public Mono<String> getData(){
		return webClient.get().uri("/data").retrieve().bodyToMono(String.class);
	}
	public Mono<String> createData(Dealership request) {
		return webClient.post().uri("/data").bodyValue(request).retrieve().bodyToMono(String.class);
	}
	public Mono<ResponseEntity<String>> fetchData(){
		return webClient.get().uri("/data").retrieve().toEntity(String.class);
	}
	public Mono<String> fetchDataSafely(){
		return webClient.get()
				.uri("/data")
				.retrieve()
				.onStatus(status -> status.is4xxClientError(), response ->
					Mono.error(new RuntimeException("Client Error: "+response.statusCode())))
				.onStatus(status -> status.is5xxServerError(), response ->
					Mono.error(new RuntimeException("Server Error: "+response.statusCode())))
				.bodyToMono(String.class);
	}
	public Mono<String> fetchDataWithRetry(){
		return webClient.get()
				.uri("/data")
				.retrieve()
				.bodyToMono(String.class)
				.retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(2)));
	}
	public Mono<Dealership> getDealership(long id){
		return webClient.get()
				.uri("/dealerships/{id}",id)
				.retrieve()
				.bodyToMono(Dealership.class);
	}
}



