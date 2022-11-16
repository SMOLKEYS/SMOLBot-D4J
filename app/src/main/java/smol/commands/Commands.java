package smol.commands;

import arc.func.*;
import arc.struct.*;
import discord4j.core.*;
import discord4j.core.event.domain.message.*;
import discord4j.core.object.entity.*;
import reactor.core.publisher.*;

public class Commands{
    private static ObjectMap<String, Cons<Message>> stringCommands = new ObjectMap<String, Cons<Message>>();
    private static final String prefix = "sm!";
    
    public static void listenerBegin(GatewayDiscordClient client){
        client.on(MessageCreateEvent.class, msg -> {
            if(stringCommands.containsKey(msg.getMessage().getContent())) stringCommands.get(msg.getMessage().getContent()).get(msg.getMessage());
            
            return Mono.empty();
        });
    }
    
    public static void stringCommand(String name, Cons<Message> message){
        stringCommands.put(prefix + name, message);
    }
}
