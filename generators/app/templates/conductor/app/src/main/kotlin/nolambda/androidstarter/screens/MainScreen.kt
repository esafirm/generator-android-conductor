package <%= appPackage %>.screens

import com.esafirm.conductorextra.common.onEvent
import kotlinx.android.synthetic.main.controller_main.*
import <%= appPackage %>.R
import <%= appPackage %>.commons.AbsScreen
import <%= appPackage %>.navigator.AppNavigator
import javax.inject.Inject

class MainScreen : AbsScreen() {

    @Inject lateinit var navigator: AppNavigator

    init {
        onEvent(onPostCreateView = { remover ->
            component.inject(this)
            remover()
        })
    }

    override fun createView() = xml(R.layout.controller_main)

    override fun render() {
        main_txt_hello.setOnClickListener {
            navigator.goToDetail()
        }
    }
}
