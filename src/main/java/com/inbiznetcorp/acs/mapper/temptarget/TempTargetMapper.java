package com.inbiznetcorp.acs.mapper.temptarget;

import org.springframework.stereotype.Repository;

import com.inbiznetcorp.acs.framework.config.mybatis.support.Master;
import com.inbiznetcorp.acs.framework.mymap.MyMap;

@Master
@Repository("com.inbiznetcorp.acs.mapper.temptarget.TempTargetMapper")
public interface TempTargetMapper {

	int InsertTempTarget(MyMap paramMap);


}
