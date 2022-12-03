//arc
importPackage(Packages.arc)
importPackage(Packages.arc.audio)
importPackage(Packages.arc.func)
importPackage(Packages.arc.flabel)
importPackage(Packages.arc.graphics)
importPackage(Packages.arc.graphics.g2d)
importPackage(Packages.arc.graphics.gl)
importPackage(Packages.arc.input)
importPackage(Packages.arc.math)
importPackage(Packages.arc.math.geom)
importPackage(Packages.arc.scene)
importPackage(Packages.arc.scene.actions)
importPackage(Packages.arc.scene.event)
importPackage(Packages.arc.scene.style)
importPackage(Packages.arc.scene.ui)
importPackage(Packages.arc.scene.ui.layout)
importPackage(Packages.arc.scene.utils)
importPackage(Packages.arc.struct)
importPackage(Packages.arc.util)
importPackage(Packages.arc.util.async)
importPackage(Packages.arc.util.io)
importPackage(Packages.arc.util.noise)
importPackage(Packages.arc.util.pooling)
importPackage(Packages.arc.util.serialization)
importPackage(Packages.arc.util.viewport)

//smolbot, nonunified
importPackage(Packages.smol)
importPackage(Packages.smol.js)
importPackage(Packages.smol.util)
importPackage(Packages.smol.struct)
importPackage(Packages.smol.console)
importPackage(Packages.smol.commands)

//rhino, common
importPackage(Packages.rhino)

//kotlin, coroutines
importPackage(Packages.kotlin.coroutines)

function extend( /*Base, ..., def*/ ) {
    const Base = arguments[0]
    const def = arguments[arguments.length - 1]
    //swap order from Base, def, ... to Base, ..., def
    const args = [Base, def].concat(Array.from(arguments).splice(1, arguments.length - 2))

    //forward constructor arguments to new JavaAdapter
    const instance = JavaAdapter.apply(null, args)
    //JavaAdapter only overrides functions; set fields too
    for (var i in def) {
        if (typeof(def[i]) != "function") {
            instance[i] = def[i]
        }
    }
    return instance
}

//accessors for singletons
const Vars = Packages.smol.Vars.INSTANCE
const Printings = Packages.smol.console.Printings.INSTANCE
const Args = Packages.smol.commands.Args.INSTANCE
const Commands = Packages.smol.commands.Commands.INSTANCE
