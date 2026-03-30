package ie.tus.eng.car.events;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import ie.tus.eng.car.dealership.Dealership;

//@FeignClient(name="dealership-service",url="http://localhost:8081")
@FeignClient(name="dealership-service",url="${dealership.service.url}")
public class getDealership(long dealership_id) {
	@GetMapping("/dealerships/{dealership_id}")
	Dealership getDealershipById(@PathVariable long dealership_id);
}
