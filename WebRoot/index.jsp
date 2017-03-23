<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="s" uri="/struts-tags" %> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>login</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link rel="stylesheet" type="text/css" href="css/login.css">
	
  </head>
  
  <body>
   <section class="container">
    <div class="login">
      <h1>大学旧书管理系统</h1>
      <form method="post" action="user_login">
        <p><input type="text" name="username"   placeholder="用户名"></p>
        <p><input type="password" name="password"  placeholder="密码"></p>
        
        <p class="submit"><input type="submit" name="commit" value="登陆"></p>
      </form>
       <s:actionerror/>  
  <s:actionmessage/> 
    </div>

    <div class="login-help">
      <p>没有注册? <a href="account_create.jsp">点击这里注册</a>.</p>
    </div>
  </section>
  
 
  </body>
</html>
