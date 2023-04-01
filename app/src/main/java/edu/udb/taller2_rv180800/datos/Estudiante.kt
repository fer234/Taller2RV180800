package edu.udb.taller2_rv180800.datos

class Estudiante {
    fun key(key: String?) {
    }

    var promedio: String? = null
    var nombre: String? = null
    var key: String? = null
    var per: MutableMap<String, Boolean> = HashMap()

    constructor() {}
    constructor(promedio: String?, nombre: String?) {
        this.promedio = promedio
        this.nombre = nombre
    }

    fun toMap(): Map<String, Any?> {
        return mapOf(
            "promedio" to promedio,
            "nombre" to nombre,
            "key" to key,
            "per" to per
        )
    }
}