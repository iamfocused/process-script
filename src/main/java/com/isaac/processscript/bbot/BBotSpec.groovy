package com.isaac.processscript.bbot

/**
 * 需要：
 * METABASE_SITE_URL = "metabase网站地址"
 * METABASE_SECRET_KEY = "密钥"
 * playloadString = '{"resource":{"question":共享问题的id},"params":{"变量名":"变量参数"}}'
 */
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import java.security.InvalidKeyException
import java.util.Base64
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
def METABASE_SITE_URL = "metabase网站地址"
def METABASE_SECRET_KEY = "密钥"
def header = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9"//=Base64.getUrlEncoder().encodeToString('{"typ":"JWT","alg":"HS256"}'.bytes);
def playloadString = '{"resource":{"question":共享问题的id},"params":{"变量名":"变量参数"}}'
def playload = Base64.getUrlEncoder().encodeToString(playloadString.bytes)

def secret =Base64.getUrlEncoder().encodeToString(hmac_sha256(METABASE_SECRET_KEY , header+'.'+playload))
def url = METABASE_SITE_URL + "/embed/question/" + header+'.'+playload+'.'+secret + "#bordered=true&titled=false"
println(url)