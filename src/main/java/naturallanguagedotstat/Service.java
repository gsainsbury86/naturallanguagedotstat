package naturallanguagedotstat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import naturallanguagedotstat.model.Dataset;
import naturallanguagedotstat.model.Dimension;
import naturallanguagedotstat.utils.Utils;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "main")
@Path("/main")
public class Service {

	private static final String local_webapp = "src/main/webapp/";
	private static final String RES_DIR = "/WEB-INF/resources/";
	private static final String serverName = "stat.abs.gov.au";
	//private static Dimension ASGS2011;
	//private static ArrayList<Dataset> datasets = null;

	@javax.ws.rs.core.Context 
	ServletContext context;

	public Service() throws IOException, ClassNotFoundException{

	}
	
	@GET
	@Path("/")
	public String landing() throws FileNotFoundException, IOException, ClassNotFoundException{
		InputStream fileIn = context.getResourceAsStream(RES_DIR+"index.html");
		InputStreamReader isr = new InputStreamReader(fileIn);
		BufferedReader br = new BufferedReader(isr);
		StringBuffer sb = new StringBuffer();
		String s;
		while ((s = br.readLine()) != null)
		    sb.append(s);	
		return new String(sb);
	}
	
	private ArrayList<Dataset> loadDatasets() throws IOException, ClassNotFoundException,
	FileNotFoundException {
		ArrayList<Dataset> datasets = new ArrayList<Dataset>();

		for(int i = 1; i <= 46; i++){
			String dsNumber = Utils.intToString(i,2);
			InputStream fileIn = context.getResourceAsStream(RES_DIR+"ABS_CENSUS2011_B"+dsNumber+".ser");
			ObjectInputStream objIn = new ObjectInputStream(fileIn);
			Dataset ds = (Dataset) objIn.readObject();
			datasets.add(ds);
			objIn.close();
			fileIn.close();
		}
		
		return datasets;
	}
	
	private ArrayList<Dataset> loadDatasets_DEBUG() throws IOException, ClassNotFoundException,
	FileNotFoundException {
		ArrayList<Dataset> datasets = new ArrayList<Dataset>();

		for(int i = 1; i <= 46; i++){
			String dsNumber = Utils.intToString(i,2);
			//InputStream fileIn = context.getResourceAsStream(RES_DIR+"ABS_CENSUS2011_B"+dsNumber+".ser");
			FileInputStream fileIn = new FileInputStream(new File(local_webapp+RES_DIR+"ABS_CENSUS2011_B"+dsNumber+".ser"));
			ObjectInputStream objIn = new ObjectInputStream(fileIn);
			Dataset ds = (Dataset) objIn.readObject();
			datasets.add(ds);
			objIn.close();
			fileIn.close();
		}
		
		return datasets;
	}

	private Dimension loadASGS_2011() throws IOException, ClassNotFoundException,
	FileNotFoundException {
		InputStream fileIn = context.getResourceAsStream(RES_DIR+"ASGS_2011.ser");
		ObjectInputStream objIn = new ObjectInputStream(fileIn);
		Dimension ASGS2011 = (Dimension) objIn.readObject();
		objIn.close();
		fileIn.close();
		
		return ASGS2011;
		
	}
	
	private Dimension loadASGS_2011_DEBUG() throws IOException, ClassNotFoundException,
	FileNotFoundException {
		//InputStream fileIn = context.getResourceAsStream(RES_DIR+"ASGS_2011.ser");
		FileInputStream fileIn = new FileInputStream(new File(local_webapp+RES_DIR+"ASGS_2011.ser"));
		ObjectInputStream objIn = new ObjectInputStream(fileIn);
		Dimension ASGS2011 = (Dimension) objIn.readObject();
		objIn.close();
		fileIn.close();
		
		return ASGS2011;
		
	}

	@GET
	@Path("/query/{query}")
	@Produces("text/html;charset=UTF-8;version=1")
	public String query(@PathParam("query") String query) throws FileNotFoundException, IOException, ClassNotFoundException{

		//ArrayList<Dataset> datasets = loadDatasets();
		//Dimension ASGS2011 = loadASGS_2011();
		ArrayList<Dataset> datasets = loadDatasets_DEBUG();
		Dimension ASGS2011 = loadASGS_2011_DEBUG();
		
		SemanticParser semanticParser = new SemanticParser(query);
		semanticParser.parseText();
		
		HashMap<String,String> queryInputs = semanticParser.getDimensions();	
		//System.err.println(queryInputs);
		String region = queryInputs.get("region");
		
		queryInputs.remove("region");

		// PARSE QUERY : list of dims and ranges - region separate
		//HashMap<String, String> queryInputs = new HashMap<String, String>();
		//queryInputs.put("Sex","2");
		//queryInputs.put("Selected Person Characteristics","T25");

		//String region = "Sandy Bay";

		ArrayList<String> dimensionNames = new ArrayList<String>();
		dimensionNames.addAll(queryInputs.keySet());

		Dataset dataset = Utils.findDatasetWithDimensionNames(datasets, dimensionNames);

		String urlToRead = queryBuilder(dataset, ASGS2011, region, queryInputs);

		System.out.println(urlToRead);

		String data = Utils.httpGET(urlToRead);

		Document dataDocument = Utils.XMLToDocument(data);

		String resultString = findObsValue(dataDocument);

		System.out.println(resultString);

		return resultString;

	}

	/**
	 * Builds the query for the given dataset and specified dimension values
	 * 
	 * @param ds the Dataset object
	 * @param dimensionValues a HashMap<String, String> where the key is the dimension name and the value is  the range
	 * @param region the Region (as appears in ASGS 2011)
	 * @return
	 */
	public static String queryBuilder(Dataset ds, Dimension ASGS2011, String region, HashMap<String, String> dimensionValues){
		String url;

		url = "http://";

		url += serverName;

		url += "/restsdmx/sdmx.ashx/GetData/";

		url += ds.getName()+"/";

		/* ensure order */
		for(Dimension dim : ds.getDimensions()){
			for(String dimKey : dimensionValues.keySet()){
				if(dim.getName().equals(dimKey)){
					url += Utils.findValue(dim.getCodelist(),dimensionValues.get(dimKey)) + ".";
				}
			}
		}
		
		//System.err.println(ds);

		String regionCode = Utils.findValue(ASGS2011.getCodelist(), region);
		String stateCode = regionCode.substring(0,1);
		String regionType = Utils.regionTypeForRegionCode(regionCode);

		url += stateCode + ".";
		url += regionType + ".";
		url += regionCode + ".";
		url += "A";

		url += "/ABS";

		return url;
	}

	public static String findObsValue(Document document) {

		NodeList nodeList = document.getElementsByTagName("ObsValue");
		Node node = nodeList.item(0);
		return node.getAttributes().getNamedItem("value").getNodeValue();
	}

	//	public static String findASGS2011Code(Document document, String search) {
	//
	//		NodeList nodeList = document.getElementsByTagName("*");
	//		for (int i = 0; i < nodeList.getLength(); i++) {
	//			Node node = nodeList.item(i);
	//			if (node.getNodeType() == Node.ELEMENT_NODE) {
	//				if(node.getTextContent().equals(search) && 
	//						node.getParentNode().getParentNode().getAttributes().getNamedItem("id").getTextContent().equals("CL_ABS_CENSUS2011_B01_ASGS_2011")
	//						){
	//					return node.getParentNode().getAttributes().getNamedItem("value").getNodeValue();
	//				}
	//
	//			}
	//		}
	//		return null;
	//	}


}
