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
      <span class="time-text-view">{{ time }}</span>
      <div class="marquee-container">
        <div
          class="marquee-box-h-layout"
          v-for="(cps, lines) in travelRewardCpsLines"
          :key="lines"
        >
          <div
            :class="[
              lines === 0 ? 'marquee-h-layout' : 'finish-marquee-h-layout',
            ]"
            :style="{
              animationDuration: `${cps.length * 10}s`,
              webkitAnimationDuration: `${cps.length * 10 + 5}s`,
            }"
          >
            <div
              :class="[
                lines === 0
                  ? 'rewarded-cp-item-h-layout'
                  : 'finish-rewarded-cp-item-h-layout',
              ]"
              v-for="(info, index) in cps"
              :key="index"
            >
              <div
                :class="[
                  lines === 0 ? 'avatars-layout' : 'finish-avatars-layout',
                ]"
              >
                <van-image
                  class="left-avatar-image-view"
                  :src="info.avatar"
                  round
                  error-icon="user-circle-o"
                />
                <van-image
                  class="right-avatar-image-view"
                  :src="info.otherAvatar"
                  round
                  error-icon="user-circle-o"
                />
              </div>
              <span
                :class="[
                  lines === 0
                    ? 'reward-info-text-view'
                    : 'finish-reward-info-text-view',
                ]"
              >
                {{
                  lines === 0
                    ? $t("couple_travel_reward_info", {
                        name1: info.name,
                        name2: info.otherName,
                        num: info.num,
                      })
                    : $t("couple_travel_finished_reward_info", {
                        name1: info.name,
                        name2: info.otherName,
                        num: info.num,
                      })
                }}
              </span>
              <img
                class="finished-decor-image-view"
                :src="require('./assets/couple_travel_finished_deco.png')"
                v-if="lines === 1"
              />
            </div>
          </div>
        </div>
      </div>
      <div class="tab-h-layout">
        <div
          :class="[tabIndex == selectedTab ? 'tab-selected' : 'tab-unselected']"
          class="tab"
          v-for="(tabIndex, index) in tabNum"
          :key="index"
          @click="onTabClick(tabIndex)"
        >
          <span
            :class="[
              tabIndex == selectedTab
                ? 'tab-selected-text-view'
                : 'tab-unselected-text-view',
            ]"
            >{{ getTabName(tabIndex) }}</span
          >
        </div>
      </div>
      <div class="tab-content-v-layout">
        <template v-if="selectedTab == 1">
          <div class="lottery-v-layout">
            <travel-lottery
              :travelActivityInfo="travelActivityInfo"
              :myUserInfo="myUserInfo"
              @on-no-cp-click="onNoCpClick"
              @on-refresh-info="getCpTravelActivityInfo"
            ></travel-lottery>
          </div>
        </template>
        <template v-else>
          <div class="map-v-layout">
            <travel-map
              :coupleUserInfo="coupleUserInfo"
              :myUserInfo="myUserInfo"
              :travelActivityInfo="travelActivityInfo"
            ></travel-map>
          </div>
        </template>
      </div>
    </div>
    <van-popup v-model="showNoCpTipDialog" get-container="body" round>
      <travel-dialog
        :message="$t('couple_no_cp')"
        :isEnd="false"
        :mainButtonText="$t('couple_group_cp')"
        @on-close-click="onNoCpTipDialogCloseClick"
        @on-main-button-click="goGroupCp"
      >
      </travel-dialog>
    </van-popup>
    <van-popup v-model="showRuleDialog" get-container="body" round>
      <travel-dialog
        :title="$t('couple_activity_rule')"
        :isEnd="false"
        :message="$t('couple_travel_rule')"
        @on-close-click="onRuleDialogCloseClick"
      >
      </travel-dialog>
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
import moment from "moment";
import TravelLottery from "./components/TravelLottery.vue";
import TravelMap from "./components/TravelMap.vue";
import TravelDialog from "./components/TravelDialog.vue";

