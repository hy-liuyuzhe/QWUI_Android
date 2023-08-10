<template>
  <div class="gift-wish-level-award-root-v-layout">
    <span class="title-text-view">{{
      $t("gift_wishes_gift_package", { level: level })
    }}</span>
    <div class="gift-wish-level-award-h-layout">
      <div
        class="gift-wish-award-item-v-layout"
        v-for="(item, index) in rewards"
        :key="index"
      >
        <div class="gift-wish-award-item-top">
          <van-image
            class="gift-wish-award-image-view"
            :src="item.img"
          ></van-image>
        </div>
        <span class="gift-wish-award-value-text-view">{{
          rewardValue(item)
        }}</span>
      </div>
    </div>
  </div>
</template>
<script>
import Vue from "vue";
import { Image as VanImage } from "vant";
import { REWARD_TYPE } from "../../invite/constant/constant";
Vue.use(VanImage);
export default {
  name: "GiftWishLevelAward",
  props: {
    rewards: [],
    level: Number,
  },
  methods: {
    rewardValue(rewardItem) {
      var rewardNum = "";
      if (rewardItem.rewardNum > 1) {
        rewardNum = ` x${rewardItem.rewardNum}`;
      }
      if (rewardItem.rewardType == REWARD_TYPE.EXP) {
        return rewardItem.rewardValue + rewardNum;
      }
      let validDays = this.$t("common_days", {
        days: Math.ceil(rewardItem.rewardValue / (24 * 60 * 60)),
      });
      return validDays + rewardNum;
    },
  },
};
</script>
<style lang="scss" scoped>
@use "@/common/css/compat.scss" as compat;
@use "@/common/css/index.scss" as *;
@import "@/common/css/var.scss";
@include v-layout(gift-wish-level-award-root) {
  width: 680px;
  position: relative;
  @include text-view(
    title,
    $font-size: 32px,
    $line-height: 38px,
    $font-color: #fff8e0,
    $font-weight: $--font-weight-bold,
    $text-align: center
  ) {
    position: absolute;
    top: 16px;
    left: 48px;
  }
  @include h-layout(gift-wish-level-award) {
    width: 680px;
    border-left: 0px;
    border-top: 90px solid transparent;
    border-bottom: 48px solid transparent;
    border-right: 0px;
    border-image: url("../assets/gift_wishes_award_card_bg.png") 90 0 48 0 fill;
    @include compat.padding-start(43px);
    @include compat.padding-end(43px);
    flex-wrap: wrap;
    @include v-layout(gift-wish-award-item) {
      width: 182px;
      @include compat.margin-start(8px);
      @include compat.margin-end(8px);
    }
    .gift-wish-award-item-top {
      width: 182px;
      height: 196px;
      background-image: url("../assets/gift_wishes_gift_bg.png");
      background-size: 100% 100%;
      position: relative;
      .gift-wish-award-image-view {
        width: 154px;
        height: 154px;
        position: absolute;
        top: 20px;
        left: 14px;
      }
    }
    @include text-view(
      gift-wish-award-value,
      $font-size: 28px,
      $line-height: 34px,
      $font-color: #884b26,
      $font-weight: $--font-weight-normal,
      $text-align: center
    ) {
      width: 182px;
    }
  }
}
</style>
