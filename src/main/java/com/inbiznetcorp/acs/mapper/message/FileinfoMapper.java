package com.inbiznetcorp.acs.mapper.message;

import org.springframework.stereotype.Repository;

import com.inbiznetcorp.acs.framework.config.mybatis.support.Master;
import com.inbiznetcorp.acs.framework.mymap.MyCamelMap;
import com.inbiznetcorp.acs.framework.mymap.MyMap;

@Master
@Repository("com.inbiznetcorp.acs.mapper.message.FileinfoMapper")
public interface FileinfoMapper {

	int 		RegisterData	(MyMap paramMap);

	MyCamelMap 	SelectFile		(MyMap paramMap);

}
