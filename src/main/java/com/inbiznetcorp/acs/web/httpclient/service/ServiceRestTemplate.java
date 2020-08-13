package com.inbiznetcorp.acs.web.httpclient.service;

import org.springframework.stereotype.Service;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.Map;
import org.json.simple.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.inbiznetcorp.acs.framework.utils.FrameworkUtils;

@Service("com.inbiznetcorp.acs.web.httpclient.service.ServiceRestTemplate")
public class ServiceRestTemplate
{
	public JSONObject postForObject(String url, Map<String, String> paramMap)
    {
        RestTemplate restTemplate = new RestTemplate();


        LOGGER.info("Url:"+url);
        LOGGER.info("ParamMap"+paramMap);

        JSONObject resultJSONObject = restTemplate.postForObject(url, paramMap, JSONObject.class);

        return resultJSONObject;
    }

    public String postForEntityMultiValueMap(String url, Map<String, String> paramMap) throws RestClientException, MalformedURLException
    {
        RestTemplate restTemplate = new RestTemplate();
        String      resultObject = null;

         MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
         for( String key : paramMap.keySet() )
         {
             parameters.add(key, (String) paramMap.get(key));
         }

        HttpHeaders headers = new HttpHeaders();

        Charset utf8 = Charset.forName("UTF-8");
        MediaType mediaType = new MediaType("application", "x-www-form-urlencoded", utf8);
        headers.setContentType(mediaType);

        headers.add("User-Agent", "mozilla");
        headers.add("Accept-Language", "ko");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(parameters, headers);

        try
        {
            resultObject  = restTemplate.postForObject(new URI(url), request, String.class);
        }
        catch (URISyntaxException e)
        {
        	FrameworkUtils.exceptionToString(e);
        }

        return resultObject;
    }

    public ResponseEntity<String> postForBody(String url, String str)
    {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> resultJSONObject = restTemplate.postForEntity(url, str, String.class);
        return resultJSONObject;
    }


    public static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(ServiceRestTemplate.class);
}
