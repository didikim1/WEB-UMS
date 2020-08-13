package com.inbiznetcorp.acs.framework.websocket;

import com.inbiznetcorp.acs.framework.mymap.MyMap;
import com.inbiznetcorp.acs.framework.utils.FrameworkUtils;

public class HttpClientFileTest {

	public static void main(String[] args)
	{
		String tid 			= FrameworkUtils.generateSessionID();
		String companyName 	= "inbiznet";
		String msg 			= "inbiznet";
		String phonenumber 	= "01071557901";
		String callerID 	= "1644-7900";
		
		IVRSender sender = new IVRSender();
		
		MyMap resultTTSMake = sender.RealTimeTTSMake(tid, companyName, msg);
		MyMap resultTTSPut = sender.TTSFileIVRServerPut(resultTTSMake);
		sender.wavPlay0001(resultTTSPut, phonenumber, "B", callerID, tid);
		
		
		
//		HttpClient 	client = new DefaultHttpClient();
//		HttpPost 	method = new HttpPost("http://127.0.0.1:8080/receiver");
//		
//		try {
//			FileBody file = new FileBody(new File("C:\\Users\\for\\Downloads\\TEST_FILE\\가나다라.wav"));
//			
//			MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE, null, Charset.forName("UTF-8"));
//			
//			reqEntity.addPart("upload_file", file);
//			
//			method.setEntity(reqEntity);
//
//			HttpResponse response = client.execute(method);
//			
//			if (response.getStatusLine().getStatusCode() == 200)
//			{
//				System.out.println("####################");
//			}
//			else
//			{
//				System.out.println("@@@@@@@@@@@@@@@@@@@@");
//			}
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//		}
	}

}
