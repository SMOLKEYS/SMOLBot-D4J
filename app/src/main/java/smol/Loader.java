package smol;

import arc.struct.*;
import smol.*;
import smol.util.*;
import discord4j.core.*;
import discord4j.core.object.entity.*;
import discord4j.core.object.prescence.*;
import discord4j.common.util.*;

public class Loader{
    public static void beginClient(GatewayDiscordClient client){
        Seq<String> heWait = Seq.with("veloren", "with no commands :pain:", "with smolkeys", "maid", "with a sleep session", "help me", "ahahahahahahahhahaha");
        
        SmolBot.client.gateway().setInitialPresence(aspac -> ClientPresence.doNotDisturb(ClientActivity.playing(heWait.random())));
        
        Time.run(60f * 14400f, () -> client.logout());
    }
}
