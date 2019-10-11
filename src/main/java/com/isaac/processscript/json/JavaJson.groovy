package com.isaac.processscript.json

import com.fasterxml.jackson.databind.ObjectMapper

def objectMapper = new ObjectMapper()
//json -> object
def obj = objectMapper.readValue("""[{"name":"tom"}, {"name":"jerry"}]""", List.class)
assert obj instanceof ArrayList

//object -> json
def jsonStr = objectMapper.writeValueAsString(obj)
assert jsonStr instanceof String

println "Object type : " + obj
println "Json String type : " + jsonStr

