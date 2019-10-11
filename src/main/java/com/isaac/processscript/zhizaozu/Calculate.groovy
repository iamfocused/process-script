package com.isaac.processscript.zhizaozu

import com.isaac.processscript.util.JsonFileUtils
import groovy.json.JsonOutput
import groovy.json.JsonSlurper

import java.math.RoundingMode
import java.util.stream.Collectors

//工序主表
def totalStdStr = """
[{"工序编号":"111","工序名称":null,"分配工段编号":null,"计划开始时间":null,"计划结束时间":null,"工序类型":"车削","工序描述":null,"设备型号":null,"装夹件数（件）":"1","人员操作时间（秒）":"1","人工检测时间（秒）":"1","其它时间（秒）":"1","加工时间（秒）":null,"工序时间（秒）":null,"工序简图":"[]","加工程序":"[]","工序物料清单":null,"工序工具清单":null,"工序检验指标":null,"通用工步表":null,"车削工步表":"1568869434279","铣削工步表":null}]
"""

//主表item
def totalItemStr = JsonFileUtils.parseJsonFileToStringValue("zhizao.json")

//车削item
def cxStdItemStr = """{"headers":[{"block":"A","controlType":"t1","datePattern":null,"enumValues":[],"fieldType":"string","id":"tbheader1566744708879-header-1566746165617_tsnumber_\\\\u5de5\\\\u6b65\\\\u5e8f\\\\u53f7_2_string_t1_\$\$A","name":"\\\\u5de5\\\\u6b65\\\\u5e8f\\\\u53f7","readable":true,"seq":2,"type":"string","writable":true},{"block":"A","controlType":"t1","datePattern":null,"enumValues":[],"fieldType":"string","id":"tbheader1566744708879-header-1566746165617_knifesetnumber_\\\\u5200\\\\u7ec4\\\\u7f16\\\\u53f7_3_string_t1_\$\$A","name":"\\\\u5200\\\\u7ec4\\\\u7f16\\\\u53f7","readable":true,"seq":3,"type":"string","writable":true},{"block":"A","controlType":"t1","datePattern":null,"enumValues":[],"fieldType":"string","id":"tbheader1566744708879-header-1566746165617_knifesetname_\\\\u5200\\\\u7ec4\\\\u540d\\\\u79f0_4_string_t1_\$\$A","name":"\\\\u5200\\\\u7ec4\\\\u540d\\\\u79f0","readable":true,"seq":4,"type":"string","writable":true},{"block":"A","controlType":"t2","datePattern":null,"enumValues":[],"fieldType":"string","id":"tbheader1566744708879-header-1566746165617_tsdesc_\\\\u5de5\\\\u6b65\\\\u63cf\\\\u8ff0_5_string_t2_\$\$A","name":"\\\\u5de5\\\\u6b65\\\\u63cf\\\\u8ff0","readable":true,"seq":5,"type":"string","writable":true},{"block":"A","controlType":"t6","datePattern":null,"enumValues":[],"fieldType":"string","id":"tbheader1566744708879-header-1566746165617_tsVC_\\\\u5207\\\\u524a\\\\u901f\\\\u5ea6\\\\uff08m/min\\\\uff09_6_string_t6_\$\$A","name":"\\\\u5207\\\\u524a\\\\u901f\\\\u5ea6\\\\uff08m/min\\\\uff09","readable":true,"seq":6,"type":"string","writable":true},{"block":"A","controlType":"t6","datePattern":null,"enumValues":[],"fieldType":"string","id":"tbheader1566744708879-header-1566746165617_tsDCX_\\\\u6700\\\\u5927\\\\u8f66\\\\u524a\\\\u76f4\\\\u5f84\\\\uff08mm\\\\uff09_7_string_t6_\$\$A","name":"\\\\u6700\\\\u5927\\\\u8f66\\\\u524a\\\\u76f4\\\\u5f84\\\\uff08mm\\\\uff09","readable":true,"seq":7,"type":"string","writable":true},{"block":"A","controlType":"t6","datePattern":null,"enumValues":[],"fieldType":"string","id":"tbheader1566744708879-header-1566746165617_tsDCN_\\\\u6700\\\\u5c0f\\\\u8f66\\\\u524a\\\\u76f4\\\\u5f84\\\\uff08mm\\\\uff09_8_string_t6_\$\$A","name":"\\\\u6700\\\\u5c0f\\\\u8f66\\\\u524a\\\\u76f4\\\\u5f84\\\\uff08mm\\\\uff09","readable":true,"seq":8,"type":"string","writable":true},{"block":"A","controlType":"t6","datePattern":null,"enumValues":[],"fieldType":"string","id":"tbheader1566744708879-header-1566746165617_tsFR_\\\\u6bcf\\\\u8f6c\\\\u8fdb\\\\u7ed9\\\\u91cf\\\\uff08mm/rev\\\\uff09_9_string_t6_\$\$A","name":"\\\\u6bcf\\\\u8f6c\\\\u8fdb\\\\u7ed9\\\\u91cf\\\\uff08mm/rev\\\\uff09","readable":true,"seq":9,"type":"string","writable":true},{"block":"A","controlType":"t6","datePattern":null,"enumValues":[],"fieldType":"string","id":"tbheader1566744708879-header-1566746165617_tsLC_\\\\u5207\\\\u524a\\\\u957f\\\\u5ea6\\\\uff08mm\\\\uff09_10_string_t6_\$\$A","name":"\\\\u5207\\\\u524a\\\\u957f\\\\u5ea6\\\\uff08mm\\\\uff09","readable":true,"seq":10,"type":"string","writable":true},{"block":"A","controlType":"t1","datePattern":null,"enumValues":[],"fieldType":"string","id":"tbheader1566744708879-header-1566746165617_join-key_Key_10_string_t1_\$\$A","name":"Key","readable":true,"seq":10,"type":"string","writable":true},{"block":"A","controlType":"t6","datePattern":null,"enumValues":[],"fieldType":"string","id":"tbheader1566744708879-header-1566746165617_tsNOC_\\\\u5207\\\\u524a\\\\u6b21\\\\u6570\\\\uff08\\\\u6b21\\\\uff09_11_string_t6_\$\$A","name":"\\\\u5207\\\\u524a\\\\u6b21\\\\u6570\\\\uff08\\\\u6b21\\\\uff09","readable":true,"seq":11,"type":"string","writable":true},{"block":"A","controlType":"t6","datePattern":null,"enumValues":[],"fieldType":"string","id":"tbheader1566744708879-header-1566746165617_tsNOLIFE_\\\\u5200\\\\u5177\\\\u5bff\\\\u547d\\\\uff08\\\\u4ef6\\\\uff09_12_string_t6_\$\$A","name":"\\\\u5200\\\\u5177\\\\u5bff\\\\u547d\\\\uff08\\\\u4ef6\\\\uff09","readable":true,"seq":12,"type":"string","writable":true},{"block":"HA","controlType":"t6","datePattern":null,"enumValues":[],"fieldType":"string","id":"tbheader1566744708879-header-1566746165617_tsRPMAVE_\\\\u5e73\\\\u5747\\\\u8f6c\\\\u901f\\\\uff08rev/min\\\\uff09_13_string_t6_\$\$HA","name":"\\\\u5e73\\\\u5747\\\\u8f6c\\\\u901f\\\\uff08rev/min\\\\uff09","readable":true,"seq":13,"type":"string","writable":true},{"block":"HA","controlType":"t6","datePattern":null,"enumValues":[],"fieldType":"string","id":"tbheader1566744708879-header-1566746165617_tsVF_\\\\u8fdb\\\\u7ed9\\\\u901f\\\\u5ea6\\\\uff08mm/min\\\\uff09_14_string_t6_\$\$HA","name":"\\\\u8fdb\\\\u7ed9\\\\u901f\\\\u5ea6\\\\uff08mm/min\\\\uff09","readable":true,"seq":14,"type":"string","writable":true},{"block":"HA","controlType":"t6","datePattern":null,"enumValues":[],"fieldType":"string","id":"tbheader1566744708879-header-1566746165617_tsTMENG_\\\\u5de5\\\\u8fdb\\\\u65f6\\\\u95f4\\\\uff08\\\\u79d2\\\\uff09_15_string_t6_\$\$HA","name":"\\\\u5de5\\\\u8fdb\\\\u65f6\\\\u95f4\\\\uff08\\\\u79d2\\\\uff09","readable":true,"seq":15,"type":"string","writable":true},{"block":"A","controlType":"t6","datePattern":null,"enumValues":[],"fieldType":"string","id":"tbheader1566744708879-header-1566746165617_tsTMTCT_\\\\u6362\\\\u5200\\\\u65f6\\\\u95f4\\\\uff08\\\\u79d2\\\\uff09_16_string_t6_\$\$A","name":"\\\\u6362\\\\u5200\\\\u65f6\\\\u95f4\\\\uff08\\\\u79d2\\\\uff09","readable":true,"seq":16,"type":"string","writable":true},{"block":"A","controlType":"t6","datePattern":null,"enumValues":[],"fieldType":"string","id":"tbheader1566744708879-header-1566746165617_tsTMPOSS_\\\\u5355\\\\u6b21\\\\u5b9a\\\\u4f4d\\\\u65f6\\\\u95f4\\\\uff08\\\\u79d2\\\\uff09_17_string_t6_\$\$A","name":"\\\\u5355\\\\u6b21\\\\u5b9a\\\\u4f4d\\\\u65f6\\\\u95f4\\\\uff08\\\\u79d2\\\\uff09","readable":true,"seq":17,"type":"string","writable":true},{"block":"HA","controlType":"t6","datePattern":null,"enumValues":[],"fieldType":"string","id":"tbheader1566744708879-header-1566746165617_tsTMPOS_\\\\u5b9a\\\\u4f4d\\\\u65f6\\\\u95f4\\\\uff08\\\\u79d2\\\\uff09_18_string_t6_\$\$HA","name":"\\\\u5b9a\\\\u4f4d\\\\u65f6\\\\u95f4\\\\uff08\\\\u79d2\\\\uff09","readable":true,"seq":18,"type":"string","writable":true},{"block":"A","controlType":"t6","datePattern":null,"enumValues":[],"fieldType":"string","id":"tbheader1566744708879-header-1566746165617_tsTMSUP_\\\\u8f85\\\\u52a9\\\\u65f6\\\\u95f4\\\\uff08\\\\u79d2\\\\uff09_19_string_t6_\$\$A","name":"\\\\u8f85\\\\u52a9\\\\u65f6\\\\u95f4\\\\uff08\\\\u79d2\\\\uff09","readable":true,"seq":19,"type":"string","writable":true}],"rows":[["1",null,null,null,null,null,null,null,null,"1568869434279",null,null,0,1,0,null,null,0,null]],"tableName":"tbheader1566744708879-header-1566746165617"}"""