Vue.use(VanImage);
Vue.use(Toast);
Vue.use(Popup);
export default {
  name: "CoupleTravel",
  components: {
    "navigation-bar": NavigationBar,
    "travel-lottery": TravelLottery,
    "travel-map": TravelMap,
    "travel-dialog": TravelDialog,
  },
  data() {
    return {
      navigationStyle: NAVIGATION_BAR_STYLE.LIGHT,
      tabNum: 2,
      selectedTab: 1,
      myUserInfo: null,
      coupleUserInfo: {},
      travelActivityInfo: {},
      showNoCpTipDialog: false,
      showRuleDialog: false,
      travelRewardCpsLines: [[], []],
    };
  },
  created() {
    hideNavigationBar();
    this.getMyUserInfo();
    this.getCpTravelActivityInfo();
    this.getCpTravelAllWinInfo();
  },
  computed: {
    topBgImgSrc() {
      let languageCode = this.$i18n.locale;
      if (languageCode === "ar") {
        return require(`./assets/couple_travel_head_ic_ar.png`);
      }
      return require(`./assets/couple_travel_head_ic_en.png`);
    },
    time() {
      if (
        !_.isNil(this.travelActivityInfo) &&
        !_.isNil(this.travelActivityInfo.startTime) &&
        !_.isNil(this.travelActivityInfo.endTime)
      ) {
        const startTime = moment
          .unix(this.travelActivityInfo.startTime / 1000)
          .format("MM.DD");
        const endTime = moment
          .unix(this.travelActivityInfo.endTime / 1000)
          .format("MM.DD");
        return this.$t("couple_activity_time", {
          start: startTime,
          end: endTime,
        });
      } else {
        return "";
      }
    },
  },
  methods: {
    onNavBackClick() {
      closeWindow();
    },
    getTabName(index) {
      return index == 1
        ? this.$t("couple_travel_tab_lottery_pool")
        : this.$t("couple_travel_map");
    },
    onTabClick(index) {
      this.selectedTab = index;
      this.getCpTravelActivityInfo();
    },
    async getMyUserInfo() {
      const myUserInfo = await getMyUserInfo();
      if (_.isNil(myUserInfo)) {
        Toast(this.$t("common_failed"));
        return;
      }
      this.myUserInfo = myUserInfo;
    },
    async getCpTravelActivityInfo() {
      const { data, code } = await api.getCpTravelActivityInfo({
        lang: this.$i18n.locale,
      });
      if (code === RES_COMMON_CODE.SUCCESS) {
        this.travelActivityInfo = data;
        if (!_.isNil(data.intimacyId)) {
          this.coupleUserInfo = {
            avatar: data.otherAvatar,
            name: data.otherName,
            uid: data.otherUid,
          };
        }
      }
    },
    async getCpTravelAllWinInfo() {
      const { data, code } = await api.getCpTravelAllWinInfo();
      if (code === RES_COMMON_CODE.SUCCESS) {
        var noFinishedRewardCps = [];
        var finishedRewardCps = [];
        if (!_.isNil(data)) {
          const map = new Map(Object.entries(data));
          if (!_.isEmpty(map.get("0"))) {
            noFinishedRewardCps = map.get("0");
          }
          if (!_.isEmpty(map.get("1"))) {
            finishedRewardCps = map.get("1");
          }
        }
        this.travelRewardCpsLines = [noFinishedRewardCps, finishedRewardCps];
      }
    },
    onNoCpClick() {
      this.showNoCpTipDialog = true;
    },
    onNoCpTipDialogCloseClick() {
      this.showNoCpTipDialog = false;
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
    onRuleDialogCloseClick() {
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
    position: fixed;
    background-color: #cedafd;
  }
  @include image-view(top-bg, 750px, 1001px) {
    position: absolute;
    top: 0;
    left: 0;
  }
  .marquee-container {
    width: 100%;
    height: 143px;
    position: relative;
    margin-top: 32px;
    overflow: hidden;
    @include h-layout(marquee-box) {
      @include h-layout(marquee) {
        position: relative;
        @include compat.animation-name(marquee);
        @include compat.animation-timing-function(linear);
        @include compat.animation-iteration-count(infinite);
        @include compat.padding-start(37px);
        @include dir {
          @include compat.animation-name(marquee-rlt);
        }
        @include h-layout(rewarded-cp-item) {
          position: relative;
          height: 56px;
          @include compat.margin-end(37px);
        }
        @include h-layout(finish-rewarded-cp-item) {
          @extend .rewarded-cp-item-h-layout;
          height: 76px;
        }
        .avatars-layout {
          width: 100px;
          height: 56px;
          position: absolute;
          left: 0;
          top: 0;
          @include dir {
            right: 0px;
            left: unset;
          }
          @include image-view(left-avatar, 56px, 56px) {
            border: 2px solid #ffffff;
            border-radius: 28px;
            position: absolute;
            left: 0;
            top: 0;
            @include dir {
              right: 0;
              left: unset;
            }
          }
          @include image-view(right-avatar, 56px, 56px) {
            border: 2px solid #ffffff;
            border-radius: 28px;
            position: absolute;
            left: 43px;
            top: 0;
            @include dir {
              right: 43px;
              left: unset;
            }
          }
        }
        .finish-avatars-layout {
          @extend .avatars-layout;
          top: 20px;
        }
        @include text-view(
          reward-info,
          $font-size: 26px,
          $line-height: 54px,
          $font-color: $--color-white,
          $font-weight: $--font-weight-medium,
          $text-align: start,
          $line-number: 1
        ) {
          height: 54px;
          @include compat.padding-start(43px);
          @include compat.padding-end(20px);
          @include compat.margin-start(76px);
          background: #fe5a8880;
          border-radius: 0px 100px 100px 0px;
          @include dir {
            border-radius: 100px 0px 0px 100px;
          }
        }
        .finish-reward-info-text-view {
          @extend .reward-info-text-view;
          margin-top: 20px;
        }
        @include image-view(finished-decor, 76px, 62px) {
          position: absolute;
          left: 0;
          top: 0;
        }
      }
      @include h-layout(finish-marquee) {
        @extend .marquee-h-layout;
        @include compat.padding-start(180px);
      }
    }
  }
  @include v-layout(content) {
    width: 100%;
    padding-bottom: 200px;
    position: absolute;
    top: 0;
    left: 0;
    .navigation-bar {
      position: fixed;
    }
    @include text-view(
      time,
      $font-size: 28px,
      $line-height: 34px,
      $font-color: #bd5a6b,
      $font-weight: $--font-weight-medium,
      $text-align: start,
      $line-number: 1
    ) {
      margin-top: 300px;
    }

    @include h-layout(tab) {
      width: 690px;
      height: 92px;
      margin-top: 418px;
      padding: 6px;
      justify-content: center;
      background: #ffffffcc;
      box-shadow: 0px 0px 2px 0px #ffffff,
        inset 0px 6px 12px 2px rgba(255, 255, 255, 0.5);
      border-radius: 46px;
      border: 1px solid #ffffff;
      .tab-unselected {
        width: 341px;
        height: 80px;
        position: relative;
      }
      .tab-selected {
        @extend .tab-unselected;
        background-image: url("./assets/couple_travel_tab_selected_ic.png");
        background-size: 100% 100%;
      }
      @include text-view(
        tab-unselected,
        $font-size: 36px,
        $line-height: 80px,
        $font-color: #bd5a6b,
        $font-weight: $--font-weight-regular
      );
      .tab-selected-text-view {
        @extend .tab-unselected-text-view;
        color: #ffffff;
      }
    }
    @include v-layout(tab-content) {
      width: 100%;
      margin-top: 30px;
      @include v-layout(lottery) {
        width: 100%;
      }
      @include v-layout(map) {
        width: 100%;
      }
    }
  }
  .rules-button {
    width: 76px;
    height: 50px;
    background: linear-gradient(180deg, #ffbfd6 0%, #ff557d 81%, #f05383 100%);
    border-radius: 8px 0px 0px 8px;
    position: absolute;
    right: 0;
    top: 150px;
    font-size: 26px;
    font-family: PingFangSC-Regular, PingFang SC;
    font-weight: 400;
    color: #ffffff;
    line-height: 50px;
    @include dir {
      left: 0;
      right: unset;
      border-radius: 0px 8px 8px 0px;
    }
  }
}
@keyframes marquee {
  0% {
    transform: translateX(100vw);
  }
  100% {
    transform: translateX(-100%);
  }
}
@keyframes marquee-rlt {
  0% {
    transform: translateX(-100vw);
  }
  100% {
    transform: translateX(100%);
  }
}
</style>
