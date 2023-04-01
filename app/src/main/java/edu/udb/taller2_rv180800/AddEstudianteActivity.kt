package edu.udb.taller2_rv180800

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import edu.udb.taller2_rv180800.datos.Estudiante

class AddEstudianteActivity : AppCompatActivity() {
    private var edtPromedio: EditText? = null
    private var edtNombre: EditText? = null
    private var key = ""
    private var nombre = ""
    private var promedio = ""
    private var accion = ""
    lateinit var edtNota1 : EditText
    lateinit var edtNota2 : EditText
    lateinit var edtNota3 : EditText
    lateinit var edtNota4 : EditText
    lateinit var edtNota5 : EditText
    lateinit var btnprom : Button
    lateinit var txtResultado : TextView
    private lateinit var  database:DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_estudiante)
        edtNota1 = findViewById(R.id.edtNota1)
        edtNota2 = findViewById(R.id.edtNota2)
        edtNota3 = findViewById(R.id.edtNota3)
        edtNota4 = findViewById(R.id.edtNota4)
        edtNota5 = findViewById(R.id.edtNota5)
        btnprom = findViewById(R.id.btnprom)
        txtResultado = findViewById(R.id.txtResultado)

        btnprom.setOnClickListener(View.OnClickListener {
            val not1 = Integer.parseInt(edtNota1.text.toString())
            val not2 = Integer.parseInt(edtNota2.text.toString())
            val not3 = Integer.parseInt(edtNota3.text.toString())
            val not4 = Integer.parseInt(edtNota4.text.toString())
            val not5 = Integer.parseInt(edtNota5.text.toString())
            txtResultado.setText("El promedio es: " + prom(not1, not2, not3, not4, not5))
        })
        inicializar()
    }
    private fun inicializar() {
        edtNombre = findViewById<EditText>(R.id.edtNombre)
        edtPromedio = findViewById<EditText>(R.id.edtPromedio)

        val edtNombre = findViewById<EditText>(R.id.edtNombre)
        val edtPromedio = findViewById<EditText>(R.id.edtPromedio)

        // Obtención de datos que envia actividad anterior
        val datos: Bundle? = intent.getExtras()
        if (datos != null) {
            key = datos.getString("key").toString()
        }
        if (datos != null) {
            edtPromedio.setText(intent.getStringExtra("promedio").toString())
        }
        if (datos != null) {
            edtNombre.setText(intent.getStringExtra("nombre").toString())
        }
        if (datos != null) {
            accion = datos.getString("accion").toString()
        }

    }

    fun prom(n1: Int, n2: Int, n3: Int, n4: Int, n5: Int) : Int{
        val sumaf = n1+n2+n3+n4+n5
        return sumaf / 5
    }

    fun guardar(v: View?) {
        val nombre: String = edtNombre?.text.toString()
        val promedio: String = edtPromedio?.text.toString()

        database=FirebaseDatabase.getInstance().getReference("estudiantes")

        // Se forma objeto persona
        val estudiante = Estudiante(promedio, nombre)

        if (accion == "a") { //Agregar registro
            database.child(nombre).setValue(estudiante).addOnSuccessListener {
                Toast.makeText(this,"Se guardo con exito", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener{
                Toast.makeText(this,"Failed ", Toast.LENGTH_SHORT).show()
            }
        } else  // Editar registro
        {
            val key = database.child("nombre").push().key
            if (key == null) {
                Toast.makeText(this,"Llave vacia", Toast.LENGTH_SHORT).show()
            }
            val estudiantesValues = estudiante.toMap()
            val childUpdates = hashMapOf<String, Any>(
                "$nombre" to estudiantesValues
            )
            database.updateChildren(childUpdates)
            Toast.makeText(this,"Se actualizo con exito", Toast.LENGTH_SHORT).show()
        }
        finish()
    }

    fun cancelar(v: View?) {
        finish()
    }
}