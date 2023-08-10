import http from "../../../common/network/http/index";

/**
 * 获取充值活动配置
 * @returns
 */
const getRechargeActivityInfo = async () => {
  return await http.get("/special_recharge/getRechargeActivityInfo");
};

/**
 * 获取充值记录
 */
const getRechargeRecords = async (page) => {
  return await http.get("/special_recharge/getRechargeRecords", {
    params: {
      currentPage: page,
      pageSize: 20,
    },
  });
};

/**
 * 特殊充值
 * @param {*} data {channel:0, amount:0, payAccount:"", payImg:""}
 * @returns
 */
const recharge = async (data) => {
  return await http.post("/special_recharge/recharge", data);
};

export default {
  getRechargeActivityInfo,
  recharge,
  getRechargeRecords,
};
