package ie.tus.eng.dealership.controller;

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

import ie.tus.eng.dealership.model.Dealership;
import ie.tus.eng.dealership.repository.DealershipRepository;

@RestController
@RequestMapping("/dealerships")
public class DealershipController {
	private final DealershipRepository repository;

	public DealershipController(DealershipRepository repository) {
		this.repository = repository;
	}

	@GetMapping
	public List<Dealership> retrieveAllDealerships() {
		return repository.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Dealership> retrieveOneDealership(@PathVariable long id) {
		Optional<Dealership> dealership = repository.findById(id);

		if (dealership.isEmpty()) {
			System.out.println("Dealership is not in database");
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(dealership.get());
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteDealership(@PathVariable long id) {
		Optional<Dealership> dealership = repository.findById(id);
		if (dealership.isEmpty()) {
			return ResponseEntity.notFound().build();
		} else {
			repository.deleteById(id);
			return ResponseEntity.noContent().build();
		}
	}
	// Delete all
	@DeleteMapping
	public ResponseEntity<Void> deleteAllDealerships(){
		repository.deleteAll();
		return ResponseEntity.noContent().build(); 
	}
	// create Dealership
	//POST
	@PostMapping
	public ResponseEntity<Dealership> createStudent(@RequestBody Dealership dealership){
		Dealership savedDealership = repository.save(dealership);
		// get the URI of the created dealership
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest() // gets the '/dealership' part of the URI
				.path("/{id}") // add the 'id' part to the path
				.buildAndExpand(savedDealership.getDealershipId()) // build and fill in the actual id
				.toUri();  // convert to URI object

		// return response status = 201 (created),along with the location
		return ResponseEntity.created(location).build();
	}
	//edit the dealership
	//PUT
	@PutMapping("/{id}")
	public ResponseEntity<Dealership> updateDealership(@RequestBody Dealership dealership){
		Optional<Dealership> updatedDealership =repository.findById(dealership.getDealershipId());
		if(updatedDealership.isEmpty()) {
			System.out.println("Dealership not found in database");
			return ResponseEntity.notFound().build();
		}else {
			Dealership editDealership = repository.save(dealership);
			return ResponseEntity.ok(editDealership);
		}
	}

}
