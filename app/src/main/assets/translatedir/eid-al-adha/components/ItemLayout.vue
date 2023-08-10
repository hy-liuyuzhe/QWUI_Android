<template>
  <div class="item-z-layout">
    <div class="wrapper-v-layout">
      <slot />
    </div>
    <span class="title-text-view">{{ title }}</span>
  </div>
</template>
<script>
export default {
  name: "ItemLayout",
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

@include z-layout(item) {
  width: 686px;

  @include v-layout(wrapper) {
    width: 100%;
    top: 16px;
    background: #ffffff;
    border-radius: 24px;
    border: 2px solid;
    padding: 88px 0px 40px 0px;
    margin-top: 16px;
    border-color: #ffa172;
  }

  @include text-view(
    title,
    $font-size: 32px,
    $line-height: 80px,
    $font-color: $--color-white,
    $font-weight: $--font-weight-medium,
    $text-align: center,
    $line-number: 1
  ) {
    position: absolute;
    width: 564px;
    height: 80px;
    background-image: url("../assets/eid_al_adha_title_bg.png");
    background-repeat: no-repeat;
    background-size: cover;
    top: 0;
    left: 50%;
    transform: translateX(-50%);
  }
}
</style>