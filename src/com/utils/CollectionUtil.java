package com.utils;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.ComparatorUtils;
import org.apache.commons.collections.comparators.ComparableComparator;
import org.apache.commons.collections.comparators.ComparatorChain;
import org.apache.commons.collections.comparators.NullComparator;

<<<<<<< HEAD
=======
import com.mvc.entityReport.WorkHouse;
import com.mvc.entityReport.WorkLoad;

>>>>>>> 58ea610958f7d6ec894f4a05b6d50cfb5deae52e
/**
 * 集合公共类
 * 
 * @author wangrui
 * @date 2017年1月10日
 */
public class CollectionUtil<T> {

	/**
	 * 根据实体按指定字段升/降排序
	 * 
	 * @param list
	 * @param filedName
	 * @param ascFlag
	 *            true升序,false降序
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void sort(List<?> list, String fieldName, boolean ascFlag) {
		if (list.size() == 0 || fieldName.equals("")) {
			return;
		}
		Comparator<?> cmp = ComparableComparator.getInstance();

		if (ascFlag) {// 升序
			cmp = ComparatorUtils.nullLowComparator(cmp);
		} else {// 降序
			cmp = ComparatorUtils.reversedComparator(cmp);
		}
		Collections.sort(list, new BeanComparator(fieldName, cmp));
	}

	/**
	 * 根据实体按多个字段升/降排序
	 * 
	 * @param beans
	 * @param sortParam
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void sortExecute(List<?> beans, Map<String, String> sortParam) {
		if (beans.size() == 0 || sortParam.keySet().size() == 0) {
			return;
		}

		if (beans.isEmpty() || sortParam.isEmpty()) {
			return;
		}
		ComparatorChain comparator = new ComparatorChain();
		boolean sortMethod = false;
		for (String itemName : sortParam.keySet()) {
			sortMethod = false;
			if ("desc".equals(sortParam.get(itemName))) {
				sortMethod = true;
			}
			comparator.addComparator(new BeanComparator(itemName, new NullComparator()), sortMethod);
		}
		Collections.sort(beans, comparator);
	}

	/**
	 * 向已排序的list中实体的某字段插入序号
	 * 
	 * @param list
	 * @param fieldName
	 * @return
	 */
	public List<T> writeSort(List<T> list, String fieldName) {
		T t = null;
		Field field = null;
		try {
			for (int i = 0; i < list.size(); i++) {
				t = list.get(i);
				field = t.getClass().getDeclaredField(fieldName);
				field.setAccessible(true);// 成员变量为private,必须进行此操作
				field.set(t, String.valueOf(i + 1));// 赋值
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return list;
	}

	/**
	 * 向已排序的list中实体的某字段插入序号
	 * 
	 * @param list
	 * @param fieldName
	 * @return
	 */
	public List<T> workLoadWriteSort(List<T> list, String fieldName) {
		try {
			Field field = WorkLoad.class.getDeclaredField(fieldName);
			T t = null;
			for (int i = 0; i < list.size(); i++) {
				t = list.get(i);
				field.setAccessible(true);// 成员变量为private,必须进行此操作
				field.set(t, String.valueOf(i + 1));// 赋值
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return list;
	}

}
