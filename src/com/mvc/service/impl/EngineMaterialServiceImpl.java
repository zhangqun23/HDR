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

import com.mvc.dao.EngineMaterialDao;
import com.mvc.entity.MaterialSort;
import com.mvc.entityReport.EngineMaterial;
import com.mvc.repository.MaterialSortRepository;
import com.mvc.service.EngineMaterialService;
import com.utils.CollectionUtil;
import com.utils.ExcelHelper;
import com.utils.FileHelper;
import com.utils.StringUtil;
import com.utils.WordHelper;

import net.sf.json.JSONObject;

/**
 * 工程物料管理业务层实现
 * 
 * @author wangrui
 * @date 2017年2月20日
 */
@Service("engineMaterialServiceImpl")
public class EngineMaterialServiceImpl implements EngineMaterialService {

	@Autowired
	EngineMaterialDao engineMaterialDao;
	@Autowired
	MaterialSortRepository materialSortRepository;

	// 查询工程物料
	@Override
	public String selectEngineMaterial(Map<String, Object> map) {
		JSONObject jsonObject = new JSONObject();

		Integer sortId = (Integer) map.get("sortId");
		MaterialSort materialSort = materialSortRepository.findOne(sortId);
		if (materialSort.getSortName().equals("所有分类")) {
			map.put("parentId", -1);
		}

		List<Object> listSource = engineMaterialDao.selectEngineMaterial(map);
		Iterator<Object> it = listSource.iterator();
		List<EngineMaterial> listGoal = objToEngineMat(it);
		jsonObject.put("list", listGoal);

		String analyseResult = anaMat(map);
		jsonObject.put("analyseResult", analyseResult);// 报表分析

		return jsonObject.toString();
	}

	// 计算
	private List<EngineMaterial> objToEngineMat(Iterator<Object> it) {
		List<EngineMaterial> listGoal = new ArrayList<EngineMaterial>();
		Object[] obj = null;
		EngineMaterial engineMaterial = null;
		int i = 0;
		while (it.hasNext()) {
			obj = (Object[]) it.next();
			i++;
			engineMaterial = new EngineMaterial();
			engineMaterial.setOrderNum(String.valueOf(i));// 序号
			engineMaterial.setMaterial_sort(obj[0].toString());// 材料类型
			engineMaterial.setMaterial_name(obj[1].toString());// 材料名称
			engineMaterial.setMaterial_type(obj[2].toString());// 材料型号
			engineMaterial.setTake_amount(obj[3].toString());// 用量
			engineMaterial.setMterial_unit(obj[4].toString());// 材料单位
			engineMaterial.setTask_num(obj[5].toString());// 任务数

			String avg_task_num = StringUtil.divide(obj[3].toString(), obj[5].toString());
			engineMaterial.setAvg_task_num(avg_task_num);// 平均用量

			listGoal.add(engineMaterial);
		}

		return listGoal;
	}

	/**
	 * 统计分析
	 * 
	 * @param map
	 * @return
	 */
	private String anaMat(Map<String, Object> map) {
		StringBuilder strb = new StringBuilder();

		String startTime = (String) map.get("startTime");
		String endTime = (String) map.get("endTime");
		strb.append("从" + startTime.substring(0, 7) + "至" + endTime.substring(0, 7));

		List<Object> listSource = engineMaterialDao.selectSumEngineMaterial(map);
		Iterator<Object> it = listSource.iterator();
		Object[] obj = null;
		long sum = 0;
		String sortName = null;
		int taskNum = 0;
		StringBuilder subStrb = new StringBuilder();
		Map<String, Integer> compareMap = new HashMap<String, Integer>();
		while (it.hasNext()) {
			obj = (Object[]) it.next();
			sortName = obj[0].toString();
			taskNum = Integer.parseInt(obj[1].toString());
			sum += taskNum;
			subStrb.append(sortName + "：" + taskNum + "项，");
			compareMap.put(sortName, taskNum);
		}
		strb.append("工程材料共产生任务数：" + sum + "，其中：");
		if (subStrb.length() > 0) {
			strb.append(subStrb.substring(0, subStrb.length() - 1));// 去掉最后一个逗号
		}
		compareMap = CollectionUtil.sortByValue(compareMap);
		strb.append("；排名前三的材料类型包括：");
		subStrb.delete(0, subStrb.length()); // 清空subStrb
		int i = 0;
		for (Map.Entry<String, Integer> entry : compareMap.entrySet()) {
			if (i < 3) {
				subStrb.append(entry.getKey() + "(" + entry.getValue() + ")，");
			} else {
				break;
			}
			i++;
		}
		if (subStrb.length() > 0) {
			strb.append(subStrb.substring(0, subStrb.length() - 1));// 去掉最后一个逗号
		} else {
			strb.append("无");
		}
		return strb.toString();
	}

