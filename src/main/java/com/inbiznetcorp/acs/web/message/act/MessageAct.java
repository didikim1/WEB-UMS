package com.inbiznetcorp.acs.web.message.act;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.RandomStringUtils;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.inbiznetcorp.acs.framework.beans.BasicBean;
import com.inbiznetcorp.acs.framework.beans.FrameworkBeans;
import com.inbiznetcorp.acs.framework.common.ServiceCommon;
import com.inbiznetcorp.acs.framework.mymap.MyCamelMap;
import com.inbiznetcorp.acs.framework.mymap.MyMap;
import com.inbiznetcorp.acs.framework.result.ResultCode;
import com.inbiznetcorp.acs.framework.result.ResultMessage;
import com.inbiznetcorp.acs.framework.utils.FrameworkUtils;
import com.inbiznetcorp.acs.web.address.service.AddressService;
import com.inbiznetcorp.acs.web.excel.read.ExcelRead;
import com.inbiznetcorp.acs.web.ivr.service.IVRService;
import com.inbiznetcorp.acs.web.jdbc.DatabaseAuthInfoDuty;
import com.inbiznetcorp.acs.web.message.service.ArsalarmttsService;
import com.inbiznetcorp.acs.web.message.service.FileinfoService;
import com.inbiznetcorp.acs.web.message.service.MessageService;

@Controller
@RequestMapping("/msg")
public class MessageAct
{
	@Autowired private Environment env;

	@Autowired private ServletContext context;

	public static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(MessageAct.class);

	@Resource(name="com.inbiznetcorp.acs.web.message.service.MessageService")
	MessageService messageService;

	@Resource(name="com.inbiznetcorp.acs.web.message.service.AddressService")
	AddressService addressService;

	@Resource(name="com.inbiznetcorp.acs.web.message.service.ArsalarmttsService")
	ArsalarmttsService arsalarmttsService;

	@Resource(name="com.inbiznetcorp.acs.web.message.service.FileinfoService")
	FileinfoService fileinfoService;

	@Resource(name="com.inbiznetcorp.acs.web.ivr.service.IVRService")
	IVRService mIVRService;

	/**
	 * ??????????????? ?????? ?????????
	 */
	@RequestMapping("/SendMessage")
	public String SendMessage(Model model)
	{
		MyMap paramMap = FrameworkBeans.findHttpServletBean().findClientRequestParameter();
		
		paramMap.put("sequser", paramMap.getStr("SESSION_USER_SEQ"));
		
		String 		grade 		= paramMap.getStr("SESSION_GRADE"); 	// A:?????????, B:?????????
		MyCamelMap 	userInfo 	= (grade.equals("A")) ? messageService.FindAdminInfo(paramMap) : messageService.FindUserInfo(paramMap);
		
		model.addAttribute("userInfo", userInfo);

		return "/msg/SendMessage";
	}

	/**
	 * ??????????????? ?????? ?????????
	 */
//	@RequestMapping("/SMSSendMessage")
//	public String SMSSendMessage(Model model)
//	{
//		MyMap paramMap = FrameworkBeans.findHttpServletBean().findClientRequestParameter();
//		
//		paramMap.put("sequser", paramMap.getStr("SESSION_USER_SEQ"));
//		
//		String 		grade 		= paramMap.getStr("SESSION_GRADE"); 	// A:?????????, B:?????????
//		MyCamelMap 	userInfo 	= (grade.equals("A")) ? messageService.FindAdminInfo(paramMap) : messageService.FindUserInfo(paramMap);
//		
//		model.addAttribute("userInfo", userInfo);
//
//		return "/msg/SMSSendMessage";
//	}

	/**
	 * ??????????????? ?????? ?????????
	 */
	@RequestMapping("/ServiceIntro")
	public String ServiceIntro()
	{
		return "/msg/ServiceIntro";
	}

	/**
	 * TTS ???????????? ?????? ?????????
	 */
	@RequestMapping("/TTSIntro")
	public String TTSIntro()
	{
		return "/msg/TTSIntro";
	}

	/**
	 * ??????,?????? ?????? ?????? ?????????
	 */
	@RequestMapping("/AddressCheck")
	public String AddressCheck(Model model)
	{
		MyMap paramMap = FrameworkBeans.findHttpServletBean().findClientRequestParameter();

		model.addAttribute("name", 			paramMap.getStr("name"));
		model.addAttribute("phonenumber", 	paramMap.getStr("phonenumber"));
		model.addAttribute("seqgroup", 		paramMap.getStr("seqgroup"));

		return "/msg/AddressCheck";
	}

