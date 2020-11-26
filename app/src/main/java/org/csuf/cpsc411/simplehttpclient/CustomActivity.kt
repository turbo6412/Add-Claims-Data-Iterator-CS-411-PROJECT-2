package org.csuf.cpsc411.simplehttpclient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import java.util.*

open class CustomActivity : AppCompatActivity() {
    lateinit var pList : MutableList<Claim>
    lateinit var cService : ClaimService

    fun refreshScreen(cObj : Claim) {
        //
        Log.d("Claim Service", "Refreshing the Screen. ")
        // select the tags on UI
        val titleView : EditText = findViewById(R.id.claim_title)
        val dateView : EditText = findViewById(R.id.claim_date)

        // Give the tags the appropriate values that user entered for title and date
        titleView.setText(cObj.title)
        dateView.setText(cObj.date)
    }

    fun addedStatus(msg : String) {
        // called in ClaimService when we add a claim on the OnSuccess or OnFailure
        // tells us whether user successfully added to db or not
        val statusView : TextView = findViewById(R.id.status)
        val status_msg = msg

        statusView.setText("Status:   <${status_msg}>")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //val fldRowGenerator = FieldValueViewGenerator(this, "SSN")
        //val colGenerator = LableColumnGenerator(this)
        //val colView = colGenerator.generate()
        val fldRowGenerator = ObjDetailScreenGenerator(this)
        val colView = fldRowGenerator.generate()
        setContentView(colView)
        //
        val bView : Button = findViewById(R.id.add_btn)
        //
        bView.setOnClickListener{
            // get the added claim object and then refresh it
            var title : EditText = findViewById(R.id.claim_title)
            var date : EditText = findViewById(R.id.claim_date)

            // convert them to string to pass into Claim class object
            var title_str = title.text.toString()
            var date_str = date.text.toString()
            var new_claim = Claim(title_str, date_str)
            cService.addClaim(new_claim)
            // refresh with new added claim object
            refreshScreen(new_claim)
        }
        //
/*        val pos = intent.getIntExtra("ElementId", 0)
        Log.d("Detailed Activity ", "Display ${pos} Person object")*/
        cService = ClaimService.getInstance(this)
/*        if (pService.claimList.count() != 0) {
            val pObj = pService.fetchAt(pos)
            refreshScreen(pObj)
        }*/

        //pService.getAll()

        //setContentView(R.layout.main_activity)
    }

}