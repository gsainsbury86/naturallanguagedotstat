package naturallanguagedotstat.test;

import java.io.IOException;

import naturallanguagedotstat.Service;

public class LocalTest {

	public static boolean debug = false;

	public static void main(String[] args) throws IOException, ClassNotFoundException{

		debug = true;

		Service service = new Service();
		
		service.query("What is the population of Sandy Bay");
		service.query("how many men aged 20-24 are in Sydney?");
		service.query("What is the population of Australia?");
		
		
	}
	
}
