package naturallanguagedotstat.test;

import java.io.IOException;
import java.util.ArrayList;

import naturallanguagedotstat.Service;
import naturallanguagedotstat.model.Dataset;
import naturallanguagedotstat.model.Dimension;

public class LocalTest {

	public static boolean debug = false;

	public static void main(String[] args) throws IOException, ClassNotFoundException{

		debug = true;

		/*Service service = new Service();

		service.query("What is the population of Sandy Bay");
		service.query("how many men aged 20-24 are in Sydney?");
		service.query("What is the population of Australia?");*/

		Service service = new Service();

		ArrayList<Dataset> datasets = service.loadDatasets();
		//service.loadDataset(name);
		//service.loadDatasetsLight();

		//for(Dataset ds : datasets){
		Dataset ds = datasets.get(3);
		//System.out.println(ds);

		for(Dimension dim : ds.getDimensions()){
			if(dim.getName().equals("Age")){
				System.out.println(dim);
			}
		}



	}

}
