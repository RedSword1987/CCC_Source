package com.ccc.dreamcollection;

/**
 * @author 邵建红
 * @date Mar 7, 2014 9:29:43 AM
 */
public class CompareBean {
	String key;
	Long size = Long.valueOf(0);

	public CompareBean(String key, Long size) {
		this.key = key;
		this.size = size;
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return this.key;
	}

	/**
	 * @return the size
	 */
	public Long getSize() {
		return this.size;
	}
}
