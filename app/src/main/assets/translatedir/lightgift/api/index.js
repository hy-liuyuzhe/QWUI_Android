import http from "../../../common/network/http/index";

/**
 * 获取点亮活动信息
 * @returns {code: 200, message: "", data: {"endTime":0,"exp":0,"goodsDay":0,"goodsId":0,"goodsImg":"string","lightUpGiftInfoList":[{"giftId":0,"giftImg":"string","times":0}],"show":false,"startTime":0}}
 */
const getLightUpActivity = async () => {
  return await http.get(`/activity/getLightUpActivity`);
};

/**
 * 获取点亮活动获奖信息
 * @returns {code: 200, message: "", data: {"exp":0,"goodsDay":1,"goodsImg":"string","times":0,"namedGiftTimes":7}}
 */
const getLightUpRewardInfo = async () => {
  return await http.get(`/activity/getLightUpRewardInfo`);
};

export default {
  getLightUpActivity,
  getLightUpRewardInfo
};
