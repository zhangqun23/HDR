/**
 * 
 */
package com.mvc.service.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.mvc.service.ZjnService;
import com.utils.FileHelper;
import com.utils.PictureUtil;
import com.utils.StringUtil;
import com.utils.SvgPngConverter;
import com.utils.WordHelper;

/**
 * @author zjn
 * @date 2017年1月18日
 */
public class ZjnServiceImpl implements ZjnService {

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
		if (expendType.equals("0")) {
			fileName = "房间耗品用量分析图.docx";
		} else {
			fileName = "卫生间耗品用量分析图.docx";
		}
		path = FileHelper.transPath(fileName, path);// 解析后的上传路径
		picMap = PictureUtil.getPicMap(picCataPath, svg);

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

	// 导出卫布草用量分析图
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
		svgs[0] = (String) map.get("svg1");
		svgs[1] = (String) map.get("svg2");
		String[] picNames = new String[2];
		String[] picPaths = new String[2];

		for (int i = 0; i < 2; i++) {
			if (StringUtil.strIsNotEmpty(svgs[i])) {
				picNames[i] = "pic" + i + ".png";
				picPaths[i] = FileHelper.transPath(picNames[i], picCataPath);// 解析后的上传路径

				picMap = new HashMap<String, Object>();
				picMap.put("width", 960);
				picMap.put("height", 400);
				picMap.put("type", "png");
				try {
					SvgPngConverter.convertToPng(svgs[i], picPaths[i]);// 图片svgCode转化为png格式，并保存到picPath[i]
					picMap.put("content", FileHelper.inputStream2ByteArray(new FileInputStream(picPaths[i]), true));
				} catch (Exception ex) {
					ex.printStackTrace();
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

}
