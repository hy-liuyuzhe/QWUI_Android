import http from "../../../common/network/http/index";
import qs from 'qs'

/**
 * 榜单列表
 * @param { type: 0 }
 * @returns {"code":0,"message":"string","sucessed":true,"data":{ranking: [{amount: 0, avatar: "", uid: 0, username: ""}], mine: {amount: 0, avatar: "", uid: 0, username: ""}}）
 */
const getStarRankList = async (data) => {
    return await http.get("/rankboard/getStarActivityResult", {
        params: {
            type: data.type
        }
    });
};

/**
 * 批量拉取用户信息
 * @param {ids: [0,1,2]}
 * @returns {"code":0,"message":"string","sucessed":true,"data":{[{uid: 0, userInfo: {avatar: "", name: ""}}]}}
 */
const getUserInfos = async (uids) => {
    return await http.get("/user/getUserInfoListByUid", {
        params: {
          ids: uids
        },
        paramsSerializer: params => {
            return qs.stringify(params, { indices: false })
        }
      });
};

export default {
    getStarRankList,
    getUserInfos,
}