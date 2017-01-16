var app = angular
		.module(
				'checkOrRobHome',
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
	$routeProvider.when('/robEfficiencyForm', {
		templateUrl : '/HDR/jsp/checkOrRobHome/robEfficiencyForm.html',
		controller : 'CheckOrRobHomeController'
	}).when('/robEffAnalyseForm', {
		templateUrl : '/HDR/jsp/checkOrRobHome/robEffAnalyseForm.html',
		controller : 'CheckOrRobHomeController'
	}).when('/checkEfficiencyForm', {
		templateUrl : '/HDR/jsp/checkOrRobHome/checkEfficiencyForm.html',
		controller : 'CheckOrRobHomeController'
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
<<<<<<< HEAD
	
=======
	// zq获取房间类型列表
	services.getRoomSorts = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'reportForm/getRoomSorts.do',
			data : data
		});
	};
	// zq获取全体成员
	services.selectRoomStaffs = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'reportForm/selectRoomStaffs.do',
			data : data
		});
	};
	// zq获取抢房明细列表
	services.selectRobDetailByLimits = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'checkOrRobHome/selectRobDetailByLimits.do',
			data : data
		});
	}
	// zq获取个人抢房工作效率折线图
	services.selectRobEffAnalyseByLimits = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'checkOrRobHome/selectRobEffAnalyseByLimits.do',
			data : data
		});
	}
