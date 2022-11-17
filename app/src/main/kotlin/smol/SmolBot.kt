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
    Vars.statusReportChannel = Vars.client.getMessageChannel(948818452678852628UL.toSnowflake())
    
    Timer(true).schedule(1000 * 60 * 60 * 4L){
        Vars.client.launch{
            Printings.info("This instance is shutting down.")
            
            Vars.statusReportChannel.createMessage{
                content = "The bot is now exiting..."
            }
            
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
                footer = "Ahehe..."
                
                color = Color(0, 255, 0)
            }
        }
    }
}
