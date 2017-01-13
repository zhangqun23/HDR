package com.mvc.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvc.dao.ExpendFormDao;
import com.mvc.entityReport.LinenExpend;
import com.mvc.service.ExpendFormService;
import com.utils.CollectionUtil;


/**
 * 耗品相关的service层接口实现
 * @author wq
 * @date 2017年1月13日
 */
@Service("expendFormServiceImpl")
public class ExpendFormServiceImpl implements ExpendFormService {
	
	@Autowired
	ExpendFormDao expendFormDao;

	@Override
	public List<LinenExpend> selectLinenExpend(Map<String, Object> map) {
		
		List<Object> listSource = expendFormDao.selectlinenExpend(map);
		Iterator<Object> it = listSource.iterator();
		List<LinenExpend> listGoal = objToLinenExpand(it);

		return listGoal;
	}
	// 排序
	private List<LinenExpend> objToLinenExpand(Iterator<Object> it) {
		List<LinenExpend> listGoal = new ArrayList<LinenExpend>();
		Object[] obj = null;
		LinenExpend linenExpend = null;
		while (it.hasNext()) {
			obj = (Object[]) it.next();
			linenExpend = new LinenExpend();
			linenExpend.setRoom_id(obj[0].toString());
			linenExpend.setBato_num(obj[1].toString());
			linenExpend.setFacl_num(obj[2].toString());
			linenExpend.setBesh_num(obj[3].toString());
			linenExpend.setHato_num(obj[4].toString());
			linenExpend.setMedo_num(obj[5].toString());
			linenExpend.setFlto_num(obj[6].toString());
			linenExpend.setBaro_num(obj[7].toString());
			linenExpend.setSlba_num(obj[8].toString());
			linenExpend.setDuto_num(obj[9].toString());
			linenExpend.setPill_num(obj[10].toString());
			linenExpend.setShop_num(obj[11].toString());
			linenExpend.setLaba_num(obj[12].toString());
			linenExpend.setPiin_num(obj[13].toString());
			linenExpend.setBlan_num(obj[14].toString());

			listGoal.add(linenExpend);
		}
		sortAndWrite(listGoal, "Room_id", false);
		
		Iterator<LinenExpend> itGoal = listGoal.iterator();
		int i = 0;
		linenExpend = null;
		while (itGoal.hasNext()) {
			i++;// 注意：若写序号放在第一个循环中，根据orderNum排序后存在问题：2在10后面
			linenExpend = (LinenExpend) itGoal.next();
			linenExpend.setOrderNum(String.valueOf(i));
		}

		return listGoal;
	}

		/**
		 * 排序并插入序号
		 * 
		 * @param list
		 * @param filedName
		 *            按指定字段排序
		 * @param ascFlag
		 *            true升序,false降序
		 * @param writeField
		 *            向指定字段插入序号
		 */
		private void sortAndWrite(List<LinenExpend> list, String filedName, boolean ascFlag) {
			CollectionUtil.sort(list, filedName, ascFlag);
		}




}
