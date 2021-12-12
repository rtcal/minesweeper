package com.rtcal.minesweeper.commands;

import com.rtcal.arenahandler.base.Arena;
import com.rtcal.command.ChildCommand;
import com.rtcal.minesweeper.Messages;
import com.rtcal.minesweeper.game.Minesweeper;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class Start extends ChildCommand {


    public Start() {
        super("start", "Start a minesweeper arena!", "arena.start", "/minesweeper start <arena_id>");
    }

    @Override
    protected void execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission(getPermission())) return;

        if (args.length != 1) {
            sender.sendMessage(getUsage());
            return;
        }

        try {
            Minesweeper arena = (Minesweeper) Arena.getArena("minesweeper", Integer.parseInt(args[0]));

            if (arena == null) sender.sendMessage(Messages.ARENA_NOT_FOUND);
            else arena.start();

        } catch (NumberFormatException e) {
            sender.sendMessage(Messages.ARENA_NOT_FOUND);
            e.printStackTrace();
        }
    }

    @Override
    protected List<String> getCompletions(final CommandSender sender, final String[] args) {
        if (args.length == 1) return Arena.getArenaIDs(Minesweeper.TYPE).stream().map(Object::toString).collect(Collectors.toList());
        return new ArrayList<>();
    }
}
