<template>
  <div class="more-op-v-layout">
    <span
      class="op-text-view"
      v-for="(item, index) in operates"
      :key="index"
      @click="$emit('on-item-click', item.action)"
    >
      {{ item.text }}
    </span>
    <div class="op-divide-line" />
    <span class="cancel-text-view" @click="$emit('on-cancel-click')">
      {{ $t("common_cancel") }}
    </span>
  </div>
</template>
<script>
export default {
  props: {
    operates: [],
  },
};
</script>
<style lang="scss" scoped>
@use "@/common/css/compat.scss" as compat;
@use "@/common/css/index.scss" as *;
@import "@/common/css/var.scss";

@include v-layout(more-op) {
  background: $--color-white;
  @include text-view(
    op,
    $font-size: 32px,
    $line-height: 152px,
    $font-color: #fe6063,
    $font-weight: $--font-weight-regular,
    $text-align: center,
    $line-number: 1
  ) {
    width: 100%;
  }
  @include divide-line(op, 686px, 1px, #c4c7cc);
  @include text-view(
    cancel,
    $font-size: 32px,
    $line-height: 120px,
    $font-color: #8a8f99,
    $font-weight: $--font-weight-regular,
    $text-align: center,
    $line-number: 1
  ) {
    width: 100%;
  }
}
</style>
