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
import naturallanguagedotstat.utils.Utils;

public class ContextListener implements ServletContextListener {

	private ServletContext context = null;
	//private static final String RES_DIR = "/WEB-INF/resources/";


	public void contextInitialized(ServletContextEvent event) {
		context = event.getServletContext();
		try {
			Service.datasets = Utils.loadDatasets(Service.resourcesDir, Service.collectionGroupName);
			Service.regionDimension = Utils.loadRegionDimension(Service.resourcesDir, Service.collectionGroupName);
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


	public LinkedHashMap<String, String> loadSynonyms() throws IOException, ClassNotFoundException,
	FileNotFoundException {
		InputStream fileIn;
		fileIn = context.getResourceAsStream(Service.resourcesDir+"synonyms.csv");

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