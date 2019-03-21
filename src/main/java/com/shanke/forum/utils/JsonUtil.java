package com.shanke.forum.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

/**
 *  https://blog.csdn.net/ZZY1078689276/article/details/79677053
 */
@Slf4j
public class JsonUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public static <T> String object2Json(T obj) {
        if (obj == null) {
            return null;
        }
        try {
            return obj instanceof String ? (String) obj : objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            log.error("Parse Object to Json error", e);
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T json2Object(String src, Class<T> clazz) {
        if (StringUtils.isEmpty(src) || clazz == null) {
            return null;
        }
        try {
            return clazz.equals(String.class) ? (T) src : objectMapper.readValue(src, clazz);
        } catch (Exception e) {
            log.error("Parse Json to Object error", e);
            e.printStackTrace();
            return null;
        }
    }

}
