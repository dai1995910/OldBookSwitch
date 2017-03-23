package model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 售后单
 * @author admin
 *
 */
@Entity
@Table(name="aftersellorder")
public class AfterSellOrder {
	@Id
	@GeneratedValue(generator="asoId")
	@GenericGenerator(name="asoId",strategy="assigned")
	@Column(length=50)
	private String asOrderId; // 售后单据，时间戳+买家学号
	
	@OneToOne/*(cascade=CascadeType.ALL)*/
	@JoinColumn(name="tradeId", unique=true)
	private Trade trade; // 交易单号，外键，唯一
	
	private String afterselldesc; // 售后描述
	private Date subTime; // 提交时间
	private String handleResult; // 处理的结果
	
	public String getAsOrderId() {
		return asOrderId;
	}
	public void setAsOrderId(String asOrderId) {
		this.asOrderId = asOrderId;
	}
	public Trade getTrade() {
		return trade;
	}
	public void setTrade(Trade trade) {
		this.trade = trade;
	}
	public String getAfterselldesc() {
		return afterselldesc;
	}
	public void setAfterselldesc(String afterselldesc) {
		this.afterselldesc = afterselldesc;
	}
	public Date getSubTime() {
		return subTime;
	}
	public void setSubTime(Date subTime) {
		this.subTime = subTime;
	}
	public String getHandleResult() {
		return handleResult;
	}
	public void setHandleResult(String handleResult) {
		this.handleResult = handleResult;
	}
	
	
}
