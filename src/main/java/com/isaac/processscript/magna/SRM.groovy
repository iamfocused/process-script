package com.isaac.processscript.magna

import com.isaac.processscript.util.JsonFileUtils
import groovy.json.JsonSlurper
import groovy.json.JsonOutput

def jsonSlurper = new JsonSlurper()
def tbhddetail_item = jsonSlurper.parseText(JsonFileUtils.parseJsonFileToStringValue("magna.json"))
def tbhddetail_item_std = jsonSlurper.parseText("""
[{"matinfo":{"code":"MAT001","name":"MAT001/大灯接触开关 ￥3"},"planqty":"10000","remainqty":"10000","prcprice":"3","orderdate":null,"orderqty":null,"batch":null,"deliveryqty":null,"deliverydate":null,"arrivaldate":null,"accnumber":null,"defnumber":null,"amount":null},{"matinfo":{"code":"MAT002","name":"MAT002/小红灯 ￥4.5"},"planqty":"20000","remainqty":"20000","prcprice":"4.5","orderdate":null,"orderqty":null,"batch":null,"deliveryqty":null,"deliverydate":null,"arrivaldate":null,"accnumber":null,"defnumber":null,"amount":null}]

""")

//本地var
def std = jsonSlurper.parseText("""
[{"matinfo":{"code":"MAT001","name":"MAT001/大灯接触开关 ￥3"},"planqty":"10000","remainqty":"10000","prcprice":"3","orderdate":"1567612800000","orderqty":"1","batch":"1","deliveryqty":"1","deliverydate":"1567612800000","arrivaldate":null,"accnumber":null,"defnumber":null,"amount":null}]
""")
//std.each {println(it.matinfo)}


tbhddetail_item.each{k,v ->
    if(k == 'rows') {
        v.each{item ->
            def find = std.find{it.matinfo == item[0]}
            if(find){
                item[2] = item[2].toBigDecimal().subtract(find.deliveryqty.toBigDecimal())
            }

        }
    }
}

tbhddetail_item_std.each{item ->
    def find = std.find{it.matinfo == item.matinfo}
    if(find) {
        item.remainqty = item.remainqty.toBigDecimal().subtract(find.deliveryqty.toBigDecimal())
    }

}

println JsonOutput.toJson(tbhddetail_item)
println JsonOutput.toJson(tbhddetail_item_std)
