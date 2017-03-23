package model;

/**
 * 用于返回我的订单页面的Bean
 * 一个对象表示一个Item
 * @author admin
 *
 */
public class TradeBean {
	// 书名
	private String bookName;
	// 书的图片url
	private String imgUrl;
	// 价格
	private float price;
	// 订单编号
	private String tradeId;
	// 订单的状态
	private String tradeState;
	// 卖家或者买家
	private String other;
	// 姓名
	private String otherName;
	
	// 卖家或者卖家的联系方式
	private String otherContact;
	// 按钮文字
	private String btnText;
	// 按钮的href
	private String btnHref;
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public String getTradeId() {
		return tradeId;
	}
	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}
	public String getTradeState() {
		return tradeState;
	}
	public void setTradeState(String tradeState) {
		this.tradeState = tradeState;
	}
	public String getOther() {
		return other;
	}
	public void setOther(String other) {
		this.other = other;
	}
	public String getOtherContact() {
		return otherContact;
	}
	public void setOtherContact(String otherContact) {
		this.otherContact = otherContact;
	}
	public String getBtnText() {
		return btnText;
	}
	public void setBtnText(String btnText) {
		this.btnText = btnText;
	}
	public String getBtnHref() {
		return btnHref;
	}
	public void setBtnHref(String btnHref) {
		this.btnHref = btnHref;
	}
	
	public String getOtherName() {
		return otherName;
	}
	public void setOtherName(String otherName) {
		this.otherName = otherName;
	}
	
}
