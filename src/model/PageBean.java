package model;

import java.util.List;

/**
 * 用于分页传输的Bean
 * @author admin
 *
 */
public class PageBean<T> {
	private int currPage; // 当前页数
	private int pageSize; // 每页显示的记录的数量 
	private int totoalCount; // 总的记录数量
	private int totoalPage; // 一共的页数
	private List<T> valueList; // 每页显示的数据
	public int getCurrPage() {
		return currPage;
	}
	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotoalCount() {
		return totoalCount;
	}
	public void setTotoalCount(int totoalCount) {
		this.totoalCount = totoalCount;
	}
	public int getTotoalPage() {
		return totoalPage;
	}
	public void setTotoalPage(int totoalPage) {
		this.totoalPage = totoalPage;
	}
	public List<T> getValueList() {
		return valueList;
	}
	public void setValueList(List<T> valueList) {
		this.valueList = valueList;
	}
	
}
