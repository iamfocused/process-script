package com.isaac.processscript.question

import groovy.json.JsonSlurper

def sn = 'sdfs'
def param=java.net.URLEncoder.encode('[{"type":"category","target":["variable",["template-tag","param"]],"value":\"'+ sn+ '\"}]', "UTF-8");
def url = "https://metabase.eorionsolution.com/public/question/2c5a9a8b-9bf4-4260-8060-0ee2cafb8b28.json?parameters="
def getUrl = url + param
println(getUrl)
