package com.search.data;


import java.util.ArrayList;
import java.util.*;
import org.xml.sax.Attributes; 
import org.xml.sax.SAXException; 
import org.xml.sax.XMLReader; 
import org.xml.sax.helpers.DefaultHandler; 
import org.xml.sax.helpers.XMLReaderFactory;

import com.sun.xml.bind.v2.schemagen.xmlschema.List; 
  
 
public class Xmlparsen { 
 
  public class BookHandler extends DefaultHandler { 
     private ArrayList<SearchFileInfo> xmlList; 
     private boolean b_title = false;
     private boolean b_author = false;
     private boolean b_url = false;
     private boolean b_year = false;
     private boolean b_article = false;
     private HashMap <String, ArrayList<SearchFileInfo>> xmlMap = new HashMap<String, ArrayList<SearchFileInfo>>();
     private String bufferedElement = "";
     
     public ArrayList<SearchFileInfo> getNameList() { 
        return xmlList; 
     } 
     
     public HashMap<String,ArrayList<SearchFileInfo>> getInfoList()
     {    	 
         for(int iyear=1971; iyear<=2017; iyear++)
         {
        	 String str = "";
        	 str = String.valueOf(iyear);
        	 
        	 ArrayList<SearchFileInfo> ss = new ArrayList<SearchFileInfo>();
        	 xmlMap.put(str.trim(), ss);
         }
         
         String key = null;
         ArrayList<SearchFileInfo> value = null;
         Iterator iter = xmlMap.keySet().iterator();
         while(iter.hasNext())
         {
        	 key = (String)iter.next();
        	 value = (ArrayList<SearchFileInfo>)xmlMap.get(key);
        	 for(int j=0;j<xmlList.size();j++)
            	{
            		if(0==key.compareTo(xmlList.get(j).strDate.trim()))
            		{
            			value.add(xmlList.get(j));
            		}
            	}
         }
         return xmlMap;
     }
     // Called at start of an XML document 
     @Override 
     public void startDocument() throws SAXException { 
        System.out.println("Start parsing document..."); 
        xmlList = new ArrayList<SearchFileInfo>(); 
        
   /*     SearchFileInfo s0 = new SearchFileInfo();
        xmlMap.put("1980", s0);
        SearchFileInfo s1 = new SearchFileInfo();
        xmlMap.put("1981", s1);
        SearchFileInfo s2 = new SearchFileInfo();
        xmlMap.put("1982", s2);*/
     } 
     // Called at end of an XML document 
     @Override 
     public void endDocument() throws SAXException {  
        System.out.println("End");  
     } 
      
     /** 
      * Start processing of an element. 
      * @param namespaceURI  Namespace URI 
      * @param localName  The local name, without prefix 
      * @param qName  The qualified name, with prefix 
      * @param atts  The attributes of the element 
      */ 
     @Override 
     public void startElement(String uri, String localName, String qName, 
        Attributes atts) throws SAXException { 
        // Using qualified name because we are not using xmlns prefixes here. 
    	 if(qName.equals("inproceedings")/* || qName.equals("article") || qName.equals("proceedings") || 
    			 qName.equals("incollection") || qName.equals("book")*/)
    	 {
    		 b_article = true;
    		 SearchFileInfo bookTitle = new SearchFileInfo();
    		 xmlList.add(bookTitle);
    	 }
    	 if (b_article && qName.equals("year")) { 
           b_year = true; 
        }
    	 if(b_article && qName.equals("title"))
        {
        	b_title = true;
        }
        if(b_article && qName.equals("author"))
        {
        	b_author = true;
        }
        if(b_article && qName.equals("ee"))
        {
        	b_url =true;
        }
     } 
   
     @Override 
     public void endElement(String namespaceURI, String localName, String qName) 
        throws SAXException { 
        // End of processing current element 
    	 if (qName.equals("inproceedings")/* || qName.equals("article") || qName.equals("proceedings") || 
    			 qName.equals("incollection") || qName.equals("book")*/) {  
         	b_article = false;
         } 
         if(0==qName.compareTo("year") && b_article)
         {
        	int i = xmlList.size();
         	if(i>0)
        		{
        			SearchFileInfo sfi = xmlList.get(i-1);
        			sfi.strDate = bufferedElement;
 //                System.out.println("Book year: " + bufferedElement); 
        		}
         	b_year = false;
         }
         if(0==qName.compareTo("author") && b_article)
         {	    
         	int i = xmlList.size();
         	if(i>0)
         	{
         		SearchFileInfo sfi = xmlList.get(i-1);
         		if(0 != sfi.strAutor.trim().compareTo(""))
         		{
         			sfi.strAutor += ",";
         			sfi.strAutor += bufferedElement;
  //       			System.out.println("Book author1: " + sfi.strAutor);
         		}
         		else 
         		{
         			sfi.strAutor = bufferedElement;
 //        			System.out.println("Book author0: " + sfi.strAutor);
         		}
         		
                  
         	}  
         	b_author = false;
         }
         if(0==qName.compareTo("title") && b_article)
         {
        	int i = xmlList.size();
         	if(i>0)
         	{
         		SearchFileInfo sfi = xmlList.get(i-1);
         		sfi.strTiel = bufferedElement;
         	}
         	b_title =false;
         }
         if(0==qName.compareTo("ee") && b_article)
         {
         	int i = xmlList.size();
         	if(i>0)
         	{
         		SearchFileInfo sfi = xmlList.get(i-1);
         		sfi.strUrl = bufferedElement;
 //                System.out.println("Book url: " + bufferedElement); 
         	}
         	b_url = false;
         }

     } 
            
     @Override 
     public void characters(char[] ch, int start, int length) { 
        // Processing character data inside an element 
    	 if(b_article)
    	 {
    		 if(b_author)
    		 {
    			 bufferedElement = new String(ch, start, length).trim();
    		 }
    		 if(b_url)
    		 {
    			 bufferedElement = new String(ch, start, length).trim();
    		 }
    		 if(b_year)
    		 {
    			 bufferedElement = new String(ch, start, length).trim();
    		 }
    		 if(b_title)
    		 {
    			 bufferedElement = new String(ch, start, length).trim();
    			 if(0==bufferedElement.compareTo("t."))
    			 {
//    				 System.out.println(ch);
    			 }
    		 }
    	 }    	                  
     }            
  }     
}