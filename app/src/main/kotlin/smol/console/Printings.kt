package smol.console

object Printings{
    
    fun info(obj: Any){
        println("[Info]: $obj")
    }
    
    fun warn(obj: Any){
        println("[Warning]: $obj")
    }
    
    fun error(obj: Any){
        println("[Error]: $obj")
    }
    
}