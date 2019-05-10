package xyz.mukri.simplehelp.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.mukri.simplehelp.Core;
import xyz.mukri.simplehelp.files.CustomHelpFile;

public class SimpleHelpCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("You are not allowed to use this commands.");
            return false;
        }

        Player p = (Player) sender;

        if (cmd.getName().equalsIgnoreCase("simplehelp")) {
            if (p.hasPermission("simplehelp.admin")) {
                if (args.length == 0) {
                    p.sendMessage("§a-------------------------");
                    p.sendMessage("§7/simplehelp reload - Reload custom-help.yml");
                    p.sendMessage("§7/simplehelp add <arguments> - Add new arguments to custom-help.yml");
                    p.sendMessage("§7/simplehelp remove <arguments> - Remove existing arguments on custom-help.yml");
                    p.sendMessage("§a-------------------------");
                }

                else if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("reload")) {

                        Core.getInstance().customHelpFile =  new CustomHelpFile();

                        p.sendMessage("§7custom-help.yml is reloaded.");
                    }
                }

                else if (args.length == 2) {
                    if (args[0].equalsIgnoreCase("add")) {
                        String arguments = args[1].toLowerCase();

                        if (Core.getInstance().customHelpFile.isArgumentsExists(arguments)) {
                            p.sendMessage("§7/help " + arguments + " already exists!");
                        }
                        else {
                            Core.getInstance().customHelpFile.createNewCommands(arguments);
                            Core.getInstance().customHelpFile.save();

                            p.sendMessage("§7Successfully created /help " + arguments + "! Please modify the custom-help.yml to your own needs.");
                        }
                    }

                    else if (args[0].equalsIgnoreCase("remove")) {
                        String arguments = args[1].toLowerCase();

                        if (Core.getInstance().customHelpFile.isArgumentsExists(arguments)) {
                            Core.getInstance().customHelpFile.removeCommands(arguments);
                            Core.getInstance().customHelpFile.save();

                            p.sendMessage("§Successfully removed /help " + arguments + " from the custom-yml config.");
                        }
                        else {
                            p.sendMessage("§7Could not found /help " + arguments + "! Make sure to check the config for the name. cAsE-SeNsItiVe ");
                        }
                    }
                }
                else {
                    p.performCommand("simplehelp");
                }
            }
        }

        return false;
    }
}
