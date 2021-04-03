<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<c:set var="context" value="${pageContext.request.contextPath}" />
<!-- BEGIN: Header -->
<header class="m-grid__item m-header" data-minimize-offset="200" data-minimize-mobile-offset="200">
	<div class="m-container m-container--fluid m-container--full-height">
		<div class="m-stack m-stack--ver m-stack--desktop">
			<!-- BEGIN: Brand -->
			<div class="m-stack__item m-brand  m-brand--skin-light">
				<div class="m-stack m-stack--ver m-stack--general">
					<div class="m-stack__item m-stack__item--middle m-brand__logo">
						<a href="index.html" class="m-brand__logo-wrapper">
							<%-- <img alt="" src="${context }/resources/base-v2/media/img/logos/logo-1-dark.png" style="height:20px;"/> --%>
							<span style="color: black;font-size: 25px;">FIBO</span><span style="color: red;font-size: 20px;">CART</span>
						</a>
					</div>
					<div class="m-stack__item m-stack__item--middle m-brand__tools">
						<!-- BEGIN: Left Aside Minimize Toggle -->
						<a href="javascript:;" id="m_aside_left_minimize_toggle" class="m-brand__icon m-brand__toggler m-brand__toggler--left m--visible-desktop-inline-block">
							<span></span>
						</a>
						<!-- END -->
						<!-- BEGIN: Responsive Aside Left Menu Toggler -->
						<a href="javascript:;" id="m_aside_left_offcanvas_toggle" class="m-brand__icon m-brand__toggler m-brand__toggler--left m--visible-tablet-and-mobile-inline-block">
							<span></span>
						</a>
						<!-- END -->
						<!-- BEGIN: Responsive Header Menu Toggler -->
						<a id="m_aside_header_menu_mobile_toggle" href="javascript:;" class="m-brand__icon m-brand__toggler m--visible-tablet-and-mobile-inline-block">
							<span></span>
						</a>
						<!-- END -->
						<!-- BEGIN: Topbar Toggler -->
						<a id="m_aside_header_topbar_mobile_toggle" href="javascript:;" class="m-brand__icon m--visible-tablet-and-mobile-inline-block">
							<i class="flaticon-more"></i>
						</a>
						<!-- BEGIN: Topbar Toggler -->
					</div>
				</div>
			</div>
			<!-- END: Brand -->
			<div class="m-stack__item m-stack__item--fluid m-header-head" id="m_header_nav">
				<!-- BEGIN: Horizontal Menu -->
				<div id="m_header_menu" class="m-header-menu m-aside-header-menu-mobile m-aside-header-menu-mobile--offcanvas  m-header-menu--skin-light m-header-menu--submenu-skin-light m-aside-header-menu-mobile--skin-light m-aside-header-menu-mobile--submenu-skin-light "  >
				</div>
				<!-- END: Horizontal Menu -->
				<!-- BEGIN: Topbar -->
				<div id="m_header_topbar" class="m-topbar  m-stack m-stack--ver m-stack--general">
					<div class="m-stack__item m-topbar__nav-wrapper">
						<ul class="m-topbar__nav m-nav m-nav--inline">
							<li class="m-nav__item m-dropdown m-dropdown--large m-dropdown--arrow m-dropdown--align-center m-dropdown--mobile-full-width m-dropdown--skin-light	m-list-search m-list-search--skin-light" data-dropdown-toggle="click" data-dropdown-persistent="true" id="m_quicksearch" data-search-type="dropdown">
								<a href="#" class="m-nav__link m-dropdown__toggle">
									<span class="m-nav__link-icon">
										<i class="flaticon-search-1"></i>
									</span>
								</a>
								<div class="m-dropdown__wrapper">
									<span class="m-dropdown__arrow m-dropdown__arrow--center"></span>
									<div class="m-dropdown__inner ">
										<div class="m-dropdown__header">
											<form  class="m-list-search__form">
												<div class="m-list-search__form-wrapper">
													<span class="m-list-search__form-input-wrapper">
														<input id="m_quicksearch_input" autocomplete="off" type="text" name="q" class="m-list-search__form-input" value="" placeholder="Tìm kiếm...">
													</span>
													<span class="m-list-search__form-icon-close" id="m_quicksearch_close">
														<i class="la la-remove"></i>
													</span>
												</div>
											</form>
										</div>
										<div class="m-dropdown__body">
											<div class="m-dropdown__scrollable m-scrollable" data-scrollable="true" data-max-height="300" data-mobile-max-height="200">
												<div class="m-dropdown__content"></div>
											</div>
										</div>
									</div>
								</div>
							</li>
							<li class="m-nav__item m-topbar__notifications m-topbar__notifications--img m-dropdown m-dropdown--large m-dropdown--header-bg-fill m-dropdown--arrow m-dropdown--align-center 	m-dropdown--mobile-full-width" data-dropdown-toggle="click" data-dropdown-persistent="true">
								<a href="#" class="m-nav__link m-dropdown__toggle" id="m_topbar_notification_icon">
									<span class="m-nav__link-badge m-badge m-badge--dot m-badge--dot-small m-badge--danger"></span>
									<span class="m-nav__link-icon">
										<i class="flaticon-music-2"></i>
									</span>
								</a>
								<div class="m-dropdown__wrapper">
									<span class="m-dropdown__arrow m-dropdown__arrow--center"></span>
									<div class="m-dropdown__inner">
										<div class="m-dropdown__header m--align-center" style="background: url(${context }/resources/images/user_profile_bg.jpg); background-size: cover;">
											<span class="m-dropdown__header-title">
												9 Thông Báo Mới
											</span>
											<!-- <span class="m-dropdown__header-subtitle">
												Thông báo chung
											</span> -->
										</div>
										<div class="m-dropdown__body">
											<div class="m-dropdown__content">
												<ul class="nav nav-tabs m-tabs m-tabs-line m-tabs-line--brand" role="tablist">
													<li class="nav-item m-tabs__item">
														<a class="nav-link m-tabs__link active" data-toggle="tab" href="#topbar_notifications_notifications" role="tab">
															Order
														</a>
													</li>
													<li class="nav-item m-tabs__item">
														<a class="nav-link m-tabs__link" data-toggle="tab" href="#topbar_notifications_events" role="tab">
															Hệ thống
														</a>
													</li>
												</ul>
												<div class="tab-content">
													<div class="tab-pane active" id="topbar_notifications_notifications" role="tabpanel">
														<div class="m-scrollable" data-scrollable="true" data-max-height="250" data-mobile-max-height="200">
															<div class="m-list-timeline m-list-timeline--skin-light timeline-notification">
																<div class="m-list-timeline__items">
																	<div class="m-list-timeline__item">
																		<span class="m-list-timeline__badge"></span>
																		<a href="#" class="m-list-timeline__text">
																			<span class="w-100 d-block">Tai Nghe Chụp Tai Sony MDR-ZX310AP - Hàng Chính Hãng</span>
																			<span class="m-badge m-badge--metal m-badge--wide">Offline Sale</span>
																			<span class="m-badge m-badge--success m-badge--wide">
																				Đã xác nhận
																			</span>
																		</a>
																		<span class="m-list-timeline__time">
																			14 mins
																		</span>
																	</div>
																	<div class="m-list-timeline__item m-list-timeline__item--read">
																		<span class="m-list-timeline__badge"></span>
																		<a href="#" class="m-list-timeline__text">
																			<span class="w-100 d-block">
																				Apple Macbook Pro Touch Bar 2019 - 13 inchs (i5/ 8GB/ 256GB) - Hàng Nhập Khẩu Chính Hãng
																			</span>
																			<span class="m-badge m-badge--metal m-badge--wide">Online Sale</span>
																			<span class="m-badge m-badge--warning m-badge--wide">
																				Chờ xác nhận
																			</span>
																		</a>
																		<span class="m-list-timeline__time">
																			20 mins
																		</span>
																	</div>
																	<div class="m-list-timeline__item">
																		<span class="m-list-timeline__badge"></span>
																		<a href="#" class="m-list-timeline__text">
																			<span class="w-100 d-block">DB overloaded 80%</span>
																			<span class="m-badge m-badge--metal m-badge--wide">Online Sale</span>
																			<span class="m-badge m-badge--info m-badge--wide">
																				Đang giao
																			</span>
																		</a>
																		<span class="m-list-timeline__time">
																			1 hr
																		</span>
																	</div>
																	<div class="m-list-timeline__item m-list-timeline__item--read">
																		<span class="m-list-timeline__badge"></span>
																		<a href="" class="m-list-timeline__text">
																			<span class="w-100 d-block">New order received</span>
																			<span class="m-badge m-badge--metal m-badge--wide">Offline Sale</span>
																			<span class="m-badge m-badge--danger m-badge--wide">
																				Đã hủy
																			</span>
																		</a>
																		<span class="m-list-timeline__time">
																			7 hrs
																		</span>
																	</div>
																	<div class="m-list-timeline__item m-list-timeline__item--read">
																		<span class="m-list-timeline__badge"></span>
																		<a href="#" class="m-list-timeline__text">
																			<span class="w-100 d-block">Production server down</span>
																		</a>
																		<span class="m-list-timeline__time">
																			3 hrs
																		</span>
																	</div>
																	<div class="m-list-timeline__item">
																		<span class="m-list-timeline__badge"></span>
																		<a href="#" class="m-list-timeline__text">
																			<span class="w-100 d-block">Production server up</span>
																		</a>
																		<span class="m-list-timeline__time">
																			5 hrs
																		</span>
																	</div>
																</div>
															</div>
														</div>
													</div>
													<div class="tab-pane" id="topbar_notifications_events" role="tabpanel">
														<div class="m-scrollable" data-scrollable="true" data-max-height="250" data-mobile-max-height="200">
															<div class="m-list-timeline m-list-timeline--skin-light timeline-notification">
																<div class="m-list-timeline__items">
																	<div class="m-list-timeline__item">
																		<span class="m-list-timeline__badge m-list-timeline__badge--state1-success"></span>
																		<a href="" class="m-list-timeline__text">
																			<span class="w-100 d-block">New order received</span>
																		</a>
																		<span class="m-list-timeline__time">
																			Just now
																		</span>
																	</div>
																	<div class="m-list-timeline__item m-list-timeline__item--read">
																		<span class="m-list-timeline__badge m-list-timeline__badge--state1-danger"></span>
																		<a href="" class="m-list-timeline__text">
																			<span class="w-100 d-block">New invoice received</span>
																		</a>
																		<span class="m-list-timeline__time">
																			20 mins
																		</span>
																	</div>
																	<div class="m-list-timeline__item m-list-timeline__item--read">
																		<span class="m-list-timeline__badge m-list-timeline__badge--state1-success"></span>
																		<a href="" class="m-list-timeline__text">
																			<span class="w-100 d-block">Production server up</span>
																		</a>
																		<span class="m-list-timeline__time">
																			5 hrs
																		</span>
																	</div>
																	<div class="m-list-timeline__item">
																		<span class="m-list-timeline__badge m-list-timeline__badge--state1-info"></span>
																		<a href="" class="m-list-timeline__text">
																			<span class="w-100 d-block">New order received</span>
																		</a>
																		<span class="m-list-timeline__time">
																			7 hrs
																		</span>
																	</div>
																	<div class="m-list-timeline__item">
																		<span class="m-list-timeline__badge m-list-timeline__badge--state1-info"></span>
																		<a href="" class="m-list-timeline__text">
																			<span class="w-100 d-block">System shutdown</span>
																		</a>
																		<span class="m-list-timeline__time">
																			11 mins
																		</span>
																	</div>
																	<div class="m-list-timeline__item m-list-timeline__item--read">
																		<span class="m-list-timeline__badge m-list-timeline__badge--state1-info"></span>
																		<a href="" class="m-list-timeline__text">
																			<span class="w-100 d-block">Production server down</span>
																		</a>
																		<span class="m-list-timeline__time">
																			3 hrs
																		</span>
																	</div>
																	<div class="m-list-timeline__item m-list-timeline__item--read">
																		<span class="m-list-timeline__badge m-list-timeline__badge--state1-info"></span>
																		<a href="" class="m-list-timeline__text">
																			<span class="w-100 d-block">Production server down</span>
																		</a>
																		<span class="m-list-timeline__time">
																			3 hrs
																		</span>
																	</div>
																	<div class="m-list-timeline__item m-list-timeline__item--read">
																		<span class="m-list-timeline__badge m-list-timeline__badge--state1-info"></span>
																		<a href="" class="m-list-timeline__text">
																			<span class="w-100 d-block">Production server down</span>
																		</a>
																		<span class="m-list-timeline__time">
																			3 hrs
																		</span>
																	</div>
																</div>
															</div>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</li>
							<li class="m-nav__item m-topbar__user-profile  m-dropdown m-dropdown--medium m-dropdown--arrow m-dropdown--align-right m-dropdown--mobile-full-width m-dropdown--skin-light" data-dropdown-toggle="click" aria-expanded="true">
								<a href="#" class="m-nav__link m-dropdown__toggle">
									<span class="m-nav__link-icon">
										<i class="la la-language" style="font-size:25px"></i>
									</span>
								</a>
								<div class="m-dropdown__wrapper" style="min-width:200px; width:auto">
									<span class="m-dropdown__arrow m-dropdown__arrow--right m-dropdown__arrow--adjust"></span>
									<div class="m-dropdown__inner">
										<div class="m-dropdown__body">
											<div class="m-dropdown__content">
												<ul class="m-nav m-nav--skin-light">
													<li class="m-nav__item">
														<a href="?language=vi" class="m-nav__link">
															<img src="${context}/resources/images/flag-VN.jpg" height="20px" width="30px" class=" d-block">
															<span class="m-nav__link-text pl-5">
																Tiếng Việt
															</span>
														</a>
													</li>
													<li class="m-nav__item">
														<a href="?language=ja" class="m-nav__link">
															<img src="${context}/resources/images/Ja-flag.jpg" height="20px" width="30px" class=" d-block" style="border:1px solid #eee">
															<span class="m-nav__link-text pl-5">
																Tiếng Nhật
															</span>
														</a>
													</li>
													<li class="m-nav__item">
														<a href="?language=en" class="m-nav__link">
															<img src="${context}/resources/images/flag-EN.png" height="20px" width="30px" class=" d-block">
															<span class="m-nav__link-text pl-5">
																Tiếng Anh
															</span>
														</a>
													</li>
												</ul>
											</div>
										</div>
									</div>
								</div>
							</li>
							<li class="m-nav__item m-topbar__user-profile m-topbar__user-profile--img  m-dropdown m-dropdown--medium m-dropdown--arrow m-dropdown--header-bg-fill m-dropdown--align-right m-dropdown--mobile-full-width m-dropdown--skin-light" data-dropdown-toggle="click" aria-expanded="true">
								<a href="#" class="m-nav__link m-dropdown__toggle">
									<span class="m-topbar__username pr-3" style="color:#000">
										<spring:message code="lable.hello" text="hello"></spring:message> ${employeeName }
									</span>
									<span class="m-topbar__userpic">
										<img src="${context }/resources/images/051-waiter.png" class="m--img-rounded m--marginless m--img-centered" alt=""/>
									</span>
								</a>
								<div class="m-dropdown__wrapper">
									<span class="m-dropdown__arrow m-dropdown__arrow--right m-dropdown__arrow--adjust"></span>
									<div class="m-dropdown__inner">
										<div class="m-dropdown__header m--align-center" style="background: url(${context }/resources/images/user_profile_bg.jpg); background-size: cover;">
											<div class="m-card-user m-card-user--skin-dark">
												<div class="m-card-user__pic">
													<img src="${context }/resources/images/051-waiter.png" class="m--img-rounded m--marginless" alt=""/>
												</div>
												<div class="m-card-user__details">
													<span class="m-card-user__name m--font-weight-500">
														${employeeName }
													</span>
												</div>
											</div>
										</div>
										<div class="m-dropdown__body">
											<div class="m-dropdown__content">
												<ul class="m-nav m-nav--skin-light">
													<li class="m-nav__section m--hide">
														<span class="m-nav__section-text">
															Section
														</span>
													</li>
													<li class="m-nav__item">
														<a id="btnChangePass" class="m-nav__link">
															<i class="m-nav__link-icon flaticon-user"></i>
															<span class="m-nav__link-text">
																Đổi mật khẩu
															</span>
														</a>
													</li>
													<li class="m-nav__item">
														<a id="btnZenCode" class="m-nav__link">
															<i class="m-nav__link-icon flaticon-profile-1"></i>
															<span class="m-nav__link-text">
																Đổi mã làm việc
															</span>
														</a>
													</li>
													<li class="m-nav__separator m-nav__separator--fit"></li>
													<li class="m-nav__item">
														<a href="${context }/logout" class="btn m-btn--pill    btn-secondary m-btn m-btn--custom m-btn--label-brand m-btn--bolder">
															<i class="m-nav__link-icon fa fa-power-off"></i> Đăng xuất
														</a>
													</li>
												</ul>
											</div>
										</div>
									</div>
								</div>
							</li>
						</ul>
					</div>
				</div>
				<!-- END: Topbar -->
			</div>
		</div>
	</div>
</header>
<!-- END: Header -->       