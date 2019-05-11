package naturallanguagedotstat.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
import java.util.LinkedHashMap;

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
			document = builder.parse( new InputSource( new StringReader( xml.trim().replaceFirst("^([\\W]+)<","<") ) ) );

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
	 * @throws IOException 
	 */
	public static String httpGET(String urlToRead) throws IOException {
		URL url;
		HttpURLConnection conn;
		BufferedReader rd;
		String line;
		String result = "";
		try{
			url = new URL(urlToRead);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			while ((line = rd.readLine()) != null) {
				result += line;
			}
			rd.close();
		}catch(IOException e){
			throw new IOException("Unable to connect to ABS.Stat");
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
			System.out.println("No ObsValue in SDMX response.");
			throw new NullPointerException("No ObsValue in SDMX response.");
		}
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			//System.out.println("value: "+Double.parseDouble(node.getAttributes().getNamedItem("value").getNodeValue()));
			c += Double.parseDouble(node.getAttributes().getNamedItem("value").getNodeValue());
		}
		return c;

	}




	public static LinkedHashMap<String, String> loadSynonyms() throws IOException, ClassNotFoundException,
	FileNotFoundException {

		LinkedHashMap<String, String> toReturn = new LinkedHashMap<String, String>();

		BufferedReader br = new BufferedReader(new FileReader(new File(local_webapp+RES_DIR+"synonyms.csv")));
		String line = null;

		while ((line = br.readLine()) != null) {

			// use comma as separator
			String[] newSynonym = line.split(",");

			// fix for commas in values e.g. Korea, Republic of South
			String value = "";
			for(int i = 1; i < newSynonym.length; i++){
				value+=newSynonym[i] + ",";
			}

			value = value.substring(0,value.length() - 1);
			
			toReturn.put(newSynonym[0],value);

		}

		br.close();
		return toReturn;
	}

}
