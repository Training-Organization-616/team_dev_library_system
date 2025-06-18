package la.bean;

public class StockBean {

	private int bookId;
	private int isbn;
	private String title;
	private String arrivalDate;
	private String disposalDate;
	private String memo;
	private int stock;

	public StockBean() {
	}

	public StockBean(int bookId, int isbn, String title,
			String arrivalDate, String disposalDate, String memo, int stock) {
		this.bookId = bookId;
		this.isbn = isbn;
		this.title = title;
		this.arrivalDate = arrivalDate;
		this.disposalDate = disposalDate;
		this.memo = memo;
		this.stock = stock;
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

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}
}
