package smol;

import discord4j.core.*;
import smol.*;

/** The core class that fires up the bot. All other things are located in Loader. */
public class SmolBot{
    public static DiscordClient client;
    public static long startedOn;
    
    public static void main(String[] args){
        if(args[0] == null) throw new NullPointerException("No token provided.");
        client = DiscordClient.create(args[0]);
        
        Loader.beginClient(client);
        varPreparation();
    }
    
    public static void varPreparation(){
        startedOn = System.currentTimeMillis();
    }
}
