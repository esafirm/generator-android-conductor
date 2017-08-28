package <%= appPackage %>.di.modules

import com.bluelinelabs.conductor.Controller
import dagger.Module
import dagger.Provides

@Module
class ControllerModule(val controller: Controller) {

    @Provides fun provideController(): Controller = controller
}
