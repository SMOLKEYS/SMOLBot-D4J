package smol;

import discord4j.core.*;

public class SmolBot{
    public static DiscordClient client;
    
    public static void main(String[] args){
        if(args[0] == null) throw new NullPointerException("No token provided.");
        client = DiscordClient.create(args[0]);
    }
}