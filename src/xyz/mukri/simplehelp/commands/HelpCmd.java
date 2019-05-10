package xyz.mukri.simplehelp.commands;

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
        return msg.replaceAll("&", "§").replaceAll("%name%", p.getName());
    }
}
