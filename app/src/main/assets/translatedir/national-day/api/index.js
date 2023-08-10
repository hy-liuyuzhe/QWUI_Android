import http from "../../../common/network/http/index";

/**
 * 获取升国旗活动
 * @param type: 活动类型
 * @returns {code: 200, message: ""}
 */
const getFlagRaisingActivityInfo = async () => {
  return await http.get("/activity/getFlagRaisingActivityInfo");
};

export default {
  getFlagRaisingActivityInfo,
};
