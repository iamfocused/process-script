package com.isaac.processscript.dpoint.payment

import com.isaac.processscript.util.JsonFileUtils
import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import java.util.stream.Collectors

static List<Map<String, String>> constructDroolsCondition(List rows) {
    def result = []
    //index 1:成本中心 2:项目 --- amount sum的维度
    def groupMap = rows.stream().collect(
            Collectors.groupingBy(
                    { "${it[1].code}-${it[2].code}" },//key
                    Collectors.mapping(
                            { it[3].toBigDecimal() },
                            Collectors.reducing(BigDecimal.ZERO, { a, b -> a.add(b) })
                    )//value
            )
    )
    //drools规则多一个收支项目维度
    groupMap.entrySet().stream().forEach({ x ->
        def keyArr = x.key.split("-")
        def costcenter = keyArr[0]
        def project = keyArr[1]
        def ioitems = rows.stream().filter({it[1].code == costcenter && it[2].code == project}).map({it[0].code}).collect(Collectors.toSet())
        ioitems.each {
            result << ["ioitem": it, "costcenter": costcenter, "project": project, "amount": x.value]
        }
    })
    return result
}

def jsonSlurper = new JsonSlurper()
def items = jsonSlurper.parseText(JsonFileUtils.parseJsonFileToStringValue("dipont/duigong_jingnei/tbhdpaymentpur002a_item.json"))
def resultList = constructDroolsCondition(items.rows)

println JsonOutput.toJson(resultList)