package naturallanguagedotstat.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class Utils {

	/**
	 * Converts an XML String into a Document
	 * 
	 * @param xml the well-formatted XML String
	 * @return a Document
	 */
	public static Document XMLToDocument(String xml) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
		DocumentBuilder builder;  
		Document document = null;
		try  
		{  	
			builder = factory.newDocumentBuilder();  
			document = builder.parse( new InputSource( new StringReader( xml ) ) );

		} catch (Exception e) {  
			e.printStackTrace();  
		}
		return document;
	}

	/**
	 * HTTP GET to a String
	 * 
	 * @param urlToRead the address from which to read data
	 * @return String from webpage
	 */
	public static String httpGET(String urlToRead) {
		URL url;
		HttpURLConnection conn;
		BufferedReader rd;
		String line;
		String result = "";
		try {
			url = new URL(urlToRead);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			while ((line = rd.readLine()) != null) {
				result += line;
			}
			rd.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}	


	/**
	 * Searches a map for the given value and returns the key
	 * 
	 * @param map the map
	 * @param value the value to search
	 * @return the key for that value
	 */
	public static String findValue(HashMap<String, String> map,
			String value) {
		for(String key : map.keySet()){
			// if (map.get(key).trim().toLowerCase().equals(value.toLowerCase())){  
			if (map.get(key).toLowerCase().equals(value.toLowerCase())){
				return key;
			}
		}
		return null;
	}

	public static int findObsValue(Document document) {
		int c = 0;
		NodeList nodeList = document.getElementsByTagName("ObsValue");
	    for (int i = 0; i < nodeList.getLength(); i++) {
	        Node node = nodeList.item(i);
			c += Integer.parseInt(  node.getAttributes().getNamedItem("value").getNodeValue());
		}
		return c;

	}

}
