package org.jsantamariap.eh_ho.data

import android.content.Context
import com.android.volley.NetworkError
import com.android.volley.Request
import com.android.volley.ServerError
import com.android.volley.toolbox.JsonObjectRequest
import org.jsantamariap.eh_ho.R
import org.json.JSONObject

/*
object: clase anómina, viene a ser un singleton. Es decir una única instancia de una clase, teniendo su propio
contexto estático.
 */
object TopicsRepo {

    var topics: MutableList<Topic> = mutableListOf()

    fun getTopic(id: String): Topic? = topics.find {
        it.id == id
    }

    fun addTopic(
        context: Context,
        model: CreateTopicModel,
        onSuccess: (CreateTopicModel) -> Unit,
        onError: (RequestError) -> Unit
    ) {
        val username = UserRepo.getUsername(context)
        val request = PostRequest(
            Request.Method.POST,
            ApiRoutes.createTopic(),
            model.toJson(),
            username,
            {
                onSuccess(model)
            },
            {
                it.printStackTrace()

                // para ver los tipos de errores es buena idea probar con Postman
                val requestError =
                    if (it is ServerError && it.networkResponse.statusCode == 422) {
                        // primera opción
                        // RequestError(it, messageResId = R.string.error_duplicated_topic)
                        // segunda opción, sacar los errores que devuelve el servidor
                        val bodyResponse = String(it.networkResponse.data, Charsets.UTF_8)
                        val jsonError = JSONObject(bodyResponse)
                        val errors = jsonError.getJSONArray("errors")
                        var errorMessage = ""

                        for (i in 0 until errors.length()) {
                            errorMessage += "${errors[i]} "
                        }

                        RequestError(it, message = errorMessage)

                    } else if (it is NetworkError) {
                        RequestError(it, messageResId = R.string.error_not_internet)
                    } else {
                        RequestError(it)
                    }
                onError(requestError)
            }
        )

        ApiRequestQueue
            .getRequestQueue(context)
            .add(request)
    }

    fun getTopics(
        context: Context,
        onSuccess: (List<Topic>) -> Unit,
        onError: (RequestError) -> Unit
    ) {
        val request = JsonObjectRequest(
            Request.Method.GET,
            ApiRoutes.getTopics(),
            null,
            {
                val list = Topic.parseTopicList(it)
                topics = list.toMutableList()
                onSuccess(list)
            },
            {
                it.printStackTrace()
                val requestError =
                    if (it is NetworkError)
                        RequestError(it, messageResId = R.string.error_not_internet)
                    else
                        RequestError(it)
                onError(requestError)
            }
        )

        ApiRequestQueue
            .getRequestQueue(context)
            .add(request)
    }

    fun getPosts(
        id: String,
        context: Context,
        onSuccess: (List<Post>) -> Unit,
        onError: (RequestError) -> Unit
    ) {
        val request = JsonObjectRequest(
            Request.Method.GET,
            ApiRoutes.getPosts(id),
            null,
            {
                val list = Post.parsePostList(it)
                list.size
                onSuccess(list)
            },
            {
                it.printStackTrace()
                val requestError =
                    if (it is NetworkError)
                        RequestError(it, messageResId = R.string.error_not_internet)
                    else
                        RequestError(it)
                onError(requestError)
            }
        )

        ApiRequestQueue
            .getRequestQueue(context)
            .add(request)
    }

    fun addPost(
        context: Context,
        model: CreatePostModel,
        onSuccess: (CreatePostModel) -> Unit,
        onError: (RequestError) -> Unit
    ) {
        val username = UserRepo.getUsername(context)
        val request = PostRequest(
            Request.Method.POST,
            ApiRoutes.createPost(),
            model.toJson(),
            username,
            {
                onSuccess(model)
            },
            {
                it.printStackTrace()

                // para ver los tipos de errores es buena idea probar con Postman
                val requestError =
                    if (it is ServerError && it.networkResponse.statusCode == 422) {
                        // primera opción
                        // RequestError(it, messageResId = R.string.error_duplicated_topic)
                        // segunda opción, sacar los errores que devuelve el servidor
                        val bodyResponse = String(it.networkResponse.data, Charsets.UTF_8)
                        val jsonError = JSONObject(bodyResponse)
                        val errors = jsonError.getJSONArray("errors")
                        var errorMessage = ""

                        for (i in 0 until errors.length()) {
                            errorMessage += "${errors[i]} "
                        }

                        RequestError(it, message = errorMessage)

                    } else if (it is NetworkError) {
                        RequestError(it, messageResId = R.string.error_not_internet)
                    } else {
                        RequestError(it)
                    }
                onError(requestError)
            }
        )

        ApiRequestQueue
            .getRequestQueue(context)
            .add(request)
    }
}