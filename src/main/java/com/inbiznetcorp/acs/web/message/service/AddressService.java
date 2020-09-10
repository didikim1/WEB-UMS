package com.inbiznetcorp.acs.web.message.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.inbiznetcorp.acs.framework.beans.BasicBean;
import com.inbiznetcorp.acs.framework.mymap.MyCamelMap;
import com.inbiznetcorp.acs.framework.mymap.MyMap;
import com.inbiznetcorp.acs.framework.utils.FrameworkPagingUtils;
import com.inbiznetcorp.acs.mapper.address.AddressMapper;
import com.inbiznetcorp.acs.mapper.message.GroupMapper;

@Service("com.inbiznetcorp.acs.web.message.service.AddressService")
public class AddressService
{
	@Resource(name="com.inbiznetcorp.acs.mapper.address.AddressMapper")
	AddressMapper addressMapper;

	@Resource(name="com.inbiznetcorp.acs.mapper.message.GroupMapper")
	GroupMapper groupMapper;

	public BasicBean ListPagingData(MyMap paramMap)
	{
		BasicBean resultBean = null;

		FrameworkPagingUtils.pagingRange(paramMap, paramMap.getInt("rows", 10));

		String searchType = paramMap.getStr("searchType", "P");
		if(searchType.equals("P"))
		{
			paramMap.put("searchType", "NAME");
		}

		resultBean = FrameworkPagingUtils.pagingData(paramMap, paramMap.getInt("rows", 10),
				addressMapper.SelectOnePagingCount(paramMap), addressMapper.ListPagingData(paramMap));

		paramMap.put("searchType", searchType);

		return resultBean;
	}

	public BasicBean ListPagingDataGroup(MyMap paramMap)
	{
		BasicBean resultBean = null;

		String searchType = paramMap.getStr("searchType", "G");
		if(searchType.equals("G"))
		{
			paramMap.put("searchType", "GROUPNAME");
		}

		FrameworkPagingUtils.pagingRange(paramMap, paramMap.getInt("rows", 10));
		resultBean = FrameworkPagingUtils.pagingData(paramMap, paramMap.getInt("rows", 10),
				groupMapper.SelectOnePagingCount(paramMap), groupMapper.ListPagingData(paramMap));

		paramMap.put("searchType", searchType);

		return resultBean;
	}

	public List<MyCamelMap> ListUpData(MyMap paramMap)
	{
		if(paramMap.getStr("listtype").equals("P"))
		{
			return addressMapper.ListPagingData(paramMap);
		}
		else
		{
			paramMap.put("groupseq", paramMap.getInt("seq", 0));

			List<MyCamelMap> sResultMap = groupMapper.ListPagingDataDetail(paramMap);
			String seqMapperStr = "'";

			for(int i=0; i<sResultMap.size(); i++)
			{
				seqMapperStr += sResultMap.get(i).getInt("groupseq");
				if( i != sResultMap.size()-1) { seqMapperStr += "', '"; }
				else { seqMapperStr += "'"; }
			}

			MyMap seqMapperMap = new MyMap();
			seqMapperMap.put("groupseq", seqMapperStr);

			List<MyCamelMap> mResultMap = groupMapper.ListPagingDataMapper(seqMapperMap);

			String seqAddressStr = "'";
			for(int i=0; i<mResultMap.size(); i++)
			{
				seqAddressStr += mResultMap.get(i).getInt("seqaddressinfo");
				if( i != mResultMap.size()-1) { seqAddressStr += "', '"; }
				else { seqAddressStr += "'"; }
			}

			MyMap seqAddressMap = new MyMap();
			seqAddressMap.put("seq", 			seqAddressStr);
			seqAddressMap.put("notphonenumber", paramMap.getStr("notphonenumber", ""));

			return addressMapper.ListPagingData(seqAddressMap);
		}
	}

	public int RegisterData(MyMap paramMap)
	{
		return addressMapper.RegisterData(paramMap);
	}

	public int DeletePData(MyMap paramMap)
	{
		return addressMapper.DeletePData(paramMap);
	}

	public int DeleteData(MyMap paramMap)
	{
		return addressMapper.DeleteData(paramMap);
	}

