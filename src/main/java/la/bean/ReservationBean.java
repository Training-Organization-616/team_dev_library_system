package la.bean;

import java.io.Serializable;

public class ReservationBean implements Serializable{

	private int reservationId;
	private int userId;
	private String reservationDate;
	private int bookId;
	private int alreadyLent;
	private String memo;
	
	public ReservationBean(){}
	
	public ReservationBean(int reservationId , int userId ,
			String reservationDate , int bookId , int alreadyLent , String memo) {
		
		this.reservationId = reservationId;
		this.userId = userId;
		this.reservationDate = reservationDate;
		this.bookId = bookId;
		this.alreadyLent = alreadyLent;
		this.memo = memo;
	}

	public int getReservationId() {
		return reservationId;
	}
	public void setReservationId(int reservationId) {
		this.reservationId = reservationId;
	}

	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getReservationDate() {
		return reservationDate;
	}
	public void setReservationDate(String reservationDate) {
		this.reservationDate = reservationDate;
	}

	public int getBookId() {
		return bookId;
	}
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public int getAlreadyLent() {
		return alreadyLent;
	}
	public void setAlreadyLent(int alreadyLent) {
		this.alreadyLent = alreadyLent;
	}

	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
}
