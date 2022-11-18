package smol

import smol.*
import smol.util.*
import smol.console.*
import java.util.*
import kotlin.concurrent.*
import kotlinx.coroutines.*
import arc.math.*
import dev.kord.core.*
import dev.kord.core.entity.channel.*
import dev.kord.core.behavior.channel.*
import dev.kord.rest.builder.message.create.*
import dev.kord.common.*

suspend fun main(vararg args: String){
    val token = args.getOrNull(0)
    
    if(token == null){
        Printings.error("No token specified")
        return
    }
    
    Vars.client = Kord(token){
        
    }
    
    Vars.ubid = Mathf.random(197360, 9801630)
    //mood
    Vars.statusReportChannel = Vars.client.getTextChannel(948818452678852628UL.toSnowflake())
    Vars.epochStatusChannel = Vars.client.getTextChannel(1043136089684201483UL.toSnowflake())
    
    Timer(true).schedule(1000 * 60 * 60 * 5L){
        Vars.client.launch{
            Printings.info("This instance is shutting down.")
            
            Vars.statusReportChannel.createMessage{
                content = "The bot is now exiting..."
            }
            
            Vars.client.shutdown()
        }
    }
    
    Vars.client.launch{
        while(true){
            delay(1000 * 25L)
            Vars.client.editPresence{
                watching(Vars.bruh.random())
            }
        }
        
        while(true){
            delay(1000 * 5L)
            if(Vars.epochStatusChannel.getLastMessage().content.toLong() > Vars.epoch){
                Vars.statusReportChannel("Bot instance with older epoch detected. Terminating newer one...")
                
                Vars.client.shutdown()
            }
        }
    }
    
    
    
    Vars.client.login{
        presence{ watching(Vars.bruh.random()) }
        
        Vars.epoch = System.currentTimeMillis()
        
        Vars.statusReportChannel.createMessage{
            content = "Bot initialized!"
            
            embed{
                title = "Extra Info"
                description = """
                    ```
                    UBid: ${Vars.ubid}
                    Epoch (ms): ${Vars.epoch}
                    ```
                """.trimIndent()
                
                color = Color(0, 255, 0)
            }
        }
        
        Vars.epochStatusChannel.createMessage(Vars.epoch.toString())
    }
}
