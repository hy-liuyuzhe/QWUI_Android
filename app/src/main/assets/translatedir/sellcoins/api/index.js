import http from "../../../common/network/http/index";
import { getDeviceInfo } from "../../../common/bridge/index";
/**
 * 获取其他用户信息
 * @returns
 */
const getUserInfoByGoodOrShortId = async (shortId) => {
    return await http.get("/user/getUserInfoByGoodOrShortId/" + shortId, null);
};

/**
 * 卖币
 *
 * @returns
 */
const currencySellCoin = async (data) => {
    return await http.post("/currency/sell_coin", data);
};


/**
 * 获取金币数
 */
const getMyCoins = async () => {
    return await http.get(`/currency/get?currencies=7&seqId=` + new Date().getTime(), null);
};

/**
 * 币商vip列表
 * @returns {"code":200,"data":[{"vipLevel":1,"vipNum":10},{"vipLevel":2,"vipNum":10}],"message":"success"}
 */
const getMerchantVipList = async () => {
    return await http.get(`/goods/getMerchantVipList`, null);
};

/**
 * 卖vip
 * @param {"vipLevel": 1,"buyer":10003013}
 */
const sellVip = async (data) => {
    return await http.post(`/goods/sellVip`, data);
};

/**
 * 拉取币商列表
 * @param {countryCode: "", versionCode: 0}
 * @returns { code: 200, message: "succeed", sucessed: true, data: { recommend: false, merchantInfoList: [] }}
 */
const getMerchantList = async (data) => {
    const deviceInfo = await getDeviceInfo();
    return await http.get("/config/getMerchantList", {
        params: {
            countryCode: data.countryCode,
            versionCode: deviceInfo ? deviceInfo.versionCode : 50
        }
    });
};

/**
 * 拉取我的币商信息
 * @returns { code: 200, message: "操作成功", data: { uid: 2003, whatsapp: "123456" }, sucessed: true}
 */
const getMyMerchantInfo = async () => {
    return await http.get("/config/getMyMerchantInfo");
};

/**
 * 更新币商信息
 * @param {whatsapp:"123456"} 
 */
const updateMerchantInfo = async (data) => {
    return await http.post("/config/updateMerchantInfo", data);
};

/**
 * 币商拉取被举报低价卖币警
 */
const getAnchorMessage = async () => {
    return await http.post("/config/getAnchorMessage");
};

/**
 * 警告消息弹窗，点击确认
 * @param {messageId:1,messagetype:6,replyCode:1}
 */
const replyAnchorMessage = async (data) => {
    return await http.post("/config/replyAnchorMessage", data);
};

export default {
    getUserInfoByGoodOrShortId,
    currencySellCoin,
    getMyCoins,
    getMerchantVipList,
    sellVip,
    getMerchantList,
    getMyMerchantInfo,
    updateMerchantInfo,
    getAnchorMessage,
    replyAnchorMessage
};