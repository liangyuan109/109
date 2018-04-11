package com.search.data;

import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.text.PDFTextStripper;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;

public class PDFParsen {
    /**获取PDF文档元数据**/
    public static String getPDFInformation(String file){
 	   String strtitle = null;
        try {  
            //打开pdf文件流
            FileInputStream fis = new   FileInputStream(file);
            //加载 pdf 文档,获取PDDocument文档对象
            PDDocument document=PDDocument.load(fis);
            /** 文档属性信息 **/  
            PDDocumentInformation info = document.getDocumentInformation(); 

            strtitle = info.getTitle();  
                           
            //关闭输入流
            document.close();
            fis.close();
        } catch (FileNotFoundException ex) {
           
        } catch (IOException ex) {
            
        } 
        return strtitle;
    } 
    
    /**提取pdf文本**/
    public static String extractTXT(String filename){
 	   String content = null;
        try{
            //打开pdf文件流
            FileInputStream fis = new   FileInputStream(filename);
            File file = new File(filename);
            //实例化一个PDF解析器
            PDFParser parser = new PDFParser(new RandomAccessFile(file,"r"));
            //解析pdf文档
            parser.parse();
            //获取PDDocument文档对象
            PDDocument document=parser.getPDDocument();
            //获取一个PDFTextStripper文本剥离对象           
            PDFTextStripper stripper = new PDFTextStripper();
            //获取文本内容
            content = stripper.getText(document); 
            document.close();
            fis.close();
        } catch (FileNotFoundException ex) {
           
        } catch (IOException ex) {
            
        }
        return content;
    }
}
