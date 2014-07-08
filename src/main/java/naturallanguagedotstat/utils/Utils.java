package naturallanguagedotstat.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import naturallanguagedotstat.model.Dataset;
import naturallanguagedotstat.model.Dimension;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class Utils {
	
	private static final String RES_DIR = "/WEB-INF/resources/";
	private static final String local_webapp = "src/main/webapp/";
	
	@SuppressWarnings("unchecked")
	public static ArrayList<Dataset> loadDatasets() throws IOException, ClassNotFoundException,
	FileNotFoundException {

		ArrayList<Dataset> datasets = new ArrayList<Dataset>();

		InputStream fileIn;
		fileIn = new FileInputStream(new File(local_webapp+RES_DIR+"datasets.ser"));
		ObjectInputStream objIn = new ObjectInputStream(fileIn);
		datasets = (ArrayList<Dataset>) objIn.readObject();
		objIn.close();
		fileIn.close();

		HashSet<Dataset> toRemove = new HashSet<Dataset>();

		for(Dataset ds : datasets){
			String name = ds.getName();
			if(!(name.startsWith("ABS_CENSUS2011_B") && name.length() == 18)
					&& !name.equals("CPI") 
					&& !name.equals("LF") 
					&& !name.equals("MERCH_EXP") 
					&& !name.equals("MERCH_IMP") 
					&& !name.equals("BOP") 
					&& !name.equals("RT") 
					){
				toRemove.add(ds);
			}
		}

		datasets.removeAll(toRemove);
		return datasets;
	}

	


	public static Dimension loadASGS_2011() throws IOException, ClassNotFoundException,
	FileNotFoundException {
		InputStream fileIn;
		fileIn = new FileInputStream(new File(local_webapp+RES_DIR+"ASGS_2011.ser"));
		ObjectInputStream objIn = new ObjectInputStream(fileIn);
		Dimension ASGS2011 = (Dimension) objIn.readObject();
		objIn.close();
		fileIn.close();

		return ASGS2011;

	}

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

	//TODO: Change this so that it only aggregates when its supposed to.
	public static double findObsValue(Document document) {
		double c = 0;
		NodeList nodeList = document.getElementsByTagName("ObsValue");
		if(nodeList.getLength() == 0){
			System.out.println("No ObsVAlue in SDMX response.");
			throw new NullPointerException("No ObsValue in SDMX response.");
		}
	    for (int i = 0; i < nodeList.getLength(); i++) {
	        Node node = nodeList.item(i);
			System.out.println("value: "+Double.parseDouble(node.getAttributes().getNamedItem("value").getNodeValue()));
			c += Double.parseDouble(node.getAttributes().getNamedItem("value").getNodeValue());
		}
		return c;

	}

}
