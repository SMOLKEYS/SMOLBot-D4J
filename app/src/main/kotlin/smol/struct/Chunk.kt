package smol.struct


open class Chunk<T>(val size: Int){
    private val holder = mutableListOf<MutableList<T>>(mutableListOf<T>())
    
    var totalElements = 0
    var totalChunks = 0
    
    /** Adds an element to the last created chunk. If the last created chunk is full, a new one is created and the provided element is added to the newly created chunk instead. */
    fun add(element: T): Chunk<T>{
        val chunk = holder[totalChunks]
        
        if(chunk.size != size){
            chunk.add(element)
            totalElements++
        }else{
            holder.add(mutableListOf<T>())
            totalChunks++
            
            holder[totalChunks].add(element)
            totalElements++
        }
        
        
        return this
    }
    
    /** Adds the following elements to the last created chunk. Will create more chunks when necessary. */
    fun addAll(vararg elements: T): Chunk<T>{
        elements.forEach{ this.add(it) }
        return this
    }
    
    /** Removes the specified chunk index, or the last created chunk if -1 is given as an argument. */
    fun remove(target: Int = -1): Chunk<T>{
        holder.remove(holder[if(target != -1) target else (holder.size - 1)])
        return this
    }
    
    /** Returns a copy of the holder of this chunk instance. Safely modifiable. */
    fun copyHolder(): MutableList<MutableList<T>>{
        val copy = mutableListOf<MutableList<T>>()
        
        forEachChunk{
            copy.add(it)
        }
        
        return copy
    }
    
    /** Returns the holder of this chunk instance. Do not modify directly! */
    fun rawHolder(): MutableList<MutableList<T>>{
        return holder
    }
    
    /** Returns true if the specified chunk index contains the specified element. */
    fun chunkContains(target: Int, element: T): Boolean{
        var b = false
        forEachElementOfChunk(target){
            if(it == element) b = true
        }
        return b
    }
    
    /** Returns true if any of the elements in all of the chunks matches the specified element. */
    fun contains(element: T): Boolean{
        var b = false
        forEachElement{
            if(it == element) b = true
        }
        return b
    }
    
    /** Iterates over each chunk. */
    fun forEachChunk(chunk: (MutableList<T>) -> Unit){
        holder.forEach(chunk)
    }
    
    /** Iterates over the elements of the specified chunk index. */
    fun forEachElementOfChunk(target: Int, element: (T) -> Unit){
        holder[target].forEach(element)
    }
    
    /** Iterates over every single element in this chunk. */
    fun forEachElement(element: (T) -> Unit){
        forEachChunk{
            it.forEach(element)
        }
    }
    
    fun randomChunk(): MutableList<T>{
        return holder.random()
    }
    
    fun randomElementFromChunk(target: Int): T{
        return holder[target].random()
    }
    
    fun randomElement(): T{
        return holder.random().random()
    }
    
    /** Swaps the positions of one chunk with another chunk. */
    fun swap(target: Int, with: Int): Chunk<T>{
        val t = holder[target]
        val w = holder[with]
        
        holder[target] = w
        holder[with] = t
        
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
