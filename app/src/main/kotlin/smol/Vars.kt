package smol

import smol.util.*
import java.util.Vector
import dev.kord.core.*
import dev.kord.common.entity.*
import dev.kord.core.entity.channel.*
import dev.kord.rest.builder.message.*
import javax.script.*

object Vars{
    lateinit var client: Kord
    lateinit var statusReportChannel: TextChannel
    lateinit var epochStatusChannel: TextChannel
    lateinit var sfwArchive: TextChannel
    lateinit var nsfwArchive: TextChannel
    var ubid = "ubid"
    var epoch = 0L
    val superuser = 691650272166019164UL.toSnowflake()
    
    val bruh = arrayOf("kordin' time", "balls", "kord", "smolkeys", "nothing")
    
    val chars = ('a'..'z') + ('0'..'9')
    
    val links = mutableListOf(
        EmbedBuilder.Field().apply{
            value = linkage("Invite Bot", "https://discord.com/oauth2/authorize?client_id=946790508737491004&permissions=2147483647&scope=bot")
            
        },
        EmbedBuilder.Field().apply{
            value = linkage("SmolBot CentCom Invite", "https://discord.gg/8syZQdqhwy")
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
}
