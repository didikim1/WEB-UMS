package com.inbiznetcorp.acs.web.excel.read;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.AbstractView;

import com.inbiznetcorp.acs.framework.beans.FrameworkBeans;
import com.inbiznetcorp.acs.framework.common.ServiceCommon;

import net.sf.jxls.transformer.Configuration;
import net.sf.jxls.transformer.XLSTransformer;
@Service("com.inbiznetcorp.acs.web.excel.read.ExcelView")
public class ExcelView extends AbstractView
{
//
//	@Value("${config.excel}")
//	private String excelPath;

	public ExcelView()
	{
		this.setContentType("application/vnd.ms-excel");
	}

	private static SimpleDateFormat sf = new SimpleDateFormat("yyyy_MM_dd");

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
			XLSTransformer xls = new XLSTransformer();
			Configuration cf = xls.getConfiguration();
			cf.setUTF16(true);	//
			xls.setConfiguration(cf);

			InputStream is = null;

			try {
				is = readTemplate((String)model.get("source") );
				Map<String, Object> beans = new HashMap<String, Object>();

				SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm");
				beans.put("date", df.format( new Date() ) );
				beans.put("now", new Date() );

				Set key = model.keySet();
				for (Iterator iterator = key.iterator(); iterator.hasNext();) {
				    String keyName = (String) iterator.next();
				    beans.put( keyName,  model.get(keyName) );

				    if( keyName.equals("list") ) {
				    	List list = (List)model.get(keyName);
				    	if( list.size() == 0 ) {
							String str = "<script>alert('목록이 존재하지 않습니다.'); history.back(-1);</script>";
							response.setContentType("text/html; charset=utf-8");
							response.getOutputStream().write(str.getBytes());
							return;
				    	}
				    }
				}
				String sSDate = (String)model.get("sSDate");
				String sEDate = (String)model.get("sEDate");

				if(sSDate != null)
				{
					writeWorkbook((String)model.get("target") + "_" + sSDate + "-" + sEDate, response, xls.transformXLS(is, beans) );
				}
				else
				{
					writeWorkbook((String)model.get("target") + "_" + sf.format(new Date() ), response, xls.transformXLS(is, beans) );
				}

			}finally {
				if( is != null ) {
					try {
						is.close();

					}catch (Exception e) {
						// TODO: handle exception
					}
				}
			}
	}

	@Autowired private Environment env;

	private InputStream readTemplate(String finalTemplate) throws FileNotFoundException {
		ServiceCommon 	serviceCommon = (ServiceCommon) FrameworkBeans.findBean("com.inbiznetcorp.acs.framework.common.ServiceCommon");

		String templateFilePath = null;

		if("local".equals(serviceCommon.getProfilesActiveName()))
		{
	    	String CONTEXT_PATH = getServletContext().getRealPath("");
			templateFilePath = CONTEXT_PATH + File.separator + "excel" + File.separator + finalTemplate;
		}
		else
		{
			String envFilePath = env.getProperty("common.file.excel");
	        templateFilePath = envFilePath + File.separator + finalTemplate;
		}

//        System.out.println("templateFilePath : "+templateFilePath);

        return new FileInputStream(templateFilePath);
    }

    private void writeWorkbook(
        String filename, HttpServletResponse response, org.apache.poi.ss.usermodel.Workbook workbook)
        throws IOException {
        response.setHeader("Content-disposition", "attachment;filename=" + encodeFileName(filename + ".xlsx"));
		response.setHeader("Content-Transfer-Encoding", "binary;");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Expires", "-1");
        workbook.write(response.getOutputStream());
    }

    private String encodeFileName(String filename) {
        try {
            return URLEncoder.encode(filename, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }



}
