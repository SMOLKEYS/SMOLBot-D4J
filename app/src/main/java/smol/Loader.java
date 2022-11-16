package smol;

import smol.commands.*;
import discord4j.core.*;

public class Loader{
    public static void beginClient(GatewayDiscordClient client){
        Commands.stringCommand("ping", msg -> {
            msg.getChannel().flatMap(chn -> chn.createMessage("Pong!"));
        });
    }
}