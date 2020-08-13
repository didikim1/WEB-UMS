package com.inbiznetcorp.acs.mapper.ttsfileputhistory;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.inbiznetcorp.acs.framework.config.mybatis.support.Master;
import com.inbiznetcorp.acs.framework.mymap.MyCamelMap;
import com.inbiznetcorp.acs.framework.mymap.MyMap;

@Master
@Repository("com.inbiznetcorp.acs.mapper.ttsfileputhistory.TTSfileputhistoryMapper")
public interface TTSfileputhistoryMapper {

	int 				RegistTTSFilePutHistory			(MyMap paramMap);

	List<MyMap> 		SelectTTSfileputhistoryList		(MyMap paramMap);
//	List<MyMap> 		SelectTTSfileputhistoryList		(String ivrlogseq);

	MyCamelMap 			SelectTTSFile					(MyMap paramMap);

}
