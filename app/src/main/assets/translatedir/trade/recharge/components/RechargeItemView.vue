<template>
  <div class="recharge-item-h-layout">
    <span class="recharge-way-text-view">{{ rechargeWayText }}</span>
    <span class="recharge-button" @click="$emit('on-recharge-click')">
      {{ $t("trade_recharge") }}
    </span>
  </div>
</template>
<script>
export default {
  name: "RechargeItemView",
  props: {
    rechargeWayText: {
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

@include h-layout(recharge-item) {
  width: 670px;
  height: 112px;
  background: #f2f8fb;
  border-radius: 16px;
  justify-content: space-between;
  padding: 0px 32px;

  @include text-view(
    recharge-way,
    $font-size: 28px,
    $line-height: 34px,
    $font-color: $--color-222222,
    $font-weight: $--font-weight-bold
  ) {
    width: 382px;
  }

  @include button(recharge, $width: 192px, $height: 56px, $text-size: 28px);
}
</style>