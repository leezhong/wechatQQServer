package wechatServer.utils.wechatUtils;


import java.io.UnsupportedEncodingException;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.spec.InvalidParameterSpecException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.Arrays;
import wechatServer.utils.HttpRequest;

/**
 * 微信小程序信息获取
 * @author Administrator
 * @Date 2017年2月16日 11:56:08
 */
public class WXAppletUserInfo {


    public  String appid = "wxe8bae68f346492bc";
    public  String secret = "49f69d9db2d57ff88247a60cda883f52";
    public  String grant_type = "authorization_code";
    public  String apiUrl = "https://api.weixin.qq.com/sns/jscode2session";

    /**
     * 获取微信小程序 session_key 和 openid
     * @param code
     * @return
     */
    public String getSessionKeyOropenid(String code){
        //微信端登录code值
//        String wxCode = code;
//        String requestUrl = "https://api.weixin.qq.com/sns/jscode2session";
//        Map<String,String> requestUrlParam = new HashMap<String,String>();
//        requestUrlParam.put("appid", "wxe8bae68f346492bc");
//        requestUrlParam.put("secret","49f69d9db2d57ff88247a60cda883f52");
//        requestUrlParam.put("js_code", wxCode);
//        requestUrlParam.put("grant_type", "authorization_code");


        String params = "appid=" + appid + "&secret=" + secret + "&js_code=" + code + "&grant_type=" + grant_type;
        String res = HttpRequest.sendGet(apiUrl, params);
        return res;
    }

    public static void main(String[] args) {
        String userInfo = getUserInfo("HALDn/J5mmR6SPuAw7GVMSKm46WEubwZO+tVl9HZGVdWwrBRznoDHR6vWYYR8kl0sPCKAuVpbCTcP5JzTA53ILba713uksOyMg/x+BUQvrx0YGX6OaoJjyPA2RNTGyUR/Y5OUrBjIn0t9yR6IdxMFM8D6MnPBFbRmcY+d5Po2C9scC/KptvpnULqlOThEAJQ0LPj5P7Vm7Cv02m7vozFsAtJiwMeq1KUElcUfWUE2UcPgtP7nE3ay6xR5zMT2i8H4thU3g1UnyyvthOzJLjQyphAepY7/12CHs0YiEk/bsWZ4/TG3gHMI1udSzezjykc1ZLjKwtkrUsJpaRQR5cFW9H75ztNdSaII2OjYx1A5kSb4KKw9BdLXzFN4ZgHJNNCN59ngS4lLh8InxxdmtcK0+ffPtw8X5nGquvobxKIQxqcQK8W5EB7RhLtodqypklnAmiWPrC5D8pJ1CAqegPQfmUrJHe5FzOpqUlkAXu7jI8=",
                "RNODdGF03D4sHdWz7WBYuA==", "0cNQwzCD+pboTTWRL7wdwg==");
        System.out.println(userInfo);
    }

    /**
     * 解密用户敏感数据获取用户信息
     * @param sessionKey 数据进行加密签名的密钥
     * @param encryptedData 包括敏感数据在内的完整用户信息的加密数据
     * @param iv 加密算法的初始向量
     * @return
     */
    public static String getUserInfo(String encryptedData,String sessionKey,String iv){
        Base64 base64 = new Base64();
        // 被加密的数据
        byte[] dataByte = base64.decode(encryptedData);
        // 加密秘钥
        byte[] keyByte = base64.decode(sessionKey);
        // 偏移量
        byte[] ivByte = base64.decode(iv);

        try {
            // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
            int base = 16;
            if (keyByte.length % base != 0) {
                int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
                byte[] temp = new byte[groups * base];
                Arrays.fill(temp, (byte) 0);
                System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
                keyByte = temp;
            }
            // 初始化
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding","BC");
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
            byte[] resultByte = cipher.doFinal(dataByte);
            if (null != resultByte && resultByte.length > 0) {
                String result = new String(resultByte, "UTF-8");
                return result;
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidParameterSpecException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        return null;
    }

}