package naturallanguagedotstat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.swing.JOptionPane;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;


@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "main")
@Path("/main")
public class Service {

	private static final String serverName = "stat.abs.gov.au";
	//	static ArrayList<Card> allCards;
	//	static ArrayList<Card> deck;
	//	static ArrayList<Card> discard;
	//	static HashMap<String,ArrayList<Card>> players;
	//	static String log;
	
	static String resultString = null;

	public Service(){

	}

	@GET
	@Path("/")
	@Produces("text/html;charset=UTF-8;version=1")
	public String hello() {
		return "Hello World";
	}

	@GET
	@Path("/search/{region}")
	@Produces("text/html;charset=UTF-8;version=1")
	public String addPlayer(@PathParam("region") String region){
		String dsd = GET("http://"+serverName+"/restsdmx/sdmx.ashx/GetDataStructure/ABS_CENSUS2011_B01/ABS");

		Document dsdDocument = XMLToDocument(dsd);

		//String input = JOptionPane.showInputDialog("Where would you like to know the population for?");

		//String SA2code = "601051031";
		String regionCode = findSA2CodeIterative(dsdDocument, region);

		String regionType = regionTypeForRegionCode(regionCode);

		String urlToRead = "http://"+serverName+"/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B01/3.TT." + regionCode.charAt(0) + "." + regionType + "." + regionCode +".A/ABS?startTime=2011&endTime=2011";

		String data = GET(urlToRead);

		Document dataDocument = XMLToDocument(data);

		findObsValueRecursive(dataDocument.getDocumentElement());

		return resultString;
		
		//System.err.println(System.currentTimeMillis() - startTime);
	}

	private static String regionTypeForRegionCode(String regionCode) {
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

	private static Document XMLToDocument(String xml) {
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

	public static void findObsValueRecursive(Node node) {
		// do something with the current node instead of System.out
		if(node.getNodeName() == "ObsValue"){
			resultString = node.getAttributes().getNamedItem("value").getNodeValue();
		}

		NodeList nodeList = node.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node currentNode = nodeList.item(i);
			if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
				//calls this method for all the children which is Element
				findObsValueRecursive(currentNode);
			}
		}
	}

	public static String findSA2CodeIterative(Document document, String search) {

		NodeList nodeList = document.getElementsByTagName("*");
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				if(node.getTextContent().equals(search) && 
						node.getParentNode().getParentNode().getAttributes().getNamedItem("id").getTextContent().equals("CL_ABS_CENSUS2011_B01_ASGS_2011")
						){
					return node.getParentNode().getAttributes().getNamedItem("value").getNodeValue();
				}

			}
		}
		return null;
	}


	//			public static void findSA2CodeRecursive(Node node, String search) {
	//			 // do something with the current node instead of System.out
	//			 if(node.getTextContent().equals(search)){
	//			 System.out.println( node.getParentNode().getAttributes().getNamedItem("value").getNodeValue());
	//			 }
	//
	//			 NodeList nodeList = node.getChildNodes();
	//			 for (int i = 0; i < nodeList.getLength(); i++) {
	//			 Node currentNode = nodeList.item(i);
	//			 if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
	//			 //calls this method for all the children which is Element
	//			 findSA2CodeRecursive(currentNode,search);
	//			 }
	//			 }
	//			}

	public static String GET(String urlToRead) {
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
}
