package la.bean;

import java.io.Serializable;

public class OverdueBean implements Serializable{

	private int lendId;
	private int userId;
	private String name;
	private String email;
	private String tel;
	private int bookId;
	private String title;
	private String dueDate;
	private String firstReminder;
	private String secondReminder;
	private String memo;
	
	public OverdueBean() {}
	
	public OverdueBean(int lendId , int userId , String name , String email , String tel , int bookId ,
			String title , String dueDate , String firstReminder , String secondReminder , String memo) {
		
		this.lendId = lendId;
		this.userId = userId;
		this.name = name;
		this.email = email;
		this.tel = tel;
		this.bookId = bookId;
		this.title = title;
		this.dueDate = dueDate;
		this.firstReminder = firstReminder;
		this.secondReminder = secondReminder;
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

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
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
	
	public String getDueDate() {
		return dueDate;
	}
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public String getTelReminder() {
		return firstReminder;
	}
	public void setTelReminder(String firstReminder) {
		this.firstReminder = firstReminder;
	}

	public String getDemandLetter() {
		return secondReminder;
	}
	public void setDemandLetter(String secondReminder) {
		this.secondReminder = secondReminder;
	}

	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
}
