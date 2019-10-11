package com.isaac.processscript.dpoint.entry

import groovy.json.JsonSlurper;
import groovy.json.JsonOutput;
//definition
class Employee {
    String sn;
    String ncCode;
    String identityCard;
    Long identityCardFrom;
    Long identityCardTo;
    String name;
    String sex;
    String maritalStatus;
    String nationality;//国籍
    Long birth;
    String birthCondition;//生育情况
    String politicsStatus;//政治面貌
    String censusRegister;//户籍
    String diploma;//最高学历
    String degree;//最高学位
    String major;//专业
    String studyAbroad;//是否有留学背景
    String homeAddress;
    String securityPayment;//社保缴纳地
    String placeOfLegalDocuments;//法律文书送达地
    String mobilePhone;
    String mail;
    List<String> roles;
    String workType;
    String hireStatus;
    Long entryGroupTime;//进入集团日期
    Long hireFrom;//合同开始日期
    Long hireTo;
    Long entryTime;//入职日期
    Long dimissionTime;//离职日期
    String expenseType;//报销级别
    String wagesCard;//工资卡号
    String bankProvince;
    String bankCity;
    String openingBank;//开户银行
    String accountName;//银行账户名
    String accountNo;//银行账号
    String corpSn;
    String depSn;
    List<Manager> managers;
    //List<Map<String,String>> fnDeleagatees;//报销委托人[{"sn":"snCode"}]
    String workAddress;
    List<CostCenter> chargeToCostCenter;
    String otherIdentify;
}
class Manager {
    Map<String, String> manager;//sn:""
    List<String> roles;
}

class CostCenter {
    Map<String, String> costCenter;//code:""
    def rate;
    String projectCode;
    String projectName;
    String function;
    Long from;
    Long to;
}
execution = task.getExecution();
//script
//build body
def jsonSlurper = new JsonSlurper()
def corpMap = jsonSlurper.parseText(execution.getVariable("legalCorp"));
def corpSn = corpMap.get("code");

def deptSn = "";
def deptVar = execution.getVariable("department");
if (deptVar != null){
    def deptMap = jsonSlurper.parseText(deptVar);
    deptSn = deptMap.get("code");
}

def costCenterVar = execution.getVariable("tbhdentryprocess_item_std");
def result = jsonSlurper.parseText(costCenterVar);
def chargeToCostCenter = result.stream()
        .map()
{ it ->
    new CostCenter(
            costCenter: ["code": it.costcenter.code],
            rate: it.proportion,
            projectCode: it.project.code,
            projectName: it.project.name,
            function: it.function,
            from: Long.parseLong(it.startdate),
            to: Long.parseLong(it.enddate)
    )
}
.collect(java.util.stream.Collectors.toList())

def employee = new Employee(
        sn: execution.getVariable("sn"),
        ncCode: execution.getVariable("employeeId"),
        identityCard: execution.getVariable("idNumber"),
        identityCardFrom: Long.parseLong(execution.getVariable("idNumberValidityPeriodFrom")),
        identityCardTo: Long.parseLong(execution.getVariable("idNumberValidityPeriodTo")),
        name: execution.getVariable("name"),
        sex: execution.getVariable("sex"),
        maritalStatus: execution.getVariable("maritalStatus"),
        nationality: execution.getVariable("citizenship"),
        birth: Long.parseLong(execution.getVariable("birthday")),
        birthCondition: execution.getVariable("fertilityStatus"),
        politicsStatus: execution.getVariable("politicalStatus"),
        censusRegister: execution.getVariable("census"),
        diploma: execution.getVariable("educationalBackground"),
        degree: execution.getVariable("bachelorOfScience"),
        major: execution.getVariable("major"),
        studyAbroad: execution.getVariable("studyAbroad"),
        homeAddress: execution.getVariable("residentialAddress"),
        securityPayment: execution.getVariable("socialSecurityPayment"),
        placeOfLegalDocuments: execution.getVariable("placeOfLegalDocuments"),
        mobilePhone: execution.getVariable("contactPhone"),
        mail: execution.getVariable("companyEmail"),
        roles: [execution.getVariable(position)],
        workType: execution.getVariable("employmentForms"),
        hireStatus: "在职",
        entryGroupTime: Long.parseLong(execution.getVariable("comeTime")),
        hireFrom: Long.parseLong(execution.getVariable("hireFrom")),
        hireTo: Long.parseLong(execution.getVariable("hireTo")),
        entryTime: Long.parseLong(execution.getVariable("actualEntryTime")),
        dimissionTime: null,
        expenseType: execution.getVariable("rank"),
        wagesCard: execution.getVariable("salaryCardNumber"),
        bankProvince: execution.getVariable("bankProvince"),
        bankCity:execution.getVariable("bankCity"),
        openingBank: execution.getVariable("payrollBank"),
        accountNo: execution.getVariable("accountNo"),
        accountName: execution.getVariable("accountName"),
        corpSn: corpSn,
        otherIdentify: "",
        depSn: deptSn,
        //managers: [new Manager(manager: ["sn":execution.getVariable("reportTo")], roles: [])],
        workAddress: execution.getVariable("dutyStation"),
        //fnDeleagatees:null,
        chargeToCostCenter: chargeToCostCenter
);
employee.managers = [new Manager(manager: ["sn":execution.getVariable("reportTo")], roles: [])];
def body = JsonOutput.toJson(employee);

//send request

