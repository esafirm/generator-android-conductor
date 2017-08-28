package <%= appPackage %>.di.components

import dagger.Subcomponent
import <%= appPackage %>.di.modules.ControllerModule
import <%= appPackage %>.screens.ApiExampleController
import <%= appPackage %>.screens.DetailController
import <%= appPackage %>.screens.MainController

@Subcomponent(modules = arrayOf(ControllerModule::class))
interface ControllerComponent {

    /* --------------------------------------------------- */
    /* > Injects */
    /* --------------------------------------------------- */

    fun inject(mainController: MainController)
    fun inject(detailController: DetailController)
    fun inject(apiExampleController: ApiExampleController)

    /* --------------------------------------------------- */
    /* > Builders */
    /* --------------------------------------------------- */

    @Subcomponent.Builder
    interface Builder {
        fun controllerModule(controllerModule: ControllerModule): ControllerComponent.Builder
        fun build(): ControllerComponent
    }
}
