package cavalier.dev.routes

import io.ktor.server.application.*
import io.ktor.server.testing.*
import org.junit.Test

internal class GameRouteKtTest {
    @Test
    fun test() {
//        withTestApplication({ module()})
    }
}

fun <R> withTestApplication(
    moduleFunction: Application.() -> Unit,
    test: TestApplicationEngine.() -> R
): R {
    return withApplication(createTestEnvironment()) {
        moduleFunction(application)
        test()
    }
}