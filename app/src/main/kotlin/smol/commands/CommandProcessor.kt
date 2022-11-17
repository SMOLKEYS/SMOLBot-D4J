package smol.commands

import smol.*
import dev.kord.core.event.message.*

object CommandProcessor{

    private val prefix = "sm!"
    
    open fun start(){
        Vars.client.on<MessageCreateEvent>{
            
        }
    }
    
    open fun parse(command: String){
        
    }
}
