<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script type="text/javascript">
	$(document).ready(function() {
	});
</script>

<title>Search Song</title>
</head>
<body>
	<form class="form-inline" action="searchDiskMp3" method="get">
		<input id="diskMp3Path" name="diskMp3Path" value="${diskMp3Path}"
			type="text" class="input-xxlarge"
			placeholder="Enter path for search mp3" autocomplete="off">
		<button type="submit" class="btn">Search on HardDisk</button>
	</form>



	<c:if test="${not empty searchSuccess}">
		<c:choose>
			<c:when
				test="${not empty nonPersistedSongListSize and nonPersistedSongListSize > 0}">
				<div class="alert alert-success">
					<a class="close" data-dismiss="alert">x</a> <strong>Success!</strong>
					<span>${searchSuccessMsg}</span>
				</div>
			</c:when>
			<c:otherwise>
				<div class="alert alert-info">
					<a class="close" data-dismiss="alert">x</a> <strong>Info!</strong>
					<span>${searchSuccessMsg}</span>
				</div>
			</c:otherwise>
		</c:choose>
	</c:if>

	<table class="table table-condensed table-bordered table-striped">
		<thead>
			<tr>
				<th>#</th>
				<th>File Name</th>
				<th>File Path</th>
				<th>Hash</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="song" items="${nonPersistedSongList}"
				varStatus="loop">
				<tr>
					<td>${loop.index + 1}</td>
					<td>${song.fileName}</td>
					<td>${song.canonicalPath}</td>
					<td>${song.infoMD5String}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>
