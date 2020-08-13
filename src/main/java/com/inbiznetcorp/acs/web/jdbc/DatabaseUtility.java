package com.inbiznetcorp.acs.web.jdbc;

import java.sql.Statement;

public class DatabaseUtility
{
    public static int getBatchCnt(int[] updates)
    {
        int tmpCnt = 0;
        for (int i = 0; i < updates.length; i++)
        {
            if (updates[i] == Statement.EXECUTE_FAILED)
            {
                tmpCnt = tmpCnt + 1;
            }
        }
        return tmpCnt;
    }
}
