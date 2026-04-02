package ie.tus.eng.car.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import ie.tus.eng.car.dealership.Dealership;

@JsonPropertyOrder({
	"carId",
	"make",
	"model",
	"age",
	"colour",
	"dealershipId"
})
	


public class CarResponse {
	
	
	public CarResponse() {
		super();
	}
	public CarResponse(Car car,Dealership dealership) {
		super();
		this.carId = car.getCarId();
		this.make = car.getMake();
		this.model = car.getModel();
		this.age = car.getAge();
		this.colour = car.getColour();
		this.dealershipId = dealership;
	}
	private Long carId;
	private String make;
	private String model;
	private int age;
	private String colour;
	private Dealership dealershipId;
	public long getCarId() {
		return carId;
	}
	public void setId(long carId) {
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
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getColour() {
		return colour;
	}
	public void setColour(String colour) {
		this.colour = colour;
	}
	public Dealership getDealershipId() {
		return dealershipId;
	}
	public void setDealershipId(Dealership dealershipId) {
		this.dealershipId = dealershipId;
	}
	@Override
	public String toString() {
		return "CarResponse [car ID=" + carId + ", make=" + make + ", model=" + model + ", age=" + age + ", colour=" + colour
				+ ", dealershipId=" + dealershipId + "]";
	}
}
