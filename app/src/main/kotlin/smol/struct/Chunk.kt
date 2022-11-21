package smol.struct

open class Chunk<T>(val size: Int){
    private var ind = 0
    private var ind2 = 0
    private val holder = mutableListOf<MutableList<T>>(mutableListOf<T>())
    
    var holderSize = 0
    
    fun add(element: T): Chunk<T>{
        if(holder[ind2].size > size){
            holder.add(mutableListOf<T>())
            ind2++
            holderSize++
        }else{
            holder[ind2].add(element)
            ind++
        }
        
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
