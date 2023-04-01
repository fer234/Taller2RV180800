package edu.udb.taller2_rv180800.datos

class Empleado {
    fun key(key: String?) {
    }

    var salarion: String? = null
    var salariob: String? = null
    var nombre: String? = null
    var key: String? = null
    var per: MutableMap<String, Boolean> = HashMap()

    constructor() {}
    constructor(salarion: String?, salariob: String?, nombre: String?) {
        this.salarion = salarion
        this.salariob = salariob
        this.nombre = nombre
    }

    fun toMap(): Map<String, Any?> {
        return mapOf(
            "salarion" to salarion,
            "salariob" to salariob,
            "nombre" to nombre,
            "key" to key,
            "per" to per
        )
    }
}