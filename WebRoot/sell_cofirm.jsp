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
    
    <title>My JSP 'sell_cofirm.jsp' starting page</title>
    
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
            <!--Header Block-->
            <div class="header-wrapper">
              <header class="container">
                <div class="head-right">
          
                           
              
                      <ul class="top-nav">
                     		 <li><a href="user_myAccount.action" title="My Account">我的账户</a></li>
                            <li class="my-wishlist"><a href="osi_getUserReqOrder.action" title="My Wishlist">我的需求单</a></li>
                            <li class="contact-us"><a href="osi_getUserItem.action" title="my goods">我的商品</a></li>
                            
                            <li class="log-in"><a href="trade_myOrders.action" title="my orders">我的订单</a></li>
                      </ul>
                    <ul class="currencyBox">
                        <li id="header_currancy" class="currency">欢迎你<s:property value="#session.loginedUser.username"/>
                          <div id="currancyBox" class="currency_detial"><a href="user_loginOut.action">登出</a> </div>
                        </li>
                    </ul>
                <section class="header-bottom">
                  <form action="osi_fuzzyQuery" method="get">
                  	<div class="select-row" style="margin-bottom:5px">
                  		选择查询条件：
                  		<select name="isByName">
                    		<option value="true">书名</option>
                    		<option value="false">ISBN</option>
                    	</select>
                  	</div>
                    <div class="search-block">
                      <input type="text" value="" name="searchText"/>
                      <input type="submit" value="search" title="Search" />
                    </div>
                    </form>
                  </section>
                </div>
                <h1 class="logo"><a href="osi_index.action" title="Logo">
                  <img title="Logo" alt="Logo" src="images/logo.png" />
                  </a></h1>
                <nav id="smoothmenu1" class="ddsmoothmenu mainMenu">
                  <ul id="nav">
                    <li><a href="osi_index.action" title="Home">主页</a></li>
                   
                  	<li><a href="osi_reqIndex.action" title="demand">需求市场</a></li>
                  
                  
                  </ul>
                </nav>
                
                <div class="mobMenu">
            <h1>
            <span>Menu</span>
            <a class="menuBox highlight" href="javascript:void(0);">ccc</a>
            <span class="clearfix"></span>
            </h1>
            
            </div>
                
              </header>
            </div>
            <!--Banner Block-->
            <section class="content-wrapper">
            	<div class="content-container container">
            		
            			
            	<!--   warp-->
            <div class="main">
			<div class="product-info-box">
				<div class="product-essential">
					<div class="product-img-box">
						<p class="product-image-zoom">
							<img src='<s:property value="#request.resultBook.imgUrl"/>'  alt="Image" title="Image" />
						</p>
						
						
				  </div>
					<div class="product-shop">
						<h3 class="product-name"><s:property value="#request.resultBook.bookName"/></h3>
						<div class="price-box">
                		    <span class="price"><s:property value="#request.resultBook.price"/>积分</span>
							
			          </div>
						<div class="model-block">
							<p>
								<span>ISBN:</span><s:property value="#request.resultBook.ISBN"/>
						  </p>
							
						</div>
						
						<form action="osi_publicSell" method="post">
						<div class="model-block">
							<p>
								<span>请选择书的新旧程度</span>
								<select name="degree">
								<option value="全新">全新</option>
								<option value="新">新</option>
								<option value="旧">旧</option>
								</select>
						  </p>
							
						</div>
						
						<div class="add-to-cart-box">
							
                           
							<button class="form-button" title="Add to Cart" type="submit">确认上架</button>
							
						</div>
						</form>
				  </div>
				</div>
			</div>	
		</div>
		
            	
            	
           <div class="news-letter-container">
                  <div class="free-shipping-block">
                    <h1>祝你愉快</h1>
                    <p>&nbsp;</p>
                  </div>
                
           </div>
           </div>
            </section>
            </div> 
    <!--Quick view Block-->
<script type="text/javascript">
jQuery (function(){
	var tabContainers=jQuery('div.tabs > div');
	tabContainers.hide().filter(':first').show();
	jQuery('div.tabs ul.tabNavigation a').click(function(){
		tabContainers.hide();
		tabContainers.filter(this.hash).show();
		jQuery('div.tabs ul.tabNavigation a').removeClass('selected');
		jQuery(this).addClass('selected');
		return false;
		}).filter(':first').click();
	});
</script>

   
            <!--Footer Block-->
            <section class="footer-wrapper">
             
            </section>
</body>
</html>
