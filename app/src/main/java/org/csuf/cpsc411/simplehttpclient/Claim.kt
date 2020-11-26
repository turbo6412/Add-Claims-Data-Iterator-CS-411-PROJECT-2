package org.csuf.cpsc411.simplehttpclient

// user will only enter a title and date for claim, no need for UUID and boolean properties
data class Claim(var title:String?, var date:String?)