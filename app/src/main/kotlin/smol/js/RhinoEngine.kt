package smol.js

import smol.*
import rhino.*
import rhino.module.*
import rhino.module.provider.*

import java.io.*
import java.net.*
import java.util.regex.*

open class RhinoEngine(){
    var context: Context
    var scope: Scriptable
    
    init{
        context = getScriptContext()
        scope = ImporterTopLevel(context)
    }
    
    fun eval(scr: String): String?{
        var res: String? = null
        
        context = getScriptContext()
        
        context.initStandardObjects()
        
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
    
    fun removeGlobalProperty(name: String){
        scope.put(name, scope, Undefined.instance)
    }
    
    fun addGlobalProperty(property: Pair<String, Any?>){
        scope.put(property.first, scope, property.second)
    }
    
    fun addGlobalProperties(vararg properties: Pair<String, Any?>){
        properties.forEach{ addGlobalProperty(it) }
    }
    
    fun loadJsGlobal(){
        eval(Vars.resourceAsString("/scripts/global.js")!!)
    }
    
    fun reset(){
        context = getScriptContext()
        scope = ImporterTopLevel(context)
        
        loadJsGlobal()
    }
    
    fun getScriptContext(): Context{
        var c = Context.getCurrentContext()
        if(c == null) c = Context.enter()
        c.setOptimizationLevel(9)
        return c
    }
}
