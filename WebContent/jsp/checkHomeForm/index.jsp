<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<jsp:include page="/jsp/top.jsp" />
<section id="checkHomeForm" ng-app="checkHomeForm" class="main">
	<div ng-view></div>
</section>
<jsp:include page="/jsp/left.jsp" />
<jsp:include page="/jsp/footer.jsp" />
<script src="http://cdn.hcharts.cn/highcharts/highcharts.js"
	type="text/javascript" charset="utf-8"></script>
<script src="http://cdn.hcharts.cn/highcharts/modules/exporting.js"
	type="text/javascript" charset="utf-8"></script>
<script src="${ctx}/js/app/chart.js"></script>
<script src="${ctx}/js/app/checkHomeForm.js"></script>
<script src="${ctx}/js/lib/My97DatePicker/WdatePicker.js"></script>
<script>
	$(function() {
		$('dd').find('ul').css("display", "none");
		$('#checkHome-ul').css("display", "block");
		var currentPage = sessionStorage.getItem("currentPage");
		if (currentPage) {
			$("#" + currentPage).addClass("active");
		}
	})
</script>
</body>
</html>