	/**
	 * ????????? ?????? ?????????
	 */
	@RequestMapping("/AddressList")
	public String AddressList(Model model)
	{
		MyMap paramMap = FrameworkBeans.findHttpServletBean().findClientRequestParameter();

		BasicBean pResult = addressService.ListPagingData(paramMap);
		BasicBean gResult = addressService.ListPagingDataGroup(paramMap);

		model.addAttribute("pList", 			pResult.getList());
		model.addAttribute("pPaginationInfo", 	pResult.getPaginationInfo());
		model.addAttribute("gList", 			gResult.getList());
		model.addAttribute("gPaginationInfo", 	gResult.getPaginationInfo());
		model.addAttribute("searchWord", 		paramMap.getStr("searchWord", ""));
		model.addAttribute("searchType", 		paramMap.getStr("searchType", "P"));

		model.addAttribute("isGroup", 			paramMap.getStr("isGroup", "N"));
		model.addAttribute("seqgroupinfo", 		paramMap.getInt("seqgroupinfo", 0));

		return "/msg/AddressList";
	}

	/**
	 * ??????????????? ???????????? ????????????
	 */
//	@RequestMapping("/getAddressList")
//	public @ResponseBody ResultMessage getAddressList(Model model)
//	{
//		MyMap paramMap = FrameworkBeans.findHttpServletBean().findClientRequestParameter();
//
//		List<MyCamelMap> resultList = addressService.getAddressList(paramMap);
//
//		return new ResultMessage(ResultCode.RESULT_OK, "ok!!!!!!", resultList);
//	}

	/**
	 * ??????????????? ????????? ?????? ?????????
	 */
	@RequestMapping("/ListUpData")
	public @ResponseBody ResultMessage ListUpData()
	{
		MyMap paramMap = FrameworkBeans.findHttpServletBean().findClientRequestParameter();

		int seqgroup = paramMap.getInt("seqgroup", 0);
		if(seqgroup == 0)
		{
			messageService.InsertTempGroup(paramMap);
			seqgroup = paramMap.getInt("seqgroup");
		}
		
		List<MyCamelMap> resultList = addressService.ListUpData(paramMap);
		for(MyCamelMap result : resultList)
		{
			String number = result.getStr("phonenumber");
			result.put("phonenumber", number.replaceAll("-", ""));

			result.put("seqgroup", seqgroup);
			messageService.InsertTempTarget(result);
		}
    	
		paramMap.put("seqgroup", seqgroup);
		paramMap.put("listSize", resultList.size());
		
		return new ResultMessage(ResultCode.RESULT_OK, "ok!!!!!!", paramMap);
	}

	@RequestMapping("/ExcelForm")
	public String ExcelForm()
	{
		return "/msg/ExcelForm";
	}

	/**
	 * ?????? ????????????
	 */
	@RequestMapping("/ExcelFormDown")
	public void ExcelFormDown(HttpServletResponse response)
	{
		ServiceCommon serviceCommon = (ServiceCommon) FrameworkBeans.findBean("com.inbiznetcorp.acs.framework.common.ServiceCommon");

		String path = "";
		if(serviceCommon.getProfilesActiveName().equals("local"))
		{
			path = context.getRealPath("") + "excel" + File.separator + "???????????????????????????.xlsx";
		}
		else
		{
			path = env.getProperty("common.file.excel") + File.separator + "???????????????????????????.xlsx";
		}

		File file = new File(path);

		String fileName = null;
		try
		{
			fileName = URLEncoder.encode("???????????????????????????.xlsx", "utf-8");
		}
		catch (UnsupportedEncodingException e1)
		{
			e1.printStackTrace();
		}

		response.setContentType("application/download; utf-8");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
        response.setHeader("Content-Transfer-Encoding", "binary");

        try
        {
        	FileInputStream fis = new FileInputStream(file);
			FileCopyUtils.copy(fis, response.getOutputStream());
		}
        catch (IOException e)
        {
        	FrameworkUtils.exceptionToString(e);
		}
	}

	/**
	 * ?????? ????????? ?????? ?????????
	 */
	@RequestMapping("/ExcelUpload")
	public String ExcelUpload()
	{
		return "/msg/ExcelUpload";
	}

