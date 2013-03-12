import net.webservicex.Airport;
import net.webservicex.GetAirportInformationByAirportCode;
import net.webservicex.GetAirportInformationByISOCountryCode;
import generated.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import google.*;
/**
 *
 * @author Dougan
 */
public class Main {
    
/*
 *  getAirportInformationByAirportCode was automatically created dragging the
 *  named method from Web Service References->airport
 */
    private static String getAirportInformationByAirportCode(java.lang.String airportCode) {
        net.webservicex.Airport service = new net.webservicex.Airport();
        net.webservicex.AirportSoap port = service.getAirportSoap();
        return port.getAirportInformationByAirportCode(airportCode);
    }

/*
 *  getAirportInformationByAirportCode was automatically created dragging the
 *  named method from Web Service References->globalweather
 */
    private static String getWeather(java.lang.String cityName, java.lang.String countryName) {
        net.webservicex.GlobalWeather service = new net.webservicex.GlobalWeather();
        net.webservicex.GlobalWeatherSoap port = service.getGlobalWeatherSoap();
        return port.getWeather(cityName, countryName);
    }
    
    
    public static void main(String[] args) throws JAXBException, MalformedURLException, IOException
    {
        // create a new instance of jaxb and unmarshaller
        JAXBContext jaxbContext = JAXBContext.newInstance("generated");
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        //Read the output from the webservice, which is a string of xml.
        StringReader reader = new StringReader(Main.getAirportInformationByAirportCode("SFO"));
        
        //Insert the xml data into local xml instances of the airport output
        NewDataSet airports = (NewDataSet) unmarshaller.unmarshal(reader);
        List<Table> airportList = airports.getTable();
     
        //Reset the jaxb and unmarshaller
        jaxbContext = JAXBContext.newInstance("generated");
        unmarshaller = jaxbContext.createUnmarshaller();

        //Read in the output from the weather service, which is also a string of xml
        reader = new StringReader(getWeather("san francisco", "united states"));
        
        //This particular output only has one weather object.
        CurrentWeather weather = (CurrentWeather) unmarshaller.unmarshal(reader);

        URL url = new URL("http://maps.googleapis.com/maps/api/distancematrix/xml?origins=Seattle&destinations=San+Francisco&mode=driving&sensor=false&units=imperial");

        // Get the input stream through URL Connection
        URLConnection con = url.openConnection();
        InputStream is =con.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuilder buildStr = new StringBuilder();
        String line = null;

        // read each line and write to System.out
        while ((line = br.readLine()) != null) {
            buildStr.append(line);
        }
        
        
        jaxbContext = JAXBContext.newInstance("google");
        unmarshaller = jaxbContext.createUnmarshaller();
        
        reader = new StringReader(buildStr.toString());
        DistanceMatrixResponse dist = (DistanceMatrixResponse) unmarshaller.unmarshal(reader);
       
        System.out.println(dist.getRow().getElement().getDistance().getText());
        
        
//Test the local objects
        
    for(Table airport: airportList)
        System.out.println(airport.getCityOrAirportName());
       
        System.out.println(weather.getTemperature());
  
    }


    
}
