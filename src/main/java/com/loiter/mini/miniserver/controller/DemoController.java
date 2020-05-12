package com.loiter.mini.miniserver.controller;

import cn.binarywang.wx.miniapp.api.*;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaSubscribeMessage;
import cn.binarywang.wx.miniapp.bean.code.WxMaCategory;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.util.http.RequestHttp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/mini")
@Slf4j
public class DemoController {

    @Resource
    public WxMaService wxMaService;

    @GetMapping("/info")
    public String miniTest() {
//        return wxMaService;
        try {
            String accessToken = wxMaService.getAccessToken();
            System.out.println("AccessToken = "+ accessToken);
//            RequestHttp request = wxMaService.getRequestHttp();
//            System.out.println(request.getRequestHttpClient());
//            WxMaCodeService wxMaCode = wxMaService.getCodeService();
//            List<WxMaCategory> cateGoryList = wxMaCode.getCategory();
//            for(WxMaCategory category: cateGoryList) {
//                System.out.println(category.getAddress() + "  " + category.getTitle());
//            }
//            List<String> pageList = wxMaCode.getPage();
//            pageList.stream().forEach(System.out::println);
//            byte[] images = wxMaCode.getQrCode("page/index/index");
//            System.out.println(images);
              // 通过前端wx.login()方法返回的code， 进行登录用户信息， openid， sessionKey, unionId
            WxMaUserService wxMaUserService = wxMaService.getUserService();
            WxMaJscode2SessionResult wxMaJscode2SessionResult = wxMaUserService.getSessionInfo("0217Fg8F0s3DTe2gEV5F0Msc8F07Fg8E");
            System.out.println(">>>>>>>>>>>>>>>");
            // openid is oA1cQ5dkxVMiculQm4Ik9Imlv8yU, session_key is foqepiGaCvi3GxO/DlFPSQ==, unionid is null
            log.info("wxMaJscode2SessionResult is {}", wxMaJscode2SessionResult);
            log.info("openid is {}, session_key is {}, unionid is {} ",
                                            wxMaJscode2SessionResult.getOpenid(),
                                            wxMaJscode2SessionResult.getSessionKey(),
                                            wxMaJscode2SessionResult.getUnionid());
//                    );
            // openid is oA1cQ5dkxVMiculQm4Ik9Imlv8yU, session_key is cENTWweH67UWMESHwQThLg==, unionid is null


            WxMaSubscribeService wxMaSubScribeService = wxMaService.getSubscribeService();
            List<WxMaSubscribeService.TemplateInfo> templateList = wxMaSubScribeService.getTemplateList();
            for(WxMaSubscribeService.TemplateInfo item: templateList) {
                log.info("tempID is {}, title is {}, type is {}, content is {}", item.getPriTmplId(), item.getTitle(), item.getType(), item.getContent());
            }



//            String tempId = "iFQbUGe4LydDLsw5cVf5hidV4h2T7zfjJUdg6gRKupo";
//            List<Integer> keyList = new ArrayList<>();
//            keyList.add(3);
//            keyList.add(5);
//            keyList.add(4);
//            String result = wxMaSubScribeService.addTemplate(tempId, keyList, "测试用");
//            System.out.println("result  = " + result);

//
//            // 发送订阅消息
            WxMaSubscribeMessage wxMaSubscribeMessage = new WxMaSubscribeMessage();
            wxMaSubscribeMessage.setTemplateId("iFQbUGe4LydDLsw5cVf5hidV4h2T7zfjJUdg6gRKupo");
            wxMaSubscribeMessage.setToUser("oA1cQ5dkxVMiculQm4Ik9Imlv8yU");
            wxMaSubscribeMessage.setPage("page/index/index");
            List<WxMaSubscribeMessage.Data> _data = new ArrayList<>();
            WxMaSubscribeMessage.Data subscribeData = new WxMaSubscribeMessage.Data();
            subscribeData.setName("phrase1");
            subscribeData.setValue("配送中");
            _data.add(subscribeData);


            WxMaSubscribeMessage.Data subscribeData1 = new WxMaSubscribeMessage.Data();
            subscribeData1.setName("thing2");
            subscribeData1.setValue("测试订阅消息");
            _data.add(subscribeData1);


            WxMaSubscribeMessage.Data subscribeData2 = new WxMaSubscribeMessage.Data();
            subscribeData2.setName("date3");
            subscribeData2.setValue("2019年10月1日 15:01");
            _data.add(subscribeData2);


            WxMaSubscribeMessage.Data subscribeData3 = new WxMaSubscribeMessage.Data();
            subscribeData3.setName("date4");
            subscribeData3.setValue("2019年10月1日 15:32");
            _data.add(subscribeData3);
            WxMaSubscribeMessage.Data subscribeData4 = new WxMaSubscribeMessage.Data();
            subscribeData4.setName("thing12");
            subscribeData4.setValue("thing12,字符串");

//            subscribeData.setName("name1");
//            subscribeData.setValue("文档被被查看通知");
//            subscribeData.setName("time2");
//            subscribeData.setValue("2019年10月1日 15:01");

            _data.add(subscribeData4);
            wxMaSubscribeMessage.setData(_data);
            String json = wxMaSubscribeMessage.toJson() ;
            System.out.println("#### " + json);
            WxMaMsgService wxMaMsgService = wxMaService.getMsgService();
            wxMaMsgService.sendSubscribeMsg(wxMaSubscribeMessage);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        return "success";
    }
}
