package com.dem.server;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import com.search.data.*;

/**
 * Handles REST requests to the server.
 */
@Path("/search")
public class RequestHandler {

	private static final String VERSION = "v 1.0";
	private static MyDataService myservice = new MyDataService();
	private String strKeyWord;
	private long itime = 0;
  /**
   * Takes a database name via a POST request and returns the keys of all
   * vertex and edge properties, and a boolean value specifying if the property has a numerical
   * type. The return is a string in the JSON format, for easy usage in a JavaScript web page.
   *
   * @param databaseName name of the loaded database
   * @return  A JSON containing the vertices and edges property keys
   */

/*	  @POST
	  @Path("/graph/{databaseName}")
	  @Produces("application/json;charset=utf-8")
	  public Response getGraph(@PathParam("databaseName") String databaseName) throws Exception {*/

  
  @POST
  @Path("/test/{searchkey}")
  @Produces("application/json;charset=utf-8")
  public Response getKeysValue(@PathParam("searchkey") String strtxtid) {
	  if( strtxtid != null) {						 			
		try {
			    myservice.empty();
				long startTime = System.currentTimeMillis();
			    String skeyword[] = {""};
				MySearchFiles msf = new MySearchFiles();
				msf.doSearcnFiles(skeyword, strtxtid);
				long endTime = System.currentTimeMillis();
				itime = endTime - startTime;
				
				ArrayList<SearchFileInfo> pathlist = new ArrayList<SearchFileInfo>(); 
				pathlist = msf.getAllPath();
				
				myservice.setDatainfo(pathlist);
												
				JSONObject jsonObject = new JSONObject();
				JSONArray urlArray = new JSONArray();
				JSONArray titelArray = new JSONArray();
				JSONArray authorArray = new JSONArray();
				JSONArray dateArray = new JSONArray();
				JSONArray strcontent = new JSONArray();
				int isize = 0;
				if(pathlist.size() < 6)
				{
					isize = pathlist.size();
				}
				else 
				{
					isize = 6;
				}
				for(int inum=0; inum<isize; inum++)
		    	{	
					authorArray.put(pathlist.get(inum).getAuthor());
					titelArray.put(pathlist.get(inum).getTitel());
					urlArray.put(pathlist.get(inum).getUrl());
					dateArray.put(pathlist.get(inum).getDate());
					strcontent.put(pathlist.get(inum).getContent());
		    	}
				jsonObject.put("author", authorArray);
				 jsonObject.put("titel", titelArray);
				 
				 jsonObject.put("url", urlArray);
				 jsonObject.put("date", dateArray);
				 jsonObject.put("num", myservice.getDataNum());
				 jsonObject.put("time", itime);
				 jsonObject.put("content", strcontent);
				return Response.ok(jsonObject.toString()).build();
		   } catch (Exception e) {			        
		}
	 }
	  
	return null;
  }

  @POST
  @Path("/page/{page_id}/{searchkey}")
  @Produces("application/json;charset=utf-8")
  public Response getPageId(@PathParam("page_id") int pageId, @PathParam("searchkey") String strKey) {
	 				 			
	try {	
		 	ArrayList<SearchFileInfo> pathlist = myservice.getDatainfo(pageId, 10);
		 	
		 	JSONObject jsonObject = new JSONObject();
			JSONArray urlArray = new JSONArray();
			JSONArray titelArray = new JSONArray();
			JSONArray authorArray = new JSONArray();
			JSONArray dateArray = new JSONArray();
			JSONArray strcontent = new JSONArray();
			int isize = 0;
			if(pathlist.size() < 6)
			{
				isize = pathlist.size();
			}
			else 
			{
				isize = 6;
			}
			for(int inum=0; inum<isize; inum++)
	    	{	
				authorArray.put(pathlist.get(inum).getAuthor());
				titelArray.put(pathlist.get(inum).getTitel());
				urlArray.put(pathlist.get(inum).getUrl());
				dateArray.put(pathlist.get(inum).getDate());
				strcontent.put(pathlist.get(inum).getContent());
	    	}
			jsonObject.put("author", authorArray);
			 jsonObject.put("titel", titelArray);
			 
			 jsonObject.put("url", urlArray);
			 jsonObject.put("date", dateArray);
			 jsonObject.put("num", myservice.getDataNum());
			 jsonObject.put("time", itime);
			 jsonObject.put("content", strcontent);	  	      
			return Response.ok(jsonObject.toString()).build();
	   } catch (Exception e) {			        
	 }
	  
	return null;
  }
  
  @POST
  @Path("/page")
  @Produces("application/json;charset=utf-8")
  public Response getDocumentInfo() {
	 				 			
	try {	
			ArrayList<String> data = new ArrayList<String>();
		 	
		 	JSONObject jsonObject = new JSONObject();
			JSONArray pathArray = new JSONArray();
			JSONArray titelArray = new JSONArray();
			for(int inum=0; inum<data.size(); inum++)
	    	{	
			  pathArray.put(data.get(inum));		  	      
	    	}
			 jsonObject.put("str", pathArray);			  
	  	      
			return Response.ok(jsonObject.toString()).build();
	   } catch (Exception e) {			        
	 }
	  
	return null;
  }

  @GET
  @Path("/check")
  @Produces("application/json;charset=utf-8")
  public String getVersion() {
//   String path = RequestHandler.class.getResource("/data/" + strtxtid).getPath();
   
   return VERSION;
  }
}