	/**
	 * ?????? ?????????
	 */
	@RequestMapping("/UploadFile")
	public @ResponseBody ResultMessage UploadFile(@RequestParam(value="uploadFile", required = false) MultipartFile file)
	{
		MyMap 		paramMap 			= FrameworkBeans.findHttpServletBean().findClientRequestParameter();
		MyCamelMap 	cpMap 				= null;
		List<MyCamelMap> resultList 	= null;
		
		String 	userSeq	= FrameworkBeans.findSessionBean().getUserSeq();
		String 	grade 		= FrameworkBeans.findSessionBean().getGrade();

		String originalFilename = file.getOriginalFilename(); // ????????????
		String tempPath 		= env.getProperty("common.file.path");
		String uploadPath 		= tempPath + File.separator + FrameworkUtils.getCurrentDate("yyyyMMdd") + File.separator; // ????????? ???????????? ??????

		makeDir(uploadPath); // ?????? ??????

		uploadPath = uploadPath + FrameworkUtils.getDateToStr() + RandomStringUtils.randomAlphabetic(5) + "_" + originalFilename; // ?????? ?????? ??????

		try
		{
			file.transferTo(new File(uploadPath)); // ?????? ??????

			ExcelRead excelRead = new ExcelRead();
			resultList = excelRead.Proc(uploadPath);
			System.out.println(resultList);
			String strErrorMsg = resultList.get(0).getStr("RESULT", "");
			if(!strErrorMsg.equals(""))
			{
				return new ResultMessage(ResultCode.RESULT_BAD_REQUEST, "no!!!!!!", strErrorMsg);
			}
		}
		catch (IllegalStateException | IOException e)
		{
			FrameworkUtils.exceptionToString(e);
			return new ResultMessage(ResultCode.RESULT_BAD_REQUEST, "bad!!!!!!", null);
		}
		return new ResultMessage(ResultCode.RESULT_OK, "ok!!!!!!", resultList);
//		if(paramMap.getStr("group").equals("N"))
//		{
//			if(paramMap.getInt("seqgroup", 0) == 0)
//			{
//				addressService.InsertTempGroup(paramMap);
//			}
//			
//			int 				seqgroup 	= paramMap.getInt("seqgroup", 0);
//			List<MyCamelMap> 	targetList 	= resultList;
//
//			// ?????? ????????? ????????? List?????? ??????
//			String[] notphonenumber = paramMap.getStr("notphonenumber").split(",");
//			if(notphonenumber != null)
//			{
//				Iterator<MyCamelMap> iterator = targetList.iterator();
//				while(iterator.hasNext())
//				{
//					String phonenumber = iterator.next().getStr("phonenumber");
//					if(Arrays.asList(notphonenumber).contains(phonenumber))
//					{
//						iterator.remove();
//					}
//				}
//			}
//			//
//			//????????? ????????? ?????? ?????? !! 
//			//
//			new Thread()
//        	{
//        		@Override
//        		public void run()
//        		{
//        			DatabaseAuthInfoDuty databaseAuthInfoDuty = new DatabaseAuthInfoDuty(targetList, seqgroup, userSeq, grade);
//        			int failUpdateCount = databaseAuthInfoDuty.excute();
//        			LOGGER.info("failUpdateCount : " + failUpdateCount);
//        		}
//        	}.start();
//			
//			BasicBean resultBean = addressService.SelectTempTarget(paramMap);
//			
//			paramMap.put("listSize", resultList.size());
//			resultBean.setParamMap(paramMap);
//			resultBean.setList(resultList);
//			return new ResultMessage(ResultCode.RESULT_OK, "ok!!!!!!", resultBean);
//		}
//		else
//		{
//			String message = "";
//			
//			for(MyMap result : resultList)
//			{
//				// ???????????????
//				if(paramMap.getStr("etc").equals("P"))
//				{
//					result.put("phonenumber", 		result.getStr("phonenumber").replaceAll("-", ""));
//					result.put("SESSION_USER_SEQ", 	paramMap.getStr("SESSION_USER_SEQ"));
//					result.put("SESSION_GRADE", 	paramMap.getStr("SESSION_GRADE"));
//					
//					List<MyCamelMap> resultPData = addressService.SelectPTarget(result);
//					if(resultPData.size() == 0)
//					{
//						addressService.RegisterData(result);
//					}
//				}
//				//???????????????
//				else
//				{
//					if(!paramMap.getStr("seqgroupinfo", "0").equals("0"))
//					{
//						// ?????? ????????? ???????????? ?????????????????? ??????
//						result.put("seqgroupinfo", 		paramMap.getStr("seqgroupinfo"));
//						result.put("phonenumber", 		result.getStr("phonenumber").replaceAll("-", ""));
//						result.put("SESSION_USER_SEQ", 	paramMap.getStr("SESSION_USER_SEQ"));
//						result.put("SESSION_GRADE", 	paramMap.getStr("SESSION_GRADE"));
//						
//						List<MyCamelMap> resultData = addressService.SelectTarget(result);
//
//						// ???????????? ????????? ?????? ??? ????????? ??????
//						if(resultData.size() < 1)
//						{
//							List<MyCamelMap> resultPData = addressService.SelectPTarget(result);
//
//							if(resultPData.size() < 1)
//							{
//								addressService.RegisterData(result);
//								if(!paramMap.getStr("seqgroupinfo", "0").equals("0"))
//								{
//									result.put("seqgroupinfo", paramMap.getStr("seqgroupinfo"));
//									addressService.RegisterAddressMapper(result);
//								}
//							}
//							else
//							{
//								if(!paramMap.getStr("seqgroupinfo", "0").equals("0"))
//								{
//									resultPData.get(0).put("seqgroupinfo", paramMap.getStr("seqgroupinfo"));
//									addressService.RegisterAddressMapper(resultPData.get(0));
//								}
//							}
//						}
//					}
//				}
//			}
//			return new ResultMessage(ResultCode.RESULT_OK, "ok!!!!!!", message);
//		}


	}

