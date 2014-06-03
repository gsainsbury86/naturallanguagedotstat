package naturallanguagedotstat.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import naturallanguagedotstat.Service;
import naturallanguagedotstat.model.Dataset;
import naturallanguagedotstat.model.Dimension;

public class LocalTest {

	public static boolean debug = false;

	public static void main(String[] args) throws IOException, ClassNotFoundException{

		debug = true;

		Service service = new Service();
		
		//service.query("What is the population of Sandy Bay");
		//service.query("how many men aged 20-24 are in Sydney?");
		
		ArrayList<Dataset> datasets = service.loadDatasets();
		
		for(Dimension dim : datasets.get(3).getDimensions()){
			HashMap<String, String> map = dim.getCodelist();
			System.out.println(dim.getName() + " - " + map);
		}
		
	}
	
}
