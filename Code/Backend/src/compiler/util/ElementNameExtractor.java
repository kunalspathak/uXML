package compiler.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
* This is a utility class that extracts element names from given schema file
* and convert the names in below format :
* final public static String ELEMENT_NAME = "element_name";
*
*/
public class ElementNameExtractor {

	public static void main(String args[]) {
		String fileName = args[0];
		BufferedReader inputFile;
		try {
			inputFile = new BufferedReader(new FileReader(fileName));
			String values[]= null;
			String answer = null;
			String text = null;
			while ((text = inputFile.readLine()) != null) {
				if(!text.trim().startsWith("<xs:element name="))
					continue;
				values = text.trim().split("<xs:element name=\"");
				answer = values[1].replaceAll("\"", "");
				answer = answer.replaceAll(">", "");
				System.out.println("final public static String " + answer.toUpperCase() + " = \"" + answer + "\";");
			}
			inputFile.close();
		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
}
