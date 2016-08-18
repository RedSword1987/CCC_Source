package com.ccc.dreamdb.db.impl;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Redsword.shao
 * @date 2016-5-11 上午12:38:22
 */

public class PageResultBean {

	private Integer count = 0;
	private List<Map<String, Object>> list;
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public List<Map<String, Object>> getList() {
		return list;
	}
	public void setList(List<Map<String, Object>> list) {
		this.list = list;
	}

}
