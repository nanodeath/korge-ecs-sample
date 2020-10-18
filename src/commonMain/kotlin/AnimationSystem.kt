import com.soywiz.klock.TimeSpan
import com.soywiz.korma.geom.minus
import com.soywiz.korma.geom.plus
import com.soywiz.korma.geom.times
import org.korge.ecs.Entity
import org.korge.ecs.IteratingEntitySystem
import org.korge.ecs.World

class AnimationSystem(world: World) : IteratingEntitySystem(world, AnimationComponent::class, ViewComponent::class) {
    private val animMapper = world.componentMapperFor<AnimationComponent>()
    private val viewMapper = world.componentMapperFor<ViewComponent>()

    override fun processEntity(dt: TimeSpan, entity: Entity) {
        val anim = animMapper[entity]
        var timeRemaining = dt.milliseconds
        var animationJustStarted = anim.idx == 0 && anim.animations[0].elapsed == 0.0
        var animationFinished = false
        while (true) {
            val current = anim.animations[anim.idx]
            current.elapsed += timeRemaining
            if (current.elapsed >= current.timeToComplete.milliseconds) {
                val overflow = current.elapsed - current.timeToComplete.milliseconds
                current.elapsed = 0.0
                timeRemaining = overflow
                if (anim.idx == anim.animations.lastIndex) {
                    if (anim.loop) {
                        anim.idx = 0
                    } else {
                        animationFinished = true
                        animMapper.removeComponent(entity)
                    }
                } else {
                    anim.idx++
                }
                animationJustStarted = true
            } else {
                break
            }
        }

        anim.animations[anim.idx].apply {
            when (this) {
                is RotationAnimation -> {
                    val view = viewMapper[entity].view
                    if (animationJustStarted) {
                        initial = view.rotation
                    }
                    val fraction = if (animationFinished) 1.0 else elapsed / timeToComplete.milliseconds
                    val angle = initial + (target - initial) * easing(fraction)
                    view.rotation = angle
                }
            }
        }
    }
}