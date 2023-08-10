<template>
  <div class="root">
    <span class="congratulations-text">
      {{ $t("common_congratulations") }}
    </span>
    <span class="light-times-text">
      {{ $t("light_gift_light_times", { num: result.times }) }}
    </span>

    <van-image
      v-if="result.vip4"
      class="reward-vip4-image-view"
      :src="result.vip4"
    />
    <reward-content
      v-else
      class="reward-content-layout"
      :goodsDay="result.goodsDay"
      :exp="result.diamond"
    />

    <div class="button-layout" @click="onOkClick">
      <span class="text">{{ $t("common_ok") }}</span>
    </div>
  </div>
</template>

<script>
import Vue from "vue";
import { Image as VanImage } from "vant";
import RewardContent from "../components/RewardContent.vue";

Vue.use(VanImage);

export default {
  name: "RewardResult",
  components: {
    "reward-content": RewardContent,
  },
  props: {
    result: Object,
  },
  methods: {
    onOkClick() {
      this.$emit("ok-btn-click");
    },
  },
};
</script>

<style lang="scss" scoped>
@import "../../common/css/index.scss";

.root {
  width: 606px;
  background: linear-gradient(180deg, #fff9e6 0%, #fec788 100%);
  border-radius: 32px;
  display: flex;
  flex-direction: column;
  align-items: center;
  .congratulations-text {
    @extend .single-line-text;
    height: 56px;
    font-size: 40px;
    margin-top: 48px;
    font-family: SFUIText-Bold, SFUIText;
    font-weight: bold;
    color: #8b3512;
    line-height: 56px;
  }
  .light-times-text {
    @extend .single-line-text;
    height: 38px;
    font-size: 32px;
    margin-top: 13px;
    font-family: SFUIText-Regular, SFUIText;
    font-weight: 400;
    color: #8b3512;
    line-height: 38px;
  }
  .reward-content-layout {
    margin-top: 48px;
  }
  .reward-vip4-image-view {
    margin-top: 48px;
    width: 266px;
    height: 220px;
  }
  .button-layout {
    position: relative;
    width: 400px;
    height: 88px;
    margin-top: 40px;
    background-image: url(('../assets/button_ic.png'));
    background-size: 100%;
    background-repeat: no-repeat;
    margin-bottom: 48px;
    .text {
      position: absolute;
      height: 32px;
      font-size: 32px;
      font-family: SFUIText-Semibold, SFUIText;
      font-weight: 600;
      color: #fecb90;
      line-height: 32px;
      top: 50%;
      left: 50%;
      display: inline;
      transform: translateX(-50%) translateY(-50%);
    }
  }
}
</style>