	// 工程物料统计Word
	@Override
	public ResponseEntity<byte[]> exportEngineMaterial(Map<String, Object> map, String path, String tempPath) {
		ResponseEntity<byte[]> byteArr = null;
		try {
			WordHelper<EngineMaterial> wh = new WordHelper<EngineMaterial>();
			String fileName = "工程物料管理统计表.docx";
			path = FileHelper.transPath(fileName, path);// 解析后的上传路径
			OutputStream out = new FileOutputStream(path);

			List<Object> listSource = engineMaterialDao.selectEngineMaterial(map);
			Iterator<Object> it = listSource.iterator();
			List<EngineMaterial> listGoal = objToEngineMat(it);

			Map<String, Object> listMap = new HashMap<String, Object>();
			listMap.put("0", listGoal);// key存放该list在word中表格的索引，value存放list
			Map<String, Object> contentMap = new HashMap<String, Object>();
			String startTime = (String) map.get("startTime");
			String endTime = (String) map.get("endTime");
			contentMap.put("${startTime}", startTime.substring(0, 7));
			contentMap.put("${endTime}", endTime.substring(0, 7));
			List<Integer> list = new ArrayList<Integer>();
			list.add(1);
			wh.export2007Word(tempPath, listMap, contentMap, 1, out, list,"material_sort");// 用模板生成word
			out.close();
			byteArr = FileHelper.downloadFile(fileName, path);// 提醒下载
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return byteArr;
	}

	// 工程物料统计Excel
	@Override
	public ResponseEntity<byte[]> exportEngineMaterialExcel(Map<String, Object> map, String path) {
		ResponseEntity<byte[]> byteArr = null;
		try {
			ExcelHelper<EngineMaterial> ex = new ExcelHelper<EngineMaterial>();
			String fileName = "工程物料管理统计表.xlsx";
			path = FileHelper.transPath(fileName, path);// 解析后的上传路径
			OutputStream out = new FileOutputStream(path);

			List<Object> listSource = engineMaterialDao.selectEngineMaterial(map);
			Iterator<Object> it = listSource.iterator();
			List<EngineMaterial> listGoal = objToEngineMat(it);

			String startTime = (String) map.get("startTime");
			String endTime = (String) map.get("endTime");
			String title = "工程物料管理统计表(" + startTime.substring(0, 7) + "至" + endTime.substring(0, 7) + ")";
			String[] header = { "序号", "材料类型", "材料名称", "材料型号", "用量", "材料单位", "任务数", "平均用量" };// 顺序必须和对应实体一致
			ex.export2007Excel(title, header, listGoal, out, "yyyy-MM-dd", -1, -1, -1, 0, 1);

			out.close();
			byteArr = FileHelper.downloadFile(fileName, path);// 提醒下载
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return byteArr;
	}

	// 获取物料分类
	@Override
	public List<MaterialSort> selectMatSortName() {
		return materialSortRepository.selectAllSortName();
	}

}
