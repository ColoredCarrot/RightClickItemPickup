package com.coloredcarrot.rightclickitempickup.nms;

import com.coloredcarrot.rightclickitempickup.RCIP;

public class NMS
{

	private static NMSHook hook;
	private static String version;
	private static boolean compatible = false;
	
	public static NMSSetupResponse setup()
	{
		
		String version;
		
		try { version = RCIP.getPlugin().getServer().getClass().getPackage().getName().replace('.', ',').split(",")[3]; }
		catch (ArrayIndexOutOfBoundsException e) { return new NMSSetupResponse(null, false); }
		
		if (version.equals("v1_9_R2"))
			hook = new NMSHook_v1_9_R2();
		else if (version.equals("v1_9_R1"))
			hook = new NMSHook_v1_9_R1();
		else if (version.equals("v1_8_R3"))
			hook = new NMSHook_v1_8_R3();
		else if (version.equals("v1_8_R2"))
			hook = new NMSHook_v1_8_R2();
		else if (version.equals("v1_8_R1"))
			hook = new NMSHook_v1_8_R1();
		
		compatible = hook != null;
		
		NMS.version = version;
		
		return new NMSSetupResponse(version, compatible);
		
	}
	
	public static NMSHook getHook()
			throws NMSNotHookedException
	{
		
		if (!compatible)
			throw new NMSNotHookedException();
		
		return hook;
		
	}
	
	public static String getVersion()
	{
		return version;
	}
	
	public static boolean foundCompatibleVersion()
	{
		return compatible;
	}
	
}
