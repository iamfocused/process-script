package com.isaac.practice.fastxml.pojo;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@Data
@AllArgsConstructor
@JacksonXmlRootElement(localName = "users")
public class UserInfos {
    @JacksonXmlElementWrapper(localName = "user")
    private List<UserInfo> userInfo;
}
