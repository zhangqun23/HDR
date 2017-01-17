package com.mvc.service.impl;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mvc.dao.ExpendFormDao;
import com.mvc.entityReport.LinenExpend;
import com.mvc.entityReport.RoomExpend;
import com.mvc.entityReport.WashExpend;
import com.mvc.service.ExpendFormService;
import com.utils.CollectionUtil;
import com.utils.FileHelper;
import com.utils.WordHelper;


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
		sortAndWrite1(listGoal, "room_id", true, "orderNum");
		
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

	// 布草导出
	@Override
	public ResponseEntity<byte[]> exportLinenExpendForm(Map<String, Object> map, String path, String tempPath) {
		String formName = (String) map.remove("formName");
		
		ResponseEntity<byte[]> byteArr = null;
		try{
			WordHelper<LinenExpend> le = new WordHelper<LinenExpend>();
			String fileName = "客房部" + formName + "布草使用量统计表.docx";
			path = FileHelper.transPath(fileName, path);// 解析后的上传路径
			OutputStream out = new FileOutputStream(path);
			
			List<Object> listSource = expendFormDao.selectlinenExpend(map);
			Iterator<Object> it = listSource.iterator();
			List<LinenExpend> listGoal = objToLinenExpand(it);
			
			LinenExpend sum = sumLinenExpend(listGoal);// 合计
			LinenExpend avg = avgLinenExpend(listGoal);// 平均
			listGoal.add(sum);
			listGoal.add(avg);
			
			Map<String, Object> listMap = new HashMap<String, Object>();
			listMap.put("0", listGoal);// key存放该list在word中表格的索引，value存放list
			Map<String, Object> contentMap = new HashMap<String, Object>();
			String startTime = (String) map.get("startTime");
			String endTime = (String) map.get("endTime");
			contentMap.put("${formName}", formName);
			contentMap.put("${startTime}", startTime.substring(0, 10));
			contentMap.put("${endTime}", endTime.substring(0, 10));

			le.export2007Word(tempPath, listMap, contentMap, 2, out);// 用模板生成word
			out.close();
			byteArr = FileHelper.downloadFile(fileName, path);// 提醒下载
		}catch (Exception ex) {
			ex.printStackTrace();
		}
		return byteArr;
	}
	
	/**
	 * list求和
	 * 
	 * @param list
	 * @return
	 */
	private LinenExpend sumLinenExpend(List<LinenExpend> list) {
		LinenExpend sum = new LinenExpend();
		Iterator<LinenExpend> it = list.iterator();
		
		Long sum_bato_num = (long) 0;
		Long sum_facl_num = (long) 0;
		Long sum_besh_num = (long) 0;
		Long sum_hato_num = (long) 0;
		Long sum_medo_num = (long) 0;
		Long sum_flto_num = (long) 0;
		Long sum_baro_num = (long) 0;
		Long sum_slba_num = (long) 0;
		Long sum_duto_num = (long) 0;
		Long sum_pill_num = (long) 0;
		Long sum_shop_num = (long) 0;
		Long sum_laba_num = (long) 0;
		Long sum_piin_num = (long) 0;
		Long sum_blan_num = (long) 0;
		
		LinenExpend linenExpend = null;
		while (it.hasNext()) {
			linenExpend = it.next();
			sum_bato_num += Integer.valueOf(linenExpend.getBato_num());
			sum_facl_num += Integer.valueOf(linenExpend.getFacl_num());
			sum_besh_num += Integer.valueOf(linenExpend.getBesh_num());
			sum_hato_num += Integer.valueOf(linenExpend.getHato_num());
			sum_medo_num += Integer.valueOf(linenExpend.getMedo_num());
			sum_flto_num += Integer.valueOf(linenExpend.getFlto_num());
			sum_baro_num += Integer.valueOf(linenExpend.getBaro_num());
			sum_slba_num += Integer.valueOf(linenExpend.getSlba_num());
			sum_duto_num += Integer.valueOf(linenExpend.getDuto_num());
			sum_pill_num += Integer.valueOf(linenExpend.getPill_num());
			sum_shop_num += Integer.valueOf(linenExpend.getShop_num());
			sum_laba_num += Integer.valueOf(linenExpend.getLaba_num());
			sum_piin_num += Integer.valueOf(linenExpend.getPiin_num());
			sum_blan_num += Integer.valueOf(linenExpend.getBlan_num());	
		}
		sum.setOrderNum("合计");
		sum.setBato_num(String.valueOf(sum_bato_num));
		sum.setFacl_num(String.valueOf(sum_facl_num));
		sum.setBesh_num(String.valueOf(sum_besh_num));
		sum.setHato_num(String.valueOf(sum_hato_num));
		sum.setMedo_num(String.valueOf(sum_medo_num));
		sum.setFlto_num(String.valueOf(sum_flto_num));
		sum.setBaro_num(String.valueOf(sum_baro_num));
		sum.setSlba_num(String.valueOf(sum_slba_num));
		sum.setDuto_num(String.valueOf(sum_duto_num));
		sum.setPill_num(String.valueOf(sum_pill_num));
		sum.setShop_num(String.valueOf(sum_shop_num));
		sum.setLaba_num(String.valueOf(sum_laba_num));
		sum.setPiin_num(String.valueOf(sum_piin_num));
		sum.setBlan_num(String.valueOf(sum_blan_num));
		
		return sum;
	}
	
	/**
	 * list求平均
	 * 
	 * @param list
	 * @return
	 */
	private LinenExpend avgLinenExpend(List<LinenExpend> list) {
		
		LinenExpend avg = new LinenExpend();
		Iterator<LinenExpend> it = list.iterator();
		
		Long sum_bato_num = (long) 0;
		Long sum_facl_num = (long) 0;
		Long sum_besh_num = (long) 0;
		Long sum_hato_num = (long) 0;
		Long sum_medo_num = (long) 0;
		Long sum_flto_num = (long) 0;
		Long sum_baro_num = (long) 0;
		Long sum_slba_num = (long) 0;
		Long sum_duto_num = (long) 0;
		Long sum_pill_num = (long) 0;
		Long sum_shop_num = (long) 0;
		Long sum_laba_num = (long) 0;
		Long sum_piin_num = (long) 0;
		Long sum_blan_num = (long) 0;
		
		LinenExpend linenExpend = null;
		long chu = Integer.valueOf(list.size());
		System.out.println(chu);
		while (it.hasNext()) {
			linenExpend = it.next();
			sum_bato_num += Integer.valueOf(linenExpend.getBato_num());
			sum_facl_num += Integer.valueOf(linenExpend.getFacl_num());
			sum_besh_num += Integer.valueOf(linenExpend.getBesh_num());
			sum_hato_num += Integer.valueOf(linenExpend.getHato_num());
			sum_medo_num += Integer.valueOf(linenExpend.getMedo_num());
			sum_flto_num += Integer.valueOf(linenExpend.getFlto_num());
			sum_baro_num += Integer.valueOf(linenExpend.getBaro_num());
			sum_slba_num += Integer.valueOf(linenExpend.getSlba_num());
			sum_duto_num += Integer.valueOf(linenExpend.getDuto_num());
			sum_pill_num += Integer.valueOf(linenExpend.getPill_num());
			sum_shop_num += Integer.valueOf(linenExpend.getShop_num());
			sum_laba_num += Integer.valueOf(linenExpend.getLaba_num());
			sum_piin_num += Integer.valueOf(linenExpend.getPiin_num());
			sum_blan_num += Integer.valueOf(linenExpend.getBlan_num());	
		}
		avg.setOrderNum("平均");
		avg.setBato_num(String.valueOf(sum_bato_num/chu));
		avg.setFacl_num(String.valueOf(sum_facl_num/chu));
		avg.setBesh_num(String.valueOf(sum_besh_num/chu));
		avg.setHato_num(String.valueOf(sum_hato_num/chu));
		avg.setMedo_num(String.valueOf(sum_medo_num/chu));
		avg.setFlto_num(String.valueOf(sum_flto_num/chu));
		avg.setBaro_num(String.valueOf(sum_baro_num/chu));
		avg.setSlba_num(String.valueOf(sum_slba_num/chu));
		avg.setDuto_num(String.valueOf(sum_duto_num/chu));
		avg.setPill_num(String.valueOf(sum_pill_num/chu));
		avg.setShop_num(String.valueOf(sum_shop_num/chu));
		avg.setLaba_num(String.valueOf(sum_laba_num/chu));
		avg.setPiin_num(String.valueOf(sum_piin_num/chu));
		avg.setBlan_num(String.valueOf(sum_blan_num/chu));
		
		return avg;
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

	//房间耗品统计
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
        sortAndWrite2(listGoal, "room_id", true, "orderNum");
		
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

	//卫生间耗品统计
	@Override
	public List<WashExpend> selectWashExpend(Map<String, Object> map) {
		
		List<Object> listSource = expendFormDao.selectwashExpend(map);
		Iterator<Object> it = listSource.iterator();
		List<WashExpend> listGoal = objToWashExpand(it);

		return listGoal;
	}
	private List<WashExpend> objToWashExpand(Iterator<Object> it) {
		List<WashExpend> listGoal = new ArrayList<WashExpend>();
		Object[] obj = null;
		WashExpend washExpend = null;
		while (it.hasNext()) {
			obj = (Object[]) it.next();
			washExpend = new WashExpend();
			washExpend.setRoom_id(obj[0].toString());
			washExpend.setToth_num(obj[1].toString());
			washExpend.setRopa_num(obj[2].toString());
			washExpend.setRins_num(obj[3].toString());
			washExpend.setBafo_num(obj[4].toString());
			washExpend.setHaco_num(obj[5].toString());
			washExpend.setShge_num(obj[6].toString());
			washExpend.setCapa_num(obj[7].toString());
			washExpend.setGarb_num(obj[8].toString());
			washExpend.setPaex_num(obj[9].toString());
			washExpend.setPeep_num(obj[10].toString());
			washExpend.setShca_num(obj[11].toString());
			washExpend.setShav_num(obj[12].toString());
			washExpend.setComb_num(obj[13].toString());
			washExpend.setShcl_num(obj[14].toString());
			washExpend.setSoap_num(obj[15].toString());
			washExpend.setNacl_num(obj[16].toString());
			washExpend.setFlow_num(obj[17].toString());
			washExpend.setBasa_num(obj[18].toString());
			washExpend.setScpa_num(obj[19].toString());
			washExpend.setRugl_num(obj[20].toString());
			washExpend.setDete_num(obj[21].toString());

			listGoal.add(washExpend);
		}
        sortAndWrite3(listGoal, "room_id", true, "orderNum");
		
		Iterator<WashExpend> itGoal = listGoal.iterator();
		int i = 0;
		washExpend = null;
		while (itGoal.hasNext()) {
			i++;// 注意：若写序号放在第一个循环中，根据orderNum排序后存在问题：2在10后面
			washExpend = (WashExpend) itGoal.next();
			washExpend.setOrderNum(String.valueOf(i));
		}
		//System.out.println(listGoal.size());

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
	private void sortAndWrite3(List<WashExpend> list, String room_id, boolean ascFlag, String writeField) {
		CollectionUtil.sort(list, room_id, ascFlag);
		CollectionUtil<WashExpend> collectionUtil = new CollectionUtil<WashExpend>();
		collectionUtil.writeSort(list, writeField);
	}


}
