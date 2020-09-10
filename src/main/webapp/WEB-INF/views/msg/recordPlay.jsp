<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>play</title>

<link rel="stylesheet" href="/assets/css/base.css?20191125_006">
<link rel="stylesheet" href="/assets/css/slick.css?20191114_004">
<link rel="stylesheet" href="/assets/css/style.css?20191127_001">

<link rel="stylesheet" href="/assets/js/jqueryui/jquery-ui.css?20191111_001">
<link rel="stylesheet" href="/assets/js/jqueryui/jquery.custom.css?20191111_001">

<script type="text/javascript" src="/assets/js/jquery-1.11.2.min.js?20191109_001"></script>
<script type="text/javascript" src="/assets/js/jqueryui/jquery-ui.js?20191109_001"></script>
<script type="text/javascript" src="/assets/js/jquery.function.js?20191109_001"></script>

<script type="text/javascript" src="/assets/js/common.js?20191206_001"></script>
<script type="text/javascript" src="/assets/js/slick.js?20191114_001"></script>
<script type="text/javascript" src="/assets/js/style.js?20191125_003"></script>

<script type="text/javascript" src="/assets/js/dmaps/postcode.v2.js"></script>

<!-- datepicker -->
<link rel="stylesheet" href="/assets/js/datepicker/jquery-ui-1.8.18.css" type="text/css" />
<script type="text/javascript" src="/assets/js/datepicker/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="/assets/js/datepicker/jquery-ui-1.8.18.min.js"></script>
<!-- //datepicker -->

<link rel="stylesheet" type="text/css" href="/assets/jplayer/skin/blue.monday/css/jplayer.blue.monday.css"/>
<script type="text/javascript" src="/assets/jplayer/jquery.jplayer.min.js"></script>

<style>
html { overflow:hidden; }
.jp-audio .jp-interface {height:101px;}
</style>

</head>
<body>
	<div id="jquery_jplayer_1" class="jp-jplayer"></div>
	<div id="jp_container_1" class="jp-audio" role="application" aria-label="media player">
		<div class="jp-type-single">
			<div class="jp-gui jp-interface">
				<div class="jp-controls">
					<button class="jp-play" role="button" tabindex="0">play</button>
					<button class="jp-stop" role="button" tabindex="0">stop</button>
				</div>
				<div class="jp-progress">
					<div class="jp-seek-bar">
						<div class="jp-play-bar"></div>
					</div>
				</div>
				<div class="jp-volume-controls">
					<button class="jp-mute" role="button" tabindex="0">mute</button>
					<button class="jp-volume-max" role="button" tabindex="0">max volume</button>
					<div class="jp-volume-bar">
						<div class="jp-volume-bar-value"></div>
					</div>
				</div>
				<div class="jp-time-holder">
					<div class="jp-current-time" role="timer" aria-label="time">&nbsp;</div>
					<div class="jp-duration" role="timer" aria-label="duration">&nbsp;</div>
					<div class="jp-toggles">
						<button class="jp-repeat" role="button" tabindex="0">repeat</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>

<script type="text/javascript">
$(document).ready(function(){
	var fileUrl = "${fileUrl}";
	if(-1 < fileUrl.indexOf("auth-test"))
	{
		fileUrl = "http://222.110.144.33:43404/uploadVMS/"+"${fileUrl}";
	}
	else
	{
		fileUrl = "http://localhost:8080/uploadfileVMS/" + fileUrl;
// 		fileUrl = "http://localhost:8080/uploadfileVMS/20200908/20200908114912STWQz_4mKwprBxc.wav";
	}
	
	console.log(fileUrl);

	$("#jquery_jplayer_1").jPlayer({
		ready: function (event) {
			$(this).jPlayer("setMedia", {
				wav : fileUrl
// 				wav: "https://dev01.ring2pay.com:9008//ttsSWavStorage//Inbiznet//20191206//4mKwprBxc//4mKwprBxc.wav"
			});
		},
		swfPath: "/assets/jplayer",
		supplied: "wav",
		wmode: "window",
		useStateClassSkin: true,
		autoBlur: false,
		smoothPlayBar: true,
		keyEnabled: true,
		remainingDuration: true,
		toggleDuration: true
	});
});
</script>
</html>