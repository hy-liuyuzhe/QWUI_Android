import api from "../api/index";
import _ from "lodash";
import { RES_COMMON_CODE } from "../../../common/network/constant";

const UPDATE_PUNISHMENT_RECORDS = "update_punishment_records";
const SET_PENALIZE_USER_RESULT = "set_penalize_user_result";

export default {
  namespaced: true,
  state: () => ({
    remainNormalPunishCount: 0, //剩余普通封禁数量
    remainSeverePunishCount: 0, //剩余严重封禁数量
    punishmentRecords: [], //惩罚记录
    penalizeUserResult: RES_COMMON_CODE.SUCCESS, //用户处罚结果
  }),
  mutations: {
    [UPDATE_PUNISHMENT_RECORDS](state, payload) {
      const { remainNormalPunishCount, remainSeverePunishCount, punishmentRecords } = payload;
      state.remainNormalPunishCount = remainNormalPunishCount;
      state.remainSeverePunishCount = remainSeverePunishCount;
      state.punishmentRecords = punishmentRecords;
    },
    [SET_PENALIZE_USER_RESULT](state, payload) {
      const { code } = payload;
      state.penalizeUserResult = code;
    },
  },
  actions: {
    async getPunishmentRecords({ commit }) {
      const res = await api.getPublishmentRecords();
      const { code, data } = res;
      if (code === RES_COMMON_CODE.SUCCESS) {
        const { normalNum, severeNum, punishedInfoList } = data;
        commit(UPDATE_PUNISHMENT_RECORDS, {
          remainNormalPunishCount: normalNum,
          remainSeverePunishCount: severeNum,
          punishmentRecords: punishedInfoList,
        });
      }
    },
    async penalizeUser({ commit, dispatch }, payload) {
      const {
        beReportedUid,
        punishmentOperations,
        punishmentReason,
        evidencePictures,
        evidenceVideos,
      } = payload;

      const res = await api.penalizeUser({
        reportedUid: beReportedUid,
        operations: punishmentOperations,
        reason: punishmentReason,
        imgList: JSON.stringify(evidencePictures),
        videoList: JSON.stringify(evidenceVideos),
      });
      const { code } = res;
      commit(SET_PENALIZE_USER_RESULT, { code });
      if (code === RES_COMMON_CODE.SUCCESS) {
        dispatch("getPunishmentRecords");
      }
    },
  },
};
