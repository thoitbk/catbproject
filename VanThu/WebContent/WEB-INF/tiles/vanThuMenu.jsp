<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="Ribbon" class="officebar">
	<ul>
		<li class="current"
			attrcheck="269d2bf5-971a-4d7b-97fa-5cfe3eb41b03">
			<a class="link" rel="#">Trang chủ</a>
			<div style="display: block;" class="officetab">
				<ul>
					<li>
						<div class="panel">
							<span>&nbsp;&nbsp;</span>
							<div class="button">
								<a class="link"
									attrcheck="7bffd7f8-6bbd-4c44-aef9-79b85ff8e5c5"
									rel="/document/createDocument.html"> 
									<img src="/images/create_document.png" alt="">Tạo mới văn bản đi
								</a>
							</div>
							<div class="button">
								<a class="link"
									attrcheck="0e44b561-a615-4cd4-a0ef-9e2f0bc56ec6"
									rel="/document/simpleSearchDocuments.html"> 
									<img src="/images/document_list.png" alt="">Danh sách văn bản đi
								</a>
							</div>
							<div class="button">
								<a class="link"
									attrcheck="0e44b561-a615-4cd4-a0ef-9e2f0bc56ec6"
									rel="/mail/showInbox.html"> 
									<img src="/images/incomming-email.png" alt="">Email văn bản đến
								</a>
							</div>
						</div>
					</li>
					<li>
						<div class="panel">
							<span>&nbsp;&nbsp;</span>
							<div class="button">
								<a class="link"
									attrcheck="7bffd7f8-6bbd-4c44-aef9-79b85ff8e5c5"
									rel="/comingDocument/createDocument.html"> 
									<img src="/images/create_coming_document.png" alt="">Tạo mới văn bản đến
								</a>
							</div>
							<div class="button">
								<a class="link"
									attrcheck="0e44b561-a615-4cd4-a0ef-9e2f0bc56ec6"
									rel="/comingDocument/simpleSearchDocuments.html"> 
									<img src="/images/coming_document_list.png" alt="">Danh sách văn bản đến
								</a>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</li>
		<li attrcheck="7bffd7f8-6bbd-4c44-aef9-79b85ff8e5c5">
		<a class="link" rel="#">Danh sách văn bản đi</a>
			<div style="display: none;" class="officetab">
				<ul>
					<li>
						<div class="panel">
							<span>&nbsp;&nbsp;</span>
							<div class="button">
								<a class="link"
									attrcheck="7bffd7f8-6bbd-4c44-aef9-79b85ff8e5c5"
									rel="/document/simpleSearchDocuments.html"> <img
									src="/images/simple_search.png" alt="">Tìm VB đi đơn giản
								</a>
							</div>
						</div>
					</li>
					<li>
						<div class="panel">
							<span>&nbsp;&nbsp;</span>
							<div class="button">
								<a class="link"
									attrcheck="7bffd7f8-6bbd-4c44-aef9-79b85ff8e5c5"
									rel="/document/complexSearchDocuments.html"> <img
									src="/images/complex_search.png" alt="">Tìm VB đi nâng cao
								</a>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</li>
		<li attrcheck="7bffd7f8-6bbd-4c44-aef9-79b85ff8e5c5">
		<a class="link" rel="#">Danh sách văn bản đến</a>
			<div style="display: none;" class="officetab">
				<ul>
					<li>
						<div class="panel">
							<span>&nbsp;&nbsp;</span>
							<div class="button">
								<a class="link"
									attrcheck="7bffd7f8-6bbd-4c44-aef9-79b85ff8e5c5"
									rel="/document/simpleSearchDocuments.html"> <img
									src="/images/simple_search_coming_document.png" alt="">Tìm VB đến đơn giản
								</a>
							</div>
						</div>
					</li>
					<li>
						<div class="panel">
							<span>&nbsp;&nbsp;</span>
							<div class="button">
								<a class="link"
									attrcheck="7bffd7f8-6bbd-4c44-aef9-79b85ff8e5c5"
									rel="/document/complexSearchDocuments.html"> <img
									src="/images/complex_search_coming_document.png" alt="">Tìm VB đến nâng cao
								</a>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</li>
		<li attrcheck="474fe503-87b0-4412-961e-9baa52e30e0e">
		<a class="link" rel="#">Thông tin khác</a>
			<div style="display: none;" class="officetab">
				<ul>
					<li>
						<div class="panel">
							<span>&nbsp;&nbsp;</span>
							<div class="button">
								<a class="link"
									attrcheck="474fe503-87b0-4412-961e-9baa52e30e0e"
									rel="/instruction.html"> <img
									src="/images/instruction.png" alt="">Hướng dẫn
								</a>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</li>
		<li attrcheck="111ad63e-d353-4c17-8a82-73fd5ac889b8">
		<a class="link" rel="#">Hệ thống</a>
			<div style="display: none;" class="officetab">
				<ul>
					<li>
						<div class="panel">
							<span>&nbsp;&nbsp;</span>
							<div class="button">
								<a class="link"
									attrcheck="111ad63e-d353-4c17-8a82-73fd5ac889b8"
									rel="/user/changeInfo.html">
									<img src="/images/edit_user.png" alt="">Thay đổi thông tin
								</a>
							</div>
							<div class="button">
								<a class="link"
									attrcheck="111ad63e-d353-4c17-8a82-73fd5ac889b8"
									rel="/user/changePassword.html"> 
									<img src="/images/change_password.png" alt="">Đổi mật khẩu
								</a>
							</div>
							<div class="button">
								<a class="link"
									attrcheck="111ad63e-d353-4c17-8a82-73fd5ac889b8" rel="j_spring_security_logout">
									<img src="/images/logout.png" alt="">Đăng xuất
								</a>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</li>
	</ul>
</div>