package smol;

import arc.util.*;
import smol.*;
import smol.util.*;
import smol.commands.*;
import discord4j.core.*;
import discord4j.core.object.entity.*;
import discord4j.common.util.*;

public class Loader{
    public static void beginClient(GatewayDiscordClient client){
        
        Commands.stringCommand("ping", msg -> {
            msg.getChannel().flatMap(chn -> chn.createMessage("Pong!")).subscribe();
        });
        
        Commands.stringCommand("epoch", msg -> {
            msg.getChannel().flatMap(chn -> chn.createMessage("SMOLBot Epoch: " + SmolBot.startedOn)).subscribe();
        });
        
        Commands.stringCommand("logout", msg -> {
            String[] args = msg.getContent().split(" ");
            
            System.out.println(Stringer.wrapInt(SmolBot.killKey));
            
            SmolBot.superusers.each(ke -> {
                if(args[1] == Stringer.wrapInt(SmolBot.killKey) && msg.getAuthorAsMember().block().getId().asLong() == ke) client.logout();
            });
        });
        
        Commands.stringCommand("commands", help -> {
            StringBuilder s = new StringBuilder();
            
            Commands.stringCommands.each((k, v) -> {
                s.append(k + "\n");
            });
            
            help.getChannel().flatMap(chn -> chn.createMessage(s.toString())).subscribe();
        });
        
        Commands.listenerBegin(client);
        
        Timer.schedule(() -> client.logout(), 14400);
    }
}
