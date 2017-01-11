var app = angular
		.module(
				'routineTaskForm',
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
	$routeProvider.when('/workloadForm', {
		templateUrl : '/HDR/jsp/routineTaskForm/workloadForm.html',
		controller : 'ReportController'
	}).when('/workloadAnalysis', {
		templateUrl : '/HDR/jsp/routineTaskForm/workloadAnalysis.html',
		controller : 'ReportController'
	}).when('/workloadSaturationForm', {
		templateUrl : '/HDR/jsp/routineTaskForm/workloadSaturationForm.html',
		controller : 'ReportController'
	}).when('/workHouseForm', {
		templateUrl : '/HDR/jsp/routineTaskForm/workHouseForm.html',
		controller : 'ReportController'
	}).when('/workHouseAnalyseForm', {
		templateUrl : '/HDR/jsp/routineTaskForm/workHouseAnalyseForm.html',
		controller : 'ReportController'
	})
} ]);
app.constant('baseUrl', '/HDR/');
app.factory('services', [ '$http', 'baseUrl', function($http, baseUrl) {
	var services = {};
	// zq获取做房用时列表
	services.selectWorkHouseByLimits = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'workHouse/selectWorkHouseBylimits.do',
			data : data
		});
	};
	// zq获取房间类型列表
	services.getRoomSorts = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'reportForm/getRoomSorts.do',
			data : data
		});
	};
	// 获取全体成员
	services.selectRoomStaffs = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'reportForm/selectRoomStaffs.do',
			data : data
		});
	};
	// zq获取单个用户的做房用时
	services.selectUserWorkHouseByLimits = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'workHouse/selectUserWorkHouseByLimits.do',
			data : data
		});
	}
	return services;
} ]);
app
		.controller(
				'ReportController',
				[
						'$scope',
						'services',
						'$location',
						function($scope, services, $location) {
							var reportForm = $scope;
							// zq打扫类型默认值
							reportForm.cleanTypes = [ {
								id : 0,
								type : "抹尘房"
							}, {
								id : 1,
								type : "离退房"
							}, {
								id : 2,
								type : "过夜房"
							} ];
							// zq查找时间季度默认值
							reportForm.quarters = [ {
								id : 0,
								type : "全年"
							}, {
								id : 1,
								type : "一季度"
							}, {
								id : 2,
								type : "二季度"
							}, {
								id : 3,
								type : "三季度"
							}, {
								id : 4,
								type : "四季度"
							} ];
							// zq做房用时统计界面设置条件
							reportForm.limit = {
								startTime : "",
								endTime : "",
								roomType : ""
							};
							// zq做房用时分析界面设置条件
							reportForm.whalimit = {
								checkYear : "",
								quarter : "0",
								roomType : "",
								cleanType : "0",
								staffId : ""
							};
							// zq公共函数始
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
							// zq为生成图表拼data
							function combine(da, name, arr) {
								var ss = new Object();
								ss.name = name;
								ss.data = arr;
								da.push(ss);
							}
							// zq公共函数终
							// zq根据条件查找做房时间列表
							reportForm.selectWorkHouseByLimits = function() {
								if (reportForm.limit.startTime == "") {
									alert("请选择开始时间！");
									return false;
								}
								if (reportForm.limit.endTime == "") {
									alert("请选择截止时间！");
									return false;
								}
								if ( reportForm.limit.roomType == "") {
									alert("请选择房间类型！");
									return false;
								}
								var workHouseLimit = JSON
										.stringify(reportForm.limit);
								services.selectWorkHouseByLimits({
									limit : workHouseLimit
								}).success(function(data) {
									reportForm.workHouseList = data.list;
									if (data.list.length) {
										reportForm.listIsShow = false;
									} else {
										reportForm.listIsShow = true;
									}
								});
							}
							// zq根据查询条件绘制员工做房用时分析折线图
							reportForm.selectUserWorkHouseByLimits = function() {
								if (reportForm.whalimit.checkYear == "") {
									alert("请填写查询年份！");
									return false;
								}
								if (reportForm.whalimit.roomType == "") {
								alert("请选择房间类型！");
								return false;
							}
								if (reportForm.whalimit.staffId == "") {
								alert("请选择查询员工！");
								return false;
							}
								var userWorkHouseLimit = JSON
										.stringify(reportForm.whalimit);
								services
										.selectUserWorkHouseByLimits({
											limit : userWorkHouseLimit
										})
										.success(
												function(data) {
													var title = "客房员工 "
															+ " "
															+ getSelectedRoomType(reportForm.whalimit.roomType)
															+ " "
															+ getSelectedCleanType(reportForm.whalimit.cleanType)
															+ " " + "做房用时分析折线图";// 折线图标题显示
													var xAxis = [];// 横坐标显示
													var yAxis = "做房用时/分钟";// 纵坐标显示
													var nowQuarter = reportForm.whalimit.quarter;// 当前的选择季度
													var lineName = getSelectedStaff(reportForm.whalimit.staffId)
															+ "员工做房用时";
													var lineData = [];// 最终传入chart1中的data
													allAverageData = [];// 全体员工做房时间的平均Data
													averageData = [];// 个人平均用时
													var userData = [];
													for ( var item in data.list) {
														userData
																.push(data.list[item].use_time);
													}
													switch (nowQuarter) {
													case 0:
														xAxis = [ '1月', '2月',
																'3月', '4月',
																'5月', '6月',
																'7月', '8月',
																'9月', '10月',
																'11月', '12月' ];
														allAverageData = getAverageData(
																data.allAverWorkTime,
																12);
														averageData = getAverageData(
																data.averWorkTime,
																12);
														break;
													case '1':
														xAxis = [ '1月', '2月',
																'3月' ];
														allAverageData = getAverageData(
																data.allAverWorkTime,
																3);
														averageData = getAverageData(
																data.averWorkTime,
																3);
														break;
													case '2':
														xAxis = [ '4月', '5月',
																'6月' ];
														allAverageData = getAverageData(
																data.allAverWorkTime,
																3);
														averageData = getAverageData(
																data.averWorkTime,
																3);
														break;
													case '3':
														xAxis = [ '7月', '8月',
																'9月' ];
														allAverageData = getAverageData(
																data.allAverWorkTime,
																3);
														averageData = getAverageData(
																data.averWorkTime,
																3);
														break;
													case '4':
														xAxis = [ '10月', '11月',
																'12月' ];
														allAverageData = getAverageData(
																data.allAverWorkTime,
																3);
														averageData = getAverageData(
																data.averWorkTime,
																3);
														break;
													}
													combine(lineData, "个人平均用时",
															averageData);
													combine(lineData, "全体平均用时",
															allAverageData);
													combine(lineData, lineName,
															userData);
													lineChartForm(lineData,
															"#lineChart1",
															title, xAxis, yAxis);
												});
							}
							// zq生成平均值的数组
							function getAverageData(data, number) {
								var arr = [];
								for (var i = 0; i < number; i++) {
									arr.push(data);
								}
								return arr;

							}

							// zq换页
							function pageTurn(totalPage, page, Func) {
								var $pages = $(".tcdPageCode");
								if ($pages.length != 0) {
									$(".tcdPageCode").createPage({
										pageCount : totalPage,
										current : page,
										backFn : function(p) {
											Func(p);
										}
									});
								}
							}
							// 折线图公用函数
							function lineChartForm(data, elementId, title,
									lx_Axis, ly_title) {
								var chart1 = new LineChart({
									elementId : elementId,
									title : title,
									data : data,
									lx_Axis : lx_Axis,
									ly_title : ly_title,
									subTitle : ''
								});
								chart1.init();
								$('#chart1-svg').val(
										$("#lineChart1").highcharts().getSVG());
							}
							// 获取房间类型下拉表
							function selectRoomSorts() {
								services.getRoomSorts().success(function(data) {
									reportForm.roomTypes = data.list;
								});
							}
							// 查询客服人员列表
							function selectRoomStaffs() {
								services.selectRoomStaffs().success(
										function(data) {
											reportForm.staffs = data.list;
										});
							}
							// 获取所选房间类型
							function getSelectedRoomType(roomSortId) {
								var type = "";
								for ( var item in reportForm.roomTypes) {
									if (reportForm.roomTypes[item].sortId == roomSortId) {
										type = reportForm.roomTypes[item].sortName;
									}
								}
								return type;
							}
							// 获取所选打扫类型
							function getSelectedCleanType(cleanTypeId) {
								var type = "";
								switch (cleanTypeId) {
								case '0':
									type = "抹尘房";
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
							// 获取所选用户
							function getSelectedStaff(staffId) {
								var staffName = "";
								for ( var item in reportForm.staffs) {
									if (reportForm.staffs[item].staff_id == staffId) {
										staffName = reportForm.staffs[item].staff_no
												+ reportForm.staffs[item].staff_name;
									}
								}
								return staffName;
							}

							reportForm.xianshi = function() {
								var title = "客房员工 "
										+ " "
										+ getSelectedRoomType(reportForm.whalimit.roomType)
										+ " "
										+ getSelectedCleanType(reportForm.whalimit.cleanType)
										+ " " + "做房用时分析折线图";// 折线图标题显示
								var xAxis = [];// 横坐标显示
								var yAxis = "做房用时/分钟";// 纵坐标显示
								var nowQuarter = reportForm.whalimit.quarter;// 当前的选择季度
								var lineName = getSelectedStaff(reportForm.whalimit.staffId)
										+ "员工做房用时";
								var lineData = [];// 最终传入chart1中的data
								allAverageData = [];// 全体员工做房时间的平均Data
								averageData = [];// 个人平均用时
								switch (nowQuarter) {
								case '0':
									xAxis = [ '1月', '2月', '3月', '4月', '5月',
											'6月', '7月', '8月', '9月', '10月',
											'11月', '12月' ];
									allAverageData = getAverageData(23, 12);
									averageData = getAverageData(22, 12);
									break;
								case '1':
									xAxis = [ '1月', '2月', '3月' ];
									allAverageData = getAverageData(22, 3);
									averageData = getAverageData(24, 3);
									break;
								case '2':
									xAxis = [ '4月', '5月', '6月' ];
									allAverageData = getAverageData(24, 3);
									averageData = getAverageData(23, 3);
									break;
								case '3':
									xAxis = [ '7月', '8月', '9月' ];
									allAverageData = getAverageData(22, 3);
									averageData = getAverageData(21, 3);
									break;
								case '4':
									xAxis = [ '10月', '11月', '12月' ];
									allAverageData = getAverageData(22, 3);
									averageData = getAverageData(25, 3);
									break;
								}
								console.log("hengzhou" + xAxis);
								console.log("heng" + averageData);
								console.log("zhou" + allAverageData);
								combine(lineData, "个人平均用时", averageData);
								combine(lineData, "全体平均用时", allAverageData);
								lineChartForm(lineData, "#lineChart1", title,
										xAxis, yAxis);

							}
							// zq初始化
							function initData() {
								console.log("初始化页面信息");
								Highcharts
										.wrap(
												Highcharts.Chart.prototype,
												'getSVG',
												function(proceed) {
													return proceed
															.call(this)
															.replace(
																	/(fill|stroke)="rgba([ 0-9]+,[ 0-9]+,[ 0-9]+),([ 0-9\.]+)"/g,
																	'$1="rgb($2)" $1-opacity="$3"');
												});
								if ($location.path().indexOf('/workHouseForm') == 0) {
									selectRoomSorts();

								} else if ($location.path().indexOf(
										'/workHouseAnalyseForm') == 0) {
									selectRoomStaffs();
									selectRoomSorts();
									allAverageData = [];// 全体员工做房时间的平均Data

								} else if ($location.path().indexOf(
										'/reportForm') == 0) {

								}
							}
							initData();
							// zq控制年
							var $dateFormat = $(".dateFormatForY");
							var dateRegexpForY = /^[0-9]{4}$/;
							$(".dateFormatForY").blur(
									function() {
										if (this.value.trim() != "") {
											if (!dateRegexpForY
													.test(this.value)) {
												$(this).parent().children(
														"span").css('display',
														'inline');
											} else {
												var month = parseInt(this.value
														.split("-")[1]);
												if (month > 12) {
													$(this).parent().children(
															"span")
															.css('display',
																	'inline');
												}
											}
										}
									});
							$(".dateFormatForY").click(
									function() {
										$(this).parent().children("span").css(
												'display', 'none');
									});

						} ]);
