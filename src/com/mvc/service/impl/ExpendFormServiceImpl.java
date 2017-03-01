package com.mvc.service.impl;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.mvc.entityReport.StaLinen;
import com.mvc.entityReport.StaMini;
import com.mvc.entityReport.StaRoom;
import com.mvc.entityReport.StaWash;
import com.mvc.entityReport.WashCount;
import com.mvc.entityReport.WashExpend;
import com.mvc.service.ExpendFormService;
import com.utils.CollectionUtil;
import com.utils.ExcelHelper;
import com.utils.FileHelper;
import com.utils.Pager;
import com.utils.PictureUtil;
import com.utils.StringUtil;
import com.utils.WordHelper;

import net.sf.json.JSONObject;

/**
 * 耗品相关的service层接口实现
 * 
 * @author wq
 * @date 2017年1月13日
 */
@SuppressWarnings(value={"unchecked", "rawtypes"})
@Service("expendFormServiceImpl")
public class ExpendFormServiceImpl implements ExpendFormService {

	@Autowired
	ExpendFormDao expendFormDao;

	//布草总数统计
	@Override
	public JSONObject linenTotleCount(Map<String, Object> map){
		String analyseResult = "分析结果：";
		List<Integer> listCondition = expendFormDao.selectCondition("房间布草");
		List<Object> listSource = expendFormDao.linenTotleCount(map, listCondition);
		Long totalRow = expendFormDao.countlinenTotal(map);
		
		Iterator<Object> it = listSource.iterator();
		List<LinenCount> listGoal = new ArrayList<LinenCount>();
		LinenCount listGoalCon = objToLinenCount(it);
		Iterator<Object> itt = listSource.iterator();
		LinenCount listGoalAvg = objToLinenAvg(itt, totalRow);
		listGoal.add(listGoalCon);
		listGoal.add(listGoalAvg);
		float sum_num = (float) 0.0;
		Map<String, Integer> linenmap = new HashMap<String, Integer>();
		linenmap.put("slba_num", Integer.parseInt(listGoalCon.getSum_slba()));
		linenmap.put("duto_num", Integer.parseInt(listGoalCon.getSum_duto()));
		linenmap.put("laba_num", Integer.parseInt(listGoalCon.getSum_laba()));
		linenmap.put("besh_num", Integer.parseInt(listGoalCon.getSum_besh()));
		linenmap.put("facl_num", Integer.parseInt(listGoalCon.getSum_facl()));
		linenmap.put("bato_num", Integer.parseInt(listGoalCon.getSum_bato()));
		linenmap.put("hato_num", Integer.parseInt(listGoalCon.getSum_hato()));
		linenmap.put("medo_num", Integer.parseInt(listGoalCon.getSum_medo()));
		linenmap.put("flto_num", Integer.parseInt(listGoalCon.getSum_flto()));
		linenmap.put("baro_num", Integer.parseInt(listGoalCon.getSum_baro()));
		linenmap.put("pill_num", Integer.parseInt(listGoalCon.getSum_pill()));
		linenmap.put("piin_num", Integer.parseInt(listGoalCon.getSum_piin()));
		linenmap.put("blan_num", Integer.parseInt(listGoalCon.getSum_blan()));
		linenmap.put("shop_num", Integer.parseInt(listGoalCon.getSum_shop()));
		
		linenmap = CollectionUtil.sortByValue(linenmap);
		
		Set set = linenmap.keySet();
		Iterator ittt = set.iterator();
		for(int i=0;i<linenmap.size();i++) {
            String key = (String) ittt.next();
            Integer value = linenmap.get(key);
            sum_num += value;
        }
		if(sum_num != 0){
			analyseResult += "物品领取量排名前三位：";
			ittt = set.iterator();
			for (int i=0;i<3;i++) {
				String key = (String) ittt.next();
				Integer value = linenmap.get(key);
				analyseResult += findname(key)+"，使用了"+value+"件，占该类物品消耗总数的"+StringUtil.strfloatToPer(StringUtil.save2Float(value/sum_num))+"；";
			}
		}
		else{
			analyseResult += "没有数据";
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("linenCount", listGoal);
		jsonObject.put("analyseResult", analyseResult);
		jsonObject.put("totalRow", totalRow);

		return jsonObject;
	}
	
	//房间耗品总数统计
	@Override
	public JSONObject roomTotleCount(Map<String, Object> map){
		String analyseResult = "分析结果：";
		List<Integer> listCondition = expendFormDao.selectCondition("房间易耗品");
		List<Object> listSource = expendFormDao.roomTotleCount(map, listCondition);
		Long totalRow = expendFormDao.countroomTotal(map);

		Iterator<Object> it = listSource.iterator();
		List<RoomCount> listGoal = new ArrayList<RoomCount>();
		RoomCount listGoalCon = objToRoomCount(it);
		Iterator<Object> itt = listSource.iterator();
		RoomCount listGoalAvg = objToRoomAvg(itt, totalRow);
		listGoal.add(listGoalCon);
		listGoal.add(listGoalAvg);
		
		float sum_num = (float) 0.0;
		Map<String, Integer> roommap = new HashMap<String, Integer>();
		roommap.put("umbr_num", Integer.parseInt(listGoalCon.getSum_umbr()));
		roommap.put("coff_num", Integer.parseInt(listGoalCon.getSum_coff()));
		roommap.put("suge_num", Integer.parseInt(listGoalCon.getSum_suge()));
		roommap.put("coup_num", Integer.parseInt(listGoalCon.getSum_coup()));
		roommap.put("penc_num", Integer.parseInt(listGoalCon.getSum_penc()));
		roommap.put("erse_num", Integer.parseInt(listGoalCon.getSum_erse()));
		roommap.put("clca_num", Integer.parseInt(listGoalCon.getSum_clca()));
		roommap.put("fati_num", Integer.parseInt(listGoalCon.getSum_fati()));
		roommap.put("enca_num", Integer.parseInt(listGoalCon.getSum_enca()));
		roommap.put("bage_num", Integer.parseInt(listGoalCon.getSum_bage()));
		roommap.put("teab_num", Integer.parseInt(listGoalCon.getSum_teab()));
		roommap.put("meca_num", Integer.parseInt(listGoalCon.getSum_meca()));
		roommap.put("opbo_num", Integer.parseInt(listGoalCon.getSum_opbo()));
		roommap.put("blte_num", Integer.parseInt(listGoalCon.getSum_blte()));
		roommap.put("dnds_num", Integer.parseInt(listGoalCon.getSum_dnds()));
		roommap.put("tvca_num", Integer.parseInt(listGoalCon.getSum_tvca()));
		roommap.put("orel_num", Integer.parseInt(listGoalCon.getSum_orel()));
		roommap.put("memo_num", Integer.parseInt(listGoalCon.getSum_memo()));
		roommap.put("coas_num", Integer.parseInt(listGoalCon.getSum_coas()));
		roommap.put("matc_num", Integer.parseInt(listGoalCon.getSum_matc()));
		roommap.put("mapp_num", Integer.parseInt(listGoalCon.getSum_mapp()));
		roommap.put("rule_num", Integer.parseInt(listGoalCon.getSum_rule()));
		roommap.put("stat_num", Integer.parseInt(listGoalCon.getSum_stat()));
		roommap.put("clip_num", Integer.parseInt(listGoalCon.getSum_clip()));
		roommap.put("bape_num", Integer.parseInt(listGoalCon.getSum_bape()));
		roommap.put("comp_num", Integer.parseInt(listGoalCon.getSum_comp()));
		roommap.put("lali_num", Integer.parseInt(listGoalCon.getSum_lali()));
		roommap.put("losu_num", Integer.parseInt(listGoalCon.getSum_losu()));
		roommap.put("shpa_num", Integer.parseInt(listGoalCon.getSum_shpa()));
		roommap.put("anma_num", Integer.parseInt(listGoalCon.getSum_anma()));
		roommap.put("grte_num", Integer.parseInt(listGoalCon.getSum_grte()));
		roommap.put("chsl_num", Integer.parseInt(listGoalCon.getSum_chsl()));
		roommap.put("cocl_num", Integer.parseInt(listGoalCon.getSum_cocl()));
		roommap.put("arel_num", Integer.parseInt(listGoalCon.getSum_arel()));
		
		roommap = CollectionUtil.sortByValue(roommap);
		
		Set set = roommap.keySet();
		Iterator ittt = set.iterator();
		for(int i=0;i<roommap.size();i++) {
            String key = (String) ittt.next();
            Integer value = roommap.get(key);
            sum_num += value;
        }
		if(sum_num != 0){
			analyseResult += "物品领取量排名前三位：";// 分析结果
			ittt= set.iterator();
			for (int i=0;i<3;i++) {
				String key = (String) ittt.next();
				Integer value = roommap.get(key);
				analyseResult += findname(key)+"，使用了"+value+"件，占该类物品消耗总数的"+StringUtil.strfloatToPer(StringUtil.save2Float(value/sum_num))+"；";
			}
		}
		else{
			analyseResult += "没有数据";
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("roomCount",listGoal);
		jsonObject.put("analyseResult", analyseResult);
		jsonObject.put("totalRow", totalRow);
		
		return jsonObject;
	}
		
	//卫生间耗品总数统计
	@Override
	public JSONObject washTotleCount(Map<String, Object> map){
		String analyseResult = "分析结果：";	
		List<Integer> listCondition = expendFormDao.selectCondition("卫生间易耗品");
		List<Object> listSource = expendFormDao.washTotleCount(map, listCondition);
		Long totalRow = expendFormDao.countwashTotal(map);
		
		List<WashCount> listGoal = new ArrayList<WashCount>();
		Iterator<Object> it = listSource.iterator();
		WashCount listGoalCon = objToWashCount(it);
		Iterator<Object> itt = listSource.iterator();
		WashCount listGoalAvg = objToWashAvg(itt, totalRow);
		listGoal.add(listGoalCon);
		listGoal.add(listGoalAvg);
		float sum_num = (float) 0.0;
		Map<String, Integer> washmap = new HashMap<String, Integer>();
		washmap.put("toth_num", Integer.parseInt(listGoalCon.getSum_toth()));
		washmap.put("ropa_num", Integer.parseInt(listGoalCon.getSum_ropa()));
		washmap.put("rins_num", Integer.parseInt(listGoalCon.getSum_rins()));
		washmap.put("bafo_num", Integer.parseInt(listGoalCon.getSum_bafo()));
		washmap.put("haco_num", Integer.parseInt(listGoalCon.getSum_haco()));
		washmap.put("shge_num", Integer.parseInt(listGoalCon.getSum_shge()));
		washmap.put("capa_num", Integer.parseInt(listGoalCon.getSum_capa()));
		washmap.put("garb_num", Integer.parseInt(listGoalCon.getSum_garb()));
		washmap.put("paex_num", Integer.parseInt(listGoalCon.getSum_paex()));
		washmap.put("peep_num", Integer.parseInt(listGoalCon.getSum_peep()));
		washmap.put("shca_num", Integer.parseInt(listGoalCon.getSum_shca()));
		washmap.put("shav_num", Integer.parseInt(listGoalCon.getSum_shav()));
		washmap.put("comb_num", Integer.parseInt(listGoalCon.getSum_comb()));
		washmap.put("shcl_num", Integer.parseInt(listGoalCon.getSum_shcl()));
		washmap.put("soap_num", Integer.parseInt(listGoalCon.getSum_soap()));
		washmap.put("nacl_num", Integer.parseInt(listGoalCon.getSum_nacl()));
		washmap.put("flow_num", Integer.parseInt(listGoalCon.getSum_flow()));
		washmap.put("basa_num", Integer.parseInt(listGoalCon.getSum_basa()));
		washmap.put("scpa_num", Integer.parseInt(listGoalCon.getSum_scpa()));
		washmap.put("rugl_num", Integer.parseInt(listGoalCon.getSum_rugl()));
		washmap.put("dete_num", Integer.parseInt(listGoalCon.getSum_dete()));
		washmap.put("thim_num", Integer.parseInt(listGoalCon.getSum_thim()));
		washmap.put("bacl_num", Integer.parseInt(listGoalCon.getSum_bacl()));
		washmap.put("tocl_num", Integer.parseInt(listGoalCon.getSum_tocl()));
		washmap.put("babr_num", Integer.parseInt(listGoalCon.getSum_babr()));
		washmap.put("clbr_num", Integer.parseInt(listGoalCon.getSum_clbr()));
		
		washmap = CollectionUtil.sortByValue(washmap);
		
		Set set = washmap.keySet();
		Iterator ittt = set.iterator();
		for(int i=0;i<washmap.size();i++) {
            String key = (String) ittt.next();
            Integer value = washmap.get(key);
            sum_num += value;
        }
		if(sum_num != 0){
			analyseResult += "物品领取量排名前三位：";// 分析结果
			ittt = set.iterator();
			for (int i=0;i<3;i++) {
				String key = (String) ittt.next();
				Integer value = washmap.get(key);
				analyseResult += findname(key)+"，使用了"+value+"件，占该类物品消耗总数的"+StringUtil.strfloatToPer(StringUtil.save2Float(value/sum_num))+"；";
			}
		}
		else{
			analyseResult += "没有数据";
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("washCount",listGoal);
		jsonObject.put("analyseResult", analyseResult);
		jsonObject.put("totalRow", totalRow);
		
		return jsonObject;
	}
	
	//迷你吧总数统计
	@Override
	public JSONObject miniTotleCount(Map<String, Object> map){
		String analyseResult = "分析结果：";
		List<Object> listSource = expendFormDao.miniTotleCount(map);
		Long totalRow = expendFormDao.countminiTotal(map);

		List<MiniCount> listGoal = new ArrayList<MiniCount>();
		Iterator<Object> it = listSource.iterator();
		MiniCount listGoalCon = objToMiniCount(it);
		Iterator<Object> itt = listSource.iterator();
		MiniCount listGoalAvg = objToMiniAvg(itt, totalRow);
		listGoal.add(listGoalCon);
		listGoal.add(listGoalAvg);
		float sum_num = (float) 0.0;
		Map<String, Integer> minimap = new HashMap<String, Integer>();
		minimap.put("redb_num", Integer.parseInt(listGoalCon.getSum_redb()));
		minimap.put("coco_num", Integer.parseInt(listGoalCon.getSum_coco()));
		minimap.put("pari_num", Integer.parseInt(listGoalCon.getSum_pari()));
		minimap.put("bige_num", Integer.parseInt(listGoalCon.getSum_bige()));
		minimap.put("jdba_num", Integer.parseInt(listGoalCon.getSum_jdba()));
		minimap.put("tine_num", Integer.parseInt(listGoalCon.getSum_tine()));
		minimap.put("kunl_num", Integer.parseInt(listGoalCon.getSum_kunl()));
		minimap.put("wine_num", Integer.parseInt(listGoalCon.getSum_wine()));
		minimap.put("bree_num", Integer.parseInt(listGoalCon.getSum_bree()));
		minimap.put("vodk_num", Integer.parseInt(listGoalCon.getSum_vodk()));
		minimap.put("auru_num", Integer.parseInt(listGoalCon.getSum_auru()));
		minimap.put("qing_num", Integer.parseInt(listGoalCon.getSum_qing()));
		minimap.put("spri_num", Integer.parseInt(listGoalCon.getSum_spri()));
		minimap.put("nail_num", Integer.parseInt(listGoalCon.getSum_nail()));
		minimap.put("abcs_num", Integer.parseInt(listGoalCon.getSum_abcs()));
		minimap.put("card_num", Integer.parseInt(listGoalCon.getSum_card()));
		minimap.put("como_num", Integer.parseInt(listGoalCon.getSum_como()));
		
		minimap = CollectionUtil.sortByValue(minimap);
		
		Set set = minimap.keySet();
		Iterator ittt = set.iterator();
		for(int i=0;i<minimap.size();i++) {
            String key = (String) ittt.next();
            Integer value = minimap.get(key);
            sum_num += value;
        }
		if(sum_num != 0){
			analyseResult += "物品领取量排名前三位：";// 分析结果
			ittt = set.iterator();
			for (int i=0;i<3;i++) {
				String key = (String) ittt.next();
				Integer value = minimap.get(key);
				analyseResult += findname(key)+",使用了"+value+"件，占该类物品消耗总数的"+StringUtil.strfloatToPer(StringUtil.save2Float(value/sum_num))+"；";
			}
		}
		else{
			analyseResult += "没有数据";
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("miniCount",listGoal);
		jsonObject.put("analyseResult", analyseResult);
		jsonObject.put("totalRow", totalRow);

		return jsonObject;
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

		return listGoal;
	}
	
	// 布草总数排序
	private LinenCount objToLinenCount(Iterator<Object> it) {

		LinenCount linenCount = null;

		linenCount = new LinenCount();
		linenCount.setOrderNum("合计");
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
	
	// 布草总数求平均
	private LinenCount objToLinenAvg(Iterator<Object> it, Long avg) {

		LinenCount linenCount = null;
		if(avg == 0){
			linenCount = null;
		}
		else{
			linenCount = new LinenCount();
			linenCount.setOrderNum("平均");
			linenCount.setSum_slba(String.valueOf(StringUtil.save2Float(Float.parseFloat(it.next().toString())/avg)));
			linenCount.setSum_duto(String.valueOf(StringUtil.save2Float(Float.parseFloat(it.next().toString())/avg)));
			linenCount.setSum_laba(String.valueOf(StringUtil.save2Float(Float.parseFloat(it.next().toString())/avg)));
			linenCount.setSum_besh(String.valueOf(StringUtil.save2Float(Float.parseFloat(it.next().toString())/avg)));
			linenCount.setSum_facl(String.valueOf(StringUtil.save2Float(Float.parseFloat(it.next().toString())/avg)));
			linenCount.setSum_bato(String.valueOf(StringUtil.save2Float(Float.parseFloat(it.next().toString())/avg)));
			linenCount.setSum_hato(String.valueOf(StringUtil.save2Float(Float.parseFloat(it.next().toString())/avg)));
			linenCount.setSum_medo(String.valueOf(StringUtil.save2Float(Float.parseFloat(it.next().toString())/avg)));
			linenCount.setSum_flto(String.valueOf(StringUtil.save2Float(Float.parseFloat(it.next().toString())/avg)));
			linenCount.setSum_baro(String.valueOf(StringUtil.save2Float(Float.parseFloat(it.next().toString())/avg)));
			linenCount.setSum_pill(String.valueOf(StringUtil.save2Float(Float.parseFloat(it.next().toString())/avg)));
			linenCount.setSum_piin(String.valueOf(StringUtil.save2Float(Float.parseFloat(it.next().toString())/avg)));
			linenCount.setSum_blan(String.valueOf(StringUtil.save2Float(Float.parseFloat(it.next().toString())/avg)));
			linenCount.setSum_shop(String.valueOf(StringUtil.save2Float(Float.parseFloat(it.next().toString())/avg)));
		}
		return linenCount;
	}

	// 房间耗品总数排序
	private RoomCount objToRoomCount(Iterator<Object> it) {

		RoomCount roomCount = null;

		roomCount = new RoomCount();
		roomCount.setOrderNum("合计");
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

	// 房间耗品总数求平均
	private RoomCount objToRoomAvg(Iterator<Object> it, Long avg) {

		RoomCount roomCount = null;

		if(avg == 0){
			roomCount = null;
		}
		else{
			roomCount = new RoomCount();
			roomCount.setOrderNum("平均");
			roomCount.setSum_coas(String.valueOf(StringUtil.save2Float(Float.parseFloat(it.next().toString())/avg)));
			roomCount.setSum_penc(String.valueOf(StringUtil.save2Float(Float.parseFloat(it.next().toString())/avg)));
			roomCount.setSum_rule(String.valueOf(StringUtil.save2Float(Float.parseFloat(it.next().toString())/avg)));
			roomCount.setSum_erse(String.valueOf(StringUtil.save2Float(Float.parseFloat(it.next().toString())/avg)));
			roomCount.setSum_cocl(String.valueOf(StringUtil.save2Float(Float.parseFloat(it.next().toString())/avg)));
			roomCount.setSum_chsl(String.valueOf(StringUtil.save2Float(Float.parseFloat(it.next().toString())/avg)));
			roomCount.setSum_umbr(String.valueOf(StringUtil.save2Float(Float.parseFloat(it.next().toString())/avg)));
			roomCount.setSum_dnds(String.valueOf(StringUtil.save2Float(Float.parseFloat(it.next().toString())/avg)));
			roomCount.setSum_meca(String.valueOf(StringUtil.save2Float(Float.parseFloat(it.next().toString())/avg)));
			roomCount.setSum_comp(String.valueOf(StringUtil.save2Float(Float.parseFloat(it.next().toString())/avg)));
			roomCount.setSum_shpa(String.valueOf(StringUtil.save2Float(Float.parseFloat(it.next().toString())/avg)));
			roomCount.setSum_bape(String.valueOf(StringUtil.save2Float(Float.parseFloat(it.next().toString())/avg)));
			roomCount.setSum_bage(String.valueOf(StringUtil.save2Float(Float.parseFloat(it.next().toString())/avg)));
			roomCount.setSum_clip(String.valueOf(StringUtil.save2Float(Float.parseFloat(it.next().toString())/avg)));
			roomCount.setSum_orel(String.valueOf(StringUtil.save2Float(Float.parseFloat(it.next().toString())/avg)));
			roomCount.setSum_arel(String.valueOf(StringUtil.save2Float(Float.parseFloat(it.next().toString())/avg)));
			roomCount.setSum_fati(String.valueOf(StringUtil.save2Float(Float.parseFloat(it.next().toString())/avg)));
			roomCount.setSum_memo(String.valueOf(StringUtil.save2Float(Float.parseFloat(it.next().toString())/avg)));
			roomCount.setSum_stat(String.valueOf(StringUtil.save2Float(Float.parseFloat(it.next().toString())/avg)));
			roomCount.setSum_lali(String.valueOf(StringUtil.save2Float(Float.parseFloat(it.next().toString())/avg)));
			roomCount.setSum_opbo(String.valueOf(StringUtil.save2Float(Float.parseFloat(it.next().toString())/avg)));
			roomCount.setSum_mapp(String.valueOf(StringUtil.save2Float(Float.parseFloat(it.next().toString())/avg)));
			roomCount.setSum_tvca(String.valueOf(StringUtil.save2Float(Float.parseFloat(it.next().toString())/avg)));
			roomCount.setSum_clca(String.valueOf(StringUtil.save2Float(Float.parseFloat(it.next().toString())/avg)));
			roomCount.setSum_enca(String.valueOf(StringUtil.save2Float(Float.parseFloat(it.next().toString())/avg)));
			roomCount.setSum_grte(String.valueOf(StringUtil.save2Float(Float.parseFloat(it.next().toString())/avg)));
			roomCount.setSum_blte(String.valueOf(StringUtil.save2Float(Float.parseFloat(it.next().toString())/avg)));
			roomCount.setSum_suge(String.valueOf(StringUtil.save2Float(Float.parseFloat(it.next().toString())/avg)));
			roomCount.setSum_losu(String.valueOf(StringUtil.save2Float(Float.parseFloat(it.next().toString())/avg)));
			roomCount.setSum_teab(String.valueOf(StringUtil.save2Float(Float.parseFloat(it.next().toString())/avg)));
			roomCount.setSum_coff(String.valueOf(StringUtil.save2Float(Float.parseFloat(it.next().toString())/avg)));
			roomCount.setSum_coup(String.valueOf(StringUtil.save2Float(Float.parseFloat(it.next().toString())/avg)));
			roomCount.setSum_anma(String.valueOf(StringUtil.save2Float(Float.parseFloat(it.next().toString())/avg)));
			roomCount.setSum_matc(String.valueOf(StringUtil.save2Float(Float.parseFloat(it.next().toString())/avg)));
		}
		return roomCount;
	}
	
	// 卫生间耗品总数排序
	private WashCount objToWashCount(Iterator<Object> it) {
		
		WashCount washCount = null;

		washCount = new WashCount();
		washCount.setOrderNum("合计");
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

	// 卫生间耗品总数求平均
	private WashCount objToWashAvg(Iterator<Object> it, long avg) {
		
		WashCount washCount = null;
		if(avg == 0){
			washCount = null;
		}
		else{
			washCount = new WashCount();
			washCount.setOrderNum("平均");
			washCount.setSum_toth(String.valueOf(StringUtil.save2Float(Float.parseFloat(it.next().toString())/avg)));
			washCount.setSum_ropa(String.valueOf(StringUtil.save2Float(Float.parseFloat(it.next().toString())/avg)));
			washCount.setSum_paex(String.valueOf(StringUtil.save2Float(Float.parseFloat(it.next().toString())/avg)));
			washCount.setSum_comb(String.valueOf(StringUtil.save2Float(Float.parseFloat(it.next().toString())/avg)));
			washCount.setSum_shcl(String.valueOf(StringUtil.save2Float(Float.parseFloat(it.next().toString())/avg)));
			washCount.setSum_shca(String.valueOf(StringUtil.save2Float(Float.parseFloat(it.next().toString())/avg)));
			washCount.setSum_nacl(String.valueOf(StringUtil.save2Float(Float.parseFloat(it.next().toString())/avg)));
			washCount.setSum_shav(String.valueOf(StringUtil.save2Float(Float.parseFloat(it.next().toString())/avg)));
			washCount.setSum_peep(String.valueOf(StringUtil.save2Float(Float.parseFloat(it.next().toString())/avg)));
			washCount.setSum_rins(String.valueOf(StringUtil.save2Float(Float.parseFloat(it.next().toString())/avg)));
			washCount.setSum_bafo(String.valueOf(StringUtil.save2Float(Float.parseFloat(it.next().toString())/avg)));
			washCount.setSum_haco(String.valueOf(StringUtil.save2Float(Float.parseFloat(it.next().toString())/avg)));
			washCount.setSum_shge(String.valueOf(StringUtil.save2Float(Float.parseFloat(it.next().toString())/avg)));
			washCount.setSum_flow(String.valueOf(StringUtil.save2Float(Float.parseFloat(it.next().toString())/avg)));
			washCount.setSum_basa(String.valueOf(StringUtil.save2Float(Float.parseFloat(it.next().toString())/avg)));
			washCount.setSum_babr(String.valueOf(StringUtil.save2Float(Float.parseFloat(it.next().toString())/avg)));
			washCount.setSum_tocl(String.valueOf(StringUtil.save2Float(Float.parseFloat(it.next().toString())/avg)));
			washCount.setSum_bacl(String.valueOf(StringUtil.save2Float(Float.parseFloat(it.next().toString())/avg)));
			washCount.setSum_thim(String.valueOf(StringUtil.save2Float(Float.parseFloat(it.next().toString())/avg)));
			washCount.setSum_dete(String.valueOf(StringUtil.save2Float(Float.parseFloat(it.next().toString())/avg)));
			washCount.setSum_clbr(String.valueOf(StringUtil.save2Float(Float.parseFloat(it.next().toString())/avg)));
			washCount.setSum_scpa(String.valueOf(StringUtil.save2Float(Float.parseFloat(it.next().toString())/avg)));
			washCount.setSum_rugl(String.valueOf(StringUtil.save2Float(Float.parseFloat(it.next().toString())/avg)));
			washCount.setSum_capa(String.valueOf(StringUtil.save2Float(Float.parseFloat(it.next().toString())/avg)));
			washCount.setSum_garb(String.valueOf(StringUtil.save2Float(Float.parseFloat(it.next().toString())/avg)));
			washCount.setSum_soap(String.valueOf(StringUtil.save2Float(Float.parseFloat(it.next().toString())/avg)));
		}
		return washCount;
	}
	// 迷你吧总数排序
	private MiniCount objToMiniCount(Iterator<Object> it) {
			
		MiniCount miniCount = null;

		miniCount = new MiniCount();
		miniCount.setOrderNum("合计");
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

	// 迷你吧总数排序
	private MiniCount objToMiniAvg(Iterator<Object> it, Long avg) {
			
		MiniCount miniCount = null;
		if(avg == 0){
			miniCount = null;
		}
		else{
			miniCount = new MiniCount();
			miniCount.setOrderNum("平均");
			miniCount.setSum_redb(String.valueOf(StringUtil.save2Float(Float.parseFloat(it.next().toString())/avg)));
			miniCount.setSum_coco(String.valueOf(StringUtil.save2Float(Float.parseFloat(it.next().toString())/avg)));
			miniCount.setSum_pari(String.valueOf(StringUtil.save2Float(Float.parseFloat(it.next().toString())/avg)));
			miniCount.setSum_bige(String.valueOf(StringUtil.save2Float(Float.parseFloat(it.next().toString())/avg)));
			miniCount.setSum_jdba(String.valueOf(StringUtil.save2Float(Float.parseFloat(it.next().toString())/avg)));
			miniCount.setSum_tine(String.valueOf(StringUtil.save2Float(Float.parseFloat(it.next().toString())/avg)));
			miniCount.setSum_kunl(String.valueOf(StringUtil.save2Float(Float.parseFloat(it.next().toString())/avg)));
			miniCount.setSum_wine(String.valueOf(StringUtil.save2Float(Float.parseFloat(it.next().toString())/avg)));
			miniCount.setSum_bree(String.valueOf(StringUtil.save2Float(Float.parseFloat(it.next().toString())/avg)));
			miniCount.setSum_vodk(String.valueOf(StringUtil.save2Float(Float.parseFloat(it.next().toString())/avg)));
			miniCount.setSum_auru(String.valueOf(StringUtil.save2Float(Float.parseFloat(it.next().toString())/avg)));
			miniCount.setSum_qing(String.valueOf(StringUtil.save2Float(Float.parseFloat(it.next().toString())/avg)));
			miniCount.setSum_spri(String.valueOf(StringUtil.save2Float(Float.parseFloat(it.next().toString())/avg)));
			miniCount.setSum_nail(String.valueOf(StringUtil.save2Float(Float.parseFloat(it.next().toString())/avg)));
			miniCount.setSum_abcs(String.valueOf(StringUtil.save2Float(Float.parseFloat(it.next().toString())/avg)));
			miniCount.setSum_card(String.valueOf(StringUtil.save2Float(Float.parseFloat(it.next().toString())/avg)));
			miniCount.setSum_como(String.valueOf(StringUtil.save2Float(Float.parseFloat(it.next().toString())/avg)));
		}
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
			String analyseResult = "分析结果：";

			List<Integer> listCondition = expendFormDao.selectCondition("房间布草");
			List<Object> listSource = expendFormDao.selectlinenExpend(map, listCondition);
			Iterator<Object> it = listSource.iterator();
			List<LinenExpend> listGoal = objToLinenExpand(it);

			LinenExpend sum = sumLinenExpend(listGoal);// 合计
			LinenExpend avg = avgLinenExpend(listGoal);// 平均
			listGoal.add(sum);
			listGoal.add(avg);

			float sum_num = (float) 0.0;
			Map<String, Integer> linenmap = new HashMap<String, Integer>();
			linenmap.put("slba_num", Integer.parseInt(sum.getSlba_num()));
			linenmap.put("duto_num", Integer.parseInt(sum.getDuto_num()));
			linenmap.put("laba_num", Integer.parseInt(sum.getLaba_num()));
			linenmap.put("besh_num", Integer.parseInt(sum.getBesh_num()));
			linenmap.put("facl_num", Integer.parseInt(sum.getFacl_num()));
			linenmap.put("bato_num", Integer.parseInt(sum.getBato_num()));
			linenmap.put("hato_num", Integer.parseInt(sum.getHato_num()));
			linenmap.put("medo_num", Integer.parseInt(sum.getMedo_num()));
			linenmap.put("flto_num", Integer.parseInt(sum.getFlto_num()));
			linenmap.put("baro_num", Integer.parseInt(sum.getBaro_num()));
			linenmap.put("pill_num", Integer.parseInt(sum.getPill_num()));
			linenmap.put("piin_num", Integer.parseInt(sum.getPiin_num()));
			linenmap.put("blan_num", Integer.parseInt(sum.getBlan_num()));
			linenmap.put("shop_num", Integer.parseInt(sum.getShop_num()));
			
			linenmap = CollectionUtil.sortByValue(linenmap);
			
			Set set = linenmap.keySet();
			Iterator itt = set.iterator();
			for(int i=0;i<linenmap.size();i++) {
	            String key = (String) itt.next();
	            Integer value = linenmap.get(key);
	            sum_num += value;
	        }
			if(sum_num != 0){
				analyseResult += "物品领取量排名前三位：";// 分析结果
				itt = set.iterator();
				for (int i=0;i<3;i++) {
					String key = (String) itt.next();
					Integer value = linenmap.get(key);
					analyseResult += findname(key)+"，使用了"+value+"件，占该类物品消耗总数的"+StringUtil.strfloatToPer(StringUtil.save2Float(value/sum_num))+"；";
				}
			}
			else{
				analyseResult += "没有数据";
			}
			Map<String, Object> listMap = new HashMap<String, Object>();
			listMap.put("0", listGoal);// key存放该list在word中表格的索引，value存放list
			Map<String, Object> contentMap = new HashMap<String, Object>();
			String startTime = (String) map.get("startTime");
			String endTime = (String) map.get("endTime");
			contentMap.put("${formName}", formName);
			contentMap.put("${startTime}", startTime.substring(0, 10));
			contentMap.put("${endTime}", endTime.substring(0, 10));
			contentMap.put("${analyseResult}", analyseResult);
			le.export2007Word(tempPath, listMap, contentMap, 2, out,-1);// 用模板生成word
			out.close();
			byteArr = FileHelper.downloadFile(fileName, path);// 提醒下载
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return byteArr;
	}
	
	// 获取布草统计列表
	public List<LinenExpend> getLinenExpendList(Map<String, Object> map) {
		List<Integer> listCondition = expendFormDao.selectCondition("房间布草");
		List<Object> listSource = expendFormDao.selectlinenExpend(map, listCondition);

		Iterator<Object> it = listSource.iterator();
		List<LinenExpend> listGoal = objToLinenExpand(it);
		
		LinenExpend sum = sumLinenExpend(listGoal);// 合计
		LinenExpend avg = avgLinenExpend(listGoal);// 平均
		listGoal.add(sum);
		listGoal.add(avg);
		
		return listGoal;
	}
	
	// 获取房间耗品统计列表
	public List<RoomExpend> getRoomExpendList(Map<String, Object> map) {
		List<Integer> listCondition = expendFormDao.selectCondition("房间易耗品");
		List<Object> listSource = expendFormDao.selectroomExpend(map, listCondition);

		Iterator<Object> it = listSource.iterator();
		List<RoomExpend> listGoal = objToRoomExpand(it);
		
		RoomExpend sum = sumRoomExpend(listGoal);// 合计
		RoomExpend avg = avgRoomExpend(listGoal);// 平均
		listGoal.add(sum);
		listGoal.add(avg);
		
		return listGoal;
	}
	
	// 获取卫生间耗品统计列表
	public List<WashExpend> getWashExpendList(Map<String, Object> map) {
		List<Integer> listCondition = expendFormDao.selectCondition("卫生间易耗品");
		List<Object> listSource = expendFormDao.selectwashExpend(map, listCondition);

		Iterator<Object> it = listSource.iterator();
		List<WashExpend> listGoal = objToWashExpand(it);
		
		WashExpend sum = sumWashExpend(listGoal);// 合计
		WashExpend avg = avgWashExpend(listGoal);// 平均
		listGoal.add(sum);
		listGoal.add(avg);

		return listGoal;
	}
	
	// 获取迷你吧统计列表
	public List<MiniExpend> getMiniExpendList(Map<String, Object> map) {
		List<Object> listSource = expendFormDao.selectminiExpend(map);

		Iterator<Object> it = listSource.iterator();
		List<MiniExpend> listGoal = objToMiniExpand(it);
		
		MiniExpend sum = sumMiniExpend(listGoal);// 合计
		MiniExpend avg = avgMiniExpend(listGoal);// 平均
		listGoal.add(sum);
		listGoal.add(avg);

		return listGoal;
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
		do{
			obj = (Object[]) it.next();
			expendAnalyse = new ExpendAnalyse();
			expendAnalyse.setGoods_name(obj[0].toString());
			expendAnalyse.setGoods_num(obj[1].toString());

			listGoal.add(expendAnalyse);
		}while (it.hasNext());

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
			i++;// 注意：若写序号放在第一个循环中，根据orderNumstaExpend后存在问题：2在10后面
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
			String analyseResult = "分析结果：";

			List<Integer> listCondition = expendFormDao.selectCondition("房间易耗品");
			List<Object> listSource = expendFormDao.selectroomExpend(map, listCondition);
			Iterator<Object> it = listSource.iterator();
			List<RoomExpend> listGoal = objToRoomExpand(it);

			RoomExpend sum = sumRoomExpend(listGoal);// 合计
			RoomExpend avg = avgRoomExpend(listGoal);// 平均
			listGoal.add(sum);
			listGoal.add(avg);

			float sum_num = (float) 0.0;
			Map<String, Integer> roommap = new HashMap<String, Integer>();
			roommap.put("umbr_num", Integer.parseInt(sum.getUmbr_num()));
			roommap.put("coff_num", Integer.parseInt(sum.getCoff_num()));
			roommap.put("suge_num", Integer.parseInt(sum.getSuge_num()));
			roommap.put("coup_num", Integer.parseInt(sum.getCoup_num()));
			roommap.put("penc_num", Integer.parseInt(sum.getPenc_num()));
			roommap.put("erse_num", Integer.parseInt(sum.getErse_num()));
			roommap.put("clca_num", Integer.parseInt(sum.getClca_num()));
			roommap.put("fati_num", Integer.parseInt(sum.getFati_num()));
			roommap.put("enca_num", Integer.parseInt(sum.getEnca_num()));
			roommap.put("bage_num", Integer.parseInt(sum.getBage_num()));
			roommap.put("teab_num", Integer.parseInt(sum.getTeab_num()));
			roommap.put("meca_num", Integer.parseInt(sum.getMeca_num()));
			roommap.put("opbo_num", Integer.parseInt(sum.getOpbo_num()));
			roommap.put("blte_num", Integer.parseInt(sum.getBlte_num()));
			roommap.put("dnds_num", Integer.parseInt(sum.getDnds_num()));
			roommap.put("tvca_num", Integer.parseInt(sum.getTvca_num()));
			roommap.put("orel_num", Integer.parseInt(sum.getOrel_num()));
			roommap.put("memo_num", Integer.parseInt(sum.getMemo_num()));
			roommap.put("coas_num", Integer.parseInt(sum.getCoas_num()));
			roommap.put("matc_num", Integer.parseInt(sum.getMatc_num()));
			roommap.put("mapp_num", Integer.parseInt(sum.getMapp_num()));
			roommap.put("rule_num", Integer.parseInt(sum.getRule_num()));
			roommap.put("stat_num", Integer.parseInt(sum.getStat_num()));
			roommap.put("clip_num", Integer.parseInt(sum.getClip_num()));
			roommap.put("bape_num", Integer.parseInt(sum.getBape_num()));
			roommap.put("comp_num", Integer.parseInt(sum.getComp_num()));
			roommap.put("lali_num", Integer.parseInt(sum.getLali_num()));
			roommap.put("losu_num", Integer.parseInt(sum.getLosu_num()));
			roommap.put("shpa_num", Integer.parseInt(sum.getShpa_num()));
			roommap.put("anma_num", Integer.parseInt(sum.getAnma_num()));
			roommap.put("grte_num", Integer.parseInt(sum.getGrte_num()));
			roommap.put("chsl_num", Integer.parseInt(sum.getChsl_num()));
			roommap.put("cocl_num", Integer.parseInt(sum.getCocl_num()));
			roommap.put("arel_num", Integer.parseInt(sum.getArel_num()));
			
			roommap = CollectionUtil.sortByValue(roommap);
			
			Set set = roommap.keySet();
			Iterator itt = set.iterator();
			for(int i=0;i<roommap.size();i++) {
	            String key = (String) itt.next();
	            Integer value = roommap.get(key);
	            sum_num += value;
	        }
			if(sum_num != 0){
				analyseResult += "物品领取量排名前三位：";// 分析结果
				itt = set.iterator();
				for (int i=0;i<3;i++) {
					String key = (String) itt.next();
					Integer value = roommap.get(key);
					analyseResult += findname(key)+"，使用了"+value+"件，占该类物品消耗总数的"+StringUtil.strfloatToPer(StringUtil.save2Float(value/sum_num))+"；";
				}
			}
			else{
				analyseResult += "没有数据";
			}
			Map<String, Object> listMap = new HashMap<String, Object>();
			listMap.put("0", listGoal);// key存放该list在word中表格的索引，value存放list
			Map<String, Object> contentMap = new HashMap<String, Object>();
			String startTime = (String) map.get("startTime");
			String endTime = (String) map.get("endTime");
			contentMap.put("${formName}", formName);
			contentMap.put("${startTime}", startTime.substring(0, 10));
			contentMap.put("${endTime}", endTime.substring(0, 10));
			contentMap.put("${analyseResult}", analyseResult);

			we.export2007Word(tempPath, listMap, contentMap, 2, out,-1);// 用模板生成word
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
	//卫生间耗品排序
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
			String analyseResult = "分析结果：";

			List<Integer> listCondition = expendFormDao.selectCondition("卫生间易耗品");
			List<Object> listSource = expendFormDao.selectwashExpend(map,listCondition);
			Iterator<Object> it = listSource.iterator();
			List<WashExpend> listGoal = objToWashExpand(it);

			WashExpend sum = sumWashExpend(listGoal);// 合计
			WashExpend avg = avgWashExpend(listGoal);// 平均
			listGoal.add(sum);
			listGoal.add(avg);

			float sum_num = (float) 0.0;
			Map<String, Integer> washmap = new HashMap<String, Integer>();
			washmap.put("toth_num", Integer.parseInt(sum.getToth_num()));
			washmap.put("ropa_num", Integer.parseInt(sum.getRopa_num()));
			washmap.put("rins_num", Integer.parseInt(sum.getRins_num()));
			washmap.put("bafo_num", Integer.parseInt(sum.getBafo_num()));
			washmap.put("haco_num", Integer.parseInt(sum.getHaco_num()));
			washmap.put("shge_num", Integer.parseInt(sum.getShge_num()));
			washmap.put("capa_num", Integer.parseInt(sum.getCapa_num()));
			washmap.put("garb_num", Integer.parseInt(sum.getGarb_num()));
			washmap.put("paex_num", Integer.parseInt(sum.getPaex_num()));
			washmap.put("peep_num", Integer.parseInt(sum.getPeep_num()));
			washmap.put("shca_num", Integer.parseInt(sum.getShca_num()));
			washmap.put("shav_num", Integer.parseInt(sum.getShav_num()));
			washmap.put("comb_num", Integer.parseInt(sum.getComb_num()));
			washmap.put("shcl_num", Integer.parseInt(sum.getShcl_num()));
			washmap.put("soap_num", Integer.parseInt(sum.getSoap_num()));
			washmap.put("nacl_num", Integer.parseInt(sum.getNacl_num()));
			washmap.put("flow_num", Integer.parseInt(sum.getFlow_num()));
			washmap.put("basa_num", Integer.parseInt(sum.getBasa_num()));
			washmap.put("scpa_num", Integer.parseInt(sum.getScpa_num()));
			washmap.put("rugl_num", Integer.parseInt(sum.getRugl_num()));
			washmap.put("dete_num", Integer.parseInt(sum.getDete_num()));
			washmap.put("thim_num", Integer.parseInt(sum.getThim_num()));
			washmap.put("bacl_num", Integer.parseInt(sum.getBacl_num()));
			washmap.put("tocl_num", Integer.parseInt(sum.getTocl_num()));
			washmap.put("babr_num", Integer.parseInt(sum.getBabr_num()));
			washmap.put("clbr_num", Integer.parseInt(sum.getClbr_num()));
			
			washmap = CollectionUtil.sortByValue(washmap);
			
			Set set = washmap.keySet();
			Iterator itt = set.iterator();
			for(int i=0;i<washmap.size();i++) {
	            String key = (String) itt.next();
	            Integer value = washmap.get(key);
	            sum_num += value;
	        }
			if(sum_num != 0){
				analyseResult += "物品领取量排名前三位：";// 分析结果
				itt = set.iterator();
				for (int i=0;i<3;i++) {
					String key = (String) itt.next();
					Integer value = washmap.get(key);
					analyseResult += findname(key)+"，使用了"+value+"件，占该类物品消耗总数的"+StringUtil.strfloatToPer(StringUtil.save2Float(value/sum_num))+"；";
				}
			}
			else{
				analyseResult += "没有数据";
			}

			Map<String, Object> listMap = new HashMap<String, Object>();
			listMap.put("0", listGoal);// key存放该list在word中表格的索引，value存放list
			Map<String, Object> contentMap = new HashMap<String, Object>();
			String startTime = (String) map.get("startTime");
			String endTime = (String) map.get("endTime");
			contentMap.put("${formName}", formName);
			contentMap.put("${startTime}", startTime.substring(0, 10));
			contentMap.put("${endTime}", endTime.substring(0, 10));
			contentMap.put("${analyseResult}", analyseResult);

			we.export2007Word(tempPath, listMap, contentMap, 2, out,-1);// 用模板生成word
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
	public String selectLinenExpendAnalyse(Map<String, Object> map) {
		String analyseResult = "分析结果：";// 分析结果
		List<Object> listSource = expendFormDao.selectLinenExpendAnalyse(map);
		JSONObject jsonObject = new JSONObject();
		if(listSource == null || listSource.size() == 0){
			analyseResult += "没有使用布草。";
			jsonObject.put("analyseResult", analyseResult);
		}
		else{
		Iterator<Object> it = listSource.iterator();
		analyseResult += getAnalyseResult("布草", listSource);
		List<ExpendAnalyse> list = objToExpandAnalyse(it);
		
		jsonObject.put("list", list);
		jsonObject.put("analyseResult", analyseResult);
		}
		return jsonObject.toString();
	}

	// 房间耗品统计分析
	@Override
	public String selectRoomExpendAnalyse(Map<String, Object> map) {

		String analyseResult = "分析结果：";// 分析结果
		List<Object> listSource = expendFormDao.selectRoomExpendAnalyse(map);
		JSONObject jsonObject = new JSONObject();
		Iterator<Object> it = listSource.iterator();
		if(listSource == null || listSource.size() == 0){
			analyseResult += "没有使用房间耗品。";
			jsonObject.put("analyseResult", analyseResult);
		}
		else{
		analyseResult += getAnalyseResult("房间易耗品", listSource);
		List<ExpendAnalyse> list = objToExpandAnalyse(it);

		jsonObject.put("list", list);
		jsonObject.put("analyseResult", analyseResult);
		}
		return jsonObject.toString();
	}

	// 卫生间耗品统计分析
	@Override
	public String selectWashExpendAnalyse(Map<String, Object> map) {

		String analyseResult = "分析结果：";// 分析结果
		List<Object> listSource = expendFormDao.selectWashExpendAnalyse(map);
		JSONObject jsonObject = new JSONObject();
		if(listSource == null || listSource.size() == 0){
			analyseResult += "没有使用卫生间耗品。";
			jsonObject.put("analyseResult", analyseResult);
		}
		else{
		Iterator<Object> it = listSource.iterator();
		analyseResult += getAnalyseResult("卫生间易耗品", listSource);
		List<ExpendAnalyse> list = objToExpandAnalyse(it);

		jsonObject.put("list", list);
		jsonObject.put("analyseResult", analyseResult);
		}
		return jsonObject.toString();
	}
	
	// 迷你吧统计分析
	@Override
	public String selectMiniExpendAnalyse(Map<String, Object> map) {

		String analyseResult = "分析结果：";// 分析结果
		List<Object> listSource = expendFormDao.selectMiniExpendAnalyse(map);
		JSONObject jsonObject = new JSONObject();
		if(listSource == null || listSource.size() == 0){
			analyseResult += "没有使用迷你吧。";
			jsonObject.put("analyseResult", analyseResult);
		}
		else{
		Iterator<Object> it = listSource.iterator();
		analyseResult += getAnalyseResult("迷你吧", listSource);
		List<ExpendAnalyse> list = objToExpandAnalyse(it);

		jsonObject.put("list", list);
		jsonObject.put("analyseResult", analyseResult);
		}
		return jsonObject.toString();
	}
	
	// 获取分析结果
	public String getAnalyseResult(String arg,List<Object> listSource){
		String analyseResult = "";
		Object[] obj = null;
			switch(arg){
				case "布草":
					Iterator<Object> it = listSource.iterator();
					obj = (Object[]) it.next();
					analyseResult += "在查询时间段内使用量最多的物品为：" + (obj[0].toString());
					analyseResult += "，使用了" + (obj[1].toString()) + "件，";
					analyseResult += "应加大该物品的供应量。";
					break;
				case "房间易耗品":
					Iterator<Object> it1 = listSource.iterator();
					obj = (Object[]) it1.next();
					analyseResult += "在查询时间段内使用量最多的物品为：" + (obj[0].toString());
					analyseResult += "，使用了" + (obj[1].toString()) + "件，";
					analyseResult += "应加大该物品的供应量。";
					break;
				case "卫生间易耗品":
					Iterator<Object> it2 = listSource.iterator();
					obj = (Object[]) it2.next();
					analyseResult += "在查询时间段内使用量最多的物品为：" + (obj[0].toString());
					analyseResult += "，使用了" + (obj[1].toString()) + "件，";
					analyseResult += "应加大该物品的供应量。";
					break;
				case "迷你吧":
					Iterator<Object> it3 = listSource.iterator();
					obj = (Object[]) it3.next();
					analyseResult += "在查询时间段内使用量最多的物品为：" + (obj[0].toString());
					analyseResult += "，使用了" + (obj[1].toString()) + "件，";
					analyseResult += "应加大该物品的供应量。";
					break;
			
		}
		return analyseResult;
	}

	// 查询布草总条数
	@Override
	public Long countlinenTotal(Map<String, Object> map) {
		return expendFormDao.countlinenTotal(map);
	}

	/********** zjn添加 **********/
	// 导出房间或卫生间耗品分析图
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
		String tableType = map.get("tableType");
		String analyseResult = map.get("analyseResult");
		String picName = "pic.png";
		if (tableType.equals("1")) {
			fileName = "房间耗品用量分析图.docx";
		} else {
			fileName = "卫生间耗品用量分析图.docx";
		}
		path = FileHelper.transPath(fileName, path);// 解析后的上传路径
		picMap = PictureUtil.getHighPicMap(picName, picCataPath, svg);

		contentMap.put("${startTime}", startTime);
		contentMap.put("${endTime}", endTime);
		contentMap.put("${analyseResult}", analyseResult);
		contentMap.put("${pic}", picMap);

		try {
			OutputStream out = new FileOutputStream(path);// 保存路径
			wh.export2007Word(modelPath, null, contentMap, 1, out,-1);
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
	@Override
	public ResponseEntity<byte[]> exportLinenOrMiniExpendPic(Map<String, String> map) {
		ResponseEntity<byte[]> byteArr = null;
		WordHelper wh = new WordHelper();
		Map<String, Object> contentMap = new HashMap<String, Object>();
		Map<String, Object> picMap = new HashMap<String, Object>();
		String fileName = "";
		String path = map.get("path");
		String modelPath = map.get("modelPath");
		String picCataPath = map.get("picCataPath");
		String startTime = map.get("startTime");
		String endTime = map.get("endTime");
		String tableType = map.get("tableType");
		String analyseResult = map.get("analyseResult");
		if (tableType.equals("0")) {
			fileName = "布草用量分析图.docx";
		} else {
			fileName = "迷你吧用量分析图.docx";
		}
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
		contentMap.put("${analyseResult}", analyseResult);
		try {
			OutputStream out = new FileOutputStream(path);// 保存路径
			wh.export2007Word(modelPath, null, contentMap, 2, out,-1);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		byteArr = FileHelper.downloadFile(fileName, path);
		return byteArr;
	}
	/********** zjn结束 **********/

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
	
	// 查询员工领取耗品总条数
	@Override
	public Long countStaTotal(Map<String, Object> map) {
		return expendFormDao.countStaTotal(map);
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
			String analyseResult = "分析结果：";

			List<Object> listSource = expendFormDao.selectminiExpend(map);
			Iterator<Object> it = listSource.iterator();
			List<MiniExpend> listGoal = objToMiniExpand(it);

			MiniExpend sum = sumMiniExpend(listGoal);// 合计
			MiniExpend avg = avgMiniExpend(listGoal);// 平均
			listGoal.add(sum);
			listGoal.add(avg);
			
			float sum_num = (float) 0.0;
			Map<String, Integer> minimap = new HashMap<String, Integer>();
			minimap.put("redb_num", Integer.parseInt(sum.getRedb_num()));
			minimap.put("coco_num", Integer.parseInt(sum.getCoco_num()));
			minimap.put("pari_num", Integer.parseInt(sum.getPari_num()));
			minimap.put("bige_num", Integer.parseInt(sum.getBige_num()));
			minimap.put("jdba_num", Integer.parseInt(sum.getJdba_num()));
			minimap.put("tine_num", Integer.parseInt(sum.getTine_num()));
			minimap.put("kunl_num", Integer.parseInt(sum.getKunl_num()));
			minimap.put("wine_num", Integer.parseInt(sum.getWine_num()));
			minimap.put("bree_num", Integer.parseInt(sum.getBree_num()));
			minimap.put("vodk_num", Integer.parseInt(sum.getVodk_num()));
			minimap.put("auru_num", Integer.parseInt(sum.getAuru_num()));
			minimap.put("qing_num", Integer.parseInt(sum.getQing_num()));
			minimap.put("spri_num", Integer.parseInt(sum.getSpri_num()));
			minimap.put("nail_num", Integer.parseInt(sum.getNail_num()));
			minimap.put("abcs_num", Integer.parseInt(sum.getAbcs_num()));
			minimap.put("card_num", Integer.parseInt(sum.getCard_num()));
			minimap.put("como_num", Integer.parseInt(sum.getComo_num()));
			
			minimap = CollectionUtil.sortByValue(minimap);
			
			Set set = minimap.keySet();
			Iterator itt = set.iterator();
			for(int i=0;i<minimap.size();i++) {
	            String key = (String) itt.next();
	            Integer value = minimap.get(key);
	            sum_num += value;
	        }
			if(sum_num != 0){
				analyseResult += "物品领取量排名前三位：";// 分析结果
				itt = set.iterator();
				for (int i=0;i<3;i++) {
					String key = (String) itt.next();
					Integer value = minimap.get(key);
					analyseResult += findname(key)+",使用了"+value+"件，占该类物品消耗总数的"+StringUtil.strfloatToPer(StringUtil.save2Float(value/sum_num))+"；";
				}
			}
			else{
				analyseResult += "没有数据";
			}
			Map<String, Object> listMap = new HashMap<String, Object>();
			listMap.put("0", listGoal);// key存放该list在word中表格的索引，value存放list
			Map<String, Object> contentMap = new HashMap<String, Object>();
			String startTime = (String) map.get("startTime");
			String endTime = (String) map.get("endTime");
			contentMap.put("${formName}", formName);
			contentMap.put("${startTime}", startTime.substring(0, 10));
			contentMap.put("${endTime}", endTime.substring(0, 10));
			contentMap.put("${analyseResult}", analyseResult);

			le.export2007Word(tempPath, listMap, contentMap, 2, out,-1);// 用模板生成word
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
	 * 迷你吧list求平均
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
	
	// 导出耗品统计表，excel格式
	@Override
	public ResponseEntity<byte[]> exportExpendExcel(Map<String, Object> map) {
		ResponseEntity<byte[]> byteArr = null;
		List<LinenExpend> linenExpendList = null;
		List<RoomExpend> roomExpendList = null;
		List<WashExpend> washExpendList = null;
		List<MiniExpend> miniExpendList = null;

		String startTime = (String) map.get("startTime");
		String endTime = (String) map.get("endTime");
		String tableType = (String) map.get("tableType");
		String path = (String) map.get("path");
		
		switch(tableType){
			case "0":
				String linenfileName = "客房部布草使用量统计表.xlsx";
				String linentitle = "(客房部布草使用量统计表" + startTime.substring(0,10) + "至 " + endTime.substring(0,10) + ")";
				try {
					ExcelHelper<LinenExpend> ex = new ExcelHelper<LinenExpend>();
					path = FileHelper.transPath(linenfileName, path);// 解析后的上传路径
					OutputStream out = new FileOutputStream(path);

					// 获取列表和文本信息
					linenExpendList = getLinenExpendList(map);

					String[] header = { "序号", "房号", "被罩", "拼尘罩", "洗衣袋", "床单", "面巾", "浴巾", "方巾","中巾" ,"地巾", "浴袍", "枕套", "枕芯", "毛毯", "购物袋" };// 顺序必须和对应实体一致
					ex.export2007Excel(linentitle, header, (Collection) linenExpendList, out, "yyyy-MM-dd",-1,-1,-1, 0, 1);// -1表示没有合并单元格,0:没有隐藏实体类

					out.close();
					byteArr = FileHelper.downloadFile(linenfileName, path);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case "1":
				String roomfileName = "客房部房间耗品使用量统计表.xlsx";
				String roomtitle = "(客房部房间耗品使用量统计表" + startTime.substring(0,10) + "至 " + endTime.substring(0,10) + ")";
				try {
					ExcelHelper<RoomExpend> ex = new ExcelHelper<RoomExpend>();
					path = FileHelper.transPath(roomfileName, path);// 解析后的上传路径
					OutputStream out = new FileOutputStream(path);

					// 获取列表和文本信息
					roomExpendList = getRoomExpendList(map);

					String[] header = {"序号","房号","雨伞","咖啡","白糖","伴侣","铅笔","橡皮","即扫牌","面巾纸","环保卡","手提袋","袋泡茶","送餐牌","意见书","立顿红茶","请勿打扰牌","电视节目单","信封(普通)",
							"便签","杯垫","火柴","地图","尺子","信纸","回形针","圆珠笔","针线包","洗衣单","低卡糖","擦鞋布","防毒面具","立顿绿茶","拖鞋(儿童)","彩色曲别针","信封(航空)"};// 顺序必须和对应实体一致
					ex.export2007Excel(roomtitle, header, (Collection) roomExpendList, out, "yyyy-MM-dd",-1,-1,-1, 0, 1);// -1表示没有合并单元格,0:没有隐藏实体类

					out.close();
					byteArr = FileHelper.downloadFile(roomfileName, path);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case "2":
				String washfileName = "客房部卫生间耗品使用量统计表.xlsx";
				String washtitle = "(客房部卫生间耗品使用量统计表" + startTime.substring(0,10) + "至 " + endTime.substring(0,10) + ")";
				try {
					ExcelHelper<WashExpend> ex = new ExcelHelper<WashExpend>();
					path = FileHelper.transPath(washfileName, path);// 解析后的上传路径
					OutputStream out = new FileOutputStream(path);

					// 获取列表和文本信息
					washExpendList = getWashExpendList(map);

					String[] header = {"序号","房号","牙具","卷纸","	洗发液","沐浴露","护发素","润肤露","护理包","黑垃圾袋","抽纸","卫生袋","浴帽","剃须刨","梳子",
							"擦鞋布","手皂","指甲锉","干花","浴盐","百洁布","橡皮手套","洗涤灵","洗消净","浴室清洁剂","洁厕灵","擦鞋布","浴缸刷","恭桶刷"};// 顺序必须和对应实体一致
					ex.export2007Excel(washtitle, header, (Collection)washExpendList, out, "yyyy-MM-dd",-1,-1,-1, 0, 1);// -1表示没有合并单元格,0:没有隐藏实体类

					out.close();
					byteArr = FileHelper.downloadFile(washfileName, path);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case "3":
				String minifileName = "客房部迷你吧使用量统计表.xlsx";
				String minititle = "(客房部迷你吧使用量统计表" + startTime.substring(0,10) + "至 " + endTime.substring(0,10) + ")";
				try {
					ExcelHelper<MiniExpend> ex = new ExcelHelper<MiniExpend>();
					path = FileHelper.transPath(minifileName, path);// 解析后的上传路径
					OutputStream out = new FileOutputStream(path);

					// 获取列表和文本信息
					miniExpendList = getMiniExpendList(map);

					String[] header = {"序号","房号","红牛","可口可乐","巴黎水","大依云","加多宝","小依云","昆仑山","红葡萄酒","威士忌","伏加特","金酒","青岛",
							"雪碧","指甲刀","ABC卫生巾","扑克牌","普通安全套"};// 顺序必须和对应实体一致
					ex.export2007Excel(minititle, header, (Collection)miniExpendList, out, "yyyy-MM-dd",-1,-1,-1, 0, 1);// -1表示没有合并单元格,0:没有隐藏实体类

					out.close();
					byteArr = FileHelper.downloadFile(minifileName, path);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
		}
		return byteArr;
	}

	//员工领取耗品统计
	@Override
	public JSONObject selectStaExpend(Map<String, Object> map) {
		
		String tableType = (String) map.get("tableType");
		JSONObject jsonObject = new JSONObject();
		String analyseResult = "分析结果：";// 分析结果
		Float sum_num = (float) 0.0;//物品使用总数
		switch(tableType){
		case "0":
			List<Integer> listCondition = expendFormDao.selectCondition("房间布草");
			List<Object> listSource = expendFormDao.selectStaExpend(map, listCondition);
			Iterator<Object> it = listSource.iterator();
			List<StaLinen> listGoal = objToLinenStaExpand(it);
			
			List<StaLinen> linenCount = new ArrayList<StaLinen>();
			StaLinen sum = sumStaLinenExpend(listGoal);// 合计
			StaLinen avg = avgStaLinenExpend(listGoal);// 平均
			linenCount.add(sum);
			linenCount.add(avg);
			jsonObject.put("list", listGoal);
			jsonObject.put("count", linenCount);
			
			Map<String, Integer> linenmap = new HashMap<String, Integer>();
			linenmap.put("slba_num", Integer.parseInt(sum.getSlba_num()));
			linenmap.put("duto_num", Integer.parseInt(sum.getDuto_num()));
			linenmap.put("laba_num", Integer.parseInt(sum.getLaba_num()));
			linenmap.put("besh_num", Integer.parseInt(sum.getBesh_num()));
			linenmap.put("facl_num", Integer.parseInt(sum.getFacl_num()));
			linenmap.put("bato_num", Integer.parseInt(sum.getBato_num()));
			linenmap.put("hato_num", Integer.parseInt(sum.getHato_num()));
			linenmap.put("medo_num", Integer.parseInt(sum.getMedo_num()));
			linenmap.put("flto_num", Integer.parseInt(sum.getFlto_num()));
			linenmap.put("baro_num", Integer.parseInt(sum.getBaro_num()));
			linenmap.put("pill_num", Integer.parseInt(sum.getPill_num()));
			linenmap.put("piin_num", Integer.parseInt(sum.getPiin_num()));
			linenmap.put("blan_num", Integer.parseInt(sum.getBlan_num()));
			linenmap.put("shop_num", Integer.parseInt(sum.getShop_num()));
			
			linenmap = CollectionUtil.sortByValue(linenmap);
			
			Set set = linenmap.keySet();
			Iterator itt = set.iterator();
			for(int i=0;i<linenmap.size();i++) {
	            String key = (String) itt.next();
	            Integer value = linenmap.get(key);
	            sum_num += value;
	        }
			if(sum_num != 0){
				analyseResult += "物品领取量排名前三位：";// 分析结果
				itt = set.iterator();
				for (int i=0;i<3;i++) {
					String key = (String) itt.next();
					Integer value = linenmap.get(key);
					analyseResult += findname(key)+"，使用了"+value+"件，占该类物品消耗总数的"+StringUtil.strfloatToPer(StringUtil.save2Float(value/sum_num))+"；";
				}
			}
			else{
				analyseResult += "没有数据";
			}
			jsonObject.put("analyseResult", analyseResult);
			break;
		case "1":
			List<Integer> listCondition1 = expendFormDao.selectCondition("房间易耗品");
			List<Object> listSource1 = expendFormDao.selectStaExpend(map, listCondition1);
			Iterator<Object> it1 = listSource1.iterator();
			List<StaRoom> listGoal1 = objToRoomStaExpand(it1);
			
			List<StaRoom> roomCount = new ArrayList<StaRoom>();
			StaRoom sum1 = sumStaRoomExpend(listGoal1);// 合计
			StaRoom avg1 = avgStaRoomExpend(listGoal1);// 平均
			roomCount.add(sum1);
			roomCount.add(avg1);
			jsonObject.put("list", listGoal1);
			jsonObject.put("count", roomCount);
			
			Map<String, Integer> roommap = new HashMap<String, Integer>();
			roommap.put("umbr_num", Integer.parseInt(sum1.getUmbr_num()));
			roommap.put("coff_num", Integer.parseInt(sum1.getCoff_num()));
			roommap.put("suge_num", Integer.parseInt(sum1.getSuge_num()));
			roommap.put("coup_num", Integer.parseInt(sum1.getCoup_num()));
			roommap.put("penc_num", Integer.parseInt(sum1.getPenc_num()));
			roommap.put("erse_num", Integer.parseInt(sum1.getErse_num()));
			roommap.put("clca_num", Integer.parseInt(sum1.getClca_num()));
			roommap.put("fati_num", Integer.parseInt(sum1.getFati_num()));
			roommap.put("enca_num", Integer.parseInt(sum1.getEnca_num()));
			roommap.put("bage_num", Integer.parseInt(sum1.getBage_num()));
			roommap.put("teab_num", Integer.parseInt(sum1.getTeab_num()));
			roommap.put("meca_num", Integer.parseInt(sum1.getMeca_num()));
			roommap.put("opbo_num", Integer.parseInt(sum1.getOpbo_num()));
			roommap.put("blte_num", Integer.parseInt(sum1.getBlte_num()));
			roommap.put("dnds_num", Integer.parseInt(sum1.getDnds_num()));
			roommap.put("tvca_num", Integer.parseInt(sum1.getTvca_num()));
			roommap.put("orel_num", Integer.parseInt(sum1.getOrel_num()));
			roommap.put("memo_num", Integer.parseInt(sum1.getMemo_num()));
			roommap.put("coas_num", Integer.parseInt(sum1.getCoas_num()));
			roommap.put("matc_num", Integer.parseInt(sum1.getMatc_num()));
			roommap.put("mapp_num", Integer.parseInt(sum1.getMapp_num()));
			roommap.put("rule_num", Integer.parseInt(sum1.getRule_num()));
			roommap.put("stat_num", Integer.parseInt(sum1.getStat_num()));
			roommap.put("clip_num", Integer.parseInt(sum1.getClip_num()));
			roommap.put("bape_num", Integer.parseInt(sum1.getBape_num()));
			roommap.put("comp_num", Integer.parseInt(sum1.getComp_num()));
			roommap.put("lali_num", Integer.parseInt(sum1.getLali_num()));
			roommap.put("losu_num", Integer.parseInt(sum1.getLosu_num()));
			roommap.put("shpa_num", Integer.parseInt(sum1.getShpa_num()));
			roommap.put("anma_num", Integer.parseInt(sum1.getAnma_num()));
			roommap.put("grte_num", Integer.parseInt(sum1.getGrte_num()));
			roommap.put("chsl_num", Integer.parseInt(sum1.getChsl_num()));
			roommap.put("cocl_num", Integer.parseInt(sum1.getCocl_num()));
			roommap.put("arel_num", Integer.parseInt(sum1.getArel_num()));
			
			roommap = CollectionUtil.sortByValue(roommap);
			
			Set set1 = roommap.keySet();
			Iterator itt1 = set1.iterator();
			for(int i=0;i<roommap.size();i++) {
	            String key = (String) itt1.next();
	            Integer value = roommap.get(key);
	            sum_num += value;
	        }
			if(sum_num != 0){
				analyseResult += "物品领取量排名前三位：";// 分析结果
				itt1 = set1.iterator();
				for (int i=0;i<3;i++) {
					String key = (String) itt1.next();
					Integer value = roommap.get(key);
					analyseResult += findname(key)+"，使用了"+value+"件，占该类物品消耗总数的"+StringUtil.strfloatToPer(StringUtil.save2Float(value/sum_num))+"；";
				}
			}
			else{
				analyseResult += "没有数据";
			}

			jsonObject.put("analyseResult", analyseResult);
			break;
		case "2":
			List<Integer> listCondition2 = expendFormDao.selectCondition("卫生间易耗品");
			List<Object> listSource2 = expendFormDao.selectStaExpend(map, listCondition2);
			Iterator<Object> it2 = listSource2.iterator();
			List<StaWash> listGoal2 = objToWashStaExpand(it2);
			
			List<StaWash> washCount = new ArrayList<StaWash>();
			StaWash sum2 = sumStaWashExpend(listGoal2);// 合计
			StaWash avg2 = avgStaWashExpend(listGoal2);// 平均
			washCount.add(sum2);
			washCount.add(avg2);
			jsonObject.put("list", listGoal2);
			jsonObject.put("count", washCount);
			
			Map<String, Integer> washmap = new HashMap<String, Integer>();
			washmap.put("toth_num", Integer.parseInt(sum2.getToth_num()));
			washmap.put("ropa_num", Integer.parseInt(sum2.getRopa_num()));
			washmap.put("rins_num", Integer.parseInt(sum2.getRins_num()));
			washmap.put("bafo_num", Integer.parseInt(sum2.getBafo_num()));
			washmap.put("haco_num", Integer.parseInt(sum2.getHaco_num()));
			washmap.put("shge_num", Integer.parseInt(sum2.getShge_num()));
			washmap.put("capa_num", Integer.parseInt(sum2.getCapa_num()));
			washmap.put("garb_num", Integer.parseInt(sum2.getGarb_num()));
			washmap.put("paex_num", Integer.parseInt(sum2.getPaex_num()));
			washmap.put("peep_num", Integer.parseInt(sum2.getPeep_num()));
			washmap.put("shca_num", Integer.parseInt(sum2.getShca_num()));
			washmap.put("shav_num", Integer.parseInt(sum2.getShav_num()));
			washmap.put("comb_num", Integer.parseInt(sum2.getComb_num()));
			washmap.put("shcl_num", Integer.parseInt(sum2.getShcl_num()));
			washmap.put("soap_num", Integer.parseInt(sum2.getSoap_num()));
			washmap.put("nacl_num", Integer.parseInt(sum2.getNacl_num()));
			washmap.put("flow_num", Integer.parseInt(sum2.getFlow_num()));
			washmap.put("basa_num", Integer.parseInt(sum2.getBasa_num()));
			washmap.put("scpa_num", Integer.parseInt(sum2.getScpa_num()));
			washmap.put("rugl_num", Integer.parseInt(sum2.getRugl_num()));
			washmap.put("dete_num", Integer.parseInt(sum2.getDete_num()));
			washmap.put("thim_num", Integer.parseInt(sum2.getThim_num()));
			washmap.put("bacl_num", Integer.parseInt(sum2.getBacl_num()));
			washmap.put("tocl_num", Integer.parseInt(sum2.getTocl_num()));
			washmap.put("babr_num", Integer.parseInt(sum2.getBabr_num()));
			washmap.put("clbr_num", Integer.parseInt(sum2.getClbr_num()));
			
			washmap = CollectionUtil.sortByValue(washmap);
			
			Set set2 = washmap.keySet();
			Iterator itt2 = set2.iterator();
			for(int i=0;i<washmap.size();i++) {
	            String key = (String) itt2.next();
	            Integer value = washmap.get(key);
	            sum_num += value;
	        }
			if(sum_num != 0){
				analyseResult += "物品领取量排名前三位：";// 分析结果
				itt2 = set2.iterator();
				for (int i=0;i<3;i++) {
					String key = (String) itt2.next();
					Integer value = washmap.get(key);
					analyseResult += findname(key)+"，使用了"+value+"件，占该类物品消耗总数的"+StringUtil.strfloatToPer(StringUtil.save2Float(value/sum_num))+"；";
					boolean ascFlag = false;
					CollectionUtil.sort(listGoal2, key, ascFlag);
					//analyseResult += ",领取该物品最多的员工为"+listGoal.get(0).getStaff_name()+";";
				}
			}
			else{
				analyseResult += "没有数据";
			}
			jsonObject.put("analyseResult", analyseResult);
			break;
		case "3":
			List<Object> listSource3 = expendFormDao.selectStaMini(map);
			Iterator<Object> it3 = listSource3.iterator();
			List<StaMini> listGoal3 = objToMiniStaExpand(it3);
			
			List<StaMini> miniCount = new ArrayList<StaMini>();
			StaMini sum3 = sumStaMiniExpend(listGoal3);// 合计
			StaMini avg3 = avgStaMiniExpend(listGoal3);// 平均
			miniCount.add(sum3);
			miniCount.add(avg3);
			jsonObject.put("list", listGoal3);
			jsonObject.put("count", miniCount);
			
			Map<String, Integer> minimap = new HashMap<String, Integer>();
			minimap.put("redb_num", Integer.parseInt(sum3.getRedb_num()));
			minimap.put("coco_num", Integer.parseInt(sum3.getCoco_num()));
			minimap.put("pari_num", Integer.parseInt(sum3.getPari_num()));
			minimap.put("bige_num", Integer.parseInt(sum3.getBige_num()));
			minimap.put("jdba_num", Integer.parseInt(sum3.getJdba_num()));
			minimap.put("tine_num", Integer.parseInt(sum3.getTine_num()));
			minimap.put("kunl_num", Integer.parseInt(sum3.getKunl_num()));
			minimap.put("wine_num", Integer.parseInt(sum3.getWine_num()));
			minimap.put("bree_num", Integer.parseInt(sum3.getBree_num()));
			minimap.put("vodk_num", Integer.parseInt(sum3.getVodk_num()));
			minimap.put("auru_num", Integer.parseInt(sum3.getAuru_num()));
			minimap.put("qing_num", Integer.parseInt(sum3.getQing_num()));
			minimap.put("spri_num", Integer.parseInt(sum3.getSpri_num()));
			minimap.put("nail_num", Integer.parseInt(sum3.getNail_num()));
			minimap.put("abcs_num", Integer.parseInt(sum3.getAbcs_num()));
			minimap.put("card_num", Integer.parseInt(sum3.getCard_num()));
			minimap.put("como_num", Integer.parseInt(sum3.getComo_num()));
			
			minimap = CollectionUtil.sortByValue(minimap);
			
			Set set3 = minimap.keySet();
			Iterator itt3 = set3.iterator();
			for(int i=0;i<minimap.size();i++) {
	            String key = (String) itt3.next();
	            Integer value = minimap.get(key);
	            sum_num += value;
	        }
			if(sum_num != 0){
				analyseResult += "物品领取量排名前三位：";// 分析结果
				itt3 = set3.iterator();
				for (int i=0;i<3;i++) {
					String key = (String) itt3.next();
					Integer value = minimap.get(key);
					analyseResult += findname(key)+",使用了"+value+"件，占该类物品消耗总数的"+StringUtil.strfloatToPer(StringUtil.save2Float(value/sum_num))+"；";
				}
			}
			else{
				analyseResult += "没有数据";
			}

			jsonObject.put("analyseResult", analyseResult);
			break;
		}
		
		return jsonObject;
	}
	
	// 排序
	private List<StaLinen> objToLinenStaExpand(Iterator<Object> it) {
		List<StaLinen> listGoal = new ArrayList<StaLinen>();
		Object[] obj = null;
		StaLinen staLinen = null;

		while (it.hasNext()) {
			obj = (Object[]) it.next();
			staLinen = new StaLinen();
			staLinen.setStaff_id(obj[0].toString());
			staLinen.setStaff_name(obj[1].toString());
			staLinen.setSlba_num(obj[2].toString());
			staLinen.setDuto_num(obj[3].toString());
			staLinen.setLaba_num(obj[4].toString());
			staLinen.setBesh_num(obj[5].toString());
			staLinen.setFacl_num(obj[6].toString());
			staLinen.setBato_num(obj[7].toString());
			staLinen.setHato_num(obj[8].toString());
			staLinen.setMedo_num(obj[9].toString());
			staLinen.setFlto_num(obj[10].toString());
			staLinen.setBaro_num(obj[11].toString());
			staLinen.setPill_num(obj[12].toString());
			staLinen.setPiin_num(obj[13].toString());
			staLinen.setBlan_num(obj[14].toString());
			staLinen.setShop_num(obj[15].toString());

			listGoal.add(staLinen);
		}
		sortAndWrite6(listGoal, "staff_id", true, "orderNum");
		return listGoal;
	}
	private void sortAndWrite6(List<StaLinen> list, String staff_id, boolean ascFlag, String writeField) {
		CollectionUtil.sort(list, staff_id, ascFlag);
		CollectionUtil<StaLinen> collectionUtil = new CollectionUtil<StaLinen>();
		collectionUtil.writeSort(list, writeField);
	}
	private List<StaRoom> objToRoomStaExpand(Iterator<Object> it) {
		List<StaRoom> listGoal = new ArrayList<StaRoom>();
		Object[] obj = null;
		StaRoom staExpend = null;
		while (it.hasNext()) {
			obj = (Object[]) it.next();
			staExpend = new StaRoom();
			staExpend.setStaff_id(obj[0].toString());
			staExpend.setStaff_name(obj[1].toString());
			staExpend.setUmbr_num(obj[8].toString());
			staExpend.setCoff_num(obj[32].toString());
			staExpend.setSuge_num(obj[29].toString());
			staExpend.setCoup_num(obj[33].toString());
			staExpend.setPenc_num(obj[3].toString());
			staExpend.setErse_num(obj[5].toString());
			staExpend.setClca_num(obj[25].toString());
			staExpend.setFati_num(obj[18].toString());
			staExpend.setEnca_num(obj[26].toString());
			staExpend.setBage_num(obj[14].toString());
			staExpend.setTeab_num(obj[31].toString());
			staExpend.setMeca_num(obj[10].toString());
			staExpend.setOpbo_num(obj[22].toString());
			staExpend.setBlte_num(obj[28].toString());
			staExpend.setDnds_num(obj[9].toString());
			staExpend.setTvca_num(obj[24].toString());
			staExpend.setOrel_num(obj[16].toString());
			staExpend.setMemo_num(obj[19].toString());
			staExpend.setCoas_num(obj[2].toString());
			staExpend.setMatc_num(obj[35].toString());
			staExpend.setMapp_num(obj[23].toString());
			staExpend.setRule_num(obj[4].toString());
			staExpend.setStat_num(obj[20].toString());
			staExpend.setClip_num(obj[15].toString());
			staExpend.setBape_num(obj[13].toString());
			staExpend.setComp_num(obj[11].toString());
			staExpend.setLali_num(obj[21].toString());
			staExpend.setLosu_num(obj[30].toString());
			staExpend.setShpa_num(obj[12].toString());
			staExpend.setAnma_num(obj[34].toString());
			staExpend.setGrte_num(obj[27].toString());
			staExpend.setChsl_num(obj[7].toString());
			staExpend.setCocl_num(obj[6].toString());
			staExpend.setArel_num(obj[17].toString());

			listGoal.add(staExpend);
		}
		sortAndWrite7(listGoal, "staff_id", true, "orderNum");
		return listGoal;
	}
	private void sortAndWrite7(List<StaRoom> list, String staff_id, boolean ascFlag, String writeField) {
		CollectionUtil.sort(list, staff_id, ascFlag);
		CollectionUtil<StaRoom> collectionUtil = new CollectionUtil<StaRoom>();
		collectionUtil.writeSort(list, writeField);
	}
	private List<StaWash> objToWashStaExpand(Iterator<Object> it) {
		List<StaWash> listGoal = new ArrayList<StaWash>();
		Object[] obj = null;
		StaWash staWash = null;
		while (it.hasNext()) {
			obj = (Object[]) it.next();
			staWash = new StaWash();
			staWash.setStaff_id(obj[0].toString());
			staWash.setStaff_name(obj[1].toString());
			staWash.setToth_num(obj[2].toString());
			staWash.setRopa_num(obj[3].toString());
			staWash.setRins_num(obj[11].toString());
			staWash.setBafo_num(obj[12].toString());
			staWash.setHaco_num(obj[13].toString());
			staWash.setShge_num(obj[14].toString());
			staWash.setCapa_num(obj[25].toString());
			staWash.setGarb_num(obj[26].toString());
			staWash.setPaex_num(obj[4].toString());
			staWash.setPeep_num(obj[10].toString());
			staWash.setShca_num(obj[7].toString());
			staWash.setShav_num(obj[9].toString());
			staWash.setComb_num(obj[5].toString());
			staWash.setShcl_num(obj[6].toString());
			staWash.setSoap_num(obj[27].toString());
			staWash.setNacl_num(obj[8].toString());
			staWash.setFlow_num(obj[15].toString());
			staWash.setBasa_num(obj[16].toString());
			staWash.setScpa_num(obj[23].toString());
			staWash.setRugl_num(obj[24].toString());
			staWash.setDete_num(obj[21].toString());
			staWash.setThim_num(obj[20].toString());
			staWash.setBacl_num(obj[19].toString());
			staWash.setTocl_num(obj[18].toString());
			staWash.setBabr_num(obj[17].toString());
			staWash.setClbr_num(obj[22].toString());

			listGoal.add(staWash);
		}
		sortAndWrite8(listGoal, "staff_id", true, "orderNum");
		return listGoal;
	}
	private void sortAndWrite8(List<StaWash> list, String staff_id, boolean ascFlag, String writeField) {
		CollectionUtil.sort(list, staff_id, ascFlag);
		CollectionUtil<StaWash> collectionUtil = new CollectionUtil<StaWash>();
		collectionUtil.writeSort(list, writeField);
	}
	private List<StaMini> objToMiniStaExpand(Iterator<Object> it) {
		List<StaMini> listGoal = new ArrayList<StaMini>();
		Object[] obj = null;
		StaMini staMini = null;

		while (it.hasNext()) {
			obj = (Object[]) it.next();
			staMini = new StaMini();
			staMini.setStaff_id(obj[0].toString());
			staMini.setStaff_name(obj[1].toString());
			staMini.setRedb_num(obj[2].toString());
			staMini.setCoco_num(obj[3].toString());
			staMini.setPari_num(obj[4].toString());
			staMini.setBige_num(obj[5].toString());
			staMini.setJdba_num(obj[6].toString());
			staMini.setTine_num(obj[7].toString());
			staMini.setKunl_num(obj[8].toString());
			staMini.setWine_num(obj[9].toString());
			staMini.setBree_num(obj[10].toString());
			staMini.setVodk_num(obj[11].toString());
			staMini.setAuru_num(obj[12].toString());
			staMini.setQing_num(obj[13].toString());
			staMini.setSpri_num(obj[14].toString());
			staMini.setNail_num(obj[15].toString());
			staMini.setAbcs_num(obj[16].toString());
			staMini.setCard_num(obj[17].toString());
			staMini.setComo_num(obj[18].toString());

			listGoal.add(staMini);
		}
		sortAndWrite9(listGoal, "staff_id", true, "orderNum");

		return listGoal;
	}
	private void sortAndWrite9(List<StaMini> list, String staff_id, boolean ascFlag, String writeField) {
		CollectionUtil.sort(list, staff_id, ascFlag);
		CollectionUtil<StaMini> collectionUtil = new CollectionUtil<StaMini>();
		collectionUtil.writeSort(list, writeField);
	}

	// 员工领取布草导出
	@Override
	public ResponseEntity<byte[]> exportStaLinen(Map<String, Object> map, String path, String tempPath) {
		ResponseEntity<byte[]> byteArr = null;
		try {
			WordHelper<StaLinen> le = new WordHelper<StaLinen>();
			String fileName = "客房部员工领取布草量统计表.docx";
			
			path = FileHelper.transPath(fileName, path);// 解析后的上传路径
			OutputStream out = new FileOutputStream(path);

			List<Integer> listCondition = expendFormDao.selectCondition("房间布草");
			List<Object> listSource = expendFormDao.selectStaExpend(map, listCondition);
			Iterator<Object> it = listSource.iterator();
			List<StaLinen> listGoal = objToLinenStaExpand(it);

			StaLinen sum = sumStaLinenExpend(listGoal);// 合计
			StaLinen avg = avgStaLinenExpend(listGoal);// 平均
			listGoal.add(sum);
			listGoal.add(avg);
			float sum_num = (float) 0.0;
			String analyseResult = "分析结果：";// 分析结果
			Map<String, Integer> linenmap = new HashMap<String, Integer>();
			linenmap.put("slba_num", Integer.parseInt(sum.getSlba_num()));
			linenmap.put("duto_num", Integer.parseInt(sum.getDuto_num()));
			linenmap.put("laba_num", Integer.parseInt(sum.getLaba_num()));
			linenmap.put("besh_num", Integer.parseInt(sum.getBesh_num()));
			linenmap.put("facl_num", Integer.parseInt(sum.getFacl_num()));
			linenmap.put("bato_num", Integer.parseInt(sum.getBato_num()));
			linenmap.put("hato_num", Integer.parseInt(sum.getHato_num()));
			linenmap.put("medo_num", Integer.parseInt(sum.getMedo_num()));
			linenmap.put("flto_num", Integer.parseInt(sum.getFlto_num()));
			linenmap.put("baro_num", Integer.parseInt(sum.getBaro_num()));
			linenmap.put("pill_num", Integer.parseInt(sum.getPill_num()));
			linenmap.put("piin_num", Integer.parseInt(sum.getPiin_num()));
			linenmap.put("blan_num", Integer.parseInt(sum.getBlan_num()));
			linenmap.put("shop_num", Integer.parseInt(sum.getShop_num()));
			
			linenmap = CollectionUtil.sortByValue(linenmap);
			
			Set set = linenmap.keySet();
			Iterator itt = set.iterator();
			for(int i=0;i<linenmap.size();i++) {
	            String key = (String) itt.next();
	            Integer value = linenmap.get(key);
	            sum_num += value;
	        }
			if(sum_num != 0){
				analyseResult += "物品领取量排名前三位：";
				itt = set.iterator();
				for (int i=0;i<3;i++) {
					String key = (String) itt.next();
					Integer value = linenmap.get(key);
					analyseResult += findname(key)+"，使用了"+value+"件，占该类物品消耗总数的"+StringUtil.strfloatToPer(StringUtil.save2Float(value/sum_num))+"；";
				}
			}
			else{
				analyseResult += "没有数据";
			}

			Map<String, Object> listMap = new HashMap<String, Object>();
			listMap.put("0", listGoal);// key存放该list在word中表格的索引，value存放list
			Map<String, Object> contentMap = new HashMap<String, Object>();
			String startTime = (String) map.get("startTime");
			String endTime = (String) map.get("endTime");
			contentMap.put("${startTime}", startTime.substring(0, 10));
			contentMap.put("${endTime}", endTime.substring(0, 10));
			contentMap.put("${analyseResult}", analyseResult);
			le.export2007Word(tempPath, listMap, contentMap, 2, out,-1);// 用模板生成word
			out.close();
			byteArr = FileHelper.downloadFile(fileName, path);// 提醒下载
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return byteArr;
	}
	/**
	 * 员工领取布草list求和
	 * 
	 * @param list
	 * @return
	 */
	private StaLinen sumStaLinenExpend(List<StaLinen> list) {
		StaLinen sum = new StaLinen();
		Iterator<StaLinen> it = list.iterator();

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
		
		StaLinen staLinen = null;
		while (it.hasNext()) {
			staLinen = it.next();
			sum_bato_num += Integer.valueOf(staLinen.getBato_num());
			sum_facl_num += Integer.valueOf(staLinen.getFacl_num());
			sum_besh_num += Integer.valueOf(staLinen.getBesh_num());
			sum_hato_num += Integer.valueOf(staLinen.getHato_num());
			sum_medo_num += Integer.valueOf(staLinen.getMedo_num());
			sum_flto_num += Integer.valueOf(staLinen.getFlto_num());
			sum_baro_num += Integer.valueOf(staLinen.getBaro_num());
			sum_slba_num += Integer.valueOf(staLinen.getSlba_num());
			sum_duto_num += Integer.valueOf(staLinen.getDuto_num());
			sum_pill_num += Integer.valueOf(staLinen.getPill_num());
			sum_shop_num += Integer.valueOf(staLinen.getShop_num());
			sum_laba_num += Integer.valueOf(staLinen.getLaba_num());
			sum_piin_num += Integer.valueOf(staLinen.getPiin_num());
			sum_blan_num += Integer.valueOf(staLinen.getBlan_num());
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
	 * 员工领取布草list求平均
	 * 
	 * @param list
	 * @return
	 */
	private StaLinen avgStaLinenExpend(List<StaLinen> list) {

		StaLinen avg = new StaLinen();
		Iterator<StaLinen> it = list.iterator();

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

		StaLinen staLinen = null;
		Float chu = Float.valueOf(list.size());
		if (chu != 0) {
			while (it.hasNext()) {
				staLinen = it.next();
				sum_bato_num += Integer.valueOf(staLinen.getBato_num());
				sum_facl_num += Integer.valueOf(staLinen.getFacl_num());
				sum_besh_num += Integer.valueOf(staLinen.getBesh_num());
				sum_hato_num += Integer.valueOf(staLinen.getHato_num());
				sum_medo_num += Integer.valueOf(staLinen.getMedo_num());
				sum_flto_num += Integer.valueOf(staLinen.getFlto_num());
				sum_baro_num += Integer.valueOf(staLinen.getBaro_num());
				sum_slba_num += Integer.valueOf(staLinen.getSlba_num());
				sum_duto_num += Integer.valueOf(staLinen.getDuto_num());
				sum_pill_num += Integer.valueOf(staLinen.getPill_num());
				sum_shop_num += Integer.valueOf(staLinen.getShop_num());
				sum_laba_num += Integer.valueOf(staLinen.getLaba_num());
				sum_piin_num += Integer.valueOf(staLinen.getPiin_num());
				sum_blan_num += Integer.valueOf(staLinen.getBlan_num());
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

	//员工领取房间耗品导出
	@Override
	public ResponseEntity<byte[]> exportStaRoom(Map<String, Object> map, String path, String tempPath) {
		ResponseEntity<byte[]> byteArr = null;
		try {
			WordHelper<StaRoom> le = new WordHelper<StaRoom>();
			String fileName = "客房部员工领取房间耗品量统计表.docx";
			
			path = FileHelper.transPath(fileName, path);// 解析后的上传路径
			OutputStream out = new FileOutputStream(path);

			List<Integer> listCondition = expendFormDao.selectCondition("房间易耗品");
			List<Object> listSource = expendFormDao.selectStaExpend(map, listCondition);
			Iterator<Object> it = listSource.iterator();
			List<StaRoom> listGoal = objToRoomStaExpand(it);

			StaRoom sum = sumStaRoomExpend(listGoal);// 合计
			StaRoom avg = avgStaRoomExpend(listGoal);// 平均
			listGoal.add(sum);
			listGoal.add(avg);
			
			float sum_num = (float) 0.0;
			String analyseResult = "分析结果：";// 分析结果
			Map<String, Integer> roommap = new HashMap<String, Integer>();
			roommap.put("umbr_num", Integer.parseInt(sum.getUmbr_num()));
			roommap.put("coff_num", Integer.parseInt(sum.getCoff_num()));
			roommap.put("suge_num", Integer.parseInt(sum.getSuge_num()));
			roommap.put("coup_num", Integer.parseInt(sum.getCoup_num()));
			roommap.put("penc_num", Integer.parseInt(sum.getPenc_num()));
			roommap.put("erse_num", Integer.parseInt(sum.getErse_num()));
			roommap.put("clca_num", Integer.parseInt(sum.getClca_num()));
			roommap.put("fati_num", Integer.parseInt(sum.getFati_num()));
			roommap.put("enca_num", Integer.parseInt(sum.getEnca_num()));
			roommap.put("bage_num", Integer.parseInt(sum.getBage_num()));
			roommap.put("teab_num", Integer.parseInt(sum.getTeab_num()));
			roommap.put("meca_num", Integer.parseInt(sum.getMeca_num()));
			roommap.put("opbo_num", Integer.parseInt(sum.getOpbo_num()));
			roommap.put("blte_num", Integer.parseInt(sum.getBlte_num()));
			roommap.put("dnds_num", Integer.parseInt(sum.getDnds_num()));
			roommap.put("tvca_num", Integer.parseInt(sum.getTvca_num()));
			roommap.put("orel_num", Integer.parseInt(sum.getOrel_num()));
			roommap.put("memo_num", Integer.parseInt(sum.getMemo_num()));
			roommap.put("coas_num", Integer.parseInt(sum.getCoas_num()));
			roommap.put("matc_num", Integer.parseInt(sum.getMatc_num()));
			roommap.put("mapp_num", Integer.parseInt(sum.getMapp_num()));
			roommap.put("rule_num", Integer.parseInt(sum.getRule_num()));
			roommap.put("stat_num", Integer.parseInt(sum.getStat_num()));
			roommap.put("clip_num", Integer.parseInt(sum.getClip_num()));
			roommap.put("bape_num", Integer.parseInt(sum.getBape_num()));
			roommap.put("comp_num", Integer.parseInt(sum.getComp_num()));
			roommap.put("lali_num", Integer.parseInt(sum.getLali_num()));
			roommap.put("losu_num", Integer.parseInt(sum.getLosu_num()));
			roommap.put("shpa_num", Integer.parseInt(sum.getShpa_num()));
			roommap.put("anma_num", Integer.parseInt(sum.getAnma_num()));
			roommap.put("grte_num", Integer.parseInt(sum.getGrte_num()));
			roommap.put("chsl_num", Integer.parseInt(sum.getChsl_num()));
			roommap.put("cocl_num", Integer.parseInt(sum.getCocl_num()));
			roommap.put("arel_num", Integer.parseInt(sum.getArel_num()));
			
			roommap = CollectionUtil.sortByValue(roommap);
			
			Set set = roommap.keySet();
			Iterator itt = set.iterator();
			for(int i=0;i<roommap.size();i++) {
	            String key = (String) itt.next();
	            Integer value = roommap.get(key);
	            sum_num += value;
	        }
			if(sum_num != 0){
				analyseResult += "物品领取量排名前三位：";// 分析结果
				itt = set.iterator();
				for (int i=0;i<3;i++) {
					String key = (String) itt.next();
					Integer value = roommap.get(key);
					analyseResult += findname(key)+"，使用了"+value+"件，占该类物品消耗总数的"+StringUtil.strfloatToPer(StringUtil.save2Float(value/sum_num))+"；";
				}
			}
			else{
				analyseResult += "没有数据";
			}

			Map<String, Object> listMap = new HashMap<String, Object>();
			listMap.put("0", listGoal);// key存放该list在word中表格的索引，value存放list
			Map<String, Object> contentMap = new HashMap<String, Object>();
			String startTime = (String) map.get("startTime");
			String endTime = (String) map.get("endTime");
			contentMap.put("${startTime}", startTime.substring(0, 10));
			contentMap.put("${endTime}", endTime.substring(0, 10));
			contentMap.put("${analyseResult}", analyseResult);
			
			le.export2007Word(tempPath, listMap, contentMap, 2, out,-1);// 用模板生成word
			out.close();
			byteArr = FileHelper.downloadFile(fileName, path);// 提醒下载
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return byteArr;
	}
	/**
	 * 员工领取房间耗品list求和
	 * 
	 * @param list
	 * @return
	 */
	private StaRoom sumStaRoomExpend(List<StaRoom> list) {
		StaRoom sum = new StaRoom();
		Iterator<StaRoom> sr = list.iterator();

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

		StaRoom staRoom = null;
		while (sr.hasNext()) {
			staRoom = sr.next();
			sum_umbr_num += Integer.valueOf(staRoom.getUmbr_num());
			sum_coff_num += Integer.valueOf(staRoom.getCoff_num());
			sum_suge_num += Integer.valueOf(staRoom.getSuge_num());
			sum_coup_num += Integer.valueOf(staRoom.getCoup_num());
			sum_penc_num += Integer.valueOf(staRoom.getPenc_num());
			sum_erse_num += Integer.valueOf(staRoom.getErse_num());
			sum_clca_num += Integer.valueOf(staRoom.getClca_num());
			sum_fati_num += Integer.valueOf(staRoom.getFati_num());
			sum_enca_num += Integer.valueOf(staRoom.getEnca_num());
			sum_bage_num += Integer.valueOf(staRoom.getBage_num());
			sum_teab_num += Integer.valueOf(staRoom.getTeab_num());
			sum_meca_num += Integer.valueOf(staRoom.getMeca_num());
			sum_opbo_num += Integer.valueOf(staRoom.getOpbo_num());
			sum_blte_num += Integer.valueOf(staRoom.getBlte_num());
			sum_dnds_num += Integer.valueOf(staRoom.getDnds_num());
			sum_tvca_num += Integer.valueOf(staRoom.getTvca_num());
			sum_orel_num += Integer.valueOf(staRoom.getOrel_num());
			sum_memo_num += Integer.valueOf(staRoom.getMemo_num());
			sum_coas_num += Integer.valueOf(staRoom.getCoas_num());
			sum_matc_num += Integer.valueOf(staRoom.getMatc_num());
			sum_mapp_num += Integer.valueOf(staRoom.getMapp_num());
			sum_rule_num += Integer.valueOf(staRoom.getRule_num());
			sum_stat_num += Integer.valueOf(staRoom.getStat_num());
			sum_clip_num += Integer.valueOf(staRoom.getClip_num());
			sum_bape_num += Integer.valueOf(staRoom.getBape_num());
			sum_comp_num += Integer.valueOf(staRoom.getComp_num());
			sum_lali_num += Integer.valueOf(staRoom.getLali_num());
			sum_losu_num += Integer.valueOf(staRoom.getLosu_num());
			sum_shpa_num += Integer.valueOf(staRoom.getShpa_num());
			sum_anma_num += Integer.valueOf(staRoom.getAnma_num());
			sum_grte_num += Integer.valueOf(staRoom.getGrte_num());
			sum_chsl_num += Integer.valueOf(staRoom.getChsl_num());
			sum_cocl_num += Integer.valueOf(staRoom.getCocl_num());
			sum_arel_num += Integer.valueOf(staRoom.getArel_num());
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
	 * 员工领取房间耗品list求平均
	 * 
	 * @param list
	 * @return
	 */
	private StaRoom avgStaRoomExpend(List<StaRoom> list) {

		StaRoom avg = new StaRoom();
		Iterator<StaRoom> re = list.iterator();
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

		StaRoom staRoom = null;
		if (chu != 0) {
			while (re.hasNext()) {
				staRoom = re.next();
				sum_umbr_num += Integer.valueOf(staRoom.getUmbr_num());
				sum_coff_num += Integer.valueOf(staRoom.getCoff_num());
				sum_suge_num += Integer.valueOf(staRoom.getSuge_num());
				sum_coup_num += Integer.valueOf(staRoom.getCoup_num());
				sum_penc_num += Integer.valueOf(staRoom.getPenc_num());
				sum_erse_num += Integer.valueOf(staRoom.getErse_num());
				sum_clca_num += Integer.valueOf(staRoom.getClca_num());
				sum_fati_num += Integer.valueOf(staRoom.getFati_num());
				sum_enca_num += Integer.valueOf(staRoom.getEnca_num());
				sum_bage_num += Integer.valueOf(staRoom.getBage_num());
				sum_teab_num += Integer.valueOf(staRoom.getTeab_num());
				sum_meca_num += Integer.valueOf(staRoom.getMeca_num());
				sum_opbo_num += Integer.valueOf(staRoom.getOpbo_num());
				sum_blte_num += Integer.valueOf(staRoom.getBlte_num());
				sum_dnds_num += Integer.valueOf(staRoom.getDnds_num());
				sum_tvca_num += Integer.valueOf(staRoom.getTvca_num());
				sum_orel_num += Integer.valueOf(staRoom.getOrel_num());
				sum_memo_num += Integer.valueOf(staRoom.getMemo_num());
				sum_coas_num += Integer.valueOf(staRoom.getCoas_num());
				sum_matc_num += Integer.valueOf(staRoom.getMatc_num());
				sum_mapp_num += Integer.valueOf(staRoom.getMapp_num());
				sum_rule_num += Integer.valueOf(staRoom.getRule_num());
				sum_stat_num += Integer.valueOf(staRoom.getStat_num());
				sum_clip_num += Integer.valueOf(staRoom.getClip_num());
				sum_bape_num += Integer.valueOf(staRoom.getBape_num());
				sum_comp_num += Integer.valueOf(staRoom.getComp_num());
				sum_lali_num += Integer.valueOf(staRoom.getLali_num());
				sum_losu_num += Integer.valueOf(staRoom.getLosu_num());
				sum_shpa_num += Integer.valueOf(staRoom.getShpa_num());
				sum_anma_num += Integer.valueOf(staRoom.getAnma_num());
				sum_grte_num += Integer.valueOf(staRoom.getGrte_num());
				sum_chsl_num += Integer.valueOf(staRoom.getChsl_num());
				sum_cocl_num += Integer.valueOf(staRoom.getCocl_num());
				sum_arel_num += Integer.valueOf(staRoom.getArel_num());
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

	//员工领取卫生间耗品导出
	@Override
	public ResponseEntity<byte[]> exportStaWash(Map<String, Object> map, String path, String tempPath) {
		ResponseEntity<byte[]> byteArr = null;
		try {
			WordHelper<StaRoom> le = new WordHelper<StaRoom>();
			String fileName = "客房部员工领取卫生间耗品量统计表.docx";
			
			path = FileHelper.transPath(fileName, path);// 解析后的上传路径
			OutputStream out = new FileOutputStream(path);

			List<Integer> listCondition = expendFormDao.selectCondition("卫生间易耗品");
			List<Object> listSource = expendFormDao.selectStaExpend(map, listCondition);
			Iterator<Object> it = listSource.iterator();
			List<StaWash> listGoal = objToWashStaExpand(it);

			StaWash sum = sumStaWashExpend(listGoal);// 合计
			StaWash avg = avgStaWashExpend(listGoal);// 平均
			listGoal.add(sum);
			listGoal.add(avg);
			
			float sum_num = (float) 0.0;
			String analyseResult = "分析结果：";// 分析结果
			Map<String, Integer> washmap = new HashMap<String, Integer>();
			washmap.put("toth_num", Integer.parseInt(sum.getToth_num()));
			washmap.put("ropa_num", Integer.parseInt(sum.getRopa_num()));
			washmap.put("rins_num", Integer.parseInt(sum.getRins_num()));
			washmap.put("bafo_num", Integer.parseInt(sum.getBafo_num()));
			washmap.put("haco_num", Integer.parseInt(sum.getHaco_num()));
			washmap.put("shge_num", Integer.parseInt(sum.getShge_num()));
			washmap.put("capa_num", Integer.parseInt(sum.getCapa_num()));
			washmap.put("garb_num", Integer.parseInt(sum.getGarb_num()));
			washmap.put("paex_num", Integer.parseInt(sum.getPaex_num()));
			washmap.put("peep_num", Integer.parseInt(sum.getPeep_num()));
			washmap.put("shca_num", Integer.parseInt(sum.getShca_num()));
			washmap.put("shav_num", Integer.parseInt(sum.getShav_num()));
			washmap.put("comb_num", Integer.parseInt(sum.getComb_num()));
			washmap.put("shcl_num", Integer.parseInt(sum.getShcl_num()));
			washmap.put("soap_num", Integer.parseInt(sum.getSoap_num()));
			washmap.put("nacl_num", Integer.parseInt(sum.getNacl_num()));
			washmap.put("flow_num", Integer.parseInt(sum.getFlow_num()));
			washmap.put("basa_num", Integer.parseInt(sum.getBasa_num()));
			washmap.put("scpa_num", Integer.parseInt(sum.getScpa_num()));
			washmap.put("rugl_num", Integer.parseInt(sum.getRugl_num()));
			washmap.put("dete_num", Integer.parseInt(sum.getDete_num()));
			washmap.put("thim_num", Integer.parseInt(sum.getThim_num()));
			washmap.put("bacl_num", Integer.parseInt(sum.getBacl_num()));
			washmap.put("tocl_num", Integer.parseInt(sum.getTocl_num()));
			washmap.put("babr_num", Integer.parseInt(sum.getBabr_num()));
			washmap.put("clbr_num", Integer.parseInt(sum.getClbr_num()));
			
			washmap = CollectionUtil.sortByValue(washmap);
			
			Set set = washmap.keySet();
			Iterator itt = set.iterator();
			for(int i=0;i<washmap.size();i++) {
	            String key = (String) itt.next();
	            Integer value = washmap.get(key);
	            sum_num += value;
	        }
			if(sum_num != 0){
				analyseResult += "物品领取量排名前三位：";// 分析结果
				itt = set.iterator();
				for (int i=0;i<3;i++) {
					String key = (String) itt.next();
					Integer value = washmap.get(key);
					analyseResult += findname(key)+"，使用了"+value+"件，占该类物品消耗总数的"+StringUtil.strfloatToPer(StringUtil.save2Float(value/sum_num))+"；";
					//boolean ascFlag = false;
					//CollectionUtil.sort(listGoal, key, ascFlag);
					//analyseResult += ",领取该物品最多的员工为"+listGoal.get(0).getStaff_name()+";";
				}
			}
			else{
				analyseResult += "没有数据";
			}

			Map<String, Object> listMap = new HashMap<String, Object>();
			listMap.put("0", listGoal);// key存放该list在word中表格的索引，value存放list
			Map<String, Object> contentMap = new HashMap<String, Object>();
			String startTime = (String) map.get("startTime");
			String endTime = (String) map.get("endTime");
			contentMap.put("${startTime}", startTime.substring(0, 10));
			contentMap.put("${endTime}", endTime.substring(0, 10));
			contentMap.put("${analyseResult}", analyseResult);
			
			le.export2007Word(tempPath, listMap, contentMap, 2, out,-1);// 用模板生成word
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
	private StaWash sumStaWashExpend(List<StaWash> list) {
		StaWash sum = new StaWash();
		Iterator<StaWash> it = list.iterator();

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

		StaWash staWash = null;
		while (it.hasNext()) {
			staWash = it.next();
			sum_toth_num += Integer.valueOf(staWash.getToth_num());
			sum_ropa_num += Integer.valueOf(staWash.getRopa_num());
			sum_rins_num += Integer.valueOf(staWash.getRins_num());
			sum_bafo_num += Integer.valueOf(staWash.getBafo_num());
			sum_haco_num += Integer.valueOf(staWash.getHaco_num());
			sum_shge_num += Integer.valueOf(staWash.getShge_num());
			sum_capa_num += Integer.valueOf(staWash.getCapa_num());
			sum_garb_num += Integer.valueOf(staWash.getGarb_num());
			sum_paex_num += Integer.valueOf(staWash.getPaex_num());
			sum_peep_num += Integer.valueOf(staWash.getPeep_num());
			sum_shca_num += Integer.valueOf(staWash.getShca_num());
			sum_shav_num += Integer.valueOf(staWash.getShav_num());
			sum_comb_num += Integer.valueOf(staWash.getComb_num());
			sum_shcl_num += Integer.valueOf(staWash.getShcl_num());
			sum_soap_num += Integer.valueOf(staWash.getSoap_num());
			sum_nacl_num += Integer.valueOf(staWash.getNacl_num());
			sum_flow_num += Integer.valueOf(staWash.getFlow_num());
			sum_basa_num += Integer.valueOf(staWash.getBasa_num());
			sum_scpa_num += Integer.valueOf(staWash.getScpa_num());
			sum_rugl_num += Integer.valueOf(staWash.getRugl_num());
			sum_dete_num += Integer.valueOf(staWash.getDete_num());
			sum_thim_num += Integer.valueOf(staWash.getThim_num());
			sum_bacl_num += Integer.valueOf(staWash.getBacl_num());
			sum_tocl_num += Integer.valueOf(staWash.getTocl_num());
			sum_babr_num += Integer.valueOf(staWash.getBabr_num());
			sum_clbr_num += Integer.valueOf(staWash.getClbr_num());
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
	private StaWash avgStaWashExpend(List<StaWash> list) {

		StaWash avg = new StaWash();
		Iterator<StaWash> it = list.iterator();
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

		StaWash staWash = null;
		if (chu != 0) {
			while (it.hasNext()) {
				staWash = it.next();
				sum_toth_num += Integer.valueOf(staWash.getToth_num());
				sum_ropa_num += Integer.valueOf(staWash.getRopa_num());
				sum_rins_num += Integer.valueOf(staWash.getRins_num());
				sum_bafo_num += Integer.valueOf(staWash.getBafo_num());
				sum_haco_num += Integer.valueOf(staWash.getHaco_num());
				sum_shge_num += Integer.valueOf(staWash.getShge_num());
				sum_capa_num += Integer.valueOf(staWash.getCapa_num());
				sum_garb_num += Integer.valueOf(staWash.getGarb_num());
				sum_paex_num += Integer.valueOf(staWash.getPaex_num());
				sum_peep_num += Integer.valueOf(staWash.getPeep_num());
				sum_shca_num += Integer.valueOf(staWash.getShca_num());
				sum_shav_num += Integer.valueOf(staWash.getShav_num());
				sum_comb_num += Integer.valueOf(staWash.getComb_num());
				sum_shcl_num += Integer.valueOf(staWash.getShcl_num());
				sum_soap_num += Integer.valueOf(staWash.getSoap_num());
				sum_nacl_num += Integer.valueOf(staWash.getNacl_num());
				sum_flow_num += Integer.valueOf(staWash.getFlow_num());
				sum_basa_num += Integer.valueOf(staWash.getBasa_num());
				sum_scpa_num += Integer.valueOf(staWash.getScpa_num());
				sum_rugl_num += Integer.valueOf(staWash.getRugl_num());
				sum_dete_num += Integer.valueOf(staWash.getDete_num());
				sum_thim_num += Integer.valueOf(staWash.getThim_num());
				sum_bacl_num += Integer.valueOf(staWash.getBacl_num());
				sum_tocl_num += Integer.valueOf(staWash.getTocl_num());
				sum_babr_num += Integer.valueOf(staWash.getBabr_num());
				sum_clbr_num += Integer.valueOf(staWash.getClbr_num());
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

	//员工领取迷你吧导出
	@Override
	public ResponseEntity<byte[]> exportStaMini(Map<String, Object> map, String path, String tempPath) {
		ResponseEntity<byte[]> byteArr = null;
		try {
			WordHelper<StaRoom> le = new WordHelper<StaRoom>();
			String fileName = "客房部员工领取迷你吧量统计表.docx";
			
			path = FileHelper.transPath(fileName, path);// 解析后的上传路径
			OutputStream out = new FileOutputStream(path);

			List<Object> listSource = expendFormDao.selectStaMini(map);
			Iterator<Object> it = listSource.iterator();
			List<StaMini> listGoal = objToMiniStaExpand(it);

			StaMini sum = sumStaMiniExpend(listGoal);// 合计
			StaMini avg = avgStaMiniExpend(listGoal);// 平均
			listGoal.add(sum);
			listGoal.add(avg);

			float sum_num = (float) 0.0;
			String analyseResult = "分析结果：";// 分析结果
			Map<String, Integer> minimap = new HashMap<String, Integer>();
			minimap.put("redb_num", Integer.parseInt(sum.getRedb_num()));
			minimap.put("coco_num", Integer.parseInt(sum.getCoco_num()));
			minimap.put("pari_num", Integer.parseInt(sum.getPari_num()));
			minimap.put("bige_num", Integer.parseInt(sum.getBige_num()));
			minimap.put("jdba_num", Integer.parseInt(sum.getJdba_num()));
			minimap.put("tine_num", Integer.parseInt(sum.getTine_num()));
			minimap.put("kunl_num", Integer.parseInt(sum.getKunl_num()));
			minimap.put("wine_num", Integer.parseInt(sum.getWine_num()));
			minimap.put("bree_num", Integer.parseInt(sum.getBree_num()));
			minimap.put("vodk_num", Integer.parseInt(sum.getVodk_num()));
			minimap.put("auru_num", Integer.parseInt(sum.getAuru_num()));
			minimap.put("qing_num", Integer.parseInt(sum.getQing_num()));
			minimap.put("spri_num", Integer.parseInt(sum.getSpri_num()));
			minimap.put("nail_num", Integer.parseInt(sum.getNail_num()));
			minimap.put("abcs_num", Integer.parseInt(sum.getAbcs_num()));
			minimap.put("card_num", Integer.parseInt(sum.getCard_num()));
			minimap.put("como_num", Integer.parseInt(sum.getComo_num()));
			
			minimap = CollectionUtil.sortByValue(minimap);
			
			Set set = minimap.keySet();
			Iterator itt = set.iterator();
			for(int i=0;i<minimap.size();i++) {
	            String key = (String) itt.next();
	            Integer value = minimap.get(key);
	            sum_num += value;
	        }
			if(sum_num != 0){
				analyseResult += "物品领取量排名前三位：";// 分析结果
				itt = set.iterator();
				for (int i=0;i<3;i++) {
					String key = (String) itt.next();
					Integer value = minimap.get(key);
					analyseResult += findname(key)+",使用了"+value+"件，占该类物品消耗总数的"+StringUtil.strfloatToPer(StringUtil.save2Float(value/sum_num))+"；";
					//boolean ascFlag = false;
					//CollectionUtil.sort(listGoal, key, ascFlag);
					//analyseResult += ",领取该物品最多的员工为"+listGoal.get(0).getStaff_name()+";";
				}
			}
			else{
				analyseResult += "没有数据";
			}

			Map<String, Object> listMap = new HashMap<String, Object>();
			listMap.put("0", listGoal);// key存放该list在word中表格的索引，value存放list
			Map<String, Object> contentMap = new HashMap<String, Object>();
			String startTime = (String) map.get("startTime");
			String endTime = (String) map.get("endTime");
			contentMap.put("${analyseResult}", analyseResult);
			contentMap.put("${startTime}", startTime.substring(0, 10));
			contentMap.put("${endTime}", endTime.substring(0, 10));

			le.export2007Word(tempPath, listMap, contentMap, 2, out,-1);// 用模板生成word
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
	private StaMini sumStaMiniExpend(List<StaMini> list) {
		StaMini sum = new StaMini();
		Iterator<StaMini> it = list.iterator();

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

		StaMini staMini = null;
		while (it.hasNext()) {
			staMini = it.next();

			sum_redb_num += Integer.valueOf(staMini.getRedb_num()); 
			sum_coco_num += Integer.valueOf(staMini.getCoco_num());
			sum_pari_num += Integer.valueOf(staMini.getPari_num());
			sum_bige_num += Integer.valueOf(staMini.getBige_num());
			sum_jdba_num += Integer.valueOf(staMini.getJdba_num());
			sum_tine_num += Integer.valueOf(staMini.getTine_num());
			sum_kunl_num += Integer.valueOf(staMini.getKunl_num());
			sum_wine_num += Integer.valueOf(staMini.getWine_num());
			sum_bree_num += Integer.valueOf(staMini.getBree_num());
			sum_vodk_num += Integer.valueOf(staMini.getVodk_num());
			sum_auru_num += Integer.valueOf(staMini.getAuru_num());
			sum_qing_num += Integer.valueOf(staMini.getQing_num());
			sum_spri_num += Integer.valueOf(staMini.getSpri_num());
			sum_nail_num += Integer.valueOf(staMini.getNail_num());
			sum_abcs_num += Integer.valueOf(staMini.getAbcs_num());
			sum_card_num += Integer.valueOf(staMini.getCard_num());
			sum_como_num += Integer.valueOf(staMini.getComo_num());
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
	private StaMini avgStaMiniExpend(List<StaMini> list) {

		StaMini avg = new StaMini();
		Iterator<StaMini> it = list.iterator();

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

		StaMini staMini = null;
		Float chu = Float.valueOf(list.size());
		if (chu != 0) {
			while (it.hasNext()) {
				staMini = it.next();
				sum_redb_num += Integer.valueOf(staMini.getRedb_num()); 
				sum_coco_num += Integer.valueOf(staMini.getCoco_num());
				sum_pari_num += Integer.valueOf(staMini.getPari_num());
				sum_bige_num += Integer.valueOf(staMini.getBige_num());
				sum_jdba_num += Integer.valueOf(staMini.getJdba_num());
				sum_tine_num += Integer.valueOf(staMini.getTine_num());
				sum_kunl_num += Integer.valueOf(staMini.getKunl_num());
				sum_wine_num += Integer.valueOf(staMini.getWine_num());
				sum_bree_num += Integer.valueOf(staMini.getBree_num());
				sum_vodk_num += Integer.valueOf(staMini.getVodk_num());
				sum_auru_num += Integer.valueOf(staMini.getAuru_num());
				sum_qing_num += Integer.valueOf(staMini.getQing_num());
				sum_spri_num += Integer.valueOf(staMini.getSpri_num());
				sum_nail_num += Integer.valueOf(staMini.getNail_num());
				sum_abcs_num += Integer.valueOf(staMini.getAbcs_num());
				sum_card_num += Integer.valueOf(staMini.getCard_num());
				sum_como_num += Integer.valueOf(staMini.getComo_num());
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
	
	// 导出员工领取耗品统计表，excel格式
	@Override
	public ResponseEntity<byte[]> exportStaExpendExcel(Map<String, Object> map) {
		ResponseEntity<byte[]> byteArr = null;
		List<StaLinen> staLinenList = null;
		List<StaRoom> staRoomList = null;
		List<StaWash> staWashList = null;
		List<StaMini> staMiniList = null;

		String startTime = (String) map.get("startTime");
		String endTime = (String) map.get("endTime");
		String tableType = (String) map.get("tableType");
		String path = (String) map.get("path");
		
		switch(tableType){
			case "0":
				String linenfileName = "客房部员工领取布草量统计表.xlsx";
				String linentitle = "(客房部布草使用量统计表" + startTime.substring(0,10) + "至 " + endTime.substring(0,10) + ")";
				try {
					ExcelHelper<StaLinen> ex = new ExcelHelper<StaLinen>();
					path = FileHelper.transPath(linenfileName, path);// 解析后的上传路径
					OutputStream out = new FileOutputStream(path);

					// 获取列表和文本信息
					List<Integer> listCondition = expendFormDao.selectCondition("房间布草");
					List<Object> listSource = expendFormDao.selectStaExpend(map, listCondition);

					Iterator<Object> it = listSource.iterator();
					staLinenList = objToLinenStaExpand(it);
					StaLinen sum = sumStaLinenExpend(staLinenList);// 合计
					StaLinen avg = avgStaLinenExpend(staLinenList);// 平均
					staLinenList.add(sum);
					staLinenList.add(avg);

					String[] header = { "序号", "员工编号","员工姓名", "被罩", "拼尘罩", "洗衣袋", "床单", "面巾", "浴巾", "方巾","中巾" ,"地巾", "浴袍", "枕套", "枕芯", "毛毯", "购物袋" };// 顺序必须和对应实体一致
					ex.export2007Excel(linentitle, header, (Collection) staLinenList, out, "yyyy-MM-dd",-1,-1,-1, 0, 1);// -1表示没有合并单元格,0:没有隐藏实体类

					out.close();
					byteArr = FileHelper.downloadFile(linenfileName, path);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case "1":
				String roomfileName = "客房部员工领取房间耗品量统计表.xlsx";
				String roomtitle = "(客房部员工领取房间耗品量统计表" + startTime.substring(0,10) + "至 " + endTime.substring(0,10) + ")";
				try {
					ExcelHelper<StaRoom> ex = new ExcelHelper<StaRoom>();
					path = FileHelper.transPath(roomfileName, path);// 解析后的上传路径
					OutputStream out = new FileOutputStream(path);

					// 获取列表和文本信息
					List<Integer> listCondition = expendFormDao.selectCondition("房间易耗品");
					List<Object> listSource = expendFormDao.selectStaExpend(map, listCondition);

					Iterator<Object> it = listSource.iterator();
					staRoomList = objToRoomStaExpand(it);
					StaRoom sum = sumStaRoomExpend(staRoomList);// 合计
					StaRoom avg = avgStaRoomExpend(staRoomList);// 平均
					staRoomList.add(sum);
					staRoomList.add(avg);

					String[] header = {"序号","员工编号","员工姓名", "雨伞","咖啡","白糖","伴侣","铅笔","橡皮","即扫牌","面巾纸","环保卡","手提袋","袋泡茶","送餐牌","意见书","立顿红茶","请勿打扰牌","电视节目单","信封(普通)",
							"便签","杯垫","火柴","地图","尺子","信纸","回形针","圆珠笔","针线包","洗衣单","低卡糖","擦鞋布","防毒面具","立顿绿茶","拖鞋(儿童)","彩色曲别针","信封(航空)"};// 顺序必须和对应实体一致
					ex.export2007Excel(roomtitle, header, (Collection) staRoomList, out, "yyyy-MM-dd",-1,-1,-1, 0, 1);// -1表示没有合并单元格,0:没有隐藏实体类

					out.close();
					byteArr = FileHelper.downloadFile(roomfileName, path);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case "2":
				String washfileName = "客房部员工领取卫生间耗品量统计表.xlsx";
				String washtitle = "(客房部员工领取卫生间耗品量统计表" + startTime.substring(0,10) + "至 " + endTime.substring(0,10) + ")";
				try {
					ExcelHelper<StaWash> ex = new ExcelHelper<StaWash>();
					path = FileHelper.transPath(washfileName, path);// 解析后的上传路径
					OutputStream out = new FileOutputStream(path);

					// 获取列表和文本信息
					List<Integer> listCondition = expendFormDao.selectCondition("卫生间易耗品");
					List<Object> listSource = expendFormDao.selectStaExpend(map, listCondition);

					Iterator<Object> it = listSource.iterator();
					staWashList = objToWashStaExpand(it);
					StaWash sum = sumStaWashExpend(staWashList);// 合计
					StaWash avg = avgStaWashExpend(staWashList);// 平均
					staWashList.add(sum);
					staWashList.add(avg);

					String[] header = {"序号","员工编号","员工姓名","牙具","卷纸","	洗发液","沐浴露","护发素","润肤露","护理包","黑垃圾袋","抽纸","卫生袋","浴帽","剃须刨","梳子",
							"擦鞋布","手皂","指甲锉","干花","浴盐","百洁布","橡皮手套","洗涤灵","洗消净","浴室清洁剂","洁厕灵","擦鞋布","浴缸刷","恭桶刷"};// 顺序必须和对应实体一致
					ex.export2007Excel(washtitle, header, (Collection)staWashList, out, "yyyy-MM-dd",-1,-1,-1, 0, 1);// -1表示没有合并单元格,0:没有隐藏实体类

					out.close();
					byteArr = FileHelper.downloadFile(washfileName, path);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case "3":
				String minifileName = "客房部员工领取迷你吧量统计表.xlsx";
				String minititle = "(客房部员工领取迷你吧量统计表" + startTime.substring(0,10) + "至 " + endTime.substring(0,10) + ")";
				try {
					ExcelHelper<MiniExpend> ex = new ExcelHelper<MiniExpend>();
					path = FileHelper.transPath(minifileName, path);// 解析后的上传路径
					OutputStream out = new FileOutputStream(path);

					// 获取列表和文本信息
					List<Object> listSource = expendFormDao.selectStaMini(map);

					Iterator<Object> it = listSource.iterator();
					staMiniList = objToMiniStaExpand(it);
					StaMini sum = sumStaMiniExpend(staMiniList);// 合计
					StaMini avg = avgStaMiniExpend(staMiniList);// 平均
					staMiniList.add(sum);
					staMiniList.add(avg);

					String[] header = {"序号","员工编号","员工姓名","红牛","可口可乐","巴黎水","大依云","加多宝","小依云","昆仑山","红葡萄酒","威士忌","伏加特","金酒","青岛",
							"雪碧","指甲刀","ABC卫生巾","扑克牌","普通安全套"};// 顺序必须和对应实体一致
					ex.export2007Excel(minititle, header, (Collection)staMiniList, out, "yyyy-MM-dd",-1,-1,-1, 0, 1);// -1表示没有合并单元格,0:没有隐藏实体类

					out.close();
					byteArr = FileHelper.downloadFile(minifileName, path);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
		}
		return byteArr;
	}
	
	//获取物品中文名称
	public String findname(String ary){
		String name = "";
		switch(ary){
		case "slba_num": name="被罩"; break;
		case "duto_num": name="拼尘罩"; break;
		case "laba_num": name="洗衣袋"; break;
		case "besh_num": name="床单"; break;
		case "facl_num": name="面巾"; break;
		case "bato_num": name="浴巾"; break;
		case "hato_num": name="方巾"; break;
		case "medo_num": name="中巾"; break;
		case "flto_num": name="地巾"; break;
		case "baro_num": name="浴袍"; break;
		case "pill_num": name="枕套"; break;
		case "piin_num": name="枕芯"; break;
		case "blan_num": name="毛毯"; break;
		case "shop_num": name="购物袋"; break;
		case "umbr_num": name="雨伞"; break;
		case "coff_num": name="咖啡"; break;
		case "suge_num": name="白糖"; break;
		case "coup_num": name="伴侣"; break;
		case "penc_num": name="铅笔"; break;
		case "erse_num": name="橡皮"; break;
		case "clca_num": name="即扫牌"; break;
		case "fati_num": name="面巾纸"; break;
		case "enca_num": name="环保卡"; break;
		case "bage_num": name="手提袋"; break;
		case "teab_num": name="袋泡茶"; break;
		case "meca_num": name="送餐牌"; break;
		case "opbo_num": name="意见书"; break;
		case "blte_num": name="立顿红茶"; break;
		case "dnds_num": name="请勿打扰牌"; break;
		case "tvca_num": name="电视节目单"; break;
		case "orel_num": name="信封(普通)"; break;
		case "memo_num": name="便签"; break;
		case "coas_num": name="杯垫"; break;
		case "matc_num": name="火柴"; break;
		case "mapp_num": name="地图"; break;
		case "rule_num": name="尺子"; break;
		case "stat_num": name="信纸"; break;
		case "clip_num": name="回形针"; break;
		case "bape_num": name="圆珠笔"; break;
		case "comp_num": name="针线包"; break;
		case "lali_num": name="洗衣单"; break;
		case "losu_num": name="低卡糖"; break;
		case "shpa_num": name="擦鞋布"; break;
		case "anma_num": name="防毒面具"; break;
		case "grte_num": name="立顿绿茶"; break;
		case "chsl_num": name="拖鞋(儿童)"; break;
		case "cocl_num": name="彩色曲别针"; break;
		case "arel_num": name="信封(航空)"; break;
		case "toth_num": name="牙具"; break;
		case "ropa_num": name="卷纸"; break;
		case "rins_num": name="洗发液"; break;
		case "bafo_num": name="沐浴液"; break;
		case "haco_num": name="护发素"; break;
		case "shge_num": name="润肤露"; break;
		case "capa_num": name="护理包"; break;
		case "garb_num": name="黑垃圾袋"; break;
		case "paex_num": name="抽纸"; break;
		case "peep_num": name="卫生袋"; break;
		case "shca_num": name="浴帽"; break;
		case "shav_num": name="剃须刨"; break;
		case "comb_num": name="梳子"; break;
		case "shcl_num": name="擦鞋布"; break;
		case "soap_num": name="手皂"; break;
		case "nacl_num": name="指甲锉"; break;
		case "flow_num": name="干花"; break;
		case "basa_num": name="浴盐"; break;
		case "scpa_num": name="百洁布"; break;
		case "rugl_num": name="橡皮手套"; break;
		case "dete_num": name="洗涤灵"; break;
		case "thim_num": name="洗消净"; break;
		case "bacl_num": name="浴室清洁剂"; break;
		case "tocl_num": name="洁厕灵"; break;
		case "babr_num": name="浴缸刷"; break;
		case "clbr_num": name="恭桶刷"; break;
		case "coco_num": name="可口可乐"; break;
		case "pari_num": name="法国巴黎水"; break;
		case "bige_num": name="大依云"; break;
		case "jdba_num": name="加多宝"; break;
		case "tine_num": name="小依云"; break;
		case "kunl_num": name="昆仑山矿泉水"; break;
		case "wine_num": name="红葡萄酒"; break;
		case "bree_num": name="威士忌"; break;
		case "vodk_num": name="伏加特"; break;
		case "auru_num": name="金酒"; break;
		case "qing_num": name="青岛听装"; break;
		case "spri_num": name="雪碧听装"; break;
		case "nail_num": name="指甲刀"; break;
		case "abcs_num": name="ABC卫生巾"; break;
		case "card_num": name="扑克牌"; break;
		case "como_num": name="普通安全套"; break;
		}
		return name;
	}
}
