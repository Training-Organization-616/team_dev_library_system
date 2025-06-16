package la.bean;

public class UserBean {
	private int userId;
	private String userName;
	private String address;
	private int tel;
	private String email;
	private String birthday;
	private String admissionDate;
	private String updateDate;
	private String cancelDate;

	public UserBean() {

	}

	//登録
	public UserBean(int userId, String userName, String address, int tel, String email, String birthday) {
		this.userId = userId;
		this.userName = userName;
		this.address = address;
		this.tel = tel;
		this.email = email;
		this.birthday = birthday;
	}

	//登録以外
	public UserBean(int userId, String userName, String address, int tel, String email, String birthday,
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

	//ゲッターセッター

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

	public int getTel() {
		return tel;
	}

	public void setTel(int tel) {
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
