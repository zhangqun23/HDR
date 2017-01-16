<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<jsp:include page="/jsp/top.jsp" />
<section id="customerService" ng-app="customerService" class="main">
	<div ng-view></div>
</section>
<jsp:include page="/jsp/left.jsp" />
<jsp:include page="/jsp/footer.jsp" />
<script src="http://cdn.hcharts.cn/highcharts/highcharts.js"
	type="text/javascript" charset="utf-8"></script>
<script src="http://cdn.hcharts.cn/highcharts/modules/exporting.js"
	type="text/javascript" charset="utf-8"></script>
<script src="${ctx}/js/app/chart.js"></script>
<script src="${ctx}/js/app/customerService.js"></script>
<script src="${ctx}/js/lib/My97DatePicker/WdatePicker.js"></script>
<script>
	$(function() {
		$('dd').find('ul').css("display", "none");
<<<<<<< HEAD
		$('#report-ul').css("display", "block");
=======
		$('#customerService-ul').css("display", "block");
>>>>>>> 9b3b642d9bd2e958022a1e2c925f3db5c693e51e
		var currentPage = sessionStorage.getItem("currentPage");
		if (currentPage) {
			$("#" + currentPage).addClass("active");
		}
	})
</script>
</body>
</html>