package common.data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CITY", schema="PUBLIC")
public class City implements Serializable {
	
	@Id
	@Column(name="CITY_NAME", table="CITY", insertable=true, updatable=false, length=64)
	String city_name;
	@Column(name="STATE_NAME", table="CITY", insertable=true, updatable=true, length=64)
	String	state_name;
	@Column(name="STATE_CODE", table="CITY", insertable=true, updatable=true, length=3)
	String	state_code;
	@Column(name="COUNTRY_NAME", table="CITY", insertable=true, updatable=true, length=36)
	String  country_name;
	@Column(name="COUNTRY_CODE", table="CITY", insertable=true, updatable=true, length=3)
	String  country_iso_code;


	
	private static final long serialVersionUID = -5462509156866391317L;
	
	public City(){}
	
	public City(HashMap metadata, List<String> values){
		city_name = ns(values.get( (Integer) metadata.get("city_name") -1));
		country_name = ns(values.get( (Integer) metadata.get("country_name") -1));
		country_iso_code = ns(values.get( (Integer) metadata.get("country_iso_code") -1));
		state_name = ns(values.get( (Integer) metadata.get("subdivision_1_name") -1));
		state_code = ns(values.get( (Integer) metadata.get("subdivision_1_iso_code") -1));
	}
	public City(String city_name, String state_name, String country_name, String country_iso_code) {
		this.city_name = city_name;
		this.state_name = state_name;
		this.country_name = country_name;
		this.country_iso_code = country_iso_code;
	}
	String ns(Object value){
		return String.format("%s", value);
	}
	public Boolean valid(){
		return city_name != null && !city_name.isEmpty() && state_name != null && !state_name.isEmpty();
	}
	public String getCountry_name() {
		return country_name;
	}
	public void setCountry_name(String country_name) {
		this.country_name = country_name;
	}
	public String getCountry_iso_code() {
		return country_iso_code;
	}
	public void setCountry_iso_code(String country_iso_code) {
		this.country_iso_code = country_iso_code;
	}
	public String getState_name() {
		return state_name;
	}
	public void setState_name(String state_name) {
		this.state_name = state_name;
	}
	public String getState_code() {
		return state_code;
	}
	public void setState_code(String state_code) {
		this.state_code = state_code;
	}
	public String getCity_name() {
		return city_name;
	}
	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}
	@Override 
	public String toString() {
		return "City name: " + this.city_name + " state: " +  this.state_name + " country: " +  this.country_iso_code;
	}
}
