package com.ulticraft.aklizreboot;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class CommandHandler
{
	public static boolean command(AklizReboot pl, CommandSender sender, Command cmd, String label, String[] args)
	{
		if(cmd.getName().equalsIgnoreCase(Data.CMD_AKLIZ))
		{
			if(!sender.hasPermission(Data.PERM_USE))
			{
				sender.sendMessage(Data.TAG_AKLIZ + ChatColor.DARK_RED + "Sorry, you do not have permission use this.");
				return true;
			}
			
			if(args.length > 0)
			{
				if(args[0].equalsIgnoreCase("setup"))
				{
					if(args.length < 3)
					{
						sendError(sender, "To Authenticate, use " + ChatColor.YELLOW + "/akliz setup <id> <username> <password>");
						return true;
					}
					
					else
					{
						String username = args[2];
						String password = "";
						String id = args[1];
						
						for(int i = 3; i<args.length-1; i++)
						{
							password = password + " " + args[i];
						}
						
						pl.getConfiguration().setAuthentication(id, Base64.encodeBytes(username.getBytes()), Base64.encodeBytes(password.substring(1).getBytes()));
						sendInfo(sender, "Set Authentication Credentials!");
					}
				}
				
				else if(args[0].equalsIgnoreCase("reboot"))
				{
					if(pl.getConfiguration().isSetup())
					{
						sendInfo(sender, "Attempting to reboot server.");
						pl.getAklizController().restart();
					}
					
					else
					{
						sendError(sender, "Please use /akliz setup <id> <username> <password>");
					}
				}
			}
			
			else
			{
				showGlobalHelp(sender);
			}
		}
		
		return false;
	}
	
	public static void showGlobalHelp(CommandSender sender)
	{
		sender.sendMessage(Data.TAG_AKLIZ + ChatColor.YELLOW + "AklizReboot v1.0");
		sender.sendMessage(Data.TAG_AKLIZ + "/akliz reboot " + ChatColor.GREEN + "- Reboot server");
		sender.sendMessage(Data.TAG_AKLIZ + "/akliz setup <id> <username> <password>" + "- Authenticate to the control panel.");
	}
	
	public static void sendError(CommandSender sender, String msg)
	{
		sender.sendMessage(Data.TAG_AKLIZ + ChatColor.DARK_RED + msg);
	}
	
	public static void sendInfo(CommandSender sender, String msg)
	{
		sender.sendMessage(Data.TAG_AKLIZ + msg);
	}
}
