package com.isaac.processscript.dpoint.payment

import com.isaac.processscript.util.JsonFileUtils
import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import java.util.stream.Collectors

static List<Map> handleDroolsResult(List<Map> droolsResult){
    def groupMap = droolsResult.stream().collect(Collectors.groupingBy({it.ruleno}))
    return groupMap.entrySet().stream().map({
        def map = it.value[0]
        def projectArr = it.value.stream().map({li -> li.project}).collect(Collectors.toSet())
        def ioitemArr = it.value.stream().map({li -> li.ioitem}).collect(Collectors.toSet())
        map.project = projectArr.join(",")
        map.ioitem = ioitemArr.join(",")
        return map
    }).collect(Collectors.toList())
}

static List<String> putItems(List<Map> handleResult, String itemString, JsonSlurper jsonSlurper){
    handleResult.forEach({
        def ioitem = it.ioitem
        def project = it.project
        def costcenter = it.costcenter
        def items = jsonSlurper.parseText(itemString)
        items.each { item ->
            if(item.key == 'rows') {
                item.value = item.value.findAll{ val ->
                    ioitem.split(',').contains(val[0].code) && val[1].code == costcenter && project.split(',').contains(val[2].code)
                }
            }
        }

        it.put("tbhd_items", items)
    })
    return handleResult.stream().map({JsonOutput.toJson(it)}).collect(Collectors.toList())
}


def jsonSlurper = new JsonSlurper()
def droolsResult = jsonSlurper.parseText(JsonFileUtils.parseJsonFileToStringValue("dipont/duigong_jingnei/droolsReturn.json"))
def handleResult = handleDroolsResult(droolsResult)
def itemString = JsonFileUtils.parseJsonFileToStringValue("dipont/duigong_jingnei/tbhdpaymentpur002a_item.json")
def result = putItems(handleResult, itemString, jsonSlurper)
println()
println(result)

