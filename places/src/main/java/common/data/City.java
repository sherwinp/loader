package common.data;

import java.io.Serializable;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.SequenceGenerator;
import javax.persistence.UniqueConstraint;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name = "CITY", schema="PUBLIC", uniqueConstraints= {@UniqueConstraint(name="CITY_X1", columnNames={"CITY_NM", "ST_ID"})})
public class City implements Serializable {
	
	@Id
    @SequenceGenerator(name = "CITY_ID_GENERATOR")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CITY_ID_GENERATOR")
	@Column(name="CITY_ID", table="CITY", insertable=true, updatable=false)
	Long city_id;
	
	@Column(name="ACTV_CD", table="CITY", insertable=false, updatable=true, nullable=false, columnDefinition="VARCHAR(1) DEFAULT 'A'")
	String activeCode;
	
	@Column(name="CITY_NM", table="CITY", insertable=true, updatable=false,  length=64)
	String cityName;
	
	@Column(name="ST_ID", table="CITY", insertable=true, updatable=false)
	Long state_id;
	
	@Column(name="CTRY_ID", table="CITY", insertable=true, updatable=false)
	Long country_id;

	@Column(name="ETL_DTTM", table="CITY", insertable=false, updatable=true, nullable=false, columnDefinition="DATE DEFAULT CURRENT_TIMESTAMP")
	@Temporal(TemporalType.TIMESTAMP)
	Calendar etl_dttm;
	
	@Transient
	String stateName;
	@Transient
	String countryName;
	
	private static final long serialVersionUID = -5462509156866391317L;
	
	public City(){}
	
	public City(String name, Long state_id, Long country_id){
		this.state_id=state_id;
		this.country_id = country_id;
		cityName = name;
	}
	public City(HashMap metadata, List<String> values){
		cityName = ns(values.get( (Integer) metadata.get("city_name") -1));
		countryName = ns(values.get( (Integer) metadata.get("country_name") -1));
		//ns(values.get( (Integer) metadata.get("country_iso_code") -1));
		stateName = ns(values.get( (Integer) metadata.get("subdivision_1_name") -1));
		//ns(values.get( (Integer) metadata.get("subdivision_1_iso_code") -1));
	}
	public City(String city_name, String state_name, String state_code, String country_name, String country_iso_code) {
		this.cityName = city_name;
		this.stateName = state_name;
		this.countryName = country_name;
	}
	String ns(Object value){
		return String.format("%s", value);
	}
	public Boolean valid(){
		return cityName != null && !cityName.isEmpty() && stateName != null && !stateName.isEmpty();
	}
	public Long getId() {
		return city_id;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String country_name) {
		this.countryName = country_name;
	}

	public String getStateName() {
		return stateName;
	}
	public void setStateName(String state_name) {
		this.stateName = state_name;
	}

	public String getCityName() {
		return cityName;
	}
	
	public void setCityName(String city_name) {
		this.cityName = city_name;
	}
	
	public Long getStateId() {
		return this.state_id;
	}
	public void setStateId(Long id) {
		this.state_id = id;
	}
	public void setState(State state) {
		this.state_id = state.getId();
		///@ToDo: check name		
		this.stateName.equals(state.getStateName());
	}
	
	public Long getCountryId() {
		return this.country_id;
	}
	public void setCountryId(Long id) {
		this.country_id = id;
	}	
	public void setCountry(Country country) {
		this.country_id = country.getId();
		///@ToDo: check name
		countryName.equals(country.getCountryName());
	}
	
	@Override 
	public String toString() {
		return "City name: " + this.cityName + " state: " +  this.stateName + " country: " + this.countryName;
	}
}
