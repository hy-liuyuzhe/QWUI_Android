<template>
  <div class="month-recharge-v-layout">
    <span class="month-text-view">{{ rechargeMonthText }}</span>
    <span class="amount-text-view">{{ `${amount}$` }}</span>
  </div>
</template>
<script>
export default {
  name: "MonthRechargeView",
  props: {
    rechargeMonthText: {
      type: String,
      default: "",
    },
    amount: {
      type: Number,
      default: 0,
    },
  },
};
</script>
<style lang="scss" scoped>
@use "@/common/css/compat.scss" as compat;
@use "@/common/css/index.scss" as *;
@import "@/common/css/var.scss";

@include v-layout(month-recharge) {
  @include text-view(
    month,
    $font-size: 26px,
    $line-height: 40px,
    $font-color: #a88c6b,
    $font-weight: $--font-weight-regular
  );
  @include text-view(
    amount,
    $font-size: 34px,
    $line-height: 40px,
    $font-color: #795228,
    $font-weight: $--font-weight-bold
  ) {
    margin-top: 8px;
  }
}
</style>