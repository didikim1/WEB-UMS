<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/x-handlebars-template" id="listTemplate">
{{#resultData}}
<li class="template">
	<ul class="receiverList">
		<li class="rName" name="name"><a>{{name}}</a></li>
		<li class="phonenumber" name="phonenumber" maxlength="13"><a>{{phonenumber}}</a></li>
		<li><button type="button" id="{{seqtarget}}" class="btn_adress btnDelete">삭제</button></li>
	</ul>
</li>
{{/resultData}}
</script>
<script type="text/x-handlebars-template" id="survey1">
<div class="surveyInner">
	<div class="ttsWriteBox">
		<textarea rows="5" cols="155" name="ttsMent2" maxlength="1000">[전달 내용 입력]</textarea>
	</div>
</div>
</script>
<script type="text/x-handlebars-template" id="survey2">
<div class="surveyInner">
	<div class="ttsWriteBox">
		<textarea rows="5" cols="155" name="ttsMent2" maxlength="1000">[전달 내용 입력]</textarea>
	</div>
	<div class="ttsWriteBox">
		<textarea rows="1" cols="155" name="ttsMent3" maxlength="100">이상 안내드린 사항에 동의하시면 1번, 동의하지 않으시는 경우 2번, 그외 기타사항은 3번  (수정가능)</textarea>
	</div>
</div>
</script>

<script type="text/javascript">
// 모음함 불러오기 버튼
function fn_TTSList()
{
	var url='/msg/TTSList';
	var popupX = window.screenLeft+(((document.body.clientWidth)*0.5)-273);
 	var popupY = window.screenTop+(((window.outerHeight)*0.5)-310);
	var option = 'width=546, height=623, left='+popupX+', top='+popupY+', resizable=no, scrollbars=no, status=no;';
	window.open(url, 'TTSList', option);
}

// 음성파일 첨부 불러오기
function fn_TTSUpload()
{
	var url='/msg/TTSUpload';
	var popupX = window.screenLeft+(((document.body.clientWidth)*0.5)-273);
 	var popupY = window.screenTop+(((window.outerHeight)*0.5)-190);
	var option = 'width=546, height=387, left='+popupX+', top='+popupY+', resizable=no, scrollbars=no, status=no;';
	window.open(url, 'TTSUpload', option);
}

// template으로 list 추가
// function useTemplate(datas)
// {
// 	var seqgroup = $("[name=seqgroup]").val();
// 	if(seqgroup == "" || seqgroup == null)
// 	{
// 		// temp_group 테이블에 group 만들고
		
// 		// seqgroup 값을 요기 넣고
// 		$("[name=seqgroup]").val("00000");
// 	}
	
// 	var dataArr = new Array();

// 	datas.forEach(function(element){
// 		var phoneArr = new Array();
// 		for(var i=0; i<$(".phonenumber").length; i++)
// 		{
// 			phoneArr.push($(".phonenumber").eq(i).find("a").text());
// 		}

// 		if(phoneArr.indexOf(element.phonenumber) < 0)
// 		{
// 			dataArr.push(element);
			
// 			// temp_target 테이블에 target insert
// 		}
// 	});

// 	var source 		= $("#listTemplate").html();
// 	var template 	= Handlebars.compile(source);
// 	var data 		= {resultData : dataArr};
// 	var html 		= template(data);

// 	$("#name").val("");
// 	$("#phonenumber").val("");

// 	$("#rList").append(html);
// 	var listLength = $("#rList").find("ul").length;
// 	$(".receiverBtnBox").find(".viewCnt").html("총 "+listLength);
// }
function useTemplate(datas)
{
	var dataArr = new Array();

	datas.forEach(function(element){
		var phoneArr = new Array();
		for(var i=0; i<$(".phonenumber").length; i++)
		{
			phoneArr.push($(".phonenumber").eq(i).find("a").text());
		}

		if(phoneArr.indexOf(element.phonenumber) < 0)
		{
			dataArr.push(element);
		}
	});

	var source 		= $("#listTemplate").html();
	var template 	= Handlebars.compile(source);
	var data 		= {resultData : dataArr};
	var html 		= template(data);

	$("#name").val("");
	$("#phonenumber").val("");

	$("#rList").append(html);
	var listLength = $("#rList").find("ul").length;
	$(".receiverBtnBox").find(".viewCnt").html("총 " + listLength);
// 	$(".receiverBtnBox").find(".viewCnt").html("총 "+listLength+"명");
}

// 엑셀 업로드 후 필요한 페이징 정보 가져와서 저장.
function setParams(result)
{
	if((result.list.length) == 0)
	{
		$("#noNextData").val("Y");
	}
	else
	{
		$("#seqgroup").val(result.list[0].seqgroup);
		$("[name=seqgroup]").val(result.list[0].seqgroup);
	}
	
	var page 		= result.paginationInfo.currentPageNo;
	var lastPageNum = result.paginationInfo.lastPageNo;
	
	$("#senderPageNum").val(Number(page) + 1);
	$("#lastPageNum").val(lastPageNum);
}

// 엑셀 업로드 후 업로드 된 총 인원 수 저장
var listSize = 0;
function setListSize(paramMap)
{
	if(paramMap == undefined)
	{
		listSize += 1;
	}
	else
	{
		listSize += paramMap.listSize;
	}
	$("#listSize").val(listSize);
	$(".receiverBtnBox").find(".totalCnt").html("/" + listSize + "명");
}


// 멘트 이어붙이기
function makeTTSMent()
{
	var ttsMent1 = $("[name=ttsMent1]");
	var ttsMent2 = $("[name=ttsMent2]");
	var ttsMent3 = $("[name=ttsMent3]");
	var ttsMent  = "";

	// 알림형
	if(ttsMent3.length == 0)
	{
		ttsMent = ttsMent1.val() + "<br/>" + ttsMent2.val();
	}
	// 응답형 (질문 1개)
	else if(ttsMent3.length == 1)
	{
		ttsMent = ttsMent1.val() + "<br/>" + ttsMent2.val() + "<br/>" + ttsMent3.val();
		ttsMent += ", 다시 들으시려면 별표를, 취소는 #버튼을 눌러주세요.";
	}
	// 응답형 (질문 2개 이상)
	else
	{
		ttsMent += ttsMent1.val();
		for(var i=0; i<ttsMent3.length; i++)
		{
			ttsMent +=  "<br/>" + ttsMent2.eq(i).val();
			ttsMent +=  "<br/>" + ttsMent3.eq(i).val();
			ttsMent += ", 다시 들으시려면 별표를, 취소는 #버튼을 눌러주세요.";
		}
	}

	return ttsMent;
}

// 음성파일 첨부하여 콜 보내기
function fn_SendCallByWav()
{
	// 수신자 리스트
	var targetArr = new Array();
	for(var i=0; i<$(".receiverList").length; i++)
	{
		var obj = {
			 name 			: $(".receiverList").eq(i).find("[name=name]").find("a").text()
			,phonenumber 	: $(".receiverList").eq(i).find("[name=phonenumber]").find("a").text()
		}

		targetArr.push(obj);
	}
	$("[name=targetArr]").val(JSON.stringify(targetArr));

	// 예약 시간
	var nextcallDate = "";
	if($("[name=sendTime]:checked").val() == "B")
	{
		var date 		= new Date();
		var currentDate = date.getFullYear() + '-' + (date.getMonth()+1) + '-' + date.getDate() + ' ' + date.getHours() + ':' + date.getMinutes();
		nextcallDate 	= $("[name=sendDay]").val() + ' ' + $("[name=sendHour]").val() + ':' + $("[name=sendMinute]").val();
	}
	else if($("[name=sendTime]:checked").val() == "C")
	{
		var date 		= new Date();
		var currentDate = date.getFullYear() + '-' + (date.getMonth()+1) + '-' + date.getDate() + ' ' + date.getHours() + ':' + date.getMinutes();
		nextcallDate 	= $("[name=repeatDay]").val() + ' ' + $("[name=repeatHour]").val() + ':' + $("[name=repeatMinute]").val();
	}
	$("[name=nextcallDate]").val(nextcallDate);

	// A:알림, B:응답 (after)
	// A:알림, B:응답, C:들어보기, D:설문지 (before)
	var callType = "";
	if($("[name=exampleCnt]").val() == 0)
	{
		callType = "A";
	}
	else if($("[name=exampleCnt]").val() >= 1)
	{
		callType = "B";
	}
	$("[name=callType]").val(callType);
	
	$.ajax({
		 url : "/IVR/SendCallByWav"
		,data : $("#formSubmit").serialize()
		,success : function(data){
			var sendTime = $("[name=sendTime]").val();
			if(data.code == '200')
			{
				if(sendTime == "A")
				{
					if(callType == "A")
					{
// 						location.href = "/result/ResultList";
					}
					else if(callType == "B")
					{
// 						location.href="/survey/SurveyResultList";
					}
				}
				else if(sendTime == "B")
				{
// 					location.href = "/IVR/IVRReservation";
				}
				else if(sendTime == "C")
				{
// 					location.href = "/result/RepeatList";
				}
			}
			else
			{
				common.alert('전송 실패', 'wav 파일 전송이 실패하였습니다.');
			}
		}
	});
}

function fn_UploadComplete()
{
	$.ajax({
		 url : "/msg/getNextTargetList"
		,data : {
			 seqgroup : $("[name=seqgroup]").val()
			,page : 1
// 			,page : $("#senderPageNum").val()
		}
		,success : function(datas){
			$(".loadingImg").hide();
			
			useTemplate(datas.result.list);
			setParams(datas.result);
		}
	});
}

function fn_appendPoint()
{
	$(".loadingImg").show();
// 	$("#rList").append("파일 업로드 중입니다. 잠시 기다려주세요.");
}

function setSeqgroup(seqgroup)
{
	$("[name=seqgroup]").val(seqgroup);
}

// websocket 초기화
function initWebSocket()
{
	ws = new WebSocket('wss://' + window.location.hostname + ':48008/ws');
	ws.onmessage = function(data) {
		fn_UploadComplete();
		ws.close();
	}
}

function getNotphonenumber()
{
	var phonenumberArr 	= new Array();
	var phonenumbers 	= $("#rList").find("[name=phonenumber]");
	for(var i=0; i<phonenumbers.length; i++)
	{
		phonenumberArr.push(phonenumbers.eq(i).find("a").text());
	}
	
	return phonenumberArr.toString();
}

// 모음함, 음성파일 첨부 선택 시 선택된 음성메세지 clear
function clearWavFile()
{
	$("[name=seqarsalarmtts]").val("");
	$("[name=recFileName]").val("");
	$("[name=recFilePrefix]").val("");
	$("[name=recFileUrl]").val("");
	$("[name=detailPath]").val("");
	$("[name=seqfile]").val("");
	$("#recFileName").html("");
}

//전화번호에 하이픈 추가
function formatPhonenumber(phonenumber)
{
    return phonenumber.toString().replace(/[^0-9]/g, "").replace(/(^02|^0505|^1[0-9]{3}|^0[0-9]{2})([0-9]+)?([0-9]{4})$/,"$1-$2-$3").replace("--", "-");
}

$(document).ready(function(){
	var ws = null;
// 	var ws = new WebSocket('wss://' + window.location.hostname + ':48008/ws');
// 	var ws = new WebSocket('ws://' + window.location.hostname + ':48008/ws');

	// 발신번호에 하이픈 추가
	var callerId = "${userInfo.callerId}";
	$("[name=callerID]").val(formatPhonenumber(callerId));
	
	
	$(".loadingImg").hide();
	$(".buttonB").hide();
	$(".buttonC").hide();
	$(".ttsMsgBox2").hide();
	$("#rDate").hide();
	$("#repeat").hide();

	var TTSMentTemplate1 = $("[name=ttsMent1]").val();
	var TTSMentTemplate2 = $("[name=ttsMent2]").val();
	var TTSMentTemplate3 = $("[name=ttsMent3]").val();

	$('#phonenumber').mask('000-0000-0000');

	// 달력
	$("[name=sendDay]").datepicker({
		 dayNamesMin : ['일', '월', '화', '수', '목', '금', '토']
		,monthNames : ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월']
		,dateFormat : "yy-mm-dd"
		,showOn: "both"
		,buttonImage : "/assets/images/ico_date.png"
	});
	$("[name=repeatDay]").datepicker({
		 dayNamesMin : ['일', '월', '화', '수', '목', '금', '토']
		,monthNames : ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월']
		,dateFormat : "yy-mm-dd"
		,showOn: "both"
		,buttonImage : "/assets/images/ico_date.png"
	});

	// 서비스 소개 버튼
	$("#btnService").click(function(e){
		e.preventDefault();
		var url='/msg/ServiceIntro';
		var popupX = window.screenLeft+(((document.body.clientWidth)*0.5)-273);
	 	var popupY = window.screenTop+(((window.outerHeight)*0.5)-190);
		var option = 'width=546, height=382, left='+popupX+', top='+popupY+', resizable=no, scrollbars=no, status=no;';
		window.open(url, 'ServiceIntro', option);
	});

	// 엔터키로 등록
	$("#phonenumber").keydown(function(key){
		if(key.keyCode == 13)
		{
			$("#btnAdd").trigger("click");
		}
	});

	// 추가 버튼
	$("#btnAdd").click(function(){
		var name 	= $("#name").val();
		var phone 	= $("#phonenumber").val();

		if(name == null || name == "")
		{
			alert("이름은 필수 입력사항입니다.");
			$("#name").focus();
			return false;
		}

		if(phone == null || phone == "")
		{
			alert("전화번호는 필수 입력사항입니다.");
			$("#phonenumber").focus();
			return false;
		}
		else if(phone.length < 11)
		{
			alert("전화번호를 다시 확인해주세요.");
			$("#phonenumber").focus();
			return false;
		}

		for(var i=0; i<$(".receiverList").length; i++)
		{
			if(phone == $(".receiverList").eq(i).find(".phonenumber").find("a").text())
			{
				alert('이미 추가된 전화번호입니다.');
				return false;
			}
		}

		var myForm = document.formAddrCheck;
		var popupX = window.screenLeft+(((document.body.clientWidth)*0.5)-273);
	 	var popupY = window.screenTop+(((window.outerHeight)*0.5)-120);
		var option = 'width=546, height=247, left='+popupX+', top='+popupY+', resizable=no, scrollbars=no, status=no;';
		window.open("", 'AddressCheck', option);

		myForm.action 				= "/msg/AddressCheck";
		myForm.method 				= "post";
		myForm.target 				= "AddressCheck";
		myForm.name.value 			= name;
		myForm.phonenumber.value 	= phone;
		myForm.seqgroup.value 		= $("#seqgroup").val();
		myForm.submit();

	});

	// 삭제 버튼
	$(".btnDelete").live("click", function(){
		var targetBox = $(this).parents(".template");
		
		$.ajax({
			 url : "/msg/DeleteTempTarget"
			,data : {
				 seqtarget : $(this).attr("id")
				,seqgroup : $("#seqgroup").val()
			}
			,success : function(data){
				console.log(data.result)
				targetBox.remove();
				listSize = data.result;
				var listLength = $("#rList").find("ul").length;
				$(".receiverBtnBox").find(".viewCnt").html("총 " + listLength);
				$(".receiverBtnBox").find(".totalCnt").html("/" + listSize + "명");
			}
		});
		
		

// 		var listLength = $("#rList").find("ul").length;
// 		$(".receiverBtnBox").find("#tip").html("총 "+listLength+"명");
	});

	// 주소록 버튼
	$("#btnAddress").click(function(){
// 		var url='/msg/AddressList?isGroup=N';
// 		var popupX = window.screenLeft+(((document.body.clientWidth)*0.5)-333);
// 	 	var popupY = window.screenTop+(((window.outerHeight)*0.5)-310);
// 		var option = 'width=666, height=667, left='+popupX+', top='+popupY+', resizable=no, scrollbars=no, status=no;';
// 		window.open(url, 'AddressList', option);

		var phonenumberArr 	= new Array();
		var phonenumbers 	= $("#rList").find("[name=phonenumber]");
		for(var i=0; i<phonenumbers.length; i++)
		{
			phonenumberArr.push(phonenumbers.eq(i).find("a").text());
		}
		
		
		var myForm 	= document.formAddress;
		var popupX = window.screenLeft+(((document.body.clientWidth)*0.5)-333);
	 	var popupY = window.screenTop+(((window.outerHeight)*0.5)-310);
		var option 	= 'width=666, height=667, left='+popupX+', top='+popupY+', resizable=no, scrollbars=no, status=no;';
		window.open("", 'formAddress', option);

		myForm.action 				= "/msg/AddressList";
		myForm.method 				= "POST";
		myForm.target 				= "formAddress";
		myForm.notphonenumber.value = phonenumberArr.toString();
		myForm.submit();
	});

	// 엑셀업로드 버튼
	$("#btnExcel").click(function(){
		initWebSocket();
		
		var url='/msg/ExcelUpload';
		var popupX = window.screenLeft+(((document.body.clientWidth)*0.5)-273);
	 	var popupY = window.screenTop+(((window.outerHeight)*0.5)-190);
		var option = 'width=546, height=387, left='+popupX+', top='+popupY+', resizable=no, scrollbars=no, status=no;';
		window.open(url, 'ExcelUpload', option);
	});
	
	// 파일업로드 버튼
	$("#btnTxt").click(function(){
		var url='/msg/FileUpload';
		var popupX = window.screenLeft+(((document.body.clientWidth)*0.5)-273);
	 	var popupY = window.screenTop+(((window.outerHeight)*0.5)-190);
		var option = 'width=546, height=387, left='+popupX+', top='+popupY+', resizable=no, scrollbars=no, status=no;';
		window.open(url, 'FileUpload', option);
	});

	// 엑셀양식받기 버튼
	$("#btnExcelForm").click(function(){
		var url='/msg/ExcelForm';
		var popupX = window.screenLeft+(((document.body.clientWidth)*0.5)-373);
	 	var popupY = window.screenTop+(((window.outerHeight)*0.5)-210);
		var option = 'width=746, height=447, left='+popupX+', top='+popupY+', resizable=no, scrollbars=no, status=no;';
		window.open(url, 'ExcelForm', option);
	});

	// 메세지 내용 라디오버튼
	$("[name=sendType]").change(function(){
		if($(this).val() == "A")
		{
			$(".buttonA").show();
			$(".buttonB").hide();
			$(".buttonC").hide();
// 			$(".ttsMsgBox").removeClass("displayNone");
			$(".ttsMsgBox").show();
			$(".ttsMsgBox2").hide();
			$("[name=ttsMent1]").val(TTSMentTemplate1);
			$("[name=ttsMent2]").val(TTSMentTemplate2);
			$("[name=ttsMent3]").val(TTSMentTemplate3);
			$("input[name=fileSeq]").val("");
		}
		else if($(this).val() == "B")
		{
			$(".buttonA").hide();
			$(".buttonB").show();
			$(".buttonC").hide();
// 			$(".ttsMsgBox").addClass("displayNone");
			$(".ttsMsgBox").hide();
			$(".ttsMsgBox2").show();
			$("textarea").val("");
			$("input[name=fileSeq]").val("");
			clearWavFile();
		}
		else if($(this).val() == "C")
		{
			$(".buttonA").hide();
			$(".buttonB").hide();
			$(".buttonC").show();
// 			$(".ttsMsgBox").addClass("displayNone");
			$(".ttsMsgBox").hide();
			$(".ttsMsgBox2").show();
			$("textarea").val("");
			$("input[name=fileSeq]").val("");
			clearWavFile();
		}

	});

	// 예문수 selectbox
	$("[name=exampleCnt]").change(function(){
		var surveyInner = $(".surveyInner");
		for(var i=0; i<surveyInner.length; i++)
		{
			surveyInner.eq(i).remove();
		}

		var exampleCnt = $(this).val();
		if(exampleCnt == 0)
		{
			var source = $("#survey1").html();
			$(".surveyOuter").append(source);
		}
		else
		{
			for(var i=0; i<exampleCnt; i++)
			{
				var source = $("#survey2").html();
				$(".surveyOuter").append(source);
			}
		}

	});

	// TTS 작성예시 버튼
	$("#btnTTS").click(function(e){
		e.preventDefault();
		var url='/msg/TTSIntro';
		var popupX = window.screenLeft+(((document.body.clientWidth)*0.5)-443);
	 	var popupY = window.screenTop+(((window.outerHeight)*0.5)-200);
		var option = 'width=886, height=397, left='+popupX+', top='+popupY+', resizable=no, scrollbars=no, status=no;';
		window.open(url, 'TTSIntro', option);
	});

	// 이어폰 듣기 버튼
	$("#btnListen").click(function(){
		var ttsMent = makeTTSMent();

		$.ajax({
			 url : "/IVR/Listen"
			,data : {
				msg : ttsMent
			}
			,success : function(data){
// 				console.log(data.result.seqmaketts); // wav파일정보 seq값

				var myForm = document.formTTSListen;
				var popupX 	= window.screenLeft+(((window.outerWidth)*0.5)-211);
			 	var popupY 	= window.screenTop+(((window.outerHeight)*0.5)-50);
				var option 	= 'width=422, height=96, left='+popupX+', top='+popupY+', resizable=no, scrollbars=no, status=no;';
				window.open("", 'TTSListen', option);

				myForm.action 				= "/IVR/recordPlay";
				myForm.method 				= "POST";
				myForm.target 				= "TTSListen";
				myForm.fileUrl.value 		= data.result.fileUrl;
				myForm.submit();

			}
		});

	});

	// ARS로 듣기 버튼
	$("#btnListenARS").click(function(){
		var url='/msg/TTSListen';
		var popupX = window.screenLeft+(((document.body.clientWidth)*0.5)-295);
	 	var popupY = window.screenTop+(((window.outerHeight)*0.5)-164);
		var option = 'width=591, height=332, left='+popupX+', top='+popupY+', resizable=no, scrollbars=no, status=no;';
		window.open(url, 'TTSListen', option);
	});

	// 다시작성 버튼
	$("#btnClear").click(function(){
		$("[name=ttsMent1]").val(TTSMentTemplate1);
		$("[name=ttsMent2]").val(TTSMentTemplate2);
		$("[name=ttsMent3]").val(TTSMentTemplate3);
	});

	// 모음함에서 가져온 파일 듣기 버튼
	$("#btnRecFileListenB").click(function(){
		var myForm = document.formTTSListen;
		var popupX 	= window.screenLeft+(((window.outerWidth)*0.5)-211);
	 	var popupY 	= window.screenTop+(((window.outerHeight)*0.5)-50);
		var option 	= 'width=422, height=96, left='+popupX+', top='+popupY+', resizable=no, scrollbars=no, status=no;';
		window.open("", 'TTSListen', option);

		myForm.action 				= "/IVR/recordPlay";
		myForm.method 				= "POST";
		myForm.target 				= "TTSListen";
		myForm.fileUrl.value 		= $("#recFileTitle").parent("li").find("[name=recFileUrl]").val();
		myForm.submit();
	});

	// 음성파일 첨부 듣기 버튼
	$("#btnRecFileListenC").click(function(){
		var recFileTitle = $("#recFileTitle").parent("li").find("[name=detailPath]").val();
		recFileTitle = recFileTitle.replace("\\", "/");
		
		var myForm = document.formTTSListen;
		var popupX 	= window.screenLeft+(((window.outerWidth)*0.5)-211);
	 	var popupY 	= window.screenTop+(((window.outerHeight)*0.5)-50);
		var option 	= 'width=422, height=96, left='+popupX+', top='+popupY+', resizable=no, scrollbars=no, status=no;';
		window.open("", 'TTSListen', option);

		myForm.action 				= "/IVR/recordPlay";
		myForm.method 				= "POST";
		myForm.target 				= "TTSListen";
		myForm.fileUrl.value 		= recFileTitle;
// 		myForm.fileUrl.value 		= $("#recFileTitle").parent("li").find("[name=detailPath]").val();
// 		myForm.fileUrl.value 		= $("#recFileTitle").parent("li").find("[name=recFilePrefix]").val();
		myForm.submit();
	});

	// 시간예약
	$("[name=sendTime]").click(function(){
		if($(this).next("span").html() == "예약발송")
		{
			$("#rDate").show();
			
			$("#repeat").hide();
			$("#repeat").find("[name=repeatType]").val("");
			$("#repeat").find("[name=repeatDay]").val("");
			$("#repeat").find("[name=repeatHour]").val("");
			$("#repeat").find("[name=repeatMinute]").val("");
		}
		else if($(this).next("span").html() == "주기발송")
		{
			$("#rDate").hide();
			$("#rDate").find("[name=sendDay]").val("");
			$("#rDate").find("[name=sendHour]").val("");
			$("#rDate").find("[name=sendMinute]").val("");
			
			$("#repeat").show();
		}
		else
		{
			$("#rDate").hide();
			$("#rDate").find("[name=sendDay]").val("");
			$("#rDate").find("[name=sendHour]").val("");
			$("#rDate").find("[name=sendMinute]").val("");

			$("#repeat").hide();
			$("#repeat").find("[name=repeatType]").val("");
			$("#repeat").find("[name=repeatDay]").val("");
			$("#repeat").find("[name=repeatHour]").val("");
			$("#repeat").find("[name=repeatMinute]").val("");
		}
	});

	// 보내기 요청 버튼
	$("#btnSubmit").click(function(){
		var title = $("[name=ttsTitle]").val();

		if(title.length < 1)
		{
			alert("제목을 작성해주세요.");
			$("[name=ttsTitle]").focus();
			return false;
		}
		else if($(".receiverList").length < 1)
		{
			alert("받는사람을 추가해주세요.");
			$("#name").focus();
			return false;
		}

		var targetArr = new Array();

		for(var i=0; i<$(".receiverList").length; i++)
		{
			var obj = {
				 name 			: $(".receiverList").eq(i).find("[name=name]").find("a").text()
				,phonenumber 	: $(".receiverList").eq(i).find("[name=phonenumber]").find("a").text()
			}

			targetArr.push(obj);
		}

		if($("[name=sendTime]:checked").val() == "B")
		{
			var date 			= new Date();
			var currentDate 	= date.getFullYear() + '-' + (date.getMonth()+1) + '-' + date.getDate() + ' ' + date.getHours() + ':' + date.getMinutes();
			var reservationDate = $("[name=sendDay]").val() + ' ' + $("[name=sendHour]").val() + ':' + $("[name=sendMinute]").val();

			if(isNaN(Date.parse(currentDate)) || isNaN(Date.parse(reservationDate)) || (Date.parse(currentDate) > Date.parse(reservationDate)))
			{
				common.alert('보내기 요청', '보내는 시간을 다시 확인해주세요.');
				return false;
			}
		}
		else if($("[name=sendTime]:checked").val() == "C")
		{
			if($("[name=repeatType]").val() == "")
			{
				common.alert('보내기 요청', '반복 주기를 다시 확인해주세요.');
				return false;
			}
			var date 		= new Date();
			var currentDate = date.getFullYear() + '-' + (date.getMonth()+1) + '-' + date.getDate() + ' ' + date.getHours() + ':' + date.getMinutes();
			var repeatDate 	= $("[name=repeatDay]").val() + ' ' + $("[name=repeatHour]").val() + ':' + $("[name=repeatMinute]").val();

			if(isNaN(Date.parse(currentDate)) || isNaN(Date.parse(repeatDate)) || (Date.parse(currentDate) > Date.parse(repeatDate)))
			{
				common.alert('보내기 요청', '보내는 시간을 다시 확인해주세요.');
				return false;
			}
		}

		// A:알림, B:응답 (after)
		// A:알림, B:응답, C:들어보기, D:설문지 (before)
		var callType = "";
		if($("[name=exampleCnt]").val() == 0)
		{
			callType = "A";
		}
		else if($("[name=exampleCnt]").val() >= 1)
		{
			callType = "B";
		}
// 		else if($("[name=exampleCnt]").val() == 1)
// 		{
// 			callType = "B";
// 		}
// 		else if($("[name=exampleCnt]").val() > 1)
// 		{
// 			callType = "D";
// 		}

		var myForm = document.formSubmit;
		var popupX = window.screenLeft+(((document.body.clientWidth)*0.5)-273);
	 	var popupY = window.screenTop+(((window.outerHeight)*0.5)-275);
		var option = 'width=546, height=557, left='+popupX+', top='+popupY+', resizable=no, scrollbars=no, status=no;';
		window.open("", 'MessageCheck', option);

		myForm.action 			= "/msg/MessageCheck";
		myForm.method 			= "post";
		myForm.target 			= "MessageCheck";
		myForm.targetArr.value 	= JSON.stringify(targetArr);
		myForm.callType.value 	= callType;
		myForm.submit();

	});

	// 주소록 팝업창이 닫히면 실행된다.
	$("#checkedListSeq").bind("click", function(){
		var phonenumberArr 	= new Array();
		var phonenumbers 	= $("#rList").find("[name=phonenumber]");
		for(var i=0; i<phonenumbers.length; i++)
		{
			phonenumberArr.push(phonenumbers.eq(i).find("a").text());
		}
		
		var seqgroup = ($("#seqgroup").val() == "") ? 0 : $("#seqgroup").val();
		
		$.ajax({
			 url : "/msg/ListUpData"
			,data : {
				 seq 			: $("#checkedListSeq").val()
				,listtype 		: $("#checkedListType").val()
				,seqgroup 		: seqgroup
				,notphonenumber : phonenumberArr.toString()
			}
			,success : function(data){
// 				useTemplate(data.result);
				$("#formSubmit").find("[name=seqgroup]").val(data.result.seqgroup);
				$("#seqgroup").val(data.result.seqgroup);
				setListSize(data.result);
				fn_UploadComplete();
			}
		});
	});
	
	// 받는사람 스크롤바
	$(".receiverBox").scroll(function(){
		if($(this).scrollTop() + $(this).innerHeight() >= $(this)[0].scrollHeight)
		{
			if(noNextData != "Y")
			{
				var seqgroup 	= $("#seqgroup").val();
				var page 		= $("#senderPageNum").val();
				var lastPageNum = $("#lastPageNum").val();
				
				if(Number(page) <= Number(lastPageNum))
				{
					$.ajax({
						 url : "/msg/getNextTargetList"
						,data : {
							 seqgroup : seqgroup
							,page : page
						}
						,success : function(datas){
							useTemplate(datas.result.list);
							setParams(datas.result);
						}
					});
				}
			}
	    }
	});
	

});
</script>