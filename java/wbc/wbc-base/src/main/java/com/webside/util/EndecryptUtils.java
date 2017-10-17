package com.webside.util;

import java.security.Key;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.codec.H64;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.AesCipherService;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Md5Hash;

import com.webside.user.model.UserEntity;

/**
 * 
 * @ClassName: EndecryptUtils
 * @Description: 加密工具类
 * @author gaogang
 * @date 2016年7月12日 下午4:24:19
 *
 */
public class EndecryptUtils {
	/** 
     * base64进制加密 
     * 
     * @param password 
     * @return 
     */ 
    public static String encrytBase64(String password) { 
        byte[] bytes = password.getBytes(); 
        return Base64.encodeToString(bytes); 
    } 
    /** 
     * base64进制解密 
     * @param cipherText 
     * @return 
     */ 
    public static String decryptBase64(String cipherText) { 
        return Base64.decodeToString(cipherText); 
    } 
    /** 
     * 16进制加密 
     * 
     * @param password 
     * @return 
     */ 
    public static String encrytHex(String password) { 
        byte[] bytes = password.getBytes(); 
        return Hex.encodeToString(bytes); 
    } 
    /** 
     * 16进制解密 
     * @param cipherText 
     * @return 
     */ 
    public static String decryptHex(String cipherText) { 
        return new String(Hex.decode(cipherText)); 
    } 
    
    public static String generateKey() 
    { 
        AesCipherService aesCipherService=new AesCipherService(); 
        Key key=aesCipherService.generateNewKey(); 
        return Base64.encodeToString(key.getEncoded()); 
    } 
    /** 
     * 对密码进行md5加密,并返回密文和salt，包含在User对象中 
     * @param username 用户名 
     * @param password 密码
     * @param hashIterations 迭代次数 
     * @return UserEntity对象，包含密文和salt 
     */ 
    public static UserEntity md5Password(String userId,String password,int hashIterations){ 
        SecureRandomNumberGenerator secureRandomNumberGenerator=new SecureRandomNumberGenerator(); 
        String salt= secureRandomNumberGenerator.nextBytes().toHex(); 
        //组合username,两次迭代，对密码进行加密 
        String password_cryto = new Md5Hash(password,userId + salt,hashIterations).toBase64(); 
//        String password_cryto = new Md5Hash(password,username+salt,hashIterations).toBase64(); //为了支持手机号和邮箱登录用一个密码，暂时注掉
        UserEntity user=new UserEntity(); 
        user.setPassword(password_cryto); 
        user.setCredentialsSalt(salt); 
        user.setId(userId); 
        return user; 
    } 
    
	public static boolean checkPassword(UserEntity userEntity, String password) {
		String password_cryto = new Md5Hash(password, userEntity.getId() + userEntity.getCredentialsSalt(), 2).toBase64();
		if (password_cryto.equals(userEntity.getPassword())) {
			return true;
		}
		return false;
	}
	
	/** 
     * 对支付密码进行md5加密,并返回密文和salt，包含在User对象中 
     * @param userId 用户名 
     * @param paypassword 密码
     * @param hashIterations 迭代次数 
     * @return UserEntity对象，包含密文和salt 
     */ 
    public static UserEntity md5PayPassword(String userId,String payPassword,int hashIterations){ 
        SecureRandomNumberGenerator secureRandomNumberGenerator=new SecureRandomNumberGenerator(); 
        String salt= secureRandomNumberGenerator.nextBytes().toHex(); 
        //组合username,两次迭代，对密码进行加密 
        String password_cryto = new Md5Hash(payPassword,userId + salt,hashIterations).toBase64(); 
//        String password_cryto = new Md5Hash(password,username+salt,hashIterations).toBase64(); //为了支持手机号和邮箱登录用一个密码，暂时注掉
        UserEntity user=new UserEntity(); 
        user.setPayPassword(password_cryto); 
        user.setPayPasswordSalt(salt); 
        user.setId(userId); 
        return user; 
    } 
    /**
     * 验证支付密码
     * @param userEntity
     * @param password
     * @return
     */
	public static boolean checkPayPassword(UserEntity userEntity, String password) {
		String password_cryto = new Md5Hash(password, userEntity.getId() + userEntity.getPayPasswordSalt(), 2).toBase64();
		if (password_cryto.equals(userEntity.getPayPassword())) {
			return true;
		}
		return false;
	}
    public static void main(String[] args) {
		String password = "test123";
		String userId = "21";
		UserEntity user = EndecryptUtils.md5Password(userId, password, 2);
		System.out.println(user.getPassword()+"///////////"+ user.getCredentialsSalt());
	}
    
    public static void main1(String[] args) { 
        String password = "admin"; 
        String cipherText = encrytHex(password); 
        System.out.println(password + "hex加密之后的密文是：" + cipherText); 
        String decrptPassword=decryptHex(cipherText); 
        System.out.println(cipherText + "hex解密之后的密码是：" + decrptPassword); 
        String cipherText_base64 = encrytBase64(password); 
        System.out.println(password + "base64加密之后的密文是：" + cipherText_base64); 
        String decrptPassword_base64=decryptBase64(cipherText_base64); 
        System.out.println(cipherText_base64 + "base64解密之后的密码是：" + decrptPassword_base64); 
        String h64=  H64.encodeToString(password.getBytes()); 
        System.out.println(h64); 
        String salt="7road"; 
        String cipherText_md5= new Md5Hash(password,salt,4).toHex(); 
        System.out.println(password+"通过md5加密之后的密文是："+cipherText_md5); 
        System.out.println(generateKey()); 
        System.out.println("=========================================================="); 
        AesCipherService aesCipherService=new AesCipherService(); 
        aesCipherService.setKeySize(128); 
        Key key=aesCipherService.generateNewKey(); 
        String aes_cipherText= aesCipherService.encrypt(password.getBytes(),key.getEncoded()).toHex(); 
        System.out.println(password+" aes加密的密文是："+aes_cipherText); 
        String aes_mingwen=new String(aesCipherService.decrypt(Hex.decode(aes_cipherText),key.getEncoded()).getBytes()); 
        System.out.println(aes_cipherText+" aes解密的明文是："+aes_mingwen); 
    } 
}