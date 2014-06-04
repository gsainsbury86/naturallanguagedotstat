package naturallanguagedotstat.parser;

import naturallanguagedotstat.test.TestModule;

public class NaturalSearch {

	public static void main(String[] args) {

		// run unitTests
		TestModule testModule = new TestModule();
		if(testModule.runAllTests()){
			System.out.println("All automated tests passed. \n");
		} else {
			System.out.println("Error: automated test failed!\n");
		};

		// .........................................................................
		// Playground
		// .........................................................................

		System.exit(0);	

	}
}