	private void makeDir(String path)
	{
		File Folder = new File(path);
		if (!Folder.exists())
		{
			try
			{
			    Folder.mkdirs(); //?????? ???????????????.
			    LOGGER.info(" ????????? ?????????(" + path+")");
	        }
	        catch(Exception e)
			{
	        	e.getStackTrace();
			}
	    }
		else
		{
			LOGGER.info("????????? ?????????.");
		}
	}

	/**
	 * ?????? ?????? ?????? ?????? ?????????
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/MessageCheck")
	public String MessageCheck(Model model)
	{
		MyMap paramMap = FrameworkBeans.findHttpServletBean().findClientRequestParameter();

//		String ment0 	= FrameworkUtils.unescapeHtml(paramMap.getStr("ttsMent1").replaceAll("\r\n", "<br/>"));
		String intro 	= FrameworkUtils.unescapeHtml(paramMap.getStr("ttsMent1").replaceAll("\r\n", ""));
		String ment1 	= "";
		String ment2 	= "";
		String ttsMent 	= "";
		
		JSONArray mentArr = new JSONArray();
		
		JSONArray jArray2 = new JSONArray();
		JSONArray jArray3 = new JSONArray();
		
		// ?????? ??????????????? ????????????
		if(paramMap.getStrArray("ttsMent2").length == 0)
		{
//			ment1 = FrameworkUtils.unescapeHtml(paramMap.getStr("ttsMent2").replaceAll("\r\n", "<br/>"));
//			ment2 = FrameworkUtils.unescapeHtml(paramMap.getStr("ttsMent3").replaceAll("\r\n", "<br/>"));
			ment1 = FrameworkUtils.unescapeHtml(paramMap.getStr("ttsMent2").replaceAll("\r\n", ""));
			ment2 = FrameworkUtils.unescapeHtml(paramMap.getStr("ttsMent3").replaceAll("\r\n", ""));

			if(ment2.equals("null") || ment2.equals(null) || ment2.equals(""))
			{
				ttsMent = FrameworkUtils.unescapeHtml(intro+"<br/>"+ment1);
			}
			else
			{
				ttsMent = FrameworkUtils.unescapeHtml(intro+"<br/>"+ment1+"<br/>"+ment2);
			}
						
			mentArr.add(ttsMent.replaceAll("<br/>", ".  "));
		}
		else
		{
			ttsMent += intro;
			ttsMent += "<br/>";
			
			String str = intro + " ";
			for(int i=0; i<paramMap.getStrArray("ttsMent2").length; i++)
			{
				ttsMent += paramMap.getStrArray("ttsMent2")[i];
				ttsMent += "<br/>";
				ttsMent += paramMap.getStrArray("ttsMent3")[i];
				ttsMent += "<br/>";
				
//				if(i == 0)
//				{
//					str += intro;
//				}
				str += paramMap.getStrArray("ttsMent2")[i];
				str += ", ";
				str += paramMap.getStrArray("ttsMent3")[i];
				
				mentArr.add(str);
			}
			
			ttsMent = FrameworkUtils.unescapeHtml(ttsMent);
		}
		
		// ??? ????????? ??????
		if(paramMap.getStrArray("ttsMent2").length == 0)
		{
			jArray2.add(paramMap.getStr("ttsMent2"));
			jArray3.add(paramMap.getStr("ttsMent3"));
		}
		else
		{
			for(String str2 : paramMap.getStrArray("ttsMent2")) { jArray2.add(str2); }
			for(String str3 : paramMap.getStrArray("ttsMent3")) { jArray3.add(str3); }
		}
		
//		System.out.println("[1]" + paramMap.getStr("ttsMent1"));
//		System.out.println("[2]" + paramMap.getStr("ttsMent2"));
//		System.out.println("[3]" + paramMap.getStr("ttsMent3"));
//		System.out.println("[4]" + mentArr.toJSONString());

		model.addAttribute("mentArr", mentArr.toJSONString());
		model.addAttribute("ttsMent", ttsMent);
		model.addAttribute("originTTSMent1", paramMap.getStr("ttsMent1"));
		model.addAttribute("originTTSMent2", jArray2);
		model.addAttribute("originTTSMent3", jArray3);
		model.addAttribute("ment0", intro);
		model.addAttribute("ment1", ment1);
		model.addAttribute("ment2", ment2);
		model.addAttribute("paramMap", paramMap);
		
		System.out.println(mentArr.toJSONString());

		return "/msg/MessageCheck";
	}

	/**
	 * ????????? ?????? ?????????
	 */
	@RequestMapping("/TTSList")
	public String TTSList(Model model)
	{
		MyMap paramMap = FrameworkBeans.findHttpServletBean().findClientRequestParameter();

		BasicBean result = arsalarmttsService.ListPagingData(paramMap);

		if(result.getList().size() > 0)
		{
			String ttsMentIntro01 = FrameworkUtils.unescapeHtml(result.getList().get(0).getStr("ttsMentIntro01")).replaceAll("<br/>", "\r\n");
			result.getList().get(0).put("ttsMentIntro01", ttsMentIntro01);
		}


		model.addAttribute("list", 				result.getList());
		model.addAttribute("paginationInfo", 	result.getPaginationInfo());
		model.addAttribute("searchWord", 		paramMap.getStr("searchWord", ""));

		return "/msg/TTSList";
	}

