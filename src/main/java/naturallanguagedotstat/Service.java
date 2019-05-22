package naturallanguagedotstat;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import naturallanguagedotstat.model.Dataset;
import naturallanguagedotstat.model.Dimension;
import naturallanguagedotstat.parser.SemanticParser;
import naturallanguagedotstat.test.LocalTest;
import naturallanguagedotstat.test.UnitTester;
import naturallanguagedotstat.utils.Utils;

import org.w3c.dom.Document;

import au.com.bytecode.opencsv.CSVWriter;


@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "main")
@Path("/main")
public class Service {

	static final String serverName = "stat.data.abs.gov.au";
	

	public static String resourcesDir = "/WEB-INF/resources/";
	static String collectionGroupName = "Census_2016";
	static String regionTypeName = "Geography Level";
	
	public static ArrayList<Dataset> datasets;
	public static Dimension regionDimension;
	

	public Service(String resourcesDir, String collectionGroupName, String regionTypeName) throws IOException, ClassNotFoundException{
		if(LocalTest.localLoad){
			datasets = Utils.loadDatasets(resourcesDir, collectionGroupName);
			regionDimension = Utils.loadRegionDimension(resourcesDir, collectionGroupName);
			SemanticParser.synonyms = Utils.loadSynonyms(resourcesDir);
			this.regionTypeName = regionTypeName;
			this.resourcesDir = resourcesDir;
		}
		

	}

	@GET
	@Path("/randomQuery")
	@Produces("text/html")
	public Response randomQuery(){
		Random r = new Random();
		int index = r.nextInt(UnitTester.TEST_QUERIES.length);
		return Response.status(200).entity(UnitTester.TEST_QUERIES[index]).build();

	}

	@GET
	@Path("/randomQueries/{n}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response randomQueries(@PathParam("n") int n){

		ArrayList<String> toReturn = new ArrayList<String>();
		for(int i = 0; i < UnitTester.TEST_QUERIES.length; i++){
			toReturn.add(UnitTester.TEST_QUERIES[i]);
		}

		Collections.shuffle(toReturn);

		while(n < toReturn.size()){
			toReturn.remove(0);
		}

		JsonBuilderFactory factory = Json.createBuilderFactory(null);
		JsonObjectBuilder builder = factory.createObjectBuilder();
		JsonArrayBuilder jab = factory.createArrayBuilder();

		for(String query : toReturn){
			jab.add(query);
		}
		builder.add("queries", jab);

		JsonObject responseObject = builder.build();

		return Response.status(200).entity(responseObject.toString()).build();

	}


	@POST
	@Path("/query")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response query(String query) throws SQLException{


		JsonObject responseObject = null;
		String error = null;
		String urlToRead = null;
		QueryBuilder queryBuilder = null;

		int responseCode = 200;

		try{

			queryBuilder = new QueryBuilder(query);
			urlToRead = queryBuilder.build();
			
			System.out.println(urlToRead);

			String data = null;
			double result = -1;

			if(!LocalTest.unitTests){
				try{
					data = Utils.httpGET(urlToRead);
				}catch(IOException e){
					throw new IOException("Unable to connect to ABS.Stat");
				}

				Document dataDocument = Utils.XMLToDocument(data);

				result = Utils.findObsValue(dataDocument);
			}


			JsonBuilderFactory factory = Json.createBuilderFactory(null);
			JsonObjectBuilder builder = factory.createObjectBuilder();
			builder.add("result", result);
			builder.add("url", urlToRead);

			String datasetNamePrefix = new String();
			if(queryBuilder.getDataset().getName().toLowerCase().contains("census")){
				datasetNamePrefix = "Census 2011 - ";
			} else {
				datasetNamePrefix = "";
			};
			builder.add("datasetName", datasetNamePrefix + queryBuilder.getDataset().getEnglishName());
			builder.add("datasetURL", "http://stat.data.abs.gov.au/Index.aspx?DatasetCode="+queryBuilder.getDataset().getName());
			for(String key : queryBuilder.getQueryInputs().keySet()){
				JsonArrayBuilder jab = factory.createArrayBuilder();
				for(String dimValue : queryBuilder.getQueryInputs().get(key)){
					jab.add(dimValue);
				}
				builder.add(key,jab);
			}
			responseObject = builder.build();
		}catch(Exception e) {
			responseCode = 500;
			error = e.toString();
			e.printStackTrace();
		} finally {

			if(!LocalTest.unitTests){
				try{
					Connection conn = null;
					PreparedStatement stmt = null;

					String host = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
					String port = System.getenv("OPENSHIFT_MYSQL_DB_PORT");

					conn = DriverManager.getConnection("jdbc:mysql://"+host+":"+port+"/naturallanguagedotstat?user=adminPyfBNpf&password=YeBCcnq6qs6K");

					String updateLog = "INSERT INTO naturallanguagedotstat.log VALUES (null, ?, ?, ?, ?, ?, NOW())";

					stmt = conn.prepareStatement(updateLog);
					stmt.setString(1, query);
					stmt.setString(2, error);
					stmt.setString(3, urlToRead);
					stmt.setString(4, queryBuilder.getQueryInputs().toString());
					stmt.setInt(5, responseCode);
					stmt.executeUpdate();

					if (stmt != null) {
						stmt.close();
					}

				}catch(Exception e){
					// do nothing
				}finally{
					// also do nothing
				}
			}
		}

		return Response.status(responseCode).entity(responseObject != null ? responseObject.toString() : error).build();

	}

	@GET
	@Path("/log")
	@Produces("text/csv")
	public Response log() throws SQLException, IOException{

		Connection conn = null;
		Statement stmt = null;

		String host = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
		String port = System.getenv("OPENSHIFT_MYSQL_DB_PORT");

		conn = DriverManager.getConnection("jdbc:mysql://"+host+":"+port+"/naturallanguagedotstat?user=adminPyfBNpf&password=YeBCcnq6qs6K");

		String sqlQuery = "select * from naturallanguagedotstat.log order by date desc LIMIT 0, 1000";
		stmt = conn.createStatement();

		ResultSet rs = stmt.executeQuery(sqlQuery);

		StringWriter sw = new StringWriter();

		CSVWriter writer = new CSVWriter(sw);

		writer.writeAll(rs, true);

		String toReturn = sw.toString();

		writer.close();
		rs.close();

		if (stmt != null) {
			stmt.close();
		}

		return Response.status(200).entity(toReturn).build();

	}
}
