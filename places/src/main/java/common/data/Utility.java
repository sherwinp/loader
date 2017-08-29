package common.data;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public final class Utility {
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