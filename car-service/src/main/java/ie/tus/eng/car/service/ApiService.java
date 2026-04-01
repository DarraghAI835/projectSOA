package ie.tus.eng.car.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import ie.tus.eng.car.dealership.Dealership;
import ie.tus.eng.car.model.Car;
import ie.tus.eng.car.model.CarResponse;
import ie.tus.eng.car.repository.CarRepository;
import reactor.core.publisher.Mono;

@Service
public class ApiService {
	@Autowired 
	private CarRepository repository;
	@Autowired
    private ModelMapper mapper;
	
    @Autowired
    private WebClient webClient;
    
    public Mono<ResponseEntity<CarResponse>> getCarById(long id) {

        Optional<Car> carO = repository.findById(id);
        
        if (carO.isEmpty()) {
        	return Mono.just(ResponseEntity.notFound().build());
        }
        Car car = carO.get();
        
        
        CarResponse CarResponse = mapper.map(car, CarResponse.class);
        
        return webClient.get()
			.uri("/dealerships/{id}",id)
			.retrieve()
			.bodyToMono(Dealership.class)
			.map(dealership -> {
				CarResponse response = new CarResponse(car,dealership);
				return ResponseEntity.ok(response);
			});
        
        // Using WebClient
    }
    public Mono<ResponseEntity<Car>> createCar(Car car) {
		return webClient.post().uri("/cars").bodyValue(car).retrieve().bodyToMono(Car.class).map(ResponseEntity::ok);
	}
    
	
	
	
	
	
	
//	private final WebClient webClient;
//	public ApiService(WebClient webClient) {
//		this.webClient = webClient;
//	}
//	public Mono<String> getData(){
//		return webClient.get().uri("/data").retrieve().bodyToMono(String.class);
//	}
//	public Mono<String> createData(Dealership request) {
//		return webClient.post().uri("/data").bodyValue(request).retrieve().bodyToMono(String.class);
//	}
//	public Mono<ResponseEntity<String>> fetchData(){
//		return webClient.get().uri("/data").retrieve().toEntity(String.class);
//	}
//	public Mono<String> fetchDataSafely(){
//		return webClient.get()
//				.uri("/data")
//				.retrieve()
//				.onStatus(status -> status.is4xxClientError(), response ->
//					Mono.error(new RuntimeException("Client Error: "+response.statusCode())))
//				.onStatus(status -> status.is5xxServerError(), response ->
//					Mono.error(new RuntimeException("Server Error: "+response.statusCode())))
//				.bodyToMono(String.class);
//	}
//	public Mono<String> fetchDataWithRetry(){
//		return webClient.get()
//				.uri("/data")
//				.retrieve()
//				.bodyToMono(String.class)
//				.retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(2)));
//	}
	public Mono<Dealership> getDealership(long id){
		return webClient.get()
				.uri("/dealerships/{id}",id)
				.retrieve()
				.bodyToMono(Dealership.class);
	}
}



