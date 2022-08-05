<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="BaseTag" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<style>
	.tbl_type01 th, .tbl_type01 td {padding:5px;}
	.info_com ul li a { margin-right:10px;margin-top:8px;font-size:13px;font-weight:400; }
	.info_com ul li input[type=text] { margin-right:20px;width:200px; }
	.sendTitle { width:500px !important; }
	#btnNumber { height:38px; float:none; margin-left:10px; }
	#btnAdd { background:#2a347f !important; }
	#btnExcelForm { width:100px; }
	#tip { margin-left:10px;font-size:14px !important;font-weight:normal !important; }
	.receiverWrap, .receiverList { display:flex; }
	.TTSMentTop > div { display:inline-block; }
	.ttsTypeWrap { display:flex;width:330px; }
	.ttsButtonWrap { width:470px;text-align:right; }
	.rName { width:200px;text-align:center; }
	.phonenumber { width:300px;text-align:center; }
	.text_title { width:600px; }
	.text_content { width:1700px; }
	.receiverBox { width:680px;height:200px;border:1px solid #ccc;margin-top:5px;overflow-y:scroll; }
	.receiverBtnBox { margin-left:15px; }
	.receiverBtnBox button { margin-bottom:3px; }
	.inputRadio { width:13px !important;height:15px !important;margin-top:6px;margin-left:12px; }
	.ttsWriteBox { margin-top:10px;display:flex; }
	.sendDay { width:90px !important;height:20px !important;margin:3px 0 3px 10px !important;font-size:13px; }
	select { width:45px !important;height:20px !important;margin:3px 0 3px 10px !important; }
	#ui-datepicker-div { font-size:13px;width:200px; }
	.btnDelete { width:70px !important;height:25px !important;margin-top:3px;margin-left:70px !important;background:#757788 !important; }
	table ul li span { color:black !important; }
	#btnTTS {text-align:center !important;  width:100px !important; }
	#btnAdd { font-weight:bold; }
	#btnSubmit, #btnCancel { height:40px !important; }
	.ttsOption > li { height:26px;line-height:26px; }
	.exampleSelect { margin-top:10px; }
	.exampleSelect > li { display:inline-block;height:26px;line-height:26px; }
	.tip { font-size:12px !important;color:red !important; margin: 7px 0 0 10px;}
	.examplCntText { font-size:12px !important;color:red !important; margin-left:10px;}
	.ttsMsgBox2 { display:inline-block; }
	.ttsMsgBox2 ul { border:1px solid #ccc; }
	#recFileTitle { height:36px;line-height:36px;margin-left:10px; }
	#recFileName { width:500px;height:36px;line-height:36px;margin-left:15px;font-size:14px;font-weight:500; }
	.buttonB, .buttonC { display:inline-block; }
	#btnGetSavedFileB, #btnGetSavedFileC { background:#2a347f !important; }
	#repeat select[name=repeatType] { width:90px !important; }
</style>

<BaseTag:layout>
<div id="jquery_jplayer_1" class="jp-jplayer"></div>
<div id="contents">
	<div class="section">

		<!--서브타이틀-->
		<div class="pageTop">
			<h2 class="pageTitle">문자메세지 발송</h2>
		</div>
        <!--//서브타이틀-->

		<!-- contents -->
		<div class="ars_content">
			<ul style="float:right;">
				<li class="btn_wrap"><a href="#"><button class="btn_service" id="btnService">서비스 소개</button></a></li>
			</ul>
		</div>
		<!-- //contents -->

		<form name="formSubmit" id="formSubmit">
			<input type="hidden" name="seqgroup" />
			<input type="hidden" name="targetArr" />
			<input type="hidden" name="callType" />
			<input type="hidden" name="nextcallDate" />
			<!-- table -->
			<div class="tbl_type01 company_list" style="margin-top:10px;">
				<table>
					<colgroup>
						<col width="15%" />
						<col width="" />
					</colgroup>
					<tbody>
						<tr>
							<th scope="row">제목</th>
							<td>
								<input type="text" class="sendTitle" name="ttsTitle" maxlength="40" autocomplete="off" value="[Web발송]OO홍보안내발송" />
								<span class="tip">* 원하는 문자발송 제목으로 수정하십시오. (40자 이내)</span>
							</td>
						</tr>
						<tr>
							<th scope="row">발신번호</th>
							<td>
								<input type="text" class="" name="callerID" autocomplete="off" readonly/>
<!-- 								<button class="btn_service" id="btnNumber">자주쓰는 번호 설정</button> -->
							</td>
						</tr>
						<tr>
							<th scope="row">받는사람</th>
							<td>
								<div class="info_com">
									<ul>
										<li><a>이름</a></li>
										<li><input type="text" class="" id="name" maxlength="40" autocomplete="off"/></li>
										<li><a>전화번호</a></li>
										<li><input type="text" class="" id="phonenumber" autocomplete="off"/></li>
										<li><button type="button" class="btn_adress" id="btnAdd">추가</button></li>
									</ul>
								</div>
								<div class="receiverWrap">
									<div class="receiverBox">
										<ul id="rList">
											<li class="loadingImg">
												<label style="margin-left:10px;">업로드 중입니다.</label>
												<img src="/assets/images/ajax-loader.gif" style="margin:3px 0 0 3px;width:20px;" />
											</li>
										</ul>
									</div>
									<div class="receiverBtnBox">
										<ul>
											<li style="padding-top:10px;"><button type="button" class="btn_adress" id="btnAddress">주소록</button></li>
											<li><button type="button" class="btn_adress" id="btnExcel">엑셀업로드</button></li>
<!-- 											<li><button type="button" class="btn_adress" id="btnTxt">파일업로드</button></li> -->
											<li><button type="button" class="btn_service" id="btnExcelForm">엑셀양식받기</button></li>
											<li>
												<span id="tip" class="viewCnt" style="color:#cd2129 !important;margin-top:15px;">총 0</span>
												<span id="tip" class="totalCnt" style="color:#cd2129 !important;margin-top:15px;margin-left:-5px;">/ 0명</span>
											</li>
										</ul>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<th scope="row">내용</th>
							<td>
								<div class="info_com">
									<div class="TTSMentTop">
										<div class="ttsTypeWrap">
											<div class="ttsTypeBox">
												<div>
													<ul class="ttsOption">
														<li>
															<label><input type="radio" class="inputRadio" name="sendType" value="A" checked /><span>직접입력</span></label>
														</li>
														<li>
															<label><input type="radio" class="inputRadio" name="sendType" value="B" /><span>모음함</span></label>
														</li>
														<li>
															<label><input type="radio" class="inputRadio" name="sendType" value="C" /><span>파일 첨부</span></label>
															<input type="hidden" name="fileSeq" />
														</li>
													</ul>
												</div>
											</div>
										</div>
										<div class="ttsButtonWrap buttonA">
											<ul>
<!-- 												<li> -->
<!-- 													<button type="button" class="btn_adress" id="btnListen">들어보기</button> -->
<!-- 													<input type="hidden" name="ttsSeq" /> -->
<!-- 													<input type="hidden" name="ttsPath" /> -->
<!-- 												</li> -->
												<li>
													<button type="button" class="btn_adress" id="btnClear">다시작성</button>
												</li>
												<li class="btn_wrap">
													<button type="button" class="btn_service" id="btnTTS">작성예시</button>
												</li>
											</ul>
										</div>
									</div>

									<div class="ttsMsgBox">
										<div class="ttsWriteBox">
											<textarea class="text_title" rows="1" cols="155" name="ttsMent1" maxlength="100">OO에서보내는 안내문자입니다. </textarea>
											<span class="tip">* 원하는 인사말 문항으로 수정하십시오.</span>
										</div>
										<div>
											<ul class="exampleSelect">
												<li><span>전달 문항 갯수 </span></li>
												<li>
													<select name="exampleCnt">
														<option value="0">0</option>
														<option value="1">1</option>
														<option value="2">2</option>
														<option value="3">3</option>
														<option value="4">4</option>
														<option value="5">5</option>
														<option value="6">6</option>
														<option value="7">7</option>
														<option value="8">8</option>
														<option value="9">9</option>
													</select>
												</li>
												<li>
													<span class="examplCntText">* 전달문항갯수 '0'선택시 회신 확인없는 안내전달만 이루어집니다.</span>
												</li>
											</ul>
										</div>
										<div class="surveyOuter">
											<div class="surveyInner">
												<div class="ttsWriteBox">
													<textarea class="text_contente" rows="5" cols="155" name="ttsMent2" maxlength="2000">(전달 내용 직접입력)</textarea>
												</div>
											</div>
										</div>
									</div>

									<div class="ttsMsgBox2">
										<ul>
											<li>
												<span id="recFileTitle">메시지 : </span>
												<input type="hidden" name="seqarsalarmtts" />
												<input type="hidden" name="recFileName" />
												<input type="hidden" name="recFilePrefix" />
												<input type="hidden" name="recFileUrl" />
												<input type="hidden" name="detailPath" />
												<input type="hidden" name="seqfile" />
											</li>
											<li><span id="recFileName"></span></li>
										</ul>
									</div>
									<div class="buttonB">
										<ul>
											<li><button type="button" class="btn_adress" id="btnRecFileListenB">듣기</button></li>
											<li><button type="button" class="btn_adress"  onclick="fn_TTSList();" id="btnGetSavedFileB">불러오기</button></li>
										</ul>
									</div>
									<div class="buttonC">
										<ul>
											<li><button type="button" class="btn_adress" id="btnRecFileListenC">듣기</button></li>
											<li><button type="button" class="btn_adress" onclick="fn_TTSUpload();" id="btnGetSavedFileC">불러오기</button></li>
										</ul>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<th scope="row">회신받기</th>
							<td>
								<div class="info_com">
									<ul>
										<li><label><input type="radio" class="inputRadio" name="voiceReceive" value="N" checked /><span>받지않음</span></label></li>
										<li><label><input type="radio" class="inputRadio" name="voiceReceive" value="Y" disabled /><span>회신요망</span></label></li>
									</ul>
								</div>
							</td>
						</tr>
						<tr>
							<th scope="row">보내는 시간</th>
							<td>
								<div class="info_com">
									<ul>
										<li><label><input type="radio" class="inputRadio" name="sendTime" value="A" checked /><span>즉시발송</span></label></li>
										<li><label><input type="radio" class="inputRadio" name="sendTime" value="B" /><span>예약발송</span></label></li>
										<li id="rDate">
											<ul>
												<li><input type="text" class="sendDay" name="sendDay" autocomplete="off"/></li>
												<li>
													<select name="sendHour">
														<option value="">-</option>
														<c:forEach begin="1" end="24" var="h">
															<option value="${h}">${h}</option>
														</c:forEach>
													</select>
													<label>시</label>
												</li>
												<li>
													<select name="sendMinute">
														<option value="">-</option>
														<c:forEach begin="0" end="59" var="m">
															<option value="${m}">${m}</option>
														</c:forEach>
													</select>
													<label>분</label>
												</li>
											</ul>
										</li>
										<li><label><input type="radio" class="inputRadio" name="sendTime" value="C" /><span>주기발송</span></label></li>
										<li id="repeat">
											<ul>
												<li>
													<select name="repeatType">
														<option value="">반복선택</option>
														<option value="A">매일 반복</option>
														<option value="B">주간 반복</option>
														<option value="C">월간 반복</option>
														<option value="D">월~금 반복</option>
													</select>
												</li>
												<li><input type="text" class="sendDay" name="repeatDay" autocomplete="off"/></li>
												<li>
													<select name="repeatHour">
														<option value="">-</option>
														<c:forEach begin="1" end="24" var="h">
															<option value="${h}">${h}</option>
														</c:forEach>
													</select>
													<label>시</label>
												</li>
												<li>
													<select name="repeatMinute">
														<option value="">-</option>
														<c:forEach begin="0" end="59" var="m">
															<option value="${m}">${m}</option>
														</c:forEach>
													</select>
													<label>분</label>
												</li>
											</ul>
										</li>
<!-- 										<li><label style="font-size:12px;margin-left:50px;">*즉시발송, 예약발송만 가능합니다.</label></li> -->
									</ul>
								</div>
							</td>
						</tr>
						<tr>
							<th scope="row">모음함에 저장</th>
							<td>
								<div class="info_com">
									<ul>
										<li><label><input type="radio" class="inputRadio" name="isSave" value="Y" /><span>모음함 저장</span></label></li>
										<li><label><input type="radio" class="inputRadio" name="isSave" value="N" checked /><span>저장하지 않음</span></label></li>
									</ul>
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			<!-- //table -->
		</form>

		<!-- button -->
		<div class="btn_next">
			<button class="btn_out" id="btnSubmit">보내기 요청</button>
			<button class="btn_can" id="btnCancel">취소</button>
		</div>
		<!-- //button -->

	</div>
</div>

<input type="hidden" id="checkedListType" />
<input type="hidden" id="checkedListSeq" />

<!-- 받는사람 리스트 페이징 정보 -->
<input type="hidden" id="listSize" />
<input type="hidden" id="seqgroup" />
<input type="hidden" id="senderPageNum" />
<input type="hidden" id="lastPageNum" />
<input type="hidden" id="noNextData" />
<!-- //받는사람 리스트 페이징 정보 -->

<form name="formAddrCheck" id="formAddrCheck">
	<input type="hidden" name="name" />
	<input type="hidden" name="phonenumber" />
	<input type="hidden" name="seqgroup" />
</form>

<form name="formTTSListen">
	<input type="hidden" name="fileUrl" />
</form>

<!-- 주소록 버튼 클릭 시 사용 -->
<form name="formAddress">
	<input type="hidden" name="isGroup" value="N" />
	<input type="hidden" name="notphonenumber" />
</form>

<%@ include file="SendMessageScript.jsp" %>
</BaseTag:layout>