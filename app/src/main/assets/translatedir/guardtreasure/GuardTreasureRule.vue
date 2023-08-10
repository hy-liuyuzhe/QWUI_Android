<template>
  <div class="content-v-layout">
    <span class="rule-text-view"> {{ $t("rule_1") }} </span>
    <span class="rule-text-view"> {{ $t("rule_2") }} </span>
    <span class="rule-text-view"> {{ $t("rule_3") }} </span>
    <span class="rule-text-view"> {{ $t("rule_4") }} </span>
    <span class="rule-text-view"> {{ $t("rule_5") }} </span>
    <span class="rule-text-view"> {{ $t("rule_6") }} </span>
  </div>
</template>

<script>
import _ from "lodash";
import Vue from "vue";

export default {
  name: "GuardTreasureRule",
}
</script>
<style lang="scss" scoped>
@use "@/common/css/compat.scss" as compat;
@use "@/common/css/index.scss" as *;
@import "@/common/css/var.scss";


@include v-layout(content) {
  widows: 100%;
  @include compat.padding-start(12px);
  @include compat.padding-end(12px);

  @include text-view(rule,
    $font-size: 28px,
    $line-height: 42px,
    $font-color: #FFEEAB,
    $font-weight: $--font-weight-regular ) {
    width: 100%;
    margin-top: 16px;
  }
}
</style>