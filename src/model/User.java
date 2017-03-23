package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

/**
 * 用户类/学生类
 * 
 * @author admin
 *
 */
@Entity(name="user")
public class User {
	@Id
	@GeneratedValue(generator="stuId")
	@GenericGenerator(name="stuId",strategy="assigned")
	@Column(length=10)
	private String stuId; // 学号，为10位例如：2013214006
	private String username; // 用户名
	private String password; // 密码
	private float integral; // 积分
	private String contact; // 联系方式
	private boolean isBaned = false; // 是否已经被ban了
	
	public String getStuId() {
		return stuId;
	}
	public void setStuId(String stuId) {
		this.stuId = stuId;
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
	public float getIntegral() {
		return integral;
	}
	public void setIntegral(float integral) {
		this.integral = integral;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public boolean isBaned() {
		return isBaned;
	}
	public void setBaned(boolean isBaned) {
		this.isBaned = isBaned;
	}
	@Override
	public String toString() {
		return "User [stuId=" + stuId + ", username=" + username
				+ ", password=" + password + ", integral=" + integral
				+ ", contact=" + contact + ", isBaned=" + isBaned + "]";
	}
	
	/*
	 * 更新数据
	 */
	public void update(User u) {
		String newContact = u.getContact();
		if(newContact != null && !"".equals(newContact) && !this.contact.equals(newContact) ) {
			this.contact = u.getContact();
		}
		float newIntegral = u.getIntegral();
		if(this.integral != newIntegral ) {
			this.integral = newIntegral;
		}
		String newPwd = u.getPassword();
		if(newPwd != null && !"".equals(newPwd) && !this.password.equals(newPwd) ) {
			this.password = newPwd;
		}
	}
}
