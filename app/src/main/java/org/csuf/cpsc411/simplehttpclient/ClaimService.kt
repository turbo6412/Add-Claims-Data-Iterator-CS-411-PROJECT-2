package org.csuf.cpsc411.simplehttpclient

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import cz.msebera.android.httpclient.entity.StringEntity
import java.lang.reflect.Type

class ClaimService (val ctx : CustomActivity){

    var claimList : MutableList<Claim> = mutableListOf()
    // var currentIndx : Int = 0

    companion object { // USED FROM PROFESSOR CODE
        private var cService : ClaimService? = null
        fun getInstance(act : CustomActivity) : ClaimService {
            if (cService == null) {
                cService = ClaimService(act)
            }
            return cService!!
        }
    }

/*    companion object {
        private var pService : ClaimService? = null

        fun getInstance(act : CustomActivity) : ClaimService {
            if (pService == null) {
                pService = ClaimService(act)
            }

            return pService!!
        }
    }

    fun next() : Claim {
        currentIndx = currentIndx + 1
        return claimList[currentIndx]
    }*/


    inner class GetAllServiceRespHandler : AsyncHttpResponseHandler() {
        override fun onSuccess(
            statusCode: Int,
            headers: Array<out Header>?,
            responseBody: ByteArray?
        ) {
            // JSON string
            if (responseBody != null) {
                Log.d("Claim Service", "The response JSON string is ${String(responseBody)}")
                val gson = Gson()
                val claimListType: Type = object : TypeToken<List<Claim>>() {}.type
                claimList = gson.fromJson(String(responseBody), claimListType)
                Log.d("Claim Service", "The Person List: ${claimList}")
                // for "Drill Down" stuff
/*                if (ctx is SummaryScreenActivity) {
                    (ctx as SummaryScreenActivity).refreshScreen()
                } else ctx.refreshScreen(claimList[currentIndx])*/
                //
            }
        }

        override fun onFailure(
            // in case data can't be retrieved from the db, send an error message
            statusCode: Int,
            headers: Array<out Header>?,
            responseBody: ByteArray?,
            error: Throwable?
        ) {
            Log.d("Claim Get All", "ERROR: Could not get all claims")
        }
    }

    inner class addServiceRespHandler : AsyncHttpResponseHandler() {
        override fun onSuccess(
            statusCode: Int,
            headers: Array<out Header>?,
            responseBody: ByteArray?
        ) {
            if (responseBody != null) {
                val respStr = String(responseBody)
                ctx.addedStatus("Claim: ${respStr} added to db!") // works with CustomActivity to check status
                Log.d("Claim Service", "Your add Service response : ${respStr}")
            }
        }

        override fun onFailure(
            // in case data can't be added to the db, send an error message
            statusCode: Int,
            headers: Array<out Header>?,
            responseBody: ByteArray?,
            error: Throwable?
        ) {
            ctx.addedStatus("ERROR: Claim failed to be added") // works with CustomActivity to check status
        }
    }

    fun addClaim(cObj : Claim) {
        // adding a claim to our db
        val client = AsyncHttpClient()
        val requestUrl = "http://192.168.45.1:8010/PersonService/add"
        // 1. Convert the cObj into JSON string
        val cJsonString= Gson().toJson(cObj)
        // 2. Send the post request
        val entity = StringEntity(cJsonString)

        // cxt is an Android Application Context object, sending post message
        client.post(ctx, requestUrl, entity,"application/json", addServiceRespHandler())
    }

    fun getAll()  { // this function grabs all the claims we have in db
        // Call Http
        //clientObj = clObj
        val client = AsyncHttpClient()
        val requestUrl = "http://192.168.0.112:8010/PersonService/getAll"
        //
        Log.d("Claim Service", "About Sending the HTTP Request. ")
        //
        client.get(requestUrl, GetAllServiceRespHandler())
    }
}