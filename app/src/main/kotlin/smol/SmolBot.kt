package smol

import smol.*
import smol.console.*
import dev.kord.core.*


suspend fun main(vararg args: String){
    val token = args.getOrNull(0)
    
    if(token == null){
        Printings.error("No token specified")
        return
    }
    
    Vars.client = Kord(token){
        
    }
    
    Vars.client.login{
        presence{ watching(Vars.bruh.random()) }
    }
}
