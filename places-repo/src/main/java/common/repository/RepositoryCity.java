package common.repository;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Properties;
import java.util.Vector;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import common.data.City;
import common.data.esri.EsriUrl;
import common.data.streams.CSVHelper;

@Repository
public interface RepositoryCity extends JpaRepository<City, String> {

}

class Utility {
	private final static Logger LOGGER = Logger.getLogger(Utility.class.getName());
	private final static Properties ConnectionProperties = new Properties();

	public static EntityManagerFactory Initialize() throws IOException{
		final Properties ConnectionProperties = new Properties();
		InputStream is = ClassLoader.getSystemResourceAsStream("META-INF/connection.properties");
		ConnectionProperties.load(is);
		is.close();
		is = null;
		return Persistence.createEntityManagerFactory("demodb", ConnectionProperties);
	}
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
				
				em.merge(city);
				
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