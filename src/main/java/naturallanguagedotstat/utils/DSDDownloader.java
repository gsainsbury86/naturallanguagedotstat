package naturallanguagedotstat.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import naturallanguagedotstat.model.Dataset;
import naturallanguagedotstat.model.Dimension;

/**
 * 
 * @author sainge
 *
 */
public class DSDDownloader {

	private static final String RESOURCES = "src/main/webapp/WEB-INF/resources/";
	static HashMap<String,String> ASGS2011CodeList;
	static String serverName = "stat.abs.gov.au";
	static String datasetName = "ABS_CENSUS2011";
	static ArrayList<Dataset> datasets;
	static int NUM_COLLECTIONS = 28;


	//TODO: http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetDataStructure/ABS_C16_T01_LGA
	// 		http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetDataStructure/ABS_C16_T01_SA
	// 		28 datasets


	public static void main(String[] args) throws IOException{


		//		for(int i = 1; i <= 46; i++){
		//		String dsNumber = Utils.intToString(i,2);
		//			PrintWriter writer = new PrintWriter(RESOURCES+dsNumber+".xml", "UTF-8");
		//			writer.println(Utils.httpGET("http://"+serverName+"/restsdmx/sdmx.ashx/GetDataStructure/ABS_CENSUS2011_B"+Utils.intToString(i, 2)+"/ABS"));
		//			writer.close();
		//
		//		}


		datasets = new ArrayList<Dataset>();

		for(int i = 1; i <= NUM_COLLECTIONS; i++){
			String dsNumber = String.format("%02d", 2);
			//File f = new File(RESOURCES+dsNumber+".xml");
			//FileReader fr = new FileReader(f);
			//BufferedReader br = new BufferedReader(fr);
			//String line = br.readLine();
			//br.close();

			InputStream in = new URL( "http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetDataStructure/ABS_C16_T01_SA" ).openStream();

			try {
				String line = IOUtils.toString( in );
				System.out.println(line);




				Document doc = Utils.XMLToDocument(line);

				Dataset dataset = new Dataset(datasetName+"_B"+dsNumber,null);

				ArrayList<String> dimensionNameList = findDimensions(doc);

				ArrayList<Dimension> dimensions = new ArrayList<Dimension>();

				for(String dimName : dimensionNameList){
					//				HashMap<String,String> map = Utils.findCodeLists(doc, dimName);
					HashMap<String, String> map = null;
					String dimLabel = findDimLabel(doc, dimName);
					Dimension dim = new Dimension(dimLabel,dimName, map);
					dimensions.add(dim);
				}

				dataset.setDimensions(dimensions);
				datasets.add(dataset);
			} finally {
				IOUtils.closeQuietly(in);
			}
		}

		FileOutputStream fileOut = new FileOutputStream(RESOURCES+"/dataset_summaries.ser");
		ObjectOutputStream out = new ObjectOutputStream(fileOut);
		out.writeObject(datasets);
		out.close();
		fileOut.close();

		//		ArrayList<Dimension> dims = datasets.get(0).getDimensions();
		//		for(Dimension dim : dims){
		//			if(dim.getName().equals("Region")){
		//				ASGS2011 = dim;
		//			}
		//		}

		//		FileOutputStream fileOut = new FileOutputStream("src/main/webapp/DSDs/ASGS_2011.ser");
		//		ObjectOutputStream out = new ObjectOutputStream(fileOut);
		//		out.writeObject(ASGS2011);
		//		out.close();
		//		fileOut.close();


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

	public static ArrayList<String> findDimensions(Document doc) {

		ArrayList<String> dimensions = new ArrayList<String>();

		NodeList nodeList = doc.getElementsByTagName("Dimension");
		Node node;
		for (int i = 0; i < nodeList.getLength(); i++) {
			node = nodeList.item(i);
			dimensions.add(node.getAttributes().getNamedItem("codelist").getNodeValue());
		}
		return dimensions;
	}

}