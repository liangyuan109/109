package com.search.data;

import org.apache.lucene.analysis.Analyzer;

/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.similarities.BM25Similarity;
import org.apache.lucene.search.similarities.BooleanSimilarity;
import org.apache.lucene.search.similarities.IBSimilarity;
import org.apache.lucene.search.similarities.LMDirichletSimilarity;
import org.apache.lucene.search.similarities.LMSimilarity;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.search.similarities.SimilarityBase;
import org.apache.lucene.search.similarities.TFIDFSimilarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
//import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import com.dem.server.HttpURLConnectionExample;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.SimpleBookmark;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


/** Index all text files under a directory. */
public class MyIndexFiles {
  
  public MyIndexFiles() {}

  static final File INDEX_DIR = new File("./index");
  static HashMap<String,ArrayList<SearchFileInfo>> m_sfi;
  static HashMap<String,ArrayList<SearchFileInfo>> m_Hmap = new HashMap<String,ArrayList<SearchFileInfo>>();
  public void SetMapList(HashMap<String,ArrayList<SearchFileInfo>> sfi) {
	  m_sfi = sfi;
  }
  public void listMap()
  {
	  Set set = m_Hmap.entrySet();
      Iterator iterator = set.iterator();
      while(iterator.hasNext()) {
         Map.Entry mentry = (Map.Entry)iterator.next();
         ArrayList<SearchFileInfo> say = (ArrayList)mentry.getValue();
         for(int i=0;i<say.size();i++)
         {
        	 System.out.println(say.get(i).strPath+say.get(i).strTiel);
         }
//         System.out.println(mentry.getValue());
      }
  }
  
  /** Index all text files under a directory. */
  public static void doIndexFiles(String args) {
    String usage = "java org.apache.lucene.demo.IndexFiles <root_directory>";
/*    if (args.length == 0) {
      System.err.println("Usage: " + usage);
      System.exit(1);
    }*/

    if (INDEX_DIR.exists()) {
      System.out.println("Cannot save index to '" +INDEX_DIR+ "' directory, please delete it first");
 //     System.exit(1);
      return;
    }
    
    
    final File docDir = new File(args);
    if (!docDir.exists() || !docDir.canRead()) {
      System.out.println("Document directory '" +docDir.getAbsolutePath()+ "' does not exist or is not readable, please check the path");
 //     System.exit(1);
      return;
    }
    
    Date start = new Date();
    try { 
      Path p = INDEX_DIR.toPath();
      Directory d = FSDirectory.open(p);
      Analyzer analyzer = new StandardAnalyzer();
      IndexWriterConfig conf = new IndexWriterConfig(analyzer);
      LMDirichletSimilarity lmSimilarity = new LMDirichletSimilarity() ;
      conf.setSimilarity(lmSimilarity);
      IndexWriter writer = new IndexWriter(d, conf);
      System.out.println("Indexing to directory '" +INDEX_DIR+ "'...");
      SearchFileInfo searchinfo = new SearchFileInfo();
      indexDocs(writer, docDir, searchinfo);
      System.out.println("Optimizing...");
      writer.commit();
      writer.close();

      Date end = new Date();
      System.out.println(end.getTime() - start.getTime() + " total milliseconds");

    } catch (Exception e) {
      System.out.println(" caught a " + e.getClass() +
       "\n with message: " + e.getMessage());
    }
  }

  static void indexDocs(IndexWriter writer, File file, SearchFileInfo searchinfo)
    throws Exception {
    // do not try to index files that cannot be read
    if (file.canRead()) {
      if (file.isDirectory()) {
    	String strname = file.getName();  
        String[] files = file.list();
        // an IO error could occur
        if (files != null) {
          for (int i = 0; i < files.length; i++) {
        	  File f = new File(file, files[i]);
			  String name = f.getName();
        	  SearchFileInfo sinfo = new SearchFileInfo();
        	  if(m_Hmap.containsKey(strname))
        	  {
        		  ArrayList<SearchFileInfo> info = m_Hmap.get(strname);
        		  
            	//  if(info != null && info.size() >= i && info.size()>0)
            	  {       		  
            		 sinfo = info.get(i); 
 //           		 System.out.println(name+sinfo.strTiel+sinfo.strPath);
            	  }
                indexDocs(writer, new File(file, files[i]), sinfo);
        	  }else {
        		  indexDocs(writer, new File(file, files[i]), sinfo);
        	  }
       	  
          }
        }
      } else {
//        System.out.println("adding " + searchinfo.strAutor+";"+searchinfo.strTiel+searchinfo.strDate+searchinfo.strUrl);
        try {
        		String strpath = searchinfo.strPath;
        		String strauthor = searchinfo.strAutor ;
        		String strtiel = searchinfo.strTiel;
        		String strdate = searchinfo.strDate;
        		String strurl = searchinfo.strUrl;
        		Document doc = new Document();
 //       		System.out.println(file.getName()+strtiel+strpath);
        		writer.addDocument(FileDocument.Document(file, strauthor, strtiel, strdate, strurl));
        }
        // at least on windows, some temporary files raise this exception with an "access denied" message
        // checking if the file can be read doesn't help
        	catch (FileNotFoundException fnfe) {
        	;
        }
      }
    }
  }
  static public int compare(String str, String target)
  {
	  int d[][];
	  int n = str.length();
	  int m = target.length();
	  int i,j;
	  char ch1,ch2;
	  int temp;
	  if(n==0)
	  {
		  return m;
	  }
	  
	  if(m==0)
	  {
		  return n;
	  }
	  d = new int[n+1][m+1];
	  for(i=0;i<=n;i++)
	  {
		  d[i][0] = i;
	  }
	  for(j=0;j<=m;j++)
	  {
		  d[0][j]=j;
	  }
	  for(i=1;i<=n;i++)
	  {
		  ch1=str.charAt(i-1);
		  for(j=1;j<=m;j++)
		  {
			  ch2=target.charAt(j-1);
			  if(ch1==ch2)
			  {
				  temp=0;
			  }else {
				  temp=1;
			  }
			  d[i][j] = min(d[i-1][j]+1,d[i][j-1],d[i-1][j-1]+temp);
		  }
	  }
	  return d[n][m];
  }
  static private int min(int one, int two, int three)
  {
	  return (one=one<two ? one:two)<three?one:three;
  }
  static public float getSimilarityRatio(String str, String target)
  {
	  return 1-(float)compare(str,target)/Math.max(str.length(), target.length());
  }
  

