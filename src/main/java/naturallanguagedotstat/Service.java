package naturallanguagedotstat;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import naturallanguagedotstat.model.Dataset;
import naturallanguagedotstat.model.Dimension;
import naturallanguagedotstat.test.LocalTest;
import naturallanguagedotstat.test.UnitTester;
import naturallanguagedotstat.utils.Utils;

import org.w3c.dom.Document;

import au.com.bytecode.opencsv.CSVWriter;


@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "main")
@Path("/main")
public class Service {

	private static final String WEB_INF = "/WEB-INF/";
	static final String serverName = "stat.abs.gov.au";
	public static ArrayList<Dataset> datasets;
	public static Dimension ASGS2011;

	@javax.ws.rs.core.Context 
	ServletContext context;

	public Service() throws IOException, ClassNotFoundException{
		if(LocalTest.localLoad){
			datasets = Utils.loadDatasets();
			ASGS2011 = Utils.loadASGS_2011();
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


	//TODO: Change this to a POST. No rush here though.
	@GET
	@Path("/query/{query}")
	@Produces("application/json")
	public Response query(@PathParam("query") String query) throws SQLException{

		JsonObject responseObject = null;
		String error = null;
		String urlToRead = null;
		QueryBuilder queryBuilder = null;

		int responseCode = 200;

		try{


			queryBuilder = new QueryBuilder(query, datasets, ASGS2011);
			urlToRead = queryBuilder.build();


			String data = null;
			double result = -1;

			if(!LocalTest.unitTests){
				data = Utils.httpGET(urlToRead);

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
			builder.add("datasetURL", "http://stat.abs.gov.au/Index.aspx?DatasetCode="+queryBuilder.getDataset().getName());
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
		} finally {

			if(!LocalTest.unitTests){
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
