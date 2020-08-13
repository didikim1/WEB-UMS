package com.inbiznetcorp.acs.web.excel.read;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.inbiznetcorp.acs.framework.mymap.MyCamelMap;
import com.inbiznetcorp.acs.framework.mymap.MyMap;
import com.inbiznetcorp.acs.framework.utils.FrameworkUtils;
public class ExcelRead
{
	public List<MyCamelMap> Proc(String excelFilePath)
	{
		List<MyCamelMap> datas = new ArrayList<MyCamelMap>();

		String 					strErrorMsg 	= null;
		Workbook 				workbook	 	= null;
		int		 				iRowCnt  		= 0;
		int 					iCellCnt 		= 0;

		try
		{
			workbook = WorkbookFactory.create(new File(excelFilePath));

		    Sheet sheet = workbook.getSheetAt(0);
		    DataFormatter dataFormatter = new DataFormatter();

		    EXIT:
		    for (Row row: sheet)
		    {
		    	iRowCnt++;
		    	iCellCnt = row.getPhysicalNumberOfCells();

		    	if(iCellCnt > 0)
		    	{
		    		MyCamelMap dataRow = new MyCamelMap();
		    		for(int i=0; i<4; i++)
			    	{
			    		String cellValue = dataFormatter.formatCellValue(row.getCell(i));
			    		
			    		if(i == 0)
			    		{
//			    			if((cellValue == null) || (cellValue.equals("")))
//			    			{
//			    				strErrorMsg = "A열 " + iRowCnt + "번째 이름을 확인 부탁드립니다.";
//			    				break EXIT;
//			    			}
//			    			else if(cellValue.equals("#"))
//			    			{
//			    				break EXIT;
//			    			}
			    			if(cellValue.equals("#"))
			    			{
			    				break EXIT;
			    			}
			    			dataRow.put("name", cellValue);
			    		}
			    		else if(i == 1)
			    		{
			    			if((cellValue == null) || (cellValue.equals("") || (!cellValue.substring(0, 1).equals("0"))))
			    			{
			    				strErrorMsg = "B열 " + iRowCnt + "번째 전화번호를 확인 부탁드립니다.";
			    				break EXIT;
			    			}
			    			else
			    			{
			    				if(cellValue.indexOf("-") < 0)
			    				{
			    					String firstNum, secondNum, thirdNum;

			    					if(cellValue.substring(0, 2).equals("02"))
			    					{
			    						firstNum = cellValue.substring(0, 2);
			    						secondNum = cellValue.substring(2, cellValue.length()-4);
			    						thirdNum = cellValue.substring(cellValue.length()-4, cellValue.length());
			    					}
			    					else
			    					{
			    						firstNum = cellValue.substring(0, 3);
			    						secondNum = cellValue.substring(3, cellValue.length()-4);
			    						thirdNum = cellValue.substring(cellValue.length()-4, cellValue.length());
			    					}

			    					cellValue = firstNum + "-" + secondNum + "-" + thirdNum;
			    				}

			    				dataRow.put("phonenumber", cellValue.replaceAll("-", ""));
			    			}
			    		}
			    		else if(i == 2)
			    		{
			    			dataRow.put("department", cellValue);
			    		}
			    		else if(i == 3)
			    		{
			    			dataRow.put("address", cellValue);
			    		}
			    	}
		    		datas.add(dataRow);
		    	}
	        }

		    if(strErrorMsg != null)
		    {
		    	datas = new ArrayList<MyCamelMap>();
		    	MyCamelMap dataRow = new MyCamelMap();
		    	dataRow.put("RESULT", strErrorMsg);
		    	datas.add(dataRow);
		    }
		    
		}
		catch (EncryptedDocumentException | InvalidFormatException | IOException e)
		{
			FrameworkUtils.exceptionToString(e);
		}
		return datas;
	}

}
