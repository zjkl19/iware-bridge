import Cookies from 'js-cookie'

const TokenKey = 'X-AUTH-TOKEN'

export function getToken() {
  return Cookies.get(TokenKey)
}

export function setToken(token) {
  return Cookies.set(TokenKey, token)
}

export function removeToken() {
  return Cookies.remove(TokenKey)
}

//加密方法
export function RSAencrypt(pas,publicKey){
    //实例化jsEncrypt对象
    let jse = new JSEncrypt();
    //设置公钥
    jse.setPublicKey(publicKey);
    console.log(pas);
    // console.log('加密：'+jse.encrypt(pas))
    return jse.encrypt(pas);
  }
  
//解密方法
export function RSAdecrypt(pas,privateKey){
    let jse = new JSEncrypt();
    // 私钥
    jse.setPrivateKey(privateKey)
    // console.log('解密：'+jse.decrypt(pas))
    return jse.decrypt(pas);
  }
