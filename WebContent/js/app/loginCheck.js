var $loginBtn = $("#login-btn");
var $nameInput = $("#userName");
var $pwdInput = $("#password");

// 输入时验证
$pwdInput.on('click', function() {
	var $userName = $nameInput.val();
	$.ajax({
		url : "/HDR/login/checkUserName.do",
		data : {
			"userName" : $userName
		},
		type : "POST"
	}).done(function(data) {
		if (data == '0') {
			showNameError();
		} else {
			hideNameError();
			hidePwdError();
		}
	})
})

$nameInput.on('click', function() {
	hideNameError();
})

// 提交之前输入验证
$loginBtn.on('click', function(event) {
	$("#login-form").submit();
});

function showNameError() {
	$("#userError").css("visibility", "visible");
}

function hideNameError() {
	$("#userError").css("visibility", "hidden");
}

function showPwdError() {
	$("#pwdError").css("visibility", "visible");
}

function hidePwdError() {
	$("#pwdError").css("visibility", "hidden");
}

/* 点击enter时登录 */
document.onkeydown = function(event) {
	var e = event || window.event || arguments.callee.caller.arguments[0];
	if (e && e.keyCode == 13) { // enter 键
		$loginBtn.click();
	}
};