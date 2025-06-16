package la.bean;

import java.io.Serializable;

public class CategoryBean implements Serializable{

	private int code;
	private String codeName;
	
	CategoryBean(){}
	
	CategoryBean(int code , String codeName){
		
		this.code = code;
		this.codeName = codeName;
	}

	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}

	public String getCodeName() {
		return codeName;
	}
	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}
}
