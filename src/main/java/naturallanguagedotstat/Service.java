package naturallanguagedotstat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import javax.json.Json;
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
	public Response query(@PathParam("query") String query) throws FileNotFoundException, IOException, ClassNotFoundException{

		ArrayList<Dataset> datasets = loadDatasets();
		Dimension ASGS2011 = loadASGS_2011();

		QueryBuilder queryBuilder = new QueryBuilder(query, datasets, ASGS2011);
		String urlToRead = queryBuilder.build();

		String data = Utils.httpGET(urlToRead);

		Document dataDocument = Utils.XMLToDocument(data);

		String resultString = Utils.findObsValue(dataDocument);

		JsonObjectBuilder builder = Json.createObjectBuilder();
		builder.add("result", resultString);
		builder.add("url", urlToRead);
		builder.add("Region",queryBuilder.getRegion());
		for(String key : queryBuilder.getQueryInputs().keySet()){
			for(String dimValue : queryBuilder.getQueryInputs().get(key)){
				builder.add(key, dimValue);
			}
		}
		JsonObject myObject = builder.build();

		return Response.status(200).entity(myObject.toString()).build();// + "<br><br>" + "<br>" + urlToRead;

	}
}
