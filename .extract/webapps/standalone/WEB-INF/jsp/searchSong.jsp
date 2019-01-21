<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="../../bootstrap/css/bootstrap-tagmanager.css"
	rel="stylesheet">
<script src="../../bootstrap/js/bootstrap-tagmanager.js"></script>
<script type="text/javascript">
	$(document).ready(
			function() {

				$(".tagManager").each(function(index) {

					var prefilledData;
					var persistedTags = $(this).data('tags');

					if (typeof persistedTags === "undefined") {
						prefilledData = [];
					} else {
						prefilledData = persistedTags.split(',');
					}

					$(".tagManager:eq(" + index + ")").tagsManager({
						prefilled : prefilledData,
						CapitalizeFirstLetter : true,
						preventSubmitOnEnter : true,
						typeahead : true,
						typeaheadAjaxSource : null,
						typeaheadSource : ${searchResultBean.tagList},
						delimeters : [ 44, 188, 13 ],
						backspace : [ 8 ],
						blinkBGColor_1 : '#FFFF9C',
						blinkBGColor_2 : '#CDE69C',
						hiddenTagListName : 'hiddenTagList-' + index
					});

				});
				$('.submitTagBtn').click(
						function() {

							var tags = $(this).parent().find(
									'input:hidden:first').attr('value');
							var songId = $(this).parent().attr('id').substr(9);
							var dataString = 'tags=' + tags + '&songId='
									+ songId;
							$.ajax({
								type : "POST",
								url : "/updateSongTag",
								data : dataString,
								success : function() {
									console.log('success');
								}
							
							});
						});
				

				$('.tagManager').focus(
						function(){
							$(this).parent().find('.submitTagForAllBtn:eq(0)').css('display','').fadeOut(0).fadeIn(1000);
						});
				$('.tagManager').focusout(
						function(){
							var submitTagForAllBtn = $(this).parent().find('.submitTagForAllBtn:eq(0)');
							submitTagForAllBtn.fadeOut(1000, function(){
								submitTagForAllBtn.css('display','none');
							});
						});
				
				$('.submitTagForAllBtn').click(
						function() {
							
							var songIds = '';
							var tags = $(this).parent().find('input:hidden:first').attr('value');			
							
							$(".submitTagForAllBtn").each(function(i) {
								songIds += $(this).parent().attr('id').substr(9);
								if (i != $(".submitTagForAllBtn").length-1){
									songIds += ",";
								}
							});
							
							var dataString = 'tags=' + tags + '&songIds='
							+ songIds;

							$.ajax({
								type : "POST",
								url : "/updateSongTag?saveall=true",
								data : dataString,
								success : function() {
									console.log('success');
									window.location.reload(true);
								}
							
							});
						});
				
				
				
				
				
				
				
				$(".playlistsTagManager").each(function(index) {

					var prefilledData;
					var persistedTags = $(this).data('tags');

					if (typeof persistedTags === "undefined") {
						prefilledData = [];
					} else {
						prefilledData = persistedTags.split(',');
					}

					$(".playlistsTagManager:eq(" + index + ")").tagsManager({
						prefilled : prefilledData,
						CapitalizeFirstLetter : true,
						preventSubmitOnEnter : true,
						typeahead : true,
						typeaheadAjaxSource : null,
						typeaheadSource : ${searchResultBean.playlistsTagList},
						delimeters : [ 44, 188, 13 ],
						backspace : [ 8 ],
						blinkBGColor_1 : '#FFFF9C',
						blinkBGColor_2 : '#CDE69C',
						hiddenTagListName : 'hiddenPlaylistsTagList-' + index
					});

				});
				$('.submitPlaylistsTagBtn').click(
						function() {

							var tags = $(this).parent().find(
									'input:hidden:first').attr('value');
							var songId = $(this).parent().attr('id').substr(14);
							var dataString = 'tags=' + tags + '&songId='
									+ songId;
							$.ajax({
								type : "POST",
								url : "/updatePlaylistsTag",
								data : dataString,
								success : function() {
									console.log('success');
								}
							
							});
						});
				

				$('.playlistsTagManager').focus(
						function(){
							$(this).parent().find('.submitPlaylistsTagForAllBtn:eq(0)').css('display','').fadeOut(0).fadeIn(1000);
						});
				$('.playlistsTagManager').focusout(
						function(){
							var submitPlaylistsTagForAllBtn = $(this).parent().find('.submitPlaylistsTagForAllBtn:eq(0)');
							submitPlaylistsTagForAllBtn.fadeOut(1000, function(){
								submitPlaylistsTagForAllBtn.css('display','none');
							});
						});
				
				$('.submitPlaylistsTagForAllBtn').click(
						function() {
							
							var songIds = '';
							var tags = $(this).parent().find('input:hidden:first').attr('value');			
							
							$(".submitPlaylistsTagForAllBtn").each(function(i) {
								songIds += $(this).parent().attr('id').substr(14);
								if (i != $(".submitPlaylistsTagForAllBtn").length-1){
									songIds += ",";
								}
							});
							
							var dataString = 'tags=' + tags + '&songIds='
							+ songIds;

							$.ajax({
								type : "POST",
								url : "/updatePlaylistsTag?saveall=true",
								data : dataString,
								success : function() {
									console.log('success');
									window.location.reload(true);
								}
							
							});
						});
				
				
				
				$('.playBtn').click(
						function() {
							var audioObj = $(this).parent().find('audio').get(0);
							console.log(audioObj);
							if(audioObj.paused) {
								audioObj.play();
								$(this).text('Pause');
							} else {
								audioObj.pause();
								$(this).text('Play');								
							}
							
							if(audioObj.error !=null){
								audioObj.load();
								audioObj.play();
								$(this).text('Pause');
							}
							
							audioObj.addEventListener('timeupdate', function showTime() {
								var prog = parseInt(audioObj.currentTime / 60) + ':' + parseInt(audioObj.currentTime) % 60;
								$(this).parent().find('.duration:eq(0)').text(prog);
							});
						});
				
				$('.volUpBtn').click(
						function() {
							var audioObj = $(this).parent().find('audio').get(0);
							if(audioObj.volume < 1) {
								audioObj.volume+=0.1;
							}
						});
				
				$('.volDownBtn').click(
						function() {
							var audioObj = $(this).parent().find('audio').get(0);
							if(audioObj.volume > 0) {
								audioObj.volume-=0.1;
							}
						});
				
				$('.deleteRow').click(
						function() {
							$(this).closest('tr').remove();;
						});
		
			});
			
