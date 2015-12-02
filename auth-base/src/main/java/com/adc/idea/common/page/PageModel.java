package com.adc.idea.common.page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/*  
 * 翻页模板
 */
@SuppressWarnings({ "rawtypes" })
public final class PageModel implements Serializable {

	private static final long serialVersionUID = -7110486225499064147L;

	/** 当前页码 */
	private int pageNo = 1;
	/** 每页显示数目 */
	private int pageSize = 10;
	/** 显示导航页长度 */
	private int navigateLength = 8;
	/** 总页数 */
	private int totalPage;
	/** 总记录数 */
	private int totalRecord;
	/** 分页数据 */
	private List list = new ArrayList<>(10);

	/** 所有导航页号 */
	private int[] navigatePageNums;
	/** 是否有前一页 */
	private boolean hasPreviousPage = false;
	/** 是否有下一页 */
	private boolean hasNextPage = false;
	/** 是否为第一页 */
	private boolean isFirstPage = false;
	/** 是否为最后一页 */
	private boolean isLastPage = false;

	public PageModel() {
	}

	public PageModel(int pageNo) {
		this.pageNo = pageNo;
	}

	/**
	 * 判定页面边界
	 */
	public void judgePageBoudary() {
		isFirstPage = pageNo == 1;
		isLastPage = pageNo == totalPage;
		hasPreviousPage = pageNo > 1;
		hasNextPage = pageNo < totalPage;
	}

	/**
	 * 计算导航页
	 */
	private void calcNavigatepageNums() {
		// 当总页数小于或等于导航页码数时
		if (totalPage <= navigateLength) {
			navigatePageNums = new int[totalPage];
			for (int i = 0; i < totalPage; i++) {
				navigatePageNums[i] = i + 1;
			}
		} else { // 当总页数大于导航页码数时
			navigatePageNums = new int[navigateLength];
			int startNum = pageNo - navigateLength / 2;
			int endNum = pageNo + navigateLength / 2;

			if (startNum < 1) {
				startNum = 1;
				// (最前navigatePages页
				for (int i = 0; i < navigateLength; i++) {
					navigatePageNums[i] = startNum++;
				}
			} else if (endNum > totalPage) {
				endNum = totalPage;
				// 最后navigatePages页
				for (int i = navigateLength - 1; i >= 0; i--) {
					navigatePageNums[i] = endNum--;
				}
			} else {
				// 所有中间页
				for (int i = 0; i < navigateLength; i++) {
					navigatePageNums[i] = startNum++;
				}
			}
		}
	}

	public int[] getNavigatePageNums() {
		return navigatePageNums;
	}

	private void setTotalPage() {
		if (totalRecord % pageSize == 0) {
			totalPage = totalRecord / pageSize;
		} else {
			totalPage = totalRecord / pageSize + 1;
		}
	}

	/*
	 * 获得总页数
	 */
	public int getTotalPage() {
		return totalPage;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
		// 初始化总页数
		setTotalPage();
		// 初始化导航页
		calcNavigatepageNums();

		judgePageBoudary();
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getNavigateLength() {
		return navigateLength;
	}

	public void setNavigateLength(int navigateLength) {
		this.navigateLength = navigateLength;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public boolean isHasPreviousPage() {
		return hasPreviousPage;
	}

	public boolean isHasNextPage() {
		return hasNextPage;
	}

	public boolean isFirstPage() {
		return isFirstPage;
	}

	public boolean isLastPage() {
		return isLastPage;
	}

}
