import http from "../../../common/network/http/index";

/**
 * 获取斋月礼物活动数据 
 * @returns
 */
const getRamadanActivityInfo = async() => {
    return await http.get("/activity/getRamadanActivityInfo", null);
};

/**
 * 拉取用户斋月心愿礼物
 */
const getUserRamadanInfo = async(data) => {
    return await http.get("/activity/getUserRamadanInfo?uid=" + data);
};

/**
 * 设置我的斋月心愿
 * @param {*} data {"giftIds":[1,2]}
 */
const setUserRamadanInfo = async(data) => {
    return await http.post("/activity/addMyRamadanGift", data);
};


export default {
    getRamadanActivityInfo,
    getUserRamadanInfo,
    setUserRamadanInfo
}