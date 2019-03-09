package <%= appPackage %>.screens

import android.os.Bundle
import android.os.Parcelable
import android.view.View
import com.esafirm.conductorextra.common.getProps
import com.esafirm.conductorextra.common.toPropsBundle
import dagger.Module
import dagger.Provides
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.controller_detail.*
import <%= appPackage %>.R
import <%= appPackage %>.commons.AbsStatefulScreen
import <%= appPackage %>.di.plugins.Plugin
import <%= appPackage %>.di.plugins.PluginManager
import <%= appPackage %>.navigator.AppNavigator
import <%= appPackage %>.network.ApiService
import nolambda.screen.Presenter
import javax.inject.Inject

class DetailPlugin(val props: DetailProps) : Plugin

@Module
class DetailModule {
    @Provides
    fun provideDetailProps(plugin: PluginManager) = plugin.get(DetailPlugin::class).props
}

@Parcelize
data class DetailProps(
        val text: String
) : Parcelable

data class DetailState(
        val prefix: String,
        val isLoading: Boolean,
        val count: Int = 0
)

class DetailPresenter @Inject constructor(private val apiService: ApiService,
                                          private val props: DetailProps) : Presenter<DetailState>() {

    override fun initialState(): DetailState = DetailState(isLoading = false, count = 0, prefix = props.text)

    override fun initPresenter() {
        setLoading(true)
    }

    fun setLoading(isLoading: Boolean) {
        setState {
            it.copy(isLoading = isLoading)
        }
    }

    fun increment() = setState {
        it.copy(count = it.count + 1)
    }
}

class DetailScreen : AbsStatefulScreen<DetailState, DetailPresenter> {

    @Inject lateinit var navigator: AppNavigator
    @Inject lateinit var presenter: DetailPresenter

    constructor(bundle: Bundle?) : super(bundle)
    constructor() : super(DetailProps("Haiii").toPropsBundle())

    override fun usePlugins(): Array<Plugin> = arrayOf(DetailPlugin(getProps()))

    override fun createView() = xml(R.layout.controller_detail)
    override fun createPresenter() = presenter

    override fun render(presenter: DetailPresenter, state: DetailState) {
        main_progress.visibility = when (state.isLoading) {
            true -> View.VISIBLE
            false -> View.GONE
        }
        main_txt_yeah.text = "${state.prefix}: ${state.count}"
        main_btn_add.setOnClickListener { presenter.increment() }
    }
}
