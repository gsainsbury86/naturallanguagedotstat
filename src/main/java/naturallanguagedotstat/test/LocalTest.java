package naturallanguagedotstat.test;

import java.io.IOException;
import java.io.StringReader;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.ws.rs.core.Response;

import naturallanguagedotstat.QueryBuilder;
import naturallanguagedotstat.Service;
import naturallanguagedotstat.model.Dataset;
import naturallanguagedotstat.model.Dimension;

public class LocalTest {

	public static boolean debug = false;
	public static boolean test = false;
	public static boolean localLoad = false;

	public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException{

		debug = true;
		localLoad = true;
		test = true;


		/* The following queries fail for various as it does not successfully identify the region....
		
		*/

		// The following queries are correct and now should be moved to UnitTester 
		/*
		 */

		//printQueryResult("How many men in Sandy Bay?") ;
//		printQueryResult("How many men aged 35-40 years in Moonah work in agriculture?") ;
//		printQueryResult("How many men in Sandy Bay?") ;
//		printQueryResult("CPI") ;

		
		printQueryResult("What is the current CPI?");
		

//		Service s = new Service();
//		
//		ArrayList<Dataset> datasets = s.loadDatasets();
//		
//		Dataset ds = datasets.get(46);
//		for(Dimension dim : ds.getDimensions()){
//			System.out.println(dim);
//		}
		
//		
//		Service s = new Service();
//		
//		for(Dataset ds : s.loadDatasets()){
//			System.out.println(ds.getName());
//		}


	}
	
	public static void printQueryResult( String str) throws IOException, ClassNotFoundException, SQLException{
		Service service = new Service();
		
		Response res = service.query(str);


		JsonReader jsonReader = Json.createReader(new StringReader((String) res.getEntity()));
		JsonObject object = jsonReader.readObject();
		jsonReader.close();
		System.out.println(object);//.getInt("result")+ " <-- " + str +"\n");
	}

}
