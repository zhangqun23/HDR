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
	}).when('/roomExpendForm', {
		templateUrl : '/HDR/jsp/customerService/roomExpendForm.html',
		controller : 'CustomerServiceController'
	}).when('/washExpendForm', {
		templateUrl : '/HDR/jsp/customerService/washExpendForm.html',
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

							// lwt:筛选出部门
							services.selectDep = function(data) {
								return $http({
									method : 'post',
									url : baseUrl + 'user/selectDep.do',
									data : data
								});
							};
							// wq选择布草消耗
							services.selectExpendFormByLimits = function(data) {
								return $http({
									method : 'post',
									url : baseUrl
											+ 'expendForm/selectExpendFormBylimits.do',
									data : data
								});
							};
							services.selectLinenExpendFormByLimits = function(
									data) {
								return $http({
									method : 'post',
									url : baseUrl
											+ 'customerService/selectLinenExpendFormByLlimits.do',
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
							// wq布草统计界面设置条件
							reportForm.llimit = {
								startTime : "",
								endTime : "",
								formType : ""
							};
							// wq房间耗品统计界面设置条件
							reportForm.rlimit = {
								startTime : "",
								endTime : "",
								formType : ""
							};
							// wq卫生间耗品统计界面设置条件
							reportForm.wlimit = {
								startTime : "",
								endTime : "",
								formType : ""
							};
							// wq选择报表类型默认值
							reportForm.formTypes = [ {
								id : 0,
								type : "过夜房"
							}, {
								id : 1,
								type : "离退房"
							}, {
								id : 2,
								type : "对客服务"
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
							reportForm.selectLinenExpendFormByLimits = function() {
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
								var linenExpendFormLimit = JSON
										.stringify(reportForm.llimit);
								services.selectLinenExpendFormByLimits({
									llimit : linenExpendFormLimit
								}).success(function(data) {
									reportForm.linenExpendFormList = data.list;
									if (data.list.length) {
										reportForm.listIsShow = false;
									} else {
										reportForm.listIsShow = true;
									}
								});
							}
							// wq根据条件查找房间耗品消耗列表
							reportForm.selectRoomExpendFormByLimits = function() {
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
								var roomExpendFormLimit = JSON
										.stringify(roomExpendForm.rlimit);
								services.selectRoomExpendFormByLimits({
									rlimit : roomExpendFormLimit
								}).success(function(data) {
									reportForm.roomExpendFormList = data.list;
									if (data.list.length) {
										reportForm.listIsShow = false;
									} else {
										reportForm.listIsShow = true;
									}
								});
							}
							// wq根据条件查找卫生间耗品消耗列表
							reportForm.selectWashExpendFormByLimits = function() {
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
								var washExpendFormLimit = JSON
										.stringify(washExpendForm.llimit);
								services.selectLinenExpendFormByLimits({
									wlimit : washExpendFormLimit
								}).success(function(data) {
									reportForm.washExpendFormList = data.list;
									if (data.list.length) {
										reportForm.listIsShow = false;
									} else {
										reportForm.listIsShow = true;
									}
								});
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
							// 初始化
							function initData() {
								console.log("初始化页面信息");
								if ($location.path().indexOf(
										'/staffWorkloadForm') == 0) {

								} else if ($location.path()
										.indexOf('/typeForm') == 0) {

								}
							}
							initData();
						} ]);
