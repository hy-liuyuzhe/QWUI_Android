<template>
  <div class="sing-rules-v-layout">
    <div class="sing-rules-content-v-layout">
      <span class="rule-text-view">{{ $t("couple_sing_rules_1") }}</span>
      <span class="rule-text-view">{{ $t("couple_sing_rules_2") }}</span>
      <span class="rule-text-view">{{ $t("couple_sing_rules_3") }}</span>
      <span class="rule-text-view">{{ $t("couple_sing_rules_4") }}</span>
      <span class="rule-text-view">{{ $t("couple_sing_rules_5") }}</span>
      <span class="rule-text-view">{{ $t("couple_sing_rules_6") }}</span>
      <span class="rule-text-view">{{ $t("couple_sing_rules_7") }}</span>
    </div>
    <span class="activity-rule-text-view">{{
      $t("couple_activity_rule")
    }}</span>
    <van-image
      class="colse-image-view"
      :src="
        require('../../../common/assets/common_dialog_bottom_white_close_ic.png')
      "
      @click="onCloseClick"
    ></van-image>
  </div>
</template>
<script>
import Vue from "vue";
import { Image as VanImage } from "vant";

Vue.use(VanImage);
export default Vue.extend({
  name: "SingActivityRule",
  methods: {
    onCloseClick() {
      this.$emit("close-btn-click");
    },
  },
});
</script>
<style lang="scss" scoped>
@use "@/common/css/compat.scss" as compat;
@use "@/common/css/index.scss" as *;
@import "@/common/css/var.scss";
@include v-layout(sing-rules) {
  width: 628px;
  position: relative;
  @include text-view(
    activity-rule,
    $font-size: 32px,
    $line-height: 60px,
    $font-color: $--color-white,
    $font-weight: $--font-weight-bold
  ) {
    width: 327px;
    height: 60px;
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    margin: auto;
    text-align: center;
    background-image: url("../assets/couple_sing_section_header_small_ic.png");
    background-size: cover;
  }
  @include v-layout(sing-rules-content) {
    width: 100%;
    max-height: 820px;
    overflow: scroll;
    margin-top: 21px;
    border-left: 0px;
    border-top: 100px solid transparent;
    border-bottom: 70px solid transparent;
    border-right: 0px;
    border-image: url("../assets/couple_sing_rule_bg.png") 100 0 70 0 fill;
    @include text-view(
      rule,
      $font-size: 28px,
      $line-height: 52px,
      $font-color: #897bf5,
      $font-weight: $--font-weight-regular
    ) {
      width: 513px;
    }
  }
  @include image-view(colse, 60px, 60px) {
    margin-top: 62px;
  }
}
</style>
