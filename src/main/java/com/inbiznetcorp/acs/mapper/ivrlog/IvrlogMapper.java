package com.inbiznetcorp.acs.mapper.ivrlog;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.inbiznetcorp.acs.framework.config.mybatis.support.Master;
import com.inbiznetcorp.acs.framework.mymap.MyCamelMap;
import com.inbiznetcorp.acs.framework.mymap.MyMap;

@Master
@Repository("com.inbiznetcorp.acs.mapper.ivrlog.IvrlogMapper")
public interface IvrlogMapper {

	int 				RegisterMapperData			(MyMap paramMap);

	int 				RegisterData				(MyMap targetMap);

	List<MyCamelMap> 	SelectRetryList				(MyMap paramMap);

	MyCamelMap 			SelectRetryTarget			(MyMap paramMap);

	int 				InsertIVRHistory			(MyMap paramMap);

	List<MyCamelMap> 	SelectARSReservation		(MyMap paramMap);

	int 				UpdateStatus				(MyMap target);

	int 				ReservationCancel			(MyMap paramMap);

	int 				DeleteIVRLogMapper			(MyMap paramMap);

	int 				RegistIVRListenlog			(MyMap paramMap);

	int 				UpdateIVRLog				(MyMap paramMap);

	List<MyCamelMap> 	SelectARSRepeat				(MyMap paramMap);

	int 				UpdateSendTime				(MyMap paramMap);

	int 				UpdateMapperSendTime		(MyMap paramMap);

	int 				RepeatCancel				(MyMap paramMap);

	int 				UpdateMapperReservationDate	(MyMap paramMap);

	List<MyCamelMap> 	SelectSendRepeatCallList	(MyMap paramMap);

	MyCamelMap 			SelectIVRLog				(String ivrlogseq);

	int 				UpdateIvrlogmapperRound		(MyMap paramMap);

	MyCamelMap 			SelectIvrlogmapper			(MyMap paramMap);

}
