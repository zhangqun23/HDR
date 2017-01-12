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
	}) 
	.when('/roomExpendForm', {
		templateUrl : '/HDR/jsp/customerService/roomExpendForm.html',
		controller : 'CustomerServiceController'
	}) 
	.when('/washExpendForm', {
		templateUrl : '/HDR/jsp/customerService/washExpendForm.html',
		controller : 'CustomerServiceController'
	}) 
} ]);
app.constant('baseUrl', '/HDR/');
app.factory('services', [ '$http', 'baseUrl', function($http, baseUrl) {
	var services = {};

	// lwt:筛选出部门
	services.selectDep = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'CustomerServiceInformation/selectDep.do',
			data : data
		});
	
	    };
	 // wq选择统计时间区间
		services.selectExpendTimeByLimits = function(data) {
			return $http({
				method : 'post',
				url : baseUrl + 'CustomerServiceInformation/selectExpendTimeBylimits.do',
				data : data
				});
			};
			//wq选择报表类型
		    services.getformType = function(data) {
			    return $http({
				    method : 'post',
				    url : baseUrl + 'CustomerServiceInformation/getformType.do',
				    data : data
			    });
	    return services;
	
	};

	return services;
} ]);
app.controller(
				'CustomerServiceController',
				[
						'$scope',
						'services',
						'$location',
						function($scope, services, $location) {
							var reportForm = $scope;
							
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
								//wq界面初始条件设置
							reportForm.limit = {
								formType : "0",
								};
								// wq布草和耗品统计界面设置条件
							reportForm.limit = {
								startTime : "",
								endTime : "",
								formType : ""
							};
					
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
							reportForm.selectExpendTimeByLimits = function() {
								if (linenExpendForm.limit.startTime == "") {
									alert("请选择开始时间！");
									return false;
								}
								if (linenExpendForm.limit.endTime == "") {
									alert("请选择截止时间！");
									return false;
								}
								if ( linenExpendForm.limit.formType == "") {
									alert("请选择报表类型！");
									return false;
								}
								var expendFormLimit = JSON
										.stringify(expendForm.limit);
								services.selectExpendTimeByLimits({
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
							
							// 初始化
							function initData() {
								console.log("初始化页面信息");
								if ($location.path().indexOf('/staffWorkloadForm') == 0) {
									
									
								} else if ($location.path().indexOf(
										'/typeForm') == 0) {
									 
								}else if($location.path().indexOf(
								'/linenExpendForm') == 0){
									
								}
								
							}
							initData();
						}
						
						
						
						]);

		
					

