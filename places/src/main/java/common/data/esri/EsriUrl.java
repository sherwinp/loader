package common.data.esri;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.net.ssl.HttpsURLConnection;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;

import common.data.City;

public final class EsriUrl {
	private final static Logger LOGGER = Logger.getLogger(EsriUrl.class.getName());
	static String esriUrl = "https://services.arcgis.com/P3ePLMYs2RVChkJx/ArcGIS/rest/services/World_Cities/FeatureServer/0/query?f=json&where=1%3D1&returnGeometry=false&spatialRel=esriSpatialRelIntersects&outFields=*&orderByFields=CNTRY_NAME,ADMIN_NAME&outSR=102100";
	
	public static List<City> accessorForCities(int offset) throws IOException {
		String requestUrl = String.format("%s&resultOffset=%d&resultRecordCount=1900", esriUrl, offset);
		HttpsURLConnection con = (HttpsURLConnection) new URL(requestUrl).openConnection();
		List<City> cities = new ArrayList<City>();
			
		while (moreData(new ObjectMapper().getFactory().createParser(con.getInputStream()), cities)){
			offset = offset + cities.size();
			requestUrl = String.format("%s&resultOffset=%d&resultRecordCount=1900", esriUrl, offset);
			con = (HttpsURLConnection) new URL(requestUrl).openConnection();
			con.setRequestMethod("GET");
		}
		return cities;
	}
	public static boolean moreData(JsonParser jsonParser, List<City> cities) throws IOException{
		Boolean exceededTransferLimit = false;
		Map<String, String> objValues = new HashMap<String, String>();
		
		while(!jsonParser.isClosed()){
		    
			JsonToken jsonToken = jsonParser.nextToken();
			
			if(jsonToken == JsonToken.VALUE_TRUE && (jsonParser.getCurrentName() == "exceededTransferLimit")){
				exceededTransferLimit = jsonParser.getValueAsBoolean();
			}
			
			if (jsonToken == JsonToken.VALUE_STRING){
				switch(jsonParser.getCurrentName()){
				case "STATUS":
				case "ADMIN_NAME":
				case "CITY_NAME":
				case "CNTRY_NAME":
				case "FIPS_CNTRY":
				case "GMI_ADMIN":
				case "POP_CLASS":
				default:
					objValues.put( jsonParser.getCurrentName(),  jsonParser.getValueAsString() );
					
				}
			}
			if( jsonToken == JsonToken.END_OBJECT ){
		    	// LOGGER.info(objValues.toString());
		    	if( objValues.get("CITY_NAME") instanceof String ){
		    		cities.add(new City(objValues.get("CITY_NAME"), objValues.get("ADMIN_NAME"), objValues.get("GMI_ADMIN"), objValues.get("CNTRY_NAME"), objValues.get("FIPS_CNTRY")));
		    	}
		    	objValues.clear();
		    }
		}
		return exceededTransferLimit;
	}
}
