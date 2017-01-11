<script type="text/javascript" src="${ctx}/js/lib/jquery-1.9.1.min.js"></script>
<script type="text/javascript"
	src="${ctx}/js/lib/jquery.json-2.2.min.js"></script>
<section class="leftbar">
	<dl class="leftmenu">
		<!-- 客户部例行任务统计-->
		<dd>
			<div class="title ">
				<span><img src="${ctx}/images/leftico01.png" /></span>例行任务统计
			</div>
			<ul id="report-ul" class="menuson">
				<li id="workLoad"><cite></cite> <a
					href="${ctx}/routineTaskForm/toReportFormPage.do#/workloadForm">员工工作量统计</a><i></i></li>
				<li id="workLoad"><cite></cite> <a
					href="${ctx}/routineTaskForm/toReportFormPage.do#/workloadAnalysis">员工工作量分析</a><i></i></li>
				<li id="workLoad"><cite></cite> <a
					href="${ctx}/routineTaskForm/toReportFormPage.do#/workloadSaturationForm">员工工作量饱和度分析</a><i></i></li>
				<li id="doRoomTime"><cite></cite> <a
					href="${ctx}/routineTaskForm/toReportFormPage.do#/workHouseForm">做房用时统计</a><i></i></li>
				<li id="doRoomAnalyse"><cite></cite> <a
					href="${ctx}/routineTaskForm/toReportFormPage.do#/workHouseAnalyseForm">做房用时分析</a><i></i></li>
				<li id="doHomeEffic"><cite></cite> <a
					href="${ctx}/routineTaskForm/toReportFormPage.do#/workEfficiencyForm">做房效率统计</a><i></i></li>
				<li id="suppplyUsage"><cite></cite> <a href="">客用品使用量统计</a><i></i></li>
				<li id="leaderCheckHome"><cite></cite> <a href="">领班查房工作量/用时统计</a><i></i></li>
			</ul>
		</dd>
		<!-- 查退房工作量/用时统计 -->
		<dd>
			<div class="title">
				<span><img src="${ctx}/images/leftico01.png" /></span>查退房工作量/用时统计
			</div>
			<ul id="checkHome-ul" class="menuson">
				<li id="checkHomeWork"><cite></cite> <a
					href="${ctx}/reportForm2/toReportFormPage.do#/reportForm2">查房工作量</a><i></i></li>
				<li id="checkHomeEffic"><cite></cite> <a href="">查房效率</a><i></i></li>
				<li id="checkHomeTime"><cite></cite> <a href="">查房时间分布图</a><i></i></li>
			</ul>
		</dd>

		<!-- 对客服务 -->
		<dd>
			<div class="title">
				<span><img src="${ctx}/images/leftico04.png" /></span>对客服务统计
			</div>
			<ul class="menuson">
				<li><cite></cite> <a
					href="${ctx}/customerService/toReportFormPage.do#/depWorkloadForm">部门总工作量统计</a><i></i></li>
				<li><cite></cite> <a
					href="${ctx}/customerService/toReportFormPage.do#/staffWorkloadForm">员工工作量统计</a><i></i></li>
				<li><cite></cite> <a
					href="${ctx}/customerService/toReportFormPage.do#/typeForm">服务类型统计</a><i></i></li>
				<li><cite></cite> <a
					href="${ctx}/customerService/toReportFormPage.do#/infoStatistics">布草/消耗品统计</a><i></i></li>
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
