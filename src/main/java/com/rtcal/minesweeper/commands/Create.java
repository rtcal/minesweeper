package com.rtcal.minesweeper.commands;

import com.rtcal.arenahandler.exceptions.ArenaAlreadyExistsException;
import com.rtcal.command.ChildCommand;
import com.rtcal.minesweeper.Main;
import com.rtcal.minesweeper.Messages;
import com.rtcal.minesweeper.game.Minesweeper;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Create extends ChildCommand {

    public Create() {
        super("create", "Create a minesweeper arena!", "minesweeper.create", "/minesweeper create <width> <length> <bombs>");
    }

    @Override
    protected void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Messages.PLAYER_ONLY_COMMAND);
            return;
        }

        if (!player.hasPermission(getPermission())) return;

        if (args.length != 3) {
            player.sendMessage(getUsage());
            return;
        }


        if (!player.getWorld().getName().equalsIgnoreCase(Main.getMinesweeperWorld().getName())) {
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
            case 1 -> Collections.singletonList("<width>");
            case 2 -> Collections.singletonList("<length>");
            case 3 -> Collections.singletonList("<bombs>");
            default -> new ArrayList<>();
        };
    }
}
