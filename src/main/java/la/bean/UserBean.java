package la.bean;

public class UserBean {
	private int userId;
	private String userName;
	private String address;
	private String tel;
	private String email;
	private String birthday;
	private String admissionDate;
	private String updateDate;
	private String cancelDate;
	private String lendBooks;
	private String reservationBooks;

	public UserBean() {

	}

	//登録
	public UserBean(int userId, String userName, String address, String tel, String email, String birthday,
			String admissionDate) {
		this.userId = userId;
		this.userName = userName;
		this.address = address;
		this.tel = tel;
		this.email = email;
		this.birthday = birthday;
		this.admissionDate = admissionDate;
	}

	public UserBean(int userId, String userName, String address, String tel, String email, String birthday) {
		this.userId = userId;
		this.userName = userName;
		this.address = address;
		this.tel = tel;
		this.email = email;
		this.birthday = birthday;
	}

	//登録以外
	public UserBean(int userId, String userName, String address, String tel, String email, String birthday,
			String admissionDate, String updateDate, String cancelDate) {
		this.userId = userId;
		this.userName = userName;
		this.address = address;
		this.tel = tel;
		this.email = email;
		this.birthday = birthday;
		this.admissionDate = admissionDate;
		this.updateDate = updateDate;
		this.cancelDate = cancelDate;

	}
	
	//検索結果
		public UserBean(int userId, String userName, String address, String tel, String email, String birthday,
				String lendBooks , String reservationBooks) {
			this.userId = userId;
			this.userName = userName;
			this.address = address;
			this.tel = tel;
			this.email = email;
			this.birthday = birthday;
			this.lendBooks = lendBooks;
			this.reservationBooks = reservationBooks;

		}

	//ゲッターセッター

	public String getLendBooks() {
		return lendBooks;
	}

	public void setLendBooks(String lendBooks) {
		this.lendBooks = lendBooks;
	}

	public String getReservationBooks() {
		return reservationBooks;
	}

	public void setReservationBooks(String reservationBooks) {
		this.reservationBooks = reservationBooks;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getAdmissionDate() {
		return admissionDate;
	}

	public void setAdmissionDate(String admissionDate) {
		this.admissionDate = admissionDate;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getCancelDate() {
		return cancelDate;
	}

	public void setCancelDate(String cancelDate) {
		this.cancelDate = cancelDate;
	}

}
