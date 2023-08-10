<template>
  <div class="we-page">
    <van-image class="top-bg-image-view" :src="topBgImgSrc" />
    <div class="content-v-layout">
      <navigation-bar
        :type="navigationStyle"
        @on-back-click="onNavBackClick($event)"
      />
      <span class="time-text-view">{{ activityTime }}</span>
      <div class="tab-h-layout">
        <div class="reward-tab" @click="onTabClick(0)">
          <img class="tab-img" :src="getTabImg(0)" />
          <span class="tab-text-view">{{ $t("couple_award") }}</span>
        </div>
        <div class="list-tab" @click="onTabClick(1)">
          <img class="tab-img" :src="getTabImg(1)" />
          <span class="tab-text-view">{{ $t("couple_list") }}</span>
        </div>
      </div>
      <template v-if="selectedTab == 0">
        <div class="reward-content-v-layout">
          <span class="reward-tip-text-view">{{
            $t("couple_rank_reward_tip")
          }}</span>
          <div
            class="reward-list-v-layout"
            v-for="(item, index) in rewardInfos"
            :key="index"
          >
            <reward-item class="reward-item" :rewardInfo="item"></reward-item>
          </div>
        </div>
      </template>
      <template v-else>
        <div class="rank-list-content-v-layout">
          <div
            class="rank-list-v-layout"
            v-for="(item, index) in rankingInfos"
            :key="index"
          >
            <rank-item class="rank-item" :rankingInfo="item"></rank-item>
          </div>
        </div>
      </template>
    </div>
    <my-rank-item
      class="my-rank"
      :rankingInfo="myRankingInfo"
      @on-group-cp-click="onGroupCpClick"
      v-if="showMyRanking"
    ></my-rank-item>
    <van-popup v-model="showNoCpTipDialog" get-container="body" round>
      <common-dialog
        :messageText="$t('couple_no_cp')"
        :primaryButtonText="$t('couple_group_cp')"
        @on-primary-button-click="onNoCpConfirmDialogClick"
      />
    </van-popup>
    <van-popup v-model="showUpgradeVersionDialog" get-container="body" round>
      <common-dialog
        :messageText="$t('couple_update_version')"
        :primaryButtonText="$t('common_go_update')"
        @on-primary-button-click="onVersionUpdateDialogGoUpdateClick"
      />
    </van-popup>
    <van-popup v-model="showRewardSentDialog" get-container="body" round>
      <common-dialog
        :messageText="rewardSentMsg"
        :primaryButtonText="$t('common_confirm')"
        @on-primary-button-click="onRewardSentConfirmClick"
      />
    </van-popup>
  </div>
</template>
<script>
import _ from "lodash";
import moment from "moment";
import Vue from "vue";
import { Image as VanImage, Toast, Popup } from "vant";
import {
  hideNavigationBar,
  closeWindow,
  getMyUserInfo,
  getVersionCode,
  dispatchDeeplink,
  android,
} from "../../../common/bridge";
import { NAVIGATION_BAR_STYLE } from "@/common/constant/index";
import NavigationBar from "@/common/components/NavigationBar";
import RewardItem from "./components/RewardItem.vue";
import RankItem from "./components/RankItem.vue";
import MyRankItem from "./components/MyRankItem.vue";
import api from "../api/index";
import { RES_COMMON_CODE } from "../../../common/network/constant";
import CommonDialog from "@/common/components/CommonDialog";
import {
  addNativeMessageListener,
  removeNativeMessageListener,
} from "../../../common/bridge/base";
import { MSG_TYPE } from "../../../common/bridge/constant";
import { CP_RANK_REWARD_TIME } from "../storage/key";
import { isSameMonth } from "../../../common/util/time/index";

