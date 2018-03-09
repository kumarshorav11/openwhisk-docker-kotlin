package openwhisk

import io.javalin.Javalin
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

data class InputObject(val parameter1: String  = "", val parameter2: String  = "")
@JsonIgnoreProperties(ignoreUnknown = true)
data class InputBody(val value: InputObject = InputObject())
data class OutputObject(val param1: String, val param2: String)

fun main(args: Array<String>) {
    val app = Javalin.create().apply {
        port(8080)
        exception(Exception::class.java) { e, ctx -> e.printStackTrace() }
    }.start()

    app.post("/init") { ctx -> ctx.status(200) }

    app.post("/run") { ctx ->
        val body = ctx.bodyAsClass(InputBody::class.java)
        val inputObject = body.value

        val outputObject = OutputObject("value1 " + inputObject.parameter1, "value2")
        ctx.json(outputObject)
        ctx.status(200)
    }
}