	/**
	 * ???????????? ?????? ?????? ?????????
	 */
	@RequestMapping("/TTSUpload")
	public String TTSUpload(Model model)
	{
		return "/msg/TTSUpload";
	}

	/**
	 * TTS?????? ?????????
	 */
	@RequestMapping("/UploadTTSFile")
	public @ResponseBody ResultMessage UploadTTSFile(@RequestParam(value="uploadFile", required = false) MultipartFile file)
	{
//		ServiceCommon serviceCommon = (ServiceCommon) FrameworkBeans.findBean("com.inbiznetcorp.acs.framework.common.ServiceCommon");

		MyMap paramMap = FrameworkBeans.findHttpServletBean().findClientRequestParameter();

		String originalFilename = file.getOriginalFilename(); // ????????????
		String tempPath 		= null;
		String fileName 		= null;

		tempPath = env.getProperty("common.file.path");
//		if(serviceCommon.getProfilesActiveName().equals("local"))
//		{
//			tempPath = FrameworkBeans.findHttpServletBean().getHttpServletRequest().getSession().getServletContext().getRealPath("/")+File.separator+"uploadfile";
//		}
//		else
//		{
//			tempPath = env.getProperty("common.file.path");
//		}

		String uploadPath 		= tempPath + File.separator + FrameworkUtils.getCurrentDate("yyyyMMdd") + File.separator; // ????????? ???????????? ??????
		String fileExtsn 		= file.getContentType().split("\\/")[file.getContentType().split("\\/").length-1];

		makeDir(uploadPath); // ?????? ??????

		fileName 	= FrameworkUtils.getDateToStr() + RandomStringUtils.randomAlphabetic(5) + "_" + originalFilename;
		uploadPath 	= uploadPath + fileName; // ?????? ?????? ??????

		try
		{
			file.transferTo(new File(uploadPath)); // ?????? ??????

			// ???????????? DB??? INSERT
			paramMap.put("fileName", 		fileName);
			paramMap.put("uploadPath", 		uploadPath);
			paramMap.put("orignlFileNm", 	originalFilename);
			paramMap.put("fileExtsn", 		fileExtsn);
			fileinfoService.RegisterData(paramMap);
		}
		catch (IllegalStateException | IOException e)
		{
			FrameworkUtils.exceptionToString(e);
		}

		return new ResultMessage(ResultCode.RESULT_OK, "ok!!!!!!", paramMap);
	}

