package smol

import smol.*
import smol.util.*
import smol.console.*
import smol.commands.*
import java.util.*
import kotlin.concurrent.*
import kotlinx.coroutines.*
import arc.math.*
import dev.kord.core.*
import dev.kord.core.event.message.*
import dev.kord.core.entity.channel.*
import dev.kord.core.behavior.channel.*
import dev.kord.rest.builder.message.create.*
import dev.kord.common.*
import dev.kord.gateway.*

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
    Vars.sfwArchive = Vars.client.getTextChannel(948818170628698182UL.toSnowflake())
    Vars.nsfwArchive = Vars.client.getTextChannel(948892839390113842UL.toSnowflake())
    
    
    Vars.client.launch{
        Commands.load()
        Commands.afterLoad()
    }
    
    Timer(true).schedule(1000 * 60 * 60 * 6L){
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
            delay(1000 * 60 * 10L)
            Vars.client.editPresence{
                watching(Vars.bruh.random())
            }
        }
    }
    
    Vars.client.launch{
        while(true){
            delay(1000 * 4L)
            
            if(Vars.epochStatusChannel.fetchChannel().toTextChannel().getLastMessage()!!.content.toLong() > Vars.epoch){
                Vars.statusReportChannel.createMessage("Bot instance with older epoch detected. Terminating newer one...")
                
                Vars.client.shutdown()
            }
        }
    }
    
    
    Vars.client.on<MessageCreateEvent>{
        Commands.process(this.message)
        //println("message send detected")
    }
    
    
    
    Vars.client.login{
        presence{ watching(Vars.bruh.random()) }
        
        @OptIn(PrivilegedIntent::class)
        intents += Intent.MessageContent
        
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
