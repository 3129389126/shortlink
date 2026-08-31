package com.zch.shortlink.admin.common.convention.serialize;


import cn.hutool.core.util.DesensitizedUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;


/**
 * 手机号脱敏反序列化
 */
//JsonSerializer<String> 是 Jackson（Spring 默认的 JSON 库）提供的扩展点：泛型填 String = “我这个自定义序列化器专门处理 String 类型的字段”。
public class PhoneDesensitizationSerializer extends JsonSerializer<String> {

    @Override
    public void serialize(String phone, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        String phoneDesensitization = DesensitizedUtil.mobilePhone(phone);
        jsonGenerator.writeString(phoneDesensitization);
    }
}
