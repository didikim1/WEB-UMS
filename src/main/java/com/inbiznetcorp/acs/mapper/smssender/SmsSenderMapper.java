package com.inbiznetcorp.acs.mapper.smssender;

import org.springframework.stereotype.Repository;

import com.inbiznetcorp.acs.framework.config.mybatis.support.Master;
import com.inbiznetcorp.acs.framework.mymap.MyMap;

@Master
@Repository
public interface SmsSenderMapper {

	int insertResult(MyMap resultMap);

	int insertRequest(MyMap paramMap);

}
