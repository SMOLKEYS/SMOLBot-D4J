package smol.commands

import smol.*
import smol.util.*
import arc.struct.*
import dev.kord.core.*
import dev.kord.core.entity.*
import dev.kord.core.behavior.*
import kotlinx.coroutines.*

object Commands{
    private val pref = "sm!"
    val registry = ObjectMap<String, (Pair<Message, Array<String>>) -> Unit>()
    
    fun command(name: String, proc: (Pair<Message, Array<String>>) -> Unit){
        registry.put(pref + name, proc)
        println("command registered: $pref$name")
        
    }
    
    fun process(msg: Message){
        println("process start")
        var base = msg.content.trim().split(' ').toTypedArray()
        println("typed array split begin")
        if(base.size == 1){
            println("base size == 1")
            var args = base.copyWithoutFirstElement()
            
            if(registry.containsKey(base[0])){
                registry[base[0]](Pair(msg, args))
            }
        }else{
            println("base size > 1")
            if(registry.containsKey(base[0])){
                registry[base[0]](Pair(msg, arrayOf<String>()))
            }
        }
    }
    
    fun load(){
        command("ping"){
            Vars.client.launch{
                it.first.reply{
                    content = buildString{
                        appendNewline("Pong!")
                        if(it.second.size > 0){
                            append("Some arguments were detected! ( ")
                            it.second.forEach{ append("$it ") }
                            append(")")
                        }
                    }
                }
            }
        }
    }
    
    fun afterLoad(){
        registry.each{ t, k ->
            println("command: $t")
        }
    }
}
