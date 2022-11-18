package smol.instances

import smol.*
import smol.util.*
import dev.kord.core.*
import dev.kord.core.entity.channel.*

object InstanceHandler{
    
    suspend fun compare(isTrue: () -> Unit): Boolean{
        if(Vars.epochSourceChannel.getLastMessage().content.toLong() > Vars.epoch) isTrue()
    }
}
