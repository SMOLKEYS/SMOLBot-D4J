package smol.commands

import smol.*
import smol.util.*
import smol.commands.*
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
        //println("process start on ${msg.content}")
        var base = msg.content.trim().split(' ').toTypedArray()
        //println("typed array split begin on ${base.toList()}")
        if(base.size > 1){
            //println("base size > 1")
            val args = base.copyWithoutFirstElement()
            //println("args: ${args.toList()}")
            
            if(registry.containsKey(base[0])){
                //println("registry contains ${base[0]}")
                registry[base[0]](Pair(msg, args))
            }
        }else{
            //println("base size == 1")
            if(registry.containsKey(base[0])){
                //println("registry contains ${base[0]}")
                registry[base[0]](Pair(msg, arrayOf<String>()))
            }
        }
        
        //println("registry: ${base[0]}: ${registry.containsKey(base[0])}")
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
        
        command("newline"){
            Vars.client.launch{
                it.first.reply{
                    content = buildString{
                        if(it.second.size == 0){
                            append("Nothing to newline! ")
                            argument(Args.ANY, it.second.size)
                        }else it.second.forEach{ appendNewline(it) }
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
