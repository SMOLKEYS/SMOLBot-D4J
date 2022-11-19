package smol.commands

import smol.util.*
import arc.struct.*
import dev.kord.core.*
import dev.kord.core.entity.*
import dev.kord.core.behavior.*

object Commands{
    private val pref = "sm!"
    val registry = ObjectMap<String, (Pair<Message, Array<String>>) -> Unit>()
    
    fun command(name: String, proc: (Pair<Message, Array<String>>) -> Unit){
        registry.put(pref + name, proc)
    }
    
    fun process(msg: Message){
        if(msg.content.startsWith(pref)){
            var base = msg.content.trim().split(' ').toTypedArray()
            if(base.size > 1){
                var args = base.copyWithoutFirstElement()
            
                if(registry.containsKey(base[0])) registry[base[0]](Pair(msg, args))
            }else{
                if(registry.containsKey(base[0])) registry[base[0]](Pair(msg, arrayOf<String>()))
            }
        }
    }
    
    fun load(){
        command("ping"){
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
