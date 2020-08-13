package com.inbiznetcorp.acs.web.ivr.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.inbiznetcorp.acs.framework.beans.BasicBean;
import com.inbiznetcorp.acs.framework.mymap.MyCamelMap;
import com.inbiznetcorp.acs.framework.mymap.MyMap;
import com.inbiznetcorp.acs.framework.utils.FrameworkPagingUtils;
import com.inbiznetcorp.acs.mapper.address.AddressMapper;
import com.inbiznetcorp.acs.mapper.ivrlog.IvrlogMapper;
import com.inbiznetcorp.acs.mapper.makettshistory.MakettshistoryMapper;
import com.inbiznetcorp.acs.mapper.message.FileinfoMapper;
import com.inbiznetcorp.acs.mapper.result.ResultMapper;
import com.inbiznetcorp.acs.mapper.ttsfileputhistory.TTSfileputhistoryMapper;

@Service("com.inbiznetcorp.acs.web.ivr.service.IVRService")
public class IVRService
{
	@Resource(name="com.inbiznetcorp.acs.mapper.address.AddressMapper")
	AddressMapper addressMapper;
	
	@Resource(name="com.inbiznetcorp.acs.mapper.result.ResultMapper")
	ResultMapper resultMapper;

	@Resource(name="com.inbiznetcorp.acs.mapper.ivrlog.IvrlogMapper")
	IvrlogMapper ivrlogMapper;

	@Resource(name="com.inbiznetcorp.acs.mapper.message.FileinfoMapper")
	FileinfoMapper fileinfoMapper;
	
	@Resource(name="com.inbiznetcorp.acs.mapper.makettshistory.MakettshistoryMapper")
	MakettshistoryMapper makettshistoryMapper;
	
	@Resource(name="com.inbiznetcorp.acs.mapper.ttsfileputhistory.TTSfileputhistoryMapper")
	TTSfileputhistoryMapper ttsfileputhistoryMapper;

	public BasicBean ListPagingData(MyMap paramMap)
	{
		BasicBean resultBean = null;

		FrameworkPagingUtils.pagingRange(paramMap, paramMap.getInt("rows", 20));

		String searchType 		= paramMap.getStr("searchType", "NAME");
		String searchWord 		= paramMap.getStr("searchWord", "");
		String searchWordOri 	= searchWord;

//		if(!searchWord.equals("")  
		if(!searchWord.equals("") && searchType.equals("RESULT"))
		{
			if("성공".indexOf(searchWord) > -1)
			{
				searchWord = "00";
			}
			else if("없는번호".indexOf(searchWord) > -1)
			{
				searchWord = "10";
			}
			else if("수신거부".indexOf(searchWord) > -1)
			{
				searchWord = "11";
			}
			else if("전화안받음".indexOf(searchWord) > -1)
			{
				searchWord = "12";
			}
			else if("재시도 횟수 초과".indexOf(searchWord) > -1)
			{
				searchWord = "31";
			}
			else if("통화중".indexOf(searchWord) > -1)
			{
				searchWord = "41";
			}
			else if("전원꺼짐".indexOf(searchWord) > -1)
			{
				searchWord = "42";
			}
			else if("통화 종료".indexOf(searchWord) > -1)
			{
				searchWord = "20";
			}
			else if("오류".indexOf(searchWord) > -1)
			{
				searchWord = "99";
			}
			else if("진행중".indexOf(searchWord) > -1)
			{
				searchType = "STATUS_COMPLETION";
				searchWord = "I";
			}
		}

		if(paramMap.getStr("sidx", "").equals(""))
		{
			paramMap.put("sidx", "ROW_NUM");
			paramMap.put("sord", "DESC");
		}

		paramMap.put("searchType", searchType);
		paramMap.put("searchWord", searchWord);

		resultBean = FrameworkPagingUtils.pagingData(paramMap, paramMap.getInt("rows", 20),
				resultMapper.SelectOnePagingCount(paramMap), resultMapper.ListPagingData(paramMap));

		// 페이징 없이 검색된 모든 list
		paramMap.put("start", 0);
		paramMap.put("end", resultMapper.SelectOnePagingCount(paramMap));
		resultBean.setAllList(resultMapper.ListPagingData(paramMap));
		//
		
		paramMap.put("searchWord", searchWordOri);

		return resultBean;
	}

	public BasicBean ListPagingMapperData(MyMap paramMap)
	{
		BasicBean resultBean = null;

		if(paramMap.getStr("sidx", "").equals(""))
		{
			paramMap.put("sidx", "ROW_NUM");
			paramMap.put("sord", "DESC");
		}

		FrameworkPagingUtils.pagingRange(paramMap, paramMap.getInt("rows", 20));
		resultBean = FrameworkPagingUtils.pagingData(paramMap, paramMap.getInt("rows", 20),
				resultMapper.SelectOnePagingMapperCount(paramMap), resultMapper.ListPagingMapperData(paramMap));

		return resultBean;
	}

	public int RegisterMapperData(MyMap paramMap)
	{
		return ivrlogMapper.RegisterMapperData(paramMap);
	}

