package com.ulticraft.aklizreboot;

import com.goebl.david.Webb;

public class AklizController
{
	private String auth;
	private String server;
	private AklizReboot pl;
	
	public AklizController(AklizReboot plugin, String username, String password, String serverId)
	{
		this.server = Data.AKLIZ_ADDRESS + serverId + "/";
		this.auth = Base64.encodeHTTPBasicAuthentication(username, password);
	}
	
	public void restart()
	{
		pl.log("Restarting");
		callCommandOverHTTP(Data.AKLIZ_COMMAND_REBOOT);
	}
	
	public void reloadAuth()
	{
		server = Data.AKLIZ_ADDRESS + pl.getConfiguration().getDataConnectionId() + "/";
		//String auth = Base64.encodeHTTPBasicAuthentication(Base64.decode(pl.getConfiguration().getDataConnectionUsername()).toString(), pl.getConfiguration().getDataConnectionPassword()).toString()));
	}
	
	private void callCommandOverHTTP(String command)
	{		
		Webb webb = Webb.create();
		webb.setDefaultHeader(Webb.HDR_AUTHORIZATION, auth);
		pl.dbg("Calling HTTP AUTH POST to " + server + "with command " + command);
		webb.post(server).param("restart", null).ensureSuccess().asVoid();
	}
}