>>>>>>> e2f7892bffe266d817c60cd54f15e418c6d68cf0
	return services;
} ]);
app
		.controller(
				'CheckOrRobHomeController',
				[
						'$scope',
						'services',
						'$location',
						function($scope, services, $location) {
							var checkOrRobHome = $scope;
							// zq打扫类型默认值
							checkOrRobHome.cleanTypes = [ {
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
							checkOrRobHome.quarters = [ {
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
<<<<<<< HEAD
							// zq做房用时统计界面设置条件
							checkOrRobHome.limit = {
								startTime : "",
								endTime : "",
								roomType : ""
							};
							
=======
							// 抢房效率统计查询限制条件
							checkRob.reLimit = {
								tableType : "0",
								startTime : "",
								endTime : "",
								roomType : ""
							}
							// 抢房效率分析查询限制条件
							checkRob.reaLimit = {
								checkYear : "",
								quarter : "0",
								roomType : "",
								staffId : ""
							}
							// 获取房间类型名称
							checkRob.sortName = "";
>>>>>>> e2f7892bffe266d817c60cd54f15e418c6d68cf0
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
							// zq折线图公用函数
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

							}
							// zq获取房间类型下拉表
							function selectRoomSorts() {
								services.getRoomSorts().success(function(data) {
									checkOrRobHome.roomTypes = data.list;
								});
							}
							// zq查询客服人员列表
							function selectRoomStaffs() {
								services.selectRoomStaffs().success(
										function(data) {
											checkOrRobHome.staffs = data.list;
										});
							}
							// zq获取所选房间类型
							function getSelectedRoomType(roomSortNo) {
								var type = "";
								for ( var item in checkOrRobHome.roomTypes) {
									if (checkOrRobHome.roomTypes[item].sortNo == roomSortNo) {
										type = checkOrRobHome.roomTypes[item].sortName;
									}
								}
								return type;
							}
							// zq获取所选打扫类型
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
							// zq获取所选用户
							function getSelectedStaff(staffId) {
								var staffName = "";
								for ( var item in checkOrRobHome.staffs) {
									if (checkOrRobHome.staffs[item].staff_id == staffId) {
										staffName = checkOrRobHome.staffs[item].staff_no
												+ checkOrRobHome.staffs[item].staff_name;
									}
								}
								return staffName;
							}
							
							// zq比较两个时间的大小
							function compareDateTime(startDate, endDate) {
								var date1 = new Date(startDate);
								var date2 = new Date(endDate);
								if (date2.getTime() < date1.getTime()) {
									return true;
								} else {
									return false;
								}
							}
<<<<<<< HEAD
=======
							// zq当房型下拉框变化时获取房型名字
							checkRob.getSortNameByNo = function() {
								var no = $("#roomSortType").val();
								checkRob.sortName = getSelectedRoomType(no);
							}
							// zq查询抢房明细表
							checkRob.selectRobByLimits = function() {
								if (checkRob.reLimit.startTime == "") {
									alert("请选择开始时间！");
									return false;
								}
								if (checkRob.reLimit.endTime == "") {
									alert("请选择截止时间！");
									return false;
								}
								if (checkRob.reLimit.roomType == "") {
									alert("请选择房间类型！");
									return false;
								}
								robEfficiencyLimit = JSON
										.stringify(checkRob.reLimit);
								if (checkRob.reLimit.tableType == '0') {
									services.selectRobEfficiencyByLimits({
										limit : robEfficiencyLimit,
										page : nowPage
									}).success(function(data) {
										checkRob.robEfficiencyList = data.list;
									});
								} else {
									services
											.selectRobDetailByLimits({
												limit : robEfficiencyLimit,
												page : nowPage
											})
											.success(
													function(data) {
														checkRob.robDetailList = data.list;
														pageTurn(
																data.totalPage,
																1,
																getRobDetailByLimits);
													});
								}

							}
							// zq抢房明细表换页函数
							function getRobDetailByLimits(p) {
								services.selectRobDetailByLimits({
									limit : robEfficiencyLimit,
									page : p
								}).success(function(data) {
									checkRob.robDetailList = data.list;
								});
							}

							// zq抢房效率折线图
							checkRob.selectRobEffAnalyseByLimits = function() {
								if (checkRob.reaLimit.checkYear == "") {
									alert("请填写查询年份！");
									return false;
								}
								if (checkRob.reaLimit.roomType == "") {
									alert("请选择房间类型！");
									return false;
								}
								if (checkRob.reaLimit.staffId == "") {
									alert("请选择查询员工！");
									return false;
								}
								var robEffAnalyseLimit = JSON
										.stringify(checkRob.reaLimit);
								services
										.selectRobEffAnalyseByLimits({
											limit : robEffAnalyseLimit
										})
										.success(
												function(data) {
													var title = "客房员工 "
															+ " "
															+ getSelectedRoomType(checkRob.reaLimit.roomType)
															+ " " + "抢房效率分析折线图";// 折线图标题显示
													var xAxis = [];// 横坐标显示
													var yAxis = "抢房效率";// 纵坐标显示
													var nowQuarter = reportForm.reaLimit.quarter;// 当前的选择季度
													var lineName = getSelectedStaff(checkRob.reaLimit.staffId)
															+ "员工抢房效率";
													var lineData = [];// 最终传入chart1中的data
													var allAverageData = [];// 全体员工抢房效率的平均Data
													var averageData = [];// 个人平均抢房效率
													var userData = [];
													for ( var item in data.list) {
														userData
																.push(data.list[item]);
													}
													switch (nowQuarter) {
													case '0':
														xAxis = [ '1月', '2月',
																'3月', '4月',
																'5月', '6月',
																'7月', '8月',
																'9月', '10月',
																'11月', '12月' ];
														allAverageData = getAverageData(
																data.allAverWorkEfficiency,
																12);
														averageData = getAverageData(
																data.averWorkEfficiency,
																12);
														break;
													case '1':
														xAxis = [ '1月', '2月',
																'3月' ];
														allAverageData = getAverageData(
																data.allAverWorkEfficiency,
																3);
														averageData = getAverageData(
																data.averWorkEfficiency,
																3);
														break;
													case '2':
														xAxis = [ '4月', '5月',
																'6月' ];
														allAverageData = getAverageData(
																data.allAverWorkEfficiency,
																3);
														averageData = getAverageData(
																data.averWorkEfficiency,
																3);
														break;
													case '3':
														xAxis = [ '7月', '8月',
																'9月' ];
														allAverageData = getAverageData(
																data.allAverWorkEfficiency,
																3);
														averageData = getAverageData(
																data.averWorkEfficiency,
																3);
														break;
													case '4':
														xAxis = [ '10月', '11月',
																'12月' ];
														allAverageData = getAverageData(
																data.allAverWorkEfficiency,
																3);
														averageData = getAverageData(
																data.averWorkEfficiency,
																3);
														break;
													}
													combine(lineData,
															"个人平均抢房效率",
															averageData);
													combine(lineData,
															"全体平均抢房效率",
															allAverageData);
													combine(lineData, lineName,
															userData);
													lineChartForm(lineData,
															"#lineChart1",
															title, xAxis, yAxis);
													$('#chart1-svg')
															.val(
																	$(
																			"#lineChart1")
																			.highcharts()
																			.getSVG());
												});
							}
							// zq根据选择的报表类型显示不同的报表
							checkRob.changeTable = function() {
								var table = $("#tableType").val();
								if (table == '0') {
									$("#efficiencyTable").show();
									$("#detailTable").hide();

								} else {
									$("#efficiencyTable").hide();
									$("#detailTable").show();
								}
							}
>>>>>>> e2f7892bffe266d817c60cd54f15e418c6d68cf0
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

<<<<<<< HEAD
								} 
=======
								} else if ($location.path().indexOf(
										'/robEffAnalyseForm') == 0) {
									selectRoomSorts();
									selectRoomStaffs();
								} else if ($location.path().indexOf(
										'/checkEfficiencyForm') == 0) {
									selectRoomSorts();
								}
>>>>>>> e2f7892bffe266d817c60cd54f15e418c6d68cf0
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
