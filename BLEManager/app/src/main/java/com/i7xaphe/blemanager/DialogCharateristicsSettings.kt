package com.i7xaphe.blemanager

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import android.widget.RadioGroup
import kotlinx.android.synthetic.main.dialog_charateristic_settings.*

interface DialogCharateristicSettingsInterface{
    fun onSaveClick(string:String)
}

class DialogCharateristicSettings(context: Context?, dialogCharateristicSettingsInterface:DialogCharateristicSettingsInterface, function:String) : Dialog(context) {
    var dialogGraphSettingsInterface =dialogCharateristicSettingsInterface
    var function:String=function

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_charateristic_settings)

        radio_group.setOnCheckedChangeListener({ radioGroup: RadioGroup, id: Int ->
            when(id){
                rb_none.id->{
                    function=rb_none.text.toString()
                    setInfo(function)
                }
                rb_string.id->{
                    function=rb_string.text.toString()
                    setInfo(function)
                }
                rb_int.id->{
                    function=rb_int.text.toString()
                    setInfo(function)
                }
                rb_float.id->{
                    function=rb_float.text.toString()
                    setInfo(function)
                }

            }
        })
        when(function){
            rb_none.text.toString()->{
                rb_none.isChecked=true
                setInfo(function)
            }
            rb_string.text.toString()->{
                rb_string.isChecked=true
                setInfo(function)
            }
            rb_int.text.toString()->{
                rb_int.isChecked=true
                setInfo(function)
            }
            rb_float.text.toString()->{
                rb_float.isChecked=true
                setInfo(function)
            }
        }

        save.setOnClickListener({
            dialogGraphSettingsInterface.onSaveClick(function)
            dismiss()
            cancel()
        })
        cancel.setOnClickListener({
            dismiss()
            cancel()
        })
    }



     fun setInfo(function:String){
        when(function){
            rb_none.text.toString()->{ info.text="Packages from a given characteristic will be\nomitted"}
            rb_string.text.toString()->{ info.text="Strings separated by newline characters \n(\\n, \\n\\r, \\r\\n or \\r) or a tab character \\t \nwill be converted to float.\nIn case of failure 0 will be returned" }
            rb_int.text.toString()->{  info.text="The four consecutive bytes in the packet \n(in little endian) will be interpreted as integer" }
            rb_float.text.toString()->{ info.text="The four consecutive bytes in the packet \n(in little endian) will be interpreted as float"  }
        }
    }

}