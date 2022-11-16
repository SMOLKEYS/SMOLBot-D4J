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
            Message mg = msg.getMessage();
            
            if(stringCommands.containsKey(mg.getContent())){
                stringCommands.get(mg.getContent()).get(mg);
                System.out.println("stringCommands contains " + mg.getContent());
            }
            
            return Mono.empty();
        }).subscribe();
    }
    
    public static void stringCommand(String name, Cons<Message> message){
        stringCommands.put(prefix + name, message);
        System.out.println("Command registered: " + prefix + name);
    }
}
