package com.iware.common.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource(value = {"classpath:checkUrl.properties"}, encoding = "utf-8")
public class CheckUrlProperties {

    @Value("#{'${url-list}'.split(',')}")
    public String[] urlList;
}
