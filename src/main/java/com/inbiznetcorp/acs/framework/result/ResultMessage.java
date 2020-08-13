package com.inbiznetcorp.acs.framework.result;

public class ResultMessage {

	public ResultMessage(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public ResultMessage(String code, String message, Object result) {
		this.code = code;
		this.message = message;
		this.result = result;
	}

	public ResultMessage(String code, Object result) {
		this.code = code;
		this.result = result;
	}

	private String code;
	private String message;
	private String referenceUrl = "";
	private Object result;

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getReferenceUrl() {
		return referenceUrl;
	}
	public void setReferenceUrl(String referenceUrl) {
		this.referenceUrl = referenceUrl;
	}
	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "ResultMessage [code=" + code + ", message=" + message + ", referenceUrl=" + referenceUrl + ", result="
				+ result + "]";
	}
}
