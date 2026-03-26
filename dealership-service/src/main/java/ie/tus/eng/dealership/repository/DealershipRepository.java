package ie.tus.eng.dealership.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ie.tus.eng.dealership.model.Dealership;


public interface DealershipRepository extends JpaRepository<Dealership, Long> {
	Dealership findByName(String name);

	//Dealership findByDealershipId(long id);

	Optional<Dealership> findByDealershipId(long id);
	
}
