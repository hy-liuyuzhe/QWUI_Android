import api from "../api/index";
import { RES_COMMON_CODE } from "../../../common/network/constant";
import { RANK_TYPE } from "../constant/constant";

const getStarRewardIcon = (rank) => {
    if (rank == 1) {
        return require('../assets/star_star_reward_1.png');
    } else if (rank == 2) {
        return require('../assets/star_star_reward_2.png');
    } else if (rank == 3) {
        return require('../assets/star_star_reward_3.png');
    } else {
        return ""
    }
}

const getRichRewardIcon = (rank) => {
    if (rank == 1) {
        return require('../assets/star_rich_reward_1.png');
    } else if (rank == 2) {
        return require('../assets/star_rich_reward_2.png');
    } else if (rank == 3) {
        return require('../assets/star_rich_reward_3.png');
    } else {
        return ""
    }
}

const starRewardItemList = [
    {
        icon: getStarRewardIcon(1),
        days: 0,
        diamond: 10000
    },
    {
        icon: getStarRewardIcon(2),
        days: 0,
        diamond: 8000
    },
    {
        icon: getStarRewardIcon(3),
        days: 0,
        diamond: 3000
    }
];
const richRewardItemList = [
    {
        icon: getRichRewardIcon(1),
        days: 7,
        diamond: 0
    },
    {
        icon: getRichRewardIcon(2),
        days: 7,
        diamond: 0
    },
    {
        icon: getRichRewardIcon(3),
        days: 7,
        diamond: 0
    }
];

export default {
    namespaced: true,
    state: () => ({
        richRewardList: richRewardItemList, //富豪榜奖励列表
        starRewardList: starRewardItemList, //星光榜奖励列表
        startRankList: [],//星光榜(收礼榜)
        richRankList: [],//富豪榜(送礼榜)
        mineRichRankInfo: {},
        mineStarRankInfo: {},
        config: {}
    }),
    mutations: {
        refreshRankList(state, payload) {
            if (payload.type == RANK_TYPE.RANK_TYPE_STAR) {
                state.startRankList = payload.result;
            } else {
                state.richRankList = payload.result;
            }
            state.config = payload.config;
        },
        refreshMyRankInfo(state, payload) {
            if (payload.type == RANK_TYPE.RANK_TYPE_STAR) {
                state.mineStarRankInfo= payload.result;
            } else {
                state.mineRichRankInfo = payload.result;
            }
        }
    },
    actions: {
        async getRankList({ commit }, payload) {
            const reqData = {
                type: payload.type
            }
            const rankListRes = await api.getStarRankList(reqData);
            const { code, data } = rankListRes;
            if (code == RES_COMMON_CODE.SUCCESS) {
                if (data.mine) {
                    data.mine.type = payload.type;
                    data.mine.no = 0;
                    const myUid = data.mine.uid;
                    if (data.ranking && data.ranking.length > 0) {
                        data.ranking.forEach((rankInfo, index) => {
                            if (rankInfo.uid == myUid) {
                                data.mine.no = index + 1
                            }
                        });
                    }
                    commit("refreshMyRankInfo", { result: data.mine, type: payload.type});
                }
                commit("refreshRankList", { result: data.ranking, type: payload.type, config: data.config }); 
            }
        }
    },
    getters: {},
  };