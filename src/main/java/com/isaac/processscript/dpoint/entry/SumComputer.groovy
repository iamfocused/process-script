package com.isaac.processscript.dpoint.entry

import java.util.stream.Collectors


//计算采购电脑总金额&&确认采购时附件等信息必填判断
def tbhd_std1 = "[{\"matitem\":\"电脑\",\"amnout\":\"1\",\"estimateprice\":\"123123\",\"unit\":\"rmb\",\"usage\":null,\"reqdatet\":\"1560700800000\",\"cpno\":{\"name\":\"联想ThinkPad E480（36CD）/普通员工\"},\"comment\":\"1\",\"actualprice\":null},{\"matitem\":\"22\",\"amnout\":\"1\",\"estimateprice\":\"2123\",\"unit\":null,\"usage\":null,\"reqdatet\":null,\"cpno\":{\"name\":\"IMAC/设计员工\"},\"comment\":null,\"actualprice\":null}]"

def tbhd_std2 = "[]"

def jsonSlurper = new groovy.json.JsonSlurper()
def itemObj = jsonSlurper.parseText(tbhd_std1)
if(itemObj.size() == 0)
    print("aaaa")

def sum = itemObj.stream().mapToDouble() { item -> Double.parseDouble(item.estimateprice) }.sum()

//costcenter

def tbhd_std = "[{\"costcenter\":{\"code\":\"0803\",\"name\":\"0803/互联网发展事业部-运营部 I&D-Operation\"},\"project\":{\"code\":\"99\",\"name\":\"无\"},\"function\":\"服务人员\",\"proportion\":\"50\",\"startdate\":\"1560441600000\",\"enddate\":\"1560441600000\"},{\"costcenter\":{\"code\":\"0802\",\"name\":\"0802/互联网发展事业部-内容研发部 I&D-Content R&D\"},\"project\":{\"code\":\"99\",\"name\":\"无\"},\"function\":\"服务人员\",\"proportion\":\"50\",\"startdate\":\"1560441600000\",\"enddate\":\"1560528000000\"},{\"costcenter\":{\"code\":\"030101\",\"name\":\"030101/独立学校总部财务 ISS-HQ-Finance\"},\"project\":{\"code\":\"99\",\"name\":\"无\"},\"function\":\"服务人员\",\"proportion\":\"50\",\"startdate\":\"1560441600000\",\"enddate\":\"1560528000000\"}]"
def costCenterObj = jsonSlurper.parseText(tbhd_std)
def costCenterList = costCenterObj.stream().map() { item -> ['costcenter':item.costcenter.code ]}.collect(java.util.stream.Collectors.toList())
//print(groovy.json.JsonOutput.toJson(costCenterList))

//drools admin
def adminUsers = "[\n" +
        "    {\n" +
        "        \"costcenter\": \"0803\",\n" +
        "        \"buyer\": \"100011\"\n" +
        "    },\n" +
        "    {\n" +
        "        \"costcenter\": \"0802\",\n" +
        "        \"buyer\": \"100012\"\n" +
        "    },\n" +
        "    {\n" +
        "        \"costcenter\": \"030101\",\n" +
        "        \"buyer\": \"100011\"\n" +
        "    }\n" +
        "]"
List adminUsersObj = jsonSlurper.parseText(adminUsers)
def adminIds = adminUsersObj.stream().map() { item -> item.buyer }.distinct().collect(Collectors.toList())
adminIds.each {}
print(adminIds)