package com.rtcal.minesweeper.commands;

import com.rtcal.command.Command;

public final class BaseCommand {

    private final Command command = new Command("minesweeper");

    public BaseCommand(){
        command.addChild(new Create());
    }

}
