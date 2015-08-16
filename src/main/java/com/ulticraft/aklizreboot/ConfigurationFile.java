package com.ulticraft.aklizreboot;

import java.io.File;
import java.io.IOException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigurationFile
{
	private boolean dataPluginEnabled;
	private boolean dataPluginDebug;
	private String dataConnectionId;
	private String dataConnectionUsername;
	private String dataConnectionPassword;
	private File configurationFile;
	private FileConfiguration fc;
	
	public ConfigurationFile(AklizReboot plugin)
	{		
		configurationFile = new File(plugin.getDataFolder() + File.separator + "config.yml");
		
		if(!configurationFile.exists())
		{
			try
			{
				dataPluginEnabled = true;
				dataPluginDebug = false;
				dataConnectionId = Data.DEFAULT_ID;
				dataConnectionUsername = Base64.encodeBytes(Data.DEFAULT_USERNAME.getBytes());
				dataConnectionPassword = Base64.encodeBytes(Data.DEFAULT_PASSWORD.getBytes());
				
				configurationFile.mkdirs();
				configurationFile.createNewFile();
				fc = new YamlConfiguration();
				
				fc.set(Data.PLUGIN_ENABLE, dataPluginEnabled);
				fc.set(Data.PLUGIN_DEBUG, dataPluginDebug);
				fc.set(Data.CONNECTION_ID, dataConnectionId);
				fc.set(Data.CONNECTION_USERNAME, dataConnectionUsername);
				fc.set(Data.CONNECTION_PASSWORD, dataConnectionPassword);
				
				fc.save(configurationFile);
			}

			catch(IOException e)
			{
				e.printStackTrace();
			}
			
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
		else
		{
			FileConfiguration fc = new YamlConfiguration();
			
			dataPluginEnabled = fc.getBoolean(Data.PLUGIN_ENABLE);
			dataPluginDebug = fc.getBoolean(Data.PLUGIN_DEBUG);
			dataConnectionId = fc.getString(Data.CONNECTION_ID);
			dataConnectionUsername = fc.getString(Data.CONNECTION_USERNAME);
			dataConnectionPassword = fc.getString(Data.CONNECTION_PASSWORD);
		}
	}

	public boolean isDataPluginEnabled()
	{
		return dataPluginEnabled;
	}

	public boolean isDataPluginDebug()
	{
		return dataPluginDebug;
	}

	public String getDataConnectionId()
	{
		return dataConnectionId;
	}

	public String getDataConnectionUsername()
	{
		return dataConnectionUsername;
	}

	public String getDataConnectionPassword()
	{
		return dataConnectionPassword;
	}
	
	public void saveConfig()
	{
		try
		{
			fc.save(configurationFile);
		}
		
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void setAuthentication(String id, String user, String pass)
	{
		dataConnectionId = id;
		dataConnectionUsername = Base64.encodeBytes(user.getBytes());
		dataConnectionPassword = Base64.encodeBytes(pass.getBytes());
		fc.set(Data.CONNECTION_ID, dataConnectionId);
		fc.set(Data.CONNECTION_USERNAME, dataConnectionUsername);
		fc.set(Data.CONNECTION_PASSWORD, dataConnectionPassword);
		saveConfig();
	}
	
	public boolean isSetup()
	{
		if(dataConnectionId.equals(Data.DEFAULT_ID))
		{
			return false;
		}
		
		if(dataConnectionUsername.equals(Base64.encodeBytes(Data.DEFAULT_USERNAME.getBytes())))
		{
			return false;
		}
		
		if(dataConnectionPassword.equals(Base64.encodeBytes(Data.DEFAULT_PASSWORD.getBytes())))
		{
			return false;
		}
		
		return true;
	}
}
