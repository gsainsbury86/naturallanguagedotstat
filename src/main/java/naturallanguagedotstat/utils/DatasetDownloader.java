package naturallanguagedotstat.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.nio.charset.Charset;
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
public class DatasetDownloader {

	private static final String RESOURCES = "src/main/webapp/WEB-INF/resources/";

	//static String serverName = "stat.data.abs.gov.au";
	static String BASE_URL = "http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetDataStructure/";


	public static void main(String[] args) throws IOException{

		//TODO: Export to config file
		ArrayList<DatasetDownloadParameters> listOfDatasetDownloadParameters = new ArrayList<DatasetDownloadParameters>();
		listOfDatasetDownloadParameters.add(new DatasetDownloadParameters("Census_2011","ABS_CENSUS2011_B%02d", 46));
		listOfDatasetDownloadParameters.add(new DatasetDownloadParameters("Census_2016","ABS_C16_T%02d_SA", 28));

		for(DatasetDownloadParameters downloadParameters : listOfDatasetDownloadParameters) {

			ArrayList<Dataset> datasets = new ArrayList<Dataset>();


			for(int i = 1; i <= downloadParameters.numCollections; i++){
				String datasetName = String.format(downloadParameters.dsNameUnformatted,i);

				InputStream in = new URL( BASE_URL + datasetName).openStream();

				try {
					String line = IOUtils.toString( in, Charset.forName("UTF-8") );

					Document doc = Utils.XMLToDocument(line);				

					ArrayList<String> dimensionNameList = findDimensions(doc);

					ArrayList<Dimension> dimensions = new ArrayList<Dimension>();

					for(String dimName : dimensionNameList){
						HashMap<String,String> map = findCodeLists(doc, dimName);
						String dimLabel = findDimLabel(doc, dimName);
						Dimension dim = new Dimension(dimLabel,dimName, map);
						dimensions.add(dim);
					}

					Dataset dataset = new Dataset(datasetName,dimensions);

					datasets.add(dataset);
				} finally {
					IOUtils.closeQuietly(in);
				}
			}


			FileOutputStream fileOut = new FileOutputStream(RESOURCES+String.format("/dataset_summaries_%s.ser", downloadParameters.collectionGroupName));
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(datasets);
			out.close();
			fileOut.close();

			Dimension regionDimension = null;
			ArrayList<Dimension> dims = datasets.get(0).getDimensions();
			for(Dimension dim : dims){
				if(dim.getName().equals("Region")){
					regionDimension = dim;
				}
			}

			fileOut = new FileOutputStream(RESOURCES+String.format("/ASGS_%s.ser", downloadParameters.collectionGroupName));
			out = new ObjectOutputStream(fileOut);
			out.writeObject(regionDimension);
			out.close();
			fileOut.close();

		}

	}

	public static String findDimLabel(Document doc, String dim) {

		//if(dim.endsWith("ASGS_2011")){
		if(dim.contains("ASGS")) {
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
			dimensions.add(node.getAttributes().getNamedItem("codelist").getNodeValue());
		}
		return dimensions;
	}

	private static class DatasetDownloadParameters{
		public String collectionGroupName;
		public String dsNameUnformatted;
		public int numCollections;

		public DatasetDownloadParameters(String collectionGroupName, String DS_NAME_UNFORMATTED, int NUM_COLLECTIONS) {
			this.collectionGroupName = collectionGroupName;
			this.numCollections = NUM_COLLECTIONS;
			this.dsNameUnformatted = DS_NAME_UNFORMATTED;

		}


	}

}