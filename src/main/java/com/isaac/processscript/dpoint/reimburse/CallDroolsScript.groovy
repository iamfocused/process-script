package com.isaac.processscript.dpoint.reimburse

import com.isaac.processscript.util.JsonFileUtils
import groovy.json.JsonOutput
import groovy.json.JsonSlurper

def jsonSlurper = new JsonSlurper()

def specialDrools = []
def tbhdexpdetfn01a_item_json = JsonFileUtils.parseJsonFileToStringValue("dipont/tbhdexpdetfn01a_item_json.json")
def tbhdexpdetfn01a_item = jsonSlurper.parseText(tbhdexpdetfn01a_item_json)

//根据成本中心、收支项目分组——目的是金额这个条件，金额是当前成本中心+项目分到每个项目上的
tbhdexpdetfn01a_item.rows.groupBy { item -> item[1].code + "_" + item[2].code }.each
        {k, v ->
            def curAmount = 0
            def ioitems = new HashSet()
            v.each{
                def amount = new java.math.BigDecimal(it[14]).setScale(2, java.math.RoundingMode.HALF_UP)
                curAmount += amount
                ioitems.add(it[0].code)
            }
            def keyArr = k.split('_')
            def costCenter = keyArr[0]
            def project = keyArr[1]
            ioitems.each {
                specialDrools<<"""{"costcenter":"${costCenter}","project":"${project}","amount":"${curAmount}","ioitem":"${it}"}"""
            }
        }

println(specialDrools)

/**
 * 报销明细是一个表格
 *        |
 * 整理表格中的数据，作为Drools的条件——成本中心（同一个）、项目、金额、收支项目，金额分摊到项目
 *        |
 * 调用Drools
 *        |
 * 对Drools返回的数据做处理——目的是，将表格中的明细按照审批规则分组，对应的一些明细对应到审批人
 */