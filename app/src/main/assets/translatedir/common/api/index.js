import http from "../../../common/network/http/index";
import qs from "qs";

/**
 * 获取其他用户信息
 * @returns
 */
const getUserInfoByGoodOrShortId = async (shortId) => {
    return await http.get("/user/getUserInfoByGoodOrShortId/" + shortId, null);
};

/**
 * 获取金币数
 */
const getMyCoins = async () => {
    return await http.get(`/currency/get?currencies=0&seqId=` + new Date().getTime(), null);
};

/**
 * 举报用户
 * @param {reportedUid: 0, reportType: 8, reason: 8, videoList: "", imgList: ""}
 */
const reportUser = async (data) => {
    return await http.post("/user/reportUser", data);
};

/**
 * 获取房间信息
 * @param {roomIds: [], needRoomOnlineInfo: false}
 */
const getRoomInfo = async (data) => {
    return await http.post("/room/info", data);
};

/**
 * 更新用户配置
 * @param {configId: 0, configValue: ""}
 */
 const updateUserConfig = async (data) => {
    return await http.post("config/updateCommonConfig", data);
};

/**
 * 拉取房间通用配置
 * @param {*} roomId 房间id
 * @param {*} configs 配置id
 * @returns 
 */
const getGlobalRoomConfig = async (roomId, configs) => {
    return await http.post("config/getGlobalRoomConfig", {
        roomId: roomId,
        configs: configs
    })
}

/**
 * 
 * @returns 获取我的房间信息
 */
const getMyRoomInfo = async () => {
    return await http.get("room/myroom", {
        params: {
            seqid: new Date().getTime()
        }
    });
}

/**
 * 设置用户房间配置
 * @param {*} roomId 房间id
 * @param {*} configInfo 配置信息 {configType: 0, configValue: "json"}
 * @returns 
 */
const setUserRoomConfig = async (roomId, configInfo) => {
    return await http.post("config/setUserRoomConfig", {
        roomId: roomId,
        configInfo: configInfo
    })
}

/**
 * 关注或者取消关注
 * @param {*} followType 关注操作类型，0:取消关注 1关注
 * @param {*} uid uid
 * @param {*} fromScene 场景
 * @returns 
 */
const followUser = async (followType, uid, fromScene) => {
    return await http.post("user/follow", {
        followType: followType,
        uid: uid,
        fromScene: fromScene
    })
}

/**
 * 获取关注关系
 * @param {*} uid 
 * @returns {followed: false, toBeFollowed: false}
 */
const isUserFollowed = async (uid) => {
    return await http.get(`user/getUserRelationShip/${uid}`, null);
};

/**
 * 批量拉取用户信息
 * @param {ids: [0,1,2]}
 * @returns {"code":0,"message":"string","sucessed":true,"data":{[{uid: 0, userInfo: {avatar: "", name: ""}}]}}
 */
const getUserInfos = async (uids) => {
    return await http.get("user/getUserInfoListByUid", {
        params: {
            ids: uids
        },
        paramsSerializer: params => {
            return qs.stringify(params, { indices: false })
        }
    });
};

/**
 * 批量拉取用户通用配置
 * @param {*} uids 
 * @param {*} configTypes 
 * @returns 
 */
const getBatchUserCommonConfig = async (uids, configTypes) => {
    return await http.post("config/getBatchUserCommonConfig", {
        seqid: 0,
        uids: uids,
        configTypes: configTypes
    })
}

/**
 * 获取家族信息
 * @param {*} familyId 家族id
 * @returns 
 */
const getUserFamilyInfo = async (familyId) => {
    return await http.get("family/getUserFamilyInfo", {
        params: {
            familyId: familyId
        },
    });
};

/**
 * 获取主播消息
 */
const getAnchorMessage = async () => {
    return await http.post("/config/getAnchorMessage");
};

/**
 * 回复主播消息
 * @param {messageId:1,messagetype:6,replyCode:1}
 */
const replyAnchorMessage = async (data) => {
    return await http.post("/config/replyAnchorMessage", data);
};

/**
 * 拉取通用配置
 * @param {*} configs 配置id
 * @returns 
 */
const getGlobalConfig = async (configs) => {
    return await http.post("config/getGlobalConfig", {
        seqid: 0,
        configs: configs
    })
}

/**
 * 根据商品ID获取商品详情
 * @param {*} goodsList goodIdList
 * @returns goodInfo
 */
const getGoodsInfoById = async (goodsIdList) => {
    return await http.get("/goods/getGoodsInfoById", {
        params: {
            goodsList: goodsIdList
        },
        paramsSerializer: params => {
            return qs.stringify(params, { indices: false })
        }
    });
}

export default {
    getUserInfoByGoodOrShortId,
    getMyCoins,
    reportUser,
    getRoomInfo,
    updateUserConfig,
    getGlobalRoomConfig,
    getMyRoomInfo,
    setUserRoomConfig,
    followUser,
    isUserFollowed,
    getUserInfos,
    getBatchUserCommonConfig,
    getUserFamilyInfo,
    getAnchorMessage,
    replyAnchorMessage,
    getGlobalConfig,
    getGoodsInfoById
};
