package smol.struct

open class Chunk<T>(val size: Int){
    private val holder = mutableListOf<MutableList<T>>(mutableListOf<T>())
    
    var totalElements = 0
    var totalChunks = 0
    
    fun add(element: T): Chunk<T>{
        val index = holder.size / size - 1
        val chunk = holder.getOrNull(index) ?: mutableListOf<T>().also{ holder.add(it); totalChunks++ }
        
        chunk.add(element)
        
        
        return this
    }
    
    fun addAll(vararg elements: T): Chunk<T>{
        elements.forEach{ this.add(it) }
        return this
    }
    
    operator fun get(index: Int): MutableList<T>{
        return holder[index]
    }
    
    operator fun get(index: Int, chunkIndex: Int): T{
        return get(index)[chunkIndex]
    }
    
    override fun toString(): String{
        return "Chunk$holder"
    }
}
