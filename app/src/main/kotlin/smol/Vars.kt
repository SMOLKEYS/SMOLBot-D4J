package smol

import smol.js.*
import smol.util.*
import smol.commands.*
import java.util.Vector
import dev.kord.core.*
import dev.kord.common.entity.*
import dev.kord.core.entity.channel.*
import dev.kord.rest.builder.message.*
import java.net.*
import javax.script.*
import rhino.*

object Vars{
    lateinit var client: Kord
    lateinit var statusReportChannel: TextChannel
    lateinit var epochStatusChannel: TextChannel
    lateinit var sfwArchive: TextChannel
    lateinit var nsfwArchive: TextChannel
    
    val battle = CombatCommand()
    
    var ubid = "ubid"
    var epoch = 0L
    val superuser = 691650272166019164UL.toSnowflake()
    
    val bruh = arrayOf("kordin' time", "balls", "kord", "smolkeys", "nothing", "SQUID GAMES")
    
    val chars = ('a'..'z') + ('0'..'9')
    val profileRange = (1..4)
    
    val links = mutableListOf(
        EmbedBuilder.Field().apply{
            value = buildString{
                appendNewline(linkage("Invite Bot", "https://discord.com/oauth2/authorize?client_id=946790508737491004&permissions=2147483647&scope=bot"))
                appendNewline(linkage("SmolBot CentCom Invite", "https://discord.gg/8syZQdqhwy"))
            }
        }
    )
    
    val scriptEngine by lazy{
        ScriptEngineManager(Thread.currentThread().contextClassLoader).getEngineByExtension("kts")!!
    }
    
    val scriptContext = SimpleScriptContext()
    
    lateinit var jsScriptEngine: RhinoEngine
    
    val defaultImports by lazy{
        ClassLoader::class.java.getDeclaredField("classes")
            .let {
                it.isAccessible = true
                it.get(Vars::class.java.classLoader) as Vector<Class<*>>
            }
            .filter { "internal" !in it.name && "$" !in it.name }
            .map { it.name.substringBeforeLast('.') + ".*" }
            .distinct()
            .joinToString(";") { "import $it" }
    }
    
    fun resource(path: String): URL?{
        return {}::class.java.getResource(path)
    }
    
    fun resourceAsString(path: String): String?{
        try{
            return String({}::class.java.getResourceAsStream(path).readAllBytes())
        }catch(e: Exception){
            return null
        }
    }
    
    fun load(){
        //generic
        CombatCommand.addWeapon(Weapon("Fist", 1..3, "p1 punched p2!"))
        CombatCommand.addWeapon(Weapon("Kick", 3..5, "p1 kicked p2!"))
        
        //less generic
        CombatCommand.addWeapon(Weapon("Sharp", 7..13, mutableListOf(
            "p1 stabbed p2!",
            "p1 slashed p2!"
        )))
        CombatCommand.addWeapon(Weapon("Fire", 15..24, mutableListOf(
            "p1 burned p2 with a torch!",
            "p1 torched p2!",
            "p1 burned p2!",
            "p1 firestormed p2!"
        )))
        CombatCommand.addWeapon(Weapon("Blunt", 16..26, mutableListOf(
            "p1 bashed p2 with a wood bat!",
            "p1 bashed p2 with a metal bat!",
            "p1 threw a chair at p2!"
        )))
        
        jsScriptEngine = RhinoEngine()
        
        jsScriptEngine.loadJsGlobal()
    }
}
