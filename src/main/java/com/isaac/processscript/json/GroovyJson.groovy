package com.isaac.processscript.json

import groovy.json.JsonGenerator
import groovy.json.JsonOutput
import groovy.json.JsonSlurper

//json -> groovy obj JsonSlurper
def jsonSlurper = new JsonSlurper()
def obj = jsonSlurper.parseText("[1, 2, 3]")
assert obj instanceof ArrayList

//groovy obj -> json JsonOutput
def jsonStr = JsonOutput.toJson(obj)
assert jsonStr instanceof String

//Customizing Output ——JsonGenerator
def generator = new JsonGenerator.Options()
        .excludeNulls()
        .dateFormat('yyyy-MM-dd')
        .excludeFieldsByName('age','password')
        .excludeFieldsByType(URL)
        .build()
generator.toJson(obj)

def str= "1561910400000"
def map = new HashMap()
def date = new Date(str.toLong())
map.put("endDate", date)
println generator.toJson(map)

println JsonOutput.toJson(['urlPath':'urlpath'])

list = ['a']
println(list[0])

def map1 = ['001':'admin']
//map1.each {k,v -> println(k + " " + v)}
println map1.keySet()[0]
println map1.get(map1.keySet()[0])



