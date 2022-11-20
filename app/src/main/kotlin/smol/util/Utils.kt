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
import kotlinx.coroutines.*

suspend fun Kord.getTextChannel(id: Snowflake) = this.getChannel(id) as TextChannel
suspend fun Kord.getNewsChannel(id: Snowflake) = this as NewsChannel
suspend fun Kord.getMessageChannel(id: Snowflake) = this as MessageChannel
suspend fun Kord.getVoiceChannel(id: Snowflake) = this as VoiceChannel

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

inline fun <reified T> Array<T>.copyWithoutFirstElements(n: Int): Array<T>{
    var dest = arrayOfNulls<T>(this.size - n)
    System.arraycopy(this, n, dest, 0, dest.size)
    return dest as Array<T>
}

fun StringBuilder.appendNewline(obj: Any){
    this.append("$obj\n")
}

/** Enforces a string, limiting it to only 2000 characters and invalidating everyone/here pings. */
fun String.enforce(sub: Int = 0) = this.take(2000 - sub).replace("@everyone", "@еveryone").replace("@here", "@hеrе")

fun String.blockWrap(): String{
    return "```\n${this.enforce(8)}\n```"
}


inline fun launch(crossinline l: suspend CoroutineScope.() -> Unit) = smol.Vars.client.launch{ l() };

inline fun <R> async(crossinline l: suspend CoroutineScope.() -> R) = smol.Vars.client.async{ l() };

fun colorRand(): Int = Mathf.random(1, 255)

