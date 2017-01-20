var app = angular
		.module(
				'customerService',
				[ 'ngRoute' ],
				function($httpProvider) {// ngRoute引入路由依赖
					$httpProvider.defaults.headers.put['Content-Type'] = 'application/x-www-form-urlencoded';
					$httpProvider.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';

					// Override $http service's default transformRequest
					$httpProvider.defaults.transformRequest = [ function(data) {
						/**
						 * The workhorse; converts an object to
						 * x-www-form-urlencoded serialization.
						 * 
						 * @param {Object}
						 *            obj
						 * @return {String}
						 */
						var param = function(obj) {
							var query = '';
							var name, value, fullSubName, subName, subValue, innerObj, i;

							for (name in obj) {
								value = obj[name];

								if (value instanceof Array) {
									for (i = 0; i < value.length; ++i) {
										subValue = value[i];
										fullSubName = name + '[' + i + ']';
										innerObj = {};
										innerObj[fullSubName] = subValue;
										query += param(innerObj) + '&';
									}
								} else if (value instanceof Object) {
									for (subName in value) {
										subValue = value[subName];
										fullSubName = name + '[' + subName
												+ ']';
										innerObj = {};
										innerObj[fullSubName] = subValue;
										query += param(innerObj) + '&';
									}
								} else if (value !== undefined
										&& value !== null) {
									query += encodeURIComponent(name) + '='
											+ encodeURIComponent(value) + '&';
								}
							}

							return query.length ? query.substr(0,
									query.length - 1) : query;
						};

						return angular.isObject(data)
								&& String(data) !== '[object File]' ? param(data)
								: data;
					} ];
				});
app.run([ '$rootScope', '$location', function($rootScope, $location) {
	$rootScope.$on('$routeChangeSuccess', function(evt, next, previous) {
		console.log('路由跳转成功');
		$rootScope.$broadcast('reGetData');
	});
} ]);

// 路由配置
app.config([ '$routeProvider', function($routeProvider) {
	$routeProvider.when('/depWorkloadForm', {
		templateUrl : '/HDR/jsp/customerService/depWorkloadForm.html',
		controller : 'CustomerServiceController'
	}).when('/staffWorkloadForm', {
		templateUrl : '/HDR/jsp/customerService/staffWorkloadForm.html',
		controller : 'CustomerServiceController'
	}).when('/typeForm', {
		templateUrl : '/HDR/jsp/customerService/typeForm.html',
		controller : 'CustomerServiceController'
	}).when('/linenExpendForm', {
		templateUrl : '/HDR/jsp/customerService/linenExpendForm.html',
		controller : 'CustomerServiceController'
	}).when('/linenExpendAnalyse', {
		templateUrl : '/HDR/jsp/customerService/linenExpendAnalyse.html',
		controller : 'CustomerServiceController'
	}).when('/roomExpendForm', {
		templateUrl : '/HDR/jsp/customerService/roomExpendForm.html',
		controller : 'CustomerServiceController'
	}).when('/roomExpendAnalyse', {
		templateUrl : '/HDR/jsp/customerService/roomExpendAnalyse.html',
		controller : 'CustomerServiceController'
	}).when('/washExpendForm', {
		templateUrl : '/HDR/jsp/customerService/washExpendForm.html',
		controller : 'CustomerServiceController'
	}).when('/washExpendAnalyse', {
		templateUrl : '/HDR/jsp/customerService/washExpendAnalyse.html',
		controller : 'CustomerServiceController'
	})
} ]);
app.constant('baseUrl', '/HDR/');

