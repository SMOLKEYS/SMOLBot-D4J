package smol.commands

import arc.util.*
import smol.*
import smol.util.*
import smol.commands.*
import arc.struct.*
import dev.kord.core.*
import dev.kord.core.entity.*
import dev.kord.core.behavior.*
import dev.kord.core.behavior.channel.*
import dev.kord.rest.builder.message.*
import dev.kord.rest.builder.message.create.*
import dev.kord.common.*
import javax.script.*
import kotlinx.coroutines.*

object Commands{
    private val pref = "sm!"
    val registry = ObjectMap<String, suspend (Pair<Message, Array<String>>) -> Unit>()
    val pairs = mutableListOf<EmbedBuilder.Field>()
    
    fun command(name: String, desc: String, proc: suspend (Pair<Message, Array<String>>) -> Unit){
        command(name, "`no arguments`", desc, proc)
    }
    
    fun command(nameO: String, args: String, desc: String, proc: suspend (Pair<Message, Array<String>>) -> Unit){
        registry.put(pref + nameO, proc)
        println("command registered: $pref$nameO")
        pairs.add(EmbedBuilder.Field().apply{
            name = "$pref$nameO $args"
            value = desc
        })
    }
    
    suspend fun process(msg: Message){
        //println("process start on ${msg.content}")
        var base = msg.content.trim().split("\\s".toRegex()).toTypedArray()
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
        command("ping", "Sends a \"Pong!\" message back to the caller."){
            it.first.reply(buildString{
                appendNewline("Pong!")
                if(it.second.size > 0){
                    append("Some arguments were detected! ( ")
                    it.second.forEach{ append("$it ") }
                    append(")")
                }
            }.enforce())
        }
        
        command("newline", "<any...>", "Repeats each argument with a newline."){
            it.first.reply(buildString{
                if(it.second.size == 0) append("Nothing to newline! (Expected ${Args.ANY} arguments, got 0)") else it.second.forEach{ appendNewline(it) }
            }.enforce())
        }
        
        command("exec", "<any...>", "Executes a shell command. Extremely dangerous.  Superuser only."){
            it.first.reply(buildString{
                if(it.first.author!!.id != Vars.superuser) append("You cannot run this command.") else if(it.second.size == 0 ) append("No arguments specified! (Expected ${Args.ANY} arguments, got 0)") else append(OS.exec(*it.second))
            }.blockWrap())
        }
        
        command("eval", "<script>", "Evaluates `kts` code. Extremely dangerous. Superuser only."){
            val script = it.first.content.substring(8)
            
            Vars.scriptEngine.put("message", it.first)
            Vars.scriptContext.setAttribute("message", it.first, ScriptContext.ENGINE_SCOPE)
            
            val res = try{
                if(it.first.author!!.id != Vars.superuser) throw Throwable("You cannot run this command.")
                
                Vars.scriptEngine.eval("${Vars.defaultImports}\n$script", Vars.scriptContext).let{
                    when(it){
                        is Deferred<*> -> it.await()
                        is Job -> it.join()
                        else -> it
                    }
                }
                
            }catch(e: Throwable){
                (e.cause ?: e).let{
                    it.toString()
                }
            }
            
            it.first.reply("$res".blockWrap())
        }
        
        //cleanup todo
        command("logout", "<bot ubid>", "Shuts down a bot instance with the specified ubid. Superuser only."){
            it.first.reply(buildString{
                if(it.first.author!!.id != Vars.superuser) append("You cannot use this command.") else if(it.second.size == 0) append("Expected at least 1 argument, got none".blockWrap()) else if(it.second[0].toIntOrNull() == null) append("Invalid number.") else if(it.second[0].toInt() != Vars.ubid) append("Wrong number.") else{ append("Exiting..."); Vars.client.shutdown() }
            })
        }
        
        command("archive", "<safe/unsafe>", "Archives a message and its attachments (as links) to the SmolBot CentCom server."){
            if(it.first.refer() != null){
                when(it.second[0]){
                    "safe" -> {
                        it.first.reply("Uploaded message to centcom sfw archive.")
                        
                        val ref = it.first.refer()!!
                        
                        //text content
                        Vars.sfwArchive.createMessage("By ${ref.author!!.username} in ${ref.channel.mention}:\n${ref.content.enforce(170)}\nUploaded by ${it.first.author!!.username}".enforce())
                        
                        //text attachments (as urls), if any
                        if(!ref.attachments.isEmpty()){
                            Vars.sfwArchive.createMessage(buildString{
                                appendNewline("Attachments:")
                                ref.attachments.forEach{
                                    appendNewline(it.url)
                                }
                            })
                        }
                    }
                    
                    "unsafe" -> {
                        it.first.reply("Uploaded message to centcom *nsfw* archive.")
                        
                        val ref = it.first.refer()!!
                        
                        Vars.nsfwArchive.createMessage("By ${ref.author!!.username} in ${ref.channel.mention}:\n${ref.content.enforce(170)}\nUploaded by ${it.first.author!!.username}".enforce())
                        
                        if(!ref.attachments.isEmpty()){
                            Vars.nsfwArchive.createMessage(buildString{
                                appendNewline("Attachments:")
                                ref.attachments.forEach{
                                    appendNewline(it.url)
                                }
                            })
                        }
                    }
                    
                    null -> it.first.reply("No arguments provided! (safe/unsafe)")
                    
                    else -> it.first.reply("Wrong arguments provided! (safe/unsafe)")
                }
            }else it.first.reply("No message target found! (Use this command as a reply!)")
        }
        
        command("help", "Returns this help embed."){
            it.first.reply{
                embed{
                    title = "Help"
                    description = "A list of all the commands SMOLBot has."
                    
                    fields = pairs
                
                    color = Color(colorRand(), colorRand(), colorRand())
                }
            }
        }
        
        command("space", "<int> <any...>", "Returns the inputted text in the second argument spaced out."){
            it.first.reply(buildString{
                when{
                    it.second[0].toIntOrNull() == null -> append("First argument is not a valid number!")
                    it.second[1] == null -> append("No second argument supplied!")
                    else -> {
                        it.second.copyWithoutFirstElement().forEach{ k ->
                            k.forEach{ n -> append(n + " ".repeat(it.second[0].toInt())) }
                        }
                    }
                }
            }.enforce())
        }
        
        command("links", "Returns an embed containing links."){
            it.first.reply{
                embed{
                    title = "Links"
                    description = "Ehh, some links."
                    
                    fields = Vars.links
                    
                    color = Color(colorRand(), colorRand(), colorRand())
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
