import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.bind.JAXBException;

/*
 *  Adapted from Prof. Ronald Mak's ServerImplementation class
 */

@WebService(targetNamespace = "http://my.org/ns/")
@SOAPBinding(style = Style.RPC)
public interface Server
{
  //  @WebMethod String getAirportInfo(String airportName, String country);
    @WebMethod String getAirportInformationByAirportCode(String airportCode);
    @WebMethod String getTempatureOfCity(String cityName, String country);
    @WebMethod String getDrivingDistance(String cityA, String cityB) throws MalformedURLException, JAXBException, IOException;
    @WebMethod String getDrivingTime(String cityA, String cityB) throws MalformedURLException, JAXBException, IOException;
    @WebMethod String getWeatherOfCity(String cityName, String country);
    @WebMethod String getAirportInformationByCityOrAirportName(String airportName);
}