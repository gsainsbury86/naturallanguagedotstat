package naturallanguagedotstat.utils;

import java.io.IOException;

import naturallanguagedotstat.Service;


public class LocalTest {

	public static boolean debug = false;

	public static void main(String[] args) throws IOException, ClassNotFoundException{

		Service service = new Service();
		
		debug = true;
		//service.query("What is the population of Sandy Bay");
		service.query("How many women in Goulburn are 10-14 years old?");

	}
	
}
