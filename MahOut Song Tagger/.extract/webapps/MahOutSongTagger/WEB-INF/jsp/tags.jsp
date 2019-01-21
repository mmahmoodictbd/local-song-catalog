<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style type="text/css">
.table a {
	color: #565656;
}

.table a:HOVER {
	text-decoration: none;
	color: #333333;
}

th.iconBtn {
	max-width: 12px;
	height: 20px;
}
</style>
<script type="text/javascript">
	$(document).ready(function() {

		$(".confirm-delete").click(function() {
			$("#deleteTag").val($(this).data('tagname'));

		});
		$(".confirm-edit").click(function() {
			$("#oldTagName").val($(this).data('tagname'));

		});

	});
</script>
<title>Tags</title>
</head>
<body>

	<c:if test="${!success && not empty msg}">
		<div class="alert alert-info">
			<a class="close" data-dismiss="alert">x</a> <strong>Info!</strong> <span>${msg}</span>
		</div>
	</c:if>
	<c:if test="${success && not empty msg}">
		<div class="alert alert-success">
			<a class="close" data-dismiss="alert">x</a> <strong>Success!</strong>
			<span>${msg}</span>
		</div>
	</c:if>


	<table class="table table-condensed">
		<thead>
			<tr>
				<th class="span3">Tags</th>
				<th class="iconBtn"></th>
				<th class="iconBtn"></th>
				<th class="span2"></th>
				<th class="span3"></th>
				<th class="iconBtn"></th>
				<th class="iconBtn"></th>
				<th class="span2"></th>
				<th class="span3"></th>
				<th class="iconBtn"></th>
				<th class="iconBtn"></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="tag" varStatus="status" begin="0"
				end="${fn:length(tags)}" step="3" items="${tags}">
				<tr>
					<td><a
						href="${contextPath}/searchSong?searchString=${tag}&searchExcludeFileName=on&searchExcludePlaylists=on&searchExcludeFilePath=on&searchExcludeLyrics=on"><i
							class="icon-tags"></i> ${tag}</a></td>
					<td><a class="confirm-edit" href="#edit" data-toggle="modal"
						data-tagname="${tag}"><i class="icon-pencil"></i></a></td>
					<td><a class="confirm-delete" href="#delete"
						data-toggle="modal" data-tagname="${tag}"><i
							class="icon-trash"></i></a></td>

					<td></td>

					<c:choose>
						<c:when test="${not empty tags[status.index+1]}">
							<td><a
								href="${contextPath}/searchSong?searchString=${tags[status.index+1]}&searchExcludeFileName=on&searchExcludePlaylists=on&searchExcludeFilePath=on&searchExcludeLyrics=on"><i
									class="icon-tags"></i> ${tags[status.index+1]}</a></td>
							<td><a class="confirm-edit" href="#edit" data-toggle="modal"
								data-tagname="${tags[status.index+1]}"><i
									class="icon-pencil"></i></a></td>
							<td><a class="confirm-delete" href="#delete"
								data-toggle="modal" data-tagname="${tags[status.index+1]}"><i
									class="icon-trash"></i></a></td>
						</c:when>
						<c:otherwise>
							<td></td>
							<td></td>
							<td></td>
						</c:otherwise>
					</c:choose>

					<td></td>

					<c:choose>
						<c:when test="${not empty tags[status.index+2]}">
							<td><a
								href="${contextPath}/searchSong?searchString=${tags[status.index+2]}&searchExcludeFileName=on&searchExcludePlaylists=on&searchExcludeFilePath=on&searchExcludeLyrics=on"><i
									class="icon-tags"></i> ${tags[status.index+2]}</a></td>
							<td><a class="confirm-edit" href="#edit" data-toggle="modal"
								data-tagname="${tags[status.index+2]}"><i
									class="icon-pencil"></i></a></td>
							<td><a class="confirm-delete" href="#delete"
								data-toggle="modal" data-tagname="${tags[status.index+2]}"><i
									class="icon-trash"></i></a></td>
						</c:when>
						<c:otherwise>
							<td></td>
							<td></td>
							<td></td>
						</c:otherwise>
					</c:choose>
				</tr>
			</c:forEach>
		</tbody>
	</table>


	<div id="edit" class="modal hide fade in" style="display: none;">
		<form class="form-horizontal" method="POST"
			action="${contextPath}/tags">
			<fieldset>
				<input type="hidden" id="oldTagName" name="oldTagName" value="" />
				<div class="modal-header">
					<a class="close" data-dismiss="modal">x</a>
					<h3>Update Tag</h3>
				</div>
				<div class="modal-body">
					<p>
						<input type="text" class="span4" name="newTagName" id="newTagName"
							placeholder="Type New Tag Name">
					</p>
				</div>
				<div class="modal-footer">
					<button type="submit" class="btn btn-success">Update</button>
					<button class="btn" data-dismiss="modal">Close</button>
				</div>
			</fieldset>
		</form>
	</div>

	<div id="delete" class="modal hide fade" style="display: none;">
		<form class="form-horizontal" method="POST"
			action="${contextPath}/tags">
			<fieldset>
				<input type="hidden" id="deleteTag" name="delete" value="" />
				<div class="modal-header">
					<h3>Delete Tag</h3>
				</div>
				<div class="modal-body">
					<p>You are about to delete one tag, this procedure is
						irreversible.</p>
					<p>Do you want to proceed?</p>
				</div>
				<div class="modal-footer">
					<button type="submit" class="btn btn-danger">Delete</button>
					<button class="btn" data-dismiss="modal">Close</button>
				</div>
			</fieldset>
		</form>
	</div>
</body>
</html>
