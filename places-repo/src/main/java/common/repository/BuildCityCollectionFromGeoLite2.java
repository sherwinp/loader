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
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;

import common.data.City;
import common.data.streams.CSVHelper;

@Component
public class BuildCityCollectionFromGeoLite2 implements CommandMarker{
	private final static Logger LOGGER = Logger.getLogger(BuildCityCollectionFromGeoLite2.class.getName());

	@CliCommand(value = "geolite2", help = "Load Cities from GeoLite2 dataset")
	public static void main(@CliOption(key="args", mandatory=false) String[] args) throws Exception {
		
		List<City> collection = new Vector<City>();
		File fileTemplate = new File("../GeoLite2-City-Locations-en.csv");
		FileInputStream fis = new FileInputStream(fileTemplate);
		Reader freader = new InputStreamReader(fis, "UTF-8");

		
		Integer indx = 0;
		HashMap<String, Integer> hmap = new LinkedHashMap<String, Integer>();
		List<String> values = CSVHelper.parseLine(freader);
		while (values != null) {
			indx++;
			if (indx == 1) {
				// Header row
				Integer ix = 0;
				for (String sz : values) {
					ix++;
					hmap.put(sz, ix);
				}
			} else {
				// Value row
				collection.add(new City(hmap, values));
			}
			values = CSVHelper.parseLine(freader);
		}

		fis.close();
		freader.close();
		RepositoryOfCity.load(collection);
	}
}
