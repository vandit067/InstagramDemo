package com.demo.instagram.dragdrop;

import java.util.ArrayList;

/**
 * Purpose:This class is use for reorder on long press of grid item.
 * 
 * @author Vandit Patel
 * @version 1.0
 * @date 18/02/15
 */
public class DynamicGridUtils {
	/**
	 * Delete item in <code>list</code> from position <code>indexFrom</code> and
	 * insert it to <code>indexTwo</code>
	 *
	 * @param list
	 * @param indexFrom
	 * @param indexTwo
	 */
	public static void reorder(ArrayList<Object> list, int indexFrom,
			int indexTo) {
		Object obj = list.remove(indexFrom);
		list.add(indexTo, obj);
	}
}
