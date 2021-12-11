package com.rtcal.minesweeper.commands;

import com.rtcal.arenahandler.exceptions.ArenaAlreadyExistsException;
import com.rtcal.command.ChildCommand;
import com.rtcal.minesweeper.Messages;
import com.rtcal.minesweeper.game.Minesweeper;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Create extends ChildCommand {

    public Create() {
        super("create", "Create an arena!", "minesweeper.create");
    }

    @Override
    protected void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player) || !sender.hasPermission(getPermission()) || args.length != 3) return;

        if (!player.getWorld().getName().equalsIgnoreCase("minesweeper")) {
            player.sendMessage(Messages.ARENA_WRONG_WORLD);
            return;
        }

        try {
            int width = Integer.parseInt(args[0]);
            int length = Integer.parseInt(args[1]);
            int bombs = Integer.parseInt(args[2]);

            new Minesweeper(player.getLocation(), width, length, bombs);

            player.sendMessage(Messages.ARENA_CREATION_SUCCESS);
        } catch (NumberFormatException | ArenaAlreadyExistsException e) {
            player.sendMessage(Messages.ARENA_CREATION_FAILED);
            e.printStackTrace();
        }

    }

    @Override
    protected List<String> getCompletions(final CommandSender sender, final String[] args) {
        return switch (args.length) {
            case 1 -> Collections.singletonList("width");
            case 2 -> Collections.singletonList("length");
            case 3 -> Collections.singletonList("bombs");
            default -> new ArrayList<>();
        };
    }
}