	public BasicBean ListPagingDataDetail(MyMap paramMap)
	{
		BasicBean resultBean = null;

		FrameworkPagingUtils.pagingRange(paramMap, paramMap.getInt("rows", 10));
		resultBean = FrameworkPagingUtils.pagingData(paramMap, paramMap.getInt("rows", 10),
				groupMapper.ListPagingDataDetailCount(paramMap), groupMapper.ListPagingDataDetail(paramMap));

		return resultBean;
	}

	public int DeleteGroup(MyMap paramMap)
	{
		addressMapper.DeleteAddressMapper(paramMap);
		return groupMapper.DeleteGroup(paramMap);
	}

	public String SelectAddressSeq(MyMap paramMap)
	{
		List<MyCamelMap> resultList = addressMapper.SelectAddressSeq(paramMap);

		String seqStr = "";

		if(resultList.size() > 0)
		{
			seqStr += "'";
		}

		for(int i=0; i<resultList.size(); i++)
		{
			seqStr += resultList.get(i).getStr("seqaddressinfo", "");
			if( i != resultList.size()-1) { seqStr += "', '"; }
			else { seqStr += "'"; }
		}

		if(seqStr.equals(""))
		{
			seqStr = "'0'";
		}

		return seqStr;
	}

	public int RegisterGroup(MyMap paramMap)
	{
		return groupMapper.RegisterGroup(paramMap);
	}

	public int RegisterAddressMapper(MyMap result)
	{
		return groupMapper.RegisterAddressMapper(result);
	}

	public List<MyCamelMap> SelectTarget(MyMap paramMap)
	{
		return addressMapper.SelectTarget(paramMap);
	}

	public List<MyCamelMap> SelectPTarget(MyMap paramMap)
	{
		return addressMapper.SelectPTarget(paramMap);
	}

	public List<MyCamelMap> SelectTargetByPhone(MyMap paramMap)
	{
		return addressMapper.SelectTargetByPhone(paramMap);
	}

	public int GroupNameDupl(MyMap paramMap)
	{
		return groupMapper.GroupNameDupl(paramMap);
	}

	public int DeleteMapperData(MyMap paramMap)
	{
		return addressMapper.DeleteMapperData(paramMap);
	}

	public int SearchDupl(MyMap paramMap)
	{
		return addressMapper.SearchDupl(paramMap);
	}

	public MyCamelMap AddressDetail(MyMap paramMap)
	{
		return addressMapper.AddressDetail(paramMap);
	}

	public List<MyCamelMap> getAddressList(MyMap paramMap)
	{
		BasicBean resultBean = null;

		FrameworkPagingUtils.pagingRange(paramMap, paramMap.getInt("rows", 10));

		resultBean = FrameworkPagingUtils.pagingData(paramMap, paramMap.getInt("rows", 10),
				addressMapper.SelectOnePagingCount(paramMap), addressMapper.ListPagingData(paramMap));

		return resultBean.getList();
	}

	public int ModifyPData(MyMap paramMap)
	{
		return addressMapper.ModifyPData(paramMap);
	}

	public int DeletePDatas(MyMap paramMap)
	{
		return addressMapper.DeletePDatas(paramMap);
	}

	public int DeleteMapperDatas(MyMap paramMap)
	{
		return addressMapper.DeleteMapperDatas(paramMap);
	}

	public int InsertTempGroup(MyMap paramMap)
	{
		return addressMapper.InsertTempGroup(paramMap);
	}

	public int InsertTempTarget(MyMap paramMap)
	{
		return addressMapper.InsertTempTarget(paramMap);
	}

	public BasicBean SelectTempTarget(MyMap paramMap)
	{
		BasicBean resultBean = null;

		FrameworkPagingUtils.pagingRange(paramMap, paramMap.getInt("rows", 100));

		resultBean = FrameworkPagingUtils.pagingData(paramMap, paramMap.getInt("rows", 10),
				addressMapper.SelectTempTargetCount(paramMap), addressMapper.SelectTempTarget(paramMap));

		return resultBean;
	}

	public int UploadFileTxt(MyMap paramMap)
	{
		return addressMapper.UploadFileTxt(paramMap);
	}

	public int DeleteTempTarget(MyMap paramMap)
	{
		return addressMapper.DeleteTempTarget(paramMap);
	}

	public int SelectTempTargetCount(MyMap paramMap)
	{
		return addressMapper.SelectTempTargetCount(paramMap);
	}

}
