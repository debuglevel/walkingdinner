package de.debuglevel.walkingdinner.rest.dinner

import de.debuglevel.walkingdinner.repository.DinnerRepository
import de.debuglevel.walkingdinner.repository.MongoDatabase
import de.debuglevel.walkingdinner.rest.responsetransformer.JsonTransformer
import mu.KotlinLogging
import spark.ModelAndView
import spark.kotlin.RouteHandler
import spark.template.mustache.MustacheTemplateEngine
import java.util.*

object DinnerController {
    private val logger = KotlinLogging.logger {}

    /*
    fun postOne(): RouteHandler.() -> Any {
        return {
            logger.debug("Got POST request on '/dinners' with content-type '${request.contentType()}'")

            if (!request.contentType().startsWith("multipart/form-data")) {
                logger.debug { "Declining POST request with unsupported content-type '${request.contentType()}'" }
                throw Exception("Content-Type ${request.contentType()} not supported.")
            }
        }
    }*/

    fun getOneHtml(): RouteHandler.() -> String {
        return {
            val model = HashMap<String, Any>()
            MustacheTemplateEngine().render(ModelAndView(model, "dinner/show.html.mustache"))
        }
    }

    fun getOneJson(): RouteHandler.() -> String {
        return {
            type(contentType = "application/json")
            val dinnerId = request.params(":dinnerId")

            try {
                val dinner = DinnerRepository.get(dinnerId)
                JsonTransformer.render(dinner)
            } catch (e: MongoDatabase.ObjectNotFoundException) {
                status(404)
                "{'message':'dinner not found'}"
            }
        }
    }

    fun getListHtml(): RouteHandler.() -> String {
        return {
            val model = HashMap<String, Any>()
            MustacheTemplateEngine().render(ModelAndView(model, "dinner/list.html.mustache"))
        }
    }

//    fun getListJson(): RouteHandler.() -> String {
//        return {
//            type(contentType = "application/json")
//            val dinners = DinnerRepository.getAll()
//
//            JsonTransformer.render(dinners)
//        }
//    }

    /*
    fun getAddFormHtml(): RouteHandler.() -> String {
        return {
            logger.debug("Got GET request on '/participants'")

            val model = HashMap<String, Any>()
            MustacheTemplateEngine().render(ModelAndView(model, "participant/add.html.mustache"))
        }
    }*/
}