package smol.util

import dev.kord.core.*
import dev.kord.common.entity.*
import dev.kord.core.entity.channel.*

fun Kord.getMessageChannel(id: Snowflake) = this.getChannel(id) as MessageChannel
fun ULong.toSnowflake() = Snowflake(this)
