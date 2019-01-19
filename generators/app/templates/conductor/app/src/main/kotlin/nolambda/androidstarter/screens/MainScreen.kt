package <%= appPackage %>.screens

import com.esafirm.conductorextra.common.onEvent
import kotlinx.android.synthetic.main.controller_main.*
import <%= appPackage %>.R
import <%= appPackage %>.R.id.main_txt_hello
import <%= appPackage %>.commons.AbsScreen
import <%= appPackage %>.navigator.AppNavigator
import javax.inject.Inject

class MainScreen : AbsScreen() {

    init {
        onEvent(onPostCreateView = { remover ->
            component.inject(this)
            remover()
        })
        screenView = xml(R.layout.controller_main)
    }

    @Inject lateinit var navigator: AppNavigator

    override fun render() {
        main_txt_hello.setOnClickListener {
            navigator.goToDetail()
        }
    }
}
