<template>
  <div class="fill-section-content-v-layout">
    <span class="section-title-text-view">{{ title }}</span>
    <slot />
    <div class="bottom-line"></div>
  </div>
</template>
<script>
export default {
  name: "SignupFillSection",
  props: {
    title: String,
  },
};
</script>
<style lang="scss" scoped>
@use "@/common/css/compat.scss" as compat;
@use "@/common/css/text.scss" as text;
@use "@/common/css/index.scss" as *;
@import "@/common/css/var.scss";
@include v-layout(fill-section-content) {
  width: 100%;
  padding: 0px 60px;
  @include text-view(
    section-title,
    $font-size: 32px,
    $line-height: 38px,
    $font-color: $--color-white,
    $font-weight: $--font-weight-regular
  ) {
    width: 100%;
  }
  .bottom-line {
    height: 2px;
    width: 100%;
    height: 2px;
    background: #ffffff;
    opacity: 0.2;
  }
}
</style>
