<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
 <c:set var="context" value="${pageContext.request.contextPath}" />
 <!DOCTYPE html>
<html lang="vi">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta name="description" content="Giải pháp phần mềm hữu ích cho các địa điểm ăn uống, ẩm thực tại Đà Nẵng - Nơi liên kết hàng ngàn địa điểm về ẩm thực, giải trí. Tham gia để chia sẻ trải nghiệm với cộng đồng">
	<meta name="keywords" content="Phần mềm, POS, ẩm thực, nhà hàng, cafe, bar/pub, quán ăn">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<title><tiles:getAsString name="title"/></title>
	<tiles:insertAttribute name="base-style" />
	<tiles:insertAttribute name="page-style" />
</head>
<!-- end::HEAD -->
<!-- begin::BODY -->
<body class="m-page--fluid m--skin- m-page--loading m-content--skin-light2 m-header--fixed m-header--fixed-mobile m-aside-left--enabled m-aside-left--skin-light m-aside-left--fixed m-aside-left--offcanvas m-footer--push m-aside--offcanvas-default" >
	<!-- begin::Page -->
	<div class="m-grid m-grid--hor m-grid--root m-page">
		<tiles:insertAttribute  name="header" />
		<!--begin::Body Content -->
		<div class="m-grid__item m-grid__item--fluid m-grid m-grid--ver-desktop m-grid--desktop m-body">
			<tiles:insertAttribute name="sidebar" />
			<tiles:insertAttribute name="body" />
		</div>
		<!--end::Body Content -->
		<tiles:insertAttribute name="footer" />
	</div>
	<!-- end::Page -->
	<!-- begin::Scroll Top -->
	<div class="m-scroll-top m-scroll-top--skin-top" data-toggle="m-scroll-top" data-scroll-offset="500" data-scroll-speed="300">
		<i class="la la-arrow-up"></i>
	</div>
	<!-- end::Scroll Top -->
</body>
<!-- end::BODY -->
<tiles:insertAttribute name="base-jquery" />
<tiles:insertAttribute name="page-jquery" />
</html>