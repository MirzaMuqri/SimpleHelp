package xyz.mukri.simplehelp;

import org.bukkit.plugin.java.JavaPlugin;
import xyz.mukri.simplehelp.commands.HelpCmd;
import xyz.mukri.simplehelp.commands.SimpleHelpCmd;
import xyz.mukri.simplehelp.files.CustomHelpFile;

public class Core extends JavaPlugin {

    public static Core instance;

    public CustomHelpFile customHelpFile;

    @Override
    public void onEnable() {
        instance = this;

        customHelpFile =  new CustomHelpFile();

        registerCommands();

        getLogger().info("SimpleHelp is enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info("SimpleHelp is disabled.");
    }

    public void registerCommands() {
        getCommand("help").setExecutor(new HelpCmd(this));
        getCommand("simplehelp").setExecutor(new SimpleHelpCmd());
    }

    public static Core getInstance() {
        return instance;
    }
}
