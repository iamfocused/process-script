package com.isaac.processscript.bbot

import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import java.security.InvalidKeyException

def  from_ = '1567267200000'
def to_ = '1569859199183'

def hmac_sha256(String secretKey, String data) {
    try {
        Mac mac = Mac.getInstance("HmacSHA256")
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256")
        mac.init(secretKeySpec)
        byte[] digest = mac.doFinal(data.getBytes())
        return digest
    } catch (InvalidKeyException e) {
        throw new RuntimeException("Invalid key exception while converting to HMacSHA256")
    }
}
def METABASE_SITE_URL = "https://my.zhizaozu.com/metabase"
def METABASE_SECRET_KEY = "dad5c368f5c8d7926c3565ac1d71c53557a0bc5c1a1d8a639e95a4b6a67f60d7"
def header = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9"
def playloadString = '{"resource":{"question":3},"params":{"procInstId":\"'+ '60951' +'\","processNumber": \"'+'1'+'\"}}'
def playload = Base64.getUrlEncoder().encodeToString(playloadString.bytes)
def secret =Base64.getUrlEncoder().encodeToString(hmac_sha256(METABASE_SECRET_KEY , header+'.'+playload))

def url = METABASE_SITE_URL + "/embed/question/" + header+'.'+playload+'.'+secret + "#bordered=true&titled=true"

println(url)