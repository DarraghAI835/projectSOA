package ie.tus.eng.car.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import ie.tus.eng.car.dealership.Dealership;
import ie.tus.eng.car.dealership.DealershipClient;
import ie.tus.eng.car.model.Car;
import ie.tus.eng.car.model.CarResponse;
import ie.tus.eng.car.repository.CarRepository;

@RestController
@RequestMapping("/cars")
public class CarController {
	private final CarRepository repository;
	private DealershipClient dealershipClient;
	
	public CarController(CarRepository repository,DealershipClient dealershipClient) {
		this.repository=repository;
		this.dealershipClient =dealershipClient;
	}
	@GetMapping
	public List<Car> retrieveAllCars(){
		return repository.findAll();
	}
	@GetMapping("/{id}")
	public ResponseEntity<CarResponse> retrieveOneCar(@PathVariable long id){
		Optional<Car> car = repository.findById(id);
		if (car.isEmpty()) {
			System.out.println("Car not found in database");
			return ResponseEntity.notFound().build();
		}
		else {
			Dealership dealership = dealershipClient.getDealershipById(car.get().getGarageId());
			CarResponse carResponse = new CarResponse(car.get(),dealership);
			return ResponseEntity.ok(carResponse);
		}
	}
	//POST
	@PostMapping
	public ResponseEntity<Car> createStudent(@RequestBody Car car){
		Car savedCar = repository.save(car);
		// get the URI of the created car
		URI location = ServletUriComponentsBuilder
			.fromCurrentRequest() // gets the '/car' part of the URI
			.path("/{id}") // add the 'id' part to the path
			.buildAndExpand(savedCar.getCarId()) // build and fill in the actual id
			.toUri();  // convert to URI object

			// return response status = 201 (created),along with the location
			return ResponseEntity.created(location).build();
	}
	//edit the car
	//PUT
	@PutMapping("/{id}")
	public ResponseEntity<Car> updateDealership(@RequestBody Car car){			
		Optional<Car> updatedCar =repository.findById(car.getCarId());
		if(updatedCar.isEmpty()) {
			System.out.println("Car not found in database");
			return ResponseEntity.notFound().build();
		}else {
			Car editCar = repository.save(car);
			return ResponseEntity.ok(editCar);			
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteCar(@PathVariable long id) {
		Optional<Car> car = repository.findById(id);
		if (car.isEmpty()) {
			return ResponseEntity.notFound().build();
		} else {
			repository.deleteById(id);
			return ResponseEntity.noContent().build();
		}
	}
	// Delete all
	@DeleteMapping
	public ResponseEntity<Void> deleteAllCars(){
		repository.deleteAll();
		return ResponseEntity.noContent().build(); 
	}
}
