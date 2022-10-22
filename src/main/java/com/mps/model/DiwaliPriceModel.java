package com.mps.model;

public class DiwaliPriceModel {

	private String modelName;
	private String mrp;
	private String dp;
	private String srp;
	
	public DiwaliPriceModel() {
		super();
	}
	public DiwaliPriceModel(String modelName, String mrp, String dp, String srp) {
		super();
		this.modelName = modelName;
		this.mrp = mrp;
		this.dp = dp;
		this.srp = srp;
	}
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	public String getMrp() {
		return mrp;
	}
	public void setMrp(String mrp) {
		this.mrp = mrp;
	}
	public String getDp() {
		return dp;
	}
	public void setDp(String dp) {
		this.dp = dp;
	}
	public String getSrp() {
		return srp;
	}
	public void setSrp(String srp) {
		this.srp = srp;
	}
	@Override
	public String toString() {
		return "DiwaliPriceModel [modelName=" + modelName + ", mrp=" + mrp + ", dp=" + dp + ", srp=" + srp + "]";
	}
	
}
