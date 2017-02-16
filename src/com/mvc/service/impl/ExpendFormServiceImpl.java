package com.mvc.service.impl;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
import com.mvc.entityReport.ExpendAnalyse;
import com.mvc.entityReport.LinenCount;
import com.mvc.entityReport.LinenExpend;
import com.mvc.entityReport.MiniCount;
import com.mvc.entityReport.MiniExpend;
import com.mvc.entityReport.RoomCount;
import com.mvc.entityReport.RoomExpend;
import com.mvc.entityReport.WashCount;
import com.mvc.entityReport.WashExpend;
import com.mvc.service.ExpendFormService;
import com.utils.CollectionUtil;
import com.utils.FileHelper;
import com.utils.Pager;
import com.utils.PictureUtil;
import com.utils.StringUtil;
import com.utils.WordHelper;

/**
 * 耗品相关的service层接口实现
 * 
 * @author wq
 * @date 2017年1月13日
 */
@Service("expendFormServiceImpl")
public class ExpendFormServiceImpl implements ExpendFormService {

	@Autowired
	ExpendFormDao expendFormDao;

	//布草总数统计
	@Override
	public LinenCount linenTotleCount(Map<String, Object> map){
		
		List<Integer> listCondition = expendFormDao.selectCondition("房间布草");
		List<Object> listSource = expendFormDao.linenTotleCount(map, listCondition);
		
		Iterator<Object> it = listSource.iterator();
		LinenCount listGoal = objToLinenCount(it);

		return listGoal;
	}
	
	//房间耗品总数统计
	@Override
	public RoomCount roomTotleCount(Map<String, Object> map){
			
		List<Integer> listCondition = expendFormDao.selectCondition("房间易耗品");
		List<Object> listSource = expendFormDao.roomTotleCount(map, listCondition);
			
		Iterator<Object> it = listSource.iterator();
		RoomCount listGoal = objToRoomCount(it);
		
		return listGoal;
	}
		
	//卫生间耗品总数统计
	@Override
	public WashCount washTotleCount(Map<String, Object> map){
			
		List<Integer> listCondition = expendFormDao.selectCondition("卫生间易耗品");
		List<Object> listSource = expendFormDao.washTotleCount(map, listCondition);
			
		Iterator<Object> it = listSource.iterator();
		WashCount listGoal = objToWashCount(it);

		return listGoal;
	}
	
	//迷你吧总数统计
	@Override
	public MiniCount miniTotleCount(Map<String, Object> map){
		
		List<Object> listSource = expendFormDao.miniTotleCount(map);
			
		Iterator<Object> it = listSource.iterator();
		MiniCount listGoal = objToMiniCount(it);

		return listGoal;
	}
	
