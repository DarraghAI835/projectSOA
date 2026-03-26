package ie.tus.eng.car.dealership;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient(name="dealership-service",url="http://localhost:8081")
@FeignClient(name="dealership-service",url="${dealership.service.url}")
public interface DealershipClient {
	@GetMapping("/dealerships/{dealership_id}")
	Dealership getDealershipById(@PathVariable long dealership_id);
}
