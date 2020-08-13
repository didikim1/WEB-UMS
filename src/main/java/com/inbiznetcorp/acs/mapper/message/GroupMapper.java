package com.inbiznetcorp.acs.mapper.message;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.inbiznetcorp.acs.framework.config.mybatis.support.Master;
import com.inbiznetcorp.acs.framework.mymap.MyCamelMap;
import com.inbiznetcorp.acs.framework.mymap.MyMap;

@Master
@Repository("com.inbiznetcorp.acs.mapper.message.GroupMapper")
public interface GroupMapper {

	int 				SelectOnePagingCount		(MyMap paramMap);

	List<MyCamelMap> 	ListPagingData				(MyMap paramMap);

	List<MyCamelMap> 	ListPagingDataDetail		(MyMap paramMap);

	List<MyCamelMap> 	ListPagingDataMapper		(MyMap result);

	int 				ListPagingDataDetailCount	(MyMap paramMap);

	int 				DeleteGroup					(MyMap paramMap);

	int 				RegisterGroup				(MyMap paramMap);

	int 				RegisterAddressMapper		(MyMap result);

	int 				GroupNameDupl				(MyMap paramMap);

}
