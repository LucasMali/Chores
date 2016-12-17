/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manager.util.curl;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 *
 * @author Lucas Maliszewski, S019356741
 * @version Nov 25, 2016, CSC-240 Assignment
 */
public abstract class ServerInterface {

    protected final String BASE_URL = "http://127.0.0.1:8080/";

     public String getResponse(String location) throws IOException {
        URL url = new URL(location);
            String _xml = "";
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
            for (String line; (line = reader.readLine()) != null;) {
                _xml += line;
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
            throw e;
        }
        return _xml;

    }
    
//    public String getResponse(String location) throws IOException {
//        
//        // Making the call
//        URL url = new URL(location);
//        
//        // This is all new to me, Java, not PHP.
//        HttpURLConnection conn = (HttpURLConnection) url.openConnection();           
//        conn.setDoOutput( true );
//        conn.setInstanceFollowRedirects( false );
//        conn.setRequestMethod( "GET" );
//        conn.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded"); 
//        conn.setRequestProperty( "charset", "utf-8");
//        conn.setUseCaches( false );
//        try( DataOutputStream wr = new DataOutputStream( conn.getOutputStream())) {
//            wr.flush();
//            wr.close();
//            
//            int responseCode = conn.getResponseCode();
//            System.out.println("\nSending 'GET' request to URL : " + url);
//            System.out.println("Response Code : " + responseCode);
//
//            BufferedReader in = new BufferedReader(
//                    new InputStreamReader(conn.getInputStream()));
//            String inputLine;
//            StringBuffer response = new StringBuffer();
//
//            while ((inputLine = in.readLine()) != null) {
//                    response.append(inputLine);
//            }
//            in.close();
//            return response.toString();
//        }
//    }
    
    
    /**
     *
     * @param location
     * @param params
     * @param method
     * @return
     */
    public String getResponse(String location, String[] params, String method) throws MalformedURLException, ProtocolException, IOException{
        // Setting up the headers
        String qp = String.join("&", params);
        byte[] postData = qp.getBytes( StandardCharsets.UTF_8 );
        int postDataLength = postData.length;
        
        // Making the call
        URL url = new URL(location);
        
        // This is all new to me, Java, not PHP.
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();           
        conn.setDoOutput( true );
        conn.setInstanceFollowRedirects( false );
        conn.setRequestMethod( method );
        conn.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded"); 
        conn.setRequestProperty( "charset", "utf-8");
        conn.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
        conn.setUseCaches( false );
        try( DataOutputStream wr = new DataOutputStream( conn.getOutputStream())) {
            wr.write( postData );
            wr.flush();
            wr.close();
            
            int responseCode = conn.getResponseCode();
            System.out.println("\nSending '"+method+"' request to URL : " + url);
            System.out.println(method+" parameters : " + qp);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
            }
            in.close();
            return response.toString();
        }
    }
}
