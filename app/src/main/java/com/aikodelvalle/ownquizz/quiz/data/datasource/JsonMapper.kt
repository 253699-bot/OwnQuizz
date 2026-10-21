package com.aikodelvalle.ownquizz.quiz.data.datasource

import org.json.JSONArray
import org.json.JSONObject

object JsonMapper {

    fun aLista(array: JSONArray): List<Pregunta> =
        (0 until array.length()).map { i ->
            val o = array.getJSONObject(i)
            val opciones = o.getJSONArray("opciones")
            Pregunta(
                id = o.getInt("id"),
                enunciado = o.getString("enunciado"),
                opciones = (0 until opciones.length()).map { opciones.getString(it) },
                indiceCorrecto = o.getInt("indiceCorrecto"),
                categoria = o.getString("categoria")
            )
        }

    fun aJson(lista: List<Pregunta>): String {
        val array = JSONArray()
        lista.forEach { p ->
            array.put(JSONObject().apply {
                put("id", p.id)
                put("enunciado", p.enunciado)
                put("opciones", JSONArray(p.opciones))
                put("indiceCorrecto", p.indiceCorrecto)
                put("categoria", p.categoria)
            })
        }
        return array.toString()
    }
}