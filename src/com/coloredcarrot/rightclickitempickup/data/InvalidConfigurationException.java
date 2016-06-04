package com.coloredcarrot.rightclickitempickup.data;

import java.io.File;

public class InvalidConfigurationException
extends Exception
{

	private static final long serialVersionUID = 2353946455784008538L;
	
	private File file;
	
	protected InvalidConfigurationException(File file, String errMsg)
	{
		super(errMsg);
		this.file = file;
	}
	
	public File getFile()
	{
		return file;
	}
	
}