//通用表
def tyStdStr = """
[]"""

//铣削表
def xxStdStr = """
[]"""

//1567472879202
//1568869434279
//车削表
def cxStdStr = """
[{"工步序号":"1","刀组编号":null,"刀组名称":null,"工步描述":null,"切削速度（m/min）":null,"最大车削直径（mm）":null,"最小车削直径（mm）":null,"每转进给量（mm/rev）":null,"切削长度（mm）":null,"Key":"1568869434279","切削次数（次）":null,"刀具寿命（件）":null,"平均转速（rev/min）":null,"进给速度（mm/min）":null,"工进时间（秒）":null,"换刀时间（秒）":null,"单次定位时间（秒）":null,"定位时间（秒）":null,"辅助时间（秒）":null}]
"""

def jsonSlurper = new JsonSlurper()
List totalStd = jsonSlurper.parseText(totalStdStr)
List tyStd = jsonSlurper.parseText(tyStdStr)
List cxStd = jsonSlurper.parseText(cxStdStr)
List xxStd = jsonSlurper.parseText(xxStdStr)

def totalItem = jsonSlurper.parseText(totalItemStr)
//车削
def cxItem = jsonSlurper.parseText(cxStdItemStr)
//铣削
def xxItem = []


//工序节拍器
//println totalStd.stream().mapToDouble({it.get('工序时间（秒）').toBigDecimal().divide(it.get('装夹件数（件）').toBigDecimal(),mc).toDouble()}).max().orElse(0)

