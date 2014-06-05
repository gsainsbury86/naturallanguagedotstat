package naturallanguagedotstat.test;

import java.io.IOException;
import java.util.ArrayList;

import naturallanguagedotstat.Service;
import naturallanguagedotstat.model.Dataset;
import naturallanguagedotstat.model.Dimension;

public class LocalTest {

	public static boolean debug = true;

	public static void main(String[] args) throws IOException, ClassNotFoundException{

		debug = true;

		Service service = new Service();
		/*
		// the frigging space after "15-19 years " is wrecking any query with this age bracket in it. Grrr!!!

		// All these queries work correctly.
		 * 
		service.query("What is the population of Sandy Bay");
		service.query("How many married 25-30 women are in Braidwood?"); //60
		service.query("How many females married in Braidwood are 25-30?"); //60
		service.query("How many married females in Braidwood are between 25 and 30 years old?"); //60

		service.query("How many married women are in Braidwood?"); //704
		service.query("How many women are separated in Braidwood?"); //51
		service.query("How many women are divorced in Braidwood?"); //150
		service.query("How many widowed women are in Braidwood?"); //145
		service.query("How many women in Braidwood are married?"); //704
		service.query("How many women in Braidwood are divorced?"); //150

		service.query("How many females have a de facto relationship in Braidwood?"); /133
		service.query("How many people have a registered marriage in Braidwood?"); //5

		service.query("How many indigenous males are there in Braidwood?"); //5

		service.query("How many men aged 35-40 in Braidwood are managers?"); //39

		service.query("How many men aged 35-40 in Goulburn are indigenous?"); //27

		service.query("How many men aged 35-40 in Queanbeyan are in the agricultural sector?"); //43
		service.query("How many men aged 35-40 in Queanbeyan are in the mining sector?"); //11
		service.query("How many men from the manufacturing industry in Queanbeyan are aged 35-40?"); //275
		service.query("How many men from the utilities industry in Queanbeyan are aged 35-40?"); //79
		service.query("How many men from the construction industry in Queanbeyan are aged 35-40?"); //567

		//FAIL service.query("How many females in Goulburn 15-20 years old are Managers?"); //5
		service.query("How many females aged 20 to 25 in Tasmania are Managers?"); //402
		service.query("How many 20-25 year old females in Tasmania are professionals?"); // 1190
		service.query("How many 20-25 year old women in Tasmania are technicians?"); //705
		service.query("How many women aged 20-25 in Tasmania are community workers?"); //18164
		service.query("How many women aged 20-25 in Tasmania are labourers?"); //7486

		 */
		service.query("How many men aged 35-40 in Queanbeyan are in the agricultural sector?"); //43


	}

}
