import javax.xml.ws.Endpoint;


public class ServerPublisher 
{
    /**
     * Main.
     * @param args the server port number
     */
    public static void main(String args[]) 
    {
        Server ts = new ServerImp();
        String url = String.format("http://localhost:%s/airport", 9000);
        
        System.out.printf("Publishing service %s to %s ...\n", 
                          ts.getClass().getName(), url);
        
        // 1st argument is the publication URL
        // 2nd argument is an SIB instance
        Endpoint.publish(url, ts);
    }
}
