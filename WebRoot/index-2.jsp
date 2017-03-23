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
    
    <title>主页</title>
    
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
                    <li  class="active"><a href="osi_index.action" title="Home">主页</a></li>
                   
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
            <section class="banner-wrapper">
              <div class="banner-block container">
                <div class="flexslider">
                  <ul class="slides">
                    <li><img title="Banner" alt="Banner" src="images/banner1.jpg" /></li>
                    <li><img title="Banner" alt="Banner" src="images/banner2.jpg" /></li>
                    <li><img title="Banner" alt="Banner" src="images/banner3.jpg" /></li>
                    <li><img title="Banner" alt="Banner" src="images/banner4.jpg" /></li>
                  </ul>
                </div>
                <ul class="banner-add">
                  <li class="add1"><img title="add1" alt="add1" src="images/static1.jpg" /><</li>
                  <li class="add2"><img title="add2" alt="add2" src="images/banner_add2.png" /></li>
                </ul>
              </div>
            </section>
            <!--Content Block-->
            <section class="content-wrapper">
              <div class="content-container container">
          
                <div class="heading-block">
                  <h1>书籍列表</h1>
                </div>
                <div class="new-product-block">
                <div>
            	<s:actionerror/>  
  				<s:actionmessage/>
  				</div>
                  <ul class="product-grid">
                
                  <s:iterator value="valueList" var="list">
                    <li>
                      <div class="pro-img">
                        <img title="Freature Product" alt="Freature Product" src="<s:property value="#list.book.imgUrl"/>" />
                      </div>
                      <div class="pro-content">
                        <p><s:property value="#list.book.bookName"/></p>
                      </div>
                      <div class="pro-price"><s:property value="#list.book.price"/>积分</div>
                      <div class="pro-btn-block"> 
                      <a class="add-cart left" href="osi_qitem?sellItemId=<s:property value="#list.sellItemId"/>" >查看详情</a> 
                      </div>
                      <div class="pro-link-block"> 
                        <div class="clearfix"></div>
                      </div>  
                    </li>
                    </s:iterator>
                   
                 
                  </ul>
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

<!--快速预览模块-->
<article style="display:none;">
	<section id="quick-view-container" class="quick-view-wrapper">
	<div class="quick-view-container">
		<div class="quick-view-left">
			<h2>Sunglass RB3184</h2>
			<div class="product-img-box">
				<p class="product-image">
					<img src="images/sale_icon_img.png" title="Sale" alt="Sale" class="sale-img" />
					<a href="view.html" title="Image"><img src="images/quick_view_img.png" title="Image" alt="Image" /></a>				</p>
				<ul class="thum-img">
					<li><img  src="images/quick_thum_img.png" title="" alt="" /></li>
					<li><img  src="images/quick_thum_img.png" title="" alt="" /></li>
				</ul>
			</div>
		</div>
		<div class="quick-view-right tabs">
			<ul class="tab-block tabNavigation">
				<li><a class="selected" title="Overview" href="#tabDetail">Overview</a></li>
				<li><a title="Description" href="#tabDes">Description</a></li>
			</ul>
			<div id="tabDetail" class="tabDetail">
            	<div class="first-review">Be the first to review this product</div>
			<div class="price-box">
				<span class="price">$600.00</span>			</div>
			<div class="availability">In stock</div>
			<div class="color-size-block">
				<div class="label-row">
					<label><em>*</em> color</label>
					<span class="required">* Required Fields</span>				</div>
				<div class="select-row">
					<select><option>-- Please Select --</option></select>
				</div>
				<div class="label-row">
					<label><em>*</em> size</label>
				</div>
				<div class="select-row">
					<select><option>-- Please Select --</option></select>
				</div>
			</div>
			<div class="add-to-cart-box">
				<span class="qty-box">
					<label for="qty">Qty:</label>
					<a class="prev" title="" href="#"><img alt="" title="" src="images/qty_prev.png"></a>
					<input type="text" name="qty" class="input-text qty" id="qty" maxlength="12" value="1">
					<a class="next" title="" href="#"><img alt="" title="" src="images/qty_next.png"></a>				</span>
				<button title="Add to Cart" class="form-button"><span>Add to Cart</span></button>
			</div>
            </div>
            <div id="tabDes" class="tabDes">
            	<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas erat odio, suscipit eu porta et, ultricies id sapien. Quisque posuere odio eget lectus suscipit sodales. Sed consequat, leo ut varius scelerisque, augue massa tincidunt est, et tincidunt enim tortor a metus. In sit amet diam in tellus tincidunt mollis. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Morbi hendrerit eleifend tortor, a dapibus tellus suscipit porta. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. In mollis adipiscing mi et volutpat. Aliquam vitae dui nunc. Nulla ac ante eu massa dictum volutpat. Sed mauris sem, posuere sit amet pretium consectetur, ullamcorper vel orci. Aenean feugiat luctus lectus ac hendrerit. Fusce pulvinar, mauris eget sodales suscipit, diam neque condimentum lectus, id imperdiet felis turpis egestas neque. In aliquet orci eget nisl sollicitudin sed gravida tortor commodo</p>
            </div>
		</div>
		<div class="clearfix"></div>
	</div>
</section>
</article>       
            <!--Footer Block-->
            <section class="footer-wrapper">
             
            </section>
</body>
</html>
