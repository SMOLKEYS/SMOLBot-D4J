package smol.js

import rhino.*
import rhino.module.*
import rhino.module.provider.*

import java.io.*
import java.net.*
import java.util.regex.*

open class RhinoEngine(){
    lateinit var context: Context
    lateinit var scope: Scriptable
    
    init{
    }
    
    fun eval(scr: String): String?{
        var res: String? = null
        
        try{
            var o = context.evaluateString(scope, scr, "rhino.js", 1)
            
            if(o is NativeJavaObject) o = o.unwrap()
            if(o is Undefined) o = "undefined"
            
            res = java.lang.String.valueOf(o)
        }catch(t: Throwable){
            res = t.toString()
        }
        
        return res
    }
    
    fun addGlobalProperty(name: String, obj: Any?){
        scope.put(name, scope, obj)
    }
    
    fun getScriptContext(): Context{
        var c = Context.getCurrentContext()
        if(c == null) c = Context.enter()
        c.setOptimizationLevel(9)
        return c
    }
}
