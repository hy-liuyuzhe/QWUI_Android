import http from "../../../common/network/http/index";

/**
 * cp配置
 */
const getCoupleConfig = async () => {
    return await http.get("/config/getIntimacyConfigInfo");
};

/**
 * 拉取CP榜单活动配置
 */
const getCpActivityInfo = async () => {
    return await http.get("/activity/getCpActivityInfo");
};

/**
 * 获取报名活动的cp
* @param {activityId: 1, filterOnline: false}
* @returns {}
 */
const getCPActivitySignupInfo = async (data) => {
    return await http.get("/activity/getCPActivitySignupInfo", {
        params: {
            activityId: data.activityId,
            filterOnline: data.filterOnline
        }
    });
};

/**
 * CP活动报名
* @param {activityId: 1, signupParam: ""}
* @returns {}
 */
const signUp = async (data) => {
    return await http.post("/activity/signup", data);
};

/**
 * CP活动榜单
* @param {activityId: 1}
* @returns {}
 */
const getCPActivityBoard = async (data) => {
    return await http.get("/activity/getCPActivityBoard", {
        params: { activityId: data.activityId }
    });
};

/**
 * 获取我的亲密关系
* @param {uid: 1, type: 0, status: 0}
* @returns {}
 */
const getIntimacyInfo = async (data) => {
    return await http.get("/user/getIntimacyInfo", {
        params: {
            uid: data.uid,
            type: data.type,
            status: data.status
        }
    });
};

/**
 * 拉取CP活动配置
* @param {activityId: 1, querySignupList: true}
* @returns {}
 */
const getCPCommonActivityInfo = async (data) => {
    return await http.get("/activity/getCPCommonActivityInfo", {
        params: {
            activityId: data.activityId,
            querySignupList: data.querySignupList,
        },
    });
};

/**
 * 邀请CP上麦
* @param {roomId: 1, activityId: 1, cpIntimacyId: "", activityTag: ""}
* @returns {}
 */
const inviteCP = async (data) => {
    return await http.post("/mic/inviteCP", data);
};

/**
 * 获取cp旅游数据
 * @param {lang: en}
 */
const getCpTravelActivityInfo = async (data) => {
    return await http.get("/activity/getCpTravelActivityInfo", {
        params: {
            lang: data.lang
        }
    });
};

/**
 * 获取cp旅游奖励信息
 */
const getCpTravelRewardInfo = async () => {
    return await http.get("/activity/getCpTravelRewardInfo");
};

/**
 * Cp旅游抽奖
 * @param {"intimacyId":"1001:2002:1","index":1, "lang": "zh"}
 */
const cpTravelLottery = async (data) => {
    return await http.post("/activity/cpTravelLottery", data);
};

/**
 * cp旅游活动所有获得奖励的用户信息
 */
const getCpTravelAllWinInfo = async () => {
    return await http.get("/activity/getCpTravelAllWinInfo");
};

export default {
    getCoupleConfig,
    getCpActivityInfo,
    getCPActivitySignupInfo,
    signUp,
    getCPActivityBoard,
    getIntimacyInfo,
    getCPCommonActivityInfo,
    inviteCP,
    getCpTravelActivityInfo,
    getCpTravelRewardInfo,
    cpTravelLottery,
    getCpTravelAllWinInfo
}