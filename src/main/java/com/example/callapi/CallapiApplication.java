package com.example.callapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

// 設定 Spring Boot 應用程式的配置
@RestController
@SpringBootApplication
public class CallapiApplication {

 // 使用 @GetMapping 註釋指定 API 端點的路徑
 // 這裡指定的路徑是 "/opendata"

 // 主方法用於啟動 Spring Boot 應用程式
 public static void main(String[] args) {
  SpringApplication.run(CallapiApplication.class, args);
  OkHttpClient client = new OkHttpClient();

  Request request = new Request.Builder()
    .url("https://tsipt.tbb.com.tw/host12/fx/n025")
    .build();

  try (Response response = client.newCall(request).execute()) {
   if (response.isSuccessful()) {
    String jsonData = response.body().string();
    System.out.println(jsonData);
   } else {
    System.out.println("Failed to retrieve data: " + response.code());
   }
  } catch (Exception e) {
   System.out.println("Exception occurred: " + e.getMessage());
  }
 }

 @PostMapping("/api")
 @ResponseBody
 public String callApi() {
  OkHttpClient client = new OkHttpClient();

  MediaType mediaType = MediaType.parse("application/json");
  RequestBody body = RequestBody.create(new byte[0], mediaType);

  Request request = new Request.Builder()
    .url("https://tsipt.tbb.com.tw/host12/fx/n025")
    .post(body)
    .addHeader("Content-Type", "application/json")
    .build();

  try (Response response = client.newCall(request).execute()) {
   if (response.isSuccessful()) {
    String jsonData = response.body().string();
    return jsonData;
   } else {
    return "Failed to retrieve data: " + response.code();
   }
  } catch (Exception e) {
   return "Exception occurred: " + e.getMessage();
  }
 }

 @GetMapping("/opendata")
 public String getOpenData() {
  // 創建一個新的 RestTemplate 對象
  RestTemplate restTemplate = new RestTemplate();
  // 呼叫 OpenData URL 並取得回應資料
  String url = "https://tsipt.tbb.com.tw/host12/fx/n025";
  // 將回應資料返回給客戶端
  String response = restTemplate.getForObject(url, String.class);
  return response;
 }
}