Vue.use(VanImage);
Vue.use(Toast);
Vue.use(Popup);
export default {
  name: "CoupleRank",
  components: {
    "navigation-bar": NavigationBar,
    "reward-item": RewardItem,
    "rank-item": RankItem,
    "my-rank-item": MyRankItem,
    "common-dialog": CommonDialog,
  },
  data() {
    return {
      navigationStyle: NAVIGATION_BAR_STYLE.LIGHT,
      startTime: 0,
      endTime: 0,
      selectedTab: 0,
      rewardInfos: [],
      rankingInfos: [],
      myRankingInfo: {},
      myUserInfo: {},
      showNoCpTipDialog: false,
      showUpgradeVersionDialog: false,
      showRewardSentDialog: false,
      rewardSentMsg: "",
      overShown: false,
      noCpDialogShown: false,
    };
  },
  created() {
    hideNavigationBar();
    this.getCpActivityConfig();
    let self = this;
    addNativeMessageListener(MSG_TYPE.ON_RESUME, (_) => {
      self.getCpActivityConfig();
    });
  },
  methods: {
    onNavBackClick() {
      window.history.length > 1 ? this.$router.go(-1) : closeWindow();
    },
    getTabImg(index) {
      return index == this.selectedTab
        ? require(`./assets/couple_select_tab_ic.png`)
        : require(`./assets/couple_unselect_tab_ic.png`);
    },
    onTabClick(index) {
      this.selectedTab = index;
    },
    async getCpActivityConfig() {
      const { code, data } = await api.getCpActivityInfo();
      if (code !== RES_COMMON_CODE.SUCCESS) {
        return;
      }
      const {
        rewardInfos,
        rankingInfos,
        myRankingInfo,
        myRewardInfo,
        startTime,
        endTime,
      } = data;
      this.rewardInfos = rewardInfos;
      this.rankingInfos = rankingInfos;
      this.myRankingInfo = myRankingInfo;
      this.startTime = startTime;
      this.endTime = endTime;
      const currentTime = new Date().getTime();
      if (currentTime > endTime) {
        if (!this.overShown) {
          Toast(this.$t("common_activity_end"));
          this.overShown = true;
        }
        if (!_.isEmpty(myRewardInfo)) {
          this.rewardSentMsg = this.$t("couple_gift_send", {
            gift: myRewardInfo.name,
          });
          this.popupRewardSentDialogIfNeed(myRankingInfo.uid);
        }
        return;
      }
      if (_.isEmpty(myRankingInfo)) {
        const userInfo = await getMyUserInfo();
        this.myUserInfo = userInfo;
        this.myRankingInfo = {
          ranking: 0,
          uid: userInfo.uid,
          sid: userInfo.sid,
          name: userInfo.name,
          avatar: userInfo.url,
        };
        if (!this.noCpDialogShown) {
          this.noCpDialogShown = true;
          this.showNoCpTipDialog = true;
        }
      }
    },
    onNoCpConfirmDialogClick() {
      this.showNoCpTipDialog = false;
      this.onGroupCpClick();
    },
    goGroupCp() {
      if (_.isNil(this.myUserInfo.uid)) {
        return;
      }
      dispatchDeeplink({
        deeplink: `wenext://wayak/userProfile?extra_uid=${this.myUserInfo.uid}&extra_tab_index=1`,
        path: "/userProfile",
      });
    },
    async onGroupCpClick() {
      let isAndroid = await android();
      if (isAndroid) {
        let versionCode = await getVersionCode();
        if (versionCode && versionCode < 108) {
          this.showUpgradeVersionDialog = true;
          return;
        }
      }
      this.goGroupCp();
    },
    onVersionUpdateDialogGoUpdateClick() {
      this.showUpgradeVersionDialog = false;
      location.href = "market://details?id=com.wenext.wayak";
    },
    onRewardSentConfirmClick() {
      this.showRewardSentDialog = false;
    },
    popupRewardSentDialogIfNeed(uid) {
      const shownTime = this.$localStorage.get(`${CP_RANK_REWARD_TIME}${uid}`, 0);
      const currentTimeMs = new Date().getTime();
      if (
        shownTime == 0 ||
        (shownTime != 0 && !isSameMonth(shownTime, currentTimeMs))
      ) {
        this.showRewardSentDialog = true;
        this.$localStorage.set(CP_RANK_REWARD_TIME, currentTimeMs);
      }
    },
  },
  computed: {
    showMyRanking() {
      return !_.isEmpty(this.myRankingInfo);
    },
    topBgImgSrc() {
      let languageCode = this.$i18n.locale;
      if (languageCode === "ar") {
        return require(`./assets/couple_rank_head_ic_ar.png`);
      }
      return require(`./assets/couple_rank_head_ic_en.png`);
    },
    activityTime() {
      return `${moment(this.startTime).format("YYYY.MM.DD")}-${moment(
        this.endTime
      ).format("MM.DD")}`;
    },
  },
  destroyed() {
    removeNativeMessageListener(MSG_TYPE.ON_RESUME);
  },
};
</script>
<style lang="scss" scoped>
@use "@/common/css/compat.scss" as compat;
@use "@/common/css/index.scss" as *;
@import "@/common/css/var.scss";

@include we(page) {
  width: 100%;
  box-sizing: border-box;
  position: relative;
  background-color: #fee7ef;
  @include image-view(top-bg, 750px, 693px) {
    position: absolute;
    top: 0;
    left: 0;
  }
  @include v-layout(content) {
    width: 100%;
    height: 100%;
    overflow: scroll;
    padding-bottom: 200px;
    @include text-view(
      time,
      $font-size: 40px,
      $line-height: 48px,
      $font-color: #ffffff,
      $font-weight: $--font-weight-medium
    ) {
      width: 400px;
      z-index: 1;
      margin-top: 340px;
      text-align: center;
      text-shadow: 0px 0px 20px rgba(224, 108, 251, 0.9);
    }
    @include h-layout(tab) {
      width: 100%;
      justify-content: center;
      margin-top: 40px;
      .reward-tab {
        width: 340px;
        height: 94px;
        position: relative;
      }
      .list-tab {
        width: 340px;
        height: 94px;
        @include compat.margin-start(24px);
        position: relative;
      }
      .tab-img {
        width: 340px;
        height: 94px;
      }
      @include text-view(
        tab,
        $font-size: 40px,
        $line-height: 94px,
        $font-color: #ffffff,
        $font-weight: $--font-weight-medium
      ) {
        position: absolute;
        left: 0;
        top: 0;
        right: 0;
        text-align: center;
      }
    }
    @include v-layout(reward-content) {
      width: 100%;
      min-height: 800px;
      @include text-view(
        reward-tip,
        $font-size: 24px,
        $line-height: 33px,
        $font-color: #c94e9b,
        $font-weight: $--font-weight-normal
      ) {
        width: 400px;
        margin-top: 24px;
        text-align: center;
      }
      @include v-layout(reward-list) {
        .reward-item {
          margin-top: 24px;
        }
      }
    }
    @include v-layout(rank-list-content) {
      width: 100%;
      margin-top: 24px;
      min-height: 800px;
      @include v-layout(rank-list) {
        width: 100%;
        .rank-item {
          margin-top: 12px;
        }
      }
    }
  }
  .my-rank {
    position: fixed;
    bottom: 0;
    left: 0;
  }
}
</style>
