package com.ccc.dreamcollection;

import java.util.Comparator;
import java.util.Map;

/**
 * @author RedSword
 * @date 2013-05-14 22:31:46
 */
public class ListMapCompareLong implements Comparator<Map<String, Object>> {

    private String field;
    private String order = "asc";

    /**
     * @param field
     * @param order:
     *            <br/> asc or desc
     */
    public ListMapCompareLong(String field, String order) {
        this.field = field;
        if ("DESC".equals(String.valueOf(order).toUpperCase())) {
            this.order = "desc";
        }
    }

    public int compare(Map<String, Object> o1, Map<String, Object> o2) {
        if ("asc".equals(order)) {
            if (Long.valueOf(String.valueOf(o1.get(field))) > Long.valueOf(String.valueOf(o2.get(field)))) {
                return 1;
            } else {
                return -1;
            }
        } else {
            if (Long.valueOf(String.valueOf(o1.get(field))) > Long.valueOf(String.valueOf(o2.get(field)))) {
                return -1;
            } else {
                return 1;
            }
        }

    }

}
