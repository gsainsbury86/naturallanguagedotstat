package naturallanguagedotstat.utils;

import java.io.IOException;

import naturallanguagedotstat.Service;

public class LocalTest {


	public static void main(String[] args) throws IOException, ClassNotFoundException{

		Service service = new Service();
		
		//service.query("What is the population of Sandy Bay");
		service.query("How many women in Sandy Bay are there");

	}
	
}
