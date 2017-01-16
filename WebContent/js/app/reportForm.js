var app = angular
		.module(
				'reportForm',
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
	$routeProvider.when('/reportForm', {
		templateUrl : '/HDR/jsp/reportForm/reportForm.html',
		controller : 'ReportController'
	}).when('/reportForm2', {
		templateUrl : '/HDR/jsp/reportForm/reportForm2.html',
		controller : 'ReportController'
	})
} ]);
app.constant('baseUrl', '/HDR/');
app.factory('services', [ '$http', 'baseUrl', function($http, baseUrl) {
	var services = {};

	// zq从设计部取出项目经理人选zq2016-11-17
	services.selectUsersFromDesign = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'user/selectUsersFromDesign.do',
			data : data
		});
	};

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

							function combine(da, name, arr) {
								var ss = new Object();
								ss.name = name;
								ss.data = arr;
								da.push(ss);
							}
							// 初始化
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
								if ($location.path().indexOf('/reportForm2') == 0) {
								} else if ($location.path().indexOf(
										'/reportForm') == 0) {
									var ar = [
											{
												name : 'Tokyo',
												data : [ 10, 10, 10, 10, 10,
														10, 10, 10, 10, 10, 10,
														10 ]
											},
											{
												name : 'New York',
												data : [ -0.2, 0.8, 5.7, 11.3,
														17.0, 22.0, 24.8, 24.1,
														20.1, 14.1, 8.6, 2.5 ]
											},
											{
												name : 'London',
												data : [ 3.9, 4.2, 5.7, 8.5,
														11.9, 15.2, 17.0, 16.6,
														14.2, 10.3, 6.6, 4.8 ]
											} ];
									var c_name = "china";
									var c_arr = [ 8, 8, 8, 8, 8, 8, 8, 8, 8, 8,
											8, 8 ];
									combine(ar, c_name, c_arr);
									var chart1 = new LineChart({
										elementId : "#lineChart1",
										title : "年自营项目新签合同额分析图（单位：万元）",
										name : "合同占比",
										data : ar,
										lx_Axis : [ 'Jan', 'Feb', 'Mar', 'Apr',
												'May', 'Jun', 'Jul', 'Aug',
												'Sep', 'Oct', 'Nov', 'Dec' ],
										ly_title : 'Temperature (\xB0C)',
										subTitle : 'test'
									});
									chart1.init();
									var har = [
											{
												name : 'Tokyo',
												data : [ 49.9, 71.5, 106.4,
														129.2, 144.0, 176.0,
														135.6, 148.5, 216.4,
														194.1, 95.6, 54.4 ]
											},
											{
												name : 'New York',
												data : [ 83.6, 78.8, 98.5,
														93.4, 106.0, 84.5,
														105.0, 104.3, 91.2,
														83.5, 106.6, 92.3 ]
											},
											{
												name : 'London',
												data : [ 48.9, 38.8, 39.3,
														41.4, 47.0, 48.3, 59.0,
														59.6, 52.4, 65.2, 59.3,
														51.2 ]
											},
											{
												name : 'Berlin',
												data : [ 42.4, 33.2, 34.5,
														39.7, 52.6, 75.5, 57.4,
														60.4, 47.6, 39.1, 46.8,
														51.1 ]
											} ];
									var hname = 'chaina';
									var hdata = [ 65, 65, 65, 65, 65, 65, 65,
											65, 65, 65, 65, 65 ];
									combine(har, hname, hdata);
									var chart2 = new Histogram({
										elementId : '#histogram',
										title : '温度测试',
										subTitle : 'xeshi',
										data : har,
										hx_Axis : [ 'Jan', 'Feb', 'Mar', 'Apr',
												'May', 'Jun', 'Jul', 'Aug',
												'Sep', 'Oct', 'Nov', 'Dec' ],
										hy_title : "zhanguqn"
									});
									chart2.init();
									var chart3Data = [ [ 'Firefox', 45.0 ],
											[ 'IE', 26.8 ], {
												name : 'Chrome',
												y : 12.8,
												sliced : true,
												selected : true
											}, [ 'Safari', 8.5 ],
											[ 'Opera', 6.2 ], [ 'Others', 0.7 ] ];
									var chart3 = new Chart({
										elementId : "#pieChart1",
										title : "自营项目新签合同额分析图（单位：万元）",
										name : "合同占比",
										data : chart3Data
									});
									chart3.init();
								}
							}
							initData();
						} ]);
