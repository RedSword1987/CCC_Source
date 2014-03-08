package com.ccc.dreamcollection;

import java.util.Comparator;

/**
 * @author RedSword
 * @date 2013-05-14 22:31:46
 */
public class BeanCompareLong implements Comparator<CompareBean> {

	private String order = "asc";

	/**
	 * @param field
	 * @param order
	 *            : <br/>
	 *            asc or desc
	 */
	public BeanCompareLong(String order) {
		if ("DESC".equals(String.valueOf(order).toUpperCase())) {
			this.order = "desc";
		}
	}

	@Override
	public int compare(CompareBean o1, CompareBean o2) {
		if ("asc".equals(order)) {
			if (Long.valueOf(String.valueOf(o1.getSize())) > Long.valueOf(String.valueOf(o2.getSize()))) {
				return 1;
			} else {
				return -1;
			}
		} else {
			if (Long.valueOf(String.valueOf(o1.getSize())) > Long.valueOf(String.valueOf(o2.getSize()))) {
				return -1;
			} else {
				return 1;
			}
		}

	}

}
