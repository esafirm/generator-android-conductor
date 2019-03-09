package <%= appPackage %>.di.helpers

import java.lang.reflect.Method
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class ComponentReflectionInjector<T>(private val componentClass: Class<T>, val component: T) {
    private val methods: HashMap<Class<*>, Method>

    init {
        this.methods = getMethods(componentClass)
    }

    fun inject(target: Any) {

        var targetClass: Class<*>? = target.javaClass
        var method = methods[targetClass]
        while (method == null && targetClass != null) {
            targetClass = targetClass.superclass
            method = methods[targetClass]
        }

        if (method == null)
            throw RuntimeException(
                    String.format("No %s injecting method exists in %s component",
                            target.javaClass,
                            componentClass)
            )

        try {
            method.invoke(component, target)
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    }

    companion object {

        private val cache = ConcurrentHashMap<Class<*>, HashMap<Class<*>, Method>>()

        private fun getMethods(componentClass: Class<*>): HashMap<Class<*>, Method> {
            var methods = cache[componentClass]
            if (methods == null) {
                synchronized(cache) {
                    methods = cache[componentClass]
                    if (methods == null) {
                        methods = HashMap()
                        for (method in componentClass.methods) {
                            val params = method.parameterTypes
                            if (params.size == 1)
                                methods!![params[0]] = method
                        }
                        cache[componentClass] = methods!!
                    }
                }
            }
            return methods!!
        }
    }
}
