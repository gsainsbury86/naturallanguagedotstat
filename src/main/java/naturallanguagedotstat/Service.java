package naturallanguagedotstat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

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
import naturallanguagedotstat.utils.Utils;

import org.w3c.dom.Document;


@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "main")
@Path("/main")
public class Service {

	private static final String local_webapp = "src/main/webapp/";
	private static final String RES_DIR = "/WEB-INF/resources/";
	private static final String WEB_INF = "/WEB-INF/";
	static final String serverName = "stat.abs.gov.au";

	@javax.ws.rs.core.Context 
	ServletContext context;

	public Service() throws IOException, ClassNotFoundException{
		//TODO: Try to pre-load
	}

	@GET
	@Path("/")
	public String landing() throws FileNotFoundException, IOException, ClassNotFoundException{
		//TODO: Serve as static page
		InputStream fileIn = context.getResourceAsStream(WEB_INF+"index.html");
		InputStreamReader isr = new InputStreamReader(fileIn);
		BufferedReader br = new BufferedReader(isr);
		StringBuffer sb = new StringBuffer();
		String s;
		while ((s = br.readLine()) != null)
			sb.append(s);	
		return new String(sb);
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Dataset> loadDatasets() throws IOException, ClassNotFoundException,
	FileNotFoundException {

		ArrayList<Dataset> datasets = new ArrayList<Dataset>();

		InputStream fileIn;
		if(LocalTest.localLoad){
			fileIn = new FileInputStream(new File(local_webapp+RES_DIR+"datasets.ser"));
		}else{
			fileIn = context.getResourceAsStream(RES_DIR+"datasets.ser");
		}
		ObjectInputStream objIn = new ObjectInputStream(fileIn);
		datasets = (ArrayList<Dataset>) objIn.readObject();
		objIn.close();
		fileIn.close();

		return datasets;
	}


	public Dimension loadASGS_2011() throws IOException, ClassNotFoundException,
	FileNotFoundException {
		InputStream fileIn;
		if(LocalTest.localLoad){
			fileIn = new FileInputStream(new File(local_webapp+RES_DIR+"ASGS_2011.ser"));
		}else{
			fileIn = context.getResourceAsStream(RES_DIR+"ASGS_2011.ser");
		}
		ObjectInputStream objIn = new ObjectInputStream(fileIn);
		Dimension ASGS2011 = (Dimension) objIn.readObject();
		objIn.close();
		fileIn.close();

		return ASGS2011;

	}

	@GET
	@Path("/query/{query}")
	@Produces("application/json")
	public Response query(@PathParam("query") String query) throws SQLException{



		ArrayList<Dataset> datasets = null;
		Dimension ASGS2011 = null;
		JsonObject responseObject = null;
		String error = null;
		String urlToRead = null;
		QueryBuilder queryBuilder = null;

		int responseCode = 200;

		try{
			datasets = loadDatasets();
			ASGS2011 = loadASGS_2011();

			queryBuilder = new QueryBuilder(query, datasets, ASGS2011);
			urlToRead = queryBuilder.build();

			String data = null;
			int result = -1;

			if(!LocalTest.test){
				data = Utils.httpGET(urlToRead);

				Document dataDocument = Utils.XMLToDocument(data);

				result = Utils.findObsValue(dataDocument);
			}

			JsonBuilderFactory factory = Json.createBuilderFactory(null);
			JsonObjectBuilder builder = factory.createObjectBuilder();
			builder.add("result", result);
			builder.add("url", urlToRead);
			builder.add("Region",queryBuilder.getRegion());
			for(String key : queryBuilder.getQueryInputs().keySet()){
				JsonArrayBuilder jab = factory.createArrayBuilder();
				for(String dimValue : queryBuilder.getQueryInputs().get(key)){
					jab.add(dimValue);
				}
				builder.add(key,jab);
			}

			responseObject = builder.build();


		} catch(Exception e) {
			responseCode = 500;
			error = e.toString();
		} finally {

			if(!LocalTest.test){
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

		return Response.status(responseCode).entity(responseObject != null ? responseObject.toString() : null).build();

	}
}
