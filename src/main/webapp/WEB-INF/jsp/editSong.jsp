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

	<div class="span9">
		<form class="form-horizontal" method="POST"
			action="${contextPath}/editSong?update=true">
			<fieldset>
				<div id="legend">
					<legend class="">Update Song</legend>
				</div>

				<input type="hidden" name="id" value="${song.id}" />

				<div class="control-group">
					<label class="control-label">Title</label>
					<div class="controls">
						<input id="filename" name="filename" type="text"
							value="${song.fileName}" class="span8" placeholder="Name">
					</div>
				</div>

				<div class="control-group">
					<label class="control-label">Lyrics</label>
					<div class="controls">
						<textarea class="span8" id="lyrics" name="lyrics"
							placeholder="lyrics..." rows="7">${song.lyrics}</textarea>
					</div>
				</div>


				<div class="control-group">
					<div class="controls">
						<button class="btn">Update</button>
						<a class="btn confirm-delete" href="#delete" data-toggle="modal">Delete</a>
					</div>
				</div>



			</fieldset>
		</form>
	</div>

	<div id="delete" class="modal hide fade" style="display: none;">
		<form class="form-horizontal" method="POST"
			action="${contextPath}/editSong?delete=true">
			<fieldset>
				<input type="hidden" name="id" value="${song.id}" />
				<div class="modal-header">
					<h3>Delete Song</h3>
				</div>
				<div class="modal-body">
					<p>You are about to delete a song, this song will be deleted
						from software and original song in Harddisk remain as it is. This
						procedure is irreversible.</p>
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
