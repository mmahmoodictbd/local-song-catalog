<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>

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

		$("td").each(function(index) {

			var persistedTags = $(this).data('tags');

			var prefilledData;
			if (typeof persistedTags === "undefined") {
				prefilledData = [];
			} else {
				prefilledData = persistedTags.split(',');
				var innerHtml = '';
				jQuery.each(prefilledData, function(index, item) {
					innerHtml += '<span class="label label-info">' + item;
					innerHtml += "</span>&nbsp";
				});
				$(this).html(innerHtml);
			}

		});

		$('.delete').click(function() {
			var closestTr = $(this).closest('tr');
			var songId = $(this).data('songid');
			var dataString = 'delete=' + songId;
			$.ajax({
				type : "GET",
				url : "${contextPath}/orphanedSongList",
				data : dataString,
				success : function() {
					closestTr.remove();
				}
			});
		});
		
		$('#iamFeelingLucky').click(function(e) {
			e.preventDefault(); 
			$.ajax({
				type : "GET",
				url : "${contextPath}/iAmFeelingLucky",
				success : function(data) {
					if(data > 0) {
						$('#iAmFeelingLuckyMsg').text(data + ' orphaned song restored :-)');
					} else {
						$('#iAmFeelingLuckyMsg').text('No orphaned song can be restored.');
					}
					$('#iAmFeelingLuckyMsg').css('display','').fadeIn('slow').trigger('change');
				}
			});
		});	
	});
</script>

<title>Orphaned Song</title>
</head>
<body>
	
	<form class="form-inline" action="searchDiskMp3" method="get">
		<input id="diskMp3Path" name="diskMp3Path" value="${diskMp3Path}"
			type="text" class="input-xxlarge"
			placeholder="Enter path for search mp3" autocomplete="off">
		<button type="submit" class="btn">Search on HardDisk</button>
		<a id="iamFeelingLucky" class="btn">I'm Feeling Lucky</a>
	</form>

	<div class="alert alert-info flash-message" data-delay="5000">
		<button type="button" class="close" data-dismiss="alert">&times;</button>
		<h4>
			<i class="icon-lightbulb icon-2x"></i> Orphaned Songs!
		</h4>
		These song are tagged earlier, but the file is not exist anymore. Scan
		your harddisk for these song!<br> If you do not want these
		anymore, you may <strong>delete</strong>.
	</div>

	<div id="iAmFeelingLuckyMsg" class="alert alert-info flash-message" data-delay="5000" style="display:none">
		<button type="button" class="close" data-dismiss="alert">&times;</button>
		<h4>
			<i class="icon-lightbulb icon-2x"></i> Orphaned Songs!
		</h4>
	</div>
	
	<table class="table table-condensed table-bordered table-striped">
		<thead>
			<tr>
				<th style="width: 25%">File Name</th>
				<th style="width: 20%">Tags</th>
				<th style="width: 20%">Playlists</th>
				<th style="width: 30%">Old File Path</th>
				<th style="width: 1%"></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="song" items="${orphanedSongList}" varStatus="loop">
				<tr>
					<td>${song.originalFileName}</td>
					<td data-tags="${song.tags}"></td>
					<td data-tags="${song.playliststags}"></td>
					<td>${song.prettifiedCanonicalPath}</td>
					<td><a class="delete" href="#" data-songId="${song.id}"><i
							class="icon-trash"></i></a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>
