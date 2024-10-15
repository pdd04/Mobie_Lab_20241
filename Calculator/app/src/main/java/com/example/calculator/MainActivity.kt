package com.example.calculator

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log.i
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.math.BigDecimal
import java.math.RoundingMode
import java.nio.file.Files.delete
import kotlin.math.round

class MainActivity : AppCompatActivity(), View.OnClickListener {


    lateinit var textResult: TextView
    lateinit var expression: TextView

    var state: Int = 1 // trang thai cua so dang la int hay double
    var op: Int = 1 // trang thai cua so dang nhap
    var op1: Int = 0
    var dec: Double = 0.0
    var inum1: Int = 0 // luu so thu nhat
    var inum2: Int = 0 // luu so thu hai
    var dnum1: Double = 0.0
    var dnum2: Double = 0.0
    var strExpression: String = ""
    var dresult: Double = 0.0
    var iresult: Int = 0


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        textResult = findViewById(R.id.textView)
        expression = findViewById(R.id.textView3)

        findViewById<Button>(R.id.btn0).setOnClickListener(this)
        findViewById<Button>(R.id.btn1).setOnClickListener(this)
        findViewById<Button>(R.id.btn2).setOnClickListener(this)
        findViewById<Button>(R.id.btn3).setOnClickListener(this)
        findViewById<Button>(R.id.btn4).setOnClickListener(this)
        findViewById<Button>(R.id.btn5).setOnClickListener(this)
        findViewById<Button>(R.id.btn6).setOnClickListener(this)
        findViewById<Button>(R.id.btn7).setOnClickListener(this)
        findViewById<Button>(R.id.btn8).setOnClickListener(this)
        findViewById<Button>(R.id.btn9).setOnClickListener(this)
        findViewById<Button>(R.id.btn10).setOnClickListener(this)
        findViewById<Button>(R.id.btnAdd).setOnClickListener(this)
        findViewById<Button>(R.id.btnC).setOnClickListener(this)
        findViewById<Button>(R.id.btnCE).setOnClickListener(this)
        findViewById<Button>(R.id.btnEq).setOnClickListener(this)
        findViewById<Button>(R.id.btnMun).setOnClickListener(this)
        findViewById<Button>(R.id.btnX).setOnClickListener(this)
        findViewById<Button>(R.id.btnDev).setOnClickListener(this)
        findViewById<Button>(R.id.btnD).setOnClickListener(this)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onClick(p0: View?) {
        val id = p0?.id
        if(id == R.id.btn0){
            addDigit(0)
        }else if(id == R.id.btn1){
            addDigit(1)
        }else if(id == R.id.btn2){
            addDigit(2)
        }else if(id == R.id.btn3){
            addDigit(3)
        }else if(id == R.id.btn4){
            addDigit(4)
        }else if(id == R.id.btn5){
            addDigit(5)
        }else if(id == R.id.btn6){
            addDigit(6)
        }else if(id == R.id.btn7){
            addDigit(7)
        }else if(id == R.id.btn8){
            addDigit(8)
        }else if(id == R.id.btn9){
            addDigit(9)
        }else if(id == R.id.btnC){ // xoa het
            delete()
        }else if(id == R.id.btnAdd){ // cong
            if(op == 1){
                op = 2
                dresult += inum1
            }else if(op == 3){
                op = 2
                dresult += dnum1
            }
            op1 = 1
            strExpression += "+"
            textResult.text = strExpression
        }else if(id == R.id.btnMun){ // tru
            if(op == 1){
                op = 2
                dresult += inum1
            }else if(op == 3){
                op = 2
                dresult += dnum1
            }
            op1 = 2
            strExpression += "-"
            textResult.text = strExpression
        }else if(id == R.id.btnX){ // tru
            if(op == 1){
                op = 2
                dresult += inum1
            }else if(op == 3){
                op = 2
                dresult += dnum1
            }
            op1 = 3
            strExpression += "x"
            textResult.text = strExpression
        }else if(id == R.id.btnDev){ // tru
            if(op == 1){
                op = 2
                dresult += inum1
            }else if(op == 3){
                op = 2
                dresult += dnum1
            }
            op1 = 4
            strExpression += "/"
            textResult.text = strExpression
        }else if(id == R.id.btnD){ // tru
            if(op == 1){
                state = 2
                op = 3
                dec = 1.0
                strExpression += "."
            }else if(op == 2){
                state = 2
                op = 4
                dec = 1.0
                strExpression += "."
            }
            textResult.text = strExpression
        }else if(id == R.id.btnEq){ // bang
            if(op == 1){
                if(op1 ==1){
                    dresult += inum1
                }else if(op1 == 2){
                    dresult -= inum1
                }else if(op1 == 3){
                    dresult *= inum1
                }else if(op1 == 4){
                    dresult /= inum1
                }
                check(dresult)
            }else if(op == 2){
                if(op1 == 1){
                    dresult += inum2
                }else if(op1 == 2){
                    dresult -= inum2
                }else if(op1 == 3){
                    dresult *= inum2
                }else if(op1 == 4){
                    dresult /= inum2
                }

            }else if(op == 3){
                if(op1 == 1){
                    dresult += dnum1
                }else if(op1 == 2){
                    dresult -= dnum1
                }else if(op1 == 3){
                    dresult *= dnum1
                }else if(op1 == 4){
                    dresult /= dnum1
                }

            }else if(op == 4){
                if(op1 == 1){
                    dresult += dnum2
                }else if(op1 == 2){
                    dresult -= dnum2
                }else if(op1 == 3){
                    dresult *= dnum2
                }else if(op1 == 4){
                    dresult /= dnum2
                }
            }
            check(dresult)

        }
    }

