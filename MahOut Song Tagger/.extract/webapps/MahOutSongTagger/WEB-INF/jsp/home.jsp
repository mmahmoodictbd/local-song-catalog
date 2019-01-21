<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style type="text/css">
</style>
<script type="text/javascript">
	$(document).ready(function() {

	});
</script>
</script>
<title>Home</title>
</head>
<body>

	<div class="span7">

		<h1>Dashboard</h1>

		<table class="table table-striped">
			<thead>
				<tr>
					<th>Status</th>
					<th>Count</th>
					<th>Detail</th>
				</tr>
			</thead>

			<tbody>
				<tr>
					<td><span class="label label-success">Tagged</span></td>
					<td>${dashboardInfoMap['TAGGEDSONG']}</td>
					<td>Songs, which you have tagged.</td>
				</tr>
				<tr>
					<td><span class="label label-warning">Untagged</span></td>
					<td>${dashboardInfoMap['UNTAGGEDSONG']}</td>
					<td>Songs, which are not tagged yet.</td>
				</tr>
				<tr>
					<td><span class="label label-info">Total</span></td>
					<td>${dashboardInfoMap['TOTALSONG']}</td>
					<td>Total Song</td>
				</tr>
				<tr>
					<td><span class="label label-important">Orphaned Song</span></td>
					<td>${dashboardInfoMap['TOTALORPHANEDSONG']}</td>
					<td>Songs which you have tagged earlier, but they are not exist
						on that path.</td>
				</tr>
			</tbody>
		</table>
	</div>


</body>
</html>
