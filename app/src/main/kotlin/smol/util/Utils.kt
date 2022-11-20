package smol.util

import arc.math.*
import smol.*
import dev.kord.core.*
import dev.kord.core.entity.*
import dev.kord.common.entity.*
import dev.kord.common.entity.optional.*
import dev.kord.core.entity.channel.*
import dev.kord.core.behavior.reply
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

suspend fun Message.reply(msg: String): Message = this.reply{ content = msg }

suspend fun Message.refer(): Message? = this.referencedMessage



inline fun <reified T> Array<T>.copyWithoutFirstElement(): Array<T>{
    var dest = arrayOfNulls<T>(this.size - 1)
    System.arraycopy(this, 1, dest, 0, dest.size)
    return dest as Array<T>
}

fun StringBuilder.appendNewline(obj: Any){
    this.append("$obj\n")
}

fun String.enforce(sub: Int = 0) = this.take(2000 - sub)

fun String.blockWrap(): String{
    return "```\n${this.enforce(8)}\n```"
}


inline fun launch(crossinline l: suspend CoroutineScope.() -> Unit) = smol.Vars.client.launch{ l() };

inline fun <R> async(crossinline l: suspend CoroutineScope.() -> R) = smol.Vars.client.async{ l() };

fun colorRand(): Int = Mathf.random(1, 255)