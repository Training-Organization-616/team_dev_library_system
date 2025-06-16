package la.bean;

import java.io.Serializable;

public class CatalogBean implements Serializable{

	private int isbn;
	private String title;
	private int code;
	private String author;
	private String publicher;
	private String publicationDate;
	private int volumeNumber;
	private String stockAmount;
	
	CatalogBean(){}
	
	CatalogBean(int isbn , String title , int code , String author , String publicher ,
			String publicationDate , int volumeNumber , String stockAmount){
		
		this.isbn = isbn;
		this.title = title;
		this.code = code;
		this.author = author;
		this.publicher = publicher;
		this.publicationDate = publicationDate;
		this.volumeNumber = volumeNumber;
		this.stockAmount = stockAmount;
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

	public int getVolumeNumber() {
		return volumeNumber;
	}
	public void setVolumeNumber(int volumeNumber) {
		this.volumeNumber = volumeNumber;
	}

	public String getStockAmount() {
		return stockAmount;
	}
	public void setStockAmount(String stockAmount) {
		this.stockAmount = stockAmount;
	}
}
