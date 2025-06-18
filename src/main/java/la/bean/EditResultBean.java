package la.bean;

import java.io.Serializable;

public class EditResultBean implements Serializable{

	private String bookId;
	private String isbn;
	private String title;
	private String author;
	private String publicher;
	private String publicationDate;
	private String arrivalDate;
	private String disposalDate;
	private String memo;
	
	public EditResultBean() {}
	
	public EditResultBean(String bookId , String isbn , String title , String author , String publicher ,
			String publicationDate , String arrivalDate , String disposalDate , String memo) {
		
		this.bookId = bookId;
		this.isbn = isbn;
		this.title = title;
		this.author = author;
		this.publicher = publicher;
		this.publicationDate = publicationDate;
		this.arrivalDate = arrivalDate;
		this.disposalDate = disposalDate;
		this.memo = memo;
	}

	public String getBookId() {
		return bookId;
	}
	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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

	public String getDisposalDate() {
		return disposalDate;
	}
	public void setDisposalDate(String disposalDate) {
		this.disposalDate = disposalDate;
	}

	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
}
