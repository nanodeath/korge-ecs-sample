import com.soywiz.klock.TimeSpan
import com.soywiz.korma.geom.Angle
import com.soywiz.korma.interpolation.Easing
import org.korge.ecs.Component

interface Animation {
    val timeToComplete: TimeSpan
    var elapsed: Double
}

class RotationAnimation(
        override val timeToComplete: TimeSpan,
        val target: Angle,
        val easing: Easing,
        var initial: Angle = Angle(Double.NaN)
) : Animation {
    override var elapsed = 0.0
}

class AnimationComponent(
        val animations: List<Animation>,
        val loop: Boolean
) : Component {
    /** Current animation in [animations]. */
    var idx = 0
}

fun wobbleAnimation(min: Angle, max: Angle, speed: TimeSpan) =
        AnimationComponent(
                animations = listOf(
                        RotationAnimation(speed, min, Easing.EASE_IN_OUT),
                        RotationAnimation(speed, max, Easing.EASE_IN_OUT)
                ), loop = true
        )