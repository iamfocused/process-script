package com.isaac.processscript.shangqi

import com.isaac.processscript.util.JsonFileUtils
import groovy.json.JsonOutput
import groovy.json.JsonSlurper

def jsonSlurper = new JsonSlurper()
def stringValue = JsonFileUtils.parseJsonFileToStringValue("shangqi/first.json")
def claimItems = jsonSlurper.parseText(stringValue)

println JsonOutput.toJson(analysisDuty(claimItems, 'tbhdclaim1'))
println JsonOutput.toJson(claimItems)
println JsonOutput.toJson(filterDissentRows(jsonSlurper.parseText(JsonFileUtils.parseJsonFileToStringValue("shangqi/second.json"))))

static def analysisDuty(def inputItem, def duty){
    def item = inputItem
    def types = ['tbhdclaim1':'仓库责任','tbhdclaim2':'运输责任','tbhdclaim3':'包装责任']
    if (!types.keySet().contains(duty)) {
        throw new Exception("责任划分超出范围")
    }
    item.each {k,v ->
        if (k == 'headers') {
            v.each { it.id = it.id.replace('tbhdclaim', duty)}
        }
    }
    item.put('tableName', duty)
    item.put('rows', item.get('rows').findAll{it[15]==types.get(duty)})
    return item
}
static def filterDissentRows(def item) {
    return item.get('rows').findAll{it[17]=='提出异议'}
}
