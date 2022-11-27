package smol.util

import arc.math.*
import smol.*
import dev.kord.core.*
import dev.kord.core.entity.*
import dev.kord.common.entity.*
import dev.kord.common.entity.optional.*
import dev.kord.core.entity.channel.*
import dev.kord.core.behavior.*
import dev.kord.core.behavior.channel.*
import dev.kord.rest.builder.message.create.*
import java.io.*
import java.awt.image.*
import java.util.*
import javax.imageio.*
import kotlin.time.*
import kotlinx.coroutines.*

suspend fun Kord.getTextChannel(id: Snowflake) = this.getChannel(id) as TextChannel
suspend fun Kord.getNewsChannel(id: Snowflake) = this.getChannel(id) as NewsChannel
suspend fun Kord.getMessageChannel(id: Snowflake) = this.getChannel(id) as MessageChannel
suspend fun Kord.getVoiceChannel(id: Snowflake) = this.getChannel(id) as VoiceChannel

fun Channel.toTextChannel() = this as TextChannel
fun Channel.toNewsChannel() = this as NewsChannel
fun Channel.toMessageChannel() = this as MessageChannel
fun Channel.toVoiceChannel() = this as VoiceChannel


fun ULong.toSnowflake() = Snowflake(this)

suspend fun MessageChannelBehavior.createMessage(msg: UserMessageCreateBuilder.() -> Unit, ment: Boolean = false): Message{
    return this.createMessage{
        msg(this)
        if(!ment) this.allowedMentions()
    }
}

suspend fun MessageChannelBehavior.createMessage(msg: String, ment: Boolean = false): Message{
    return this.createMessage({ content = msg }, ment)
}

suspend fun Message.reply(msg: UserMessageCreateBuilder.() -> Unit, ment: Boolean = false): Message{
    return this.reply{
        msg(this)
        if(!ment) this.allowedMentions()
    }
}

suspend fun Message.reply(msg: String, ment: Boolean = false): Message{
    return this.reply({ content = msg }, ment)
}

suspend fun Message.refer(): Message? = this.referencedMessage


inline fun <reified T> Array<T>.copyWithoutFirstElement(): Array<T>{
    var dest = arrayOfNulls<T>(this.size - 1)
    System.arraycopy(this, 1, dest, 0, dest.size)
    return dest as Array<T>
}

inline fun <reified T> Array<T>.copyWithoutFirstElementOrNull(): Array<T>?{
    try{
        return this.copyWithoutFirstElement()
    }catch(e: Exception){
        return null
    }
}

inline fun <reified T> Array<T>.copyWithoutFirstElements(n: Int): Array<T>{
    var dest = arrayOfNulls<T>(this.size - n)
    System.arraycopy(this, n, dest, 0, dest.size)
    return dest as Array<T>
}

inline fun <reified T> Array<T>.copyWithoutFirstElementsOrNull(n: Int): Array<T>?{
    try{
        return this.copyWithoutFirstElements(n)
    }catch(e: Exception){
        return null
    }
}

fun StringBuilder.appendNewline(obj: Any){
    this.append("$obj\n")
}

fun StringBuilder.emptyNewline() = this.append("\n")


/** Enforces a string, limiting it to only 2000 (or 4000 if nitro is true) characters and invalidating everyone/here pings. */
fun String.enforce(sub: Int = 0, nitro: Boolean = false) = this.take((if(nitro) 4000 else 2000) - sub).replace("@everyone", "@еveryone").replace("@here", "@hеrе")

fun String.blockWrap(): String{
    return "```\n${this.enforce(8)}\n```"
}


inline fun launch(crossinline l: suspend CoroutineScope.() -> Unit) = smol.Vars.client.launch{ l() };

inline fun <R> async(crossinline l: suspend CoroutineScope.() -> R) = smol.Vars.client.async{ l() };


fun colorRand(): Int = Mathf.random(1, 255)

fun linkage(text: String, link: String) = "**[$text]($link)**"


suspend fun uinfo(kord: Kord, guild: Guild?): String{
    return uinfo(userFrom(kord.selfId)!!, guild)
}

@OptIn(ExperimentalTime::class)
suspend fun uinfo(usr: User, guild: Guild?): String{
    return buildString{
        appendNewline("**User Info**")
        emptyNewline()
        appendNewline("Name/Tag: ${usr.tag}")
        appendNewline("Is Bot: ${usr.isBot}")
        appendNewline("Discord Join Date: ${Date(usr.id.timestamp.toEpochMilliseconds())}")
        appendNewline(linkage("PFP Link", usr.avatar!!.cdnUrl.toUrl()))
        emptyNewline()
        
        if(guild != null){
            val ext = usr.asMemberOrNull(guild.id)
            if(ext != null){
                appendNewline("**Guild-specific Info**")
                if(ext.nickname != null) appendNewline("Nickname: ${ext.nickname!!}")
                appendNewline("Server Owner: ${ext.isOwner()}")
                appendNewline("Join Date: ${Date(ext.joinedAt.toEpochMilliseconds())}")
                append("Roles: ")
                ext.roles.collect{ append(it.mention + " ") }
                appendNewline("")
            }
        }
        
    }
}

suspend fun userFrom(id: Snowflake): User?{
    return Vars.client.unsafe.user(id).asUserOrNull()
}

fun BufferedImage.toKordImage(): dev.kord.rest.Image{
    val byteStream = ByteArrayOutputStream()
    ImageIO.write(this, "png", byteStream)
    val result = byteStream.toByteArray()
    
    return dev.kord.rest.Image.raw(result, dev.kord.rest.Image.Format.fromContentType("image/png"))
}
