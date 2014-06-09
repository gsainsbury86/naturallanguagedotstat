package naturallanguagedotstat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
import naturallanguagedotstat.parser.NumericParser;
import naturallanguagedotstat.parser.SemanticParser;
import naturallanguagedotstat.test.LocalTest;
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
	private static final String WEB_INF = "/WEB-INF/";
	private static final String serverName = "stat.abs.gov.au";

	@javax.ws.rs.core.Context 
	ServletContext context;

	public Service() throws IOException, ClassNotFoundException{

	}

	@GET
	@Path("/")
	public String landing() throws FileNotFoundException, IOException, ClassNotFoundException{
		InputStream fileIn = context.getResourceAsStream(WEB_INF+"index.html");
		//		InputStream fileIn = this.getClass().getResourceAsStream(RES_DIR+"index.html");
		InputStreamReader isr = new InputStreamReader(fileIn);
		BufferedReader br = new BufferedReader(isr);
		StringBuffer sb = new StringBuffer();
		String s;
		while ((s = br.readLine()) != null)
			sb.append(s);	
		return new String(sb);
	}

	public ArrayList<Dataset> loadDatasets() throws IOException, ClassNotFoundException,
	FileNotFoundException {

		ArrayList<Dataset> datasets = new ArrayList<Dataset>();

		for(int i = 1; i <= 46; i++){
			String dsNumber = Utils.intToString(i,2);

			InputStream fileIn;
			if(LocalTest.localLoad){
				fileIn = new FileInputStream(new File(local_webapp+RES_DIR+"ABS_CENSUS2011_B"+dsNumber+".ser"));
			}else{
				fileIn = context.getResourceAsStream(RES_DIR+"ABS_CENSUS2011_B"+dsNumber+".ser");
			}
			ObjectInputStream objIn = new ObjectInputStream(fileIn);
			Dataset ds = (Dataset) objIn.readObject();
			datasets.add(ds);
			objIn.close();
			fileIn.close();
		}

		return datasets;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Dataset> loadDatasetsLight() throws IOException, ClassNotFoundException,
	FileNotFoundException {

		ArrayList<Dataset> datasets = new ArrayList<Dataset>();

		InputStream fileIn;
		if(LocalTest.localLoad){
			fileIn = new FileInputStream(new File(local_webapp+RES_DIR+"dataset_summaries.ser"));
		}else{
			fileIn = context.getResourceAsStream(RES_DIR+"dataset_summaries.ser");
		}
		ObjectInputStream objIn = new ObjectInputStream(fileIn);
		datasets = (ArrayList<Dataset>) objIn.readObject();
		objIn.close();
		fileIn.close();

		return datasets;
	}


	public Dimension loadASGS_2011() throws IOException, ClassNotFoundException,
	FileNotFoundException {
		InputStream fileIn;
		if(LocalTest.localLoad){
			fileIn = new FileInputStream(new File(local_webapp+RES_DIR+"ASGS_2011.ser"));
		}else{
			fileIn = context.getResourceAsStream(RES_DIR+"ASGS_2011.ser");
		}
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

		ArrayList<Dataset> datasets;
		Dimension ASGS2011;

		datasets = loadDatasetsLight();
		ASGS2011  = loadASGS_2011();

		SemanticParser semanticParser = new SemanticParser(query);
		semanticParser.parseText();

		HashMap<String,String> queryInputs = semanticParser.getDimensions();	
		
		if(LocalTest.debug)
			System.out.println(queryInputs);

		String region = queryInputs.get("region");
		queryInputs.remove("region");
		
		ArrayList<String> dimensionNames = new ArrayList<String>();
		dimensionNames.addAll(queryInputs.keySet());
		
		Dataset dataset = Utils.findDatasetWithDimensionNames(datasets, dimensionNames);

		if(LocalTest.debug)
			System.out.println(dataset.getName());

		dataset = loadDataset(dataset.getName());



		if(queryInputs.get("Age") != null){
			optimizeAgeCodeList(queryInputs, dataset);
			// if(LocalTest.debug) System.out.println(queryInputs);
		};

		if(LocalTest.debug)
			System.out.println(queryInputs);

		//TODO: make sure dimension value exists
		String urlToRead = queryBuilder(dataset, ASGS2011, region, queryInputs);

		if(LocalTest.debug)
			System.out.println("URLToRead is "+urlToRead);

		String data = Utils.httpGET(urlToRead);

		Document dataDocument = Utils.XMLToDocument(data);

		String resultString = findObsValue(dataDocument);

		if(LocalTest.debug)
			System.out.println(resultString);

		return resultString;

	}



	private Dataset loadDataset(String name) throws IOException, ClassNotFoundException {

		InputStream fileIn;
		if(LocalTest.localLoad){
			fileIn = new FileInputStream(new File(local_webapp+RES_DIR+name+".ser"));
		}else{
			fileIn = context.getResourceAsStream(RES_DIR+name+".ser");
		}
		ObjectInputStream objIn = new ObjectInputStream(fileIn);
		Dataset dataset = (Dataset) objIn.readObject();
		objIn.close();
		fileIn.close();

		return dataset;
	}

	/**
	 * Builds the query for the given dataset and specified dimension values
	 * 
	 * @param ds the Dataset object
	 * @param queryDimensionValues a HashMap<String, String> where the key is the dimension name and the value is  the range
	 * @param region the Region (as appears in ASGS 2011)
	 * @return
	 */
	public static String queryBuilder(Dataset ds, Dimension ASGS2011, String region, HashMap<String, String> queryDimensionValues){
		String url;

		url = "http://";

		url += serverName;

		url += "/restsdmx/sdmx.ashx/GetData/";

		url += ds.getName()+"/";

		/* ensure order */
		for(Dimension dim : ds.getDimensions()){
			//System.out.println("dim is "+dim);
			for(String dimKey : queryDimensionValues.keySet()){
				if(dim.getName().equals(dimKey)){
					//System.out.println(Utils.findValue(dim.getCodelist(),queryDimensionValues.get(dimKey)) );
					url += Utils.findValue(dim.getCodelist(),queryDimensionValues.get(dimKey)) + ".";
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




	// ........................................................................................

	private String getKeyForMaxValue (HashMap< String, Double> map){
		double maxValue = -9999999;
		String keyForMaxValue = null;

		for (String key : map.keySet()) {
			if(map.get(key) > maxValue){keyForMaxValue = key; maxValue = map.get(key);};
		};
		return keyForMaxValue;
	};



	private void optimizeAgeCodeList(HashMap<String, String> queryInputs, Dataset dataset) {
		if(queryInputs.get("Age")==null){return;};
		if(queryInputs.get("Age")=="Total all ages"){return;}


		NumericParser ageQueryParser = new NumericParser(queryInputs.get("Age") );

		int a0 = Integer.parseInt(ageQueryParser.getExplicitNumbers().get(0) );
		int a1 = (ageQueryParser.getExplicitNumbers().size() >1) 
				? Integer.parseInt(ageQueryParser.getExplicitNumbers().get(1) ) : -1;



		HashMap<String, String> ageCodeList = null;
		for(Dimension dim : dataset.getDimensions()){
			if(dim.getName().equals("Age")){
				ageCodeList = dim.getCodelist();
			};
		};

		List<String> ageCodeListDescriptions = new ArrayList<String>(ageCodeList.values());

		HashMap< String, Double> matches = new HashMap< String, Double>();

		Double overlapScore;
		for (String descr: ageCodeListDescriptions){
			NumericParser ageDescriptionParser = new NumericParser(descr);

			int b0 = (ageDescriptionParser.getExplicitNumbers().size() >0) 
					? Integer.parseInt(ageDescriptionParser.getExplicitNumbers().get(0) ) : -1;

			int b1 = (ageDescriptionParser.getExplicitNumbers().size() >1) 
					? Integer.parseInt(ageDescriptionParser.getExplicitNumbers().get(1) ) : -1;

			overlapScore =  getOverlapScore(a0, a1, b0, b1);
			if(overlapScore > 0 ){matches.put(descr, overlapScore);}
			ageDescriptionParser = null;
		};

		queryInputs.put("Age", getKeyForMaxValue (matches));
	}

	private Double getOverlapScore(int a0, int a1, int b0, int b1) {
		if(a1 ==-1  && b1 == -1){
			if(a0==b0){
				return 1.00;
			};
		};


		if(b1 != -1){
			if(b0 <= a0 && a0 <= b1){
				return Math.min( 1.0 * (b1-a0+1)/(b1-b0+1), 1.0* (b1-a0+1)/(a1-a0+1) );
			};
		};


		if(a1 != -1){
			if(a0 <= b0 && b0 <= a1){
				return Math.min(1.0*  (a1-b0+1)/(b1-b0+1), 1.0* (a1-b0+1)/(a1-a0+1) ) ;
			};
		};

		if(a1 !=-1  && b1 !=-1 ){
			if(b0 <= a0 && a1 <= b1){
				return Math.min( 1.0* (a1-a0+1)/(b1-b0+1), 1.0* (a1-a0+1)/(a1-a0+1) );
			};
		};

		// reutrn any negative value to signify a null result.
		return -1.00;
	};


}
