package com.isaac.processscript.zhizaozu

import com.isaac.processscript.util.JsonFileUtils
import groovy.json.JsonOutput
import groovy.json.JsonSlurper

import java.math.RoundingMode
import java.util.stream.Collectors

def jsonSlurper = new JsonSlurper()

//主表Std
def totalStd = jsonSlurper.parseText(JsonFileUtils.parseJsonFileToStringValue("zhizaozu/mainStd.json"))
if (totalStd.size() == 0) {
    return
}

//主表item
def totalItem = jsonSlurper.parseText(JsonFileUtils.parseJsonFileToStringValue("zhizaozu/mainItem.json"))

//车削item
def cxItem = jsonSlurper.parseText(JsonFileUtils.parseJsonFileToStringValue("zhizaozu/chexueItem.json"))
def cxStd = jsonSlurper.parseText(JsonFileUtils.parseJsonFileToStringValue("zhizaozu/chexueStd.json"))

//铣削item
def xxItem = jsonSlurper.parseText(JsonFileUtils.parseJsonFileToStringValue("zhizaozu/xixueItem.json"))
def xxStd = jsonSlurper.parseText(JsonFileUtils.parseJsonFileToStringValue("zhizaozu/xixueStd.json"))


//通用工步子表
def tyStd = jsonSlurper.parseText(JsonFileUtils.parseJsonFileToStringValue("zhizaozu/tongyongStd.json"))


def list = totalStd.stream().map({
    def li = []
    li << it.工序编号
    li << it.工序类型
    switch (it.工序类型) {
        case '通用': li << it.通用工步表; break
        case '铣削': li << it.铣削工步表; break
        case '车削': li << it.车削工步表; break
        default: throw new RuntimeException("找不到对应工序类型"); break
    }
    li << (it.get('装夹件数（件）') ?: '0')//3
    li << (it.get('人员操作时间（秒）') ?: '0')//4
    li << (it.get('人工检测时间（秒）') ?: '0')//5
    li << (it.get('其它时间（秒）') ?: '0')//6

    li << it.get('工序物料清单')//7
    li << it.get('工序工具清单')//8
}).collect(Collectors.toList())

