package com.isaac.practice.fastxml;

import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.isaac.practice.fastxml.pojo.UserInfo;
import com.isaac.practice.fastxml.pojo.UserInfos;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class XMLUsage {
    private static final Logger logger = LoggerFactory.getLogger(XMLUsage.class);
    private XmlMapper xmlMapper;

    @Before
    public void createMapper() {
        //configure settings
        JacksonXmlModule module = new JacksonXmlModule();
        module.setDefaultUseWrapper(false);
        xmlMapper = new XmlMapper(module);
    }

    @Test
    public void testSeriPOJOsAsXML() throws IOException {
        UserInfo pojo = new UserInfo("Isaac", 24, "13262270185");
        UserInfos userInfos = new UserInfos(Arrays.asList(pojo));
        //xmlMapper.writeValue(new File("/Users/eorionx/Downloads/aaa_now/test.json"), pojo);
        String xmlStr = xmlMapper.writeValueAsString(userInfos);
        assertNotNull(xmlStr);
        logger.info(xmlStr);
    }
}
