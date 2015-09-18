package com.zhizun.pos.bean;

import com.zhizun.pos.base.BaseBean;

/**
 * 有奖活动 图片集合
 */
public class MktgPicList extends BaseBean {

	private static final long serialVersionUID = 1L;
	private String name;
	private String path;
	private String thumbSaveName;
	private String thumbPath;

	public String getName() {
		return name;
	}

	public String getThumbSaveName() {
		return thumbSaveName;
	}

	public void setThumbSaveName(String thumbSaveName) {
		this.thumbSaveName = thumbSaveName;
	}

	public String getThumbPath() {
		return thumbPath;
	}

	public void setThumbPath(String thumbPath) {
		this.thumbPath = thumbPath;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
