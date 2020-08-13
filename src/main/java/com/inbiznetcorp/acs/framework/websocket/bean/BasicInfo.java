package com.inbiznetcorp.acs.framework.websocket.bean;

import org.springframework.stereotype.Service;

@Service("com.inbiznetcorp.acs.framework.websocket.bean.BasicInfo")
public class BasicInfo
{
	private boolean normalCallExist 		= false;
	private boolean normalCallThreadFull 	= false;
	private boolean importantCallExist 		= false;
	private boolean importantCallThreadFull = false;
	
//	private boolean runNormal 				= false;
//	private boolean runImportant 			= false;
	
	private int 	SESSION_CPS 			= 160;
	private int 	SESSION_NORMAL_CPS 		= 0;
	private int 	SESSION_IMPORTANT_CPS 	= 0;
	
	
	
	public boolean isNormalCallExist() {
		return normalCallExist;
	}
	public void setNormalCallExist(boolean normalCallExist) {
		this.normalCallExist = normalCallExist;
	}
	public boolean isNormalCallThreadFull() {
		return normalCallThreadFull;
	}
	public void setNormalCallThreadFull(boolean normalCallThreadFull) {
		this.normalCallThreadFull = normalCallThreadFull;
	}
	public boolean isImportantCallExist() {
		return importantCallExist;
	}
	public void setImportantCallExist(boolean importantCallExist) {
		this.importantCallExist = importantCallExist;
	}
	public boolean isImportantCallThreadFull() {
		return importantCallThreadFull;
	}
	public void setImportantCallThreadFull(boolean importantCallThreadFull) {
		this.importantCallThreadFull = importantCallThreadFull;
	}
	
	
//	public boolean isRunNormal() {
//		return runNormal;
//	}
//	public void setRunNormal(boolean runNormal) {
//		this.runNormal = runNormal;
//	}
//	public boolean isRunImportant() {
//		return runImportant;
//	}
//	public void setRunImportant(boolean runImportant) {
//		this.runImportant = runImportant;
//	}
	
	
	public int getSESSION_CPS() {
		return SESSION_CPS;
	}
	public void setSESSION_CPS(int sESSION_CPS) {
		SESSION_CPS = sESSION_CPS;
	}
	public int getSESSION_NORMAL_CPS() {
		SESSION_NORMAL_CPS = (int) (this.SESSION_CPS*(0.7));
		return SESSION_NORMAL_CPS;
	}
	public void setSESSION_NORMAL_CPS(int sESSION_NORMAL_CPS) {
		SESSION_NORMAL_CPS = sESSION_NORMAL_CPS;
	}
	public int getSESSION_IMPORTANT_CPS() {
		SESSION_IMPORTANT_CPS = (int) (this.SESSION_CPS*(0.3));
		return SESSION_IMPORTANT_CPS;
	}
	public void setSESSION_IMPORTANT_CPS(int sESSION_IMPORTANT_CPS) {
		SESSION_IMPORTANT_CPS = sESSION_IMPORTANT_CPS;
	}
	
}
