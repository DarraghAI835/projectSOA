package ie.tus.eng.car.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ie.tus.eng.car.dealership.DealershipClient;
import ie.tus.eng.car.etag.EtagGenerator;
import ie.tus.eng.car.model.Car;
import ie.tus.eng.car.model.CarResponse;
import ie.tus.eng.car.repository.CarRepository;
import ie.tus.eng.car.service.ApiService;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/cars")
public class CarController {
	private final CarRepository repository;
	private DealershipClient dealershipClient;
	private ApiService apiservice;
	
	public CarController(CarRepository repository,DealershipClient dealershipClient,ApiService apiservice) {
		this.repository=repository;
		this.dealershipClient =dealershipClient;
		this.apiservice=apiservice;
	}
//	@GetMapping
//	public List<Car> retrieveAllCars(){
//		return repository.findAll();
//	}
//	@GetMapping
//	public ResponseEntity<List<Car>> retrieveAllCarsEtags(){
//		List<Car> cars = repository.findAll();
//		String etag = Integer.toString(cars.hashCode());
//		return ResponseEntity.ok().eTag(etag).body(cars);
//	}
	@GetMapping
	public ResponseEntity<List<Car>> retrieveAllCars(@RequestHeader(value =HttpHeaders.IF_NONE_MATCH,required=false) String ifNoneMatch) throws Exception{
		List<Car> cars = repository.findAll();
		String carsString =cars.stream().map(car->car.toString()).collect(Collectors.joining(","));
		String etag = EtagGenerator.generate(carsString);

        HttpHeaders headers = new HttpHeaders();
        //ResponseEntity.ETag(etag);
        if(etag.equals(ifNoneMatch)) {
        	return ResponseEntity.status(HttpStatus.NOT_MODIFIED).eTag(etag).build();
        }
        if (ifNoneMatch != null) {
        	for(String candidate:ifNoneMatch.split(",")) {
        		if(etag.equals(candidate.trim())) {
        			return ResponseEntity.status(HttpStatus.NOT_MODIFIED).eTag(etag).build();
        		}
        	}
        }
        return new ResponseEntity<List<Car>>(cars, headers, HttpStatus.OK);
    }
	@GetMapping("/{id}")
	public Mono<ResponseEntity<CarResponse>> retrieveOneCar (@PathVariable long id){
//		Optional<Car> car = repository.findById(id);
//		if (car.isEmpty()) {
//			System.out.println("Car not found in database");
//			return Mono.just(ResponseEntity.notFound().build());
//		}
//		else {
		//Car carf=car.get();
		return apiservice.getCarById(id);//carf.getCarId());
//		}
	}
	//POST
	@PostMapping
	public Mono<ResponseEntity<Car>> createCar (@RequestBody Car car){
		return apiservice.createCar(car);
	}
	//edit the car
	//PUT
	@PutMapping("/{id}")
	public Mono<ResponseEntity<Car>> updateCar(@RequestBody Car car){			
		Optional<Car> updatedCar =repository.findById(car.getCarId());
		if(updatedCar.isEmpty()) {
			System.out.println("Car not found in database");
			return Mono.just(ResponseEntity.notFound().build());
		}else {
			Car editCar = repository.save(car);
			return Mono.just(ResponseEntity.ok(editCar));	
		}
	}
	@DeleteMapping("/{id}")
	public Mono<ResponseEntity<Void>> deleteCar(@PathVariable long id) {
		Optional<Car> car = repository.findById(id);
		if (car.isEmpty()) {
			return Mono.just(ResponseEntity.notFound().build());
		} else {
			repository.deleteById(id);
			return Mono.just(ResponseEntity.noContent().build());
		}
	}
	// Delete all
	@DeleteMapping
	public Mono<ResponseEntity<Void>> deleteAllCars(){
		repository.deleteAll();
		return Mono.just(ResponseEntity.noContent().build()); 
	}
}
