package smol.util

import dev.kord.core.*
import dev.kord.common.entity.*
import dev.kord.core.entity.channel.*

suspend fun Kord.getTextChannel(id: Snowflake) = this.getChannel(id) as TextChannel


fun Channel.toTextChannel() = this as TextChannel
fun ULong.toSnowflake() = Snowflake(this)
