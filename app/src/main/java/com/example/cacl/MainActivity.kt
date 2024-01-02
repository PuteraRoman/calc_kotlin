package com.example.cacl

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import net.objecthunter.exp4j.ExpressionBuilder
import kotlin.text.StringBuilder

// Activity class implementing the MemoriesMethods interface
class MainActivity : AppCompatActivity(),MemoriesMethods {

    // Declaration of variables for view elements and data
    private lateinit var mathOperation: TextView

    private lateinit var buttonZero: Button

    private lateinit var buttonOne: Button

    private lateinit var buttonTwo: Button

    private lateinit var buttonThree: Button

    private lateinit var buttonFour: Button

    private lateinit var buttonFive: Button

    private lateinit var buttonSix: Button

    private lateinit var buttonSeven: Button

    private lateinit var buttonEight: Button

    private lateinit var buttonNine: Button

    private lateinit var buttonMinus: Button

    private lateinit var buttonPlus: Button

    private lateinit var buttonMultiply: Button

    private lateinit var buttonAC: Button

    private lateinit var buttonComa: Button

    private lateinit var buttonDivine: Button

    private lateinit var buttonEqual: Button

    private lateinit var buttonPlusMinus: Button

    private lateinit var buttonProcent: Button

    private var memory:Double = 0.0

    private var resultSet: StringBuilder = StringBuilder()

    private var isEndResult: Boolean = true;


    // onCreate function, called when the activity is created
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        // Initialization of all view elements


        mathOperation = findViewById(R.id.math_operation)

        buttonZero = findViewById(R.id.button_0)

        buttonOne = findViewById(R.id.button_1)

        buttonTwo = findViewById(R.id.button_2)

        buttonThree = findViewById(R.id.button_3)

        buttonFour = findViewById(R.id.button_4)

        buttonFive = findViewById(R.id.button_5)

        buttonSix = findViewById(R.id.button_6)

        buttonSeven = findViewById(R.id.button_7)

        buttonEight = findViewById(R.id.button_8)

        buttonNine = findViewById(R.id.button_9)

        buttonMinus = findViewById(R.id.button_minus)

        buttonPlus = findViewById(R.id.button_plus)

        buttonMultiply = findViewById(R.id.button_multiplication)

        buttonAC = findViewById(R.id.button_AC)

        buttonComa = findViewById(R.id.button_coma)

        buttonDivine = findViewById(R.id.button_division)

        buttonEqual = findViewById(R.id.button_equal_sign)

        buttonPlusMinus = findViewById(R.id.button_plus_minus)
        buttonProcent = findViewById(R.id.button_procent)


        // Adding event handlers for buttons
        buttonZero.setOnClickListener {
            setTextField("0")
            isEndResult = true
        }

        buttonOne.setOnClickListener {
            setTextField("1")
            isEndResult = true
        }

        buttonTwo.setOnClickListener {
            setTextField("2")
            isEndResult = true
        }

        buttonThree.setOnClickListener {
            setTextField("3")
            isEndResult = true
        }

        buttonFour.setOnClickListener {
            setTextField("4")
            isEndResult = true
        }

        buttonFive.setOnClickListener {
            setTextField("5")
            isEndResult = true
        }

        buttonSix.setOnClickListener {
            setTextField("6")
            isEndResult = true
        }

        buttonSeven.setOnClickListener {
            setTextField("7")
            isEndResult = true
        }

        buttonEight.setOnClickListener {
            setTextField("8")
            isEndResult = true
        }

        buttonNine.setOnClickListener {
            setTextField("9")
            isEndResult = true
        }


        buttonPlusMinus.setOnClickListener {

            var a = resultSet.toString().toInt()
            a -= (a * 2)
            resultSet = StringBuilder()
            mathOperation.text = ""
            resultSet.append(a)
            mathOperation.append(a.toString())
            isEndResult = true
        }

         buttonProcent.setOnClickListener {
            if (resultSet.isNotEmpty()) {
                try {
                    var a = resultSet.toString().toDouble()
                    a /= 100
                    resultSet = StringBuilder()
                    mathOperation.text = ""
                    resultSet.append(a)
                    mathOperation.append(a.toString())
                    isEndResult = false
                } catch (e: NumberFormatException) {
                    Log.d("Exception", "Invalid number format")
                }
            }
        }

        buttonMultiply.setOnClickListener { setTextField("*") }

        buttonPlus.setOnClickListener { setTextField("+") }

        buttonMinus.setOnClickListener { setTextField("-") }

        buttonComa.setOnClickListener { setTextField(".") }

        buttonDivine.setOnClickListener { setTextField("/") }

        // Event handler for the "Clear All" (AC) button
        buttonAC.setOnClickListener {
            // Clearing the operation display text field
            mathOperation.text = ""

            if (resultSet.isNotEmpty()) {
                // Clearing the result buffer
                resultSet = StringBuilder()
            }
            // Clearing memory
            clearAllmemory()
        }

        // Event handler for the "Equals" (=) button
        buttonEqual.setOnClickListener {

            try {
                // Building the expression
                val ex = ExpressionBuilder(resultSet.toString()).build()
                // Evaluating the expression result
                val result = ex.evaluate()
                val modRes = result.toLong()
                // Displaying the result on the screen
                if (result == modRes.toDouble()) {
                    mathOperation.text = modRes.toString()
                    resultSet = StringBuilder()
                    resultSet.append(modRes.toString())
                } else {
                    mathOperation.text = result.toString()
                    resultSet = StringBuilder()
                    resultSet.append(result.toString())
                }
                // Saving the result to memory
                saveMemory()

                isEndResult = false

            } catch (e: Exception) {
                // Handling exception during expression evaluation
                Log.d("Exception", "Message: ${e.message}")
            }
        }


    }

    // Function to add text to the input field
    fun setTextField(str: String) {
        buttonAC.text = "C"
        if (resultSet.isEmpty()) {
            buttonAC.text = "AC"
        }
        // Check if the input string is a dot (.) and the last character in the result is not already a dot
        if (str == "." && !resultSet.get(resultSet.length - 1).equals(".")) {
            resultSet.append(str)
            mathOperation.append(str)
            //If input is not a dot
        } else if (str != ".") {
            resultSet.append(str)
            mathOperation.append(str)
            // If the input character is an operator (*, +, -, /), reset the display
            if (str in setOf("*", "+", "-", "/")) {
                mathOperation.text = ""
                isEndResult = true
            }

            if (isEndResult == false) {
                resultSet = StringBuilder()
                mathOperation.text = ""
                resultSet.append(str)
                mathOperation.append(str)
            }
        }
    }


    fun returnMemory(): Double {
        return memory
    }


    // Overridden function to save the result in memory
    override fun saveMemory(): Boolean {
        val res = resultSet.toString().toDouble()
        memory =res
        println("Saved in memory: $memory")
        return true

    }

    // Overridden function to clear all memory
    override fun clearAllmemory() {
        memory = 0.0
    }
}