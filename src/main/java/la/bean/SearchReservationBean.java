package la.bean;

import java.io.Serializable;

public class SearchReservationBean implements Serializable{

	private int reservationId;
	private String reservationDate;
	private int userId;
	private String name;
	private String tel;
	private String email;
	private int bookId;
	private String title;
	private int alreadyLent;
	private String memo;
	
	public SearchReservationBean() {}
	
	public SearchReservationBean(int reservationId , String reservationDate , int userId , String name ,
			String tel , String email , int bookId , String title , int alreadyLent , String memo) {
		
		this.reservationId = reservationId;
		this.reservationDate = reservationDate;
		this.userId = userId;
		this.name = name;
		this.tel = tel;
		this.email = email;
		this.bookId = bookId;
		this.title = title;
		this.alreadyLent = alreadyLent;
		this.memo = memo;
	}

	public int getReservationId() {
		return reservationId;
	}
	public void setReservationId(int reservationId) {
		this.reservationId = reservationId;
	}

	public String getReservationDate() {
		return reservationDate;
	}
	public void setReservationDate(String reservationDate) {
		this.reservationDate = reservationDate;
	}

	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public int getBookId() {
		return bookId;
	}
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
