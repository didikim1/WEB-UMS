package com.inbiznetcorp.acs.web.main.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.inbiznetcorp.acs.framework.beans.BasicBean;
import com.inbiznetcorp.acs.framework.mymap.MyCamelMap;
import com.inbiznetcorp.acs.framework.mymap.MyMap;
import com.inbiznetcorp.acs.framework.utils.FrameworkPagingUtils;
import com.inbiznetcorp.acs.mapper.address.AddressMapper;
import com.inbiznetcorp.acs.mapper.main.MainMapper;
import com.inbiznetcorp.acs.mapper.message.GroupMapper;

@Service("com.inbiznetcorp.acs.web.main.MainService")
public class MainService
{
	@Resource(name="com.inbiznetcorp.acs.mapper.main.MainMapper")
	MainMapper mMapper;
	
	@Resource(name="com.inbiznetcorp.acs.mapper.address.AddressMapper")
	AddressMapper addressMapper;

	@Resource(name="com.inbiznetcorp.acs.mapper.message.GroupMapper")
	GroupMapper groupMapper;

    /**
    * 페이징 데이터
    * @param paramMap
    * @return
    */
    public BasicBean ListPagingData(MyMap paramMap)
    {
        BasicBean resultBean = null;
        FrameworkPagingUtils.pagingRange(paramMap, paramMap.getInt("rows", 10));
        resultBean = FrameworkPagingUtils.pagingData(paramMap,
        		paramMap.getInt("rows", 10),
        		mMapper.SelectOnePagingCount(paramMap),
        		mMapper.ListPagingData(paramMap));

        return resultBean;
    }

    /**
    * 상세 조회
    * @param paramMap
    * @return
    */
    public MyCamelMap SelectOneData(MyMap paramMap)
    {
        return mMapper.SelectOneData(paramMap);
    }

    /**
    * 등록/수정
    * @param paramMap
    * @return
    */
    public int RegisterData(MyMap paramMap)
    {
        System.out.println("==================inset 전 ================");
        System.out.println(paramMap);
        System.out.println("==================//inset 전 ================");
    	if( mMapper.RegisterData(paramMap)  >0 )
    	{
    	 System.out.println("==================inset 후 ================");
         System.out.println(paramMap);
         System.out.println("==================//inset 후 ================");
    	}

    	return 0;
    }

    /**
    * 수정
    * @param paramMap
    * @return
    */
    public int ModifyData(MyMap paramMap)
    {

    	if(mMapper.ModifyData(paramMap) > 0 )
        {
    		paramMap.put("procSttus", "U");
        }
        return 0;
    }

    /**
    * 삭제
    * @param paramMap
    * @return
    */
    public int DeleteData(MyMap paramMap)
    {

    	return mMapper.DeleteData(paramMap);
    }
}
