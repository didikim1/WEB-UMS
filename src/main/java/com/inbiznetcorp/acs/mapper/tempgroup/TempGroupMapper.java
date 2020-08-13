package com.inbiznetcorp.acs.mapper.tempgroup;

import org.springframework.stereotype.Repository;

import com.inbiznetcorp.acs.framework.config.mybatis.support.Master;
import com.inbiznetcorp.acs.framework.mymap.MyMap;

@Master
@Repository("com.inbiznetcorp.acs.mapper.tempgroup.TempGroupMapper")
public interface TempGroupMapper {

	int InsertTempGroup(MyMap paramMap);


}
