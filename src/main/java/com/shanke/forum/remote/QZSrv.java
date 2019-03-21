package com.shanke.forum.remote;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shanke.forum.entity.ResultInfo;
import com.shanke.forum.entity.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class QZSrv {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Value("${validate_url}")
    private String validateUrl;

    public ResultInfo validate(UserInfo userInfo) {

        String url = validateUrl + userInfo.getAccount();

        HttpGet httpGet = new HttpGet(url);

        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpResponse response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String res = EntityUtils.toString(response.getEntity(), "utf-8");
                JsonNode jsonNode = objectMapper.readTree(res);
                if ("1".equals(jsonNode.get("code").toString())) {
                    return new ResultInfo(1, "该账号不存在");
                }
                JsonNode dataNode = jsonNode.get("data");
                userInfo.setCollege(dataNode.get("college").toString().replaceAll("\"", ""));
                userInfo.setMajor(dataNode.get("major").toString().replaceAll("\"", ""));
                userInfo.setSex(dataNode.get("sex").toString().replaceAll("\"", ""));
                userInfo.setPhone(dataNode.get("phone").toString().replaceAll("\"", ""));
                userInfo.setUsername(dataNode.get("username").toString().replaceAll("\"", ""));
                return new ResultInfo(0, userInfo);
            } else {
                log.error("the result code of url {} is {}", url, response.getStatusLine().getStatusCode());
                return null;
            }
        } catch (ClientProtocolException e) {
            log.error("http-client fail to exec the post", e);
            return null;
        } catch (IOException e) {
            log.error("fail to change entity to string", e);
            return null;
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                log.error("fail to close httpClient", e);
            }
        }
    }
}