app
		.factory(
				'services',
				[
						'$http',
						'baseUrl',
						function($http, baseUrl) {
							var services = {};

							// lwt:查询部门对客服务工作量
							services.selectDepWorkload = function(data) {
								return $http({
									method : 'post',
									url : baseUrl
											+ 'customerServiceInformation/selectDepWorkload.do',
									data : data
								});
							};
							// lwt:查询员工对客服务工作量
							services.selectStaffWorkload = function(data) {
								return $http({
									method : 'post',
									url : baseUrl
											+ 'customerServiceInformation/selectStaffWorkload.do',
									data : data
								});
							};
							// lwt:查询客房部对客服务类型统计
							services.selectType = function(data) {
								return $http({
									method : 'post',
									url : baseUrl
											+ 'customerServiceInformation/selectType.do',
									data : data
								});
							};
							// lwt:查询部门列表
							services.selectDepart = function(data) {
								return $http({
									method : 'post',
									url : baseUrl
											+ 'customerServiceInformation/selectDep.do',
									data : data
								});
							};
							// lwt:根据部门ID筛选出员工列表
							services.selectStaffByDepId = function(data) {
								return $http({
									method : 'post',
									url : baseUrl
											+ 'customerServiceInformation/selectStaffByDepId.do',
									data : data
								});
							};
							// lwt:筛选出部门
							services.selectDep = function(data) {
								return $http({
									method : 'post',
									url : baseUrl + 'user/selectDep.do',
									data : data
								});
							};

							// wq选择布草消耗
							services.selectLinenExpendFormByLlimits = function(
									data) {
								return $http({
									method : 'post',
									url : baseUrl
											+ 'customerService/selectLinenExpendFormByLlimits.do',
									data : data
								});
							};
							// wq布草用量分析
							services.selectLinenExpendAnalyseByLlimits = function(
									data) {
								return $http({
									method : 'post',
									url : baseUrl
											+ 'customerService/selectLinenExpendAnalyseByLlimits.do',
									data : data
								});
							};
							// wq选择房间耗品
							services.selectRoomExpendFormByRlimits = function(
									data) {
								return $http({
									method : 'post',
									url : baseUrl
											+ 'customerService/selectRoomExpendFormByRlimits.do',
									data : data
								});
							};
							// wq房间耗品用量分析
							services.selectRoomExpendAnalyseByRlimits = function(
									data) {
								return $http({
									method : 'post',
									url : baseUrl
											+ 'customerService/selectRoomExpendAnalyseByRlimits.do',
									data : data
								});
							};
							// wq选择卫生间耗品
							services.selectWashExpendFormByWlimits = function(
									data) {
								return $http({
									method : 'post',
									url : baseUrl
											+ 'customerService/selectWashExpendFormByWlimits.do',
									data : data
								});
							};
							// wq卫生间耗品用量分析
							services.selectWashExpendAnalyseByWlimits = function(
									data) {
								return $http({
									method : 'post',
									url : baseUrl
											+ 'customerService/selectWashExpendAnalyseByWlimits.do',
									data : data
								});
							};
							return services;
						} ]);
