package la.bean;

public class StockBean {

	private int bookId;
	private long isbn;
	private String title;
	private String author;
	private String arrivalDate;
	private String disposalDate;
	private String memo;
	private int stock;
	private int reservation;
	private int reservationAmount;

	public StockBean() {
	}

	public StockBean(int bookId, long isbn, String title, String author,
			String arrivalDate, String disposalDate, String memo, int stock, int reservation, int reservationAmount) {
		this.bookId = bookId;
		this.isbn = isbn;
		this.title = title;
		this.author = author;
		this.arrivalDate = arrivalDate;
		this.disposalDate = disposalDate;
		this.memo = memo;
		this.stock = stock;
		this.reservation = reservation;
		this.reservationAmount = reservationAmount;
	}

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public long getIsbn() {
		return isbn;
	}

	public void setIsbn(long isbn) {
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

	public int getReservation() {
		return reservation;
	}

	public void setReservation(int reservation) {
		this.reservation = reservation;
	}

	public int getReservationAmount() {
		return reservationAmount;
	}

	public void setReservationAmount(int reservationAmount) {
		this.reservationAmount = reservationAmount;
	}

}
