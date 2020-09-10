package com.inbiznetcorp.acs.web.jdbc;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.inbiznetcorp.acs.framework.beans.FrameworkBeans;
import com.inbiznetcorp.acs.framework.common.ServiceCommon;
import com.inbiznetcorp.acs.framework.mymap.MyCamelMap;
import com.inbiznetcorp.acs.framework.mymap.MyMap;
import com.inbiznetcorp.acs.websocket.SocketTextHandler;

public class DatabaseAuthInfoDuty
{
	public static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(DatabaseAuthInfoDuty.class);
	
    public DatabaseAuthInfoDuty(List<MyCamelMap>  parseResult, int seqgroup, String userSeq, String grade)
    {
    	ServiceCommon serviceCommon = (ServiceCommon) FrameworkBeans.findBean("com.inbiznetcorp.acs.framework.common.ServiceCommon");
    	
    	if(serviceCommon.getProfilesActiveName().equals("prd"))
    	{
    		mDBURL 	= "jdbc:mysql://211.61.220.21:3306/ACS?autoReconnect=true&zeroDateTimeBehavior=convertToNull&characterEncoding=utf8";
    		mDBUser = "acs";
    		mDBPass = "Inbiz90201!@";
    		mDBServer = "21";
    	}
    	else if(serviceCommon.getProfilesActiveName().equals("prd2"))
    	{
    		mDBURL 	= "jdbc:mysql://211.61.220.22:3306/ACS?autoReconnect=true&zeroDateTimeBehavior=convertToNull&characterEncoding=utf8";
    		mDBUser = "acs";
    		mDBPass = "Inbiz90201!@";
    		mDBServer = "22";
    	}
    	else if(serviceCommon.getProfilesActiveName().equals("test"))
    	{
    		mDBURL 	= "jdbc:mysql://dev01.ring2pay.com:4418/dreamline_vms?autoReconnect=true&zeroDateTimeBehavior=convertToNull&characterEncoding=utf8";
    		mDBUser = "inbiznet";
    		mDBPass = "inbiz9020";
    		mDBServer = "dev";
    	}
    	else
    	{
    		mDBURL 	= "jdbc:mysql://dev01.ring2pay.com:4418/dreamline_vms_dev?autoReconnect=true&zeroDateTimeBehavior=convertToNull&characterEncoding=utf8";
    		mDBUser = "inbiznet";
    		mDBPass = "inbiz9020";
    		mDBServer = "dev";
    	}
    	
        mParseResult 	= parseResult;
        mSeqgroup 		= seqgroup;
        mUserSeq 		= userSeq;
        mGrade 			= grade;
        
    }
    

    public int excute()
    {
        Connection        conn            = null;
        PreparedStatement ps              = null;
        int               failUpdateCount = 0;
        
        LOGGER.info("seqgroup : " + mSeqgroup);
        LOGGER.info("mDBServer : " + mDBServer);

        try
        {
            Class.forName(mDBDriver);
            conn = DriverManager.getConnection(mDBURL, mDBUser, mDBPass);
            ps   = conn.prepareStatement(mQuery);
            
            for (int i = 0; i < mParseResult.size(); i++)
            {
                MyMap bean = mParseResult.get(i);

                ps.setString(1, bean.getStr("name", null));
                ps.setString(2, bean.getStr("phonenumber", null));
                ps.setInt(3, 	mSeqgroup);
                ps.setString(4, mUserSeq);
                ps.setString(5, mGrade);
                
                ps.addBatch();
                ps.clearParameters();

                if (i % 1000 == 0)
                {
                    failUpdateCount += DatabaseUtility.getBatchCnt(ps.executeBatch());

                    LOGGER.info("   Execute Batch(i % 1000 == 0) OK!");
                }
            }
            failUpdateCount += DatabaseUtility.getBatchCnt(ps.executeBatch());
            ps.close();
            conn.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            try { conn.rollback(); } catch (SQLException e1) { e1.printStackTrace(); }
        }
        finally
        {
            if (ps != null) try { ps.close(); ps = null; }  catch (SQLException ex)  { }
            if (conn != null) try{conn.close();conn = null;}catch (SQLException ex)  { }
        }
        LOGGER.info("             Ok!!");
        
        SocketTextHandler socketTextHandler = (SocketTextHandler) FrameworkBeans.findBean("com.inbiznetcorp.acs.websocket.SocketTextHandler");
        HashMap<String, WebSocketSession> sessions = socketTextHandler.getSessions();
        
        for (String key : sessions.keySet()) {
			WebSocketSession ss = sessions.get(key);
			try
			{
				ss.sendMessage(new TextMessage("hello"));
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
        
        return failUpdateCount;
    }
    
    private List<MyCamelMap> 	mParseResult 	= null;
    private int 				mSeqgroup 		= 0;
    private String 				mUserSeq 		= null;
    private String 				mGrade 			= null;

    private String              mDBDriver		= "com.mysql.jdbc.Driver";
    private String              mDBURL 			= null;
    private String              mDBUser         = null;
    private String              mDBPass         = null;
    
    private String              mDBServer       = null;
    
    private String mQuery = " INSERT INTO TEMP_TARGET"
            + "("
            + " NAME"
            + ",PHONENUMBER"
            + ",SEQGROUP"
            + ",CREATEDATE"
            + ",COMPANYSEQ"
            + ",COMPANYGRADE"
            + ")"
            + "VALUES"
            + "("
            + " ?"
            + ",?"
            + ",?"
            + ",?"
            + ",?"
            + ",now()"
            + ")";
}
