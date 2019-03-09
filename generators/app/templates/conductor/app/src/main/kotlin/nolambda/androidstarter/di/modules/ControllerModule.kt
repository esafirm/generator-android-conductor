package <%= appPackage %>.di.modules

import dagger.Module
import <%= appPackage %>.screens.DetailModule

@Module(includes = [DetailModule::class])
class ControllerModule

