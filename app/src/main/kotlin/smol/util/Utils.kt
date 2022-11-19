package smol.util

import dev.kord.core.*
import dev.kord.core.entity.*
import dev.kord.common.entity.*
import dev.kord.core.entity.channel.*
import dev.kord.core.behavior.reply


suspend fun Kord.getTextChannel(id: Snowflake) = this.getChannel(id) as TextChannel


fun Channel.toTextChannel() = this as TextChannel
fun ULong.toSnowflake() = Snowflake(this)

suspend fun Message.reply(msg: String): Message = this.reply{ content = msg }


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
