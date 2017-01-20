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

	// 客房部员工工作量汇总表
	public static final String WORDLOAD_PATH = DICTIONARY + "workLoadSummary.docx";
	// 客房部员工工作量饱和度分析表
	public static final String WORDLOADLEVEL_PATH = DICTIONARY + "workLoadLevel.docx";
	// 客房部单个员工工作量分析图
	public static final String WORKLOADANALYSE_PATH = DICTIONARY + "workLoadAnalyse.docx";

	// 领班查房效率
	public static final String CHECKHOUSE_PATH = DICTIONARY + "checkHouseList.docx";

	// 部门员工做房用时统计表
	public static final String WORKHOUSE_PATH = DICTIONARY + "workHouse.docx";
	// 部门员工做房用时分析
	public static final String WORKHOUSEANA_PATH = DICTIONARY + "houseAnalyse.docx";
	// 部门员工工作效率统计表
	public static final String WORKEFF_PATH = DICTIONARY + "workEff.docx";
	// 部门员工工作效率分析
	public static final String WORKEFFANA_PATH = DICTIONARY + "workEffAna.docx";

	// 布草统计表
	public static final String LINENEXPEND_PATH = DICTIONARY + "linenExpend.docx";
	// 房间耗品统计表
	public static final String ROOMEXPEND_PATH = DICTIONARY + "roomExpend.docx";
	// 卫生间耗品统计表
	public static final String WASHEXPEND_PATH = DICTIONARY + "washExpend.docx";
	// 布草用量分析图
	public static final String LINENEXPENDPIC_PATH = DICTIONARY + "linenExpendPic.docx";
	// 房间耗品用量分析图
	public static final String ROOMEXPENDPIC_PATH = DICTIONARY + "roomExpendPic.docx";
	// 卫生间易耗品分析图
	public static final String WASHEXPENDPIC_PATH = DICTIONARY + "washExpendPic.docx";

	// 抢房效率表
	public static final String ROBEFFICIENCY_PATH = DICTIONARY + "robEfficiency.docx";
	// 抢房明细表
	public static final String ROBDETAIL_PATH = DICTIONARY + "robdetail.docx";
	// 抢房效率折线图
	public static final String ROBEFFICIENCYANALYSE_PATH = DICTIONARY + "robEfficiencyAnalyse.docx";

	// 驳回率统计表
	public static final String RejectEff_PATH = DICTIONARY + "workReject.docx";
	// 驳回率折线图
	public static final String RejectAnalyse_PATH = DICTIONARY + "rejectAnalyse.docx";

	// 导出酒店对客服务信息统计表
	public static final String CUSTOMERSERVICE_PATH = DICTIONARY + "customerService.docx";
	// 导出部门对客服务工作量统计表
	public static final String ROOMWORKLOAD_PATH = DICTIONARY + "roomWorkload.docx";
	// 导出部门对客服务类型统计
	public static final String ROOMWORKTYPE_PATH = DICTIONARY + "roomType.docx";

}
