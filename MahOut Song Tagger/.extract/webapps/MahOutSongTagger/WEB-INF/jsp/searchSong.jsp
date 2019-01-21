<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<style type="text/css">
.table a {
	color: #333333;
}

.table a:HOVER {
	text-decoration: none;
	color: #565656;
}
</style>

<link href="${contextPath}/css/bootstrap-tagmanager.css" rel="stylesheet">
<link href="${contextPath}/css/inlineplayer.css" rel="stylesheet">
<script src="${contextPath}/js/bootstrap-tagmanager.js"></script>
<script src="${contextPath}/js/soundmanager2.js"></script>
<script src="${contextPath}/js/inlineplayer.js"></script>
<script>
soundManager.setup({
	url: '${contextPath}/swf/'
});
</script>
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
								url : "${contextPath}/updateSongTag",
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
								url : "${contextPath}/updateSongTag?saveall=true",
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
								url : "${contextPath}/updatePlaylistsTag",
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
								url : "${contextPath}/updatePlaylistsTag?saveall=true",
								data : dataString,
								success : function() {
									console.log('success');
									window.location.reload(true);
								}
							
							});
						});
			
			
				$('.playBtn').click(
						function() {
								var playBtn = $(this);	
								inlinePlayer.events = {
									play: function() {
										playBtn.text('Pause');
									},
									resume: function() {
										playBtn.text('Pause');
									},
									pause: function() {
										playBtn.text('Play');
									},
									stop: function() {
										playBtn.text('Play');
									}
								}
						});
				
				$('.volUpBtn').click(
						function() {
							var soundId = 'inlineMP3Sound' + $(this).closest('td').find('.playBtn').data('songid');
							if(soundManager.getSoundById(soundId) != null && soundManager.defaultOptions.volume < 81) {
								soundManager.defaultOptions.volume = soundManager.defaultOptions.volume + 20;
								soundManager.setVolume(soundId, soundManager.defaultOptions.volume);
							}
						});
				
				$('.volDownBtn').click(
						function() {
							var soundId = 'inlineMP3Sound' + $(this).closest('td').find('.playBtn').data('songid');
							if(soundManager.getSoundById(soundId) != null && soundManager.defaultOptions.volume > 19) {
								soundManager.defaultOptions.volume = soundManager.defaultOptions.volume - 20;
								soundManager.setVolume(soundId, soundManager.defaultOptions.volume);
							}
						});
				
				$('.seekBtn').click(
						function() {
							var soundId = 'inlineMP3Sound' + $(this).closest('td').find('.playBtn').data('songid');
							var mySMSound = soundManager.getSoundById(soundId);
							if(mySMSound != null && mySMSound.readyState == 3){
								soundManager.setPosition(soundId, mySMSound.position+5000);
							}
						});
				
				$('.deleteRow').click(
						function() {
							$(this).closest('tr').remove();;
						});
				
				$('[id^=tagsForm]').each(function() {
					$(this).change(function() {
						$(this).find('.submitTagBtn:eq(0)').removeAttr('disabled');
						$(this).find('.submitTagForAllBtn:eq(0)').removeAttr('disabled');
					});
				});
				$('[id^=playlistsForm]').each(function() {
					$(this).change(function() {
						$(this).find('.submitPlaylistsTagBtn:eq(0)').removeAttr('disabled');
						$(this).find('.submitPlaylistsTagForAllBtn:eq(0)').removeAttr('disabled');
					});
				});
				
				$('.submitTagBtn').each(function() {
					$(this).click(function() {
						$(this).attr("disabled", true);
						$(this).parent().find('.submitTagForAllBtn:eq(0)').attr("disabled", true);
					});
				});
				$('.submitTagForAllBtn').each(function() {
					$(this).click(function() {
						$(this).attr("disabled", true);
						$(this).parent().find('.submitTagBtn:eq(0)').attr("disabled", true);
					});
				});
				$('.submitPlaylistsTagBtn').each(function() {
					$(this).click(function() {
						$(this).attr("disabled", true);
						$(this).parent().find('.submitPlaylistsTagForAllBtn:eq(0)').attr("disabled", true);
					});
				});
				$('.submitPlaylistsTagForAllBtn').each(function() {
					$(this).click(function() {
						$(this).attr("disabled", true);
						$(this).parent().find('.submitPlaylistsTagBtn:eq(0)').attr("disabled", true);
					});
				});
				
				$('#advancedSearchTools input:checked').each(function() {
						$('#advancedSearchTools').show();
				});
 
				$('#advSearchBtn').click(function(e) {
						e.preventDefault();
						$('#advancedSearchTools').toggle();
				});
				
		
			});
			
