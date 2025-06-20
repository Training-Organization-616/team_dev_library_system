package la.bean;

import java.io.Serializable;

public class SearchResultsBean implements Serializable{

	private int bookId;
	private int isbn;
	private String title;
	private int code;
	private String author;
	private String publicher;
	private String publicationDate;
	private String arrivalDate;
	
	public SearchResultsBean(){}
	
	public SearchResultsBean(int bookId , int isbn , String title , int code , String author , 
			String publicher , String publicationDate , String arrivalDate){
		
		this.bookId = bookId;
		this.isbn = isbn;
		this.title = title;
		this.code = code;
		this.author = author;
		this.publicher = publicher;
		this.publicationDate = publicationDate;
		this.arrivalDate = arrivalDate;
	}

	public int getBookId() {
		return bookId;
	}
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public int getIsbn() {
		return isbn;
	}
	public void setIsbn(int isbn) {
		this.isbn = isbn;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}

	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public String getPublicher() {
		return publicher;
	}
	public void setPublicher(String publicher) {
		this.publicher = publicher;
	}

	public String getPublicationDate() {
		return publicationDate;
	}
	public void setPublicationDate(String publicationDate) {
		this.publicationDate = publicationDate;
	}
	
	public String getArrivalDate() {
		return arrivalDate;
	}
	public void setArrivalDate(String arrivalDate) {
		this.arrivalDate = arrivalDate;
	}
}
