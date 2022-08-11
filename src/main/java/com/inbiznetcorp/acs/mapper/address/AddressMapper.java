package com.inbiznetcorp.acs.mapper.address;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.inbiznetcorp.acs.framework.config.mybatis.support.Master;
import com.inbiznetcorp.acs.framework.mymap.MyCamelMap;
import com.inbiznetcorp.acs.framework.mymap.MyMap;

@Master
@Repository("com.inbiznetcorp.acs.mapper.address.AddressMapper")
public interface AddressMapper {

	int 				SelectOnePagingCount		(MyMap paramMap);

	List<MyCamelMap> 	ListPagingData				(MyMap paramMap);

	int 				RegisterData				(MyMap paramMap);

	int 				DeletePData					(MyMap paramMap);

	int 				DeleteData					(MyMap paramMap);

	int 				DeleteAddressMapper			(MyMap paramMap);

	List<MyCamelMap> 	SelectAddressSeq			(MyMap paramMap);

	List<MyCamelMap> 	SelectTarget				(MyMap paramMap);

	List<MyCamelMap> 	SelectPTarget				(MyMap paramMap);

	List<MyCamelMap> 	SelectTargetByPhone			(MyMap paramMap);

	int 				DeleteMapperData			(MyMap paramMap);

	int 				SearchDupl					(MyMap paramMap);

	MyCamelMap 			AddressDetail				(MyMap paramMap);

	int 				ModifyPData					(MyMap paramMap);

	int 				DeletePDatas				(MyMap paramMap);

	int 				DeleteMapperDatas			(MyMap paramMap);

	int 				InsertTempGroup				(MyMap paramMap);

	int 				InsertTempTarget			(MyMap paramMap);

	List<MyCamelMap> 	SelectTempTarget			(MyMap paramMap);

	int 				SelectTempTargetCount		(MyMap paramMap);

	int 				UploadFileTxt				(MyMap paramMap);

	int 				DeleteTempTarget			(MyMap paramMap);

	List<MyCamelMap> 	selectTargetList			(MyMap paramMap);

}
