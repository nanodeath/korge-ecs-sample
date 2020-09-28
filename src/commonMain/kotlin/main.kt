import com.soywiz.klock.*
import com.soywiz.korge.*
import com.soywiz.korge.view.*
import com.soywiz.korim.color.*
import com.soywiz.korim.format.*
import com.soywiz.korio.file.std.*
import com.soywiz.korma.geom.*
import com.soywiz.korma.interpolation.*
import org.korge.ecs.World

// Run by invoking the `runJvm` target.
// If you're on Linux and get a blank gray window, try setting the MESA_LOADER_DRIVER_OVERRIDE=i965 env variable.
suspend fun main() = Korge(width = 512, height = 512, bgcolor = Colors["#2b2b2b"]) {
    val minDegrees = (-16).degrees
    val maxDegrees = (+16).degrees

    val image = image(resourcesVfs["korge.png"].readBitmap()) {
        rotation = maxDegrees
        anchor(.5, .5)
        scale(.8)
        position(256, 256)
    }

    val world = World().apply {
        registerComponentType<AnimationComponent>()
        registerComponentType<ViewComponent>()
        createEntity {
            addComponent(ViewComponent(image))
            addComponent(wobbleAnimation(minDegrees, maxDegrees, 1.seconds))
        }
        AnimationSystem(this)
    }

    addUpdater { world.tick(it) }
}