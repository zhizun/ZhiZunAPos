package com.zhizun.pos.bean;

import java.util.ArrayList;
import java.util.List;

public class SortTreeNode {

	/**
	 * id
	 */
	String itemId;

	/**
	 * Name
	 */
	String itemName;

	/**
	 * type
	 */
	String type;

	/**
	 * 是否选中
	 */
	boolean checked = false;

	/**
	 * 上级节点
	 */
	SortTreeNode parentNode;

	/**
	 * 下级节点列表
	 */
	List<SortTreeNode> subItemList = new ArrayList<SortTreeNode>();

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public SortTreeNode getParentNode() {
		return parentNode;
	}

	public void setParentNode(SortTreeNode parentNode) {
		this.parentNode = parentNode;
	}

	public List<SortTreeNode> getSubItemList() {
		return subItemList;
	}

	public void setSubItemList(List<SortTreeNode> subItemList) {
		this.subItemList = subItemList;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
