package smol.commands

import smol.util.*
import smol.struct.*
import kotlin.ranges.*
import kotlin.random.*



data class Player(var name: String, var health: Int){
    var hits = 0
    var winner = false
    var myTurn = false
    
    fun set(newName: String, baseHealth: Int){
        name = newName
        health = baseHealth
        hits = 0
        winner = false
        myTurn = false
    }
    
    fun damage(loss: Int){
        if(health < loss){
            health = 0
            return
        }
        health -= loss
    }
    
    fun attack(target: Player, weapon: Weapon): String{
        val finalDamage = weapon.damage.random()
        
        target.damage(finalDamage)
        target.hits++
        
        target.myTurn = true
        myTurn = false
        
        if(target.health <= 0) winner = true
        
        return weapon.announce().replace("p1", name).replace("p2", target.name) + " $finalDamage damage!"
    }
    
    override fun toString() = name
    
    companion object{
        fun deadCheck(p1: Player, p2: Player) = (p1.health <= 0 || p2.health <= 0)
        
        fun hitsEndured(p1: Player, p2: Player): String{
            return buildString{
                appendNewline("$p1 - hit ${p1.hits} times!")
                append("$p2 - hit ${p2.hits} times!")
            }
        }
        
        fun turn(attacker: Player, target: Player): Player{
            return if(attacker.myTurn) attacker else target
        }
        
        fun turnOther(attacker: Player, target: Player): Player{
            return if(!attacker.myTurn) attacker else target
        }
    }
}



data class Weapon(var name: String, var damage: IntRange, val announcementMessages: MutableList<String>){
    
    constructor(name: String, damage: IntRange, announcement: String) : this(name, damage, mutableListOf(announcement))
    
    fun announce(): String{ return announcementMessages.random() }
    
    override fun toString() = "Weapon(name=$name, damage=$damage)"
}



open class CombatCommand{

    private val u0 = Player("pl", 100)
    private val u1 = Player("pl1", 100)
    
    var occupied = false
    
    
    fun begin(player1: String, player2: String, health: Int): List<String>{
        val texts = mutableListOf<String>()
        u0.set(player1, health)
        u1.set(player2, health)
        
        (if(Random.nextBoolean()) u0 else u1).myTurn = true
        
        while(!Player.deadCheck(u0, u1)){
            texts.add(Player.turn(u0, u1).attack(Player.turnOther(u0, u1), weapons.random()) + "\n$u0: ${u0.health}\n$u1: ${u1.health}")
        }
        
        texts.add("${if(u0.winner) u0 else u1} wins!")
        
        return texts.toList()
    }

    companion object{
        private val weapons = mutableListOf<Weapon>()

        fun addWeapon(weapon: Weapon){
            weapons.add(weapon)
        }
    }
}
