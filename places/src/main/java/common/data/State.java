package common.data;

import common.data.Country;

import java.io.Serializable;
import javax.persistence.*;
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
    @SequenceGenerator(name = "STATE_ID_GENERATOR")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STATE_ID_GENERATOR")
    @Column(name = "ID")
    private long id;
    
    @Column(name = "STATE_CODE")
    private String stateCode;

    @Column(name = "STATE_NAME")
    private String stateName;

    //bi-directional many-to-one association to CountryList
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COUNTRY_ID")
    private Country country;

    public State() {
    }
    public State(String name, Country country) {
    	stateName = name;
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
