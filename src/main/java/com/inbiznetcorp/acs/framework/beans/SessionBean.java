package com.inbiznetcorp.acs.framework.beans;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.inbiznetcorp.acs.framework.websocket.bean.BasicInfo;


@Component("sessionBean")
@Scope(value="session")
public class SessionBean
{
	BasicInfo basicInfo = (BasicInfo) FrameworkBeans.findBean("com.inbiznetcorp.acs.framework.websocket.bean.BasicInfo");
	
	private String 	userSeq		= "";
	private String 	userName	= "";
	private String 	grade		= "";
	private int 	cps 		= basicInfo.getSESSION_CPS();

	

	public String getUserSeq() {
		return userSeq;
	}

	public void setUserSeq(String userSeq) {
		this.userSeq = userSeq;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public int getCps() {
		return cps;
	}

	public void setCps(int cps) {
		this.cps = cps;
	}

	@Override
	public String toString() {
		return "SessionBean [userSeq=" + this.userSeq + ", userName=" + this.userName + ", grade=" + this.grade + ", cps=" + this.cps + "]";
	}
	
	

}
