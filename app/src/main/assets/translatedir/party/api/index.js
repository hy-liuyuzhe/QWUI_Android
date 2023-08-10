import http from "../../../common/network/http/index";

/**
 * 派对活动配置
 * @returns {"code":0,"message":"string","sucessed":true,"data":{selectiveActivityType: {"typeId":1,"typeName":"","beginTime":0,"endTime":0,"activityNumLimit":0,"activityLimitPerUser":0
 * "rankRewards":[{"diamonds":0,"roomThemeId":0,"roomGiftSpeedupMultitimes":0}]}）
 */
const getPartyActivityConfig = async () => {
    return await http.get("/square_activity/get_config_info");
};

/**
 * 派对榜单
 * @param { activityType: 0 },
 * @returns {"code":0,"message":"string","sucessed":true,"data": {rankList: [{"activityId":0, "creatorUid": 0, "creatorId": 0, "creatorUrl": "", "creatorNicName": "", "maxOnline": 0,
 * "diamonds":0, "hot": 0, "addRankBoardTime": 0}], myRank: 1}
 * ）
 */
const getActivityRank = async (data) => {
    return await http.get("/square_activity/activity_rank", {
        params: {
            activityType: data.activityType
        }
    });
};

export default {
    getPartyActivityConfig,
    getActivityRank
}