</script>
<title>SongList</title>
</head>
<body>

	<div class="span9">
		<form class="form-inline" action="searchSong" method="get">
			<input name="searchString" value="${searchResultBean.searchString}"
				type="text" class="input-xxlarge" placeholder="Search">
			<button type="submit" class="btn">Search</button>
			<label class="checkbox"><input name="searchOnlyUntagged"
				type="checkbox" ${searchResultBean.searchOnlyUntagged}>
				Search Only Untagged</label>
		</form>
	</div>

	<table class="table table-condensed table-bordered table-striped">
		<thead>
			<tr>
				<th style="width: 20%">File Name</th>
				<th style="width: 20%">Tags</th>
				<th style="width: 20%">Play</th>
				<th style="width: 20%">Playlists</th>
				<th style="width: 19%">File Path</th>
				<th style="width: 1%"></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="song" items="${searchResultBean.songList}"
				varStatus="loop">
				<tr>
					<td>${song.fileName}</td>
					<td>
						<form id="tagsForm-${song.id}" class="form-inline">

							<c:choose>
								<c:when test="${not empty song.tags}">
									<input type="text" name="tags${song.id}" placeholder="Tags"
										class="tagManager" data-tags="${song.tags}" />
								</c:when>
								<c:otherwise>
									<input type="text" name="tags${song.id}" placeholder="Tags"
										class="tagManager" />
								</c:otherwise>
							</c:choose>

							<button class="btn btn-mini submitTagBtn" type="button">Save
								Tags</button>
							<button style="display: none"
								class="btn btn-mini submitTagForAllBtn" type="button">Save
								Tags For All</button>
						</form>
					</td>
					<td><audio src="playSong?path=${song.canonicalPath}"
							preload="none" loop></audio>
						<button class="btn btn-mini playBtn" type="button">Play</button>
						<button class="btn btn-mini volUpBtn" type="button">Vol+</button>
						<button class="btn btn-mini volDownBtn" type="button">Vol-</button>
						<button class="btn btn-mini duration" type="button"></button></td>
					<td>
						<form id="playlistsForm-${song.id}" class="form-inline">

							<c:choose>
								<c:when test="${not empty song.playliststags}">
									<input type="text" name="playlistsTags${song.id}"
										placeholder="Tags" class="playlistsTagManager"
										data-tags="${song.playliststags}" />
								</c:when>
								<c:otherwise>
									<input type="text" name="playlistsTags${song.id}"
										placeholder="Tags" class="playlistsTagManager" />
								</c:otherwise>
							</c:choose>

							<button class="btn btn-mini submitPlaylistsTagBtn" type="button">Save
								Playlists</button>
							<button style="display: none"
								class="btn btn-mini submitPlaylistsTagForAllBtn" type="button">Save
								Playlists For All</button>
						</form>
					</td>
					<td>${song.canonicalPath}</td>
					<td><a class="close deleteRow">×</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>




</body>
</html>
