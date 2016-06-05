package com.coloredcarrot.rightclickitempickup.nms;

public class NMSSetupResponse
{

	private String version;
	private boolean compatible;
	
	protected NMSSetupResponse(String version, boolean compatible)
	{
		this.version = version;
		this.compatible = compatible;
	}

	public String getVersion()
	{
		return version;
	}

	public boolean isCompatible()
	{
		return compatible;
	}
	
}
