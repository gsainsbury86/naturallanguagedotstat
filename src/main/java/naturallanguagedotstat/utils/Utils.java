package naturallanguagedotstat.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import naturallanguagedotstat.model.Dataset;
import naturallanguagedotstat.model.Dimension;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class Utils {

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
	
	public static String regionTypeForRegionCode(String regionCode) {
		String regionType = "";

		switch(regionCode.length()){
		case 1:
			regionType = "STE";
			break;
		case 3:
			regionType = "SA4";
			break;
		case 5: 
			regionType = "SA3";
			break;
		case 9: 
			regionType = "SA2";
			break;
		default:
			break;
		}

		//System.err.println(regionCode);

		if(regionCode.equals("0")){
			regionType = "AUS";
		}
		return regionType;
	}
	
	/**
	 * Print an integer with leading zeroes to a specified number of digits.
	 * 
	 * @param num the integer
	 * @param digits number of digits
	 * @return formatted String
	 */
	public static String intToString(int num, int digits) {
		assert digits > 0 : "Invalid number of digits";

		// create variable length array of zeros
		char[] zeros = new char[digits];
		Arrays.fill(zeros, '0');
		// format number as String
		DecimalFormat df = new DecimalFormat(String.valueOf(zeros));

		return df.format(num);
	}
	
	public static String findValue(HashMap<String, String> map,
			String value) {
		for(String key : map.keySet()){
			if (map.get(key).trim().toLowerCase().equals(value.toLowerCase())){
				return key;
			}
		}
		return null;
	}
	
	public static String findDimLabel(Document doc, String dim) {
		//HashMap<String,String> dimensions = new HashMap<String,String>();

		if(dim.endsWith("ASGS_2011")){
			return "Region";
		}


		NodeList nodeList = doc.getElementsByTagName("Name");
		Node node;
		for (int i = 0; i < nodeList.getLength(); i++) {
			node = nodeList.item(i);
			if (node.getParentNode().getNodeName().equals("Concept") && 
					dim.endsWith(node.getParentNode().getAttributes().getNamedItem("id").getNodeValue()) && 
					node.getAttributes().getNamedItem("xml:lang").getNodeValue().equals("en")){
				return node.getTextContent();

			}
		}
		return null;
	}
	
	public static HashMap<String,String> findCodeLists(Document doc, String dim) {
		HashMap<String,String> dimensions = new HashMap<String,String>();

		NodeList nodeList = doc.getElementsByTagName("Description");
		Node node;
		for (int i = 0; i < nodeList.getLength(); i++) {
			node = nodeList.item(i);
			if (node.getParentNode().getParentNode().getAttributes().getNamedItem("id").getNodeValue().equals(dim)
					&&
					node.getAttributes().getNamedItem("xml:lang").getNodeValue().equals("en")
					){
				dimensions.put(node.getParentNode().getAttributes().getNamedItem("value").getNodeValue(),node.getTextContent());
			}
		}
		return dimensions;
	}


	public static ArrayList<String> findDimensions(Document doc) {

		ArrayList<String> dimensions = new ArrayList<String>();

		NodeList nodeList = doc.getElementsByTagName("Dimension");
		Node node;
		for (int i = 0; i < nodeList.getLength(); i++) {
			node = nodeList.item(i);
			//if (){
			dimensions.add(node.getAttributes().getNamedItem("codelist").getNodeValue());
			//}
		}
		return dimensions;
	}
	
	public static ArrayList<Dataset> findDatasetsWithDimensionName(ArrayList<Dataset> datasets, String dimensionName){
		ArrayList<String> listOfOne = new ArrayList<String>();
		listOfOne.add(dimensionName);
		return findDatasetsWithDimensionNames(datasets, listOfOne);
	}

	/**
	 * Find a list of Datasets which contain the given dimensions (by name).
	 *  
	 * @param dimensionNames an ArrayList of Strings with names of Dimensions
	 * @return an ArrayList of Dimension objects
	 */
	public static ArrayList<Dataset> findDatasetsWithDimensionNames(ArrayList<Dataset> datasets, ArrayList<String> dimensionNames){
		ArrayList<Dataset> toReturn = new ArrayList<Dataset>();

		for(Dataset dataset : datasets){
			int c = 0;
			for(Dimension dimension : dataset.getDimensions()){
				for(String dimensionName : dimensionNames){
					if(dimension.getName().equals(dimensionName)){
						c++;
					}
				}
			}
			if(c == dimensionNames.size()){
				toReturn.add(dataset);
			}
		}
		return toReturn;
	}
	
	public static Dataset findDatasetWithDimensionNames(ArrayList<Dataset> datasets, ArrayList<String> dimensionNames){
		ArrayList<Dataset> datasetsWithDimensions = findDatasetsWithDimensionNames(datasets, dimensionNames);
				
		Dataset toReturn = null;
		for(Dataset ds : datasetsWithDimensions){
			if(toReturn == null){
				toReturn = ds;
			}
			if(ds.getDimensions().size() < toReturn.getDimensions().size()){
				toReturn = ds;
			}
		}
		
		return toReturn;
		
	}



}
