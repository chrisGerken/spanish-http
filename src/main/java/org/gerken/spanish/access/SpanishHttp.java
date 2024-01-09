package org.gerken.spanish.access;

// Begin imports

import java.io.File;
import java.util.HashMap;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.util.resource.PathResource;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.util.resource.ResourceCollection;
import org.gerken.spanish.internal.SpanishPath;
import org.gerken.spanish.logic.Loader;
import org.gerken.spanish.servlet.FilterServlet;
import org.gerken.spanish.servlet.QuestionServlet;
import org.gerken.spanish.servlet.StatsServlet;
import org.gerken.spanish.servlet.StopServlet;

// End imports

public class SpanishHttp implements Runnable {

		// Begin declarations

	private int port;
	private String resourcesDir;
	
	private Server server;
	private HashMap<String, String> parms = new HashMap<String, String>();
	public static SpanishHttp common;

		// End declarations
	
	public SpanishHttp(int port, String resourcesDir) {
		this.port = port;
		this.resourcesDir = resourcesDir;
	}

	public void start() {
		// Begin start
		new Thread(this).start();
		common = this;

		// End start
	}
	
	public void stop() {
		// Begin stop
		try {
			server.stop();
			common = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		// End stop
	}
	
	public void run() {
		try {
			server = new Server();
	
			ServletContextHandler sHandler = defineServletContextHandler();
			server.setHandler(sHandler);

			HttpConfiguration config = new HttpConfiguration();
		    config.setRequestHeaderSize(65535);
		    ServerConnector http = new ServerConnector(server, new HttpConnectionFactory(config));
		    http.setPort(port);
		    server.setConnectors(new Connector[] {http});
		    
			server.start();
			server.join();
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private ServletContextHandler defineServletContextHandler() {
		ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.SESSIONS);
		handler.setContextPath("/spanish");
		ResourceCollection recoll = new ResourceCollection();
		Resource[] resource = new Resource[1]; 
//		resource[0] = new SpanishPath();
		resource[0] = new PathResource(new File(resourcesDir));
		recoll.setResources(resource);
		handler.setBaseResource(recoll);
		
		handler.addServlet(FilterServlet.class, "/filter/*");
		handler.addServlet(QuestionServlet.class, "/question/*");
		handler.addServlet(StatsServlet.class, "/stats/*");
		handler.addServlet(StopServlet.class, "/stop/*");
		handler.addServlet(DefaultServlet.class, "/");

		for (String name: parms.keySet()) {
			handler.setInitParameter(name, parms.get(name));
		}

		return handler;
	}
	
	public void setInitParameter(String name, String value) {
		parms.put(name,  value);
	}
	
	public static void main(String[] args) {
		
		try {
		
			// Start server test
			
			if (args.length < 2) {
				System.out.println("SpanishHttp  <port>  <file-root> ");
				return;
			}
			
			Loader.load();
			
			SpanishHttp http = new SpanishHttp(Integer.parseInt(args[0]), args[1]); 

			http.start();
			
			// End server test
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
