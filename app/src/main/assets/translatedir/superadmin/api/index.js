import http from "../../../common/network/http/index";

/**
 * 获取超级管理员处罚记录
 */
const getPublishmentRecords = async () => {
  return await http.get("/report/getSuperAdminPenaltyRecords");
};

/**
 * 处罚用户
 * @param {*} data {reportedUid: '', operations: [], reason: 0, imgList:[], videoList:[]}
 */
const penalizeUser = async (data) => {
  return await http.post("/user/superAdminPenalizeUser", data);
};

/**
 * 获取其他用户信息
 * @returns
 */
const getUserInfoByGoodOrShortId = async (shortId) => {
  return await http.get("/user/getUserInfoByGoodOrShortId/" + shortId, null);
};

/**
 * 查询是否为有效签约主播
 * @param {*} uid 查询uid
 * @returns {code: 0, data: true}
 */
const isSignedAnchor = async (uid) => {
  return await http.get("/config/isSignedAnchor", {
    params: {
      opUid: uid,
    },
  });
};

export default {
  getPublishmentRecords,
  penalizeUser,
  getUserInfoByGoodOrShortId,
  isSignedAnchor,
};
