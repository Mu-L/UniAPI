package com.burukeyou;

import com.alibaba.fastjson.JSON;
import com.burukeyou.demo.DemoApplication;
import com.burukeyou.demo.api.SimpleServiceApi;
import com.burukeyou.demo.api.UserServiceApi;
import com.burukeyou.demo.entity.*;
import com.burukeyou.uniapi.http.core.response.HttpBinaryResponse;
import com.burukeyou.uniapi.http.core.response.HttpResponse;
import com.burukeyou.uniapi.http.util.UniHttpBuilder;
import okhttp3.Cookie;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;


@SpringBootTest(classes = DemoApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class HttpApiTest {

    @Autowired
    private UserServiceApi meituanApi;

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private SimpleServiceApi simpleServiceApi;

    private File getLocalFile(String path) {
        try {
            return resourceLoader.getResource("classpath:" + path).getFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void test1(){
        BaseRsp<String> add = meituanApi.add("999");
        System.out.println(JSON.toJSONString(add));
    }


    @Test
    public void test2(){
        BaseRsp<String> add = meituanApi.add2(new U2DTO(3,"jay"));
        System.out.println(JSON.toJSONString(add));
    }

    @Test
    public void test3(){
        BaseRsp<String> add = meituanApi.add3("33","fadlkfl");
        System.out.println(JSON.toJSONString(add));
    }

    @Test
    public void test4(){
        BaseRsp<Add4DTO> rsp = meituanApi.add4(new Add4DTO(1L, "jay"));
        System.out.println(JSON.toJSONString(rsp));
    }

    @Test
    public void test44(){
        BaseRsp<String> rsp = meituanApi.add41("jay");
        System.out.println(JSON.toJSONString(rsp));
    }

    @Test
    public void test5(){
        BaseRsp<String> add = meituanApi.add5("90");
        System.out.println(JSON.toJSONString(add));
    }

    @Test
    public void test6(){
        Add6DTO req = new Add6DTO();
        req.setId(2L);
        req.setName("jay");
        req.setReq(new Add4DTO(99L,"张三"));
        req.setCook("userName=哈哈;id=33;sessionId=fklasdf8902342");

        HttpResponse<BaseRsp<Add4DTO>> rsp = meituanApi.add6(req);
        String s = rsp.toHttpProtocol();
        System.out.println();
    }

    @Test
    public void test7(){
        File file = getLocalFile("img/a.txt");
        BaseRsp<String> rsp = meituanApi.add7(file);
        System.out.println(JSON.toJSONString(rsp));
    }


    @Test
    public void test8(){
        BaseRsp<String> rsp = meituanApi.add8(new Add4DTO(1L, "jay"));
        System.out.println(JSON.toJSONString(rsp));
    }

    @Test
    public void test9(){
        Add9DTO add9DTO = new Add9DTO(1L,"99");
        File file1 = getLocalFile("img/a.txt");
        File file2 = getLocalFile("img/b.txt");
        File file3 = getLocalFile("img/c.txt");

        add9DTO.setUserImg(file1);
        //add9DTO.setLogoImg(Arrays.asList(file2,file3));
        add9DTO.setLogoImg(new File[]{file2,file3});

        BaseRsp<String> rsp = meituanApi.add9(add9DTO);
        System.out.println(JSON.toJSONString(rsp));
    }

    @Test
    public void test10(){
        HttpBinaryResponse fileResponse = meituanApi.add10();
        System.out.println();
    }

    @Test
    public void test11(){
        File file = meituanApi.add11();
        System.out.println();
    }

    @Test
    public void test12(){
        String savePath = "/Users/burukeyou/dev/tmp/tmp7/jou";
        String savePath2 = "/Users/burukeyou/dev/tmp/tmp7/jou/用户列表.txt";
        File file = meituanApi.add12(savePath);
        System.out.println();
    }


    @Test
    public void testUpdate0(){
        BaseRsp<String> stringBaseRsp = meituanApi.update0();
        System.out.println();
    }

    @Test
    public void testUpdate1(){
        String cookie = "dc_sid=a0f98c5f364defa703b5d4d11df524ae; hasShowRRGuideBottom=1; _ga=GA1.1.937830630.1693224449; _ga_7W1N0GEY1P=GS1.1.1693224449.1.1.1693224848.60.0.0; uuid_tt_dd=10_19008843070-1710416231795-499398; c_segment=6; SESSION=573f2127-90b8-4043-8cb6-37da2187d49b; loginbox_strategy=%7B%22taskId%22%3A349%2C%22abCheckTime%22%3A1715607630543%2C%22version%22%3A%22exp11%22%2C%22blog-threeH-dialog-exp11tipShowTimes%22%3A4%2C%22blog-threeH-dialog-exp11%22%3A1715607630544%7D; UserName=weixin_41347419; UserInfo=52b0a06dcd084d8984d1c339b673c4ba; UserToken=52b0a06dcd084d8984d1c339b673c4ba; UserNick=%E7%AC%A8%E7%8C%AA%E5%A4%A7%E9%9A%BE%E4%B8%B4%E5%A4%B4; AU=9B5; UN=weixin_41347419; BT=1715607770342; p_uid=U010000; Hm_up_6bcd52f51e9b3dce32bec4a3997715ac=%7B%22islogin%22%3A%7B%22value%22%3A%221%22%2C%22scope%22%3A1%7D%2C%22isonline%22%3A%7B%22value%22%3A%221%22%2C%22scope%22%3A1%7D%2C%22isvip%22%3A%7B%22value%22%3A%220%22%2C%22scope%22%3A1%7D%2C%22uid_%22%3A%7B%22value%22%3A%22weixin_41347419%22%2C%22scope%22%3A1%7D%7D; cf_clearance=m.u7Ah9yqWA.8k._tCpgoAmK.tqXIPNdGnea1iw1p9g-1715964801-1.0.1.1-nn_.mg87UlirO4x_QEuX5.qcZPfnETGZ4oYAIYsBm0T6wU.CwxDLYHDZizfhk176cvMV7SyAgY2mrs3eCVCTQg; _clck=l58584%7C2%7Cflu%7C0%7C1594; chat-version=2.1.1; log_Id_click=78; dc_session_id=10_1717774764604.786630; c_pref=default; c_ref=default; c_first_ref=default; c_first_page=https%3A//www.csdn.net/; c_dsid=11_1717774765344.647129; c_page_id=default; log_Id_pv=75; c_adb=1; Hm_lvt_6bcd52f51e9b3dce32bec4a3997715ac=1715607631,1717774767; Hm_lpvt_6bcd52f51e9b3dce32bec4a3997715ac=1717774767; creativeSetApiNew=%7B%22toolbarImg%22%3A%22https%3A//img-home.csdnimg.cn/images/20230921102607.png%22%2C%22publishSuccessImg%22%3A%22https%3A//img-home.csdnimg.cn/images/20240229024608.png%22%2C%22articleNum%22%3A53%2C%22type%22%3A2%2C%22oldUser%22%3Atrue%2C%22useSeven%22%3Afalse%2C%22oldFullVersion%22%3Atrue%2C%22userName%22%3A%22weixin_41347419%22%7D; creative_btn_mp=1; log_Id_view=1136; dc_tos=sepvht;userToken=jay";
        BaseRsp<String> stringBaseRsp = meituanApi.update1(cookie);
        System.out.println();
    }

    @Test
    public void testUpdate2(){
        String cookie = "dc_sid=a0f98c5f364defa703b5d4d11df524ae; hasShowRRGuideBottom=1; _ga=GA1.1.937830630.1693224449; _ga_7W1N0GEY1P=GS1.1.1693224449.1.1.1693224848.60.0.0; uuid_tt_dd=10_19008843070-1710416231795-499398; c_segment=6; SESSION=573f2127-90b8-4043-8cb6-37da2187d49b; loginbox_strategy=%7B%22taskId%22%3A349%2C%22abCheckTime%22%3A1715607630543%2C%22version%22%3A%22exp11%22%2C%22blog-threeH-dialog-exp11tipShowTimes%22%3A4%2C%22blog-threeH-dialog-exp11%22%3A1715607630544%7D; UserName=weixin_41347419; UserInfo=52b0a06dcd084d8984d1c339b673c4ba; UserToken=52b0a06dcd084d8984d1c339b673c4ba; UserNick=%E7%AC%A8%E7%8C%AA%E5%A4%A7%E9%9A%BE%E4%B8%B4%E5%A4%B4; AU=9B5; UN=weixin_41347419; BT=1715607770342; p_uid=U010000; Hm_up_6bcd52f51e9b3dce32bec4a3997715ac=%7B%22islogin%22%3A%7B%22value%22%3A%221%22%2C%22scope%22%3A1%7D%2C%22isonline%22%3A%7B%22value%22%3A%221%22%2C%22scope%22%3A1%7D%2C%22isvip%22%3A%7B%22value%22%3A%220%22%2C%22scope%22%3A1%7D%2C%22uid_%22%3A%7B%22value%22%3A%22weixin_41347419%22%2C%22scope%22%3A1%7D%7D; cf_clearance=m.u7Ah9yqWA.8k._tCpgoAmK.tqXIPNdGnea1iw1p9g-1715964801-1.0.1.1-nn_.mg87UlirO4x_QEuX5.qcZPfnETGZ4oYAIYsBm0T6wU.CwxDLYHDZizfhk176cvMV7SyAgY2mrs3eCVCTQg; _clck=l58584%7C2%7Cflu%7C0%7C1594; chat-version=2.1.1; log_Id_click=78; dc_session_id=10_1717774764604.786630; c_pref=default; c_ref=default; c_first_ref=default; c_first_page=https%3A//www.csdn.net/; c_dsid=11_1717774765344.647129; c_page_id=default; log_Id_pv=75; c_adb=1; Hm_lvt_6bcd52f51e9b3dce32bec4a3997715ac=1715607631,1717774767; Hm_lpvt_6bcd52f51e9b3dce32bec4a3997715ac=1717774767; creativeSetApiNew=%7B%22toolbarImg%22%3A%22https%3A//img-home.csdnimg.cn/images/20230921102607.png%22%2C%22publishSuccessImg%22%3A%22https%3A//img-home.csdnimg.cn/images/20240229024608.png%22%2C%22articleNum%22%3A53%2C%22type%22%3A2%2C%22oldUser%22%3Atrue%2C%22useSeven%22%3Afalse%2C%22oldFullVersion%22%3Atrue%2C%22userName%22%3A%22weixin_41347419%22%7D; creative_btn_mp=1; log_Id_view=1136; dc_tos=sepvht;userToken=jay";
        HttpResponse httpResponse = meituanApi.update2(cookie);
        List<String> setCookies = httpResponse.getSetCookiesString();
        List<Cookie> httpSetCookies = httpResponse.getSetCookies();
        System.out.println();
    }

    @Test
    public void testUpdate3(){
        BaseRsp<String> stringBaseRsp = meituanApi.update3(Arrays.asList(7,8,9));
        System.out.println(stringBaseRsp);
    }

    @Test
    public void testSimple(){
        SimpleServiceApi proxy = UniHttpBuilder.getProxy(SimpleServiceApi.class);
        String s = proxy.add2("222");
        System.out.println();

        String add = simpleServiceApi.add2("1");
        System.out.println();
    }

    @Test
    public void test22(){
        String add = simpleServiceApi.getxxxx("1");
        System.out.println();
    }
}


