package smol.struct

open class Chunk<T>(val size: Int){
    private val holder = mutableListOf<MutableList<T>>()
    
    var totalElements = 0
    
    fun add(element: T): Chunk<T>{
        if(totalElements % size == 0 && totalElements != 0) holder.add(mutableListOf<T>())

        val chunk = holder[totalElements / size]
        
        chunk.add(element)
        totalElements++
        
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
