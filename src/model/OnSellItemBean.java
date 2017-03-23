package model;

/**
 * 用作OnSellItem界面显示的Bean
 * @author admin
 *
 */
public class OnSellItemBean {
	// 书名
	private String bookName;
	// 积分值
	private float price;
	// 订单状态
	private String state;
	private String otherKind;
	private String sellerName;
	private String sellerContect;
	// 按钮文字
	private String btnText;
	// 按钮href
	private String btnHref;
	// 书的图片
	private String imgUrl;
	// OnSellItem的Id
	private String onSellItemId;
	
	
	
	public String getOnSellItemId() {
		return onSellItemId;
	}
	public void setOnSellItemId(String onSellItemId) {
		this.onSellItemId = onSellItemId;
	}
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getSellerName() {
		return sellerName;
	}
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}
	public String getSellerContect() {
		return sellerContect;
	}
	public void setSellerContect(String sellerContect) {
		this.sellerContect = sellerContect;
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
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getOtherKind() {
		return otherKind;
	}
	public void setOtherKind(String otherKind) {
		this.otherKind = otherKind;
	}
	
	
}
