package edu.udb.taller2_rv180800

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*
import edu.udb.taller2_rv180800.datos.Estudiante

class EstudiantesActivity : AppCompatActivity() {
    // Ordenamiento para hacer la consultas a los datos
    var consultaOrdenada: Query = refEstudiantes.orderByChild("nombre")
    var estudiantes: MutableList<Estudiante>? = null
    var listaEstudiantes: ListView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_estudiantes)
        inicializar()
    }
    private fun inicializar() {
        val fab_agregar: FloatingActionButton = findViewById<FloatingActionButton>(R.id.fab_agregar)
        listaEstudiantes = findViewById<ListView>(R.id.ListaEstudiantes)

        // Cuando el usuario haga clic en la lista (para editar registro)
        listaEstudiantes!!.setOnItemClickListener(object : AdapterView.OnItemClickListener {
            override fun onItemClick(adapterView: AdapterView<*>?, view: View, i: Int, l: Long) {
                val intent = Intent(getBaseContext(), AddEstudianteActivity::class.java)
                intent.putExtra("accion", "e") // Editar
                intent.putExtra("key", estudiantes!![i].key)
                intent.putExtra("nombre", estudiantes!![i].nombre)
                intent.putExtra("promedio", estudiantes!![i].promedio)
                startActivity(intent)
            }
        })

        // Cuando el usuario hace un LongClic (clic sin soltar elemento por mas de 2 segundos)
        // Es por que el usuario quiere eliminar el registro
        listaEstudiantes!!.onItemLongClickListener = object : AdapterView.OnItemLongClickListener {
            override fun onItemLongClick(
                adapterView: AdapterView<*>?,
                view: View,
                position: Int,
                l: Long
            ): Boolean {
                // Preparando cuadro de dialogo para preguntar al usuario
                // Si esta seguro de eliminar o no el registro
                val ad = AlertDialog.Builder(this@EstudiantesActivity)
                ad.setMessage("Está seguro de eliminar registro?")
                    .setTitle("Confirmación")
                ad.setPositiveButton("Si"
                ) { dialog, id ->
                    estudiantes!![position].nombre?.let {
                        refEstudiantes.child(it).removeValue()
                    }
                    Toast.makeText(
                        this@EstudiantesActivity,
                        "Registro borrado!", Toast.LENGTH_SHORT
                    ).show()
                }
                ad.setNegativeButton("No", object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface, id: Int) {
                        Toast.makeText(
                            this@EstudiantesActivity,
                            "Operación de borrado cancelada!", Toast.LENGTH_SHORT
                        ).show()
                    }
                })
                ad.show()
                return true
            }
        }
        fab_agregar.setOnClickListener(View.OnClickListener { // Cuando el usuario quiere agregar un nuevo registro
            val i = Intent(getBaseContext(), AddEstudianteActivity::class.java)
            i.putExtra("accion", "a") // Agregar
            i.putExtra("key", "")
            i.putExtra("nombre", "")
            i.putExtra("promedio", "")
            startActivity(i)
        })
        estudiantes = ArrayList<Estudiante>()

        // Cambiarlo refProductos a consultaOrdenada para ordenar lista
        consultaOrdenada.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Procedimiento que se ejecuta cuando hubo algun cambio
                // en la base de datos
                // Se actualiza la coleccion de personas
                estudiantes!!.removeAll(estudiantes!!)
                for (dato in dataSnapshot.getChildren()) {
                    val estudiante: Estudiante? = dato.getValue(Estudiante::class.java)
                    estudiante?.key(dato.key)
                    if (estudiante != null) {
                        estudiantes!!.add(estudiante)
                    }
                }
                val adapter = AdaptadorEstudiante(
                    this@EstudiantesActivity,
                    estudiantes as ArrayList<Estudiante>
                )
                listaEstudiantes!!.adapter = adapter
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    companion object {
        var database: FirebaseDatabase = FirebaseDatabase.getInstance()
        var refEstudiantes: DatabaseReference = database.getReference("estudiantes")
    }
}