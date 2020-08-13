package com.inbiznetcorp.acs.mapper.result;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.inbiznetcorp.acs.framework.config.mybatis.support.Master;
import com.inbiznetcorp.acs.framework.mymap.MyCamelMap;
import com.inbiznetcorp.acs.framework.mymap.MyMap;

@Master
@Repository("com.inbiznetcorp.acs.mapper.result.ResultMapper")
public interface ResultMapper {

	List<MyCamelMap> 	ListPagingData						(MyMap paramMap);

	int 				SelectOnePagingCount				(MyMap paramMap);

	List<MyCamelMap> 	ListPagingMapperData				(MyMap paramMap);

	int 				SelectOnePagingMapperCount			(MyMap paramMap);

	void 				UpdateData							(MyMap paramMap);

	void 				InsertListenData					(MyMap paramMap);

	List<MyCamelMap> 	SelectRepeatListByIVRMapperSeq		(MyMap paramMap);

	int 				SelectRepeatListByIVRMapperSeqCount	(MyMap paramMap);

	List<MyCamelMap> 	SelectRepeatListBySenddate			(MyMap paramMap);

	int 				SelectRepeatListBySenddateCount		(MyMap paramMap);

	MyCamelMap 			MapperInfoData						(MyMap paramMap);

}
