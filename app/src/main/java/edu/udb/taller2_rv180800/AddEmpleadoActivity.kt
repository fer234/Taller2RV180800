package edu.udb.taller2_rv180800

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import android.widget.Button
import android.widget.TextView
import edu.udb.taller2_rv180800.datos.Empleado


class AddEmpleadoActivity : AppCompatActivity() {
    private var edtSalariob: EditText? = null
    private var edtSalarion: EditText? = null
    private var edtNombre: EditText? = null
    private var key = ""
    private var nombre = ""
    private var salariob = ""
    private var salarion = ""
    private var accion = ""
    private lateinit var  database:DatabaseReference
    lateinit var edtSalario : EditText
    lateinit var btncalcular : Button
    lateinit var txtResultado : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_empleado)

        edtSalario = findViewById(R.id.edtSalariob)
        btncalcular = findViewById(R.id.btncalcular)
        txtResultado = findViewById(R.id.txtResultado)

        btncalcular.setOnClickListener(View.OnClickListener {
            val salario = Integer.parseInt(edtSalario.text.toString())
            txtResultado.setText("Su salario neto es: " + calculo(salario))
        })

        inicializar()
    }
    private fun inicializar() {
        edtNombre = findViewById<EditText>(R.id.edtNombre)
        edtSalariob = findViewById<EditText>(R.id.edtSalariob)
        edtSalarion = findViewById<EditText>(R.id.edtSalarion)

        val edtNombre = findViewById<EditText>(R.id.edtNombre)
        val edtSalariob = findViewById<EditText>(R.id.edtSalariob)
        val edtSalarion = findViewById<EditText>(R.id.edtSalarion)

        // Obtención de datos que envia actividad anterior
        val datos: Bundle? = intent.getExtras()
        if (datos != null) {
            key = datos.getString("key").toString()
        }
        if (datos != null) {
            edtSalariob.setText(intent.getStringExtra("salariob").toString())
        }
        if (datos != null) {
            edtSalarion.setText(intent.getStringExtra("salarion").toString())
        }
        if (datos != null) {
            edtNombre.setText(intent.getStringExtra("nombre").toString())
        }
        if (datos != null) {
            accion = datos.getString("accion").toString()
        }

    }


    fun guardar(v: View?) {
        val nombre: String = edtNombre?.text.toString()
        val salariob: String = edtSalariob?.text.toString()
        val salarion: String = edtSalarion?.text.toString()

        database=FirebaseDatabase.getInstance().getReference("empleados")

        // Se forma objeto persona
        val empleado = Empleado(salariob, salarion, nombre)

        if (accion == "a") { //Agregar registro
            database.child(nombre).setValue(empleado).addOnSuccessListener {
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
            val empleadosValues = empleado.toMap()
            val childUpdates = hashMapOf<String, Any>(
                "$nombre" to empleadosValues
            )
            database.updateChildren(childUpdates)
            Toast.makeText(this,"Se actualizo con exito", Toast.LENGTH_SHORT).show()
        }
        finish()
    }

    fun cancelar(v: View?) {
        finish()
    }

    fun calculo(sal: Int) : Int{
        var costo = 60
        return sal-costo
    }
}