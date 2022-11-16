package smol;

import arc.util.*;
import smol.*;
import smol.util.*;
import discord4j.core.*;
import discord4j.core.object.entity.*;
import discord4j.common.util.*;

public class Loader{
    public static void beginClient(GatewayDiscordClient client){
        Time.run(60f * 14400f, () -> client.logout());
    }
}
