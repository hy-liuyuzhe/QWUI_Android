import api from "../api/index";
import { RES_COMMON_CODE } from "../../../common/network/constant";
import _ from "lodash";

const UPDATE_RECHARGE_ACTIVITY_INFO = "update_recharge_activity_info";
const UPDATE_RECHARGE_ACTIVITY_RES = "update_recharge_activity_res";

export default {
  namespaced: true,
  state: () => ({
    userRechargeInfo: {
      amount: 0,
      lastMonthAmount: 0,
      coinsPrice: 0,
      advanceAmount: 0,
      level: 0,
      showProgressReward: 0,
    }, //用户充值信息
    rechargeProgressAdards: [], //充值进步奖励
    rechargeLevelInfos: [], //充值等级信息
    rechargeLevelProgress: [], //充值等级进度
    rechargeAccountInfo: {
      officialPayonner: "",
      officialPaypal: "",
      myPayonner: "",
      myPaypal: "",
    }, //充值账号信息
    rechargeActivityInfoRes: null, //充值活动信息结果
  }),
  mutations: {
    [UPDATE_RECHARGE_ACTIVITY_INFO](state, payload) {
      const {
        userRechargeInfo,
        rechargeProgressAdards,
        rechargeLevelInfos,
        rechargeLevelProgress,
        rechargeAccountInfo,
      } = payload;
      state.userRechargeInfo = userRechargeInfo;
      state.rechargeProgressAdards = rechargeProgressAdards;
      state.rechargeLevelInfos = rechargeLevelInfos;
      state.rechargeAccountInfo = rechargeAccountInfo;
      state.rechargeLevelProgress = rechargeLevelProgress;
    },
    [UPDATE_RECHARGE_ACTIVITY_RES](state, payload) {
      state.rechargeActivityInfoRes = payload;
    },
  },
  actions: {
    async getRechargeActivityInfo({ commit }) {
      const res = await api.getRechargeActivityInfo();
      commit(UPDATE_RECHARGE_ACTIVITY_RES, res);
      const { code, data } = res;
      if (code !== RES_COMMON_CODE.SUCCESS) {
        return;
      }

      const {
        configInfo,
        packageInfo,
        userRechargeInfo,
        progressRewardInfo,
        accountInfo,
      } = data;
      const { levelConfig } = configInfo;
      const rechargeLevelInfos = _.map(levelConfig, (config) => {
        config.awards = packageInfo[config.level];
        return config;
      });
      const rechargeLevelProgress = _.dropRight(levelConfig);
      commit(UPDATE_RECHARGE_ACTIVITY_INFO, {
        rechargeAccountInfo: accountInfo,
        userRechargeInfo,
        rechargeProgressAdards: progressRewardInfo,
        rechargeLevelProgress,
        rechargeLevelInfos,
      });
    },
  },
};
