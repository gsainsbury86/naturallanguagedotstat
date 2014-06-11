package naturallanguagedotstat.resprep;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import naturallanguagedotstat.model.Dataset;
import naturallanguagedotstat.model.Dimension;
import naturallanguagedotstat.utils.Utils;

import org.w3c.dom.Document;

/**
 * This will download all 46 Census DSDs to the current directory and label them 1.xml, 2.xml, ... 
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


	public static void main(String[] args) throws IOException{
		

//		for(int i = 1; i <= 46; i++){
//		String dsNumber = Utils.intToString(i,2);
//			PrintWriter writer = new PrintWriter(RESOURCES+dsNumber+".xml", "UTF-8");
//			writer.println(Utils.httpGET("http://"+serverName+"/restsdmx/sdmx.ashx/GetDataStructure/ABS_CENSUS2011_B"+Utils.intToString(i, 2)+"/ABS"));
//			writer.close();
//
//		}


		datasets = new ArrayList<Dataset>();

		for(int i = 1; i <= 46; i++){
			String dsNumber = Utils.intToString(i,2);
			File f = new File(RESOURCES+dsNumber+".xml");
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			String line = br.readLine();
			br.close();
			Document doc = ConceptAnalyser.XMLToDocument(line);

			Dataset dataset = new Dataset(datasetName+"_B"+dsNumber,null);

			ArrayList<String> dimensionNameList = Utils.findDimensions(doc);

			ArrayList<Dimension> dimensions = new ArrayList<Dimension>();

			for(String dimName : dimensionNameList){
				HashMap<String,String> map = Utils.findCodeLists(doc, dimName);
				if(dimName.endsWith("ASGS_2011") || dimName.endsWith("FREQUENCY") || dimName.endsWith("STATE") || dimName.endsWith("REGIONTYPE")){
					map = null;
				}
				String dimLabel = Utils.findDimLabel(doc, dimName);
				Dimension dim = new Dimension(dimLabel,dimName, map);
				dimensions.add(dim);
			}

			dataset.setDimensions(dimensions);
			datasets.add(dataset);
		}
		
		FileOutputStream fileOut = new FileOutputStream(RESOURCES+"/datasets.ser");
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

}
