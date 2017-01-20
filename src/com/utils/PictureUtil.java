/**
 * 
 */
package com.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.batik.transcoder.TranscoderException;

/**
 * 图片相关工具类
 * 
 * @author zjn
 * @date 2017年1月18日
 */
public class PictureUtil {
	public static Map<String, Object> getPicMap(String picCataPath, String svg) {
		Map<String, Object> picMap = new HashMap<String, Object>();
		String picName = "pic.png";
		String picPath = FileHelper.transPath(picName, picCataPath);// 解析后的上传路径

		try {
			SvgPngConverter.convertToPng(svg, picPath);// 图片svgCode转化为png格式，并保存到picPath
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (TranscoderException e1) {
			e1.printStackTrace();
		}
		picMap.put("width", 960);
		picMap.put("height", 400);
		picMap.put("type", "png");
		try {
			picMap.put("content", FileHelper.inputStream2ByteArray(new FileInputStream(picPath), true));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return picMap;
	}

	public static Map<String, Object> getHighPicMap(String picName,String picCataPath, String svg) {
		Map<String, Object> picMap = new HashMap<String, Object>();
		
		String picPath = FileHelper.transPath(picName, picCataPath);// 解析后的上传路径

		try {
			SvgPngConverter.convertToPng(svg, picPath);// 图片svgCode转化为png格式，并保存到picPath
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (TranscoderException e1) {
			e1.printStackTrace();
		}
		picMap.put("width", 550);
		picMap.put("height", 920);
		picMap.put("type", "png");
		try {
			picMap.put("content", FileHelper.inputStream2ByteArray(new FileInputStream(picPath), true));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return picMap;
	}

	public static Map<String, Object> getSquarePicMap(String picName, String picCataPath, String svg) {
		Map<String, Object> picMap = new HashMap<String, Object>();
		String picPath = FileHelper.transPath(picName, picCataPath);// 解析后的上传路径

		try {
			SvgPngConverter.convertToPng(svg, picPath);// 图片svgCode转化为png格式，并保存到picPath
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (TranscoderException e1) {
			e1.printStackTrace();
		}
		picMap.put("width", 500);
		picMap.put("height", 500);
		picMap.put("type", "png");
		try {
			picMap.put("content", FileHelper.inputStream2ByteArray(new FileInputStream(picPath), true));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return picMap;
	}
}
