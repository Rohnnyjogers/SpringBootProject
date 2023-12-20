package parser;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.json.simple.parser.ParseException;
import org.xml.sax.SAXException;

import com.spring.springbootproject.entity.Emission;



public class ParseAndPersistMain {

	public static void main(String[] args) throws IOException, ParseException, ParserConfigurationException, SAXException {
		
		Parser parser = new Parser();
		EmissionDAO eDAO = new EmissionDAO();
		
		parser.setMap(parser.descriptionMap());
		
		ArrayList<Emission> emissionsList = parser.parseJSON();
		ArrayList<Emission> xmlList = parser.parseXML();
		
		emissionsList.addAll(xmlList);
		
		for(Emission e: emissionsList) {
			
			eDAO.persist(e);
		}
	}

}
