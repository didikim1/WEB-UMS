package com.inbiznetcorp.acs.mapper.makettshistory;

import org.springframework.stereotype.Repository;

import com.inbiznetcorp.acs.framework.config.mybatis.support.Master;
import com.inbiznetcorp.acs.framework.mymap.MyMap;

@Master
@Repository("com.inbiznetcorp.acs.mapper.makettshistory.MakettshistoryMapper")
public interface MakettshistoryMapper {

	int RegistMakeTTSHistory(MyMap paramMap);

}