	// 布草统计
	@Override
	public List<LinenExpend> selectLinenPage(Map<String, Object> map, Pager pager) {

		List<Integer> listCondition = expendFormDao.selectCondition("房间布草");
		List<Object> listSource = expendFormDao.selectlinenPage(map, pager.getOffset(), pager.getPageSize(),
				listCondition);

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
			linenExpend.setSlba_num(obj[1].toString());
			linenExpend.setDuto_num(obj[2].toString());
			linenExpend.setLaba_num(obj[3].toString());
			linenExpend.setBesh_num(obj[4].toString());
			linenExpend.setFacl_num(obj[5].toString());
			linenExpend.setBato_num(obj[6].toString());
			linenExpend.setHato_num(obj[7].toString());
			linenExpend.setMedo_num(obj[8].toString());
			linenExpend.setFlto_num(obj[9].toString());
			linenExpend.setBaro_num(obj[10].toString());
			linenExpend.setPill_num(obj[11].toString());
			linenExpend.setPiin_num(obj[12].toString());
			linenExpend.setBlan_num(obj[13].toString());
			linenExpend.setShop_num(obj[14].toString());

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
	
	// 布草总数排序
	private LinenCount objToLinenCount(Iterator<Object> it) {

		LinenCount linenCount = null;

		linenCount = new LinenCount();
		linenCount.setSum_slba(it.next().toString());
		linenCount.setSum_duto(it.next().toString());
		linenCount.setSum_laba(it.next().toString());
		linenCount.setSum_besh(it.next().toString());
		linenCount.setSum_facl(it.next().toString());
		linenCount.setSum_bato(it.next().toString());
		linenCount.setSum_hato(it.next().toString());
		linenCount.setSum_medo(it.next().toString());
		linenCount.setSum_flto(it.next().toString());
		linenCount.setSum_baro(it.next().toString());
		linenCount.setSum_pill(it.next().toString());
		linenCount.setSum_piin(it.next().toString());
		linenCount.setSum_blan(it.next().toString());
		linenCount.setSum_shop(it.next().toString());

		return linenCount;
	}

	// 房间耗品总数排序
	private RoomCount objToRoomCount(Iterator<Object> it) {

		RoomCount roomCount = null;

		roomCount = new RoomCount();
		roomCount.setSum_coas(it.next().toString());
		roomCount.setSum_penc(it.next().toString());
		roomCount.setSum_rule(it.next().toString());
		roomCount.setSum_erse(it.next().toString());
		roomCount.setSum_cocl(it.next().toString());
		roomCount.setSum_chsl(it.next().toString());
		roomCount.setSum_umbr(it.next().toString());
		roomCount.setSum_dnds(it.next().toString());
		roomCount.setSum_meca(it.next().toString());
		roomCount.setSum_comp(it.next().toString());
		roomCount.setSum_shpa(it.next().toString());
		roomCount.setSum_bape(it.next().toString());
		roomCount.setSum_bage(it.next().toString());
		roomCount.setSum_clip(it.next().toString());
		roomCount.setSum_orel(it.next().toString());
		roomCount.setSum_arel(it.next().toString());
		roomCount.setSum_fati(it.next().toString());
		roomCount.setSum_memo(it.next().toString());
		roomCount.setSum_stat(it.next().toString());
		roomCount.setSum_lali(it.next().toString());
		roomCount.setSum_opbo(it.next().toString());
		roomCount.setSum_mapp(it.next().toString());
		roomCount.setSum_tvca(it.next().toString());
		roomCount.setSum_clca(it.next().toString());
		roomCount.setSum_enca(it.next().toString());
		roomCount.setSum_grte(it.next().toString());
		roomCount.setSum_blte(it.next().toString());
		roomCount.setSum_suge(it.next().toString());
		roomCount.setSum_losu(it.next().toString());
		roomCount.setSum_teab(it.next().toString());
		roomCount.setSum_coff(it.next().toString());
		roomCount.setSum_coup(it.next().toString());
		roomCount.setSum_anma(it.next().toString());
		roomCount.setSum_matc(it.next().toString());

		return roomCount;
	}
	
	// 卫生间耗品总数排序
	private WashCount objToWashCount(Iterator<Object> it) {
		
		WashCount washCount = null;

		washCount = new WashCount();	
		washCount.setSum_toth(it.next().toString());
		washCount.setSum_ropa(it.next().toString());
		washCount.setSum_paex(it.next().toString());
		washCount.setSum_comb(it.next().toString());
		washCount.setSum_shcl(it.next().toString());
		washCount.setSum_shca(it.next().toString());
		washCount.setSum_nacl(it.next().toString());
		washCount.setSum_shav(it.next().toString());
		washCount.setSum_peep(it.next().toString());
		washCount.setSum_rins(it.next().toString());
		washCount.setSum_bafo(it.next().toString());
		washCount.setSum_haco(it.next().toString());
		washCount.setSum_shge(it.next().toString());
		washCount.setSum_flow(it.next().toString());
		washCount.setSum_basa(it.next().toString());
		washCount.setSum_babr(it.next().toString());
		washCount.setSum_tocl(it.next().toString());
		washCount.setSum_bacl(it.next().toString());
		washCount.setSum_thim(it.next().toString());
		washCount.setSum_dete(it.next().toString());
		washCount.setSum_clbr(it.next().toString());
		washCount.setSum_scpa(it.next().toString());
		washCount.setSum_rugl(it.next().toString());
		washCount.setSum_capa(it.next().toString());
		washCount.setSum_garb(it.next().toString());
		washCount.setSum_soap(it.next().toString());
		
		return washCount;
	}
	
	// 卫生间耗品总数排序
	private MiniCount objToMiniCount(Iterator<Object> it) {
			
		MiniCount miniCount = null;

		miniCount = new MiniCount();	
		miniCount.setSum_redb(it.next().toString());
		miniCount.setSum_coco(it.next().toString());
		miniCount.setSum_pari(it.next().toString());
		miniCount.setSum_bige(it.next().toString());
		miniCount.setSum_jdba(it.next().toString());
		miniCount.setSum_tine(it.next().toString());
		miniCount.setSum_kunl(it.next().toString());
		miniCount.setSum_wine(it.next().toString());
		miniCount.setSum_bree(it.next().toString());
		miniCount.setSum_vodk(it.next().toString());
		miniCount.setSum_auru(it.next().toString());
		miniCount.setSum_qing(it.next().toString());
		miniCount.setSum_spri(it.next().toString());
		miniCount.setSum_nail(it.next().toString());
		miniCount.setSum_abcs(it.next().toString());
		miniCount.setSum_card(it.next().toString());
		miniCount.setSum_como(it.next().toString());
			
		return miniCount;
	}
		
	// 布草导出
	@Override
	public ResponseEntity<byte[]> exportLinenExpendForm(Map<String, Object> map, String path, String tempPath) {
		String formName = (String) map.remove("formName");

		ResponseEntity<byte[]> byteArr = null;
		try {
			WordHelper<LinenExpend> le = new WordHelper<LinenExpend>();
			String fileName = "客房部" + formName + "布草使用量统计表.docx";
			path = FileHelper.transPath(fileName, path);// 解析后的上传路径
			OutputStream out = new FileOutputStream(path);

			List<Integer> listCondition = expendFormDao.selectCondition("房间布草");
			List<Object> listSource = expendFormDao.selectlinenExpend(map, listCondition);
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
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return byteArr;
	}

	/**
	 * 布草list求和
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
	 * 布草list求平均
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
		Float chu = Float.valueOf(list.size());
		if (chu != 0) {
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
			avg.setBato_num(String.valueOf(StringUtil.save2Float(sum_bato_num / chu)));
			avg.setFacl_num(String.valueOf(StringUtil.save2Float(sum_facl_num / chu)));
			avg.setBesh_num(String.valueOf(StringUtil.save2Float(sum_besh_num / chu)));
			avg.setHato_num(String.valueOf(StringUtil.save2Float(sum_hato_num / chu)));
			avg.setMedo_num(String.valueOf(StringUtil.save2Float(sum_medo_num / chu)));
			avg.setFlto_num(String.valueOf(StringUtil.save2Float(sum_flto_num / chu)));
			avg.setBaro_num(String.valueOf(StringUtil.save2Float(sum_baro_num / chu)));
			avg.setSlba_num(String.valueOf(StringUtil.save2Float(sum_slba_num / chu)));
			avg.setDuto_num(String.valueOf(StringUtil.save2Float(sum_duto_num / chu)));
			avg.setPill_num(String.valueOf(StringUtil.save2Float(sum_pill_num / chu)));
			avg.setShop_num(String.valueOf(StringUtil.save2Float(sum_shop_num / chu)));
			avg.setLaba_num(String.valueOf(StringUtil.save2Float(sum_laba_num / chu)));
			avg.setPiin_num(String.valueOf(StringUtil.save2Float(sum_piin_num / chu)));
			avg.setBlan_num(String.valueOf(StringUtil.save2Float(sum_blan_num / chu)));
		}
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

	// 排序
	private List<ExpendAnalyse> objToExpandAnalyse(Iterator<Object> it) {
		List<ExpendAnalyse> listGoal = new ArrayList<ExpendAnalyse>();
		Object[] obj = null;
		ExpendAnalyse expendAnalyse = null;
		while (it.hasNext()) {
			obj = (Object[]) it.next();
			expendAnalyse = new ExpendAnalyse();
			expendAnalyse.setGoods_name(obj[0].toString());
			expendAnalyse.setGoods_num(obj[1].toString());

			listGoal.add(expendAnalyse);
		}

		return listGoal;
	}

	// 房间耗品统计
	@Override
	public List<RoomExpend> selectRoomExpend(Map<String, Object> map, Pager pager) {

		List<Integer> listCondition = expendFormDao.selectCondition("房间易耗品");
		List<Object> listSource = expendFormDao.selectroomPage(map, pager.getOffset(), pager.getPageSize(),
				listCondition);
		
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
			roomExpend.setUmbr_num(obj[7].toString());
			roomExpend.setCoff_num(obj[31].toString());
			roomExpend.setSuge_num(obj[28].toString());
			roomExpend.setCoup_num(obj[32].toString());
			roomExpend.setPenc_num(obj[2].toString());
			roomExpend.setErse_num(obj[4].toString());
			roomExpend.setClca_num(obj[24].toString());
			roomExpend.setFati_num(obj[17].toString());
			roomExpend.setEnca_num(obj[25].toString());
			roomExpend.setBage_num(obj[13].toString());
			roomExpend.setTeab_num(obj[30].toString());
			roomExpend.setMeca_num(obj[9].toString());
			roomExpend.setOpbo_num(obj[21].toString());
			roomExpend.setBlte_num(obj[27].toString());
			roomExpend.setDnds_num(obj[8].toString());
			roomExpend.setTvca_num(obj[23].toString());
			roomExpend.setOrel_num(obj[15].toString());
			roomExpend.setMemo_num(obj[18].toString());
			roomExpend.setCoas_num(obj[1].toString());
			roomExpend.setMatc_num(obj[34].toString());
			roomExpend.setMapp_num(obj[22].toString());
			roomExpend.setRule_num(obj[3].toString());
			roomExpend.setStat_num(obj[19].toString());
			roomExpend.setClip_num(obj[14].toString());
			roomExpend.setBape_num(obj[12].toString());
			roomExpend.setComp_num(obj[10].toString());
			roomExpend.setLali_num(obj[20].toString());
			roomExpend.setLosu_num(obj[29].toString());
			roomExpend.setShpa_num(obj[11].toString());
			roomExpend.setAnma_num(obj[33].toString());
			roomExpend.setGrte_num(obj[26].toString());
			roomExpend.setChsl_num(obj[6].toString());
			roomExpend.setCocl_num(obj[5].toString());
			roomExpend.setArel_num(obj[16].toString());

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

	// 房间耗品导出
	@Override
	public ResponseEntity<byte[]> exportRoomExpendForm(Map<String, Object> map, String path, String tempPath) {
		String formName = (String) map.remove("formName");

		ResponseEntity<byte[]> byteArr = null;
		try {
			WordHelper<RoomExpend> we = new WordHelper<RoomExpend>();
			String fileName = "客房部" + formName + "房间耗品使用量统计表.docx";
			path = FileHelper.transPath(fileName, path);// 解析后的上传路径
			OutputStream out = new FileOutputStream(path);

			List<Integer> listCondition = expendFormDao.selectCondition("房间易耗品");
			List<Object> listSource = expendFormDao.selectroomExpend(map, listCondition);
			Iterator<Object> it = listSource.iterator();
			List<RoomExpend> listGoal = objToRoomExpand(it);

			RoomExpend sum = sumRoomExpend(listGoal);// 合计
			RoomExpend avg = avgRoomExpend(listGoal);// 平均
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

			we.export2007Word(tempPath, listMap, contentMap, 2, out);// 用模板生成word
			out.close();
			byteArr = FileHelper.downloadFile(fileName, path);// 提醒下载
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return byteArr;
	}

	/**
	 * 房间耗品list求和
	 * 
	 * @param list
	 * @return
	 */
	private RoomExpend sumRoomExpend(List<RoomExpend> list) {
		RoomExpend sum = new RoomExpend();
		Iterator<RoomExpend> re = list.iterator();

		Long sum_umbr_num = (long) 0;
		Long sum_coff_num = (long) 0;
		Long sum_suge_num = (long) 0;
		Long sum_coup_num = (long) 0;
		Long sum_penc_num = (long) 0;
		Long sum_erse_num = (long) 0;
		Long sum_clca_num = (long) 0;
		Long sum_fati_num = (long) 0;
		Long sum_enca_num = (long) 0;
		Long sum_bage_num = (long) 0;
		Long sum_teab_num = (long) 0;
		Long sum_meca_num = (long) 0;
		Long sum_opbo_num = (long) 0;
		Long sum_blte_num = (long) 0;
		Long sum_dnds_num = (long) 0;
		Long sum_tvca_num = (long) 0;
		Long sum_orel_num = (long) 0;
		Long sum_memo_num = (long) 0;
		Long sum_coas_num = (long) 0;
		Long sum_matc_num = (long) 0;
		Long sum_mapp_num = (long) 0;
		Long sum_rule_num = (long) 0;
		Long sum_stat_num = (long) 0;
		Long sum_clip_num = (long) 0;
		Long sum_bape_num = (long) 0;
		Long sum_comp_num = (long) 0;
		Long sum_lali_num = (long) 0;
		Long sum_losu_num = (long) 0;
		Long sum_shpa_num = (long) 0;
		Long sum_anma_num = (long) 0;
		Long sum_grte_num = (long) 0;
		Long sum_chsl_num = (long) 0;
		Long sum_cocl_num = (long) 0;
		Long sum_arel_num = (long) 0;

		RoomExpend roomExpend = null;
		while (re.hasNext()) {
			roomExpend = re.next();
			sum_umbr_num += Integer.valueOf(roomExpend.getUmbr_num());
			sum_coff_num += Integer.valueOf(roomExpend.getCoff_num());
			sum_suge_num += Integer.valueOf(roomExpend.getSuge_num());
			sum_coup_num += Integer.valueOf(roomExpend.getCoup_num());
			sum_penc_num += Integer.valueOf(roomExpend.getPenc_num());
			sum_erse_num += Integer.valueOf(roomExpend.getErse_num());
			sum_clca_num += Integer.valueOf(roomExpend.getClca_num());
			sum_fati_num += Integer.valueOf(roomExpend.getFati_num());
			sum_enca_num += Integer.valueOf(roomExpend.getEnca_num());
			sum_bage_num += Integer.valueOf(roomExpend.getBage_num());
			sum_teab_num += Integer.valueOf(roomExpend.getTeab_num());
			sum_meca_num += Integer.valueOf(roomExpend.getMeca_num());
			sum_opbo_num += Integer.valueOf(roomExpend.getOpbo_num());
			sum_blte_num += Integer.valueOf(roomExpend.getBlte_num());
			sum_dnds_num += Integer.valueOf(roomExpend.getDnds_num());
			sum_tvca_num += Integer.valueOf(roomExpend.getTvca_num());
			sum_orel_num += Integer.valueOf(roomExpend.getOrel_num());
			sum_memo_num += Integer.valueOf(roomExpend.getMemo_num());
			sum_coas_num += Integer.valueOf(roomExpend.getCoas_num());
			sum_matc_num += Integer.valueOf(roomExpend.getMatc_num());
			sum_mapp_num += Integer.valueOf(roomExpend.getMapp_num());
			sum_rule_num += Integer.valueOf(roomExpend.getRule_num());
			sum_stat_num += Integer.valueOf(roomExpend.getStat_num());
			sum_clip_num += Integer.valueOf(roomExpend.getClip_num());
			sum_bape_num += Integer.valueOf(roomExpend.getBape_num());
			sum_comp_num += Integer.valueOf(roomExpend.getComp_num());
			sum_lali_num += Integer.valueOf(roomExpend.getLali_num());
			sum_losu_num += Integer.valueOf(roomExpend.getLosu_num());
			sum_shpa_num += Integer.valueOf(roomExpend.getShpa_num());
			sum_anma_num += Integer.valueOf(roomExpend.getAnma_num());
			sum_grte_num += Integer.valueOf(roomExpend.getGrte_num());
			sum_chsl_num += Integer.valueOf(roomExpend.getChsl_num());
			sum_cocl_num += Integer.valueOf(roomExpend.getCocl_num());
			sum_arel_num += Integer.valueOf(roomExpend.getArel_num());
		}
		sum.setOrderNum("合计");
		sum.setUmbr_num(String.valueOf(sum_umbr_num));
		sum.setCoff_num(String.valueOf(sum_coff_num));
		sum.setSuge_num(String.valueOf(sum_suge_num));
		sum.setCoup_num(String.valueOf(sum_coup_num));
		sum.setPenc_num(String.valueOf(sum_penc_num));
		sum.setErse_num(String.valueOf(sum_erse_num));
		sum.setClca_num(String.valueOf(sum_clca_num));
		sum.setFati_num(String.valueOf(sum_fati_num));
		sum.setEnca_num(String.valueOf(sum_enca_num));
		sum.setBage_num(String.valueOf(sum_bage_num));
		sum.setTeab_num(String.valueOf(sum_teab_num));
		sum.setMeca_num(String.valueOf(sum_meca_num));
		sum.setOpbo_num(String.valueOf(sum_opbo_num));
		sum.setBlte_num(String.valueOf(sum_blte_num));
		sum.setDnds_num(String.valueOf(sum_dnds_num));
		sum.setTvca_num(String.valueOf(sum_tvca_num));
		sum.setOrel_num(String.valueOf(sum_orel_num));
		sum.setMemo_num(String.valueOf(sum_memo_num));
		sum.setCoas_num(String.valueOf(sum_coas_num));
		sum.setMatc_num(String.valueOf(sum_matc_num));
		sum.setMapp_num(String.valueOf(sum_mapp_num));
		sum.setRule_num(String.valueOf(sum_rule_num));
		sum.setStat_num(String.valueOf(sum_stat_num));
		sum.setClip_num(String.valueOf(sum_clip_num));
		sum.setBape_num(String.valueOf(sum_bape_num));
		sum.setComp_num(String.valueOf(sum_comp_num));
		sum.setLali_num(String.valueOf(sum_lali_num));
		sum.setLosu_num(String.valueOf(sum_losu_num));
		sum.setShpa_num(String.valueOf(sum_shpa_num));
		sum.setAnma_num(String.valueOf(sum_anma_num));
		sum.setGrte_num(String.valueOf(sum_grte_num));
		sum.setChsl_num(String.valueOf(sum_chsl_num));
		sum.setCocl_num(String.valueOf(sum_cocl_num));
		sum.setArel_num(String.valueOf(sum_arel_num));

		return sum;
	}

	/**
	 * 房间耗品list求平均
	 * 
	 * @param list
	 * @return
	 */
	private RoomExpend avgRoomExpend(List<RoomExpend> list) {

		RoomExpend avg = new RoomExpend();
		Iterator<RoomExpend> re = list.iterator();
		Float chu = Float.valueOf(list.size());

		Long sum_umbr_num = (long) 0;
		Long sum_coff_num = (long) 0;
		Long sum_suge_num = (long) 0;
		Long sum_coup_num = (long) 0;
		Long sum_penc_num = (long) 0;
		Long sum_erse_num = (long) 0;
		Long sum_clca_num = (long) 0;
		Long sum_fati_num = (long) 0;
		Long sum_enca_num = (long) 0;
		Long sum_bage_num = (long) 0;
		Long sum_teab_num = (long) 0;
		Long sum_meca_num = (long) 0;
		Long sum_opbo_num = (long) 0;
		Long sum_blte_num = (long) 0;
		Long sum_dnds_num = (long) 0;
		Long sum_tvca_num = (long) 0;
		Long sum_orel_num = (long) 0;
		Long sum_memo_num = (long) 0;
		Long sum_coas_num = (long) 0;
		Long sum_matc_num = (long) 0;
		Long sum_mapp_num = (long) 0;
		Long sum_rule_num = (long) 0;
		Long sum_stat_num = (long) 0;
		Long sum_clip_num = (long) 0;
		Long sum_bape_num = (long) 0;
		Long sum_comp_num = (long) 0;
		Long sum_lali_num = (long) 0;
		Long sum_losu_num = (long) 0;
		Long sum_shpa_num = (long) 0;
		Long sum_anma_num = (long) 0;
		Long sum_grte_num = (long) 0;
		Long sum_chsl_num = (long) 0;
		Long sum_cocl_num = (long) 0;
		Long sum_arel_num = (long) 0;

		RoomExpend roomExpend = null;
		if (chu != 0) {
			while (re.hasNext()) {
				roomExpend = re.next();
				sum_umbr_num += Integer.valueOf(roomExpend.getUmbr_num());
				sum_coff_num += Integer.valueOf(roomExpend.getCoff_num());
				sum_suge_num += Integer.valueOf(roomExpend.getSuge_num());
				sum_coup_num += Integer.valueOf(roomExpend.getCoup_num());
				sum_penc_num += Integer.valueOf(roomExpend.getPenc_num());
				sum_erse_num += Integer.valueOf(roomExpend.getErse_num());
				sum_clca_num += Integer.valueOf(roomExpend.getClca_num());
				sum_fati_num += Integer.valueOf(roomExpend.getFati_num());
				sum_enca_num += Integer.valueOf(roomExpend.getEnca_num());
				sum_bage_num += Integer.valueOf(roomExpend.getBage_num());
				sum_teab_num += Integer.valueOf(roomExpend.getTeab_num());
				sum_meca_num += Integer.valueOf(roomExpend.getMeca_num());
				sum_opbo_num += Integer.valueOf(roomExpend.getOpbo_num());
				sum_blte_num += Integer.valueOf(roomExpend.getBlte_num());
				sum_dnds_num += Integer.valueOf(roomExpend.getDnds_num());
				sum_tvca_num += Integer.valueOf(roomExpend.getTvca_num());
				sum_orel_num += Integer.valueOf(roomExpend.getOrel_num());
				sum_memo_num += Integer.valueOf(roomExpend.getMemo_num());
				sum_coas_num += Integer.valueOf(roomExpend.getCoas_num());
				sum_matc_num += Integer.valueOf(roomExpend.getMatc_num());
				sum_mapp_num += Integer.valueOf(roomExpend.getMapp_num());
				sum_rule_num += Integer.valueOf(roomExpend.getRule_num());
				sum_stat_num += Integer.valueOf(roomExpend.getStat_num());
				sum_clip_num += Integer.valueOf(roomExpend.getClip_num());
				sum_bape_num += Integer.valueOf(roomExpend.getBape_num());
				sum_comp_num += Integer.valueOf(roomExpend.getComp_num());
				sum_lali_num += Integer.valueOf(roomExpend.getLali_num());
				sum_losu_num += Integer.valueOf(roomExpend.getLosu_num());
				sum_shpa_num += Integer.valueOf(roomExpend.getShpa_num());
				sum_anma_num += Integer.valueOf(roomExpend.getAnma_num());
				sum_grte_num += Integer.valueOf(roomExpend.getGrte_num());
				sum_chsl_num += Integer.valueOf(roomExpend.getChsl_num());
				sum_cocl_num += Integer.valueOf(roomExpend.getCocl_num());
				sum_arel_num += Integer.valueOf(roomExpend.getArel_num());
			}
			avg.setOrderNum("平均");
			avg.setUmbr_num(String.valueOf(StringUtil.save2Float(sum_umbr_num / chu)));
			avg.setCoff_num(String.valueOf(StringUtil.save2Float(sum_coff_num / chu)));
			avg.setSuge_num(String.valueOf(StringUtil.save2Float(sum_suge_num / chu)));
			avg.setCoup_num(String.valueOf(StringUtil.save2Float(sum_coup_num / chu)));
			avg.setPenc_num(String.valueOf(StringUtil.save2Float(sum_penc_num / chu)));
			avg.setErse_num(String.valueOf(StringUtil.save2Float(sum_erse_num / chu)));
			avg.setClca_num(String.valueOf(StringUtil.save2Float(sum_clca_num / chu)));
			avg.setFati_num(String.valueOf(StringUtil.save2Float(sum_fati_num / chu)));
			avg.setEnca_num(String.valueOf(StringUtil.save2Float(sum_enca_num / chu)));
			avg.setBage_num(String.valueOf(StringUtil.save2Float(sum_bage_num / chu)));
			avg.setTeab_num(String.valueOf(StringUtil.save2Float(sum_teab_num / chu)));
			avg.setMeca_num(String.valueOf(StringUtil.save2Float(sum_meca_num / chu)));
			avg.setOpbo_num(String.valueOf(StringUtil.save2Float(sum_opbo_num / chu)));
			avg.setBlte_num(String.valueOf(StringUtil.save2Float(sum_blte_num / chu)));
			avg.setDnds_num(String.valueOf(StringUtil.save2Float(sum_dnds_num / chu)));
			avg.setTvca_num(String.valueOf(StringUtil.save2Float(sum_tvca_num / chu)));
			avg.setOrel_num(String.valueOf(StringUtil.save2Float(sum_orel_num / chu)));
			avg.setMemo_num(String.valueOf(StringUtil.save2Float(sum_memo_num / chu)));
			avg.setCoas_num(String.valueOf(StringUtil.save2Float(sum_coas_num / chu)));
			avg.setMatc_num(String.valueOf(StringUtil.save2Float(sum_matc_num / chu)));
			avg.setMapp_num(String.valueOf(StringUtil.save2Float(sum_mapp_num / chu)));
			avg.setRule_num(String.valueOf(StringUtil.save2Float(sum_rule_num / chu)));
			avg.setStat_num(String.valueOf(StringUtil.save2Float(sum_stat_num / chu)));
			avg.setClip_num(String.valueOf(StringUtil.save2Float(sum_clip_num / chu)));
			avg.setBape_num(String.valueOf(StringUtil.save2Float(sum_bape_num / chu)));
			avg.setComp_num(String.valueOf(StringUtil.save2Float(sum_comp_num / chu)));
			avg.setLali_num(String.valueOf(StringUtil.save2Float(sum_lali_num / chu)));
			avg.setLosu_num(String.valueOf(StringUtil.save2Float(sum_losu_num / chu)));
			avg.setShpa_num(String.valueOf(StringUtil.save2Float(sum_shpa_num / chu)));
			avg.setAnma_num(String.valueOf(StringUtil.save2Float(sum_anma_num / chu)));
			avg.setGrte_num(String.valueOf(StringUtil.save2Float(sum_grte_num / chu)));
			avg.setChsl_num(String.valueOf(StringUtil.save2Float(sum_chsl_num / chu)));
			avg.setCocl_num(String.valueOf(StringUtil.save2Float(sum_cocl_num / chu)));
			avg.setArel_num(String.valueOf(StringUtil.save2Float(sum_arel_num / chu)));
		}

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
	private void sortAndWrite2(List<RoomExpend> list, String room_id, boolean ascFlag, String writeField) {
		CollectionUtil.sort(list, room_id, ascFlag);
		CollectionUtil<RoomExpend> collectionUtil = new CollectionUtil<RoomExpend>();
		collectionUtil.writeSort(list, writeField);
	}

	// 卫生间耗品统计
	@Override
	public List<WashExpend> selectWashExpend(Map<String, Object> map, Pager pager) {

		List<Integer> listCondition = expendFormDao.selectCondition("卫生间易耗品");
		List<Object> listSource = expendFormDao.selectwashPage(map, pager.getOffset(), pager.getPageSize(),
				listCondition);
		
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
			washExpend.setRins_num(obj[10].toString());
			washExpend.setBafo_num(obj[11].toString());
			washExpend.setHaco_num(obj[12].toString());
			washExpend.setShge_num(obj[13].toString());
			washExpend.setCapa_num(obj[24].toString());
			washExpend.setGarb_num(obj[25].toString());
			washExpend.setPaex_num(obj[3].toString());
			washExpend.setPeep_num(obj[9].toString());
			washExpend.setShca_num(obj[6].toString());
			washExpend.setShav_num(obj[8].toString());
			washExpend.setComb_num(obj[4].toString());
			washExpend.setShcl_num(obj[5].toString());
			washExpend.setSoap_num(obj[26].toString());
			washExpend.setNacl_num(obj[7].toString());
			washExpend.setFlow_num(obj[14].toString());
			washExpend.setBasa_num(obj[15].toString());
			washExpend.setScpa_num(obj[22].toString());
			washExpend.setRugl_num(obj[23].toString());
			washExpend.setDete_num(obj[20].toString());
			washExpend.setThim_num(obj[19].toString());
			washExpend.setBacl_num(obj[18].toString());
			washExpend.setTocl_num(obj[17].toString());
			washExpend.setBabr_num(obj[16].toString());
			washExpend.setClbr_num(obj[21].toString());

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

		return listGoal;
	}

	// 卫生间耗品导出
	@Override
	public ResponseEntity<byte[]> exportWashExpendForm(Map<String, Object> map, String path, String tempPath) {
		String formName = (String) map.remove("formName");

		ResponseEntity<byte[]> byteArr = null;
		try {
			WordHelper<WashExpend> we = new WordHelper<WashExpend>();
			String fileName = "客房部" + formName + "卫生间耗品使用量统计表.docx";
			path = FileHelper.transPath(fileName, path);// 解析后的上传路径
			OutputStream out = new FileOutputStream(path);

			List<Integer> listCondition = expendFormDao.selectCondition("卫生间易耗品");
			List<Object> listSource = expendFormDao.selectwashExpend(map,listCondition);
			Iterator<Object> it = listSource.iterator();
			List<WashExpend> listGoal = objToWashExpand(it);

			WashExpend sum = sumWashExpend(listGoal);// 合计
			WashExpend avg = avgWashExpend(listGoal);// 平均
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

			we.export2007Word(tempPath, listMap, contentMap, 2, out);// 用模板生成word
			out.close();
			byteArr = FileHelper.downloadFile(fileName, path);// 提醒下载
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return byteArr;
	}

	/**
	 * 卫生间耗品list求和
	 * 
	 * @param list
	 * @return
	 */
	private WashExpend sumWashExpend(List<WashExpend> list) {
		WashExpend sum = new WashExpend();
		Iterator<WashExpend> it = list.iterator();

		Long sum_toth_num = (long) 0;
		Long sum_ropa_num = (long) 0;
		Long sum_rins_num = (long) 0;
		Long sum_bafo_num = (long) 0;
		Long sum_haco_num = (long) 0;
		Long sum_shge_num = (long) 0;
		Long sum_capa_num = (long) 0;
		Long sum_garb_num = (long) 0;
		Long sum_paex_num = (long) 0;
		Long sum_peep_num = (long) 0;
		Long sum_shca_num = (long) 0;
		Long sum_shav_num = (long) 0;
		Long sum_comb_num = (long) 0;
		Long sum_shcl_num = (long) 0;
		Long sum_soap_num = (long) 0;
		Long sum_nacl_num = (long) 0;
		Long sum_flow_num = (long) 0;
		Long sum_basa_num = (long) 0;
		Long sum_scpa_num = (long) 0;
		Long sum_rugl_num = (long) 0;
		Long sum_dete_num = (long) 0;
		Long sum_thim_num = (long) 0;
		Long sum_bacl_num = (long) 0;
		Long sum_tocl_num = (long) 0;
		Long sum_babr_num = (long) 0;
		Long sum_clbr_num = (long) 0;

		WashExpend washExpend = null;
		while (it.hasNext()) {
			washExpend = it.next();
			sum_toth_num += Integer.valueOf(washExpend.getToth_num());
			sum_ropa_num += Integer.valueOf(washExpend.getRopa_num());
			sum_rins_num += Integer.valueOf(washExpend.getRins_num());
			sum_bafo_num += Integer.valueOf(washExpend.getBafo_num());
			sum_haco_num += Integer.valueOf(washExpend.getHaco_num());
			sum_shge_num += Integer.valueOf(washExpend.getShge_num());
			sum_capa_num += Integer.valueOf(washExpend.getCapa_num());
			sum_garb_num += Integer.valueOf(washExpend.getGarb_num());
			sum_paex_num += Integer.valueOf(washExpend.getPaex_num());
			sum_peep_num += Integer.valueOf(washExpend.getPeep_num());
			sum_shca_num += Integer.valueOf(washExpend.getShca_num());
			sum_shav_num += Integer.valueOf(washExpend.getShav_num());
			sum_comb_num += Integer.valueOf(washExpend.getComb_num());
			sum_shcl_num += Integer.valueOf(washExpend.getShcl_num());
			sum_soap_num += Integer.valueOf(washExpend.getSoap_num());
			sum_nacl_num += Integer.valueOf(washExpend.getNacl_num());
			sum_flow_num += Integer.valueOf(washExpend.getFlow_num());
			sum_basa_num += Integer.valueOf(washExpend.getBasa_num());
			sum_scpa_num += Integer.valueOf(washExpend.getScpa_num());
			sum_rugl_num += Integer.valueOf(washExpend.getRugl_num());
			sum_dete_num += Integer.valueOf(washExpend.getDete_num());
			sum_thim_num += Integer.valueOf(washExpend.getThim_num());
			sum_bacl_num += Integer.valueOf(washExpend.getBacl_num());
			sum_tocl_num += Integer.valueOf(washExpend.getTocl_num());
			sum_babr_num += Integer.valueOf(washExpend.getBabr_num());
			sum_clbr_num += Integer.valueOf(washExpend.getClbr_num());
		}
		sum.setOrderNum("合计");
		sum.setToth_num(String.valueOf(sum_toth_num));
		sum.setRopa_num(String.valueOf(sum_ropa_num));
		sum.setRins_num(String.valueOf(sum_rins_num));
		sum.setBafo_num(String.valueOf(sum_bafo_num));
		sum.setHaco_num(String.valueOf(sum_haco_num));
		sum.setShge_num(String.valueOf(sum_shge_num));
		sum.setCapa_num(String.valueOf(sum_capa_num));
		sum.setGarb_num(String.valueOf(sum_garb_num));
		sum.setPaex_num(String.valueOf(sum_paex_num));
		sum.setPeep_num(String.valueOf(sum_peep_num));
		sum.setShca_num(String.valueOf(sum_shca_num));
		sum.setShav_num(String.valueOf(sum_shav_num));
		sum.setComb_num(String.valueOf(sum_comb_num));
		sum.setShcl_num(String.valueOf(sum_shcl_num));
		sum.setSoap_num(String.valueOf(sum_soap_num));
		sum.setNacl_num(String.valueOf(sum_nacl_num));
		sum.setFlow_num(String.valueOf(sum_flow_num));
		sum.setBasa_num(String.valueOf(sum_basa_num));
		sum.setScpa_num(String.valueOf(sum_scpa_num));
		sum.setRugl_num(String.valueOf(sum_rugl_num));
		sum.setDete_num(String.valueOf(sum_dete_num));
		sum.setThim_num(String.valueOf(sum_thim_num));
		sum.setBacl_num(String.valueOf(sum_bacl_num));
		sum.setTocl_num(String.valueOf(sum_tocl_num));
		sum.setBabr_num(String.valueOf(sum_babr_num));
		sum.setClbr_num(String.valueOf(sum_clbr_num));

		return sum;
	}

	/**
	 * 卫生间耗品list求平均
	 * 
	 * @param list
	 * @return
	 */
	private WashExpend avgWashExpend(List<WashExpend> list) {

		WashExpend avg = new WashExpend();
		Iterator<WashExpend> it = list.iterator();
		Float chu = Float.valueOf(list.size());

		Long sum_toth_num = (long) 0;
		Long sum_ropa_num = (long) 0;
		Long sum_rins_num = (long) 0;
		Long sum_bafo_num = (long) 0;
		Long sum_haco_num = (long) 0;
		Long sum_shge_num = (long) 0;
		Long sum_capa_num = (long) 0;
		Long sum_garb_num = (long) 0;
		Long sum_paex_num = (long) 0;
		Long sum_peep_num = (long) 0;
		Long sum_shca_num = (long) 0;
		Long sum_shav_num = (long) 0;
		Long sum_comb_num = (long) 0;
		Long sum_shcl_num = (long) 0;
		Long sum_soap_num = (long) 0;
		Long sum_nacl_num = (long) 0;
		Long sum_flow_num = (long) 0;
		Long sum_basa_num = (long) 0;
		Long sum_scpa_num = (long) 0;
		Long sum_rugl_num = (long) 0;
		Long sum_dete_num = (long) 0;
		Long sum_thim_num = (long) 0;
		Long sum_bacl_num = (long) 0;
		Long sum_tocl_num = (long) 0;
		Long sum_babr_num = (long) 0;
		Long sum_clbr_num = (long) 0;

		WashExpend washExpend = null;
		if (chu != 0) {
			while (it.hasNext()) {
				washExpend = it.next();
				sum_toth_num += Integer.valueOf(washExpend.getToth_num());
				sum_ropa_num += Integer.valueOf(washExpend.getRopa_num());
				sum_rins_num += Integer.valueOf(washExpend.getRins_num());
				sum_bafo_num += Integer.valueOf(washExpend.getBafo_num());
				sum_haco_num += Integer.valueOf(washExpend.getHaco_num());
				sum_shge_num += Integer.valueOf(washExpend.getShge_num());
				sum_capa_num += Integer.valueOf(washExpend.getCapa_num());
				sum_garb_num += Integer.valueOf(washExpend.getGarb_num());
				sum_paex_num += Integer.valueOf(washExpend.getPaex_num());
				sum_peep_num += Integer.valueOf(washExpend.getPeep_num());
				sum_shca_num += Integer.valueOf(washExpend.getShca_num());
				sum_shav_num += Integer.valueOf(washExpend.getShav_num());
				sum_comb_num += Integer.valueOf(washExpend.getComb_num());
				sum_shcl_num += Integer.valueOf(washExpend.getShcl_num());
				sum_soap_num += Integer.valueOf(washExpend.getSoap_num());
				sum_nacl_num += Integer.valueOf(washExpend.getNacl_num());
				sum_flow_num += Integer.valueOf(washExpend.getFlow_num());
				sum_basa_num += Integer.valueOf(washExpend.getBasa_num());
				sum_scpa_num += Integer.valueOf(washExpend.getScpa_num());
				sum_rugl_num += Integer.valueOf(washExpend.getRugl_num());
				sum_dete_num += Integer.valueOf(washExpend.getDete_num());
				sum_thim_num += Integer.valueOf(washExpend.getThim_num());
				sum_bacl_num += Integer.valueOf(washExpend.getBacl_num());
				sum_tocl_num += Integer.valueOf(washExpend.getTocl_num());
				sum_babr_num += Integer.valueOf(washExpend.getBabr_num());
				sum_clbr_num += Integer.valueOf(washExpend.getClbr_num());
			}
			avg.setOrderNum("平均");
			avg.setToth_num(String.valueOf(StringUtil.save2Float(sum_toth_num / chu)));
			avg.setRopa_num(String.valueOf(StringUtil.save2Float(sum_ropa_num / chu)));
			avg.setRins_num(String.valueOf(StringUtil.save2Float(sum_rins_num / chu)));
			avg.setBafo_num(String.valueOf(StringUtil.save2Float(sum_bafo_num / chu)));
			avg.setHaco_num(String.valueOf(StringUtil.save2Float(sum_haco_num / chu)));
			avg.setShge_num(String.valueOf(StringUtil.save2Float(sum_shge_num / chu)));
			avg.setCapa_num(String.valueOf(StringUtil.save2Float(sum_capa_num / chu)));
			avg.setGarb_num(String.valueOf(StringUtil.save2Float(sum_garb_num / chu)));
			avg.setPaex_num(String.valueOf(StringUtil.save2Float(sum_paex_num / chu)));
			avg.setPeep_num(String.valueOf(StringUtil.save2Float(sum_peep_num / chu)));
			avg.setShca_num(String.valueOf(StringUtil.save2Float(sum_shca_num / chu)));
			avg.setShav_num(String.valueOf(StringUtil.save2Float(sum_shav_num / chu)));
			avg.setComb_num(String.valueOf(StringUtil.save2Float(sum_comb_num / chu)));
			avg.setShcl_num(String.valueOf(StringUtil.save2Float(sum_shcl_num / chu)));
			avg.setSoap_num(String.valueOf(StringUtil.save2Float(sum_soap_num / chu)));
			avg.setNacl_num(String.valueOf(StringUtil.save2Float(sum_nacl_num / chu)));
			avg.setFlow_num(String.valueOf(StringUtil.save2Float(sum_flow_num / chu)));
			avg.setBasa_num(String.valueOf(StringUtil.save2Float(sum_basa_num / chu)));
			avg.setScpa_num(String.valueOf(StringUtil.save2Float(sum_scpa_num / chu)));
			avg.setRugl_num(String.valueOf(StringUtil.save2Float(sum_rugl_num / chu)));
			avg.setDete_num(String.valueOf(StringUtil.save2Float(sum_dete_num / chu)));
			avg.setThim_num(String.valueOf(StringUtil.save2Float(sum_thim_num / chu)));
			avg.setBacl_num(String.valueOf(StringUtil.save2Float(sum_bacl_num / chu)));
			avg.setTocl_num(String.valueOf(StringUtil.save2Float(sum_tocl_num / chu)));
			avg.setBabr_num(String.valueOf(StringUtil.save2Float(sum_babr_num / chu)));
			avg.setClbr_num(String.valueOf(StringUtil.save2Float(sum_clbr_num / chu)));
		}
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
	private void sortAndWrite3(List<WashExpend> list, String room_id, boolean ascFlag, String writeField) {
		CollectionUtil.sort(list, room_id, ascFlag);
		CollectionUtil<WashExpend> collectionUtil = new CollectionUtil<WashExpend>();
		collectionUtil.writeSort(list, writeField);
	}

	// 布草统计分析
	@Override
	public List<ExpendAnalyse> selectLinenExpendAnalyse(Map<String, Object> map) {

		List<Object> listSource = expendFormDao.selectLinenExpendAnalyse(map);
		Iterator<Object> it = listSource.iterator();
		List<ExpendAnalyse> listGoal = objToExpandAnalyse(it);

		return listGoal;
	}

	// 房间耗品统计分析
	@Override
	public List<ExpendAnalyse> selectRoomExpendAnalyse(Map<String, Object> map) {

		List<Object> listSource = expendFormDao.selectRoomExpendAnalyse(map);
		Iterator<Object> it = listSource.iterator();
		List<ExpendAnalyse> listGoal = objToExpandAnalyse(it);

		return listGoal;
	}

	// 卫生间耗品统计分析
	@Override
	public List<ExpendAnalyse> selectWashExpendAnalyse(Map<String, Object> map) {

		List<Object> listSource = expendFormDao.selectWashExpendAnalyse(map);
		Iterator<Object> it = listSource.iterator();
		List<ExpendAnalyse> listGoal = objToExpandAnalyse(it);

		return listGoal;
	}
	
	// 迷你吧统计分析
	@Override
	public List<ExpendAnalyse> selectMiniExpendAnalyse(Map<String, Object> map) {

		List<Object> listSource = expendFormDao.selectMiniExpendAnalyse(map);
		Iterator<Object> it = listSource.iterator();
		List<ExpendAnalyse> listGoal = objToExpandAnalyse(it);

		return listGoal;
	}

	// 查询布草总条数
	@Override
	public Long countlinenTotal(Map<String, Object> map) {
		return expendFormDao.countlinenTotal(map);
	}

	/********** zjn添加 **********/
	// 导出房间或卫生间耗品分析图
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ResponseEntity<byte[]> exportRoomOrWashExpendPic(Map<String, String> map) {
		ResponseEntity<byte[]> byteArr = null;
		WordHelper wh = new WordHelper();
		Map<String, Object> contentMap = new HashMap<String, Object>();
		Map<String, Object> picMap = new HashMap<String, Object>();
		String fileName = "";
		String path = map.get("path");
		String modelPath = map.get("modelPath");
		String picCataPath = map.get("picCataPath");
		String svg = map.get("svg");
		String startTime = map.get("startTime");
		String endTime = map.get("endTime");
		String expendType = map.get("expendType");
		String picName = "pic.png";
		if (expendType.equals("0")) {
			fileName = "房间耗品用量分析图.docx";
		} else {
			fileName = "卫生间耗品用量分析图.docx";
		}
		path = FileHelper.transPath(fileName, path);// 解析后的上传路径
		picMap = PictureUtil.getHighPicMap(picName, picCataPath, svg);

		contentMap.put("${startTime}", startTime);
		contentMap.put("${endTime}", endTime);
		contentMap.put("${pic}", picMap);

		try {
			OutputStream out = new FileOutputStream(path);// 保存路径
			wh.export2007Word(modelPath, null, contentMap, 1, out);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		byteArr = FileHelper.downloadFile(fileName, path);
		return byteArr;
	}

	// 导出布草用量分析图
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ResponseEntity<byte[]> exportLinenExpendPic(Map<String, String> map) {
		ResponseEntity<byte[]> byteArr = null;
		WordHelper wh = new WordHelper();
		Map<String, Object> contentMap = new HashMap<String, Object>();
		Map<String, Object> picMap = new HashMap<String, Object>();
		String fileName = "客房部布草用量分析图.docx";
		String path = map.get("path");
		String modelPath = map.get("modelPath");
		String picCataPath = map.get("picCataPath");
		String startTime = map.get("startTime");
		String endTime = map.get("endTime");
		path = FileHelper.transPath(fileName, path);// 解析后的上传路径
		// 图片相关
		String[] svgs = new String[2];
		svgs[0] = (String) map.get("svg2");
		svgs[1] = (String) map.get("svg1");
		String[] picNames = new String[2];

		for (int i = 0; i < 2; i++) {
			if (StringUtil.strIsNotEmpty(svgs[i])) {
				picNames[i] = "pic" + i + ".png";
				if (i == 0) {
					picMap = PictureUtil.getHighPicMap(picNames[i], picCataPath, svgs[i]);
				} else {
					picMap = PictureUtil.getSquarePicMap(picNames[i], picCataPath, svgs[i]);
				}
				contentMap.put("${pic" + i + "}", picMap);
			}
		}
		contentMap.put("${startTime}", startTime);
		contentMap.put("${endTime}", endTime);
		try {
			OutputStream out = new FileOutputStream(path);// 保存路径
			wh.export2007Word(modelPath, null, contentMap, 1, out);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		byteArr = FileHelper.downloadFile(fileName, path);
		return byteArr;
	}
		
	// 查询房间耗品总条数
	@Override
	public Long countroomTotal(Map<String, Object> map) {
		return expendFormDao.countroomTotal(map);
	}

	// 查询卫生间耗品总条数
	@Override
	public Long countwashTotal(Map<String, Object> map) {
		return expendFormDao.countwashTotal(map);
	}
	
	// 查询迷你吧总条数
	@Override
	public Long countminiTotal(Map<String, Object> map) {
		return expendFormDao.countminiTotal(map);
	}
	// 迷你吧统计
	@Override
	public List<MiniExpend> selectMiniPage(Map<String, Object> map, Pager pager) {

		List<Object> listSource = expendFormDao.selectminiPage(map, pager.getOffset(), pager.getPageSize());

		Iterator<Object> it = listSource.iterator();
		List<MiniExpend> listGoal = objToMiniExpand(it);

		return listGoal;
	}

	// 排序
	private List<MiniExpend> objToMiniExpand(Iterator<Object> it) {
		List<MiniExpend> listGoal = new ArrayList<MiniExpend>();
		Object[] obj = null;
		MiniExpend miniExpend = null;

		while (it.hasNext()) {
			obj = (Object[]) it.next();
			miniExpend = new MiniExpend();
			miniExpend.setRoom_id(obj[0].toString());
			miniExpend.setRedb_num(obj[1].toString());
			miniExpend.setCoco_num(obj[2].toString());
			miniExpend.setPari_num(obj[3].toString());
			miniExpend.setBige_num(obj[4].toString());
			miniExpend.setJdba_num(obj[5].toString());
			miniExpend.setTine_num(obj[6].toString());
			miniExpend.setKunl_num(obj[7].toString());
			miniExpend.setWine_num(obj[8].toString());
			miniExpend.setBree_num(obj[9].toString());
			miniExpend.setVodk_num(obj[10].toString());
			miniExpend.setAuru_num(obj[11].toString());
			miniExpend.setQing_num(obj[12].toString());
			miniExpend.setSpri_num(obj[13].toString());
			miniExpend.setNail_num(obj[14].toString());
			miniExpend.setAbcs_num(obj[15].toString());
			miniExpend.setCard_num(obj[16].toString());
			miniExpend.setComo_num(obj[17].toString());

			listGoal.add(miniExpend);
		}
		sortAndWrite5(listGoal, "room_id", true, "orderNum");

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
	private void sortAndWrite5(List<MiniExpend> list, String room_id, boolean ascFlag, String writeField) {
		CollectionUtil.sort(list, room_id, ascFlag);
		CollectionUtil<MiniExpend> collectionUtil = new CollectionUtil<MiniExpend>();
		collectionUtil.writeSort(list, writeField);
	}
	
	// 迷你吧导出
	@Override
	public ResponseEntity<byte[]> exportMiniExpendForm(Map<String, Object> map, String path, String tempPath) {
		String formName = (String) map.remove("formName");

		ResponseEntity<byte[]> byteArr = null;
		try {
			WordHelper<MiniExpend> le = new WordHelper<MiniExpend>();
			String fileName = "客房部" + formName + "布草使用量统计表.docx";
			path = FileHelper.transPath(fileName, path);// 解析后的上传路径
			OutputStream out = new FileOutputStream(path);

			List<Object> listSource = expendFormDao.selectminiExpend(map);
			Iterator<Object> it = listSource.iterator();
			List<MiniExpend> listGoal = objToMiniExpand(it);

			MiniExpend sum = sumMiniExpend(listGoal);// 合计
			MiniExpend avg = avgMiniExpend(listGoal);// 平均
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
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return byteArr;
	}

	/**
	 * 迷你吧list求和
	 * 
	 * @param list
	 * @return
	 */
	private MiniExpend sumMiniExpend(List<MiniExpend> list) {
		MiniExpend sum = new MiniExpend();
		Iterator<MiniExpend> it = list.iterator();

		Long sum_redb_num = (long) 0;
		Long sum_coco_num = (long) 0;
		Long sum_pari_num = (long) 0;
		Long sum_bige_num = (long) 0;
		Long sum_jdba_num = (long) 0;
		Long sum_tine_num = (long) 0;
		Long sum_kunl_num = (long) 0;
		Long sum_wine_num = (long) 0;
		Long sum_bree_num = (long) 0;
		Long sum_vodk_num = (long) 0;
		Long sum_auru_num = (long) 0;
		Long sum_qing_num = (long) 0;
		Long sum_spri_num = (long) 0;
		Long sum_nail_num = (long) 0;
		Long sum_abcs_num = (long) 0;
		Long sum_card_num = (long) 0;
		Long sum_como_num = (long) 0;

		MiniExpend miniExpend = null;
		while (it.hasNext()) {
			miniExpend = it.next();

			sum_redb_num += Integer.valueOf(miniExpend.getRedb_num()); 
			sum_coco_num += Integer.valueOf(miniExpend.getCoco_num());
			sum_pari_num += Integer.valueOf(miniExpend.getPari_num());
			sum_bige_num += Integer.valueOf(miniExpend.getBige_num());
			sum_jdba_num += Integer.valueOf(miniExpend.getJdba_num());
			sum_tine_num += Integer.valueOf(miniExpend.getTine_num());
			sum_kunl_num += Integer.valueOf(miniExpend.getKunl_num());
			sum_wine_num += Integer.valueOf(miniExpend.getWine_num());
			sum_bree_num += Integer.valueOf(miniExpend.getBree_num());
			sum_vodk_num += Integer.valueOf(miniExpend.getVodk_num());
			sum_auru_num += Integer.valueOf(miniExpend.getAuru_num());
			sum_qing_num += Integer.valueOf(miniExpend.getQing_num());
			sum_spri_num += Integer.valueOf(miniExpend.getSpri_num());
			sum_nail_num += Integer.valueOf(miniExpend.getNail_num());
			sum_abcs_num += Integer.valueOf(miniExpend.getAbcs_num());
			sum_card_num += Integer.valueOf(miniExpend.getCard_num());
			sum_como_num += Integer.valueOf(miniExpend.getComo_num());
		}
		sum.setOrderNum("合计");
		sum.setRedb_num(String.valueOf(sum_redb_num));
		sum.setCoco_num(String.valueOf(sum_coco_num));
		sum.setPari_num(String.valueOf(sum_pari_num));
		sum.setBige_num(String.valueOf(sum_bige_num));
		sum.setJdba_num(String.valueOf(sum_jdba_num));
		sum.setTine_num(String.valueOf(sum_tine_num));
		sum.setKunl_num(String.valueOf(sum_kunl_num));
		sum.setWine_num(String.valueOf(sum_wine_num));
		sum.setBree_num(String.valueOf(sum_bree_num));
		sum.setVodk_num(String.valueOf(sum_vodk_num));
		sum.setAuru_num(String.valueOf(sum_auru_num));
		sum.setQing_num(String.valueOf(sum_qing_num));
		sum.setSpri_num(String.valueOf(sum_spri_num));
		sum.setNail_num(String.valueOf(sum_nail_num));
		sum.setAbcs_num(String.valueOf(sum_abcs_num));
		sum.setCard_num(String.valueOf(sum_card_num));
		sum.setComo_num(String.valueOf(sum_como_num));

		return sum;
	}

	/**
	 * 布草list求平均
	 * 
	 * @param list
	 * @return
	 */
	private MiniExpend avgMiniExpend(List<MiniExpend> list) {

		MiniExpend avg = new MiniExpend();
		Iterator<MiniExpend> it = list.iterator();

		Long sum_redb_num = (long) 0;
		Long sum_coco_num = (long) 0;
		Long sum_pari_num = (long) 0;
		Long sum_bige_num = (long) 0;
		Long sum_jdba_num = (long) 0;
		Long sum_tine_num = (long) 0;
		Long sum_kunl_num = (long) 0;
		Long sum_wine_num = (long) 0;
		Long sum_bree_num = (long) 0;
		Long sum_vodk_num = (long) 0;
		Long sum_auru_num = (long) 0;
		Long sum_qing_num = (long) 0;
		Long sum_spri_num = (long) 0;
		Long sum_nail_num = (long) 0;
		Long sum_abcs_num = (long) 0;
		Long sum_card_num = (long) 0;
		Long sum_como_num = (long) 0;

		MiniExpend miniExpend = null;
		Float chu = Float.valueOf(list.size());
		if (chu != 0) {
			while (it.hasNext()) {
				miniExpend = it.next();
				sum_redb_num += Integer.valueOf(miniExpend.getRedb_num()); 
				sum_coco_num += Integer.valueOf(miniExpend.getCoco_num());
				sum_pari_num += Integer.valueOf(miniExpend.getPari_num());
				sum_bige_num += Integer.valueOf(miniExpend.getBige_num());
				sum_jdba_num += Integer.valueOf(miniExpend.getJdba_num());
				sum_tine_num += Integer.valueOf(miniExpend.getTine_num());
				sum_kunl_num += Integer.valueOf(miniExpend.getKunl_num());
				sum_wine_num += Integer.valueOf(miniExpend.getWine_num());
				sum_bree_num += Integer.valueOf(miniExpend.getBree_num());
				sum_vodk_num += Integer.valueOf(miniExpend.getVodk_num());
				sum_auru_num += Integer.valueOf(miniExpend.getAuru_num());
				sum_qing_num += Integer.valueOf(miniExpend.getQing_num());
				sum_spri_num += Integer.valueOf(miniExpend.getSpri_num());
				sum_nail_num += Integer.valueOf(miniExpend.getNail_num());
				sum_abcs_num += Integer.valueOf(miniExpend.getAbcs_num());
				sum_card_num += Integer.valueOf(miniExpend.getCard_num());
				sum_como_num += Integer.valueOf(miniExpend.getComo_num());
			}
			avg.setOrderNum("平均");
			avg.setRedb_num(String.valueOf(StringUtil.save2Float(sum_redb_num / chu)));
			avg.setCoco_num(String.valueOf(StringUtil.save2Float(sum_coco_num / chu)));
			avg.setPari_num(String.valueOf(StringUtil.save2Float(sum_pari_num / chu)));
			avg.setBige_num(String.valueOf(StringUtil.save2Float(sum_bige_num / chu)));
			avg.setJdba_num(String.valueOf(StringUtil.save2Float(sum_jdba_num / chu)));
			avg.setTine_num(String.valueOf(StringUtil.save2Float(sum_tine_num / chu)));
			avg.setKunl_num(String.valueOf(StringUtil.save2Float(sum_kunl_num / chu)));
			avg.setWine_num(String.valueOf(StringUtil.save2Float(sum_wine_num / chu)));
			avg.setBree_num(String.valueOf(StringUtil.save2Float(sum_bree_num / chu)));
			avg.setVodk_num(String.valueOf(StringUtil.save2Float(sum_vodk_num / chu)));
			avg.setAuru_num(String.valueOf(StringUtil.save2Float(sum_auru_num / chu)));
			avg.setQing_num(String.valueOf(StringUtil.save2Float(sum_qing_num / chu)));
			avg.setSpri_num(String.valueOf(StringUtil.save2Float(sum_spri_num / chu)));
			avg.setNail_num(String.valueOf(StringUtil.save2Float(sum_nail_num / chu)));
			avg.setAbcs_num(String.valueOf(StringUtil.save2Float(sum_abcs_num / chu)));
			avg.setCard_num(String.valueOf(StringUtil.save2Float(sum_card_num / chu)));
			avg.setComo_num(String.valueOf(StringUtil.save2Float(sum_como_num / chu)));
		}
		return avg;
	}


}
