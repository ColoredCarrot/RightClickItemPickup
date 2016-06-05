package com.coloredcarrot.rightclickitempickup.nms;

/**
 * Indicates that the NMS system is not compatible with this server version.
 * @see #NMSNotHookedException()
 * @author ColoredCarrot
 * @since 1.2.0
 */
public class NMSNotHookedException
extends Exception
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -785625851069808577L;

	/**
	 * Constructs a new {@link NMSNotHookedException}.
	 */
	public NMSNotHookedException()
	{
		super("NMS not hooked!");
	}
	
}
