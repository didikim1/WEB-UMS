package com.inbiznetcorp.acs.framework.beans;

import java.util.List;

import com.inbiznetcorp.acs.framework.mymap.MyCamelMap;
import com.inbiznetcorp.acs.framework.mymap.MyMap;

import egovframework.pagination.PaginationInfo;

public class BasicBean
{
    private PaginationInfo      paginationInfo;
    private MyMap               paramMap;
    private List<MyCamelMap>    list;
    private List<MyCamelMap>    allList;

    public PaginationInfo       getPaginationInfo()     { return paginationInfo; }
    public MyMap                getParamMap()           { return paramMap; }
    public List<MyCamelMap>     getList()               { return list; }
    public List<MyCamelMap>     getAllList()            { return allList; }

    public void setPaginationInfo(PaginationInfo paginationInfo) { this.paginationInfo = paginationInfo; }
    public void setParamMap(MyMap paramMap)                      { this.paramMap = paramMap; }
    public void setList(List<MyCamelMap> list)                   { this.list = list; }
    public void setAllList(List<MyCamelMap> allList)             { this.allList = allList; }
}
