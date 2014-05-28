package naturallanguagedotstat.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

import naturallanguagedotstat.model.Dataset;
import naturallanguagedotstat.model.Dimension;

import org.w3c.dom.Document;

/**
 * This will download all 46 Census DSDs to the current directory and label them 1.xml, 2.xml, ... 
 * 
 * @author sainge
 *
 */
public class DSDDownloader {
	
	static HashMap<String,String> ASGS2011CodeList;
	static String serverName = "stat.abs.gov.au";
	static String datasetName = "ABS_CENSUS2011";
	static ArrayList<Dataset> datasets;
	private static Dimension ASGS2011;


	public static void main(String[] args) throws IOException{
		

//		for(int i = 1; i <= 46; i++){
//			PrintWriter writer = new PrintWriter("src/main/webapp/DSDs/"i+".xml", "UTF-8");
//			writer.println(Utils.httpGET("http://"+serverName+"/restsdmx/sdmx.ashx/GetDataStructure/ABS_CENSUS2011_B"+Utils.intToString(i, 2)+"/ABS"));
//			writer.close();
//
//		}


		datasets = new ArrayList<Dataset>();

		for(int i = 1; i <= 46; i++){
			String dsNumber = Utils.intToString(i,2);
			File f = new File("src/main/webapp/DSDs/"+dsNumber+".xml");
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
				String dimLabel = Utils.findDimLabel(doc, dimName);
				Dimension dim = new Dimension(dimLabel,dimName, map);
				dimensions.add(dim);
			}

			dataset.setDimensions(dimensions);
			datasets.add(dataset);

			FileOutputStream fileOut = new FileOutputStream("src/main/webapp/DSDs/"+dataset.getName()+".ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(dataset);
			out.close();
			fileOut.close();
		}
		
		ArrayList<Dimension> dims = datasets.get(0).getDimensions();
		for(Dimension dim : dims){
			if(dim.getName().equals("Region")){
				ASGS2011 = dim;
			}
		}
		
		FileOutputStream fileOut = new FileOutputStream("src/main/webapp/DSDs/ASGS_2011.ser");
		ObjectOutputStream out = new ObjectOutputStream(fileOut);
		out.writeObject(ASGS2011);
		out.close();
		fileOut.close();


	}

}
