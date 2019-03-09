package <%= appPackage %>.di.components

import com.bluelinelabs.conductor.Controller
import dagger.BindsInstance
import dagger.Subcomponent
import <%= appPackage %>.di.modules.ControllerModule
import <%= appPackage %>.di.plugins.PluginManager
import <%= appPackage %>.screens.ApiExampleStatefulScreen
import <%= appPackage %>.screens.DetailScreen
import <%= appPackage %>.screens.MainScreen

@Subcomponent(modules = [ControllerModule::class])
interface ControllerComponent {

    /* --------------------------------------------------- */
    /* > Injects */
    /* --------------------------------------------------- */

    fun inject(mainController: MainScreen)
    fun inject(detailController: DetailScreen)
    fun inject(apiExampleController: ApiExampleStatefulScreen)

    /* --------------------------------------------------- */
    /* > Builders */
    /* --------------------------------------------------- */

    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun bindController(controller: Controller) : ControllerComponent.Builder

        @BindsInstance
        fun bindPluginManager(pluginManager: PluginManager): ControllerComponent.Builder

        fun controllerModule(controllerModule: ControllerModule): ControllerComponent.Builder

        fun build(): ControllerComponent
    }
}
