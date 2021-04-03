<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="context" value="${pageContext.request.contextPath}"/>

<!--begin::Page Scripts -->
<script src="${context }/resources/js/jquery.min.js" type="text/javascript"></script>
<script src="${context }/resources/js/jquery.validate.min.js" type="text/javascript"></script>
<!--end::Page Scripts -->
<!--begin::Base Scripts admin Core-->
<script src="${context }/resources/js/vendors.bundle.js" type="text/javascript"></script>
<script src="${context }/resources/js/scripts.bundle.js" type="text/javascript"></script>
<script src="${context }/resources/js/input-mask.js" type="text/javascript"></script>
<!--end::Base Scripts admin Core-->
<script type="text/javascript">
var CONTEXT          = '${context}';
</script>