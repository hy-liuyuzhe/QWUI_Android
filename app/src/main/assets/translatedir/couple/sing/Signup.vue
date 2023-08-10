<template>
  <div class="we-page">
    <div class="content-bg"></div>
    <van-image class="top-bg-image-view" :src="topBgImgSrc" />
    <div class="content-v-layout">
      <navigation-bar
        class="navigation-bar"
        :type="navigationStyle"
        @on-back-click="onNavBackClick($event)"
      />
      <span class="rules-button" @click="onRuleClick">{{
        $t("common_rule")
      }}</span>
      <sing-title-section
        class="section-reward"
        :title="$t('couple_activity_reward')"
      >
        <div class="rank-reward-layout">
          <activity-reward
            class="rank3-reward"
            :rank="3"
            :rewardInfos="rankRewards[2]"
            v-if="rankRewards.length > 2"
          ></activity-reward>
          <activity-reward
            class="rank1-reward"
            :rank="1"
            :rewardInfos="rankRewards[0]"
            v-if="rankRewards.length > 0"
          ></activity-reward>
          <activity-reward
            class="rank2-reward"
            :rank="2"
            :rewardInfos="rankRewards[1]"
            v-if="rankRewards.length > 1"
          ></activity-reward>
        </div>
      </sing-title-section>
      <sing-title-section
        class="section-signup-list"
        :title="$t('couple_signup_info')"
      >
        <div class="sign-up-list-v-layout">
          <signup-item
            class="signup-item"
            v-for="(signupInfo, index) in signupSuccessList"
            :key="index"
            :signupInfo="signupInfo"
          ></signup-item>
        </div>
      </sing-title-section>
    </div>
    <span
      :class="[signupBtnEnable ? 'signup-button' : 'signup-disable-button']"
      @click="onSignupBtnClick"
      >{{ signupBtnText }}</span
    >
    <van-popup v-model="showNoCpTipDialog" get-container="body" round>
      <common-dialog
        :messageText="$t('couple_sing_no_cp_tip')"
        :primaryButtonText="$t('couple_group_cp')"
        @on-primary-button-click="onNoCpConfirmDialogClick"
      />
    </van-popup>
    <van-popup v-model="showRuleDialog">
      <sing-activity-rule @close-btn-click="onRuleCloseClick" />
    </van-popup>
  </div>
</template>
<script>
import _ from "lodash";
import Vue from "vue";
import { Image as VanImage, Toast, Popup } from "vant";
import {
  hideNavigationBar,
  closeWindow,
  getMyUserInfo,
  dispatchDeeplink,
} from "../../../common/bridge";
import { NAVIGATION_BAR_STYLE } from "@/common/constant/index";
import NavigationBar from "@/common/components/NavigationBar";
import api from "../api/index";
import { RES_COMMON_CODE } from "../../../common/network/constant";
import CommonDialog from "@/common/components/CommonDialog";
import SingTitleSection from "./components/SingTitleSection.vue";
import ActivityReward from "./components/ActivityReward.vue";
import SignupItem from "./components/SignupItem.vue";
import { SIGNUP_STATUS } from "../constant/index";
import SingActivityRule from "./components/SingActivityRule.vue";

