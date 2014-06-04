package naturallanguagedotstat.resprep;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;


/**
 * This class will read in the 46 DSDs as prepared by DSDDownloader.java
 * and extract the set of unique concepts across them
 * 
 * @author sainge
 *
 */
public class ConceptAnalyser {

	public static void main(String[] args) throws IOException{

		ArrayList<Document> dsds = new ArrayList<Document>();

		for(int i = 1; i <= 46; i++){
			File f = new File(i+".xml");
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			String line = br.readLine();
			dsds.add(XMLToDocument(line));
			br.close();
		}

		HashSet<String> measures = new HashSet<String>();

		for(Document doc : dsds){
			measures.addAll(findMeasures(doc));
		}
		

		for(String measure : measures){
			System.out.println(measure);
		}
	}

	/**
	 * Search through a SDMX DSD Document to find the concepts
	 * 
	 * @param document The document to search
	 * @return the list of concepts
	 */
	public static HashSet<String> findMeasures(Document document) {

		HashSet<String> measures = new HashSet<String>();

		NodeList nodeList = document.getElementsByTagName("Name");
		Node node;
		for (int i = 0; i < nodeList.getLength(); i++) {
			node = nodeList.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE && 
					node.getParentNode().getNodeName().equals("Concept") && 
					node.getAttributes().getNamedItem("xml:lang").getNodeValue().equals("en")){
				measures.add(node.getTextContent());
			}
		}
		return measures;
	}

	/**
	 * Convert an XML String to a Document object
	 * 
	 * @param xml the String to parse
	 * @return the Document object
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

}
