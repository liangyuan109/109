package com.search.data;

public class SearchFileInfo {
	String strPath;
	String strAutor;
	String strDescrip;
	String strDate;
	String strTiel;
	String strUrl;

	SearchFileInfo(){
	strPath = "";
	strAutor = "";
	strDescrip = "";
	strDate = "";
	strTiel = "";
	strUrl = "";
	}
	
	SearchFileInfo(String path)
	{
		this.strPath = path;
	}
	
	SearchFileInfo(String path, String autor, String descrip, String date, String tiel, String url)
	{
		this.strPath = path;
		this.strAutor = autor;
		this.strDescrip = descrip;
		this.strDate = date;
		this.strTiel = tiel;
		this.strUrl = url;
	}
	
	public void setPath(String path) {
		this.strPath = path;
	}
	
	public String getPath() {
		return this.strPath;
	}
	
	public String getAuthor() {
		return this.strAutor;
	}
	
	public String getTitel() {
		return this.strTiel;
	}
	
	public String getUrl() {
		return this.strUrl;
	}
	
	public String getDate() {
		return this.strDate;
	}
	
	public String getContent() {
		return this.strDescrip;
	}
}
