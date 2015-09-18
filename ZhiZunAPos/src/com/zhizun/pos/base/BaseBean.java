package com.zhizun.pos.base;

import java.io.Serializable;

/**
 * 实体基类：实现序列化
 * 创建人：李林中
 * 创建日期：2014-12-15  上午10:01:33
 * 作用：    
 * 修改
 * ===================================================
 *  修改人             修改日期                原因(描述)
 * ===================================================
 */
public abstract class BaseBean implements Serializable {
	private long timestamp;		//接口时间戳

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
}
