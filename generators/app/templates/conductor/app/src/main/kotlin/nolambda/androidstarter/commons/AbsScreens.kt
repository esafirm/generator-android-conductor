package <%= appPackage %>.commons

import android.os.Bundle
import com.bluelinelabs.conductor.Controller
import com.esafirm.conductorextra.common.onEvent
import <%= appPackage %>.di.components.ActivityComponent
import <%= appPackage %>.di.components.ControllerComponent
import <%= appPackage %>.di.helpers.ComponentReflectionInjector
import <%= appPackage %>.di.helpers.HasComponent
import <%= appPackage %>.di.modules.ControllerModule
import <%= appPackage %>.di.plugins.Plugin
import <%= appPackage %>.di.plugins.PluginManager
import nolambda.screen.Presenter
import nolambda.screen.Screen
import nolambda.screen.StatefulScreen

typealias InitBlock = () -> Unit

abstract class AbsStatefulScreen<S, P : Presenter<S>> : StatefulScreen<S, P> {

    private val injector by lazy {
        ComponentReflectionInjector(ControllerComponent::class.java, makeComponent(*usePlugins()))
    }

    private val injectOnce by lazy { injector.inject(this@AbsStatefulScreen) }

    constructor() : super()
    constructor(bundle: Bundle?) : super(bundle)

    protected open fun usePlugins(): Array<Plugin> = emptyArray()

    protected open fun useInject(): Boolean = true

    init {
        onEvent(onPreContextAvailable = { remover ->
            if (useInject()) {
                injectOnce
            }
            remover()
        })
    }
}

abstract class AbsScreen : Screen {
    protected var onInit: InitBlock? = null
    private val initLazy by lazy { onInit?.invoke() }

    constructor() : super()
    constructor(bundle: Bundle?) : super(bundle)

    init {
        onEvent(onPreContextAvailable = { remover ->
            initLazy
            remover()
        })
    }
}

@Suppress("UNCHECKED_CAST")
internal fun Controller.makeComponent(vararg plugins: Plugin): ControllerComponent {
    if (activity == null) {
        throw IllegalStateException("Not attached to Activity")
    }
    if ((activity is HasComponent<*>).not()) {
        throw IllegalStateException("Activity should implement HasComponent<Component>")
    }
    return activity.let { it as HasComponent<ActivityComponent> }
            .getComponent()
            .controllerComponent()
            .bindController(this)
            .bindPluginManager(PluginManager(*plugins))
            .controllerModule(ControllerModule())
            .build()
}



