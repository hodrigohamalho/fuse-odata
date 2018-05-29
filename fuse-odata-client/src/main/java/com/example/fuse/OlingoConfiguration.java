package com.example.fuse;

import java.io.IOException;

import org.apache.camel.component.olingo4.Olingo4Configuration;
import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpCoreContext;
import org.springframework.stereotype.Component;

@Component
public class OlingoConfiguration extends Olingo4Configuration{
	
	String URI = "http://services.odata.org/TripPinRESTierService";
	
	public OlingoConfiguration() {
		
        try {
        		String realURI = getRealServiceUrl(URI);
			setServiceUri(realURI);
			System.out.println("Using base uri: "+realURI);
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        setContentType("application/json;charset=utf-8");
    }
	
    /*
     * Every request to the demo OData 4.0
     * (http://services.odata.org/TripPinRESTierService) generates unique
     * service URL with postfix like (S(tuivu3up5ygvjzo5fszvnwfv)) for each
     * session This method makes reuest to the base URL and return URL with
     * generated postfix
     */
    private String getRealServiceUrl(String baseUrl) throws ClientProtocolException, IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(baseUrl);
        HttpContext httpContext = new BasicHttpContext();
        httpclient.execute(httpGet, httpContext);
        HttpUriRequest currentReq = (HttpUriRequest) httpContext.getAttribute(HttpCoreContext.HTTP_REQUEST);
        HttpHost currentHost = (HttpHost) httpContext.getAttribute(HttpCoreContext.HTTP_TARGET_HOST);
        String currentUrl = (currentReq.getURI().isAbsolute()) ? currentReq.getURI().toString() : (currentHost.toURI() + currentReq.getURI());

        return currentUrl;
    }
    
    
	
}
