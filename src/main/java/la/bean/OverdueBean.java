package la.bean;

import java.io.Serializable;

public class OverdueBean implements Serializable{

	private int lendId;
	private int userId;
	private String name;
	private int bookId;
	private String title;
	private String telReminder;
	private String demandLetter;
	private String memo;
	
	public OverdueBean() {}
	
	public OverdueBean(int lendId , int userId , String name , int bookId ,
			String title , String telReminder , String demandLetter , String memo) {
		
		this.lendId = lendId;
		this.userId = userId;
		this.name = name;
		this.bookId = bookId;
		this.title = title;
		this.telReminder = telReminder;
		this.demandLetter = demandLetter;
		this.memo = memo;
	}

	public int getLendId() {
		return lendId;
	}
	public void setLendId(int lendId) {
		this.lendId = lendId;
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

	public String getTelReminder() {
		return telReminder;
	}
	public void setTelReminder(String telReminder) {
		this.telReminder = telReminder;
	}

	public String getDemandLetter() {
		return demandLetter;
	}
	public void setDemandLetter(String demandLetter) {
		this.demandLetter = demandLetter;
	}

	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
}
