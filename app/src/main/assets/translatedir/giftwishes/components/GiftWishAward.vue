<template>
  <div class="gift-wish-award-root-v-layout">
    <div class="gift-wish-award-v-layout">
      <div class="gift-wish-award-progress-v-layout">
        <span class="gift-wish-help-others-text-view">{{
          $t("gift_wishes_help_others_desc")
        }}</span>
        <span class="gift-wish-help-others-detail-text-view">{{
          $t("gift_wishes_help_others_detail")
        }}</span>
        <reward-progress-bar
          class="reward-progress-bar"
          progressTrackColor="#f4e3a7"
          progressColor="#ff76aa"
          :rewardItemWidth="80"
          :width="600"
          :progressMarginTop="55"
          :progressBarMarginEnd="0"
          :rewardValues="levelInfos"
          :currentRewardValue="helpNum"
        >
          <template v-slot:reward-items>
            <div
              class="level-container-v-layout"
              v-for="(item, index) in levelInfos"
              :key="index"
            >
              <img
                class="level-image-view"
                :src="
                  helpNum >= item
                    ? require('../assets/gift_wishes_reward_reached.png')
                    : require('../assets/gift_wishes_reward_unreached.png')
                "
              />
              <span
                class="level-text-view"
                :style="
                  helpNum >= item ? { color: '#ff76aa' } : { color: '#884B26' }
                "
                >{{ item }}</span
              >
            </div>
          </template>
        </reward-progress-bar>
        <!-- <div class="start-point"></div> -->
      </div>
      <div class="gift-wish-level-award-v-layout">
        <gift-wish-level-award
          v-for="(item, index) in configInfos"
          :key="index"
          v-bind:rewards="item"
          v-bind:level="index + 1"
        ></gift-wish-level-award>
      </div>
    </div>
    <van-image
      class="left-lantern-image-view"
      :src="require('../assets/gift_wishes_lantern.png')"
    ></van-image>
    <van-image
      class="right-lantern-image-view"
      :src="require('../assets/gift_wishes_lantern.png')"
    ></van-image>
    <span class="award-title-text-view">{{ $t("gift_wishes_award") }}</span>
  </div>
</template>
<script>
import Vue from "vue";
import { Image as VanImage } from "vant";
import RewardProgressBar from "@/common/components/RewardProgressBar";
import GiftWishLevelAward from "./GiftWishLevelAward";
import api from "../api/index";
Vue.use(VanImage);
export default {
  name: "GiftWishAward",
  components: {
    "reward-progress-bar": RewardProgressBar,
    "gift-wish-level-award": GiftWishLevelAward,
  },
  props: {
    levelInfos: Array,
    helpNum: Number,
    configInfos: Array
  },
};
</script>
<style lang="scss" scoped>
@use "@/common/css/compat.scss" as compat;
@use "@/common/css/index.scss" as *;
@import "@/common/css/var.scss";
@include v-layout(gift-wish-award-root) {
  position: relative;
  width: 750px;
  display: flex;
  flex-direction: column;
  align-items: center;
  @include v-layout(gift-wish-award) {
    width: 692px;
    border-left: 0px;
    border-top: 96px solid transparent;
    border-bottom: 40px solid transparent;
    border-right: 0px;
    border-image: url("../assets/gift_wishes_reward_bg.png") 96 0 40 0 fill;
    @include v-layout(gift-wish-award-progress) {
      width: 692px;
      position: relative;
      border-left: 0px;
      border-top: 49px solid transparent;
      border-bottom: 49px solid transparent;
      border-right: 0px;
      padding-bottom: 20px;
      border-image: url("../assets/gift_wishes_reward_top_content_bg.png") 49 0
        49 0 fill;
      @include text-view(
        gift-wish-help-others,
        $font-size: 28px,
        $line-height: 34px,
        $font-color: #82431d,
        $font-weight: $--font-weight-bold,
        $text-align: center
      ) {
        width: 560px;
      }
      @include text-view(
        gift-wish-help-others-detail,
        $font-size: 24px,
        $line-height: 29px,
        $font-color: #82431d80,
        $font-weight: $--font-weight-normal,
        $text-align: center
      ) {
        width: 560px;
        margin-top: 20px;
      }
      .reward-progress-bar {
        width: 600px;
        height: 156px;
        margin-top: 32px;
        @include v-layout(level-container) {
          .level-image-view {
            width: 80px;
            height: 80px;
          }
          @include text-view(
            level,
            $font-size: 22px,
            $line-height: 26px,
            $font-color: #ff76aa,
            $font-weight: $--font-weight-normal,
            $text-align: center
          ) {
            height: 26px;
            margin-top: 50px;
          }
        }
      }
      // .start-point {
      //   width: 26px;
      //   height: 26px;
      //   background-color: #fff8e0;
      //   border-radius: 50%;
      //   border: 8px solid #ff76aa;
      //   position: absolute;
      //   left: 28px;
      //   bottom: 50px;
      //   @include dir {
      //     left: unset;
      //     right: 28px;
      //   }
      // }
    }
    @include v-layout(gift-wish-level-award) {
      width: 680px;
      margin-top: 16px;
    }
  }
  .left-lantern-image-view {
    position: absolute;
    left: 0px;
    top: 36px;
    width: 76px;
    height: 176px;
  }
  .right-lantern-image-view {
    position: absolute;
    right: 0px;
    top: 36px;
    width: 76px;
    height: 176px;
  }
  @include text-view(
    award-title,
    $font-size: 40px,
    $line-height: 48px,
    $font-color: #fff8e0,
    $font-weight: $--font-weight-bold,
    $text-align: center
  ) {
    width: 750px;
    position: absolute;
    left: 0px;
    top: 28px;
  }
}
</style>
