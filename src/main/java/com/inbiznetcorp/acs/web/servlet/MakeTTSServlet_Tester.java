package com.inbiznetcorp.acs.web.servlet;

import java.util.HashMap;

import org.json.simple.JSONObject;

public class MakeTTSServlet_Tester
{
	@SuppressWarnings("unchecked")
	public static void main(String[] args)
	{
//		String strHost 	= "https://dev01.ring2pay.com:9008";
////		String strHost 	= "http://local.ring2pay.com:8080";
//		String strURL 	= "/RealTimeTTSMake.do";
//
//		HashMap    paramMap  = new HashMap();
//
//		paramMap.put("tid", 			Utility.getStrRandom(10));
//		paramMap.put("msg",   			"일이삼사.오육칠팔");
////		paramMap.put("seperator",   	".");
////		paramMap.put("seperator",   	"dat");
//		paramMap.put("companyName",   	"Tirebank");
//
//		JSONObject resultJSONObject = new RequestProc().sendPacket(strHost+strURL, paramMap);
//
//		System.out.println( resultJSONObject.toString() );
		
		
		String 	strURL 		= "https://openapi.openbanking.or.kr/v2.0/inquiry/real_name";
		HashMap paramMap 	= new HashMap();
		
		paramMap.put("bank_code_std", "088");
		paramMap.put("account_num", "110498222272");
		paramMap.put("account_holder_info", "8810201");
		paramMap.put("tran_dtime", "8810201");
		
		JSONObject resultJSONObject = new RequestProc().sendPacket(strURL, paramMap);

	}

	public static String tStrScript()
	{
		StringBuffer strBuffer = new StringBuffer();

		strBuffer.append("나는 대학 졸업 후 2년 동안 다달이 방세를 내기 위해 이런저런 아르바이트를 해나가는 동시에 내 창의적 에너지를 소설 쓰기에 모두 쏟아 부었다. 그렇게 해서 완성한 소설이 바로 『산마루의 수줍음』이었고, 10여 개 출판사에 보낸 결과 하나같이 거절당했다. 나는 출판 불가를 알려주는 편지들을 하나도 빠짐없이 내 책상 위 벽면에 부착해둔 코르크판에 압핀으로 꽂아두었다. 거절편지를 코르크판에 꽂을 때마다 마치 내 심장에 뾰족한 압핀을 찔러 넣는 느낌을 받았다. 나는 글쓰기에 대한 열정이 남달리 강했기 때문에 출판사로부터 인정받지 못한 것에 대한 상처도 깊었다.");
		strBuffer.append("다행스럽게 절망감은 그다지 오래 가지 않았다. 적어도 지금껏 나는 실패가 결국 성공으로 이끄는 대기실이라고 굳게 믿어왔다. 스티븐 킹은 서른 번의 고배를 마신 끝에 『캐리』를 출판할 수 있었다. 런던에 자리 잡은 출판사들 가운데 절반이 조앤 K. 롤링의 『해리 포터시리즈』 첫 권이 ‘어린 아이들에게는 너무 길다.’고 혹평했다. 현재 전 세계에서 가장 많이 팔린다는 공상과학소설로 등극하기 전까지 프랭크 허버트의 『듄』은 출판사들로부터 적어도 스무 번 이상 퇴짜를 맞았다. 프랜시스 스콧 피츠제럴드로 말하자면 단편소설을 출판사에 투고할 때마다 받은 122통의 거절 편지를 모아 서재의 벽면 전체를 도배했다.");
		strBuffer.append("그레구아르는 서랍에서 가죽 장정으로 된 방명록을 꺼내더니 읽어보라는 무언의 명령처럼 나에게 내밀었다. 아닌 게 아니라 방명록에 붙어있는 사진들 중 미셸 투르니에, J.M.G. 르 클레지오, 프랑수아즈 사강, 장 도르메송, 존 어빙, 존 르카레 그리고 내가 가장 만나고 싶어 하는 네이선 파울스의 얼굴이 있었다.");
		strBuffer.append("“이토록 유서 깊은 서점인데 문을 닫아야 한다는 게 정말 아쉬워요.”");
		strBuffer.append("“난 미련이 없어.” 그레구아르가 전혀 망설이지 않고 말했다. “사람들이 책을 읽지 않는데 어떻게 서점을 운영하겠나?”");
		strBuffer.append("나는 그의 말을 애써 수정해주었다.");
		strBuffer.append("“책을 구입해 읽는 사람들이 예전보다 많이 줄긴 했죠. 종이 책이 아니라서 그렇지 아직 뭔가 읽기를 좋아하는 사람들은 많다고 봅니다. 종이책 대신 킨들이나 오디오북, 페이스북 같은 다양한 방식을 통해 글을 읽고 있으니까요.”");
		strBuffer.append("그레구아르는 이탈리아 산 커피메이커에서 휘파람 소리가 나자 가스레인지를 껐다.");
		strBuffer.append("“자네는 내가 무얼 말하는지 잘 알고 있지 않나? 나는 오락적인 출판물이 아니라 ‘진정한 문학’에 대해 말하는 걸세.”");
		strBuffer.append("그레구아르 같은 사람들의 입에서 언제나 ‘진정한 문학’ 또는 ‘진정한 작가’라는 말이 튀어나오기 마련이었다. 나는 누군가에게 이 책은 가치가 있으니 반드시 읽어야 하고, 어떤 책은 내용이 형편없는 쓰레기이니 읽지 말라고 한 적이 없었다. 나는 그렇게 말해도 되는 권리를 부여받은 적이 없으니까.");

		return strBuffer.toString();
	}

}