  static public boolean readFileByLines(File file, String strsuch, String strs) throws IOException
	{
	  	String strname = file.getName();
	  	String str = strsuch.toLowerCase();
	  	if(strname.endsWith("txt"))
	  	{	  		
			String tempString = null;
			BufferedReader reader = null;
			StringBuffer response = new StringBuffer();
			String strss = strs.toLowerCase();
			try {
				reader = new BufferedReader(new FileReader(file));	
				int line = 0;
				while(line < 5 && ((tempString = reader.readLine()) != null)) {	
					response.append(tempString.toLowerCase()); 
					if(0.95<getSimilarityRatio(response.toString(), str) && str.length()>20)
					{	
						reader.close();
						return true;
					}
					line++;
				}
				
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
	  	else
	  	{
	  		PDFParsen pdf = new PDFParsen();
	  		String strtitle = pdf.getPDFInformation(file.getAbsolutePath());

	  	   if(0.99<getSimilarityRatio(strtitle.toLowerCase(), str) && str.length()>20)
			{	  		
				return true;
			}
	  	}
	
	  	return false;
	}
  
	public ArrayList<File> getFileList(String strPath) {
		ArrayList<File> filelist = new ArrayList<File>();
        File dir = new File(strPath);
        File[] files = dir.listFiles(); // 该文件目录下文件全部放入数组
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                String fileName = files[i].getName();
                if (files[i].isDirectory()) { // 判断是文件还是文件夹
                    getFileList(files[i].getAbsolutePath()); // 获取文件绝对路径
                } else if (fileName.endsWith("txt")) { // 判断文件名是否以.txt结尾
                    String strFileName = files[i].getAbsolutePath();
 //                   System.out.println("---" + strFileName);
                    filelist.add(files[i]);
                } else {
                    continue;
                }
            }

        }
        return filelist;
    }
	
	static public boolean findDocs(File file, String strsuch, String strs)
		    throws Exception {
		    // do not try to index files that cannot be read
		    if (file.canRead()) {
		      if (file.isDirectory()) {
		    	  String strname = file.getName();
		    	  System.out.println(strname);
		    	  String strpath = strname + ".txt";
//		    	  File _file = new File(strpath);
//		    	  if(!_file.exists())
//		    		  _file.createNewFile();
//		    	  BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(_file));
		    
			    	if(!m_Hmap.containsKey(strname)/* && 0!=strname.compareTo("SIGIR")*/)
		        	  {
		        		  ArrayList<SearchFileInfo> ss = new ArrayList<SearchFileInfo>();
		        		  m_Hmap.put(strname, ss);
		        	  }	    	
		        String[] files = file.list();
		        // an IO error could occur
		        if (files != null) {
		          for (int i = 0; i < files.length; i++) {
		        	  
		        	  SearchFileInfo sinfo = new SearchFileInfo();
		        	  File f = new File(file, files[i]);
    				  String name = f.getName();
    				  sinfo.strPath = name;  				 
    				  
		        	  ArrayList<SearchFileInfo> m = m_Hmap.get(strname);	        				        			
		        	  if(m_sfi.containsKey(strname))
		        	  {		        		  		        		  
		        		  ArrayList<SearchFileInfo> info = m_sfi.get(strname);		        		  
			        		  for(int j = 0; j < info.size(); j++)
			        		  {	        			 
			        				  boolean b = findDocs(new File(file, files[i]), info.get(j).strTiel, info.get(j).strAutor);
				        			  if(b)
				        			  {			        				  					        				  	        	
				        				  sinfo.strAutor = info.get(j).strAutor;
				        				  sinfo.strTiel = info.get(j).strTiel;
				        				  sinfo.strUrl = info.get(j).strUrl;
				        				  sinfo.strDate = info.get(j).strDate;
				        				  sinfo.strDescrip = info.get(j).strDescrip;
				        				  sinfo.strPath = name;		        				 
				        				  m.add(sinfo);
				        				  
//				        				  bufferedWriter.write(sinfo.strPath+"\r\n");
//				        				  bufferedWriter.write(sinfo.strTiel+"\r\n");
//				        				  bufferedWriter.flush();
				        				  				        
				        				  break;		        				 
				        			  }  			        			        				        			  
			        		  }
		        	  }
		        	  else
		        	  {
		        		  findDocs(new File(file, files[i]), strsuch, strs);  
		        	  }	
		        	  
//		        	  File fs = new File(file, files[i]);
		        	 
		        	  if(sinfo.strTiel.isEmpty())
		        	  {
		        		  m.add(sinfo);  
		        	  }		        	  
		          }
		        }
		        
//		        bufferedWriter.close();
		      } else {
		        try {
		        		return readFileByLines(file, strsuch, strs);		        	
		        	}
		        // at least on windows, some temporary files raise this exception with an "access denied" message
		        // checking if the file can be read doesn't help
		        	catch (Exception fnfe) {
		        	;
		        }
		      }
		    }
		    return false;
		  } 		
}
