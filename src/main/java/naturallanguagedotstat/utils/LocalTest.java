package naturallanguagedotstat.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;

import naturallanguagedotstat.Service;
import naturallanguagedotstat.model.Dataset;
import naturallanguagedotstat.model.Dimension;

public class LocalTest {


	public static void main(String[] args) throws IOException, ClassNotFoundException{

		Service service = new Service();
		
		service.query(null);

	}
	
}