	@RequestMapping("/TTSListen")
	public String TTSListen()
	{
		return "/msg/TTSListen";
	}
	
	@RequestMapping("/getNextTargetList")
	public @ResponseBody ResultMessage getNextTargetList()
	{
		MyMap paramMap = FrameworkBeans.findHttpServletBean().findClientRequestParameter();
		
		BasicBean result = addressService.SelectTempTarget(paramMap);
		
		return new ResultMessage(ResultCode.RESULT_OK, "ok!!!!!!", result);
	}
	
	@RequestMapping("/FileUpload")
	public String FileUpload()
	{
		return "/msg/FileUpload";
	}
	
	@RequestMapping("/UploadFileTxt")
	public @ResponseBody ResultMessage UploadFileTxt(@RequestParam(value="uploadFile", required = false) MultipartFile file) throws FileNotFoundException
	{
		MyMap paramMap = FrameworkBeans.findHttpServletBean().findClientRequestParameter();
		
		String 		originalFilename 	= file.getOriginalFilename(); // ????????????
		String 		tempPath 			= env.getProperty("common.file.path");
		String 		uploadPath 			= tempPath + File.separator + FrameworkUtils.getCurrentDate("yyyyMMdd") + File.separator; // ????????? ???????????? ??????
		BasicBean 	result 				= null;
		int 		seqgroup 			= 0;

		makeDir(uploadPath); // ?????? ??????

		uploadPath = uploadPath + FrameworkUtils.getDateToStr() + RandomStringUtils.randomAlphabetic(5) + "_" + originalFilename; // ?????? ?????? ??????

		try
		{
			file.transferTo(new File(uploadPath)); // ?????? ??????

			FileReader fileReader = new FileReader(new File(uploadPath));
			BufferedReader budderReader = new BufferedReader(fileReader);
			
			addressService.InsertTempGroup(paramMap);
			seqgroup = paramMap.getInt("seqgroup", 0);
			
			String phonenumber = "";
			
			while((phonenumber = budderReader.readLine()) != null)
			{
//				System.out.println(phonenumber);
				paramMap.put("phonenumber", phonenumber.replaceAll("-", ""));
				addressService.UploadFileTxt(paramMap);
			}
			
			budderReader.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		paramMap.put("seqgroup", seqgroup);
		addressService.SelectTempTarget(paramMap);
		
		return new ResultMessage(ResultCode.RESULT_OK, "ok!!!!!!", result);
	}
	
	@RequestMapping("/DeleteTempTarget")
	public @ResponseBody ResultMessage DeleteTempTarget()
	{
		MyMap paramMap = FrameworkBeans.findHttpServletBean().findClientRequestParameter();
		
		addressService.DeleteTempTarget(paramMap);
		int count = addressService.SelectTempTargetCount(paramMap);
		
		return new ResultMessage(ResultCode.RESULT_OK, "ok!!!!!!", count);
	}
	
	@RequestMapping("/InsertTempGroup")
	public @ResponseBody ResultMessage InsertTempGroup()
	{
		MyMap paramMap = FrameworkBeans.findHttpServletBean().findClientRequestParameter();

		messageService.InsertTempGroup(paramMap);
		System.out.println("paramMap : " + paramMap);
		messageService.InsertTempTarget(paramMap);
		
		return new ResultMessage(ResultCode.RESULT_OK, "ok!!!!!!", paramMap);
	}
	
	@RequestMapping("/InsertTempTarget")
	public @ResponseBody ResultMessage InsertTempTarget()
	{
		MyMap paramMap = FrameworkBeans.findHttpServletBean().findClientRequestParameter();
		
		messageService.InsertTempTarget(paramMap);
		
		return new ResultMessage(ResultCode.RESULT_OK, "ok!!!!!!");
	}
}