    fun addDigit(num: Int){
        if(op == 1){
            state = 1
            inum1 = inum1*10 + num
            strExpression = "$inum1"
            textResult.text = strExpression
        }else if(op == 2){
            state = 1
            inum2 = inum2*10 + num
            strExpression += "$inum2"
            textResult.text = strExpression
        }else if(op == 3){
            state = 2
            if(dec == 1.0){
                dnum1 = inum1.toDouble()
            }
            var decBigDecimal = BigDecimal(dec.toString())
            var pow = BigDecimal("0.1").pow(decBigDecimal.toInt())
            var num1 = num.toDouble()
            var decimal = BigDecimal(num1.toString()).multiply(pow)
            var decimal1 = decimal.toDouble()
            dnum1 = dnum1 + decimal1
            strExpression += decimal1.toString().removeRange(0, dec.toInt() + 1)
            textResult.text = strExpression
            dec += 1.0
        }else if(op == 4){
            state = 2
            if(dec == 1.0){
                dnum2 = inum2.toDouble()
            }
            var decBigDecimal = BigDecimal(dec.toString())
            var pow = BigDecimal("0.1").pow(decBigDecimal.toInt())
            var num1 = num.toDouble()
            var decimal = BigDecimal(num1.toString()).multiply(pow)
            var decimal1 = decimal.toDouble()
            dnum2 = dnum2 + decimal1
            strExpression += decimal1.toString().removeRange(0, dec.toInt() + 1)
            textResult.text = strExpression
            dec += 1.0
        }
    }

    fun delete(){
        state= 1 // trang thai cua so dang la int hay double
        op = 1 // trang thai cua so dang nhap
        dec= 0.0
        inum1= 0 // luu so thu nhat
        inum2= 0 // luu so thu hai
        dnum1= 0.0
        dnum2= 0.0
        strExpression= ""
        dresult= 0.0
        iresult = 0
        expression.text = ""
        textResult.text = "0"
    }

    fun check(num: Double){
        var inum = round(num)
        if(num - inum != 0.0){
            dresult = num
            textResult.text = "$dresult"
            op = 3
            dnum1 = dresult
            dnum2 = 0.0
            inum1 = 0
            inum2 = 0
            expression.text = strExpression
            strExpression = "$dresult"
        }else if(num - inum == 0.0){
            iresult = num.toInt()
            textResult.text = "$iresult"
            op = 1
            inum1 = iresult
            inum2 = 0
            dnum1 = 0.0
            dnum2 = 0.0
            expression.text = strExpression
            strExpression = "$iresult"
        }
        dresult = 0.0
        iresult = 0
    }

}