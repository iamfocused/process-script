package com.isaac.processscript.mailsender

import groovy.json.JsonOutput
import groovy.json.JsonSlurper

def bodyMap = new HashMap()//{"mail":detailMap}

def detailMap = new HashMap()////{"templateData": dataMap,"from":"","to":"","templateName":""...}

def dataMap = new HashMap()

detailMap.put("from","develop@eorionsolution.com")
detailMap.put("to", ["baocaixue@eorionsolution.com"])

detailMap.put("subject", "subject")

detailMap.put("templateName", "dummy")

detailMap.put("templateData",dataMap)
dataMap.put("user", "test")
bodyMap.put("mail", detailMap)

def body = JsonOutput.toJson(bodyMap)

println(body)

def jsonSlurper = new JsonSlurper()