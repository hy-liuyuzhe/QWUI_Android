import http from "../../../common/network/http/index";

/**
 * 获取古尔邦节活动信息
 * @returns
 */
const getEidAlAdhaActivityInfo = async () => {
  return await http.get("/activity/getCorbanActivityInfo", null);
};

export default {
  getEidAlAdhaActivityInfo,
};