//产能测算（件/年）

//总的工序-->工序编号，工序类型---> 对应字表key ----> 获取字表信息 ----> 计算结果赋值到主表中 ----> 主表中各种字段计算值赋值到基本信息
//"通用工步表":null,"车削工步表":"1567472879202","铣削工步表
def list = totalStd.stream().map({
    def li = []
    li<<it.工序编号
    li<<it.工序类型
    switch (it.工序类型){
        case '通用':li<<it.通用工步表;break
        case '铣削':li<<it.铣削工步表;break
        case '车削':li<<it.车削工步表;break
        default: throw new RuntimeException("找不到对应工序类型") ;break
    }
    li<<it.get('装夹件数（件）')?:'0'
    li<<it.get('人员操作时间（秒）')?:'0'
    li<<it.get('人工检测时间（秒）')?:'0'
    li<<it.get('其它时间（秒）')?:'0'
}).collect(Collectors.toList())

list.each { gx ->
    switch (gx[1]){
        case '通用':
            def collect = tyStd.stream().filter({ ty -> gx[2].split(",").contains(ty.Key) }).collect(Collectors.toList())
            //赋值给主表加工时间TMMT index 12

            def tmmt = collect.stream().mapToDouble({
                def gjsj = it.get('工进时间（秒）')?:'0'
                def fzsj = it.get('辅助时间（秒）')?:'0'
                gjsj.toDouble() + fzsj.toDouble()
            }).sum().toBigDecimal().setScale(2, RoundingMode.HALF_UP)
            //赋值给主表工序时间TMOP index 13
            def tmop = tmmt.add(gx[4].toBigDecimal()).add(gx[5].toBigDecimal()).add(gx[6].toBigDecimal()).setScale(2, RoundingMode.HALF_UP)

            totalStd.each{item->
                if(item.get('工序编号') == gx[0]){
                    item.put('工序时间（秒）',tmop.toDouble().toString())
                }
            }

            totalItem.each {k,v ->
                if(k == 'rows'){
                    v.each {
                        if(it[0] == gx[0]) {
                            it[12] = tmmt
                            it[13] = tmop
                        }
                    }
                }
            }
            break
        case '车削':
            def collect = cxStd.stream().filter({ cx -> gx[2].split(",").contains(cx.Key) }).collect(Collectors.toList())
            def tmmt = collect.stream().mapToDouble({ cx->
                def cxsd = cx.get('切削速度（m/min）')?:'0'
                def zxcxzj = cx.get('最小车削直径（mm）')?:'1'
                def zdcxzj = cx.get('最大车削直径（mm）')?:'0'
                def tsRPMAVE = cxsd.toBigDecimal().multiply(BigDecimal.valueOf(2000)).divide(Math.PI,2, RoundingMode.HALF_UP).divide(zxcxzj.toBigDecimal().add(zdcxzj.toBigDecimal()),2, RoundingMode.HALF_UP)

                def mzjgl = cx.get('每转进给量（mm/rev）')?:'0'
                def tsVF = mzjgl.toBigDecimal().multiply(tsRPMAVE).toBigDecimal().setScale(2, RoundingMode.HALF_UP)
                tsVF = tsVF == 0 ? 1 : tsVF
                def dcdwsj = cx.get('单次定位时间（秒）')?:'0'
                def cxcs = cx.get('切削次数（次）')?:'0'
                def tsTMPOS = dcdwsj.toBigDecimal().multiply(cxcs.toBigDecimal()).multiply(gx[3].toBigDecimal()).toBigDecimal().setScale(2, RoundingMode.HALF_UP)

                def qxcd = cx.get('切削长度（mm）')?:'0'
                def tsTMENG = qxcd.toBigDecimal().multiply(BigDecimal.valueOf(60)).multiply(cxcs.toBigDecimal()).multiply(gx[3].toBigDecimal()).divide(tsVF,2, RoundingMode.HALF_UP)

                cxItem.each {k,v ->
                    if(k == 'rows'){
                        v.each{
                            if(it[0] == cx.get('工步序号') && cx.Key == it[9]){
                                it[12] = tsRPMAVE
                                it[13] = tsVF
                                it[17] = tsTMPOS
                                it[14] = tsTMENG
                            }
                        }
                    }
                }

                def hdsj = cx.get('换刀时间（秒）')?:'0'
                def fzsj = cx.get('辅助时间（秒）')?:'0'
                return tsTMENG.add(tsTMPOS).add(hdsj.toBigDecimal()).add(fzsj.toBigDecimal()).toDouble()
            }).sum().toBigDecimal().setScale(2, RoundingMode.HALF_UP)
            def tmop = tmmt.add(gx[4].toBigDecimal()).add(gx[5].toBigDecimal()).add(gx[6].toBigDecimal()).setScale(2, RoundingMode.HALF_UP)
            totalStd.each{item->
                if(item.get('工序编号') == gx[0]){
                    item.put('工序时间（秒）',tmop.toDouble().toString())
                }
            }

            totalItem.each {k,v ->
                if(k == 'rows'){
                    v.each{
                        if(it[0] == gx[0]){
                            it[12] = tmmt
                            it[13] = tmop
                        }
                    }
                }
            }
            break
        case '铣削':
            def collect = xxStd.stream().filter({ xx -> gx[2].split(",").contains(xx.Key) }).collect(Collectors.toList())
            def tmmt = collect.stream().mapToDouble({ xx ->
                def qxsd = xx.get('切削速度（m/min）')?:'0'
                def qxzj = xx.get('切削直径（mm）')?:'0'
                def msRPM = qxsd.toBigDecimal().multiply(BigDecimal.valueOf(1000)).divide(Math.PI,2, RoundingMode.HALF_UP).divide(qxzj.toBigDecimal(),2, RoundingMode.HALF_UP)

                def mcjgl = xx.get('每齿进给量（mm/tooth）')?:'0'
                def yxcs = xx.get('有效齿数&amp;nbsp;')?:'0'
                def msVF = mcjgl.toBigDecimal().multiply(yxcs.toBigDecimal()).multiply(msRPM).toBigDecimal().setScale(2, RoundingMode.HALF_UP)

                def dcdwsj = xx.get('单次定位时间（秒）')?:'0'
                def qxcs = xx.get('切削次数（次）')?:'0'
                def msTMPOS = dcdwsj.toBigDecimal().multiply(qxcs.toBigDecimal()).multiply(gx[3].toBigDecimal()).toBigDecimal().setScale(2, RoundingMode.HALF_UP)

                def qxcd = xx.get('切削长度（mm）')?:'0'
                def msTMENG = qxcd.toBigDecimal().multiply(BigDecimal.valueOf(60)).multiply(qxcs.toBigDecimal()).multiply(gx[3].toBigDecimal()).divide(msVF,2, RoundingMode.HALF_UP)

                xxItem.each {k,v ->
                    if(k == 'rows'){
                        v.each{
                            if(it[0] == xx.get('工步序号') && xx.Key == it[9]){
                                it[12] = msRPM
                                it[13] = msVF
                                it[17] = msTMPOS
                                it[14] = msTMENG
                            }
                        }
                    }
                }

                def hdsj=xx.get('换刀时间（秒）')?:'0'
                def fzsj = xx.get('辅助时间（秒）')?:'0'
                return msTMENG.add(hdsj.toBigDecimal()).add(msTMPOS).add(fzsj.toBigDecimal()).toDouble()
            }).sum().toBigDecimal().setScale(2, RoundingMode.HALF_UP)
            def tmop = tmmt.add(gx[4].toBigDecimal()).add(gx[5].toBigDecimal()).add(gx[6].toBigDecimal())
            totalStd.each{item->
                if(item.get('工序编号') == gx[0]){
                    item.put('工序时间（秒）',tmop.toDouble().toString())
                }
            }
            totalItem.each {k,v ->
                if(k == 'rows'){
                    v.each{
                        if(it[0] == gx[0]){
                            it[12] = tmmt
                            it[13] = tmop
                        }
                    }
                }
            }
            break
        default:
            throw new RuntimeException('没有对应到工序类型');break
    }
}
println JsonOutput.toJson(totalItem)
println(JsonOutput.toJson(cxItem))
