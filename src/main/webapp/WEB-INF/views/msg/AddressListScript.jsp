<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
var listtype 	= "P";
var page 		= 1;	// 페이지 번호

// 체크된 항목의 seq값
function checkedList(listID)
{
	var checkedSeqArr = new Array();

	var checkboxList = $("#"+listID+" input[type=checkbox]");
	for(var i=0; i<checkboxList.length; i++)
	{
		if(checkboxList.eq(i).prop("checked"))
		{
			checkedSeqArr.push(checkboxList.eq(i).val());
		}
	}

	return checkedSeqArr;
}

//스크롤바가 바닥에 닿았을 때 list에 추가할 data
// function getList(page){
// 	var isGroup 		= $("input[name=isGroup]").val();
// 	var seqgroupinfo 	= $("input[name=seqgroupinfo]").val();

// 	$.ajax({
// 		 type : 'POST'
// 		,datatype : 'json'
// 		,data : {
// 			 page : page
// 			,isGroup : isGroup
// 			,seqgroupinfo : seqgroupinfo
// 		}
// 		,url : "/msg/getAddressList"
// 		,success : function(data){
// 			var list 	= data.result;

// 			if(list.length > 0)
// 			{
// 				for(var i=0; i<list.length; i++)
// 				{
// 					var addList = innerHTML(list[i]);
// 					$("#pList").append(addList);
// 				}
// 			}

// 		}
// 	});
// }

//list 추가 양식
// function innerHTML(data){
// 	var innerHTML = '';
// 		innerHTML += '<tr>';
// 		innerHTML += '	<td><input type="checkbox" value="'+data.seq+'" /></td>';
// 		innerHTML += '	<td>'+data.name+'</td>';
// 		if(data.phonenumber.length == "10")
// 		{
// 			var number1 = data.phonenumber.substring(0,2);
// 			var number2 = data.phonenumber.substring(2,6);
// 			var number3 = data.phonenumber.substring(6);
// 			innerHTML += '<td>'+number1+'-'+number2+'-'+number3+'</td>';
// 		}
// 		else
// 		{
// 			var number1 = data.phonenumber.substring(0,3);
// 			var number2 = data.phonenumber.substring(3,7);
// 			var number3 = data.phonenumber.substring(7);
// 			innerHTML += '<td>'+number1+'-'+number2+'-'+number3+'</td>';
// 		}
// 		innerHTML += '</tr>';
// 	return innerHTML;
// }

$(document).ready(function(){
	if("${isGroup}" == "Y")
	{
		$("[name=searchType]").eq(0).parent("label").css("display", "none");
		$("[name=searchType]").eq(1).parent("label").css("display", "none");
	}

	for(var i=0; i<$("[name=searchType]").length; i++)
	{
		if($("[name=searchType]").eq(i).prop("checked"))
		{
			if(i == 0)
			{
				$("#pAddress").addClass("on");
				$("#gAddress").removeClass("on");
				listtype = "P";
			}
			else
			{
				$("#pAddress").removeClass("on");
				$("#gAddress").addClass("on");
				listtype = "G";
			}
		}
	}

	// 전체 선택
	$("#checkAll").click(function(){
		console.log($("#pAddress input[type=checkbox]").length);
		for(var i=0; i<$("#pAddress input[type=checkbox]").length; i++)
		{
			$("#pAddress input[type=checkbox]").eq(i).prop("checked", $(this).prop("checked"));
		}
	});

	// 주소록관리 버튼
	$("#btnAddressList").click(function(){
		opener.location.href="/addr/AddressPList";
		window.close();
	});

	// 개인/그룹 라디오 버튼
	$(".inputRadio").click(function(){
		if($(this).val() == "P")
		{
			$("#pAddress").addClass("on");
			$("#gAddress").removeClass("on");
			listtype = "P";
			$("[name=searchWord]").val("");
			$("[name=searchForm]").submit();
		}
		else
		{
			$("#pAddress").removeClass("on");
			$("#gAddress").addClass("on");
			listtype = "G";
			$("[name=searchWord]").val("");
			$("[name=searchForm]").submit();
		}
	});

	// 선택 버튼
	$("#btnSubmit").click(function(){
		var isGroup 		= "${isGroup}";
		var seqgroupinfo 	= "${seqgroupinfo}";

		var checklist 		= new Array();

		if(listtype == "P")
		{
			// 개인 주소록
			checklist = checkedList("pList");
		}
		else
		{
			// 그룹 주소록
			checklist = checkedList("gList");
		}

		// 음성메세지 발송 페이지에서 열었을 때
		if(isGroup == "N")
		{
			$(opener.document).find("#checkedListType").val(listtype);
			$(opener.document).find("#checkedListSeq").val(checklist);
			$(opener.document).find("#checkedListSeq").trigger("click"); 	// 부모창의 $("#checkedListSeq")요소의 change 이벤트를 발생시킨다.
			window.close();
		}
		// 그룹 주소록 상세 페이지에서 열었을 때
		else if(isGroup == "Y")
		{
			$.ajax({
				 url : "/addr/RegisterDataGroup"
				,data : {
					 seqStr : checklist.toString()
					,seqgroupinfo : seqgroupinfo
				}
				,success : function(){
					window.opener.location.reload();
					window.close();
				}
			});
		}

	});

	// 닫기 버튼
	$("#btnCancel").click(function(){
		window.close();
	});

	// scroll이 바닥에 닿았을 때
// 	$("#wrap_pop_ser").scroll(function(){
// 		var scrollT 	= $(this).scrollTop();	// 스크롤바의 상단 위치
// 		var scrollH 	= $(this).height();		// 스크롤바를 갖는 div 높이
// 		var contentH 	= $("#pList").height(); // 문서 전체 내용을 갖는 div 높이
// 		if(scrollT + scrollH + 1 >= contentH)
// 		{
// 			var timer = setTimeout(function() {
// 				page++;
// 				getList(page);
// 				clearTimeout(timer);
// 			}, 300);
// 		}
// 	});

});
</script>