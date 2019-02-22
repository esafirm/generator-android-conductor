package <%= appPackage %>.screens

import kotlinx.android.synthetic.main.controller_detail.*
import <%= appPackage %>.R
import <%= appPackage %>.commons.AbsStatefulScreen
import <%= appPackage %>.navigator.AppNavigator
import nolambda.screen.Presenter
import javax.inject.Inject

data class DetailState(
        val isLoading: Boolean,
        val count: Int = 0
)

class DetailPresenter @Inject constructor() : Presenter<DetailState>() {

    override fun initialState(): DetailState = DetailState(isLoading = false, count = 0)

    fun increment() = setState {
        it.copy(count = it.count + 1)
    }
}

class DetailScreen : AbsStatefulScreen<DetailState, DetailPresenter>() {

    @Inject lateinit var navigator: AppNavigator
    @Inject lateinit var presenter: DetailPresenter

    init {
        onInit = {
            component.inject(this)
        }
    }

    override fun createView() = xml(R.layout.controller_detail)
    override fun createPresenter() = presenter

    override fun render(presenter: DetailPresenter, state: DetailState) {
        main_txt_yeah.text = "Counter: ${state.count}"
        main_btn_add.setOnClickListener { presenter.increment() }
    }
}
