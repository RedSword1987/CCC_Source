package com.ccc.dreamcollection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author RedSword
 * @date 2013-05-19 11:10:02
 */
public class CollectionUtil {

	/**
	 * cut off a list by size<br/>
	 * size>=1
	 * 
	 * @param param
	 * @param size
	 * @return
	 */
	public static List<List> cutOffList(List param, int size) {
		List<List> result = new ArrayList<List>();
		List<IntervalBean> lList = cutOffList(param.size(), size);
		for (IntervalBean intervalBean : lList) {
			List<Map<String, Object>> temp = param.subList(intervalBean.first, intervalBean.last);
			result.add(temp);
		}
		return result;
	}

	/**
	 * cut off a length,divide into several part <br/>
	 * size>=1
	 * 
	 * @param param
	 * @param size
	 * @return
	 */
	public static List<IntervalBean> cutOffList(int length, int size) {
		List<IntervalBean> result = new ArrayList<IntervalBean>();
		if (length <= 0 || size <= 0) {
			return result;
		} else {
			int allL = length / size;
			if (allL * size < length) {
				allL = allL + 1;
			}

			int start = 0;
			int max = 0;
			while (allL-- > 0) {
				max = start + size > length ? length : start + length;
				IntervalBean intervalbean = new IntervalBean();
				intervalbean.first = start;
				intervalbean.last = max;
				result.add(intervalbean);
				start = max;
			}
			return result;
		}
	}

	/**
	 * sort map（by value size）
	 * 
	 * @param paramMap
	 * @param sign
	 * @return
	 */
	public static Map<String, List<?>> sortMapByValueSize(Map<String, List<?>> paramMap, String sign) {
		Map<String, List<?>> allMap_ = new LinkedHashMap<String, List<?>>();
		List<CompareBean> list = new ArrayList<CompareBean>();

		for (Entry<String, List<?>> en : paramMap.entrySet()) {
			list.add(new CompareBean(en.getKey(), Long.valueOf(en.getValue().size())));
		}

		Collections.sort(list, new BeanCompareLong(sign));
		for (CompareBean compareBean : list) {
			allMap_.put(compareBean.getKey(), paramMap.get(compareBean.getKey()));
		}
		return allMap_;
	}
}
