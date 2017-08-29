package common.repository;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.springframework.data.jpa.repository.JpaRepository;
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
	
	em.getTransaction().begin();
	for( City city: collection ){
		try{
			if(city.valid()){
				
				State state = em.find(State.class, city.getState_name());
				Country country = em.find(Country.class, city.getCountry_name());
				
				em.merge(new City(city.getCity_name(), state.getId(), country.getId()));
				
			}
		}catch(Exception exception){
			LOGGER.info(exception.getMessage());
			break;
		}
	}
	em.getTransaction().commit();
	em.close();
	emf.close();
	}

}