	public int RegisterData(MyMap paramMap)
	{
		if(paramMap.getStr("nextcallDate").equals("")) { paramMap.put("nextcallDate", null); }
		if(paramMap.getStr("repeatType").equals("")) { paramMap.put("repeatType", null); }
		return ivrlogMapper.RegisterData(paramMap);
	}

	public List<MyCamelMap> SelectRetryList(MyMap paramMap)
	{
		return ivrlogMapper.SelectRetryList(paramMap);
	}

	public MyCamelMap SelectRetryTarget(MyMap paramMap)
	{
		return ivrlogMapper.SelectRetryTarget(paramMap);
	}

	public int RegistMakeTTSHistory(MyMap paramMap)
	{
		return makettshistoryMapper.RegistMakeTTSHistory(paramMap);
	}

	public int ReservationCancel(MyMap paramMap)
	{
		ivrlogMapper.ReservationCancel(paramMap);

		int result = 0;
		if(resultMapper.SelectOnePagingCount(paramMap) == 0)
		{
			result = ivrlogMapper.DeleteIVRLogMapper(paramMap);
		}

		return result;
	}

	public int RegistTTSFilePutHistory(MyMap paramMap)
	{
		return ttsfileputhistoryMapper.RegistTTSFilePutHistory(paramMap);
	}

	public int RegistIVRListenlog(MyMap paramMap)
	{
		return ivrlogMapper.RegistIVRListenlog(paramMap);
	}

	public int UpdateIVRLog(MyMap paramMap)
	{
		return ivrlogMapper.UpdateIVRLog(paramMap);
	}

	public int UpdateSendTime(MyMap paramMap)
	{
		return ivrlogMapper.UpdateSendTime(paramMap);
	}

	public int UpdateMapperSendTime(MyMap paramMap)
	{
		return ivrlogMapper.UpdateMapperSendTime(paramMap);
	}

	public int RepeatCancel(MyMap paramMap)
	{
		return ivrlogMapper.RepeatCancel(paramMap);
	}

	public int UpdateMapperReservationDate(MyCamelMap target)
	{
		return ivrlogMapper.UpdateMapperReservationDate(target);
	}

	public BasicBean SelectRepeatListByIVRMapperSeq(MyMap paramMap)
	{
		BasicBean resultBean = null;

		if(paramMap.getStr("sidx", "").equals(""))
		{
			paramMap.put("sidx", "ROW_NUM");
			paramMap.put("sord", "DESC");
		}

		FrameworkPagingUtils.pagingRange(paramMap, paramMap.getInt("rows", 20));
		resultBean = FrameworkPagingUtils.pagingData(paramMap, paramMap.getInt("rows", 20),
				resultMapper.SelectRepeatListByIVRMapperSeqCount(paramMap), resultMapper.SelectRepeatListByIVRMapperSeq(paramMap));

		return resultBean;
	}

	public List<MyCamelMap> SelectSendRepeatCallList(MyMap paramMap)
	{
		return ivrlogMapper.SelectSendRepeatCallList(paramMap);
	}

	public BasicBean SelectRepeatListBySenddate(MyMap paramMap)
	{
		BasicBean resultBean = null;

		if(paramMap.getStr("sidx", "").equals(""))
		{
			paramMap.put("sidx", "ROW_NUM");
			paramMap.put("sord", "DESC");
		}

		if(paramMap.getStr("searchType").equals("USER_INPUT"))
		{
			if(paramMap.getStr("searchWord").equals("-"))
			{
				paramMap.put("inputNull", "-");
			}
		}

		FrameworkPagingUtils.pagingRange(paramMap, paramMap.getInt("rows", 20));
		resultBean = FrameworkPagingUtils.pagingData(paramMap, paramMap.getInt("rows", 20),
				resultMapper.SelectRepeatListBySenddateCount(paramMap), resultMapper.SelectRepeatListBySenddate(paramMap));

		return resultBean;
	}

	public MyCamelMap MapperInfoData(MyMap paramMap)
	{
		return resultMapper.MapperInfoData(paramMap);
	}

	public MyCamelMap SelectIVRLog(String ivrlogseq)
	{
		return ivrlogMapper.SelectIVRLog(ivrlogseq);
	}

	public MyCamelMap SelectFile(MyMap paramMap)
	{
		return fileinfoMapper.SelectFile(paramMap);
	}

	public List<MyCamelMap> SelectTempTargets(MyMap paramMap)
	{
		return addressMapper.SelectTempTarget(paramMap);
	}

	public int UpdateIvrlogmapperRound(MyMap paramMap)
	{
		return ivrlogMapper.UpdateIvrlogmapperRound(paramMap);
	}

	public MyCamelMap SelectIvrlogmapper(MyMap paramMap)
	{
		return ivrlogMapper.SelectIvrlogmapper(paramMap);
	}

	public MyCamelMap SelectTTSFile(MyMap paramMap)
	{
		return ttsfileputhistoryMapper.SelectTTSFile(paramMap);
	}

}
