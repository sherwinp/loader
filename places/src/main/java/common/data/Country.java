package common.data;

import common.data.State;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the COUNTRY_LIST database table.
 *
 */
@Entity
@Table(name = "COUNTRY", schema="PUBLIC")
public class Country implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -3873822518506356136L;

    @Id
    @SequenceGenerator(name = "COUNTRY_ID_GENERATOR")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COUNTRY_ID_GENERATOR")
    @Column(name = "COUNTRY_ID")
    private long id;

    @Column(name = "COUNTRY_CODE")
    private String countryCode;

    @Column(name = "COUNTRY_NAME")
    private String countryName;
    
    //bi-directional many-to-one association to StateList
    @OneToMany(mappedBy = "country")
    private List<State> states;

    public Country() {
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCountryCode() {
        return this.countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryName() {
        return this.countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public List<State> getStates() {
        return states;
    }

    public void setStates(List<State> states) {
        this.states = states;
    }

}
