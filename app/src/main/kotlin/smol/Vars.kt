package smol

import smol.util.*
import smol.commands.*
import java.util.Vector
import dev.kord.core.*
import dev.kord.common.entity.*
import dev.kord.core.entity.channel.*
import dev.kord.rest.builder.message.*
import java.net.*
import javax.script.*

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
	
	fun load(){
	    CombatCommand.addWeapon("{0} punched {1}!", 1..3)
	    CombatCommand.addWeapon("Lightning called down by {0} hit {1}!", 40..100)
	    CombatCommand.addWeapon("Fist storm from {0}! All fists landed on {1}!", 10..60)
	    CombatCommand.addWeapon("{0} sent burning coal to {1}!", 15..20)
	    CombatCommand.addWeapon("{0} kicked {1}!", 4..9)
	    CombatCommand.addWeapon("Storming flames!\n{0} called a firestorm onto {1}!", 300..550)
	    CombatCommand.addWeapon("{0} dropped a piano onto {1}!", 150..650)
	    CombatCommand.addWeapon("{0} became based! {1} couldn't handle it and lost a lot of health!", 250..1000)
	    CombatCommand.addWeapon("{0}, {0}! YOU'VE MADE A DEAL WITH ME, ME!\n{1} was hit by a scythe!", 860..870)
	}
}
