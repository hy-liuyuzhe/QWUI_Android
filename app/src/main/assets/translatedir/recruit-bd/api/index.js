import http from "../../../common/network/http/index";

/**
 * 拉取bd信息
 * @returns
 */
const getBdInfo = async () => {
  return await http.get("/config/getBdInfo");
};

/**
 * 提交bd信息
 * @returns
 */
const addBdInfo = async (data) => {
  return await http.post("/config/addBdInfo", data);
};

export default {
  getBdInfo,
  addBdInfo,
};