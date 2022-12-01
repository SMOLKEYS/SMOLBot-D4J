package smol.commands

import smol.util.*
import smol.struct.*
import kotlin.ranges.*
import kotlin.random.*


open class CombatCommand{

    private var u0 = MutablePair<String, Int>("pl", 100)
    private var u1 = MutablePair<String, Int>("pl1", 100)
    private var attacks = 0

    fun preposition(): MutablePair<String, Int>{
        return if(attacks % 2 == 0) u1 else u0
    }
    
    fun opposition(): MutablePair<String, Int>{
        return if(attacks % 2 == 0) u0 else u1
    }

    fun begin(player1: String, player2: String, health: Int): List<String>{
        val texts = mutableListOf<String>()
        u0.first = player1
        u1.first = player2
        u0.second = health
        u1.second = health

        while(u0.second > 0 && u1.second > 0){
            val weapon = pairs.random()
            val finalDamage = if(weapon.first.contains("Healing!")) -weapon.second.random() else weapon.second.random()

            opposition().second -= finalDamage
            attacks++
            texts.add(buildString{
                appendNewline(formSentence(opposition().first, preposition().first, weapon.first, finalDamage))
                appendNewline("${u0.first}: ${u0.second}")
                append("${u1.first}: ${u1.second}")
            })
        }

        attacks = 0

        texts.add(if(u0.second <= 0) "${u1.first} wins!" else "${u0.first} wins!")

        return texts.toList()
    }

    fun formSentence(attacker: String, target: String, choicer: String, damage: Int): String{
        return buildString{
            append(choicer.replace("{0}", attacker).replace("{1}", target) + " ")
            append(if(damage < 0) "$damage health restored!" else "$damage damage!")
        }
    }

    companion object{
        private val pairs = mutableListOf<Pair<String, IntRange>>()

        fun addWeapon(text: String, damage: IntRange){
            pairs.add(text to damage)
        }
        
        fun addHealing(text: String, health: IntRange){
            pairs.add("Healing! $text" to health)
        }
    }
}
