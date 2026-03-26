package ie.tus.eng.car.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ie.tus.eng.car.model.Car;

public interface CarRepository extends JpaRepository<Car,Long> {
	//Car findByMake(String make);
}
