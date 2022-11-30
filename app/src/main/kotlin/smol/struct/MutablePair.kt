package smol.struct

/** A data class that can hold two mutable objects. */
data class MutablePair<A, B>(var first: A, var second: B)