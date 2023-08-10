import api from "../api/index";
import { RES_COMMON_CODE } from "../../../common/network/constant";

export default {
    namespaced: true,
    state: () => ({
        rankList: [], //活动派对榜单
        activityConfig: {}, //活动配置
        myRankInfo: undefined,
    }),
    mutations: {
        refreshRankList(state, payload) {
            state.rankList = payload.result;
        },
        refreshActivityConfig(state, payload) {
            state.activityConfig = payload.result;
        },
        refreshMyRankInfo(state, payload) {
            state.myRankInfo = payload.result;
        },
    },
    actions: {
        async getRankList({ commit }, payload) {
            const reqData = {
                activityType: payload.activityType
            };
            const rankListRes = await api.getActivityRank(reqData);
            const { code, data } = rankListRes;
            if (code == RES_COMMON_CODE.SUCCESS) {
                commit("refreshRankList", { result: data.rankList });
                if (data.rankList && data.rankList.length > 0) {
                    data.rankList.forEach((rankInfo, index) => {
                        if (index == data.myRank) {
                            rankInfo.rank = index + 1;
                            commit("refreshMyRankInfo", { result: rankInfo });
                        }
                    });
                }
            }
        },
        async getActivityConfig({ commit }, payload) {
            const configRes = await api.getPartyActivityConfig();
            const { code, data } = configRes;
            if (code == RES_COMMON_CODE.SUCCESS) {
                if (data.selectiveActivityType && data.selectiveActivityType.length > 0) {
                    data.selectiveActivityType.forEach((activityTypeInfo) => {
                        if (activityTypeInfo.typeId == payload.activityType) {
                            commit("refreshActivityConfig", { result: activityTypeInfo });
                        }
                    });
                }
            }
        }
    },
    getters: {},
};