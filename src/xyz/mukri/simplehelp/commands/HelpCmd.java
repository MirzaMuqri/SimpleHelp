package xyz.mukri.simplehelp.commands;

import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.mukri.simplehelp.Core;

import java.util.List;

public class HelpCmd implements CommandExecutor {

    public Core plugin;

    public HelpCmd(Core plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {

            sender.sendMessage("You must be a player to perform this commands!");

            return false;
        }

        Player p = (Player) sender;

        if (cmd.getName().equalsIgnoreCase("help")) {
            if (args.length == 1) {
                String arguments = args[0];

                if (plugin.customHelpFile.isArgumentsExists(arguments)) {

                    if (!p.hasPermission(plugin.customHelpFile.getPermissions(arguments))) {
                        p.sendMessage(plugin.customHelpFile.noPermissions());
                        return false;
                    }

                    List<String> msg = plugin.customHelpFile.getCustomHelp(arguments);

                    for (String msgs : msg) {
                        p.sendMessage(replaceAllMsg(p, msgs));
                    }
                }
                else {
                   p.performCommand("help");
                }

            }
            else {
                List<String> msg = plugin.customHelpFile.getDefaultCustomHelp();

                for (String msgs : msg) {
                    p.sendMessage(replaceAllMsg(p, msgs));
                }

            }
        }

        return false;
    }

    public String replaceAllMsg(Player p, String msg) {
        return msg.replaceAll("&", "§")
                .replaceAll("%name%", p.getName()) // Players Name
                .replaceAll("%displayname%", p.getDisplayName()) // Display Name
                .replaceAll("%level%", String.valueOf(p.getLevel())) // Players Level
                .replaceAll("%death%", String.valueOf(p.getStatistic(Statistic.DEATHS))) // Players Death
                .replaceAll("%kills%", String.valueOf(p.getStatistic(Statistic.PLAYER_KILLS))) // Players Kills
                .replaceAll("%damage_dealt%", String.valueOf(p.getStatistic(Statistic.DAMAGE_DEALT))) // Damage Dealt
                .replaceAll("%mobs_killed%", String.valueOf(p.getStatistic(Statistic.MOB_KILLS))) // Mobs Killed
                .replaceAll("%gamemode%", p.getGameMode().toString()) // Players Gamemode
                .replaceAll("%chest_open%", String.valueOf(p.getStatistic(Statistic.CHEST_OPENED))) // Chest Opend
                .replaceAll("%fish_caught%", String.valueOf(p.getStatistic(Statistic.FISH_CAUGHT))) // Fish Caught
                .replace("%item_enchanted%", String.valueOf(p.getStatistic(Statistic.ITEM_ENCHANTED))); // Item Enchanted
    }
}
