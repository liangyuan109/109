package com.search.data;

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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.ArrayList;  
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Collector;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Scorer;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.search.highlight.Formatter;
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.SimpleSpanFragmenter;
import org.apache.lucene.search.highlight.TokenSources;
import org.apache.lucene.search.similarities.LMDirichletSimilarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

/** Simple command-line based search demo. */
public class MySearchFiles {
//	+ '&nbsp;,total&nbsp;' + maxentries + 
  /** Use the norms from one field for all fields.  Norms are read into memory,
   * using a byte of memory per document per searched field.  This can cause
   * search of large collections with a large number of fields to run out of
   * memory.  If all of the fields contain only a single token, then the norms
   * are all identical, then single norm vector may be shared. */
	static final File INDEX_DIR = new File("./index");
	
  private static ArrayList<SearchFileInfo> mPathlist = new ArrayList<SearchFileInfo>(); 
  public ArrayList<SearchFileInfo> getAllPath()
  {
	  return mPathlist;
  }
  
  private static int mitotalnum;
  public int getAllnum() {
	  return mitotalnum;
  }
  
  public MySearchFiles() {}

  /** Simple command-line based search demo. */
  public static void doSearcnFiles(String[] skeyword, String squery) throws Exception {
	 
		    String index = "index";
		    String field = "contents";
		    String queries = null;		   
		    boolean raw = false;		    
		    int hitsPerPage = 10;
		    Directory d = FSDirectory.open(INDEX_DIR.toPath());
		    IndexReader reader = DirectoryReader.open(d); // only searching, so read-only=true
		    IndexSearcher searcher = new IndexSearcher(reader);
		    LMDirichletSimilarity lmSimilarity = new LMDirichletSimilarity();
		    searcher.setSimilarity(lmSimilarity);
		    Analyzer analyzer = new StandardAnalyzer();

		    QueryParser parser = new QueryParser("contents", analyzer);
		     String line = squery;//in.readLine();

		      if (line == null || line.length() == -1)
		        return;

		      line = line.trim();
		      if (line.length() == 0)
		    	  return;
		      
		      Query query = parser.parse(line);
		      System.out.println("Searching for: " + query.toString(field));

		      // Collect enough docs to show 5 pages
//		      TopScoreDocCollector collector = TopScoreDocCollector.create(
	//	          5 * hitsPerPage, false);
		      TopDocs search = searcher.search(query, 500);
//		      searcher.search(query, collector);
		      ScoreDoc[] hits = search.scoreDocs;
		      
		      int numTotalHits = hits.length;
		      System.out.println(numTotalHits + " total matching documents");

		      int start = 0;
		      mitotalnum = numTotalHits;
		      int end = numTotalHits;//Math.min(numTotalHits, hitsPerPage);
		        
		       /* if (end > hits.length) {
		          collector = TopScoreDocCollector.create(numTotalHits, false);
		          searcher.search(query, collector);
		          hits = collector.topDocs().scoreDocs;
		        }*/
		        
		   // Highlighter Code Start
		        Formatter formatter = new SimpleHTMLFormatter("<span style='color:red;'>","</span>");
		        QueryScorer scorer = new QueryScorer(query);
		        Highlighter highlighter = new Highlighter(formatter, scorer);
		        Fragmenter fragmenter = new SimpleSpanFragmenter(scorer, 1000);
		        highlighter.setTextFragmenter(fragmenter);
		        
		        for (int i = start; i < end; i++) {
		          if (raw) {                              // output raw format
		            System.out.println("doc="+hits[i].doc+" score="+hits[i].score);
		            continue;
		          }

		          SearchFileInfo searchinfo = new SearchFileInfo();
		          int docId = hits[i].doc;
		          Document doc = searcher.doc(hits[i].doc);
		          String path = doc.get("path");
		          if (path != null) {
		          	
		        	    searchinfo.strPath = path;
	//	              System.out.println((i+1) + ". " + path);
		          }
		            String title = doc.get("title");
		            if (title != null && 0!=title.trim().compareTo("")) {
		          	  searchinfo.strTiel = title;
//		              System.out.println("   Title: " + doc.get("title"));
		              
		              String url = doc.get("url");
		              if(url != null)
		              {
		              	searchinfo.strUrl = url;
		              }
		              String author = doc.get("author");
		              if(author != null) {
		              	searchinfo.strAutor = author;
		              }
		              String date = doc.get("date");
		              if(date != null) {
		              	searchinfo.strDate = date;
		              }
		              String text = doc.get("contents");
		              if(text != null)
		              {
		              	TokenStream stream = TokenSources.getAnyTokenStream(reader, docId, "contents", analyzer);
		              	String[] frags = highlighter.getBestFragments(stream, text, 10);
//		              	searchinfo.strDescrip = frags.toString();
		              	for(int ii=0; ii<frags.length; ii++)
		              	{
//		              		System.out.println(frag);
		              		searchinfo.strDescrip += frags[ii]+";";
		              	}
		              }	
		              			              
		              mPathlist.add(searchinfo);
		            }
		        }     

		 /*     if (paging) {
		        doPagingSearch(in, searcher, query, hitsPerPage, raw, queries == null);
		      } else {
		        doStreamingSearch(searcher, query);
		      }*/
//		    }
		   reader.close();
  }
}
