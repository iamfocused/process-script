package com.isaac.processscript.dpoint.purchase

import com.isaac.processscript.util.JsonFileUtils
import groovy.json.JsonOutput
import groovy.json.JsonSlurper

import java.util.stream.Collectors

/**
 * 采购明细分组，并且计算每组的总价钱
 */
def jsonSlurper = new JsonSlurper()
def tbhdpurchasepu001_item_std_str = JsonFileUtils.parseJsonFileToStringValue("dipont/purchase.json")
def std = jsonSlurper.parseText(tbhdpurchasepu001_item_std_str)

def types = std.groupBy({it.type})
def type_stationery = "文具及生活用品 Stationery And Necessary"
types.each{ item ->
    if(item.key == type_stationery) println(JsonOutput.toJson(item.value) + "")
    if(item.key == "固定资产 Fixed Assets") println(JsonOutput.toJson(item.value) + "")
    def doubleSummary = item.value.stream().collect(Collectors.summarizingDouble({a->Optional.ofNullable(a.price).orElse(0).toDouble() * Optional.ofNullable(a.quantity).orElse(0).toDouble()}))
    item.value = doubleSummary?.sum
}
def stationeryPrice = Optional.ofNullable(types.get(type_stationery)).orElse(0)

println(stationeryPrice)

def keySet = types.keySet()

println keySet