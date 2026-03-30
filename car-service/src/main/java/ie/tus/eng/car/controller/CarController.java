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
//			
//			//get default engine instance
//			EPServiceProvider engine = EPServiceProviderManager.getDefaultProvider();
//			
//			// tell engine to listen for personEvents
//			engine.getEPAdministrator().getConfiguration().addEventType(getDealership.class);
//			
//			//create event processing language statment
//			// when person arrives get the name and age
//			String epl = "select name from Dealership";
//			EPStatement statement = engine.getEPAdministrator().createEPL(epl);
//			
//			//add a callback function to invoke when a personevent arrives
//			// this gets called when an event arrives
//			statement.addListener((newData,oldData)->{
//				String name = (String) newData[0].get("name");
//				//int age =(int) newData[0].get("age");
//				System.out.println(String.format("Name: %s",name));
//			});
//			// this line sends an event into the engine
//			engine.getEPRuntime().sendEvent(getDealership(car.get().getGarageId()));
//			
//			//get default engine instance
//			EPServiceProvider engine = EPServiceProviderManager.getDefaultProvider();
//			
//			// tell engine to listen for personEvents
//			engine.getEPAdministrator().getConfiguration().addEventType(PersonEvent.class);
//			
//			//create event processing language statment
//			// when person arrives get the name and age
//			String epl = "select name, age from PersonEvent";
//			EPStatement statement = engine.getEPAdministrator().createEPL(epl);
//			
//			//add a callback function to invoke when a personevent arrives
//			// this gets called when an event arrives
//			statement.addListener((newData,oldData)->{
//				String name = (String) newData[0].get("name");
//				int age =(int) newData[0].get("age");
//				System.out.println(String.format("Name: %s, Age: %d",name,age));
//			});
			// this line sends an event into the engine
			//engine.getEPRuntime().sendEvent(new PersonEvent("Peter",10));
			
			
			CarResponse carResponse = new CarResponse(car.get(),dealership);
			return ResponseEntity.ok(carResponse);
		}
	}
	@GetMapping("test/{id}")
	public Mono<ResponseEntity<CarResponse>> retrieveOneCarTest (@PathVariable long id){
		Optional<Car> car = repository.findById(id);
		if (car.isEmpty()) {
			System.out.println("Car not found in database");
			return Mono.just(ResponseEntity.notFound().build());
		}
		else {
			Car carf=car.get();
			return apiservice.getDealership(carf.getGarageId())
					.map(dealership -> new CarResponse(carf, dealership))
					.map(ResponseEntity::ok);
		}
		//Dealership dealership= dealershipClient.getDealershipById(car.getGarageId());
		
				
		//else {
			//Dealership dealership = dealershipClient.getDealershipById(car.get().getGarageId());
			//Dealership dealership = dealershipClientAsic.getDealershipById(car.get().getGarageId());
			
			
			//CarResponse carResponse = new CarResponse(car.get(),dealership);
			//return ResponseEntity.ok(carResponse);
		//}
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
