package com.inbiznetcorp.acs.framework.utils;

import java.util.List;

import com.inbiznetcorp.acs.framework.beans.BasicBean;
import com.inbiznetcorp.acs.framework.mymap.MyCamelMap;
import com.inbiznetcorp.acs.framework.mymap.MyMap;

import egovframework.pagination.PaginationInfo;

public class FrameworkPagingUtils
{
    public static void pagingRange(MyMap paramMap, int iRecordCountPerPage)
    {
        PaginationInfo   paginationInfo   = new PaginationInfo();
        paginationInfo.setCurrentPageNo(paramMap.getInt("page", 1));
        paginationInfo.setRecordCountPerPage(paramMap.getInt("rows", iRecordCountPerPage));

        paramMap.put("start", paginationInfo.getFirstRecordIndex());
        paramMap.put("end",   paginationInfo.getRecordCountPerPage());
    }

    public static BasicBean pagingData(MyMap paramMap, int iRecordCountPerPage, int iToatal, List<MyCamelMap> dataList)
    {
        PaginationInfo   paginationInfo   = new PaginationInfo();
        BasicBean        bean             = new BasicBean();

        paginationInfo.setCurrentPageNo(paramMap.getInt("page", 1));
        paginationInfo.setRecordCountPerPage(paramMap.getInt("rows", iRecordCountPerPage));

        paginationInfo.setTotalRecordCount(iToatal);

        bean.setList(dataList);
        bean.setPaginationInfo(paginationInfo);

        return bean;
    }
}
