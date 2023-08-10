<template>
  <div class="award-title-v-layout">
    <div class="award-title-h-layout">
      <van-image
        class="icon-image-view"
        :src="require('../assets/trade_recharge_adward_ic.png')"
      />
      <span class="title-text-view">{{ title }}</span>
    </div>
    <slot />
  </div>
</template>
<script>
export default {
  name: "AwardTitleView",
  props: {
    title: {
      type: String,
      default: "",
    },
  },
};
</script>
<style lang="scss" scoped>
@use "@/common/css/compat.scss" as compat;
@use "@/common/css/index.scss" as *;
@import "@/common/css/var.scss";

@include v-layout(award-title) {
  width: 100%;
  padding: 0px 0px 32px 0px;

  @include h-layout(award-title) {
    width: 100%;
    height: 104px;
    padding: 0px 40px;

    @include image-view(icon, 38px, 56px);

    @include text-view(
      title,
      $font-size: 32px,
      $line-height: 38px,
      $font-color: $--color-222222,
      $font-weight: $--font-weight-bold,
      $line-number: 1
    ) {
      @include compat.margin-start(16px);
    }
  }
}
</style>
