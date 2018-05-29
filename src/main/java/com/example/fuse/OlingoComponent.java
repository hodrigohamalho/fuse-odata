package com.example.fuse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.olingo4.Olingo4Component;
import org.apache.camel.component.olingo4.Olingo4Configuration;
import org.apache.camel.util.IntrospectionSupport;
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
public class OlingoComponent extends RouteBuilder{

//	protected static final String OLINGO_BASE_URL = "http://services.odata.org/TripPinRESTierService";
	protected static final String OLINGO_BASE_URL = "http://localhost:8080/demo-jpa/Demo.svc";

	@Override
	public void configure() throws Exception {
		Map<String, Object> options = new HashMap<>();
		options.put("serviceUri", getRealServiceUrl(OLINGO_BASE_URL));
		options.put("contentType", "application/json;charset=utf-8");

		final Olingo4Configuration configuration = new Olingo4Configuration();
		IntrospectionSupport.setProperties(configuration, options);

		// add OlingoComponent to Camel context
		final Olingo4Component component = new Olingo4Component(getContext());
		component.setConfiguration(configuration);
		getContext().addComponent("olingo4", component);
	}

	/*
	 * Every request to the demo OData 4.0
	 * (http://services.odata.org/TripPinRESTierService) generates unique
	 * service URL with postfix like (S(tuivu3up5ygvjzo5fszvnwfv)) for each
	 * session This method makes request to the base URL and return URL with
	 * generated postfix
	 */
	protected String getRealServiceUrl(String baseUrl) throws ClientProtocolException, IOException {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(baseUrl);
		HttpContext httpContext = new BasicHttpContext();
		httpclient.execute(httpGet, httpContext);
		HttpUriRequest currentReq = (HttpUriRequest)httpContext.getAttribute(HttpCoreContext.HTTP_REQUEST);
		HttpHost currentHost = (HttpHost)httpContext.getAttribute(HttpCoreContext.HTTP_TARGET_HOST);
		String currentUrl = (currentReq.getURI().isAbsolute()) ? currentReq.getURI().toString() : (currentHost.toURI() + currentReq.getURI());

		return currentUrl;
	}
}
