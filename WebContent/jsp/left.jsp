﻿<script type="text/javascript" src="${ctx}/js/lib/jquery-1.9.1.min.js"></script>
<script type="text/javascript"
	src="${ctx}/js/lib/jquery.json-2.2.min.js"></script>
<section class="leftbar">
	<dl class="leftmenu">
		<!-- 客户部例行任务统计-->
		<dd>
			<div class="title ">
				<span><img src="${ctx}/images/leftico01.png" /></span>例行任务统计分析
			</div>
			<ul id="report-ul" class="menuson">
				<li id="workload"><cite></cite> <a
					href="${ctx}/workHouse/toReportFormPage.do#/workloadForm">员工工作量统计</a><i></i></li>
				<li id="workloadAnalysis"><cite></cite> <a
					href="${ctx}/workHouse/toReportFormPage.do#/workloadAnalysis">员工工作量分析</a><i></i></li>
				<li id="workloadLevelForm"><cite></cite> <a
					href="${ctx}/workHouse/toReportFormPage.do#/workloadLevelForm">员工工作量饱和度分析</a><i></i></li>
				<li id="doRoomTime"><cite></cite> <a
					href="${ctx}/workHouse/toReportFormPage.do#/workHouseForm">做房用时统计</a><i></i></li>
				<li id="doRoomAnalyse"><cite></cite> <a
					href="${ctx}/workHouse/toReportFormPage.do#/workHouseAnalyseForm">做房用时分析</a><i></i></li>
				<li id="doHomeEffic"><cite></cite> <a
					href="${ctx}/workHouse/toReportFormPage.do#/workEfficiencyForm">做房效率统计</a><i></i></li>
				<li id="doHomeEfficAnalyse"><cite></cite> <a
					href="${ctx}/workHouse/toReportFormPage.do#/workEffAnalyseForm">做房效率分析</a><i></i></li>
				<li id="doHomeReject"><cite></cite> <a
					href="${ctx}/workHouse/toReportFormPage.do#/workRejectForm">做房驳回率统计</a><i></i></li>
				<li id="doHomeRejectAnalyse"><cite></cite> <a
					href="${ctx}/workHouse/toReportFormPage.do#/workRejectAnalyseForm">做房驳回率分析</a><i></i></li>
			</ul>
		</dd>
		<!-- 查退房工作量/用时统计 -->
		<dd>
			<div class="title">
				<span><img src="${ctx}/images/leftico01.png" /></span>查房/抢房统计分析
			</div>
			<ul id="checkOrRobHome-ul" class="menuson">
				<li id="robHomeEfficiency"><cite></cite> <a
					href="${ctx}/checkOrRobHome/toCheckOrRobHomePage.do#/robEfficiencyForm">抢房效率统计</a><i></i></li>
				<li id="robHomeEffAnalyse"><cite></cite> <a
					href="${ctx}/checkOrRobHome/toCheckOrRobHomePage.do#/robEffAnalyseForm">抢房效率分析</a><i></i></li>
				<li id="checkHomeTime"><cite></cite> <a
					href="${ctx}/checkOrRobHome/toCheckOrRobHomePage.do#/checkEfficiencyForm">领班查房效率统计</a><i></i></li>
			</ul>
		</dd>

		<!-- 对客服务 -->
		<dd>
			<div class="title">
				<span><img src="${ctx}/images/leftico04.png" /></span>对客服务统计
			</div>
			<ul id="customerService-ul" class="menuson">
				<li id="depWorkload"><cite></cite> <a
					href="${ctx}/customerService/toReportFormPage.do#/depWorkloadForm">部门总工作量统计</a><i></i></li>
				<li id="staffWorkload"><cite></cite> <a
					href="${ctx}/customerService/toReportFormPage.do#/staffWorkloadForm">员工工作量统计</a><i></i></li>
				<li id="type"><cite></cite> <a
					href="${ctx}/customerService/toReportFormPage.do#/typeForm">服务类型统计</a><i></i></li>
				<li id="linenExpend"><cite></cite> <a
					href="${ctx}/customerService/toReportFormPage.do#/linenExpendForm">布草统计</a><i></i></li>
				<li id="linenExpendAnalyse"><cite></cite> <a
					href="${ctx}/customerService/toReportFormPage.do#/linenExpendAnalyse">布草用量分析</a><i></i></li>
				<li id="roomExpend"><cite></cite> <a
					href="${ctx}/customerService/toReportFormPage.do#/roomExpendForm">房间耗品统计</a><i></i></li>
				<li id="roomExpendAnalyse"><cite></cite> <a
					href="${ctx}/customerService/toReportFormPage.do#/roomExpendAnalyse">房间耗品用量分析</a><i></i></li>
				<li id="washExpend"><cite></cite> <a
					href="${ctx}/customerService/toReportFormPage.do#/washExpendForm">卫生间易耗品统计</a><i></i></li>
				<li id="washExpendAnalyse"><cite></cite> <a
					href="${ctx}/customerService/toReportFormPage.do#/washExpendAnalyse">卫生间易耗品用量分析</a><i></i></li>
				<li id="miniExpend"><cite></cite> <a
					href="${ctx}/customerService/toReportFormPage.do#/miniExpendForm">迷你吧用量统计</a><i></i></li>
				<li id="miniExpendAnalyse"><cite></cite> <a
					href="${ctx}/customerService/toReportFormPage.do#/miniExpendAnalyse">迷你吧用量分析</a><i></i></li>
			</ul>

		</dd>
	</dl>
</section>
<script>
	$(document).ready(function() {
		//点击li时将当前页面的信息存入sessionStorage
		var $li = $('.leftmenu li');
		$li.click(function() {
			sessionStorage.setItem("currentPage", $(this).attr('id'));
		});
	});
</script>
