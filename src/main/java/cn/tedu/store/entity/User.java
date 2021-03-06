package cn.tedu.store.entity;

import java.util.Date;

/**
 * 后续会调整
 * 
 * @author soft01
 *
 */
public class User extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8404538918024696628L;
	private Integer id;
	private String username;
	private String password;
	private String phone;
	private String email;
	private Integer gender;
	private Integer status;
	private Integer isDelete;
	private String avatar;
	private String salt;

	public User() {
		super();
	}

	public User(String username, String password, String phone, String email, Integer gender) {
		super();
		this.username = username;
		this.password = password;
		this.phone = phone;
		this.email = email;
		this.gender = gender;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", phone=" + phone + ", email="
				+ email + ", gender=" + gender + ", status=" + status + ", isDelete=" + isDelete + ", avatar=" + avatar
				+ ", salt=" + salt + "]";
	}

}
