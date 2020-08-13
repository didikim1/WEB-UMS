package com.inbiznetcorp.acs.mapper.userinfo;

import org.springframework.stereotype.Repository;

import com.inbiznetcorp.acs.framework.config.mybatis.support.Master;
import com.inbiznetcorp.acs.framework.mymap.MyCamelMap;
import com.inbiznetcorp.acs.framework.mymap.MyMap;

@Master
@Repository("com.inbiznetcorp.acs.mapper.userinfo.UserInfoMapper")
public interface UserInfoMapper
{

	MyCamelMap FindUserInfo(MyMap paramMap);

}
