package com.inbiznetcorp.acs.web.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.inbiznetcorp.acs.framework.utils.FrameworkUtils;

public class RequestProcSSLJSON
{

    public JSONObject sendPacket(String url, String strParamObject)
    {
        String              strJSONObject       = this.excute(url, strParamObject);
        JSONObject          jsonObject          = this.str2JSONObject(strJSONObject);

        return jsonObject;
    }

    @SuppressWarnings("deprecation")
    private String excute(String url, String strParamObject)
    {

//        SSLContext ctx = SSLContext.getInstance("TLS");
//        X509TrustManager tm = new X509TrustManager() {
//            public void checkClientTrusted(X509Certificate[] xcs, String string) throws CertificateException {
//            }
//
//            public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException {
//            }
//
//            public X509Certificate[] getAcceptedIssuers() {
//                return null;
//            }
//        };
//        ctx.init(null, new TrustManager[]{tm}, null);
//        SSLSocketFactory ssf = new SSLSocketFactory(ctx,SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
//        ClientConnectionManager ccm = client.getConnectionManager();
//        SchemeRegistry sr = ccm.getSchemeRegistry();


        HttpClient client = new DefaultHttpClient();
        StringBuffer returnData = new StringBuffer();
        BufferedReader rd = null;
        int soketTimeout = Integer.valueOf(180);
        try
        {
            client.getParams().setParameter("http.socket.timeout", new Integer(soketTimeout * 1000));
            client.getParams().setParameter("http.protocol.content-charset", "UTF-8");

            HttpPost post = new HttpPost(url);

            StringEntity entity = new StringEntity(strParamObject, "UTF-8");
            post.setEntity(entity);

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
        	FrameworkUtils.exceptionToString(e);

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
