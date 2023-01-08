package emlakcepte.model;

import emlakcepte.model.enums.RealtyStatusType;

import java.time.LocalDateTime;


public class Realty {
	private Integer id;
	private Integer no;
	private String title;
	private LocalDateTime createDate;
	private User user;
	private RealtyStatusType status;
	private String province;

	public Realty() {
		super();
	}

	public Realty(Integer no, String title, LocalDateTime createDate, RealtyStatusType status) {
		super();
		this.no = no;
		this.title = title;
		this.createDate = createDate;
		this.status = status;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getNo() {
		return no;
	}

	public void setNo(Integer no) {
		this.no = no;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public RealtyStatusType getStatus() {
		return status;
	}

	public void setStatus(RealtyStatusType status) {
		this.status = status;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	@Override
	public String toString() {
		return "Realty [no=" + no + ", title=" + title + ", createDate=" + createDate + ", status=" + getStatus()
				+ ", province=" + province + "]";
	}

}
