package com.inbiznetcorp.acs.mapper.scheduler;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.inbiznetcorp.acs.framework.config.mybatis.support.Master;
import com.inbiznetcorp.acs.framework.mymap.MyCamelMap;
import com.inbiznetcorp.acs.framework.mymap.MyMap;

@Master
@Repository("com.inbiznetcorp.acs.mapper.scheduler.SchedulerMapper")
public interface SchedulerMapper
{

	int 				RegisterData				(MyMap paramMap);
	
	List<MyCamelMap> 	SelectNormalCallList		(MyMap paramMap);

	List<MyCamelMap> 	SelectImportantCallList		(MyMap paramMap);

	int 				UpdateCallStatusI			(MyCamelMap call);
	
	int 				UpdateCallStatusC			(MyCamelMap call);

	MyCamelMap 			SelectNormalCall			(MyMap paramMap);
	
	MyCamelMap 			SelectImportantCall			(MyMap paramMap);

}
