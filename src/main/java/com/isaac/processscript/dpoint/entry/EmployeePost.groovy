package com.isaac.processscript.dpoint.entry

import groovy.json.JsonOutput

def postBody = new HashMap()
postBody.put("name", "员工姓名")
postBody.put("sn", "员工sn")
postBody.put("identityCard", "身份证")
postBody.put("otherIdentify", "其它识别号")
postBody.put("ncCode", "NC人员档案编码")
postBody.put("lastName", "姓")
postBody.put("middleName", "中间名")
postBody.put("firstName", "名")
postBody.put("sex", "性别")
postBody.put("maritalStatus", "婚姻状况")
postBody.put("nationality", "国籍")
postBody.put("birth", "出生日期（long）")
postBody.put("homeAddress", "家庭住址")
postBody.put("mobilePhone", "手机号码")
postBody.put("homePhone", "家庭电话")
postBody.put("mail", "工作邮箱")
postBody.put("otherMail", "其它邮箱")
postBody.put("roles", ["职位A"])
postBody.put("workType", "工作类别")
postBody.put("hireStatus", "雇佣状态")
postBody.put("hireFrom", "")
postBody.put("hireTo", "")
postBody.put("expenseType", "报销级别")
postBody.put("openingBank", "开户银行")
postBody.put("bankProvince", "银行所在省（市）")
postBody.put("bankCity", "银行所在市（区）")
postBody.put("wagesCard", "工资卡")
postBody.put("accountName", "银行账户名")
postBody.put("accountNo", "银行账号")
postBody.put("workAddress", "工作地址")
postBody.put("belongToDepartment", ["sn":"部门sn"])

def costCenters = new ArrayList()
def costCenter = new HashMap()
costCenter.put("costCenter", ["code":"成本中心code"])
costCenter.put("rate",12)
costCenters.add(costCenter)
postBody.put("chargeToCostCenter", costCenters)

def managers = new ArrayList()
def manager = new HashMap()
manager.put("manager", ["sn":"manager sn"])
manager.put("roles", [])
managers.add(manager)
postBody.put("managers", managers)

println(JsonOutput.toJson(postBody))

/* list <-- map*/
def ccs = new ArrayList()
def data = [["name":"名字1","code": "S111", "rate":11],["name":"名字2","code": "S222", rate: 14]]
data.forEach{it -> def cc = new HashMap();cc.put("costCenter", ["code":it.code]);cc.put("rate",it.rate);ccs.add(cc)}
println(JsonOutput.toJson(ccs))

/* */