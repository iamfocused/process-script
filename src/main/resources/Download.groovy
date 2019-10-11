
def prefixUrl = 'https://bpms.eorionsolution.com/bpms-upload/download'
def varStr = "[{\"path\":\"/100011/DPENTRYPROCESS-1-68192/20190610161229/个人固资申请表.xlsx\",\"name\":\"个人固资申请表.xlsx\",\"size\":\"12528 bytes\",\"type\":\"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet\"},{\"path\":\"/100011/DPENTRYPROCESS-1-68192/20190610161295/个人行政资产卡.xlsx\",\"name\":\"个人行政资产卡.xlsx\",\"size\":\"81375 bytes\",\"type\":\"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet\"},{\"path\":\"/100011/DPENTRYPROCESS-1-68192/20190610162907/个人行政资产卡.xlsx\",\"name\":\"个人行政资产卡.xlsx\",\"size\":\"81375 bytes\",\"type\":\"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet\"},{\"path\":\"/100011/DPENTRYPROCESS-1-68192/20190610162967/狄邦入职流程.docx\",\"name\":\"狄邦入职流程.docx\",\"size\":\"15525 bytes\",\"type\":\"application/vnd.openxmlformats-officedocument.wordprocessingml.document\"}]"
def jsonSlurper = new groovy.json.JsonSlurper()
def varObj = jsonSlurper.parseText(varStr)
def links = new ArrayList()
def collect = varObj.stream().collect(java.util.stream.Collectors.groupingBy() { it -> it.name})
def comparator = Comparator.comparing(){str->str.path.substring(str.path.lastIndexOf("/") - 14, str.path.lastIndexOf("/"))}
collect.each{it->links.add(prefixUrl + it.value.stream().sorted(comparator.reversed()).map(){item->item.path}.findFirst().orElse(""))}
println(links)
//varObj.stream().map(){it->it.path}

//varObj.each {it->links.add(prefixUrl + it.path)}

//println(links)

//println(str.substring(str.lastIndexOf("/") - 14, str.lastIndexOf("/")))
