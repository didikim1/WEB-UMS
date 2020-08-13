package com.inbiznetcorp.acs.mapper.result;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.inbiznetcorp.acs.framework.config.mybatis.support.Master;
import com.inbiznetcorp.acs.framework.mymap.MyCamelMap;
import com.inbiznetcorp.acs.framework.mymap.MyMap;

@Master
@Repository("com.inbiznetcorp.acs.mapper.result.SurveyMapper")
public interface SurveyMapper
{

	List<MyCamelMap> 		SelectSurveyList			(MyMap paramMap);

	int 					SelectSurveyListCount		(MyMap paramMap);

	MyCamelMap 				SelectSurveyMapper			(MyMap paramMap);

	MyCamelMap 				SelectSurvey				(MyMap paramMap);

	MyCamelMap 				SelectSurveyStatistic		(MyMap paramMap);

	List<MyCamelMap> 		SelectSurveyIVRLog			(MyMap paramMap);

}
