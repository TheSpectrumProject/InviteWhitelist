package org.spectrum.invitewl;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class InviteWL extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getLogger().info("InviteWL enabled. Author: Tim");
        getServer().getPluginManager().registerEvents(this, this);
        saveDefaultConfig();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        FileConfiguration config = getConfig();
        if (command.getName().equalsIgnoreCase("invite")) {
            if (args.length != 1) {
                sender.sendMessage("Usage: /invite <name>");
                return false;
            }

            String invitee = args[0];
            if (config.contains("invites." + invitee + ".invitedBy")) {
                sender.sendMessage("Error: " + invitee + " has already been invited.");
                return true;
            }

            String inviter = sender instanceof Player ? sender.getName() : "CONSOLE";
            String inviteTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            config.set("invites." + invitee + ".invitedBy.inviter", inviter);
            config.set("invites." + invitee + ".invitedBy.time", inviteTime);
            if (sender instanceof Player) {
                config.set("invites." + inviter + ".invitedPlayers." + invitee, inviteTime);
            }
            saveConfig();

            sender.sendMessage("Player " + invitee + " has been invited by " + inviter);
            return true;
        } else if (command.getName().equalsIgnoreCase("inviteinspect")) {
            if (!(sender instanceof Player) || sender.isOp()) {
                if (args.length != 1) {
                    sender.sendMessage("Usage: /inviteinspect <name>");
                    return false;
                }

                String playerName = args[0];
                if (!config.contains("invites." + playerName + ".invitedBy")) {
                    sender.sendMessage(playerName + " has not been invited.");
                    return true;
                }

                String inviter = config.getString("invites." + playerName + ".invitedBy.inviter");
                String time = config.getString("invites." + playerName + ".invitedBy.time");
                sender.sendMessage(playerName + " was invited by " + inviter + " at " + time);

                if (config.contains("invites." + playerName + ".invitedPlayers")) {
                    Map<String, Object> invitedPlayers = config.getConfigurationSection("invites." + playerName + ".invitedPlayers").getValues(false);
                    if (!invitedPlayers.isEmpty()) {
                        sender.sendMessage(playerName + " has invited:");
                        invitedPlayers.forEach((invitee, inviteTime) -> sender.sendMessage("  " + invitee + " at " + inviteTime));
                    } else {
                        sender.sendMessage(playerName + " has not invited anyone.");
                    }
                }
                return true;
            } else {
                sender.sendMessage("You do not have permission to use this command.");
                return false;
            }
        }
        return false;
    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        String playerName = event.getPlayer().getName();
        FileConfiguration config = getConfig();
        if (!config.contains("invites." + playerName + ".invitedBy")) {
            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, "You are not invited to this server.");
        }
    }
}
