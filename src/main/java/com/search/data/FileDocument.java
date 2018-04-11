package com.search.data;

import java.io.Reader;
import java.nio.file.Files;
import java.awt.TextField;

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

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.apache.lucene.document.DateTools;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.LongPoint;
import org.apache.lucene.document.StringField;
//import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexOptions;

import java.io.Reader;
/** A utility for making Lucene Documents from a File. */

public class FileDocument {
  /** Makes a document for a File.
    <p>
    The document has three fields:
    <ul>
    <li><code>path</code>--containing the pathname of the file, as a stored,
    untokenized field;
    <li><code>modified</code>--containing the last modified date of the file as
    a field as created by <a
    href="lucene.document.DateTools.html">DateTools</a>; and
    <li><code>contents</code>--containing the full contents of the file, as a
    Reader field;
 * @throws IOException 
    */

  public static Document Document(File f, String strauthor, String strtiel, String strdate, String strurl)
       throws IOException {
	 
	FieldType type = new FieldType();
	type.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS);;
	type.setStored(true);
    // make a new, empty document
    Document doc = new Document();

    // Add the path of the file as a field named "path".  Use a field that is 
    // indexed (i.e. searchable), but don't tokenize the field into words.
    doc.add(new StringField("path", f.getPath(), Field.Store.YES));
    // Add the last modified date of the file a field named "modified".  Use 
    // a field that is indexed (i.e. searchable), but don't tokenize the field
    // into words.
    doc.add(new LongPoint("modified",f.lastModified()));

    // Add the contents of the file to a field named "contents".  Specify a Reader,
    // so that the text of the file is tokenized and indexed, but not stored.
    // Note that FileReader expects the file to be in the system's default encoding.
    // If that's not the case searching for special characters will fail.
    
    String str = "";
    if(f.getName().endsWith("txt"))
    {
    
    str = new String(Files.readAllBytes(f.toPath()));
//        int ipos = str.lastIndexOf("REFERENCES");
//        str = str.substring(0, ipos);
    }
    else
    {
    	str = pdfdemo.extractTXT(f.getAbsolutePath());
    }
    
    doc.add(new Field("contents", str,type));
//    doc.add(new Field("contents", new FileReader(f), type));
    doc.add(new Field("title", strtiel, type));
    doc.add(new Field("author", strauthor, type));
    doc.add(new Field("date", strdate, type));
    doc.add(new Field("url", strurl, type));
    
    // return the document
    return doc;
  }

  private  static PDFParsen pdfdemo = new PDFParsen();
  private FileDocument() {}
}
    
