package com.inbiznetcorp.acs.web.servlet;

public class Utility
{
	public static String getStrRandom( int iSize )
	{
		StringBuffer sb = new StringBuffer();
        for (int i = 0; i < iSize; i++)
        {
            sb.append( String.valueOf((char)((int)(Math.random()*26)+65)) );
        }

        return sb.toString();
	}

}


