package wechatServer.controller;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import wechatServer.data.JsonDto;
import wechatServer.utils.HttpRequest;
import wechatServer.utils.JacksonUtil;
import wechatServer.utils.wechatUtils.AES;
import wechatServer.utils.wechatUtils.WXAppletUserInfo;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/server")
public class WechatUserController {
    public static String appid = "wxe8bae68f346492bc";
    public static String secret = "49f69d9db2d57ff88247a60cda883f52";
    public static String grant_type = "authorization_code";
    public static String apiUrl = "https://api.weixin.qq.com/sns/jscode2session";

    /**
     * 使用code换取用户openid
     * @return
     */
    @RequestMapping("/c2opid")
    @ResponseBody
    public JsonDto code2Openid(String code){
        JsonDto jsonDto = new JsonDto();
        jsonDto.setCode(0);
        jsonDto.setMsg("成功");
        jsonDto.setData(123);
        //接口地址
        //https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code
        String params = "appid=" + appid + "&secret=" + secret + "&js_code=" + code + "&grant_type=" + grant_type;
        String res = HttpRequest.sendGet(apiUrl, params);
        System.out.println(res);
        Map map = JacksonUtil.JsonToMap(res);
        if(map==null){
            return new JsonDto(-1,"操作失败");
        }
        String session_key = map.get("session_key").toString();
        String openid = map.get("openid").toString();
        System.out.println(session_key+"----"+openid);

        return jsonDto;
    }
    //{"session_key":"ZBRpczHgydVALVgUMUgkSA==","expires_in":7200,"openid":"o65UP0YxnhcwJE79SHjkaTP1IKY4"}
    @RequestMapping("/enUserInfo")
    @ResponseBody
    public JsonDto getUserInfoBySessionid(@RequestParam(value = "thiedSessionKey",required = false) String sessionId, @RequestParam(value = "encreptUserInfo",required = false) String enCreypetUserInfo, String iv){
        System.out.println(sessionId+"-----"+enCreypetUserInfo+"---"+iv);

        //解密
        //根据sessionid可以获取到之前存储的openid和session_key
        if(!"123".equals(sessionId)){
            return null;
        }
        String sessionkey = "+eUbUbjtv1iGsos1fAIahA==";
        String stoageOpenid = "o65UP0YxnhcwJE79SHjkaTP1IKY4";
        Base64 base64 = new Base64();
        String userInfo = null;
        try {
            userInfo = new String(AES.decrypt(base64.decode(enCreypetUserInfo),base64.decode(sessionkey) , base64.decode(iv) ),"UTF-8");
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if(null==userInfo){
            return null;
        }
        Map<String,String> map = JacksonUtil.JsonToMap(userInfo);

        JsonDto jsonDto = new JsonDto();
        jsonDto.setData(map);
        return jsonDto;
    }
}
