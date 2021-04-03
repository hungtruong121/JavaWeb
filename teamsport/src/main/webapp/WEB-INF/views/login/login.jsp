<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="logo">
	<a href="http://nhahangpos.com/">
		<%-- <img src="${context }/resources/images/logo-2.png" alt="" class="logo-login"/> --%>
		<span style="color: #fff;font-size: xxx-large;">FIBO</span><span style="color: red;font-size: xxx-large;">CART</span>
	</a>
</div>
<div class="content">
	<!-- BEGIN LOGIN FORM -->
	<form class="login-form" method="post" id="loginform" action="<c:url value='login' />">
		<h3 class="form-title" style="text-align: center;">Đăng nhập</h3>
		<c:if test="${loginFail }">
		<div class="alert alert-danger">
			<button class="close" data-close="alert"></button>
			<span> Tài khoản hoặc mật khẩu không đúng. </span>
		</div>
		</c:if>
		<div class="form-group">
			<!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->
			<label class="control-label visible-ie8 visible-ie9">Tài khoản</label>
			<div class="input-icon">
			   	<i class="fa fa-user"></i>
			    <input class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="Tài khoản" name="username" />
			</div>
		</div>
        <div class="form-group">
            <label class="control-label visible-ie8 visible-ie9">Mật khẩu</label>
            <div class="input-icon">
                <i class="fa fa-lock"></i>
                <input class="form-control placeholder-no-fix" type="password" autocomplete="off" placeholder="Mật khẩu" name="password" /> </div>
        </div>
        <div class="form-actions">
            <!-- <label class="rememberme mt-checkbox mt-checkbox-outline">
                <input type="checkbox" name="remember-me" id="rememberMe"/> Ghi nhớ
                <span></span>
            </label> -->
            <button type="submit" class="btn green pull-right"> Đăng nhập </button>
        </div>
        <!-- <div class="forget-password">
            <h4>Chưa có tài khoản ?</h4>
            <p>Nhấn<a href="javascript:;" id="register-btn"> vào đây </a> để đăng ký mới.</p>
        </div> -->
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
    </form>
    <!-- END LOGIN FORM -->
    <!-- BEGIN FORGOT PASSWORD FORM -->
    <form class="forget-form" action="#" method="post">
        <h3>Quên mật khẩu ?</h3>
        <p> Nhập địa chỉ email bạn muốn khôi phục mật khẩu. </p>
        <div class="form-group">
            <div class="input-icon">
                <i class="fa fa-envelope"></i>
                <input class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="Email" name="email" /> </div>
        </div>
        <div class="form-actions">
            <button type="button" id="back-btn" class="btn red btn-outline">Quay lại </button>
            <button type="submit" class="btn green pull-right"> Gửi </button>
        </div>
    </form>
    <!-- END FORGOT PASSWORD FORM -->
    <!-- BEGIN REGISTRATION FORM -->
    <form class="register-form" action="#" method="post">
        <h3>Đăng ký</h3>
        <p> Nhập thông tin bên dưới: </p>
        <div class="form-group">
            <label class="control-label visible-ie8 visible-ie9">Tên công ty</label>
            <div class="input-icon">
                <i class="fa fa-font"></i>
                <input class="form-control placeholder-no-fix" type="text" placeholder="Họ tên" name="fullname" /> </div>
        </div>
        <div class="form-group">
            <!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->
            <label class="control-label visible-ie8 visible-ie9">Email</label>
            <div class="input-icon">
                <i class="fa fa-envelope"></i>
                <input class="form-control placeholder-no-fix" type="text" placeholder="Email" name="email" /> </div>
        </div>
        <div class="form-group">
            <label class="control-label visible-ie8 visible-ie9">Địa chỉ</label>
            <div class="input-icon">
                <i class="fa fa-check"></i>
                <input class="form-control placeholder-no-fix" type="text" placeholder="Địa chỉ" name="address" /> </div>
        </div>
        <div class="form-group">
            <label class="control-label visible-ie8 visible-ie9">Tỉnh/Thành phố</label>
            <div class="input-icon">
                <i class="fa fa-location-arrow"></i>
                <input class="form-control placeholder-no-fix" type="text" placeholder="Tỉnh/Thành phố" name="city" /> </div>
        </div>
        <div class="form-group">
            <label class="control-label visible-ie8 visible-ie9">Thành phố</label>
            <select name="country" id="country_list" class="select2 form-control">
                <option value="">Loại hình kinh doanh</option>
                <option value="AF">Nhà Hàng</option>
                <option value="ZW">Siêu thị</option>
            </select>
        </div>
        <p> Nhập thông tin bên dưới: </p>
        <div class="form-group">
            <label class="control-label visible-ie8 visible-ie9">Tài khoản</label>
            <div class="input-icon">
                <i class="fa fa-user"></i>
                <input class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="Tài khoản" name="username" /> </div>
        </div>
        <div class="form-group">
            <label class="control-label visible-ie8 visible-ie9">Mật khẩu</label>
            <div class="input-icon">
                <i class="fa fa-lock"></i>
                <input class="form-control placeholder-no-fix" type="password" autocomplete="off" id="register_password" placeholder="Mật khẩu" name="password" /> </div>
        </div>
        <div class="form-group">
            <label class="control-label visible-ie8 visible-ie9">Nhập lại mật khẩu</label>
            <div class="controls">
                <div class="input-icon">
                    <i class="fa fa-check"></i>
                    <input class="form-control placeholder-no-fix" type="password" autocomplete="off" placeholder="Nhập lại mật khẩu" name="rpassword" /> </div>
            </div>
        </div>
        <div class="form-group">
            <label class="mt-checkbox mt-checkbox-outline">
                <input type="checkbox" name="tnc" /> Tôi đồng ý với
                <a href="javascript:;">Điều khoản </a> &
                <a href="javascript:;">Chính sách </a>
                <span></span>
            </label>
            <div id="register_tnc_error"> </div>
        </div>
        <div class="form-actions">
            <button id="register-back-btn" type="button" class="btn red btn-outline"> Quay lại </button>
            <button type="submit" id="register-submit-btn" class="btn green pull-right"> Đăng ký </button>
        </div>
    </form>
    <!-- END REGISTRATION FORM -->
</div>
<!-- BEGIN COPYRIGHT -->
<div class="copyright"> 2020 &copy; FiboCart . All rights reserved </div>
<!-- END COPYRIGHT -->