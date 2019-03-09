package <%= appPackage %>.di.plugins

import kotlin.reflect.KClass

class PluginManager(vararg plugins: Plugin) {

    private val pluginMap = plugins.map { it::class to it }.toMap()

    @Suppress("UNCHECKED_CAST")
    fun <T : Plugin> get(klass: KClass<T>): T {
        return pluginMap.get(klass) as T
    }
}
