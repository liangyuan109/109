package com.search.data;

public class PageInfo {
	
	  private int iPage;
	  private int iPageNum;
	  private int iAllItem;
	  private static int iPageitem = 10; // 10 item pre page
	  private int iCurrentPage;
	  private int iEndPage;
	  private int iEndPageItem;
	  
	  PageInfo(){
		  
	  }
	  
	  PageInfo(int iPage, int iPageNum, int iAllItem, int iCurrentPage, int iEndPage, int iEndPageItem){
		  this.iPage = iPage;
		  this.iPageNum = iPageNum;
		  this.iAllItem = iAllItem;
		  this.iCurrentPage = iCurrentPage;
		  this.iEndPage = iEndPage;
		  this.iEndPageItem = iEndPageItem;
	  }
	  
	  public void SetPage(int iPage)
	  {
		  this.iPage = iPage;
	  }
	  
	  public int GetPage() {
		  return this.iPage;
	  }
	  
	  public void SetPageNum(int iPageNum) {
		  this.iPageNum = iPageNum;
	  }
	  
	  public int GetPageNum() {
		  return this.iPageNum;
	  }
	  
	  public void SetAllItem(int iAllItem) {
		  this.iAllItem = iAllItem;
	  }
	  
	  public int GetAllItem() {
		  return this.iAllItem;
	  }
	  
	  public void SetCurrentPage(int iCurrentPage) {
		  this.iCurrentPage = iCurrentPage;
	  }
	  
	  public int GetCurrentPage() {
		  return this.iCurrentPage;
	  }
	  
	  public void SetEndPage(int iEndPage) {
		  this.iEndPage = iEndPage;
	  }
	  
	  public int GetEndPage() {
		  return this.iEndPage;
	  }
	  
	  public void SetEndPageItem(int iEndPageItem) {
		  this.iEndPageItem = iEndPage;
	  }
	  
	  public int GetEndPageItem() {
		  return this.iEndPageItem;
	  }
	  
	  public int GetPageItem() {
		  return this.iPageitem;
	  }
}
