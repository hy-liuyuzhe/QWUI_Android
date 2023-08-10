<template>
  <div class="fill-section-content-v-layout">
    <span class="section-title-text-view">{{ title }}</span>
    <slot />
  </div>
</template>
<script>
export default {
  name: "FillSection",
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
  padding: 0px 32px 32px;
  background-color: white;
  @include text-view(
    section-title,
    $font-size: 28px,
    $line-height: 34px,
    $font-color: #25252f,
    $font-weight: $--font-weight-medium
  ) {
    width: 100%;
    padding: 26px 0;
  }
}
</style>
