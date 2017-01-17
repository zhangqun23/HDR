package com.base.constants;

/**
 * 报表相关路径常量
 * 
 * @author zjn
 * @date 2016年11月19日
 */
public class ReportFormConstants {

	// 报表相关上传路径
	public static final String SAVE_PATH = "/WEB-INF/reportForm";

	// 报表图片路径
	public static final String PIC_PATH = "/WEB-INF/picture";

	/**
	 * Word相关路径
	 */
	// word模版所在包名
	public static final String DICTIONARY = "word\\";

	// 客房部员工工作量汇总表word模版
	public static final String WORDLOAD_PATH = DICTIONARY + "workLoad.docx";
	// 客房部员工工作量饱和度分析表word模版
	public static final String WORDLOADLEVEL_PATH = DICTIONARY + "WorkLoadLevel.docx";

	//部门员工做房用时统计表
	public static final String WORKHOUSE_PATH = DICTIONARY + "workHouse.docx";
	
	public static final String LINENEXPEND_PATH = DICTIONARY + "linenexpend.docx";
	
}
