package model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 交易单
 * @author admin
 *
 */
@Entity
@Table(name="trade")
public class Trade {
	@Id
	@GeneratedValue(generator="tradeId")
	@GenericGenerator(name="tradeId",strategy="assigned")
	@Column(length=50)
	private String tradeId; // 交易单号， 当前时间戳+发起人+被受人
	
	@ManyToOne(/*cascade={CascadeType.ALL},*/fetch=FetchType.EAGER)
	@JoinColumn(name="actionerId")
	private User actioner; // 发起动作的人，也就是创建这个订单的人，一个人买书那么，买家就是actioner
	
	@ManyToOne(/*cascade={CascadeType.ALL},*/fetch=FetchType.EAGER)
	@JoinColumn(name="sellItemId")
	private OnSellItem onSellItem; // 当前订单对应的上架物品
	
	private String state; // 订单的状态
	private Date tradingDate; // 交易时间
	
	public String getTradeId() {
		return tradeId;
	}
	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}
	public User getActioner() {
		return actioner;
	}
	public void setActioner(User actioner) {
		this.actioner = actioner;
	}
	public OnSellItem getOnSellItem() {
		return onSellItem;
	}
	public void setOnSellItem(OnSellItem onSellItem) {
		this.onSellItem = onSellItem;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Date getTradingDate() {
		return tradingDate;
	}
	public void setTradingDate(Date tradingDate) {
		this.tradingDate = tradingDate;
	}
}
