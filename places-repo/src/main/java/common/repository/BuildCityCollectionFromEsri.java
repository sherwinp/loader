package common.repository;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Vector;
import java.util.logging.Logger;

import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliAvailabilityIndicator;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;

import common.data.City;
import common.data.esri.EsriUrl;
import common.data.streams.CSVHelper;

@Component
public class BuildCityCollectionFromEsri implements CommandMarker{
	private final static Logger LOGGER = Logger.getLogger(BuildCityCollectionFromEsri.class.getName());
	  
	@CliAvailabilityIndicator({"esri"})
	  public boolean isCommandAvailable() {
	    return true;
	  }
	
	@CliCommand(value = "esri", help = "Load Cities from esri dataset")
	public static void main(@CliOption(key="args", mandatory=false) String[] args) throws Exception {

		List<City> cities = EsriUrl.accessorForCities(0);
		cities.forEach((city)->{
			LOGGER.info(city.toString());
		});
		
		//RepositoryOfCity.load(cities);

	}
}