import generated.NewDataSet;
import generated.Table;
import google.DistanceMatrixResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebService;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
@WebService(endpointInterface = "Server", targetNamespace = "http://my.org/ns/")
public class ServerImp implements Server
{
    
    
    @Override
    public String getAirportInfo(String airportCode, String country) {

        return getAirportInformationByAirportCode(airportCode);
        
    }
    @Override    
    public String getAirportInformationByAirportCode(java.lang.String airportCode) {
        List<Table> airportList = null;
        try {
            net.webservicex.Airport service = new net.webservicex.Airport();
            net.webservicex.AirportSoap port = service.getAirportSoap();
            
              // create a new instance of jaxb and unmarshaller
            JAXBContext jaxbContext = JAXBContext.newInstance("generated");
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            //Read the output from the webservice, which is a string of xml.
            StringReader reader = new StringReader( port.getAirportInformationByAirportCode(airportCode));
            
            //Insert the xml data into local xml instances of the airport output
            NewDataSet airports = (NewDataSet) unmarshaller.unmarshal(reader);
            airportList = airports.getTable();   
           
        } catch (JAXBException ex) {
            Logger.getLogger(ServerImp.class.getName()).log(Level.SEVERE, null, ex);
        }
      
         return airportList.get(0).getCityOrAirportName();
    }
    @Override
        public String getTempatureOfCity(java.lang.String cityName, java.lang.String countryName) {
        net.webservicex.GlobalWeather service = new net.webservicex.GlobalWeather();
        net.webservicex.GlobalWeatherSoap port = service.getGlobalWeatherSoap();
        return port.getWeather(cityName, countryName);
    }
        @Override
        public String getDrivingDistance(java.lang.String cityNameA, java.lang.String cityNameB) throws MalformedURLException, JAXBException, IOException {

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
      
          StringReader reader;
        JAXBContext jaxbContext = JAXBContext.newInstance("google");
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        
        reader = new StringReader(buildStr.toString());
        DistanceMatrixResponse dist = (DistanceMatrixResponse) unmarshaller.unmarshal(reader);
       
        System.out.println(dist.getRow().getElement().getDistance().getText());
        return dist.getRow().getElement().getDistance().getText();
      
    }
         @Override
        public String getWeatherOfCity(java.lang.String cityName, java.lang.String countryName) {
        net.webservicex.GlobalWeather service = new net.webservicex.GlobalWeather();
        net.webservicex.GlobalWeatherSoap port = service.getGlobalWeatherSoap();
        return port.getWeather(cityName, countryName);
    }
    
}
