<template>
  <div class="get-award-dialog-v-layout">
    <div class="title-v-layout">
      <span class="title-text-view">{{ $t("eid_al_adha_activiy_award") }}</span>
    </div>
    <span class="desc-text-view">{{ $t("eid_al_adha_activiy_end_tip") }}</span>
    <div class="award-grid-layout">
      <get-award-item-view
        v-for="(award, index) in awardInfos"
        :key="index"
        :awardInfo="award"
      />
    </div>
    <span class="ok-button" @click="$emit('on-ok-click')">
      {{ $t("common_ok") }}
    </span>
  </div>
</template>
<script>
import Vue from "vue";
import { Image as VanImage } from "vant";
import GetAwardItemView from "@/eid-al-adha/components/GetAwardItemView";

Vue.use(VanImage);

export default {
  name: "GetAwardDialog",
  components: {
    "get-award-item-view": GetAwardItemView,
  },
  props: {
    awardInfos: [],
  },
};
</script>
<style lang="scss" scoped>
@use "@/common/css/compat.scss" as compat;
@use "@/common/css/index.scss" as *;
@import "@/common/css/var.scss";

@include v-layout(get-award-dialog) {
  width: 606px;
  background: #ffffff;
  border-radius: 32px;
  border: 2px solid;
  padding: 0px 40px 48px 40px;

  @include v-layout(title) {
    width: 460px;
    height: 120px;
    background-image: url("../assets/eid_al_adha_get_award_title_bg.png");
    background-repeat: no-repeat;
    background-size: cover;

    @include text-view(
      title,
      $font-size: 40px,
      $line-height: 56px,
      $font-color: #ee5e09,
      $font-weight: $--font-weight-bold
    ) {
      margin-top: 48px;
    }
  }

  @include text-view(
    desc,
    $font-size: 32px,
    $line-height: 38px,
    $font-color: #724637,
    $font-weight: $--font-weight-regular
  ) {
    margin-top: 13px;
  }

  @include grid-layout(award, 160px 160px 160px, 222px, 16px, 16px) {
    margin-top: 27px;
  }

  @include button(
    ok,
    $width: 400px,
    $height: 88px,
    $text-size: 32px,
    $text-color: $--color-white,
    $border-radius: 0px,
    $background: $--color-transparent
  ) {
    margin-top: 40px;
    background-image: url("../assets/eid_al_adha_get_award_button_bg.png");
    background-repeat: no-repeat;
    background-size: cover;
  }
}
</style>