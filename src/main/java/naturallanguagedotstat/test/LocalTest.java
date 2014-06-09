package naturallanguagedotstat.test;

import java.io.IOException;
import java.util.ArrayList;

import naturallanguagedotstat.Service;
import naturallanguagedotstat.model.Dataset;
import naturallanguagedotstat.model.Dimension;

public class LocalTest {

	public static boolean debug = false;
	public static boolean localLoad = false;

	public static void main(String[] args) throws IOException, ClassNotFoundException{

		debug = true;
		localLoad = true;

		Service service = new Service();
		
		/* The following queries fail for various reasons.
		// service.query("How many men in Goulburn are from India?"); // 30  //Can't do this type yet, where it does not require an Age dimension.....
		 */

		
		/* The following queries are correct and now should be moved to UnitTester 

		 */
		service.query("How many women in Braidwood are divorced"); //150
	}

}
