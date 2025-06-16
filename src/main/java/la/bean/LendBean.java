package la.bean;

import java.io.Serializable;

public class LendBean implements Serializable {

	//フィールド
	private int lendId; //貸出ID
	private int userId; //会員ID
	private int bookId; //資料ID
	private String lendDate; //貸出年月日
	private String dueDate; //返却期日
	private String returnDate; //返却年月日
	private String memo; //備考

	//デフォルトコンストラクタ
	LendBean() {
	}

	//コンストラクタ
	LendBean(int lendId, int userId, int bookId, String lendDate, String dueDate, String returnDate, String memo) {
		this.lendId = lendId;
		this.userId = userId;
		this.bookId = bookId;
		this.lendDate = lendDate;
		this.dueDate = dueDate;
		this.returnDate = returnDate;
		this.memo = memo;
	}

	//ゲッターとセッター
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

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public String getLendDate() {
		return lendDate;
	}

	public void setLendDate(String lendDate) {
		this.lendDate = lendDate;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public String getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(String returnDate) {
		this.returnDate = returnDate;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}
