package fr.guigur.src;

import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class MyFriendsBecomePlayer extends JavaPlugin {

	Logger log;
	String message;
	public String Reponse = "testReponse";
	
	public void onEnable()
		{	
			log = this.getLogger();
			log.info("START");
			if(this.getConfig() == null)
				{
					this.saveDefaultConfig();
				}
			}

	public void onDisable()
		{
			log = this.getLogger();
			log.info("STOP");
		}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		int question;
		int erreurs;
		int	i;
		double X;
		double Y;
		double Z;
		Object String_nombre_questions;
		//String message;
		Player player = (Player) sender;
		
		question = 1;
		erreurs = 10000;
		i = 0;
		
		if(cmd.getName().equalsIgnoreCase("quiz_define"))
				{
				if (player.isOp())
					{
						Location setlocation = player.getLocation();
						System.out.println("[MyFriendsBecomePlayer] Interrogatoire sauve @ X = " + setlocation.getX() + " | Y = " + setlocation.getY() + " | Z = " + setlocation.getZ());
						this.Config.set("MyFriendsBecomePlayer.Position_pour_tp.X", setlocation.getX());
						this.getConfig().set("MyFriendsBecomePlayer.Position_pour_tp.Y", setlocation.getY());
						this.getConfig().set("MyFriendsBecomePlayer.Position_pour_tp.Z", setlocation.getZ());
						this.saveConfig();
						player.sendMessage(ChatColor.GREEN + "Nouveau point d'interrogatoire définit");
					
					}
				else
					player.sendMessage(ChatColor.RED + "Je suis désolé, mais vous n'avez pas la permission >:("); 
					
				}
		if(cmd.getName().equalsIgnoreCase("quiz"))
				{
					//get the current location
					Location base = player.getLocation();
					//take the saved location in config file
					X = Double.parseDouble(this.getConfig().getString("MyFriendsBecomePlayer.Position_pour_tp.X"));
					Y = Double.parseDouble(this.getConfig().getString("MyFriendsBecomePlayer.Position_pour_tp.Y"));
					Z = Double.parseDouble(this.getConfig().getString("MyFriendsBecomePlayer.Position_pour_tp.Z"));
					//Teleport player to the location in config file
					player.teleport(new Location(Bukkit.getWorld("world"), X, Y, Z));
					
					//do init
					player.sendMessage(this.getConfig().getString("MyFriendsBecomePlayer.Phrase_intro1"));
					player.sendMessage(this.getConfig().getString("MyFriendsBecomePlayer.Phrase_intro2") + "\n");
					String_nombre_questions = this.getConfig().get("MyFriendsBecomePlayer.Nombre_de_questions");
					i = (Integer) String_nombre_questions;
					System.out.println("i = " + i + "String = "+ String_nombre_questions);
					
				while (question != i + 1 && player.isOnline() == true)
				{
						player.sendMessage(this.getConfig().getString("MyFriendsBecomePlayer.Quiz.Q"+ question +".titre"));
						Reponse = this.getConfig().getString("MyFriendsBecomePlayer.Quiz.Q"+ question +".c");
						player.sendMessage("[1] " + this.getConfig().getString("MyFriendsBecomePlayer.Quiz.Q"+ question +".r1"));
						player.sendMessage("[2] " + this.getConfig().getString("MyFriendsBecomePlayer.Quiz.Q"+ question +".r2"));
						
					/*	if(cmd.getName().equalsIgnoreCase("1"))
						{
							player.sendMessage("1");
							question++;
						}
						else if(cmd.getName().equalsIgnoreCase("2"))
						{
							player.sendMessage("2");
							question++;
						}
						question++;
						*/
				}
				player.teleport(base);
				if (erreurs == 0)
					{
						Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), this.getConfig().getString("MyFriendsBecomePlayer.Succes"));
						System.out.println("[MyFriendsBecomePlayer] " + player.getName() + " a reussi le test");
					}
				else
					{
						Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), this.getConfig().getString("MyFriendsBecomePlayer.Fail"));
						System.out.println("[MyFriendsBecomePlayer] " + player.getName() + " a fail le test");
					}
					return true;
				}
		return false; 
	}
	
}
