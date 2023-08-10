<template>
  <div class="item-z-layout">
    <div class="title-v-layout">
      <span class="top-title-text-view">{{ title }}</span>
    </div>
    <div class="wrapper-v-layout">
      <slot />
    </div>
  </div>
</template>
<script>
export default {
  name: "ItemLayout",
  props: {
    title: String,
  }
};
</script>
<style lang="scss" scoped>
@use "@/common/css/compat.scss" as compat;
@use "@/common/css/index.scss" as *;
@import "@/common/css/var.scss";

@include z-layout(item) {
  width: 100%;

  @include v-layout(title) {
    position: relative;
    width: fit-content;
    max-width: 690px;
    height: 58px;
    margin: 0 auto;
    
    padding-left: 30px;
    padding-right: 30px;

    border-width: 1px;
    border-style: solid;
    border-image: url("../assets/medal_rule_title.png") 1 200 1 200 fill / 1px 83px stretch;

    @include text-view(top-title,
      $font-size: 28px,
      $line-height: 30px,
      $font-color: $--color-white,
      $font-weight: --font-weight-normal) {
      width: fit-content;
      text-align: center;
      margin: auto auto;
      padding: 0px 20px 0px 20px;
    }
  }

  @include v-layout(wrapper) {
    width: fit-content;
    margin-top: -29px;
    margin-left: 30px;
    margin-right: 30px;

    padding-left: 24px;
    padding-right: 24px;

    border: 2px solid #FFF8E5;
    background: rgba(24, 15, 8, 0.6);
    border-radius: 16px;

  }
}
</style>