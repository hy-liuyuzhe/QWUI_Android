import http from "../../../common/network/http/index";
import { getDeviceInfo } from "../../../common/bridge/index";

/**
 * 获取三方支付方式
 * @param {countryCode: "", versionCode: 0, channel: ""}
 * @returns {"code":0,"data":[{"name":"string","type":0}],"message":"string","sucessed":true}
 */
const getChannels = async (data) => {
    const deviceInfo = await getDeviceInfo();
    return await http.get("/payermax/getPayType", {
        params: {
            countryCode: data.countryCode,
            versionCode: deviceInfo ? deviceInfo.versionCode : 0,
            channel: deviceInfo ? deviceInfo.appChannel : "gp"
        }
    });
};

/**
 * 获取原生支付套餐列表
 * @param {seqId: 0, channelIds: [""], clientVersion: "", platform: ""}
 * @returns {"code":0,"data":{"seqId": "", "channelLists": {"default": { "productInfos": [{ "productId": 0, "skuId": "", "vipLevel": 0]}}},"message":"string","sucessed":true}
 */
const getNativeProductConfig = async (platform, channel) => {
    const deviceInfo = await getDeviceInfo();
    return await http.post("/config/getProductConfig", {
        seqId: new Date().getTime(),
        clientVersion: deviceInfo ? deviceInfo.versionCode : 0,
        channelIds: [channel],
        platform: platform
    });
};

/**
 * 获取充值套餐
* @param {countryCode: "", type: 0, version: 0}
 * @returns {"code":0,"data":[{"amount":0,"countryCode":"string","currency":"string","goldCoins":0,"id":0,"subject":"string","charge_reward": 50, "vipLevel": 0}],"message":"string","sucessed":true}
 */
const getProducts = async (data) => {
    const deviceInfo = await getDeviceInfo();
    return await http.get("/payermax/getPayConfig", {
        params: {
            countryCode: data.countryCode,
            version: deviceInfo ? deviceInfo.versionCode : 0,
            type: data.type
        }
    });
};

/**
 * 三方支付下单
 * @param {payPackageId:0, type: 0, orderProductType: "coin", orderParam: {giveUid: 0, vipLevel: 0}}
 * @returns {"code":0,"data":{"orderId":0, "requestUrl":""},"message":"string","sucessed":true}
 */
const createOrder = async (data) => {
    return await http.post("/payermax/createOrder", data);
};

/**
 * Native支付方式下单
 * @param {productId: "", orderProductType: "coin", orderParam: {giveUid: 0, vipLevel: 0}, channel: ""}
 * @returns {"code":0,"data":{"seqId":0, "innerOrderId":""},"message":"string","sucessed":true}
 */
const createNativeOrder = async (data) => {
    return await http.post("/pay/createOrder", {
        seqId: new Date().getTime(),
        innerProductId: data.productId,
        orderParam: data.orderParam,
        orderProductType: data.orderProductType,
        channel: data.channel,
        renew: data.renew
    });
};

/**
 * 拉取币商充值方式入口配置
 * @param {countryCode: "", versionCode: 0, channel: ""}
 * @returns { code: 200, message: "succeed", sucessed: true}
 */
const getMerchantConfig = async (data) => {
    const deviceInfo = await getDeviceInfo();
    return await http.get("/config/getMerchantConfig", {
        params: {
            countryCode: data.countryCode,
            versionCode: deviceInfo ? deviceInfo.versionCode : 0,
            channel: deviceInfo ? deviceInfo.appChannel : "gp"
        }
    });
};

/**
 * 验证订单，目前用于金币购买vip，所以传递的参数较少
 * @param {innerOrderId: "", channel: ""} 
 * @returns 
 */
const verifyOrder = async (data) => {
    return await http.post("pay/verifyOrder", {
        seqId: new Date().getTime(),
        innerOrderId: data.innerOrderId,
        channel: data.channel,
        renew: data.renew
    });
};

export default {
    getChannels,
    getProducts,
    createOrder,
    getNativeProductConfig,
    createNativeOrder,
    getMerchantConfig,
    verifyOrder
};
