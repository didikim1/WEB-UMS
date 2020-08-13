package com.inbiznetcorp.acs.web.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.inbiznetcorp.acs.framework.utils.FrameworkUtils;
import com.inbiznetcorp.acs.framework.websocket.IVRSender;


public class RequestProc
{
	public static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(IVRSender.class);

    public JSONObject sendPacket(String url, Map<String, String> paramMap)
    {
        List<NameValuePair> paramList           = this.map2NameValuePairList(paramMap);
        String              strJSONObject       = this.excute(url, paramList);
        JSONObject          jsonObject          = this.str2JSONObject(strJSONObject);

        return jsonObject;
    }

    @SuppressWarnings("deprecation")
    private String excute(String url, List<NameValuePair> paramList)
    {
        HttpClient client = new DefaultHttpClient();
        StringBuffer returnData = new StringBuffer();
        BufferedReader rd = null;
        int soketTimeout = Integer.valueOf(1800);
        try
        {
            client.getParams().setParameter("http.socket.timeout", new Integer(soketTimeout * 1000));
            client.getParams().setParameter("http.protocol.content-charset", "UTF-8");

            HttpPost post = new HttpPost(url);

            post.setEntity(new UrlEncodedFormEntity(paramList, "UTF-8"));

            HttpResponse response = client.execute(post);

            rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
            String line = "";
            while ((line = rd.readLine()) != null)
            {
                returnData.append(line);
            }
            rd.close();
        }
        catch (Exception e)
        {
//        	FrameworkUtils.exceptionToString(e);
        	
        	LOGGER.info("===== ClientException =====");
        	LOGGER.info(FrameworkUtils.exceptionToString(e));
        	
            return null;
        }
        finally
        {
            if (rd != null)
                try
                {
                    rd.close();
                }
                catch (IOException e)
                {
                	FrameworkUtils.exceptionToString(e);
                }
            client.getConnectionManager().shutdown();
        }
        return returnData.toString();
    }

    private List<NameValuePair> map2NameValuePairList(Map<String, String> paramMap)
    {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        Iterator<String> keys = paramMap.keySet().iterator();
        while (keys.hasNext())
        {
            String key = keys.next();
            nameValuePairs.add(new BasicNameValuePair(key, paramMap.get(key)));
        }
        return nameValuePairs;
    }

    private JSONObject str2JSONObject(String strJSONObject)
    {
        JSONObject json = null;

        try
        {
            JSONParser parser = new JSONParser();
            json = (JSONObject) parser.parse(strJSONObject);
        }
        catch (ParseException e)
        {
        	FrameworkUtils.exceptionToString(e);
        }
        return json;
    }
}
