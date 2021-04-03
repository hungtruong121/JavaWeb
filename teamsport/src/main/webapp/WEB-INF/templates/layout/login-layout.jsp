<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<c:set var="context" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="vi">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta name="description" content="Giải pháp phần mềm hữu ích cho các địa điểm ăn uống, ẩm thực tại Đà Nẵng - Nơi liên kết hàng ngàn địa điểm về ẩm thực, giải trí. Tham gia để chia sẻ trải nghiệm với cộng đồng">
	<meta name="keywords" content="Phần mềm, POS, ẩm thực, nhà hàng, cafe, bar/pub, quán ăn">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<tiles:insertAttribute name="base-style" />
</head>
<!-- end::HEAD -->
<!-- begin::BODY -->
<body class="login">
	<tiles:insertAttribute name="body" />
	<tiles:insertAttribute name="base-jquery" />
</body>
<!-- end::BODY -->
<script src="${context }/resources/js/jquery.backstretch.min.js" type="text/javascript"></script>
<script src="${context }/resources/js/login-4.js" type="text/javascript"></script>
</html>