package naturallanguagedotstat;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;

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

	private static final String serverName = "stat.abs.gov.au";
	private static Dimension ASGS2011;
	private static ArrayList<Dataset> datasets = null;

	public Service() throws IOException, ClassNotFoundException{
		datasets = new ArrayList<Dataset>();

		for(int i = 1; i <= 46; i++){
			String dsNumber = Utils.intToString(i,2);
			FileInputStream fileIn = new FileInputStream("src/main/resources/"+"ABS_CENSUS2011_B"+dsNumber+".ser");
			ObjectInputStream objIn = new ObjectInputStream(fileIn);
			Dataset ds = (Dataset) objIn.readObject();
			datasets.add(ds);
			objIn.close();
			fileIn.close();
		}

		FileInputStream fileIn = new FileInputStream("src/main/resources/"+"ASGS_2011.ser");
		ObjectInputStream objIn = new ObjectInputStream(fileIn);
		ASGS2011 = (Dimension) objIn.readObject();
		objIn.close();
		fileIn.close();
	}

	//	@GET
	//	@Path("/search/{region}")
	//	@Produces("text/html;charset=UTF-8;version=1")
	//	public String search(@PathParam("region") String region){
	//		String dsd = Utils.httpGET("http://"+serverName+"/restsdmx/sdmx.ashx/GetDataStructure/ABS_CENSUS2011_B01/ABS");
	//
	//		Document dsdDocument = Utils.XMLToDocument(dsd);
	//
	//		String regionCode = findASGS2011Code(dsdDocument, region);
	//
	//		String regionType = Utils.regionTypeForRegionCode(regionCode);
	//
	//		char stateCode = regionCode.charAt(0);
	//
	//		String urlToRead = "http://"+serverName+"/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B01/3.TT." + stateCode + "." + regionType + "." + regionCode +".A/ABS?startTime=2011&endTime=2011";
	//
	//		String data = Utils.httpGET(urlToRead);
	//
	//		Document dataDocument = Utils.XMLToDocument(data);
	//
	//		return findObsValue(dataDocument);
	//
	//
	//	}

	@GET
	@Path("/query/{query}")
	@Produces("text/html;charset=UTF-8;version=1")
	public String query(@PathParam("query") String query){

		// PARSE QUERY : list of dims and ranges - region separate
		HashMap<String, String> queryInputs = new HashMap<String, String>();
		queryInputs.put("Sex","2");
		queryInputs.put("Selected Person Characteristics","T25");

		String region = "Sandy Bay";

		ArrayList<String> dimensionNames = new ArrayList<String>();
		dimensionNames.addAll(queryInputs.keySet());

		Dataset dataset = Utils.findDatasetWithDimensionNames(datasets, dimensionNames);

		String urlToRead = queryBuilder(dataset, region, queryInputs);

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
	public static String queryBuilder(Dataset ds, String region, HashMap<String, String> dimensionValues){
		String url;

		url = "http://";

		url += serverName;

		url += "/restsdmx/sdmx.ashx/GetData/";

		url += ds.getName()+"/";

		/* ensure order */
		for(Dimension dim : ds.getDimensions()){
			for(String dimKey : dimensionValues.keySet()){
				if(dim.getName().equals(dimKey)){
					url += dimensionValues.get(dimKey) + ".";
				}
			}
		}

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
