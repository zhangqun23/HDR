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
<<<<<<< HEAD
	})
	.when('/linenExpendForm', {
=======
	}).when('/linenExpendForm', {
>>>>>>> 9b3b642d9bd2e958022a1e2c925f3db5c693e51e
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
<<<<<<< HEAD
app.factory('services', [ '$http', 'baseUrl', function($http, baseUrl) {
	var services = {};
	 // wq选择布草消耗
	services.selectExpendFormByLimits = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'expendForm/selectExpendFormBylimits.do',
			data : data
			});
		};
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
	//lwt:查询部门列表
	services.selectDepart = function(data) {
		return $http({
			method : 'post',
			url : baseUrl
					+ 'customerServiceInformation/selectDep.do',
			data : data
		});
	};	
	//lwt:根据部门ID筛选出员工列表
	services.selectStaffByDepId = function(data) {
		return $http({
			method : 'post',
			url : baseUrl
					+ 'customerServiceInformation/selectStaffByDepId.do',
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
							reportForm.limit = {
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
							reportForm.selectExpendFormByLimits = function() {
								if (reportForm.limit.startTime == "") {
									alert("请选择开始时间！");
									return false;
								}
								if (reportForm.limit.endTime == "") {
									alert("请选择截止时间！");
									return false;
								}
								if (compareDateTime(reportForm.limit.startTime,
										reportForm.limit.endTime)) {
									alert("截止时间不能大于开始时间！");
									return false;
								}
								if (reportForm.limit.formType == "") {
									alert("请选择报表类型！");
									return false;
								}
								var expendFormLimit = JSON
										.stringify(expendForm.limit);
								services.selectExpendFormByLimits({
									limit : expendFormLimit
								}).success(function(data) {
									reportForm.expendFormList = data.list;
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
								services.selectDepart().success(
										function(data) {
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
								if (compareDateTime(reportForm.depWorkloadLimit.start_time,
										reportForm.depWorkloadLimit.end_time)) {
									alert("截止时间不能大于开始时间！");
									return false;
								}
								var depWorkloadLimit = JSON
										.stringify(reportForm.depWorkloadLimit);
								services.selectDepWorkload({
									limit : depWorkloadLimit
								}).success(function(data) {
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
								if (compareDateTime(reportForm.staffWorkloadLimit.start_time,
										reportForm.staffWorkloadLimit.end_time)) {
									alert("截止时间不能大于开始时间！");
									return false;
								}
								if (reportForm.staffWorkloadLimit.depart == "") {
									alert("请选择部门！");
									return false;
								}

								var staffWorkloadLimit = JSON
										.stringify(reportForm.staffWorkloadLimit);
								services.selectStaffWorkload({
									limit : staffWorkloadLimit
								}).success(function(data) {
									reportForm.staffWorkloadList = data.list;
									if (data.list.length) {
										reportForm.listIsShow = false;
									} else {
										reportForm.listIsShow = true;
									}
								});
							}

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
								if (compareDateTime(reportForm.typeLimit.start_time,
										reportForm.typeLimit.end_time)) {
									alert("截止时间不能大于开始时间！");
									return false;
								}
								if (reportForm.typeLimit.depart == "") {
									alert("请选择部门！");
									return false;
								}

								var typeLimit = JSON.stringify(reportForm.typeLimit);
								services.selectType({
									limit : typeLimit
								}).success(function(data) {
									reportForm.typeList = data.list;
									if (data.list.length) {
										reportForm.listIsShow = false;
									} else {
										reportForm.listIsShow = true;
									}
								});
							}
							// 初始化
							function initData() {
								console.log("初始化页面信息");
								if ($location.path().indexOf('/staffWorkloadForm') == 0) {
									selectDepart();
//									selectStaffByDepId();
									
								} else if ($location.path().indexOf(
										'/typeForm') == 0) {
									selectDepart();
								}
							}
							initData();
						} ]);
=======

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
							return services;
						} ]);
app.controller('CustomerServiceController', [
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
				if (compareDateTime(reportForm.llimit.startTime,
						reportForm.llimit.endTime)) {
					alert("截止时间不能大于开始时间！");
					return false;
				}
				if (reportForm.llimit.formType == "") {
					alert("请选择报表类型！");
					return false;
				}
				var linenExpendFormLlimit = JSON.stringify(reportForm.llimit);
				services.selectLinenExpendFormByLlimits({
					llimit : linenExpendFormLlimit
				}).success(function(data) {
					// alert(data.list.length);
					reportForm.linenExpendFormList = data.list;
					if (data.list.length) {
						reportForm.listIsShow = false;
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
				if (compareDateTime(reportForm.rlimit.startTime,
						reportForm.rlimit.endTime)) {
					alert("截止时间不能大于开始时间！");
					return false;
				}
				if (reportForm.rlimit.formType == "") {
					alert("请选择报表类型！");
					return false;
				}
				var roomExpendFormRlimit = JSON.stringify(reportForm.rlimit);
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
				if (compareDateTime(reportForm.wlimit.startTime,
						reportForm.wlimit.endTime)) {
					alert("截止时间不能大于开始时间！");
					return false;
				}
				if (reportForm.wlimit.formType == "") {
					alert("请选择报表类型！");
					return false;
				}
				var washExpendFormWlimit = JSON.stringify(reportForm.wlimit);
				services.selectLinenExpendFormByWlimits({
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
				services.selectStaffByDepId().success(function(data) {
					reportForm.staffs = data.list;
				});
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
				if (compareDateTime(reportForm.depWorkloadLimit.start_time,
						reportForm.depWorkloadLimit.end_time)) {
					alert("截止时间不能大于开始时间！");
					return false;
				}
				var depWorkloadLimit = JSON
						.stringify(reportForm.depWorkloadLimit);
				services.selectDepWorkload({
					limit : depWorkloadLimit
				}).success(function(data) {
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
				if (compareDateTime(reportForm.staffWorkloadLimit.start_time,
						reportForm.staffWorkloadLimit.end_time)) {
					alert("截止时间不能大于开始时间！");
					return false;
				}
				if (reportForm.staffWorkloadLimit.depart == "") {
					alert("请选择部门！");
					return false;
				}

				var staffWorkloadLimit = JSON
						.stringify(reportForm.staffWorkloadLimit);
				services.selectStaffWorkload({
					limit : staffWorkloadLimit
				}).success(function(data) {
					reportForm.staffWorkloadList = data.list;
					if (data.list.length) {
						reportForm.listIsShow = false;
					} else {
						reportForm.listIsShow = true;
					}
				});
			}

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
				if (compareDateTime(reportForm.typeLimit.start_time,
						reportForm.typeLimit.end_time)) {
					alert("截止时间不能大于开始时间！");
					return false;
				}
				if (reportForm.typeLimit.depart == "") {
					alert("请选择部门！");
					return false;
				}

				var typeLimit = JSON.stringify(reportForm.typeLimit);
				services.selectType({
					limit : typeLimit
				}).success(function(data) {
					reportForm.typeList = data.list;
					if (data.list.length) {
						reportForm.listIsShow = false;
					} else {
						reportForm.listIsShow = true;
					}
				});
			}
			// 初始化
			function initData() {
				console.log("初始化页面信息");

				if ($location.path().indexOf('/staffWorkloadForm') == 0) {
					selectDepart();
					selectStaffByDepId();

				} else if ($location.path().indexOf('/typeForm') == 0) {
					selectDepart();
				}
			}
			initData();
		} ]);
>>>>>>> 9b3b642d9bd2e958022a1e2c925f3db5c693e51e
