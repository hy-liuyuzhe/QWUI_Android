<template>
  <div class="title-wapper-v-layout">
    <span class="title-text-view">{{ title }}<span class="note-text-view">&nbsp;{{note}}</span></span>
    <slot />
  </div>
</template>
<script>
export default {
  name: "TitleLayout",
  props: {
    title: {
      type: String,
      default: "",
    },
    note: {
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

@include v-layout(title-wapper, flex-start) {
  width: 100%;
  padding: 10px 32px 32px 32px;
  background-color: $--color-white;

  @include text-view(
    title,
    $font-size: 28px,
    $line-height: 34px,
    $font-color: $--color-222222,
    $font-weight: $--font-weight-medium
  );

  @include text-view(
      note,
  $font-size: 24px,
  $line-height: 34px,
  $font-color: #B7B7B7,
  $font-weight: $--font-weight-regular
  );
}
</style>