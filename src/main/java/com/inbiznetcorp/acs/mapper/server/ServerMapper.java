package com.inbiznetcorp.acs.mapper.server;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.inbiznetcorp.acs.framework.config.mybatis.support.Master;
import com.inbiznetcorp.acs.framework.mymap.MyCamelMap;
import com.inbiznetcorp.acs.framework.mymap.MyMap;

@Master
@Repository("com.inbiznetcorp.acs.mapper.server.ServerMapper")
public interface ServerMapper {

	List<MyCamelMap> 	ListPagingData			(MyMap paramMap);
	int 				SelectOnePagingCount	(MyMap paramMap);

}
