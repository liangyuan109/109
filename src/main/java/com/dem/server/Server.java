package com.dem.server;

import com.sun.jersey.api.container.grizzly2.GrizzlyServerFactory;
import com.sun.jersey.api.core.DefaultResourceConfig;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.api.json.JSONConfiguration;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.StaticHttpHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import javax.ws.rs.core.UriBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import javax.xml.parsers.SAXParser;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.search.data.*;
import com.search.data.Xmlparsen.BookHandler;

/**
 * 
 * Basic class, used for starting and stopping the server.
 */
public class Server {

  /**
   * URI that specifies where the server is run.
   */
  private static final URI BASE_URI = getBaseURI();
  /**
   * Default port
   */
  private static final int PORT = 8080;
  /**
   * Path to demo application
   */
//  private static String strfilepath = "C:\\SIGIR";
  private static final String APPLICATION_PATH = "RestServletDemo/html/searchtext.html";
  public final static String ENTITY_EXPANSION_LIMIT = "entityExpansionLimit";
  static final File INDEX_DIR = new File("./index");
  /**
   * Creates the base URI.
   * @return Base URI
   */
  private static URI getBaseURI() {
    return UriBuilder.fromUri("http://localhost/").port(PORT).build();
  }

  /**
   * Starts the server and adds the request handlers.
   *
   * @return the running server
   * @throws IOException if server creation fails
   */
  private static HttpServer startServer() throws IOException {
    System.out.println("Starting grizzly...");
    ResourceConfig rc = new PackagesResourceConfig("com/dem/server");
    rc.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, true);
    HttpServer server = GrizzlyServerFactory.createHttpServer(BASE_URI, rc);
    String strpath = Server.class.getResource("/webapp").getPath();
    HttpHandler staticHandler = new StaticHttpHandler(
      Server.class.getResource("/webapp").getPath());
    server.getServerConfiguration().addHttpHandler( staticHandler, "/RestServletDemo" );

    
    return server;
  }
//  body { background:#fff; font: 12px/1.5 Tahoma,'ËÎÌå';color:#000;}
  /**
   * Main method. Run this to start the server.
   *
   * @param args command line parameters
 * @param TOTAL_ENTITY_SIZE_LIMIT 
   * @throws IOException if server creation fails
   */
  public static void main(String[] args) throws Exception {	 
	  HttpServer httpServer = startServer();

    if (!INDEX_DIR.exists()) {       
    	SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
    	SAXParser saxParser = saxParserFactory.newSAXParser();
 	 	org.apache.xerces.util.SecurityManager mgr = new org.apache.xerces.util.SecurityManager();
 	 	mgr.setEntityExpansionLimit(100000000);
   	 	saxParser.getXMLReader().setProperty("http://apache.org/xml/properties/security-manager", mgr);

   	 	InputStream inputStream= new FileInputStream("dblp.xml");
		InputSource is = new InputSource(inputStream);
		is.setEncoding("ISO-8859-1");
   	 	BookHandler bookHandler = (new Xmlparsen()).new BookHandler();	 

   	 	saxParser.parse(is, bookHandler);
   	 	HashMap<String,ArrayList<SearchFileInfo>> sfi = bookHandler.getInfoList();
    	      
   	 	String strfilepath = "C:\\SIGIR";
        MyIndexFiles mif = new MyIndexFiles();
        mif.SetMapList(sfi);
        File file = new File(strfilepath);
        mif.findDocs(file, "", "");
   	 	mif.SetMapList(sfi);
   	 	mif.doIndexFiles(strfilepath);
      }
    
	System.out.printf("org.searchengine.server started at %s%s%n" +
	      "Press any key to stop it.%n", getBaseURI(), APPLICATION_PATH);
   	
    System.in.read();
    httpServer.stop();	 
  }
}

