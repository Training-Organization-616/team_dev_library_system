package la.bean;

import java.io.Serializable;

public class InquiriesBean implements Serializable{

	private int inquiriesId;
	private String receptionDate;
	private String contentsTitle;
	private String contents;
	private int handling;
	private String memo;
	
	public InquiriesBean() {}
	
	public InquiriesBean(String receptionDate , String contentsTitle ,
			String contents , int handling , String memo) {
		
		this.receptionDate = receptionDate;
		this.contentsTitle = contentsTitle;
		this.contents = contents;
		this.handling = handling;
		this.memo = memo;
	}
	
	public InquiriesBean(int inquiriesId , String receptionDate , String contentsTitle ,
			String contents , int handling , String memo) {
		
		this.inquiriesId = inquiriesId;
		this.receptionDate = receptionDate;
		this.contentsTitle = contentsTitle;
		this.contents = contents;
		this.handling = handling;
		this.memo = memo;
	}

	public int getInquiriesId() {
		return inquiriesId;
	}
	public void setInquiriesId(int inquiriesId) {
		this.inquiriesId = inquiriesId;
	}

	public String getReceptionDate() {
		return receptionDate;
	}
	public void setReceptionDate(String receptionDate) {
		this.receptionDate = receptionDate;
	}

	public String getContentsTitle() {
		return contentsTitle;
	}
	public void setContentsTitle(String contentsTitle) {
		this.contentsTitle = contentsTitle;
	}

	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}

	public int getHandling() {
		return handling;
	}
	public void setHandling(int handling) {
		this.handling = handling;
	}

	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
}
