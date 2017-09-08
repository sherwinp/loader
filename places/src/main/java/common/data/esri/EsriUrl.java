package common.data.esri;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
import javax.json.JsonValue.ValueType;

import common.data.City;

public final class EsriUrl {
	private final static Logger LOGGER = Logger.getLogger(EsriUrl.class.getName());
	static String esriUrl = "https://services.arcgis.com/P3ePLMYs2RVChkJx/ArcGIS/rest/services/World_Cities/FeatureServer/0/query?f=json&where=1%3D1&returnGeometry=false&spatialRel=esriSpatialRelIntersects&outFields=*&orderByFields=CNTRY_NAME,ADMIN_NAME&outSR=102100";
	
	public static List<City> accessorForCities(int offset) throws IOException {
		String requestUrl = String.format("%s&resultOffset=%d&resultRecordCount=1900", esriUrl, offset);
		Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy.apps.dhs.gov", 80));
		HttpURLConnection con =(HttpURLConnection)new URL(requestUrl).openConnection(proxy);
		List<City> cities = new ArrayList<City>();
		con.setRequestMethod("GET");
		con.setRequestProperty("Accept", "application/json");
		
		if(con.usingProxy())
			LOGGER.info( "Using Proxy: "  );

		while (moreData( Json.createReader(new InputStreamReader(con.getInputStream(), Charset.defaultCharset())).read(), cities)){
			offset = offset + cities.size();
			requestUrl = String.format("%s&resultOffset=%d&resultRecordCount=1900", esriUrl, offset);
			con = (HttpURLConnection)new URL(requestUrl).openConnection(proxy);
			con.setRequestMethod("GET");
		}
		return cities;
	}

	public static boolean moreData(javax.json.JsonStructure structure, List<City> cities) throws IOException{
		Boolean exceededTransferLimit = false;
		
		int features_size = structure.asJsonObject().getJsonArray("features").size();
		
		for( JsonValue obj : structure.asJsonObject().getJsonArray("features")) {
			javax.json.JsonObject fields = obj.asJsonObject().get("attributes").asJsonObject();
			int size = fields.size();
			fields.getJsonString("CITY_NAME");
			String field = fields.getJsonString("CITY_NAME").getString();
			LOGGER.info(field);
	    	if( !field.isEmpty() ){
	    		cities.add(new City( field, 
	    					fields.getJsonString("ADMIN_NAME").getString(), 
	    					fields.getJsonString("GMI_ADMIN").getString(), 
	    					fields.getJsonString("CNTRY_NAME").getString(), 
	    					fields.getJsonString("FIPS_CNTRY").getString()));
	    	}
		}
		if( features_size > 0 && null!=structure.asJsonObject().get("exceededTransferLimit") )
			exceededTransferLimit = structure.asJsonObject().getBoolean("exceededTransferLimit");
		
		return exceededTransferLimit;

	}
}
