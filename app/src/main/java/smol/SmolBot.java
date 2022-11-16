package smol;

import arc.math.*;
import arc.struct.*;
import smol.*;
import discord4j.core.*;
import reactor.core.publisher.*;

/** The core class that fires up the bot. All other things are located in Loader. */
public class SmolBot{
    public static DiscordClient client;
    public static int killKey;
    public static Mono<Void> mono;
    public static long startedOn;
    public static Seq<Long> superusers = Seq.with(691650272166019164L);
    
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
        killKey = Mathf.random(85250, 9816390);
        
        //starts the overall client.
        mono.block();
    }
}
