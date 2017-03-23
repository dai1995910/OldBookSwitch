<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>account_create</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
		<script src="js/jquery-1.8.2.min.js"></script>
<script src="js/common.js"></script>
<script src="js/jquery.easing.1.3.js"></script>
<script src="js/ddsmoothmenu.js"></script>
<script src="js/jquery.flexslider.js"></script>
<script src="js/jquery.elastislide.js"></script>
<script src="js/jquery.jcarousel.min.js"></script>
<script src="js/jquery.accordion.js"></script>
<script src="js/light_box.js"></script>
<script type="text/javascript">$(document).ready(function(){$(".inline").colorbox({inline:true, width:"50%"});});</script>
<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/colors.css">
<link rel="stylesheet" href="css/skeleton.css">
<link rel="stylesheet" href="css/layout.css">
<link rel="stylesheet" href="css/ddsmoothmenu.css"/>
<link rel="stylesheet" href="css/elastislide.css"/>
<link rel="stylesheet" href="css/home_flexslider.css"/>

<link rel="stylesheet" href="css/light_box.css"/>
<script src="js/html5.js"></script>

  </head>
  
  <body>
	<div class="mainContainer sixteen container">
		 <h1 class="logo"><a href="index-2.html" title="Logo">
                  <img title="Logo" alt="Logo" src="images/logo.png" />
        </a></h1>
	<div class="main">
	<form action="user_signUp.action" method="post" >
			<h1 class="page-title">注册用户</h1>
			<div class="fieldset">
	            <h2 class="legend">个人信息</h2>
				<ul class="form-list">
					<li class="fields">
						<div class="customer-name">
							<div class="input-box name-firstname">
								<label for="firstname">用户名<em>*</em></label>
								<input type="text" class="required-entry input-text" title="First Name" name="username">
							</div>
							<div class="input-box name-lastname">
								<label for="number">学号<em>*</em></label>
								<input type="text" class="required-entry input-text" title="Last Name" value="" name="stuId">
							</div>
							<div class="clear"></div>
	
	</div>                </li>
					<li>
						
						<div class="input-box">
							<label class="required" for="contact">联系方式<em>*</em></label>
							<input type="text" class="input-text validate-email required-entry" title="Email Address" value=""name="contact">
						</div>
						<div class="clear"></div>
					</li>
					<li class="control">
						
						<div class="clear"></div>
					</li>
				</ul>
				<h2 class="legend">登录信息</h2>
				<ul class="form-list">
					<li class="fields">
						<div class="customer-name">
							<div class="input-box name-firstname">
								<label for="password">密码<em>*</em></label>
								<input type="password" class="required-entry input-text" title="passWord" value="" name="password">
							</div>
							
							<div class="clear"></div>
						</div>
					</li>
				</ul>
				<div class="buttons-set">
					<p class="required">* 必填</p>
					<a href="index.html" title="Back" class="f-left">&laquo; 返回</a>
					<input type="submit" value="注册" class="colors-btn">
					<div class="clear"></div>
				</div>
        </div>
        </form>
		</div>
		</div>
		
</body>
</html>
