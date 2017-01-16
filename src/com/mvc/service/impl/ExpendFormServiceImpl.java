package com.mvc.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvc.dao.ExpendFormDao;
import com.mvc.entityReport.LinenExpend;
import com.mvc.entityReport.RoomExpend;
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

	//布草统计
	@Override
	public List<LinenExpend> selectLinenExpend(Map<String, Object> map) {
		
		List<Object> listSource = expendFormDao.selectlinenExpend(map);
		Iterator<Object> it = listSource.iterator();
		List<LinenExpend> listGoal = objToLinenExpand(it);

		return listGoal;
	}
	
	//排序
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
		sortAndWrite1(listGoal, "room_id", true, "room_id_rank");
		
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
	 * @param room_id
	 *            按指定字段排序
	 * @param ascFlag
	 *            true升序,false降序
	 * @param writeField
	 *            向指定字段插入序号
	 */
	private void sortAndWrite1(List<LinenExpend> list, String room_id, boolean ascFlag, String writeField) {
		CollectionUtil.sort(list, room_id, ascFlag);
		CollectionUtil<LinenExpend> collectionUtil = new CollectionUtil<LinenExpend>();
		collectionUtil.writeSort(list, writeField);
	}

	@Override
	public List<RoomExpend> selectRoomExpend(Map<String, Object> map) {
		
		List<Object> listSource = expendFormDao.selectroomExpend(map);
		Iterator<Object> it = listSource.iterator();
		List<RoomExpend> listGoal = objToRoomExpand(it);

		return listGoal;
	}
	
	private List<RoomExpend> objToRoomExpand(Iterator<Object> it) {
		List<RoomExpend> listGoal = new ArrayList<RoomExpend>();
		Object[] obj = null;
		RoomExpend roomExpend = null;
		while (it.hasNext()) {
			obj = (Object[]) it.next();
			roomExpend = new RoomExpend();
			roomExpend.setRoom_id(obj[0].toString());
			roomExpend.setUmbr_num(obj[1].toString());
			roomExpend.setCoff_num(obj[2].toString());
			roomExpend.setSuge_num(obj[3].toString());
			roomExpend.setCoup_num(obj[4].toString());
			roomExpend.setPenc_num(obj[5].toString());
			roomExpend.setErse_num(obj[6].toString());
			roomExpend.setClca_num(obj[7].toString());
			roomExpend.setFati_num(obj[8].toString());
			roomExpend.setEnca_num(obj[9].toString());
			roomExpend.setBage_num(obj[10].toString());
			roomExpend.setTeab_num(obj[11].toString());
			roomExpend.setMeca_num(obj[12].toString());
			roomExpend.setOpbo_num(obj[13].toString());
			roomExpend.setBlte_num(obj[14].toString());
			roomExpend.setDnds_num(obj[15].toString());
			roomExpend.setTvca_num(obj[16].toString());
			roomExpend.setOrel_num(obj[17].toString());
			roomExpend.setMemo_num(obj[18].toString());
			roomExpend.setCoas_num(obj[19].toString());
			roomExpend.setMatc_num(obj[20].toString());
			roomExpend.setMapp_num(obj[21].toString());
			roomExpend.setRule_num(obj[22].toString());
			roomExpend.setStat_num(obj[23].toString());
			roomExpend.setClip_num(obj[24].toString());
			roomExpend.setBape_num(obj[25].toString());
			roomExpend.setComp_num(obj[26].toString());
			roomExpend.setLali_num(obj[27].toString());
			roomExpend.setLosu_num(obj[28].toString());
			roomExpend.setShpa_num(obj[29].toString());
			roomExpend.setAnma_num(obj[30].toString());
			roomExpend.setGrte_num(obj[31].toString());
			roomExpend.setChsl_num(obj[32].toString());
			roomExpend.setCocl_num(obj[33].toString());
			roomExpend.setArel_num(obj[34].toString());

			listGoal.add(roomExpend);
		}
        sortAndWrite2(listGoal, "room_id", true, "room_id_rank");
		
		Iterator<RoomExpend> itGoal = listGoal.iterator();
		int i = 0;
		roomExpend = null;
		while (itGoal.hasNext()) {
			i++;// 注意：若写序号放在第一个循环中，根据orderNum排序后存在问题：2在10后面
			roomExpend = (RoomExpend) itGoal.next();
			roomExpend.setOrderNum(String.valueOf(i));
		}

		return listGoal;
	}
	
	/**
	 * 排序并插入序号
	 * 
	 * @param list
	 * @param room_id
	 *            按指定字段排序
	 * @param ascFlag
	 *            true升序,false降序
	 * @param writeField
	 *            向指定字段插入序号
	 */
	private void sortAndWrite2(List<RoomExpend> list, String room_id, boolean ascFlag, String writeField) {
		CollectionUtil.sort(list, room_id, ascFlag);
		CollectionUtil<RoomExpend> collectionUtil = new CollectionUtil<RoomExpend>();
		collectionUtil.writeSort(list, writeField);
	}
	


}
