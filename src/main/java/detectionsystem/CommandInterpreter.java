package detectionsystem;

import detectionsystem.commands.ListCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CommandInterpreter {

    private static CommandInterpreter instance;

    private CommandInterpreter() {

    }

    public static CommandInterpreter getInstance() {
        if (instance == null) {
            instance = new CommandInterpreter();
        }
        return instance;
    }

    public boolean interpretCommand(CommandSender sender, String label, String[] args) {
        switch(label) {
            case "aaflist":
                ListCommand command = new ListCommand();
                command.showInfo(sender, args);
                break;
            default:
                sender.sendMessage(ChatColor.RED + "AlternateAccountFinder doesn't know that command");
        }

        return false;
    }
}
