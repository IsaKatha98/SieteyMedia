package com.example.sieteymedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.sieteymedia.databinding.JuegoBinding
import com.example.sieteymedia.databinding.NombresBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    //Suma de los puntos de cada jugador
    var puntosJ1=0.0
    var puntosJ2=0.0

    val maxPuntos=7.5

    var nomJ1=""
    var nomJ2=""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Declaramos las vistas
        val primera=NombresBinding.inflate(layoutInflater)
        val segunda=JuegoBinding.inflate(layoutInflater)


        setContentView(primera.root)

        //Asociamos los nombres.
        nomJ1 = primera.nombreJ1.text.toString()
        nomJ2 = primera.nombreJ2.text.toString()
        Log.i("actividad", "Nombre jug1: $nomJ1")


        //Cuando pulsamos el botón
        primera.button.setOnClickListener() {

            //si alguno de los nombres está vacío aparece un mensaje.
            if (nomJ1.isEmpty()||nomJ2.isEmpty()) {


                    Toast.makeText(applicationContext, "Ambos jugadores deben tener nombre", Toast.LENGTH_LONG)
                        .show()

            } else {
                //Lamamos a la función juego.
                juego()
                Toast.makeText(applicationContext, "gola gisa", Toast.LENGTH_LONG)
                    .show()
            }


        }

    }

    fun juego() {

        //Declaramos las vistas
        val primera=NombresBinding.inflate(layoutInflater)
        val segunda=JuegoBinding.inflate(layoutInflater)

        //Asociamos los nombres.
        nomJ1=primera.nombreJ1.text.toString()
        nomJ2=primera.nombreJ2.text.toString()

        //Llamamos a la segunda vista
        setContentView(segunda.root)

        //Mostramos los puntos de cada jugador según el nombre.
        //Sobreescribrimos el textView de los puntos.
        segunda.puntosJ1.text="Puntos de:$nomJ1"
        segunda.puntosJ2.text="Puntos de:$nomJ2"

        //Mostramos que es el turno del jugador 1.
        segunda.turno.text="Es el turno de $nomJ1"

        //Mostramos los puntos del jugador 1.
        segunda.numPuntosJ1.text="$puntosJ1"

        //Desactivamos los botones del jugador2.
        segunda.plantarseJ2.isEnabled=false
        segunda.cartaJ2.isEnabled=false

        //Llamamos a la función del turnoJ1
        segunda.cartaJ1.setOnClickListener{
            cartaJug1()
        }

        segunda.plantarseJ1.setOnClickListener{
            plantarseJ1()
        }

    }

    /**
     * Función que controla el turno del jugador 1.
     */
    fun cartaJug1 () {

        //Declaramos las vistas
        val primera=NombresBinding.inflate(layoutInflater)
        val segunda=JuegoBinding.inflate(layoutInflater)


        if (puntosJ1<=maxPuntos){

            //le sumamos el resultado de la función cartaRandom
            puntosJ1 += cartaRandom()

            //Se muestran los puntos por pantalla
            segunda.numPuntosJ1.text="$puntosJ1"

        } else{

            val toast=  Toast.makeText(applicationContext, "$nomJ1 te has pasado", Toast.LENGTH_LONG)
                .show()
            //Llamamos a la función de plantarse.
            plantarseJ1()

            }
        }


    fun plantarseJ1() {

        //Declaramos las vistas
        val primera=NombresBinding.inflate(layoutInflater)
        val segunda=JuegoBinding.inflate(layoutInflater)


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

        //Llamamos a la función de turnojug2
        segunda.cartaJ2.setOnClickListener {
            cartaJug2()
        }

        segunda.plantarseJ2.setOnClickListener{

            //LLamamos a la función de plantarseJ2.
        }
    }

        fun cartaJug2() {

            //Declaramos las vistas
            val primera=NombresBinding.inflate(layoutInflater)
            val segunda=JuegoBinding.inflate(layoutInflater)

            if (puntosJ2 <= maxPuntos) {

                //le sumamos el resultado de la función cartaRandom
                puntosJ2 += cartaRandom()

                //Se muestran los puntos por pantalla
                segunda.numPuntosJ2.text = "$puntosJ2"

            } else {

                val toast =
                    Toast.makeText(applicationContext, "$nomJ2 te has pasado", Toast.LENGTH_LONG)
                        .show()

                //Mostramos los puntos del jugador 2.
                segunda.numPuntosJ2.text = "$puntosJ2"

                //Deshabilitamos los botones del J2.
                segunda.cartaJ2.isEnabled=false
                segunda.plantarseJ2.isEnabled=false

                //Preguntamos por el ganador.
                ganador()

            }
        }

    fun ganador() {

        //Declaramos las vistas
        val primera=NombresBinding.inflate(layoutInflater)
        val segunda=JuegoBinding.inflate(layoutInflater)

        if (puntosJ1>puntosJ2&&puntosJ1<=maxPuntos&&puntosJ2<=maxPuntos) {
            //Gana el J1
            segunda.turno.text="Ha ganado $nomJ1"

        }
        if (puntosJ1<=maxPuntos&&puntosJ2>maxPuntos) {
            //gana el J1.
            segunda.turno.text="Ha ganado $nomJ1"
        }
        if (puntosJ2>puntosJ1&&puntosJ1<=maxPuntos&&puntosJ2<=maxPuntos) {
            //gana el J2.
            segunda.turno.text="Ha ganado $nomJ2"
        }
        if (puntosJ1==puntosJ2&&puntosJ1<=maxPuntos&&puntosJ2<=maxPuntos) {
            //han empatado
            segunda.turno.text="Ha habido empate"
        }
        if (puntosJ1>maxPuntos&&puntosJ2>maxPuntos) {
            //Ambos hugadores han perdido.
            segunda.turno.text="Ambos jugadores han perdido"
        }

        //Deberían aparecer los botones para jugar otra partida.

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

    }