Vue.use(VanImage);
Vue.use(Toast);
Vue.use(Popup);
export default {
  name: "CoupleSignup",
  components: {
    "navigation-bar": NavigationBar,
    "sing-title-section": SingTitleSection,
    "activity-reward": ActivityReward,
    "signup-item": SignupItem,
    "common-dialog": CommonDialog,
    "sing-activity-rule": SingActivityRule,
  },
  created() {
    this.activityId = this.$route.query.activity_id;
    hideNavigationBar();
    this.getCpActivityInfo();
    this.checkMyIntimacyInfo();
  },
  data() {
    return {
      navigationStyle: NAVIGATION_BAR_STYLE.LIGHT,
      activityId: 0,
      rankRewards: [],
      activitySignupList: [],
      activitySignupTimeRange: null,
      myUserInfo: null,
      hasCp: null,
      signupSuccessList: [],
      signupLimit: 40,
      mySignUpStatus: null,
      signupBtnText: "",
      signupBtnEnable: false,
      showNoCpTipDialog: false,
      showRuleDialog: false,
    };
  },
  computed: {
    topBgImgSrc() {
      let languageCode = this.$i18n.locale;
      if (languageCode === "ar") {
        return require(`./assets/couple_sing_signup_head_ic_ar.png`);
      }
      return require(`./assets/couple_sing_signup_head_ic_en.png`);
    },
  },
  methods: {
    onNavBackClick() {
      closeWindow();
    },
    async getCpActivityInfo() {
      if (_.isNil(self.myUserInfo)) {
        const myUserInfo = await getMyUserInfo();
        if (_.isNil(myUserInfo)) {
          Toast(this.$t("common_failed"));
          return;
        }
        this.myUserInfo = myUserInfo;
      }
      const myUid = this.myUserInfo.uid;
      const { code, data } = await api.getCPCommonActivityInfo({
        activityId: this.activityId,
        querySignupList: true,
      });
      if (code !== RES_COMMON_CODE.SUCCESS) {
        Toast(this.$t("common_failed_with_errorcode", { code }));
        return;
      }
      const {
        activitySignupTimeRange,
        activitySignupList,
        activitySignupLimit,
        rankRewards,
      } = data;
      if (!_.isNil(activitySignupList)) {
        this.activitySignupList = activitySignupList;
      }
      this.signupLimit = activitySignupLimit;
      this.rankRewards = rankRewards;
      this.activitySignupTimeRange = activitySignupTimeRange;
      this.signupSuccessList = _.filter(this.activitySignupList, function(o) {
        return o.signUpStatus === SIGNUP_STATUS.SIGNUP_SUCCESSED;
      });
      let mySignupInfo = _.find(this.activitySignupList, function(o) {
        return o.signupUserInfo.uid === myUid || o.cpPartnerInfo.uid === myUid;
      });
      if (!_.isNil(mySignupInfo)) {
        this.mySignUpStatus = mySignupInfo.signUpStatus;
      }
      this.updateSignStatus();
    },
    async checkMyIntimacyInfo(goSignup) {
      const myUserInfo = await getMyUserInfo();
      if (_.isNil(myUserInfo)) {
        Toast(this.$t("common_failed"));
        return;
      }
      this.myUserInfo = myUserInfo;
      const { data, code } = await api.getIntimacyInfo({
        uid: myUserInfo.uid,
        type: 0,
        status: 1,
      });
      if (code === RES_COMMON_CODE.SUCCESS) {
        this.hasCp = !_.isEmpty(data.couples);
      } else {
        console.log(code);
        Toast(this.$t("common_failed_with_errorcode", { code }));
        return;
      }
      if (goSignup) {
        if (this.hasCp) {
          this.goSignup();
        } else {
          this.showNoCpTipDialog = true;
        }
      }
    },
    updateSignStatus() {
      this.signupBtnEnable = false;
      var timestamp = new Date().getTime();
      if (
        !_.isNil(this.mySignUpStatus) &&
        this.mySignUpStatus === SIGNUP_STATUS.SIGNUP_SUCCESSED
      ) {
        this.signupBtnText = this.$t("couple_signup_success");
      } else if (
        !_.isNil(this.mySignUpStatus) &&
        this.mySignUpStatus === SIGNUP_STATUS.SIGNUP_VERIFYING
      ) {
        this.signupBtnText = this.$t("couple_signup_auditing");
      } else if (
        _.isNil(this.activitySignupTimeRange.fromTime) ||
        _.isNil(this.activitySignupTimeRange.toTime) ||
        timestamp < this.activitySignupTimeRange.fromTime ||
        timestamp > this.activitySignupTimeRange.toTime
      ) {
        this.signupBtnText = this.$t("couple_signup_time_over");
      } else if (this.signupSuccessList.length >= this.signupLimit) {
        this.signupBtnText = this.$t("couple_signup_num_over");
      } else if (this.activitySignupList.length >= this.signupLimit) {
        this.signupBtnText = this.$t("couple_signup_aduit_limit");
      } else {
        this.signupBtnEnable = true;
        this.signupBtnText = this.$t("couple_signup_btn", {
          num: this.activitySignupList.length,
          limit: this.signupLimit,
        });
      }
    },
    async onSignupBtnClick() {
      if (_.isEmpty(this.hasCp)) {
        this.checkMyIntimacyInfo(true);
      } else if (this.hasCp) {
        this.goSignup();
      } else {
        this.showNoCpTipDialog = true;
      }
    },
    goSignup() {
      this.$router.push(
        `/couple_sing_signup/fill_in?activity_id=${this.activityId}`
      );
    },
    onNoCpConfirmDialogClick() {
      this.showNoCpTipDialog = false;
      this.goGroupCp();
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
    onRuleClick() {
      this.showRuleDialog = true;
    },
    onRuleCloseClick() {
      this.showRuleDialog = false;
    },
  },
};
</script>
<style lang="scss" scoped>
@use "@/common/css/compat.scss" as compat;
@use "@/common/css/index.scss" as *;
@import "@/common/css/var.scss";
@include we(page) {
  width: 750px;
  box-sizing: border-box;
  .content-bg {
    width: 100%;
    height: 100%;
    background: linear-gradient(181deg, #c9cfff 0%, #fff5fa 100%);
    position: fixed;
  }
  @include image-view(top-bg, 750px, 1066px) {
    position: absolute;
    top: 0;
    left: 0;
  }
  @include v-layout(content) {
    width: 100%;
    padding-bottom: 200px;
    .navigation-bar {
      position: fixed;
    }
    .section-reward {
      width: 690px;
      margin-top: 793px;
      .rank-reward-layout {
        width: 100%;
        height: 465px;
        position: relative;
        .rank3-reward {
          position: absolute;
          top: 31px;
          left: 36px;
          @include dir {
            right: 36px;
            left: unset;
          }
        }
        .rank1-reward {
          position: absolute;
          top: 77px;
          left: 245px;
          @include dir {
            right: 245px;
            left: unset;
          }
        }
        .rank2-reward {
          position: absolute;
          top: 31px;
          right: 36px;
          @include dir {
            left: 36px;
            right: unset;
          }
        }
      }
    }
    .section-signup-list {
      width: 690px;
      margin-top: 35px;
      @include v-layout(sign-up-list) {
        padding-bottom: 40px;
        .signup-item {
          margin-top: 40px;
        }
      }
    }
  }
  .rules-button {
    width: 81px;
    height: 48px;
    background: linear-gradient(139deg, #fec6f4 0%, #5467eb 100%);
    border-radius: 28px 0px 0px 28px;
    border: 2px solid #ffffff;
    position: absolute;
    right: 0;
    top: 288px;
    font-size: 20px;
    font-family: PingFangSC-Medium, PingFang SC;
    font-weight: 500;
    color: #ffffff;
    line-height: 48px;
    @include dir {
      left: 0;
      right: unset;
      border-radius: 0px 28px 28px 0px;
    }
  }
  @include button(
    signup,
    $width: 750px,
    $height: 100px,
    $text-size: 32px,
    $text-color: $--color-white,
    $border-radius: 0px,
    $background: linear-gradient(139deg, #dfa8ff 0%, #5c53eb 100%)
  ) {
    bottom: 0px;
    position: fixed;
    left: 0px;
  }
  .signup-disable-button {
    @extend .signup-button;
    color: rgba(255, 255, 255, 0.5);
    pointer-events: none;
  }
}
</style>
