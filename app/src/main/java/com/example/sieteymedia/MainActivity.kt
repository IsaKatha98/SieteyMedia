package com.example.sieteymedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.example.sieteymedia.databinding.JuegoBinding
import com.example.sieteymedia.databinding.NombresBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    //Suma de los puntos de cada jugador
    var puntosJ1 = 0.0
    var puntosJ2 = 0.0

    val maxPuntos = 7.5

    var totalJ1=0
    var totalJ2=0

    var ganador=""

    var nomJ1=""
    var nomJ2=""

    //var otra= true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Declaramos las vistas
        val primera = NombresBinding.inflate(layoutInflater)

        setContentView(primera.root)

        //Hacemos un binding para el boton de inicio.
        var boton = primera.button

        //Cuando pulsamos el botón
        boton.setOnClickListener() {

            //Creamos las variables para guardar los nombres de los jugadores
            var jugador1 = findViewById<EditText>(R.id.nombreJ1).text.toString()
            var jugador2 = findViewById<EditText>(R.id.nombreJ2).text.toString()

            nomJ1=jugador1
            nomJ2=jugador2

            //si alguno de los nombres está vacío aparece un mensaje.
            if (jugador1.isEmpty() || jugador2.isEmpty()) {

                Toast.makeText(
                    applicationContext,
                    "Ambos jugadores deben tener nombre",
                    Toast.LENGTH_LONG
                ).show()

            } else {

                //Llamamos a la función juego.
                juego()
            }

        }

    }

    fun juego() {

        Log.i("actividad","funciín juego")
        //Declaramos las vistas
        val segunda = JuegoBinding.inflate(layoutInflater)


        //Llamamos a la segunda vista
        setContentView(segunda.root)

        Log.i("actividad","primer bucle while" )

        //Mostramos los puntos de cada jugador según el nombre.
        //Sobreescribrimos el textView de los puntos.
        segunda.puntosJ1.text = "Puntos de $nomJ1: "
        segunda.puntosJ2.text = "Puntos de $nomJ2: "
        segunda.puntosTotalJ1.text = totalJ1.toString()
        segunda.puntosTotalJ2.text = totalJ2.toString()

        //Mostramos que es el turno del jugador 1.
        segunda.turno.text = "Es el turno de $nomJ1"

        //Mostramos los puntos del jugador 1.
        segunda.numPuntosJ1.text = "$puntosJ1"

        //Desactivamos los botones del jugador2.
        segunda.plantarseJ2.isEnabled = false
        segunda.cartaJ2.isEnabled = false

        segunda.cartaJ1.setOnClickListener {

            var salir = false

            while (salir == false) {

                Log.i("actividad", "entra en el bucle while")
                if (puntosJ1 <= maxPuntos) {
                    //le sumamos el resultado de la función cartaRandom
                    puntosJ1 += cartaRandom()

                    //Se muestran los puntos por pantalla
                    segunda.numPuntosJ1.text = "$puntosJ1"

                    if (puntosJ1 > maxPuntos) {
                        Toast.makeText(
                            applicationContext,
                            "$nomJ1 te has pasado",
                            Toast.LENGTH_LONG
                        ).show()
                        //Mostramos el mensaje de turno de jugador 2.
                        segunda.turno.text = "Es el turno de $nomJ2"

                        //Mostramos los puntos del jugador 2.
                        segunda.numPuntosJ2.text = "$puntosJ2"

                        //Desactivamos los botones del jug1.
                        segunda.cartaJ1.isEnabled = false
                        segunda.plantarseJ1.isEnabled = false

                        //Activamos los botones del jug2.
                        segunda.cartaJ2.isEnabled = true
                        segunda.plantarseJ2.isEnabled = true
                    }
                    salir = true

                }

            }

        }

        segunda.plantarseJ1.setOnClickListener {

            //Mostramos el mensaje de turno de jugador 2.
            segunda.turno.text = "Es el turno de $nomJ2"

            //Mostramos los puntos del jugador 2.
            segunda.numPuntosJ2.text = "$puntosJ2"

            //Desactivamos los botones del jug1.
            segunda.cartaJ1.isEnabled = false
            segunda.plantarseJ1.isEnabled = false

            //Activamos los botones del jug2.
            segunda.cartaJ2.isEnabled = true
            segunda.plantarseJ2.isEnabled = true

        }

        //tURNO DEL JUGADOR 2
        segunda.cartaJ2.setOnClickListener {

            var salir = false

            while (salir == false) {

                Log.i("actividad", "entra en el bucle while")
                if (puntosJ2 <= maxPuntos) {
                    //le sumamos el resultado de la función cartaRandom
                    puntosJ2 += cartaRandom()

                    //Se muestran los puntos por pantalla
                    segunda.numPuntosJ2.text = "$puntosJ2"

                    if (puntosJ2 > maxPuntos) {
                        Toast.makeText(
                            applicationContext,
                            "$nomJ2 te has pasado",
                            Toast.LENGTH_LONG
                        ).show()

                        //Mostramos quién ha ganado.
                        segunda.turno.text = ganador(nomJ1, nomJ2)

                        ganador = ganador(nomJ1, nomJ2)

                        //Llamamos a la función reinicio.
                        //reinicio()


                    }
                    salir = true

                }

            }

        }

        segunda.plantarseJ2.setOnClickListener {

            //Mostramos quién ha ganado
            segunda.turno.text = ganador(nomJ1, nomJ2)

            ganador = ganador(nomJ2, nomJ1)

            //Llamamos a la función reinicio.
            // reinicio()

        }
    }

    /**
     * La función cartaRandom saca una carta con puntuación entre 0.5 y 7
     * y devuelve los puntos de esa carta.
     */
    fun cartaRandom(): Double {

        var puntosCarta = Random.nextInt(1, 11)
        var res = 0.0

        //Si el número randome está entre 8 y 10 valdrá 0.5
        if (puntosCarta in 8..10) {
            res = 0.5
        } else {
            res = puntosCarta.toDouble()
        }

        //Devolvemos res.
        return res
    }

    /**
     * Función que declara el ganador
     */
    fun ganador(jugador1:String, jugador2:String): String {

        Log.i("actividad", "entra en la funcion ganador")

        var res=""

        if (puntosJ1 > puntosJ2 && puntosJ1 <= maxPuntos && puntosJ2 <= maxPuntos) {
            //Gana el J1

            res="Ha ganado $jugador1"
            totalJ1++

        } else if (puntosJ1 <= maxPuntos && puntosJ2 > maxPuntos) {
            //gana el J1.
            res="Ha ganado $jugador1"
            totalJ1++

        }else if (puntosJ2 > puntosJ1 && puntosJ1 <= maxPuntos && puntosJ2 <= maxPuntos) {
            //gana el J2.
            Log.i("actividad","gana el $jugador2")
            res="Ha ganado $jugador2"
            totalJ2++
        } else if (puntosJ1 == puntosJ2 && puntosJ1 <= maxPuntos && puntosJ2 <= maxPuntos) {
            //han empatado
            res = "Ha habido empate"
            totalJ1++
            totalJ2++
        }else if (puntosJ1 > maxPuntos && puntosJ2 > maxPuntos) {
            //Ambos hugadores han perdido.
            res= "Ambos jugadores han perdido"
        }

        return res

    }

    /**
     * Función que controla el reinicio de la vista o si se sale del juego.
     */
    /*fun reinicio ():Boolean {

        Log.i("actividad","se mete en la función reinicio")

        //Desaparecen los botones.
        val tercera = OtraBinding.inflate(layoutInflater)

        //Ponemos la tercer vista
        setContentView(tercera.root)

        tercera.turno.text=ganador

        //jugador1.
        tercera.noJ2.isEnabled=false
        tercera.siJ2.isEnabled=false

        //Si el primer jugador si quiere jugar
        tercera.siJ1.setOnClickListener {

            //Desbloquea los botones del jugador2.
            tercera.noJ2.isEnabled=true
            tercera.siJ2.isEnabled=true
            tercera.noJ1.isEnabled=false
            tercera.siJ1.isEnabled=false

            tercera.preguntaJ1.text="Esperando al otro jugador..."

            tercera.siJ2.setOnClickListener{

                otra=true

            }

            //Si el segundo jugador no quiere jugar
            tercera.noJ2.setOnClickListener{

                otra=false
            }

        }

        //Si el primer jugador no quiere jugar
        tercera.noJ1.setOnClickListener {

            otra=false

        }

        return otra

    }*/
    }


