<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
	prefix="decorator"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title><decorator:title default="" /></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<!-- Le styles -->
<link href="<%=request.getContextPath()%>/bootstrap/css/bootstrap.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/css/main.css" rel="stylesheet">

<style type="text/css">
body {
	padding-top: 60px;
	padding-bottom: 40px;
}

.sidebar-nav {
	padding: 9px 0;
}

@media ( max-width : 980px) { /* Enable use of floated navbar text */
	.navbar-text.pull-right {
		float: none;
		padding-left: 5px;
		padding-right: 5px;
	}
}
</style>
<link href="<%=request.getContextPath()%>/bootstrap/css/bootstrap-responsive.css"
	rel="stylesheet">
<script src="<%=request.getContextPath()%>/js/jquery-1.9.1.js"></script>

<decorator:head />
</head>

<body>
	<div class="navbar navbar-inverse navbar-fixed-top">
		<div class="navbar-inner">
			<div class="container-fluid">
				<button type="button" class="btn btn-navbar" data-toggle="collapse"
					data-target=".nav-collapse">
					<span class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="brand" href="#">MahOut Song Tagger</a>
				<div class="nav-collapse collapse">
					<!--<p class="navbar-text pull-right">
						Logged in as <a href="#" class="navbar-link">Username</a>
					</p>-->
					<ul class="nav">
						<li class="active"><a href="#">Home</a></li>
						<li><a href="#about">About</a></li>
						<li><a href="#contact">Contact</a></li>
					</ul>
					<!--<ul class="nav pull-right">
						<li class="dropdown"><a href="#" class="dropdown-toggle"
							data-toggle="dropdown">Utility <b class="caret"></b></a>
							<ul class="dropdown-menu">
								<li><a href="#">Item 1</a></li>
								<li><a href="#">Item 2</a></li>
								<li><a href="#">Item 3</a></li>
							</ul></li>
					</ul>-->
				</div>
				<!--/.nav-collapse -->
			</div>
		</div>
	</div>

	<div class="container-fluid">
		<div class="row-fluid">
			<%@include file="menu.jsp"%>
			<div class="span9">
				<decorator:body />
			</div>
			<!--/span-->
		</div>
		<!--/row-->

		<hr>

		<footer>
			<p>&copy; MahOut 2013</p>
		</footer>

	</div>
	<!--/.fluid-container-->

	<!-- Le javascript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script src="<%=request.getContextPath()%>/bootstrap/js/bootstrap.js"></script>
	<script src="<%=request.getContextPath()%>/bootstrap/js/bootstrap-transition.js"></script>
	<script src="<%=request.getContextPath()%>/bootstrap/js/bootstrap-alert.js"></script>
	<script src="<%=request.getContextPath()%>/bootstrap/js/bootstrap-modal.js"></script>
	<script src="<%=request.getContextPath()%>/bootstrap/js/bootstrap-dropdown.js"></script>
	<script src="<%=request.getContextPath()%>/bootstrap/js/bootstrap-scrollspy.js"></script>
	<script src="<%=request.getContextPath()%>/bootstrap/js/bootstrap-tab.js"></script>
	<script src="<%=request.getContextPath()%>/bootstrap/js/bootstrap-tooltip.js"></script>
	<script src="<%=request.getContextPath()%>/bootstrap/js/bootstrap-popover.js"></script>
	<script src="<%=request.getContextPath()%>/bootstrap/js/bootstrap-button.js"></script>
	<script src="<%=request.getContextPath()%>/bootstrap/js/bootstrap-collapse.js"></script>
	<script src="<%=request.getContextPath()%>/bootstrap/js/bootstrap-carousel.js"></script>
	<script src="<%=request.getContextPath()%>/bootstrap/js/bootstrap-typeahead.js"></script>

	<script src="<%=request.getContextPath()%>/js/main.js"></script>

	<!-- highlight nav menu -->
	<script type="text/javascript">
		$(document).ready(function() {
			var url = window.location.href + '';
			var paramStartIndex = url.indexOf("?");
			if (paramStartIndex > -1) {
				url = url.substring(0, paramStartIndex);
			}

			$('ul.nav a').filter(function() {
				return this.href == url;
			}).parent().addClass('active');
		});
	</script>
</body>
</html>
