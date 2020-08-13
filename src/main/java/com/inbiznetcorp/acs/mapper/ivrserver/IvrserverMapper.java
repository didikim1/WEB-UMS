package com.inbiznetcorp.acs.mapper.ivrserver;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.inbiznetcorp.acs.framework.config.mybatis.support.Master;
import com.inbiznetcorp.acs.framework.mymap.MyCamelMap;
import com.inbiznetcorp.acs.framework.mymap.MyMap;

@Master
@Repository("com.inbiznetcorp.acs.mapper.ivrserver.IvrserverMapper")
public interface IvrserverMapper
{

	List<MyCamelMap> 	SelectIVRServerList		(MyMap paramMap);

	int 				SelectIVRServerCount	(MyMap paramMap);

	MyCamelMap 			SelectIVRServer			(MyMap paramMap);

	int 				InsertIVRServer			(MyMap paramMap);

	int 				UpdateIVRServer			(MyMap paramMap);
	
}
