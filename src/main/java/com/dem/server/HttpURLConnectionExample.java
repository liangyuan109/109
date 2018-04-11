package com.dem.server;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.xml.bind.v2.schemagen.xmlschema.List;

public class HttpURLConnectionExample {

	private final String USER_AGENT = "Mozilla/5.0";
	
/*	public static void main(String[] args)throws Exception {
		// TODO Auto-generated method stub
		HttpURLConnectionExample http = new HttpURLConnectionExample();
		 
	     // Sending get request
		http.sendingGetRequest();
	  
	    // Sending post request
		http.sendingPostRequest();
	}*/

	 // HTTP GET request
	public String sendingGetRequest(String str) throws Exception {
	 
	  String urlString = "http://dblp.org/search/publ/api?q="+str+"&format=json";
	  
	  URL url = new URL(urlString);
	  HttpURLConnection con = (HttpURLConnection) url.openConnection();
	 
	  // By default it is GET request
	  con.setRequestMethod("GET");
	 
	  //add request header
	  con.setRequestProperty("User-Agent", USER_AGENT);
	 
	  int responseCode = con.getResponseCode();
	  System.out.println("Sending get request : "+ url);
	  System.out.println("Response code : "+ responseCode);
	 
	  // Reading response from input Stream
	  BufferedReader in = new BufferedReader(
	          new InputStreamReader(con.getInputStream()));
	  String output;
	  StringBuffer response = new StringBuffer();
	 
	  while ((output = in.readLine()) != null) {
	   response.append(output);
	  }
	  in.close();
	  
	  return response.toString();
	 }
	 
	 // HTTP Post request
	public void sendingPostRequest() throws Exception {
	 
	  String url = "http://dblp.org/search/author/api";
	  URL obj = new URL(url);
	  HttpURLConnection con = (HttpURLConnection) obj.openConnection();
	 
	        // Setting basic post request
	  con.setRequestMethod("POST");
	  con.setRequestProperty("User-Agent", USER_AGENT);
	  con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
	  con.setRequestProperty("Content-Type","application/json");
	 
	  String postJsonData = "{id:5,countryName:USA,population:8000}";
	  
	  // Send post request
	  con.setDoOutput(true);
	  DataOutputStream wr = new DataOutputStream(con.getOutputStream());
	  wr.writeBytes(postJsonData);
	  wr.flush();
	  wr.close();
	 
	  int responseCode = con.getResponseCode();
	  System.out.println("nSending 'POST' request to URL : " + url);
	  System.out.println("Post Data : " + postJsonData);
	  System.out.println("Response Code : " + responseCode);
	 
	  BufferedReader in = new BufferedReader(
	          new InputStreamReader(con.getInputStream()));
	  String output;
	  StringBuffer response = new StringBuffer();
	 
	  while ((output = in.readLine()) != null) {
		  System.out.println(output);
	   response.append(output);
	   System.out.println("\n");
	  }
	  in.close();
	  
//	  Gson gson = new GsonBuilder().create();
//     Person p = gson.fromJson(reader, Person.class);
//      System.out.println(p);
	  //printing result from response
//	  System.out.println(response.toString());
	 }
	
	public String read(File file) {
		String str = null;
		Reader reader = null;
		try {
            System.out.println("以字符为单位读取文件内容，一次读多个字节：");
            // 一次读多个字符
            char[] tempchars = new char[30];
            int charread = 0;
            reader = new InputStreamReader(new FileInputStream(file));
            // 读入多个字符到字符数组中，charread为一次读取字符数
            int isize = 0;
            while ((isize < 3) && ((charread = reader.read(tempchars)) != -1)) {
                // 同样屏蔽掉\r不显示
            	isize++;
                if ((charread == tempchars.length)
                        && (tempchars[tempchars.length - 1] != '\r')) {
                    System.out.print(tempchars);
                } else {
                    for (int i = 0; i < charread; i++) {
                        if (tempchars[i] == '\r') {
                            continue;
                        } else {
                            System.out.print(tempchars[i]);
                        }
                    }
                }
            }

        } catch (Exception e1) {
            e1.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        } 
        return str;
	}
	
	
	
	public boolean readFileByLines(File file)
	{
	//	File file = new File(filename);
		StringBuffer response = new StringBuffer();
		String tempString = null;
		BufferedReader reader = null;
		
		File writename = new File("output1.txt");
		
		try {
			writename.createNewFile();
			BufferedWriter out = new BufferedWriter(new FileWriter(writename));
			
			reader = new BufferedReader(new FileReader(file));	
			int line = 1;
			while((line < 15000000)&&((tempString = reader.readLine()) != null)) {
				line++;
				if(line >= 10000000)
				{
					out.write(tempString);
					out.flush();	
				}				
			}
			out.close();
			reader.close();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(reader != null) {
				try {
					reader.close();
				}catch(Exception el) {
					
				}
			}
		}

		return false;
	}


}
