package ie.tus.eng.dealership.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="dealerships")
public class Dealership {
	public Dealership() {
		super();
	}
	public Dealership(Long dealershipId, String name, String address, String county, int phone, String owner) {
		super();
		this.dealershipId = dealershipId;
		this.name = name;
		this.address = address;
		this.county = county;
		this.phone = phone;
		this.owner = owner;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long dealershipId;
	
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private String address;
	@Column(nullable = false)
	private String county;
	@Column(nullable = false)
	private int phone;
	@Column(nullable = false)
	private String owner;
	@Override
	public String toString() {
		return "Dealership [dealershipId=" + dealershipId + ", name=" + name + ", address=" + address + ", county="
				+ county + ", phone=" + phone + ", owner=" + owner + "]";
	}
	public Long getDealershipId() {
		return dealershipId;
	}
	public void setDealershipId(Long dealershipId) {
		this.dealershipId = dealershipId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	public int getPhone() {
		return phone;
	}
	public void setPhone(int phone) {
		this.phone = phone;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
}
