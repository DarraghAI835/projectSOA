package ie.tus.eng.car.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="cars")
public class Car {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long carId;
	
	@Column(nullable=false)
	private String make;
	@Column(nullable=false)
	private String model;
	@Column(nullable=false)
	private int age;
	@Column(nullable=false)
	private String colour;
	@Column(nullable=false)
	private Long dealershipId;
	public Car(Long carId, String make, String model, int age, String colour, Long dealershipId) {
		super();
		this.carId = carId;
		this.make = make;
		this.model = model;
		this.age = age;
		this.colour = colour;
		this.dealershipId = dealershipId;
	}
	public Car() {
		super();
	}
	
	
	public Long getCarId() {
		return carId;
	}
	public void setCarId(Long carId) {
		this.carId = carId;
	}
	public String getMake() {
		return make;
	}
	public void setMake(String make) {
		this.make = make;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getColour() {
		return this.colour;
	}
	public void setColour(String colour) {
		this.colour = colour;
	}
	public Long getDealershipId() {
		return dealershipId;
	}
	public void setDealershipId(Long dealershipId) {
		this.dealershipId = dealershipId;
	}
	@Override
	public String toString() {
		return "Car [carId=" + carId + ", make=" + make + ", model=" + model + ", age=" + age + ", Colour=" + colour
				+ ", dealershipId=" + dealershipId + "]";
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	
}
