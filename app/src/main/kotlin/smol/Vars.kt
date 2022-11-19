package smol

import smol.util.*
import dev.kord.core.*
import dev.kord.common.entity.*
import dev.kord.core.entity.channel.*
import javax.script.*

object Vars{
    lateinit var client: Kord
    lateinit var statusReportChannel: TextChannel
    lateinit var epochStatusChannel: TextChannel
    var ubid = 0
    var epoch = 0L
    val superuser = 691650272166019164UL.toSnowflake()
    val bruh = arrayOf("kordin' time", "balls", "kord", "smolkeys", "nothing")
    
    val scriptEngine by lazy{
        ScriptEngineManager(Thread.currentThread().contextClassLoader).getEngineByExtension("kts")!!
    }
    
    val scriptContext = SimpleScriptContext()
}
