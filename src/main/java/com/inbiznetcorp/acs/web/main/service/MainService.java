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
import com.inbiznetcorp.acs.mapper.userinfo.UserInfoMapper;

@Service("com.inbiznetcorp.acs.web.main.service.MainService")
public class MainService
{
	@Resource(name="com.inbiznetcorp.acs.mapper.main.MainMapper")
	MainMapper mMapper;
	
	@Resource(name="com.inbiznetcorp.acs.mapper.address.AddressMapper")
	AddressMapper addressMapper;

	@Resource(name="com.inbiznetcorp.acs.mapper.message.GroupMapper")
	GroupMapper groupMapper;
	
	@Resource(name="com.inbiznetcorp.acs.mapper.userinfo.UserInfoMapper")
	UserInfoMapper mUserInfoMapper;

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
//    public int RegisterData(MyMap paramMap)
//    {
//        System.out.println("==================inset 전 ================");
//        System.out.println(paramMap);
//        System.out.println("==================//inset 전 ================");
//    	if( mMapper.RegisterData(paramMap)  >0 )
//    	{
//    	 System.out.println("==================inset 후 ================");
//         System.out.println(paramMap);
//         System.out.println("==================//inset 후 ================");
//    	}
//
//    	return 0;
//    }

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
    
	public MyCamelMap FindUserInfo(MyMap paramMap)
	{
		String grade = paramMap.getStr("SESSION_GRADE"); 	// A:관리자, B:사용자
		
		return grade.equals("A") ? mUserInfoMapper.FindAdminInfo(paramMap) : mUserInfoMapper.FindUserInfo(paramMap);
	}

	public int RegisterData(MyMap paramMap)
	{
//		String grade 		= paramMap.getStr("SESSION_GRADE"); 	// A:관리자, B:사용자
		String userPw 		= paramMap.getStr("password1", "");
		String userName 	= paramMap.getStr("userName");
		String tel 			= paramMap.getStr("tel1") + paramMap.getStr("tel2") + paramMap.getStr("tel3");
		String phoneNumber 	= paramMap.getStr("phone1") + paramMap.getStr("phone2") + paramMap.getStr("phone3");
		String email 		= paramMap.getStr("email1") + "@" + paramMap.getStr("email2");
//		String address 		= paramMap.getStr("postcode") + paramMap.getStr("address") + paramMap.getStr("detailAddress") + paramMap.getStr("extraAddress");
		
		if(!userPw.equals("")) { paramMap.put("userPw", userPw); }
		
		paramMap.put("userName", 	userName);
		paramMap.put("tel", 		tel);
		paramMap.put("phoneNumber", phoneNumber);
		paramMap.put("email", 		email);
//		paramMap.put("address", 	address);
		
		return mMapper.RegisterData(paramMap);
	}
}
