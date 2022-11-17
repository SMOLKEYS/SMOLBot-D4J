package smol

import smol.*
import smol.console.*
import java.util.*
import kotlin.concurrent.*
import kotlinx.coroutines.*
import arc.math.*
import dev.kord.core.*
import dev.kord.core.entity.channel.*

suspend fun main(vararg args: String){
    val token = args.getOrNull(0)
    
    if(token == null){
        Printings.error("No token specified")
        return
    }
    
    Vars.client = Kord(token){
        
    }
    
    Vars.ubid = Mathf.random(197360, 9801630)
    
    Timer(true).schedule(1000 * 60 * 60 * 5L){
        Vars.client.launch{
            Printings.info("This instance is shutting down.")
            Vars.client.shutdown()
        }
    }
    
    Timer(true).scheduleAtFixedRate(0L, 1000 * 25L){
        Vars.client.launch{
            Vars.client.editPresence{
                watching(Vars.bruh.random())
            }
        }
    }
    
    Vars.client.login{
        presence{ watching(Vars.bruh.random()) }
        (Vars.client.getChannel(Vars.statusReportChannel) as MessageChannel).createMessage("Bot login complete! (${Vars.ubid})")
    }
}
