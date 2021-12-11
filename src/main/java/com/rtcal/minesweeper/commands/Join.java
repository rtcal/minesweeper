package com.rtcal.minesweeper.commands;

import com.rtcal.arenahandler.base.Arena;
import com.rtcal.command.ChildCommand;
import com.rtcal.minesweeper.Messages;
import com.rtcal.minesweeper.game.Minesweeper;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Join extends ChildCommand {


    public Join() {
        super("join", "Join a minesweeper arena!", "minesweeper.join", "/minesweeper join <arena_id>");
    }

    @Override
    protected void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Messages.PLAYER_ONLY_COMMAND);
            return;
        }

        if (!player.hasPermission(getPermission())) return;

        if (args.length != 1) {
            player.sendMessage(getUsage());
            return;
        }

        try {
            Minesweeper arena = (Minesweeper) Arena.getArena("minesweeper", Integer.parseInt(args[0]));
            arena.join(arena.getTeam(), player);

        } catch (NumberFormatException e) {
            player.sendMessage(Messages.ARENA_NOT_FOUND);
            e.printStackTrace();
        }
    }

    @Override
    protected List<String> getCompletions(final CommandSender sender, final String[] args) {
        if (args.length == 1) return Collections.singletonList("<arena_id>");
        return new ArrayList<>();
    }
}
