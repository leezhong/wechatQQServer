package wechatServer.utils.wechatUtils;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.UnsupportedEncodingException;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;


import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public class AES {
    public static boolean initialized = false;


    public static void main(String[] args) throws InvalidAlgorithmParameterException, UnsupportedEncodingException {
        Base64 base64 = new Base64();
        // 被加密的数据
        byte[] dataByte = base64.decode("HALDn/J5mmR6SPuAw7GVMSKm46WEubwZO+tVl9HZGVdWwrBRznoDHR6vWYYR8kl0sPCKAuVpbCTcP5JzTA53ILba713uksOyMg/x+BUQvrx0YGX6OaoJjyPA2RNTGyUR/Y5OUrBjIn0t9yR6IdxMFM8D6MnPBFbRmcY+d5Po2C9scC/KptvpnULqlOThEAJQ0LPj5P7Vm7Cv02m7vozFsAtJiwMeq1KUElcUfWUE2UcPgtP7nE3ay6xR5zMT2i8H4thU3g1UnyyvthOzJLjQyphAepY7/12CHs0YiEk/bsWZ4/TG3gHMI1udSzezjykc1ZLjKwtkrUsJpaRQR5cFW9H75ztNdSaII2OjYx1A5kSb4KKw9BdLXzFN4ZgHJNNCN59ngS4lLh8InxxdmtcK0+ffPtw8X5nGquvobxKIQxqcQK8W5EB7RhLtodqypklnAmiWPrC5D8pJ1CAqegPQfmUrJHe5FzOpqUlkAXu7jI8=");
        // 加密秘钥
        byte[] keyByte = base64.decode("RNODdGF03D4sHdWz7WBYuA==");
        // 偏移量
        byte[] ivByte = base64.decode("0cNQwzCD+pboTTWRL7wdwg==");
        byte[] decrypt = decrypt(dataByte, keyByte, ivByte);
        if(decrypt!=null&& decrypt.length>0){
            String resStr = new String(decrypt,"UTF-8");
            System.out.println(resStr);
        }
    }
    /**
     * AES解密
     * @param content 密文
     * @return
     * @throws InvalidAlgorithmParameterException
     * @throws NoSuchProviderException
     */
    public static byte[] decrypt(byte[] content, byte[] keyByte, byte[] ivByte) throws InvalidAlgorithmParameterException {
        initialize();
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            Key sKeySpec = new SecretKeySpec(keyByte, "AES");

            cipher.init(Cipher.DECRYPT_MODE, sKeySpec, generateIV(ivByte));// 初始化
            byte[] result = cipher.doFinal(content);
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static void initialize(){
        if (initialized) return;
        Security.addProvider(new BouncyCastleProvider());
        initialized = true;
    }
    //生成iv
    public static AlgorithmParameters generateIV(byte[] iv) throws Exception{
        AlgorithmParameters params = AlgorithmParameters.getInstance("AES");
        params.init(new IvParameterSpec(iv));
        return params;
    }


}
