package com.search.data;

import java.util.ArrayList;

public class MyDataService {

	  private static ArrayList<SearchFileInfo> mDataInfo = new ArrayList<SearchFileInfo>(); 
	  private static PageInfo mPageInfo;
	  
	  public void setDatainfo(ArrayList<SearchFileInfo> datainfolist) {
	     this.mDataInfo = datainfolist; 
	  }
	  
	  public int getDataNum() {
		  return this.mDataInfo.size();
	  }
	  
	  public void empty() {
		  this.mDataInfo.clear();
	  }
	  // 获得第几页的数据
	  public ArrayList<SearchFileInfo> getDatainfo(int iseite, int ipitem) {
		 ArrayList<SearchFileInfo> datainfo = new ArrayList<SearchFileInfo>();
		  int idextemp = iseite*ipitem;
	     for(int idex=idextemp; idex<idextemp+ipitem; idex++) {
	    	 datainfo.add(mDataInfo.get(idex));
	     }
		  return datainfo;
	  }
}	  
	 