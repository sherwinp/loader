package common.data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.SecondaryTables;
import javax.persistence.Table;

@Entity
@Table(name = "CITY", schema="PUBLIC")
@SecondaryTables({
@SecondaryTable(name = "STATE", schema="PUBLIC", pkJoinColumns = @PrimaryKeyJoinColumn(name = "STATE_ID")),
@SecondaryTable(name="COUNTRY", schema="PUBLIC", pkJoinColumns = @PrimaryKeyJoinColumn(name = "COUNTRY_ID"))})
public class City implements Serializable {
	
	@Id
	@Column(name="CITY_NAME", table="CITY", insertable=true, updatable=false, length=64)
	String city_name;
	
	@Column(name="STATE_ID", insertable=true, updatable=true)
	Long state_id;
	
	@Column(name="STATE_NAME", table="STATE", insertable=false, updatable=false, length=64)
	String	state_name;

	@Column(name="COUNTRY_ID", insertable=true, updatable=true)
	Long country_id;
	
	@Column(name="COUNTRY_NAME", table="COUNTRY", insertable=false, updatable=false, length=36)
	String  country_name;
	
	private static final long serialVersionUID = -5462509156866391317L;
	
	public City(){}
	
	public City(String name, Long state_id, Long country_id){
		this.state_id=state_id;
		this.country_id = country_id;
		city_name = name;
	}
	public City(HashMap metadata, List<String> values){
		city_name = ns(values.get( (Integer) metadata.get("city_name") -1));
		country_name = ns(values.get( (Integer) metadata.get("country_name") -1));
		//ns(values.get( (Integer) metadata.get("country_iso_code") -1));
		state_name = ns(values.get( (Integer) metadata.get("subdivision_1_name") -1));
		//ns(values.get( (Integer) metadata.get("subdivision_1_iso_code") -1));
	}
	public City(String city_name, String state_name, String state_code, String country_name, String country_iso_code) {
		this.city_name = city_name;
		this.state_name = state_name;
	
		this.country_name = country_name;

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

	public String getState_name() {
		return state_name;
	}
	public void setState_name(String state_name) {
		this.state_name = state_name;
	}

	public String getCity_name() {
		return city_name;
	}
	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}
	@Override 
	public String toString() {
		return "City name: " + this.city_name + " state: " +  this.state_name + " country: " + this.country_name;
	}
}
