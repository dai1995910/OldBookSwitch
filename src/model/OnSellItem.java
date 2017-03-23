package model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

/**
 * 上架出售的物品
 * @author admin
 *
 */
@Entity
public class OnSellItem {
	@Id
	@GeneratedValue(generator="sellItemId")
	@GenericGenerator(name="sellItemId",strategy="assigned")
	@Column(length=50)
	private String sellItemId; // 上架物品ID，为时间戳+学号
	
	@ManyToOne(cascade={CascadeType.ALL},fetch=FetchType.EAGER)
	@JoinColumn(name="ISBN")
	private Book book; // 书籍外键
	
	@ManyToOne(/*cascade={CascadeType.ALL},*/fetch=FetchType.EAGER)
	@JoinColumn(name="poster")
	private User poster;  // 提交这个商品的人，一个人可以提交需求单和卖书单
	
	private String degree; // 新旧程度
	private Date sellTime; // 提交时间
	private boolean isForSell; // 标志是不是用来销售还是求购的
	private boolean isSelled = false; // 是否已经卖掉，用于区分是否展示在页面上
	
	public String getSellItemId() {
		return sellItemId;
	}
	public void setSellItemId(String sellItemId) {
		this.sellItemId = sellItemId;
	}
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public User getPoster() {
		return poster;
	}
	public void setPoster(User poster) {
		this.poster = poster;
	}
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	public Date getSellTime() {
		return sellTime;
	}
	public void setSellTime(Date sellTime) {
		this.sellTime = sellTime;
	}
	public boolean isForSell() {
		return isForSell;
	}
	//***********************
	public boolean getIsForSell(){
		return isForSell;
	}
	public void setForSell(boolean isForSell) {
		this.isForSell = isForSell;
	}
	public boolean isSelled() {
		return isSelled;
	}
	public boolean getIsSelled(){
		return isSelled;
	}
	public void setSelled(boolean isSelled) {
		this.isSelled = isSelled;
	}
	
	
}
