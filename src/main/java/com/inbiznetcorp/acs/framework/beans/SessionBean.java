package com.inbiznetcorp.acs.framework.beans;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component("sessionBean")
@Scope(value="session")
public class SessionBean
{
	private String 	companyName	= "";
	private String 	grade		= "";
	private int 	cps 		= 160;	// INTERCEPTOR 확인용..

	public int getCps() {
		return cps;
	}

	public void setCps(int cps) {
		this.cps = cps;
	}

	public String getCompanyName() {
		return this.companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	public String getGrade() {
		return this.grade;
	}
	
	public void setGrade(String grade) {
		this.grade = grade;
	}

	@Override
	public String toString() {
		return "SessionBean [companyName=" + this.companyName + ", grade=" + this.grade + ", cps=" + this.cps + "]";
	}
	
	

}
