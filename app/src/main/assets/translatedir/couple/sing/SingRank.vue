<template>
  <div class="we-page">
    <div class="content-bg"></div>
    <van-image class="top-bg-image-view" :src="topBgImgSrc" />
    <div class="content-v-layout">
      <navigation-bar
        class="navigation-bar"
        :type="navigationStyle"
        :title="$t('couple_sing_rank')"
        @on-back-click="onNavBackClick($event)"
      />
      <span class="play-list-button" @click="onPlayListClick">{{
        $t("couple_play_list")
      }}</span>
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
          <div class="total-rank-v-layout">
            <div class="reward-v-layout">
              <span class="reward-title-text-view">{{
                $t("couple_activity_reward_cp")
              }}</span>
              <span class="reward-value-text-view">{{
                $t("couple_sing_reward_1")
              }}</span>
              <span class="reward-value-text-view">{{
                $t("couple_sing_reward_2")
              }}</span>
              <span class="reward-value-text-view">{{
                $t("couple_sing_reward_3")
              }}</span>
            </div>
            <total-rank-list
              class="total-rank-list"
              :rankingBoard="rankingBoard"
            ></total-rank-list>
          </div>
        </template>
        <template v-else>
          <div class="pk-rank-v-layout">
            <pk-rank-list
              class="pk-rank-list"
              :pkRounds="pkRounds"
            ></pk-rank-list>
          </div>
        </template>
      </div>
    </div>
  </div>
</template>
<script>
import _ from "lodash";
import Vue from "vue";
import { Image as VanImage, Toast, Popup } from "vant";
import { hideNavigationBar, closeWindow } from "../../../common/bridge";
import { NAVIGATION_BAR_STYLE } from "@/common/constant/index";
import NavigationBar from "@/common/components/NavigationBar";
import api from "../api/index";
import { RES_COMMON_CODE } from "../../../common/network/constant";
import TotalRankList from "./components/TotalRankList.vue";
import PKRankList from "./components/PKRankList.vue";

Vue.use(VanImage);
Vue.use(Toast);
Vue.use(Popup);
export default {
  name: "CoupleSingRank",
  components: {
    "navigation-bar": NavigationBar,
    "total-rank-list": TotalRankList,
    "pk-rank-list": PKRankList,
  },
  created() {
    hideNavigationBar();
    this.activityId = this.$route.query.activity_id;
    this.getActivityRankboard();
  },
  data() {
    return {
      navigationStyle: NAVIGATION_BAR_STYLE.LIGHT,
      activityId: null,
      selectedTab: 1,
      tabNum: 2,
      rankingBoard: [],
      pkRounds: [],
    };
  },
  computed: {
    topBgImgSrc() {
      let languageCode = this.$i18n.locale;
      if (languageCode === "ar") {
        return require(`./assets/couple_sing_rank_header_ic_ar.png`);
      }
      return require(`./assets/couple_sing_rank_header_ic_en.png`);
    },
  },
  methods: {
    onNavBackClick() {
      closeWindow();
    },
    onPlayListClick() {
      this.$router.push(
        `/couple_sing_rank/play_list?activity_id=${this.activityId}`
      );
    },
    getTabImg(index) {
      return index == this.selectedTab
        ? require(`./assets/couple_rank_selected.png`)
        : require(`./assets/couple_rank_unselected.png`);
    },
    getTabName(index) {
      return index == 1
        ? this.$t("couple_sing_total_rank_list")
        : this.$t("couple_sing_round_pk_list");
    },
    onTabClick(index) {
      this.selectedTab = index;
    },
    async getActivityRankboard() {
      const { code, data } = await api.getCPActivityBoard({
        activityId: this.activityId,
      });
      if (code == RES_COMMON_CODE.SUCCESS) {
        const { pkRounds, rankingBoard } = data;
        this.pkRounds = pkRounds;
        this.rankingBoard = rankingBoard;
      }
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
  position: relative;
  .content-bg {
    width: 100%;
    height: 100%;
    position: fixed;
  }
  @include image-view(top-bg, 750px, 564px) {
    position: absolute;
    top: 0;
    left: 0;
  }
  @include v-layout(content) {
    width: 100%;
    height: 100%;
    overflow: scroll;
    padding-bottom: 200px;
    .navigation-bar {
      position: fixed;
    }
    .play-list-button {
      width: 109px;
      height: 48px;
      background: linear-gradient(139deg, #fec6f4 0%, #5467eb 100%);
      border-radius: 28px 0px 0px 28px;
      border: 2px solid #ffffff;
      position: absolute;
      right: 0;
      top: 246px;
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
    @include h-layout(tab) {
      width: 100%;
      height: 129px;
      justify-content: center;
      margin-top: 436px;
      .tab {
        width: 345px;
        height: 100%;
        position: relative;
        background-position: bottom;
        background-size: auto;
        background-repeat: no-repeat;
      }
      .tab-unselected {
        @extend .tab;
        background-image: url("./assets/couple_rank_unselected.png");
        background-size: 345px 107px;
      }
      .tab-selected {
        @extend .tab;
        background-image: url("./assets/couple_rank_selected.png");
        background-size: 345px 100%;
      }
      @include text-view(
        tab,
        $font-size: 36px,
        $line-height: 50px,
        $font-color: #ffffff,
        $font-weight: $--font-weight-medium
      );
      @include text-view(
        tab-unselected,
        $font-size: 28px,
        $line-height: 34px,
        $font-color: #ffffff,
        $font-weight: $--font-weight-medium
      );
      .tab-selected-text-view {
        @extend .tab-text-view;
        position: absolute;
        left: 0;
        top: 18px;
        right: 0;
        text-align: center;
      }
      .tab-unselected-text-view {
        @extend .tab-text-view;
        position: absolute;
        left: 0;
        top: 41px;
        right: 0;
        text-align: center;
      }
    }
    @include v-layout(tab-content) {
      margin-top: -40px;
      width: 100%;
      background: linear-gradient(360deg, #ffffff 0%, #fef0ff 100%);
      border-radius: 40px 40px 0px 0px;
      z-index: 1;
      @include v-layout(total-rank) {
        width: 100%;
        @include v-layout(reward) {
          margin-top: 40px;
          width: 690px;
          background: linear-gradient(136deg, #fff5fa 0%, #eed8ff 100%);
          border-radius: 16px;
          padding-bottom: 32px;
          @include text-view(
            reward-title,
            $font-size: 28px,
            $line-height: 34px,
            $font-color: #333333,
            $font-weight: $--font-weight-medium
          ) {
            width: 100%;
            text-align: start;
            margin-top: 32px;
            @include compat.padding-start(30px);
          }
          @include text-view(
            reward-value,
            $font-size: 32px,
            $line-height: 40px,
            $font-color: #333333,
            $font-weight: $--font-weight-regular
          ) {
            width: 100%;
            text-align: start;
            margin-top: 32px;
            @include compat.padding-start(30px);
          }
        }
        .total-rank-list {
          margin-top: 32px;
        }
      }
      @include v-layout(pk-rank) {
        .pk-rank-list {
          margin-top: 23px;
          min-height: 100px;
        }
      }
    }
  }
}
</style>