list.each { gx ->

    switch (gx[1]) {
        case '通用':
            def collect = tyStd.stream().filter({ ty -> gx[2].split(",").contains(ty.Key) }).collect(Collectors.toList())

            def tmmt = collect.stream().mapToDouble({
                def gjsj = it.get('工进时间（秒）') ?: '0'
                def fzsj = it.get('辅助时间（秒）') ?: '0'
                gjsj.toDouble() + fzsj.toDouble()
            }).sum().toBigDecimal().setScale(2, RoundingMode.HALF_UP)
            //赋值给主表工序时间TMOP index 13
            def tmop = tmmt.add(gx[4].toBigDecimal()).add(gx[5].toBigDecimal()).add(gx[6].toBigDecimal()).setScale(2, RoundingMode.HALF_UP)

            totalStd.each { item ->
                if (item.get('工序编号') == gx[0]) {
                    item.put('工序时间（秒）', tmop.toDouble().toString())
                }
            }

            totalItem.each { k, v ->
                if (k == 'rows') {
                    v.each {
                        if (it[0] == gx[0]) {
                            it[12] = tmmt
                            it[13] = tmop
                        }
                    }
                }
            }
            break
        case '车削':
            def collect = cxStd.stream().filter({ cx -> gx[2].split(",").contains(cx.Key) }).collect(Collectors.toList())
            def tmmt = collect.stream().mapToDouble({ cx ->
                def cxsd = cx.get('切削速度（m/min）') ?: '0'
                def zxcxzj = cx.get('最小车削直径（mm）') ?: '0'
                def zdcxzj = cx.get('最大车削直径（mm）') ?: '0'
                def tsRPMAVE = 0.toBigDecimal()
                if (zxcxzj != '0') {
                    tsRPMAVE = cxsd.toBigDecimal().multiply(BigDecimal.valueOf(2000)).divide(Math.PI as BigDecimal, 2, RoundingMode.HALF_UP).divide(zxcxzj.toBigDecimal().add(zdcxzj.toBigDecimal()), 2, RoundingMode.HALF_UP)
                }

                def mzjgl = cx.get('每转进给量（mm/rev）') ?: '0'
                def tsVF = mzjgl.toBigDecimal().multiply(tsRPMAVE).toBigDecimal().setScale(2, RoundingMode.HALF_UP)
                def dcdwsj = cx.get('单次定位时间（秒）') ?: '0'
                def cxcs = cx.get('切削次数（次）') ?: '0'
                def tsTMPOS = dcdwsj.toBigDecimal().multiply(cxcs.toBigDecimal()).multiply(gx[3].toBigDecimal()).toBigDecimal().setScale(2, RoundingMode.HALF_UP)

                def qxcd = cx.get('切削长度（mm）') ?: '0'

                def tsTMENG = 0.toBigDecimal()
                if (tsVF > 0) {
                    tsTMENG = qxcd.toBigDecimal().multiply(BigDecimal.valueOf(60)).multiply(cxcs.toBigDecimal()).multiply(gx[3].toBigDecimal()).divide(tsVF, 2, RoundingMode.HALF_UP)
                }

                if (tsRPMAVE == 0) {
                    tsRPMAVE = null
                }
                if (tsVF == 0) {
                    tsVF = null
                }
                cxItem.each { k, v ->
                    if (k == 'rows') {
                        v.each {
                            if (it[0] == cx.get('工步序号') && cx.Key == it[9]) {
                                it[12] = tsRPMAVE
                                it[13] = tsVF
                                it[17] = tsTMPOS
                                it[14] = tsTMENG
                            }
                        }
                    }
                }

                def hdsj = cx.get('换刀时间（秒）') ?: '0'
                def fzsj = cx.get('辅助时间（秒）') ?: '0'
                return tsTMENG.add(tsTMPOS).add(hdsj.toBigDecimal()).add(fzsj.toBigDecimal()).toDouble()
            }).sum().toBigDecimal().setScale(2, RoundingMode.HALF_UP)
            def tmop = tmmt.add(gx[4].toBigDecimal()).add(gx[5].toBigDecimal()).add(gx[6].toBigDecimal()).setScale(2, RoundingMode.HALF_UP)
            totalStd.each { item ->
                if (item.get('工序编号') == gx[0]) {
                    item.put('工序时间（秒）', tmop.toDouble().toString())
                }
            }

            totalItem.each { k, v ->
                if (k == 'rows') {
                    v.each {
                        if (it[0] == gx[0]) {
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
                def qxsd = xx.get('切削速度（m/min）') ?: '0'
                def qxzj = xx.get('切削直径（mm）') ?: '0'
                def msRPM = 0.toBigDecimal()
                if (qxzj != '0') {
                    msRPM = qxsd.toBigDecimal().multiply(BigDecimal.valueOf(1000)).divide(Math.PI as BigDecimal, 2, RoundingMode.HALF_UP).divide(qxzj.toBigDecimal(), 2, RoundingMode.HALF_UP)
                }

                def mcjgl = xx.get('每齿进给量（mm/tooth）') ?: '0'
                def yxcs = xx.get('有效齿数&amp;nbsp;') ?: '0'
                def msVF = mcjgl.toBigDecimal().multiply(yxcs.toBigDecimal()).multiply(msRPM).toBigDecimal().setScale(2, RoundingMode.HALF_UP)

                def dcdwsj = xx.get('单次定位时间（秒）') ?: '0'
                def qxcs = xx.get('切削次数（次）') ?: '0'
                def msTMPOS = dcdwsj.toBigDecimal().multiply(qxcs.toBigDecimal()).multiply(gx[3].toBigDecimal()).toBigDecimal().setScale(2, RoundingMode.HALF_UP)

                def qxcd = xx.get('切削长度（mm）') ?: '0'
                def msTMENG = 0.toBigDecimal()
                if (msVF > 0) {
                    msTMENG = qxcd.toBigDecimal().multiply(BigDecimal.valueOf(60)).multiply(qxcs.toBigDecimal()).multiply(gx[3].toBigDecimal()).divide(msVF, 2, RoundingMode.HALF_UP)
                }

                if (msRPM == 0) {
                    msRPM = null
                }

                if (msVF == 0) {
                    msVF = null
                }
                xxItem.each { k, v ->
                    if (k == 'rows') {
                        v.each {
                            if (it[0] == xx.get('工步序号') && xx.Key == it[9]) {
                                it[12] = msRPM
                                it[13] = msVF
                                it[17] = msTMPOS
                                it[14] = msTMENG
                            }
                        }
                    }
                }

                def hdsj = xx.get('换刀时间（秒）') ?: '0'
                def fzsj = xx.get('辅助时间（秒）') ?: '0'
                return msTMENG.add(hdsj.toBigDecimal()).add(msTMPOS).add(fzsj.toBigDecimal()).toDouble()
            }).sum().toBigDecimal().setScale(2, RoundingMode.HALF_UP)
            def tmop = tmmt.add(gx[4].toBigDecimal()).add(gx[5].toBigDecimal()).add(gx[6].toBigDecimal())
            totalStd.each { item ->
                if (item.get('工序编号') == gx[0]) {
                    item.put('工序时间（秒）', tmop.toDouble().toString())
                }
            }
            totalItem.each { k, v ->
                if (k == 'rows') {
                    v.each {
                        if (it[0] == gx[0]) {
                            it[12] = tmmt
                            it[13] = tmop
                        }
                    }
                }
            }
            break
        default:
            throw new RuntimeException('没有对应到工序类型'); break
    }
}

println '主表item'
println JsonOutput.toJson(totalItem)

println ''

println '车削Item'
println JsonOutput.toJson(cxItem)

println ''

println '铣削Item'
println JsonOutput.toJson(xxItem)