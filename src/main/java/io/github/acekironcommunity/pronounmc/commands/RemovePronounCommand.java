package io.github.acekironcommunity.pronounmc.commands;

import io.github.acekironcommunity.pronounmc.PronounAPI;
import io.github.acekironcommunity.pronounmc.Utils;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RemovePronounCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        switch (args.length) {
            case 1:
                // Modify own pronouns
                if (!(sender instanceof Player)) {
                    sender.sendMessage(Utils.formatMessage("Only players can run this command.", false));
                    return true;
                }
                Player player = (Player) sender;

                if (PronounAPI.removePronouns(player.getUniqueId(), args[0])) {
                    player.sendMessage(Utils.formatMessage("Updated pronouns.", true));
                } else {
                    player.sendMessage(Utils.formatMessage(args[0] + " isn't a valid pronoun code.", false));
                }
                break;

            case 2:
                // Modify somebody else's pronouns
                if (!sender.hasPermission("pronounmc.modify.other")) {
                    sender.sendMessage(Utils.formatMessage("Missing the pronounmc.modify.other permission.", false));
                    return true;
                }

                Player player2 = Bukkit.getPlayer(args[1]);
                if (player2 == null) {
                    sender.sendMessage(Utils.formatMessage("Could not find player " + args[0] + ".", false));
                    return true;
                }

                if (PronounAPI.removePronouns(player2.getUniqueId(), args[0])) {
                    sender.sendMessage(Utils.formatMessage("Updated pronouns.", true));
                } else {
                    sender.sendMessage(Utils.formatMessage(args[0] + " isn't a valid pronoun code.", false));
                }
                break;

            default:
                return false;
        }

        return true;
    }

}
