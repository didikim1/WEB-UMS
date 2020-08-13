package com.inbiznetcorp.acs.framework.mymap;
import java.util.LinkedHashMap;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
public class MyMap extends LinkedHashMap<Object,Object>
{
	private static final long serialVersionUID = -7964609600835164119L;

	public Object put(Object key,Object value)
	{
		return super.put(key,value);
	}

	/**
	 * <pre>
	 * @Method Name  : getStr
	 * @작성일    : 2016. 6. 12.
	 * @작성자    : hsjeon1224(효성)
	 * @변경이력  :
	 * @Method 설명 : 해당 키의 String을 반환한다.
	 * @param key  값의 키
	 * @return
	 * </pre>
	 */
	public String getStr(Object key)
	{
		Object oValue = super.get(key);

		return String.valueOf(oValue);
	}

	/**
	 * <pre>
	 * @Method Name  : getStr
	 * @작성일    : 2016. 6. 12.
	 * @작성자    : hsjeon1224(효성)
	 * @변경이력  :
	 * @Method 설명 : 해당 키의 String을 반환한다. 만약 값이 없으면 Default값을 반환한다.
	 * @param key 값의 키
	 * @param def 값이 없을 대체할 값
	 * @return
	 * </pre>
	 */
	public String getStr(Object key,String def)
	{
		Object oValue = super.get(key);
		String convStr = (oValue==null)?"":String.valueOf(oValue);

		return (convStr.equals("")) ? def:convStr.trim();
	}


	/**
	 * <pre>
	 * @Method Name  : getInt
	 * @작성일    : 2016. 6. 12.
	 * @작성자    : hsjeon1224(효성)
	 * @변경이력  :
	 * @Method 설명 : 해당 키의 Int 을 반환한다.
	 * @param key 값의 키
	 * @return
	 * </pre>
	 */
	public int getInt(Object key)
	{
		Object oValue = super.get(key);
		String convStr = (oValue==null)?"":String.valueOf(oValue);

		return Integer.valueOf(convStr);
	}


	/**
	 * <pre>
	 * @Method Name  : getInt
	 * @작성일    : 2016. 6. 12.
	 * @작성자    : hsjeon1224(효성)
	 * @변경이력  :
	 * @Method 설명 : 해당 키의 Int 을 반환한다. 만약 값이 없으면 Default값을 반환한다.
	 * @param key 값의 키
	 * @param def 값이 없을 대체할 값
	 * @return
	 * </pre>
	 */
	public int getInt(Object key,int def)
	{
		Object oValue = super.get(key);
		String convStr = (oValue==null)?"":String.valueOf(oValue);

		return (convStr.equals("")) ? def:Integer.valueOf(convStr);

	}

	/**
	 * <pre>
	 * @Method Name  : getLong
	 * @작성일    : 2016. 6. 12.
	 * @작성자    : hsjeon1224(효성)
	 * @변경이력  :
	 * @Method 설명 : 해당 키의 long 을 반환한다.
	 * @param key
	 * @return
	 * </pre>
	 */
	public long getLong(Object key)
	{
		Object oValue = super.get(key);
		String convStr = (oValue==null)?"":String.valueOf(oValue);

		return Long.valueOf(convStr);
	}

	/**
	 * <pre>
	 * @Method Name  : getLong
	 * @작성일    : 2016. 6. 12.
	 * @작성자    : hsjeon1224(효성)
	 * @변경이력  :
	 * @Method 설명 : 해당 키의 long 을 반환한다. 만약 값이 없으면 Default값을 반환한다.
	 * @param key
	 * @param def
	 * @return
	 * </pre>
	 */
	public long getLong(Object key,long def)
	{
		Object oValue = super.get(key);
		String convStr = (oValue==null)?"":String.valueOf(oValue);

		return (convStr.equals("")) ? def:Long.valueOf(convStr);
	}

	/**
	 * <pre>
	 * @Method Name  : getListMyCamelMap
	 * @작성일    : 2016. 6. 12.
	 * @작성자    : hsjeon1224(효성)
	 * @변경이력  :
	 * @Method 설명 : 해당 객체를 키를 Camel 타입으로 목록을 반환한다.
	 * @param key
	 * @return
	 * </pre>
	 */
	public List<MyCamelMap> getListMyCamelMap(Object key)
	{
		return (List<MyCamelMap>)super.get(key);
	}

	/**
	 * <pre>
	 * @Method Name  : getListMyMap
	 * @작성일    : 2016. 6. 12.
	 * @작성자    : hsjeon1224(효성)
	 * @변경이력  :
	 * @Method 설명 : 해당 객체의 목록을 반환한다.
	 * @param key
	 * @return
	 * </pre>
	 */
	public List<MyMap> getListMyMap(Object key)
	{
		return (List<MyMap>)super.get(key);
	}

	/**
	 * @Method Name  : getMyMap
	 * @작성일    : 2016. 6. 12.
	 * @작성자    : hsjeon1224(효성)
	 * @변경이력  :
	 * @Method 설명 : 해당 객체를 반환한다.
	 * @param key
	 * @return
	 */
	public MyMap getMyMap(Object key)
	{
		return (MyMap)super.get(key);
	}

	/**
	 * @Method Name  : getJSONObject
	 * @작성일    : 2016. 6. 12.
	 * @작성자    : hsjeon1224(효성)
	 * @변경이력  :
	 * @Method 설명 : 해당 객체의 JSON Object를 반환한다.
	 * @param key
	 * @return
	 */
	public JSONObject getJSONObject(Object key)
	{
		return (JSONObject)super.get(key);
	}

	/**
	 * <pre>
	 * @Method Name  : getJSONArray
	 * @작성일    : 2016. 6. 12.
	 * @작성자    : hsjeon1224(효성)
	 * @변경이력  :
	 * @Method 설명 : 해당 객체의 JSON Array를 반환한다.
	 * @param key
	 * @return
	 * </pre>
	 */
	public JSONArray getJSONArray(Object key)
	{
		return (JSONArray)super.get(key);
	}

	public String[] getStrArray(Object key)
	{
		return objectToStringArray(super.get(key));
	}

	public int[] getIntArray(Object key)
	{
		return objectToIntArray(super.get(key));
	}

	public String[] objectToStringArray(Object _Array)
	{
		String[] arr=null;
		try{
			arr=(String[])_Array;
		}catch(Exception e){
			arr=new String[0];
		}
		return arr==null?new String[0]:arr;
	}

	public int[] objectToIntArray(Object _Array)
	{
		int[] arr=null;
		try{
			arr=(int[])_Array;
		}catch(Exception e){
			arr=new int[0];
		}
		return arr==null?new int[0]:arr;
	}
}