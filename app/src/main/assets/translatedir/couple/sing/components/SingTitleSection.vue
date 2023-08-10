<template>
  <div class="sing-title-section-v-layout">
    <div class="sing-section-content-v-layout">
      <slot />
    </div>
    <span class="section-title-text-view">{{ title }}</span>
  </div>
</template>
<script>
export default {
  name: "SingTitleSection",
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
@include v-layout(sing-title-section) {
  width: 100%;
  position: relative;
  @include text-view(
    section-title,
    $font-size: 42px,
    $line-height: 78px,
    $font-color: $--color-white,
    $font-weight: $--font-weight-bold
  ) {
    width: 426px;
    height: 78px;
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    margin: auto;
    text-align: center;
    background-image: url("../assets/couple_sing_section_header_ic.png");
    background-size: cover;
  }
  @include v-layout(sing-section-content) {
    width: 100%;
    margin-top: 36px;
    padding-top: 36px;
    background: linear-gradient(136deg, #fff5fa 0%, #eed8ff 100%);
    border-radius: 16px;
    backdrop-filter: blur(22px);
    border: 4px solid #ffe6b3;
  }
}
</style>