app
		.controller(
				'CustomerServiceController',
				[
						'$scope',
						'services',
						'$location',
						function($scope, services, $location) {
							var reportForm = $scope;
							var nowPage = 1;
							// wq布草统计界面设置条件
							reportForm.llimit = {
								startTime : "",
								endTime : "",
								formType : "0",
							};
							// wq布草分析统计界面设置条件
							reportForm.allimit = {
								startTime : "",
								endTime : ""
							};
							// wq房间耗品统计界面设置条件
							reportForm.rlimit = {
								startTime : "",
								endTime : "",
								formType : "0"
							};
							// wq布草分析统计界面设置条件
							reportForm.arlimit = {
								startTime : "",
								endTime : ""
							};
							// wq卫生间耗品统计界面设置条件
							reportForm.wlimit = {
								startTime : "",
								endTime : "",
								formType : "0"
							};
							// wq布草分析统计界面设置条件
							reportForm.awlimit = {
								startTime : "",
								endTime : ""
							};
							// wq获取报表类型名称
							reportForm.formName = "对客服务";
							// wq选择报表类型默认值
							reportForm.formTypes = [ {
								id : 0,
								type : "对客服务"
							}, {
								id : 1,
								type : "离退房"
							}, {
								id : 2,
								type : "过夜房"
							} ];

							function preventDefault(e) {
								if (e && e.preventDefault) {
									// 阻止默认浏览器动作(W3C)
									e.preventDefault();
								} else {
									// IE中阻止函数器默认动作的方式
									window.event.returnValue = false;
									return false;
								}
							}
							// wq根据条件查找布草消耗列表
							reportForm.selectLinenExpendFormByLlimits = function() {
								if (reportForm.llimit.startTime == "") {
									alert("请选择开始时间！");
									return false;
								}
								if (reportForm.llimit.endTime == "") {
									alert("请选择截止时间！");
									return false;
								}
								if (compareDateTime(
										reportForm.llimit.startTime,
										reportForm.llimit.endTime)) {
									alert("截止时间不能大于开始时间！");
									return false;
								}
								if (reportForm.llimit.formType == "") {
									alert("请选择报表类型！");
									return false;
								}
								linenExpendFormLlimit = JSON
										.stringify(reportForm.llimit);
								services
										.selectLinenExpendFormByLlimits({
											llimit : linenExpendFormLlimit,
											page : nowPage
										})
										.success(
												function(data) {
													reportForm.linenExpendFormList = data.list;
													pageTurn(data.totalPage, 1,
															getLinenExpendFormByLlimits);
													if (data.list.length) {
														reportForm.listIsShow = false;
													} else {
														reportForm.listIsShow = true;
													}
												});
							}
							// wq布草统计换页函数
							function getLinenExpendFormByLlimits(p) {
								services.selectLinenExpendFormByLlimits({
									llimit : linenExpendFormLlimit,
									page : p
								}).success(function(data) {
									reportForm.linenExpendFormList = data.list;
								});
							}
							// wq根据条件查找布草分析
							reportForm.selectLinenExpendAnalyseByLlimits = function() {
								if (reportForm.allimit.startTime == "") {
									alert("请选择开始时间！");
									return false;
								}
								if (reportForm.allimit.endTime == "") {
									alert("请选择截止时间！");
									return false;
								}
								if (compareDateTime(
										reportForm.allimit.startTime,
										reportForm.allimit.endTime)) {
									alert("截止时间不能大于开始时间！");
									return false;
								}
								var linenExpendAnalyseLlimit = JSON
										.stringify(reportForm.allimit);
								//alert(allimit.startTime);
								services
										.selectLinenExpendAnalyseByLlimits({
											allimit : linenExpendAnalyseLlimit
										})
										.success(
												function(data) {

													reportForm.typeList = data.list;
													if (data.list.length) {
														reportForm.listIsShow = false;

														if (data.list.length < 15) {
															reportForm.barSize = data.list.length * 80;
														} else {
															reportForm.barSize = 1200;
														}
														$("#bar1")
																.css(
																		'height',
																		reportForm.barSize
																				+ 'px');
														var title = "客房部布草使用量条形图分析";// 条形图标题显示
														var xAxis = [];// 横坐标显示
														var yAxis = "单位:数量";// 纵坐标显示
														var barData = [];// 最终传入bar1中的data
														var linenNum = [];
														for ( var item in data.list) {
															if (data.list[item].good_name != '') {
																xAxis
																		.push(data.list[item].goods_name);
																linenNum
																		.push(parseInt(data.list[item].goods_num));
															}
														}
														combine(barData,
																'布草使用数量',
																linenNum);
														barForm(barData,
																"#bar1", title,
																xAxis, yAxis);
														$('#bar-svg')
																.val(
																		$(
																				"#bar1")
																				.highcharts()
																				.getSVG());
														
													} else {
														reportForm.listIsShow = true;
													}

												});
							}
							// wq根据条件查找房间耗品消耗列表
							reportForm.selectRoomExpendFormByRlimits = function() {
								if (reportForm.rlimit.startTime == "") {
									alert("请选择开始时间！");
									return false;
								}
								if (reportForm.rlimit.endTime == "") {
									alert("请选择截止时间！");
									return false;
								}
								if (compareDateTime(
										reportForm.rlimit.startTime,
										reportForm.rlimit.endTime)) {
									alert("截止时间不能大于开始时间！");
									return false;
								}
								if (reportForm.rlimit.formType == "") {
									alert("请选择报表类型！");
									return false;
								}
								var roomExpendFormRlimit = JSON
										.stringify(reportForm.rlimit);
								services.selectRoomExpendFormByRlimits({
									rlimit : roomExpendFormRlimit
								}).success(function(data) {
									reportForm.roomExpendFormList = data.list;
									if (data.list.length) {
										reportForm.listIsShow = false;
									} else {
										reportForm.listIsShow = true;
									}
								});
							}
							// wq根据条件查找房间耗品分析
							reportForm.selectRoomExpendAnalyseByRlimits = function() {
								if (reportForm.arlimit.startTime == "") {
									alert("请选择开始时间！");
									return false;
								}
								if (reportForm.arlimit.endTime == "") {
									alert("请选择截止时间！");
									return false;
								}
								if (compareDateTime(
										reportForm.arlimit.startTime,
										reportForm.arlimit.endTime)) {
									alert("截止时间不能大于开始时间！");
									return false;
								}
								var roomExpendAnalyseRlimit = JSON
										.stringify(reportForm.arlimit);
								services
										.selectRoomExpendAnalyseByRlimits({
											arlimit : roomExpendAnalyseRlimit
										})
										.success(
												function(data) {
													//reportForm.typeList = data.list;
													if (data.list.length) {
														reportForm.listIsShow = false;

														if (data.list.length < 15) {
															reportForm.barSize = data.list.length * 80;
														} else {
															reportForm.barSize = 1200;
														}
														$("#bar1")
																.css(
																		'height',
																		reportForm.barSize
																				+ 'px');
														var title = "客房部房间耗品使用量条形图分析";// 条形图标题显示
														var xAxis = [];// 横坐标显示
														var yAxis = "单位:数量";// 纵坐标显示
														var barData = [];// 最终传入bar1中的data
														var linenNum = [];
														for ( var item in data.list) {
															if (data.list[item].good_name != '') {
																xAxis
																		.push(data.list[item].goods_name);
																linenNum
																		.push(parseInt(data.list[item].goods_num));
															}
														}
														combine(barData,
																'房间耗品使用数量',
																linenNum);
														barForm(barData,
																"#bar1", title,
																xAxis, yAxis);
														$('#bar-svg')
																.val(
																		$(
																				"#bar1")
																				.highcharts()
																				.getSVG());
													} else {
														reportForm.listIsShow = true;
													}

												});
							}
							// wq根据条件查找卫生间耗品消耗列表
							reportForm.selectWashExpendFormByWlimits = function() {
								if (reportForm.wlimit.startTime == "") {
									alert("请选择开始时间！");
									return false;
								}
								if (reportForm.wlimit.endTime == "") {
									alert("请选择截止时间！");
									return false;
								}
								if (compareDateTime(
										reportForm.wlimit.startTime,
										reportForm.wlimit.endTime)) {
									alert("截止时间不能大于开始时间！");
									return false;
								}
								if (reportForm.wlimit.formType == "") {
									alert("请选择报表类型！");
									return false;
								}
								var washExpendFormWlimit = JSON
										.stringify(reportForm.wlimit);
								services.selectWashExpendFormByWlimits({
									wlimit : washExpendFormWlimit
								}).success(function(data) {
									reportForm.washExpendFormList = data.list;
									if (data.list.length) {
										reportForm.listIsShow = false;
									} else {
										reportForm.listIsShow = true;
									}
								});
							}
							// wq根据条件查找卫生间耗品分析
							reportForm.selectWashExpendAnalyseByWlimits = function() {
								if (reportForm.wrlimit.startTime == "") {
									alert("请选择开始时间！");
									return false;
								}
								if (reportForm.wrlimit.endTime == "") {
									alert("请选择截止时间！");
									return false;
								}
								if (compareDateTime(
										reportForm.wrlimit.startTime,
										reportForm.wrlimit.endTime)) {
									alert("截止时间不能大于开始时间！");
									return false;
								}
								var washExpendAnalyseWlimit = JSON
										.stringify(reportForm.wrlimit);
								services
										.selectWashExpendAnalyseByWlimits({
											wrlimit : washExpendAnalyseWlimit
										})
										.success(
												function(data) {
													//reportForm.typeList = data.list;
													if (data.list.length) {
														reportForm.listIsShow = false;

														if (data.list.length < 15) {
															reportForm.barSize = data.list.length * 80;
														} else {
															reportForm.barSize = 1200;
														}
														$("#bar1")
																.css(
																		'height',
																		reportForm.barSize
																				+ 'px');
														var title = "客房部卫生间耗品使用量条形图分析";// 条形图标题显示
														var xAxis = [];// 横坐标显示
														var yAxis = "单位:数量";// 纵坐标显示
														var barData = [];// 最终传入bar1中的data
														var linenNum = [];
														for ( var item in data.list) {
															if (data.list[item].goods_name != '') {
																xAxis
																		.push(data.list[item].goods_name);
																linenNum
																		.push(parseInt(data.list[item].goods_num));
															}
														}
														combine(barData,
																'卫生间耗品使用数量',
																linenNum);
														barForm(barData,
																"#bar1", title,
																xAxis, yAxis);
														$('#bar-svg')
																.val(
																		$(
																				"#bar1")
																				.highcharts()
																				.getSVG());
													} else {
														reportForm.listIsShow = true;
													}

												});
							}
							// wq传入报表名称
							reportForm.getFormNameByNo = function() {
								var no = $("#linenFormType").val();
								reportForm.formName = getSelectedFormName(no);
							}
							// wq获取所选报表名称
							function getSelectedFormName(formType) {
								var type = "";
								switch (formType) {
								case '0':
									type = "对客服务";
									break;
								case '1':
									type = "离退房";
									break;
								case '2':
									type = "过夜房";
									break;
								}
								return type;
							}
							// wq比较两个时间的大小
							function compareDateTime(startDate, endDate) {
								var date1 = new Date(startDate);
								var date2 = new Date(endDate);
								if (date2.getTime() < date1.getTime()) {
									return true;
								} else {
									return false;
								}
							}
							// wq换页
							function pageTurn(totalPage, page, Func) {
								var $pages = $(".tcdPageCode");
								if ($pages.length != 0) {
									$(".tcdPageCode").createPage({
										pageCount : totalPage,
										current : page,
										backFn : function(p) {
											Func(p);
											nowPage = p;// 暂时没用，留待将来换页改序号使用
										}
									});
								}
							}
							// wq扇形图图公用函数
							function pieChartForm(elementId, title, dataName,
									data) {
								var chart1 = new Chart({
									elementId : elementId,
									title : title,
									data : data,
									name : dataName
								});
								chart1.init();
							}
							// lwt对客服务部门设置条件
							reportForm.depWorkloadLimit = {
								start_time : "",
								end_time : ""
							};
							// lwt对客服务员工工作量统计设置条件
							reportForm.staffWorkloadLimit = {
								start_time : "",
								end_time : "",
								depart : ""
							}
							// lwt对客服务类型统计
							reportForm.typeLimit = {
								start_time : "",
								end_time : "",
								depart : ""

							}

							// lwt查询部门列表
							function selectDepart() {
								services.selectDepart().success(function(data) {
									reportForm.depts = data;
								});
							}
							// lwt根据部门id筛选出员工列表
							function selectStaffByDepId() {
								services.selectStaffByDepId().success(
										function(data) {
											reportForm.staffs = data.list;
										});
							}
							// lwt部门名称
							reportForm.deptName = "";
							// lwt当部门下拉框变化时获取部门名字
							reportForm.getDeptNameById = function() {
								var Id = $("#dept").val();
								reportForm.deptName = getDeptName(Id);
							}
							// lwt根据deptId获取部门名称
							function getDeptName(deptId) {
								var type = "";
								for ( var item in reportForm.depts) {
									if (reportForm.depts[item].departmentId == deptId) {
										type = reportForm.depts[item].departmentName;
									}
								}
								return type;
							}

							// lwt根据条件查找部门对客服务工作量
							reportForm.selectDepWorkload = function() {
								if (reportForm.depWorkloadLimit.start_time == "") {
									alert("请选择开始时间！");
									return false;
								}
								if (reportForm.depWorkloadLimit.end_time == "") {
									alert("请选择截止时间！");
									return false;
								}
								if (compareDateTime(
										reportForm.depWorkloadLimit.start_time,
										reportForm.depWorkloadLimit.end_time)) {
									alert("截止时间不能大于开始时间！");
									return false;
								}
								$(".overlayer").fadeIn(200);
								$(".tipLoading").fadeIn(200);
								var depWorkloadLimit = JSON
										.stringify(reportForm.depWorkloadLimit);
								services.selectDepWorkload({
									limit : depWorkloadLimit
								}).success(function(data) {
									$(".overlayer").fadeOut(200);
									$(".tipLoading").fadeOut(200);
									reportForm.depWorkloadList = data.list;
									if (data.list.length) {
										reportForm.listIsShow = false;
									} else {
										reportForm.listIsShow = true;
									}
								});
							}
							// lwt根据条件查找员工对客服务工作量
							reportForm.selectStaffWorkload = function() {
								if (reportForm.staffWorkloadLimit.start_time == "") {
									alert("请选择开始时间！");
									return false;
								}
								if (reportForm.staffWorkloadLimit.end_time == "") {
									alert("请选择截止时间！");
									return false;
								}
								if (compareDateTime(
										reportForm.staffWorkloadLimit.start_time,
										reportForm.staffWorkloadLimit.end_time)) {
									alert("截止时间不能大于开始时间！");
									return false;
								}
								if (reportForm.staffWorkloadLimit.depart == "") {
									alert("请选择部门！");
									return false;
								}
								$(".overlayer").fadeIn(200);
								$(".tipLoading").fadeIn(200);

								var staffWorkloadLimit = JSON
										.stringify(reportForm.staffWorkloadLimit);
								services.selectStaffWorkload({
									limit : staffWorkloadLimit
								}).success(function(data) {
									$(".overlayer").fadeOut(200);
									$(".tipLoading").fadeOut(200);
									reportForm.staffWorkloadList = data.list;
									if (data.list.length) {
										reportForm.listIsShow = false;
									} else {
										reportForm.listIsShow = true;
									}
								});
							}
							reportForm.barSize = "";
							// lwt根据条件查找服务类型统计
							reportForm.selectType = function() {
								if (reportForm.typeLimit.start_time == "") {
									alert("请选择开始时间！");
									return false;
								}
								if (reportForm.typeLimit.end_time == "") {
									alert("请选择截止时间！");
									return false;
								}
								if (compareDateTime(
										reportForm.typeLimit.start_time,
										reportForm.typeLimit.end_time)) {
									alert("截止时间不能大于开始时间！");
									return false;
								}
								if (reportForm.typeLimit.depart == "") {
									alert("请选择部门！");
									return false;
								}
								$(".overlayer").fadeIn(200);
								$(".tipLoading").fadeIn(200);
								var typeLimit = JSON
										.stringify(reportForm.typeLimit);
								services
										.selectType({
											limit : typeLimit
										})
										.success(
												function(data) {
													$(".overlayer")
															.fadeOut(200);
													$(".tipLoading").fadeOut(
															200);

													if (data.list.length == 1) {
														reportForm.typeList = '';
														reportForm.listIsShow = true;
														reportForm.barIsShow = false;
													} else {
														reportForm.barIsShow = true;
														reportForm.typeList = data.list;
														reportForm.listIsShow = false;
														if (data.list.length < 15) {
															reportForm.barSize = data.list.length * 80;
														} else {
															reportForm.barSize = 1200;
														}
														$("#bar1")
																.css(
																		'height',
																		reportForm.barSize
																				+ 'px');
														var title = getSelectedDepartName(reportForm.typeLimit.depart)
																+ "对客服务类型条形图分析";// 条形图标题显示
														var xAxis = [];// 横坐标显示
														var yAxis = "单位:数量";// 纵坐标显示
														var barData = [];// 最终传入bar1中的data
														var serviceLoads = [];
														for ( var item in data.list) {
															if (data.list[item].serviceType != '') {
																xAxis
																		.push(data.list[item].serviceType);
																serviceLoads
																		.push(parseInt(data.list[item].serviceLoad));
															}
														}
														combine(barData,
																'服务完成数量',
																serviceLoads);
														barForm(barData,
																"#bar1", title,
																xAxis, yAxis);
														$('#bar-svg')
																.val(
																		$(
																				"#bar1")
																				.highcharts()
																				.getSVG());
													}

												});
							}
							// lwt为生成条形图拼data
							function combine(da, name, arr) {
								var ss = new Object();
								ss.name = name;
								ss.data = arr;
								da.push(ss);
							}
							// lwt条形图公用函数
							function barForm(data, elementId, title, x_Axis,
									y_title) {
								var bar1 = new BarChart({
									elementId : elementId,
									title : title,
									subTitle : '',
									hx_Axis : x_Axis,
									hy_title : y_title,
									unit : '',
									data : data
								});
								bar1.init();
							}
							// lwt获取所选部门的名称
							function getSelectedDepartName(departId) {
								var departName = "";
								for ( var item in reportForm.depts) {
									if (reportForm.depts[item].departmentId == departId) {
										departName = reportForm.depts[item].departmentName;
									}
								}
								return departName;
							}
							// 初始化
							function initData() {
								console.log("初始化页面信息");

								if ($location.path().indexOf(
										'/staffWorkloadForm') == 0) {
									selectDepart();
									selectStaffByDepId();

								} else if ($location.path()
										.indexOf('/typeForm') == 0) {
									selectDepart();
								}
							}
							initData();
						} ]);
// lwt:小数转换为百分数过滤器
app.filter('numPercent', function() {
	return function(input) {
		var number = (input * 100).toFixed(2) + "%";
		return number;
	}
});
