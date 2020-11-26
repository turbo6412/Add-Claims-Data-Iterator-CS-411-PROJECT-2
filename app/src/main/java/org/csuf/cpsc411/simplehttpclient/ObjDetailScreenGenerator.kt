package org.csuf.cpsc411.simplehttpclient

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class ObjDetailScreenGenerator(val ctx : Context) {
    lateinit var layoutObj : LinearLayout
    fun generate() : LinearLayout {
        // 1. Create a linearLayout object
        layoutObj = LinearLayout(ctx)
        val lParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        layoutObj.layoutParams = lParams
        layoutObj.orientation = LinearLayout.VERTICAL
        layoutObj.setBackgroundColor(Color.MAGENTA)

        // 2. Add ObjDetailSection
        val fldRowGenerator = ObjDetailSectionGenerator(ctx)
        val colView = fldRowGenerator.generate()
        layoutObj.addView(colView)

        // 3. Add Next Button LinearLayout
        val nLayout = LinearLayout(ctx)
        val nParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)
        // only applied to height now
        nParams.gravity = Gravity.RIGHT
        nLayout.layoutParams = nParams
        nLayout.orientation = LinearLayout.HORIZONTAL
        nLayout.setBackgroundColor(Color.GRAY)

        // CREATE THE ADD BUTTON
        val add_Button = Button(ctx)
        add_Button.text = "Add"
        add_Button.setId(R.id.add_btn)
        add_Button.setBackgroundColor(Color.CYAN)
        val nbParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        nbParams.gravity = Gravity.BOTTOM
        nLayout.addView(add_Button, nbParams)
        //
        layoutObj.addView(nLayout, nParams)

        // CREATE THE STATUS MESSAGE NEXT TO BUTTON
        var status_msg = TextView(ctx)
        status_msg.text = "Status   <Status Message>"
        status_msg.setId(R.id.status)
        status_msg.setBackgroundColor(Color.CYAN)
        val statusParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        statusParams.gravity = Gravity.BOTTOM
        nLayout.addView(status_msg, statusParams)
        //
        layoutObj.addView(nLayout, statusParams)



        return layoutObj
    }
}