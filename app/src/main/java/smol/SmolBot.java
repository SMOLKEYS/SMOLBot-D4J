package smol;

import discord4j.core.*;
import reactor.core.publisher.*;
import smol.*;

/** The core class that fires up the bot. All other things are located in Loader. */
public class SmolBot{
    public static DiscordClient client;
    public static Mono<Void> mono;
    public static long startedOn;
    
    public static void main(String[] args){
        if(args[0] == null) throw new NullPointerException("No token provided.");
        client = DiscordClient.create(args[0]);
        
        mono = client.withGateway(cl -> {
            Loader.beginClient(cl);
            return Mono.empty();
        });
        preparation();
    }
    
    public static void preparation(){
        startedOn = System.currentTimeMillis();
        
        //starts the overall client.
        mono.block();
    }
}
