package com.isaac.processscript.dpoint.reimburse

import com.fasterxml.jackson.databind.ObjectMapper

/**
 * 逻辑：
 *  （成本中心+项目+金额） +  收支项目 --drools--> 对应的多级审批人
 *  返回中多条信息根据 drools 规则分组，同一个规则归位一组，同一组中的 项目 和 收支项目拼接，合并为同一个规则下只有一条
 *  根据合并的结果，去报销明细中查询，并且将符合条件的明细增加至合并结果中
 */
import com.isaac.processscript.util.JsonFileUtils
import groovy.json.JsonOutput
import groovy.json.JsonSlurper

def slurper = new JsonSlurper()
def droolsResult = slurper.parseText(JsonFileUtils.parseJsonFileToStringValue("dipont/costCenterDrools_json.json"))

def tbhdexpdetfn01a_item_json = JsonFileUtils.parseJsonFileToStringValue("dipont/tbhdexpdetfn01a_item_json.json")


def groupMap = droolsResult.groupBy { it.ruleno }
//merge
def mergeResult = []//[{key:value...}]
groupMap.each {
    def projects = new HashSet()
    def ioitems = new HashSet()
    it.value.each {
        item -> projects << item.project; ioitems << item.ioitem
    }
    it.value[0].project = projects.join(",")
    it.value[0].ioitem = ioitems.join(",")
    mergeResult << it.value[0]
}

mergeResult.each {
    def tbhdexpdetfn01a_item = slurper.parseText(tbhdexpdetfn01a_item_json)
    tbhdexpdetfn01a_item.each {item->
        if (item.key == 'rows') {
            item.value = item.value.findAll { i -> it.ioitem.split(",").contains(i[0].code) && it.project.split(",").contains(i[2].code) }
        }
    }
    it.put('tbhd_items',tbhdexpdetfn01a_item)
}
def mergeResult1 = []
mergeResult.each {mergeResult1<<JsonOutput.toJson(it)}
println(mergeResult1)

//def map = new JsonSlurper().parseText("""{"key":"value"}""")
//println(map.getClass())
//
//def objectMapper = new ObjectMapper()
//def map1 = objectMapper.readValue("""{"key":"value"}""", Map.class)
//println(map1.getClass())
