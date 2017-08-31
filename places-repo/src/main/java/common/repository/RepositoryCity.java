package common.repository;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.stereotype.Repository;

import common.data.Utility;
import common.data.City;
import common.data.Country;
import common.data.State;

@Repository
public interface RepositoryCity extends JpaRepository<City, String> {

}

class RepositoryOfCity{
	private final static Logger LOGGER = Logger.getLogger(RepositoryOfCity.class.getName());

	static void load(List<City> collection) throws IOException{
	EntityManagerFactory emf = Utility.Initialize();
	EntityManager em = emf.createEntityManager();
	TypedQuery<State> stateQuery = em.createQuery("SELECT s FROM State s WHERE stateName like ?1", State.class);
	TypedQuery<Country> countryQuery = em.createQuery("SELECT c FROM Country c WHERE countryName like ?1", Country.class);
	TypedQuery<City> cityQuery = em.createQuery("SELECT cty FROM City cty WHERE cityName like ?1 AND state_id=?2", City.class);
	Long idCounter = 1L;
	for( City aCity: collection ){
		try{
			if(aCity.valid()){
				
				Country country = null;
				State state = null;
				em.getTransaction().begin();
				
				LOGGER.info("Loading City: " + aCity.toString());
				
				countryQuery.setParameter(1, aCity.getCountryName());
				List<Country> countryList = countryQuery.getResultList(); 
				if( countryList.size() > 1 )
					throw( new IllegalStateException("Country Mismatch, more than one.") );

				if( countryList.isEmpty()) {
					em.getTransaction().rollback();
					continue;
					//country = em.merge(new Country(aCity.getCountryName()));
				} else {
					country = countryList.get(0);
				}
				
				stateQuery.setParameter(1, aCity.getStateName());
				List<State> stateList = stateQuery.getResultList();
				if( stateList.size() > 1 )
					throw( new IllegalStateException("State Mismatch, more than one.") );

				if( stateList.isEmpty() ) {
					em.getTransaction().rollback();
					continue;
					//state = em.merge(new State(aCity.getStateName(), country));
				} else {
					state = stateList.get(0);				
				}

				cityQuery.setParameter(1, aCity.getCityName());
				cityQuery.setParameter(2, state.getId());
				List<City> cityList = cityQuery.getResultList();
				if( cityList.size() > 0 ) {
					/// already in database possibly different state.
					em.getTransaction().rollback();
					continue;
				}
				
				aCity.setCountry(country);
				aCity.setState(state);
				City newCity = new City(aCity.getCityName(), state.getId(), country.getId());
				newCity.setId(++idCounter);
				em.merge(newCity);
				
				try {
					em.getTransaction().commit();
				}catch(Exception e) {
					LOGGER.info(e.getMessage());
					em.getTransaction().rollback();
				}
				
			}
		}catch(Exception exception){
			LOGGER.info(exception.getMessage());
			break;
		}
	}
	em.close();
	emf.close();
	}

}