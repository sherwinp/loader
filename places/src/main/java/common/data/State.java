package common.data;

import common.data.Country;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Calendar;
import java.util.List;

/**
 * The persistent class for the STATE_LIST database table.
 *
 */
@Entity
@Table(name = "STATE", schema="CTPAT2")
public class State implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 4394851791620990450L;

    @Id
    @Column(name = "ID", table="STATE", insertable=true, updatable=false)
    private long id;
    
    @Column(name = "STATE_CODE", table="STATE", insertable=true, updatable=false, length=3)
    private String stateCode;
    
    @Column(name = "CTRY_SBDVSN_CD", table="STATE", insertable=true, updatable=true, length=3)
    private String subDivisionCode;
    

    @Column(name = "STATE_NAME", table="STATE", insertable=true, updatable=false)
    private String stateName;

    //bi-directional many-to-one association to CountryList
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COUNTRY_ID", table="STATE", insertable=true, updatable=false)
    private Country country;

	@Column(name="ETL_DTTM", table="STATE", insertable=true, updatable=true, columnDefinition="DATE DEFAULT CURRENT_TIMESTAMP")
	@Temporal(TemporalType.TIMESTAMP)
	Calendar etl_dttm;
	
    public State() {
    }
    public State(String name, Country country) {
    	stateName = name;
    	this.country = country;
    }
    public State(String name, String code, Country country) {
    	stateName = name;
    	stateCode = code;
    	this.subDivisionCode = stateCode;
    	this.country = country;
    }
    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStateCode() {
        return this.stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
        this.subDivisionCode = this.stateCode;
    }

    public String getStateName() {
        return this.stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public Country getCountry() {
        return this.country;
    }

    public void setCountry(Country countryList) {
        this.country = countryList;
    }

}
