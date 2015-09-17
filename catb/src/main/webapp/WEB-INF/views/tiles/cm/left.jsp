<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="/WEB-INF/tag/functions.tld"%>
<%@ taglib prefix="s" uri="/WEB-INF/tag/extags.tld" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<c:set var="menu" value="${f:getActiveMenuId(pageContext.request)}" scope="request"></c:set>
<c:set var="menuId" value="${menu[0]}" scope="request"></c:set>
<c:set var="menuItemId" value="${menu[1]}" scope="request"></c:set>

<div id="vertical-menu">
	<ul class="accordion">
		<s:hasAnyPermission name="news:create, news:manage, news:approve, newsCatalog:manage">
			<li class="item ${f:getMenuClass(menuId, 'info', 'selected_item')}" id="info">
				<img src="${ct}/resources/images/info.png" class="icon_menu" />
				<h3>Quản trị tin tức</h3>
				<ul class="child">
					<shiro:hasPermission name="news:create">
						<li id="info_1" class="${f:getMenuClass(menuItemId, 'info_1', 'selected_subitem')}">
							<img src="${ct}/resources/images/add_news.gif" class="bullet_menu" />
							<a href="${ct}/cm/news/create">Thêm mới tin</a>
						</li>
					</shiro:hasPermission>
					<shiro:hasPermission name="news:manage">
						<li id="info_2" class="${f:getMenuClass(menuItemId, 'info_2', 'selected_subitem')}">
							<img src="${ct}/resources/images/earth_f.gif" class="bullet_menu" />
							<a href="${ct}/cm/news/manage">Quản trị tin</a>
						</li>
					</shiro:hasPermission>
					<shiro:hasPermission name="news:approve">
						<li id="info_3" class="${f:getMenuClass(menuItemId, 'info_3', 'selected_subitem')}">
							<img src="${ct}/resources/images/template_f.gif" class="bullet_menu" />
							<a href="${ct}/cm/news/approve">Duyệt tin</a>
						</li>
					</shiro:hasPermission>
					<shiro:hasPermission name="newsCatalog:manage">
						<li id="info_4" class="${f:getMenuClass(menuItemId, 'info_4', 'selected_subitem')}">
							<img src="${ct}/resources/images/book_f.gif" class="bullet_menu" />
							<a href="${ct}/cm/newsCatalog/add">Quản trị danh mục tin</a>
						</li>
					</shiro:hasPermission>
				</ul>
			</li>
		</s:hasAnyPermission>
		<s:hasAnyPermission name="administrativeProcedure:manage, document:manage">
			<li class="item ${f:getMenuClass(menuId, 'doc', 'selected_item')}" id="doc">
				<img src="${ct}/resources/images/doc.png" class="icon_menu" />
				<h3>Quản trị văn bản</h3>
				<ul class="child">
					<shiro:hasPermission name="administrativeProcedure:manage">
						<li id="doc_1" class="${f:getMenuClass(menuItemId, 'doc_1', 'selected_subitem')}">
							<img src="${ct}/resources/images/add_news.gif" class="bullet_menu" />
							<a href="${ct}/cm/administrativeProcedure/add">Quản trị TTHC</a>
						</li>
					</shiro:hasPermission>
					<shiro:hasPermission name="document:manage">
						<li id="doc_2" class="${f:getMenuClass(menuItemId, 'doc_2', 'selected_subitem')}">
							<img src="${ct}/resources/images/earth_f.gif" class="bullet_menu" />
							<a href="${ct}/cm/document/add">Quản trị văn bản</a>
						</li>
					</shiro:hasPermission>
				</ul>
			</li>
		</s:hasAnyPermission>
		<s:hasAnyPermission name="qaCatalog:manage, comment:manage, criminalDenouncement:manage">
			<li class="item ${f:getMenuClass(menuId, 'question', 'selected_item')}" id="question">
				<img src="${ct}/resources/images/question.png" class="icon_menu" />
				<h3>Quản trị hỏi đáp & tố cáo</h3>
				<ul class="child">
					<shiro:hasPermission name="qaCatalog:manage">
						<li id="question_1" class="${f:getMenuClass(menuItemId, 'question_1', 'selected_subitem')}">
							<img src="${ct}/resources/images/add_news.gif" class="bullet_menu" />
							<a href="${ct}/cm/qaCatalog/add">Quản trị DM hỏi đáp</a>
						</li>
					</shiro:hasPermission>
					<shiro:hasPermission name="comment:manage">
						<li id="question_2" class="${f:getMenuClass(menuItemId, 'question_2', 'selected_subitem')}">
							<img src="${ct}/resources/images/earth_f.gif" class="bullet_menu" />
							<a href="${ct}/cm/comment/show">Trả lời câu hỏi</a>
						</li>
					</shiro:hasPermission>
					<shiro:hasPermission name="criminalDenouncement:manage">
						<li id="question_3" class="${f:getMenuClass(menuItemId, 'question_3', 'selected_subitem')}">
							<img src="${ct}/resources/images/book_f.gif" class="bullet_menu" />
							<a href="${ct}/cm/denouncement/show">Tố giác tội phạm</a>
						</li>
					</shiro:hasPermission>
				</ul>
			</li>
		</s:hasAnyPermission>
		<s:hasAnyPermission name="imageCatalog:manage, videoCatalog:manage, image:manage, video:manage">
			<li class="item ${f:getMenuClass(menuId, 'media', 'selected_item')}" id="media">
				<img src="${ct}/resources/images/media.png" class="icon_menu" />
				<h3>Quản trị ảnh - video</h3>
				<ul class="child">
					<shiro:hasPermission name="imageCatalog:manage">
						<li id="media_1" class="${f:getMenuClass(menuItemId, 'media_1', 'selected_subitem')}">
							<img src="${ct}/resources/images/add_news.gif" class="bullet_menu" />
							<a href="${ct}/cm/imageCatalog/add">Danh mục Ảnh</a>
						</li>
					</shiro:hasPermission>
					<shiro:hasPermission name="videoCatalog:manage">
						<li id="media_2" class="${f:getMenuClass(menuItemId, 'media_2', 'selected_subitem')}">
							<img src="${ct}/resources/images/earth_f.gif" class="bullet_menu" />
							<a href="${ct}/cm/videoCatalog/add">Danh mục Video</a>
						</li>
					</shiro:hasPermission>
					<shiro:hasPermission name="image:manage">
						<li id="media_3" class="${f:getMenuClass(menuItemId, 'media_3', 'selected_subitem')}">
							<img src="${ct}/resources/images/template_f.gif" class="bullet_menu" />
							<a href="${ct}/cm/image/add">Quản trị Ảnh</a>
						</li>
					</shiro:hasPermission>
					<shiro:hasPermission name="video:manage">
						<li id="media_4" class="${f:getMenuClass(menuItemId, 'media_4', 'selected_subitem')}">
							<img src="${ct}/resources/images/book_f.gif" class="bullet_menu" />
							<a href="${ct}/cm/video/add">Quản trị Video</a>
						</li>
					</shiro:hasPermission>
				</ul>
			</li>
		</s:hasAnyPermission>
		<s:hasAnyPermission name="position:manage, department:manage, documentType:manage, field:manage">
			<li class="item ${f:getMenuClass(menuId, 'catalog', 'selected_item')}" id="catalog">
				<img src="${ct}/resources/images/catalog.png" class="icon_menu" />
				<h3>Quản trị danh mục</h3>
				<ul class="child">
					<shiro:hasPermission name="position:manage">
						<li id="catalog_1" class="${f:getMenuClass(menuItemId, 'catalog_1', 'selected_subitem')}">
							<img src="${ct}/resources/images/home_f.gif" class="bullet_menu" />
							<a href="${ct}/cm/position/add">Quản trị chức danh</a>
						</li>
					</shiro:hasPermission>
					<shiro:hasPermission name="department:manage">
						<li id="catalog_2" class="${f:getMenuClass(menuItemId, 'catalog_2', 'selected_subitem')}">
							<img src="${ct}/resources/images/Depart.png" class="bullet_menu" />
							<a href="${ct}/cm/department/add">Quản trị phòng ban</a>
						</li>
					</shiro:hasPermission>
					<shiro:hasPermission name="documentType:manage">
						<li id="catalog_3" class="${f:getMenuClass(menuItemId, 'catalog_3', 'selected_subitem')}">
							<img src="${ct}/resources/images/Depart.png" class="bullet_menu" />
							<a href="${ct}/cm/documentType/add">Quản trị loại văn bản</a>
						</li>
					</shiro:hasPermission>
					<shiro:hasPermission name="field:manage">
						<li id="catalog_4" class="${f:getMenuClass(menuItemId, 'catalog_4', 'selected_subitem')}">
							<img src="${ct}/resources/images/Depart.png" class="bullet_menu" />
							<a href="${ct}/cm/field/add">Quản trị lĩnh vực</a>
						</li>
					</shiro:hasPermission>
				</ul>
			</li>
		</s:hasAnyPermission>
		<s:hasAnyPermission name="link:manage, ad:manage, introduction:manage">
			<li class="item ${f:getMenuClass(menuId, 'other', 'selected_item')}" id="other">
				<img src="${ct}/resources/images/link.png" class="icon_menu" />
				<h3>Thông tin khác</h3>
				<ul class="child">
					<shiro:hasPermission name="link:manage">
						<li id="other_1" class="${f:getMenuClass(menuItemId, 'other_1', 'selected_subitem')}">
							<img src="${ct}/resources/images/add_news.gif" class="bullet_menu" />
							<a href="${ct}/cm/linkCatalog/add">Quản trị liên kết</a>
						</li>
					</shiro:hasPermission>
					<shiro:hasPermission name="ad:manage">
						<li id="other_2" class="${f:getMenuClass(menuItemId, 'other_2', 'selected_subitem')}">
							<img src="${ct}/resources/images/earth_f.gif" class="bullet_menu" />
							<a href="${ct}/cm/adv/add">Quản trị quảng cáo</a>
						</li>
					</shiro:hasPermission>
					<shiro:hasPermission name="introduction:manage">
						<li id="other_3" class="${f:getMenuClass(menuItemId, 'other_3', 'selected_subitem')}">
							<img src="${ct}/resources/images/template_f.gif" class="bullet_menu" />
							<a href="${ct}/cm/intro/add">Thông tin chung</a>
						</li>
					</shiro:hasPermission>
				</ul>
			</li>
		</s:hasAnyPermission>
		<s:hasAnyPermission name="user:manage, role:assign, permission:assign, role:manage, user:search, user:editSelf, permission:manage, configuration:manage, ui:manage">
			<li class="item ${f:getMenuClass(menuId, 'system', 'selected_item')}" id="system">
				<img src="${ct}/resources/images/system.png" class="icon_menu" />
				<h3>Quản trị hệ thống</h3>
				<ul class="child">
					<shiro:hasPermission name="user:manage">
						<li id="system_1" class="${f:getMenuClass(menuItemId, 'system_1', 'selected_subitem')}">
							<img src="${ct}/resources/images/add_news.gif" class="bullet_menu" />
							<a href="${ct}/cm/user/add">Thêm mới người dùng</a>
						</li>
					</shiro:hasPermission>
					<shiro:hasPermission name="role:assign">
						<li id="system_2" class="${f:getMenuClass(menuItemId, 'system_2', 'selected_subitem')}">
							<img src="${ct}/resources/images/earth_f.gif" class="bullet_menu" />
							<a href="${ct}/cm/manageUserRole">Phân quyền người dùng</a>
						</li>
					</shiro:hasPermission>
					<shiro:hasPermission name="permission:assign">
						<li id="system_3" class="${f:getMenuClass(menuItemId, 'system_3', 'selected_subitem')}">
							<img src="${ct}/resources/images/template_f.gif" class="bullet_menu" />
							<a href="${ct}/cm/showPermission">Phân quyền nhóm người dùng</a>
						</li>
					</shiro:hasPermission>
					<shiro:hasPermission name="role:manage">
						<li id="system_4" class="${f:getMenuClass(menuItemId, 'system_4', 'selected_subitem')}">
							<img src="${ct}/resources/images/book_f.gif" class="bullet_menu" />
							<a href="${ct}/cm/role/add">Quản trị nhóm người sử dụng</a>
						</li>
					</shiro:hasPermission>
					<shiro:hasPermission name="user:search">
						<li id="system_5" class="${f:getMenuClass(menuItemId, 'system_5', 'selected_subitem')}">
							<img src="${ct}/resources/images/book_f.gif" class="bullet_menu" />
							<a href="#">Danh sách người sử dụng</a>
						</li>
					</shiro:hasPermission>
					<shiro:hasPermission name="user:editSelf">
					<li id="system_6" class="${f:getMenuClass(menuItemId, 'system_6', 'selected_subitem')}">
						<img src="${ct}/resources/images/UserList.png" class="bullet_menu" />
						<a href="${ct}/cm/user/edit">Thông tin cá nhân</a>
					</li>
					</shiro:hasPermission>
					<shiro:hasPermission name="permission:manage">
					<li id="system_7" class="${f:getMenuClass(menuItemId, 'system_7', 'selected_subitem')}">
						<img src="${ct}/resources/images/User.png" class="bullet_menu" />
						<a href="${ct}/cm/permission/add">Quản trị quyền</a>
					</li>
					</shiro:hasPermission>
					<shiro:hasPermission name="configuration:manage">
					<li id="system_8" class="${f:getMenuClass(menuItemId, 'system_8', 'selected_subitem')}">
						<img src="${ct}/resources/images/home_f.gif" class="bullet_menu" />
						<a href="${ct}/cm/configurations">Quản trị cấu hình</a>
					</li>
					</shiro:hasPermission>
					<shiro:hasPermission name="ui:manage">
					<li id="system_9" class="${f:getMenuClass(menuItemId, 'system_9', 'selected_subitem')}">
						<img src="${ct}/resources/images/home_f.gif" class="bullet_menu" />
						<a href="#">Quản trị giao diện</a>
					</li>
					</shiro:hasPermission>
				</ul>
			</li>
		</s:hasAnyPermission>
	</ul>
</div>