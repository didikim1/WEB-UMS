package com.inbiznetcorp.acs.mapper.message;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.inbiznetcorp.acs.framework.config.mybatis.support.Master;
import com.inbiznetcorp.acs.framework.mymap.MyCamelMap;
import com.inbiznetcorp.acs.framework.mymap.MyMap;

@Master
@Repository("com.inbiznetcorp.acs.mapper.message.ArsalarmttsMapper")
public interface ArsalarmttsMapper {

	int 				SelectOnePagingCount	(MyMap paramMap);

	List<MyCamelMap> 	ListPagingData			(MyMap paramMap);

	int 				RegisterData			(MyMap paramMap);

}