</script>
<title>SongList</title>
</head>
<body>

	<div class="span10">
		<form class="form-inline" action="searchSong" method="get">

			<input name="searchString" value="${searchResultBean.searchString}"
				type="text" class="input-xxlarge" placeholder="Search" autofocus>
			<button type="submit" class="btn">
				<i class="icon-search"></i> Search
			</button>
			<a id="advSearchBtn" href=""> &nbsp;Advanced Search</a>


			<div id="advancedSearchTools" class="row-fluid" style="display: none">
				<c:choose>
					<c:when test="${searchResultBean.searchOnlyUntagged}">
						<label class="checkbox"><input name="searchOnlyUntagged"
							type="checkbox" checked> Only Untagged</label>
					</c:when>
					<c:otherwise>
						<label class="checkbox"><input name="searchOnlyUntagged"
							type="checkbox"> Only Untagged</label>
					</c:otherwise>
				</c:choose>

				<c:choose>
					<c:when test="${searchResultBean.searchExcludeFileName}">
						<label class="checkbox"><input
							name="searchExcludeFileName" type="checkbox" checked>Exclude
							FileName</label>
					</c:when>
					<c:otherwise>
						<label class="checkbox"><input
							name="searchExcludeFileName" type="checkbox">Exclude
							FileName</label>
					</c:otherwise>
				</c:choose>


				<c:choose>
					<c:when test="${searchResultBean.searchExcludeTags}">
						<label class="checkbox"><input name="searchExcludeTags"
							type="checkbox" checked>Exclude Tags</label>
					</c:when>
					<c:otherwise>
						<label class="checkbox"><input name="searchExcludeTags"
							type="checkbox">Exclude Tags</label>
					</c:otherwise>
				</c:choose>

				<c:choose>
					<c:when test="${searchResultBean.searchExcludePlaylists}">
						<label class="checkbox"><input
							name="searchExcludePlaylists" type="checkbox" checked>Exclude
							Playlists</label>
					</c:when>
					<c:otherwise>
						<label class="checkbox"><input
							name="searchExcludePlaylists" type="checkbox">Exclude
							Playlists</label>
					</c:otherwise>
				</c:choose>

				<c:choose>
					<c:when test="${searchResultBean.searchExcludeFilePath}">
						<label class="checkbox"><input
							name="searchExcludeFilePath" type="checkbox" checked>Exclude
							FilePath</label>
					</c:when>
					<c:otherwise>
						<label class="checkbox"><input
							name="searchExcludeFilePath" type="checkbox">Exclude
							FilePath</label>
					</c:otherwise>
				</c:choose>


				<c:choose>
					<c:when test="${searchResultBean.searchExcludeLyrics}">
						<label class="checkbox"><input name="searchExcludeLyrics"
							type="checkbox" checked>Exclude Lyrics</label>
					</c:when>
					<c:otherwise>
						<label class="checkbox"><input name="searchExcludeLyrics"
							type="checkbox">Exclude Lyrics</label>
					</c:otherwise>
				</c:choose>

			</div>
		</form>
	</div>

	<table class="table table-condensed table-bordered table-striped">
		<thead>
			<tr>
				<th style="width: 20%">Title</th>
				<th style="width: 20%">Tags</th>
				<th style="width: 2%">Play</th>
				<th style="width: 20%">Playlists</th>
				<th style="width: 37%">File Path</th>
				<th style="width: 1%"></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="song" items="${searchResultBean.songList}"
				varStatus="loop">
				<tr>
					<td><a href="${contextPath}/editSong?id=${song.id}">${song.fileName}</a></td>
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

							<button class="btn btn-mini submitTagBtn" type="button" disabled>Save
								Tags</button>
							<button style="display: none"
								class="btn btn-mini submitTagForAllBtn" type="button" disabled>Save
								Tags For All</button>
						</form>
					</td>
					<td>
						<a href="${contextPath}/playSong?path=${song.canonicalPath}" class="btn btn-mini playBtn sm2_link inline-playable" data-songId="${song.id}">Play</a>	
						<a class="btn btn-mini volUpBtn">Vol+</a>
						<a class="btn btn-mini volDownBtn">Vol-</a>
						<a class="btn btn-mini seekBtn">>></a></td>
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

							<button class="btn btn-mini submitPlaylistsTagBtn" type="button"
								disabled>Save Playlists</button>
							<button style="display: none"
								class="btn btn-mini submitPlaylistsTagForAllBtn" type="button"
								disabled>Save Playlists For All</button>
						</form>
					</td>
					<td><a
						href="${contextPath}/searchSong?searchString=${song.prettifiedCanonicalPath}">${song.prettifiedCanonicalPath}</a></td>
					<td><a class="close deleteRow"><i class="icon-remove"></i></a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>




</body>
</html>
