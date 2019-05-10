package xyz.mukri.simplehelp.files;

import com.google.common.collect.Lists;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import xyz.mukri.simplehelp.Core;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class CustomHelpFile {

    File file;
    FileConfiguration config;

    public CustomHelpFile() {
        file = new File(Core.getInstance().getDataFolder(), "custom-help.yml");

        if (!isExists()) {

            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }

            Core.getInstance().saveResource("custom-help.yml", false);
        }

        config = YamlConfiguration.loadConfiguration(file);
    }

    public boolean isExists() {
        return file.exists();
    }

    public void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // GETTER
    public boolean isArgumentsExists(String args) {
        return config.getConfigurationSection("custom-help." + args) == null ? false : true;
    }

    public String getPermissions(String args) {
        return config.getString("custom-help." + args + ".permission");
    }

    public List<String> getCustomHelp(String args) {
        return  config.getStringList("custom-help." + args + ".message");
    }

    public List<String> getDefaultCustomHelp() {
        return config.getStringList("default-help");
    }

    public String noPermissions() {
        return config.getString("no-permissions").replaceAll("&", "§");
    }

    // FUNCTIONS
    public String replaceAllReg(Player p, String msg) {
        return msg.replaceAll("&", "§")
                .replaceAll("%player%", p.getName());

    }

    public void createNewCommands(String arguments) {
        config.set("custom-help." + arguments + ".permission", "custom.permissions");
        config.set("custom-help." + arguments + ".message", Arrays.asList("&7This is a line", "&7This is another line!", " ", "&a%name% &7hello there!"));
    }

    public void removeCommands(String arguments) {
        config.set("custom-help." + arguments, null);
    }
}
