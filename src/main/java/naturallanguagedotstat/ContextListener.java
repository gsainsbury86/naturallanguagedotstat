package naturallanguagedotstat;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import naturallanguagedotstat.model.Dataset;
import naturallanguagedotstat.model.Dimension;
import naturallanguagedotstat.parser.SemanticParser;

public class ContextListener implements ServletContextListener {

	private ServletContext context = null;
	private static final String RES_DIR = "/WEB-INF/resources/";


	public void contextInitialized(ServletContextEvent event) {
		context = event.getServletContext();
		try {
			Service.datasets = loadDatasets();
			Service.ASGS2011 = loadASGS_2011();
			SemanticParser.synonyms = loadSynonyms();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}
	public void contextDestroyed(ServletContextEvent event) {
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Dataset> loadDatasets() throws IOException, ClassNotFoundException,
	FileNotFoundException {

		ArrayList<Dataset> datasets = new ArrayList<Dataset>();

		InputStream fileIn;
		fileIn = context.getResourceAsStream(RES_DIR+"datasets.ser");
		ObjectInputStream objIn = new ObjectInputStream(fileIn);
		datasets = (ArrayList<Dataset>) objIn.readObject();
		objIn.close();
		fileIn.close();

		HashSet<Dataset> toRemove = new HashSet<Dataset>();

		for(Dataset ds : datasets){
			String name = ds.getName();
			if(!(name.startsWith("ABS_CENSUS2011_B") && name.length() == 18)
					&& !name.equals("CPI") 
					&& !name.equals("LF") 
					&& !name.equals("MERCH_EXP") 
					&& !name.equals("MERCH_IMP") 
					&& !name.equals("BOP") 
					&& !name.equals("RT") 
					){
				toRemove.add(ds);
			}
		}

		datasets.removeAll(toRemove);
		return datasets;
	}


	public Dimension loadASGS_2011() throws IOException, ClassNotFoundException,
	FileNotFoundException {
		InputStream fileIn;
		fileIn = context.getResourceAsStream(RES_DIR+"ASGS_2011.ser");
		ObjectInputStream objIn = new ObjectInputStream(fileIn);
		Dimension ASGS2011 = (Dimension) objIn.readObject();
		objIn.close();
		fileIn.close();

		return ASGS2011;

	}

	public LinkedHashMap<String, String> loadSynonyms() throws IOException, ClassNotFoundException,
	FileNotFoundException {
		InputStream fileIn;
		fileIn = context.getResourceAsStream(RES_DIR+"synonyms.csv");

		LinkedHashMap<String, String> toReturn = new LinkedHashMap<String, String>();

		BufferedReader br = new BufferedReader(new InputStreamReader(fileIn));
		String line = null;

		while ((line = br.readLine()) != null) {

			// use comma as separator
			String[] newSynonym = line.split(",");

			// fix for commas in values e.g. Korea, Republic of South
			String value = "";
			for(int i = 1; i < newSynonym.length; i++){
				value+=newSynonym[i] + ",";
			}

			value = value.substring(0,value.length() - 1);

			toReturn.put(newSynonym[0],value);
		}
		
		br.close();
		return toReturn;

	}
}