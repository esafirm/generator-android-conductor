package <%= appPackage %>.di.components

import dagger.Subcomponent
import <%= appPackage %>.di.modules.ControllerModule
import <%= appPackage %>.screens.ApiExampleStatefulScreen
import <%= appPackage %>.screens.DetailScreen
import <%= appPackage %>.screens.MainScreen

@Subcomponent(modules = arrayOf(ControllerModule::class))
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
        fun controllerModule(controllerModule: ControllerModule): ControllerComponent.Builder
        fun build(): ControllerComponent
    }
}
