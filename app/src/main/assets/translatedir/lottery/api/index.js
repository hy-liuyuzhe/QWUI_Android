import http from "../../../common/network/http/index";
import qs from "qs";
import { CURRENCY_TYPE } from "../constant/index";

/**
 * 获取指定类型抽奖活动
 * @param type: 活动类型
 * @returns {code: 200, message: ""}
 */
const getLotteryActivity = async (type) => {
  return await http.get(`/activity/getLotteryActivity`, {
    params: {
      type: type,
    },
  });
};

/**
 * 抽奖
 * @param activityId: 活动ID
 * @param lotteryType: 抽奖类型
 * @returns {code: 200, message: ""}
 */
const lottery = async (activityId, lotteryType) => {
  return await http.post("/activity/lottery", { activityId, lotteryType });
};

/**
 * 获取代金券
 * @returns {code: 200, message: ""}
 */
const getRaffleTicket = async () => {
  return await http.get(`/currency/get`, {
    params: {
      seqId: new Date().getTime(),
      currencies: [CURRENCY_TYPE.RAFFLE_TICKET],
    },
    paramsSerializer: (params) => {
      return qs.stringify(params, { indices: false });
    },
  });
};

/**
 * 获取自己的抽奖记录
 * @param activityType: 活动类型
 * @returns {code: 200, message: ""}
 */
const getUserLotteryResults = async (activityType) => {
  return await http.get(`/activity/getUserLotteryResults`, {
    params: {
      activityType,
    },
  });
};

/**
 * 获取全部用户最新20条抽奖记录
 * @param activityType: 活动类型
 * @returns {code: 200, message: ""}
 */
const getLatest20LotteryResults = async (activityType) => {
  return await http.get(`/activity/getLatest20LotteryResults`, {
    params: {
      activityType,
    },
  });
};

/**
 * 获取碎片兑换配置
 * @param activityType: 活动类型
 * @returns {code: 200, message: ""}
 */
const getDebrisExchangeItems = async (activityType) => {
  return await http.get(`/activity/getDebrisExchangeItems`, {
    params: {
      activityType,
    },
  });
};

/**
 * 碎片兑换
 * @param exchangeId: 兑换ID
 * @returns {code: 200, message: ""}
 */
const debrisExchange = async (exchangeId) => {
  return await http.post("/activity/debrisExchange", { exchangeId });
};

/**
 * 获取碎片
 * @returns {code: 200, message: ""}
 */
const getDebris = async () => {
  return await http.get(`/currency/get`, {
    params: {
      seqId: new Date().getTime(),
      currencies: [CURRENCY_TYPE.DEBRIS],
    },
    paramsSerializer: (params) => {
      return qs.stringify(params, { indices: false });
    },
  });
};

/**
 * 获取抽奖券
 * @returns {code: 200, message: ""}
 */
const getLotteryTickets = async () => {
  return await http.get(`/currency/get`, {
    params: {
      seqId: new Date().getTime(),
      currencies: [CURRENCY_TYPE.LOTTERY_TICKETS],
    },
    paramsSerializer: (params) => {
      return qs.stringify(params, { indices: false });
    },
  });
};

/**
 * 开抽奖礼盒
 * @param {*} activityId 活动id
 * @param {*} energyValue 礼盒所需能量值
 * @returns
 */
const openLotteryEnergyBox = async (activityId, energyValue) => {
  return await http.post(`/activity/openLotteryEnergyBox`, {
    activityId,
    energyProgress: energyValue,
  });
};

export default {
  getLotteryActivity,
  lottery,
  getRaffleTicket,
  getLatest20LotteryResults,
  getUserLotteryResults,
  getDebrisExchangeItems,
  debrisExchange,
  getDebris,
  openLotteryEnergyBox,
  getLotteryTickets
};
