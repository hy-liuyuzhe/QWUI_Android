<template>
  <div class="achieve-level-v-layout">
    <span class="title-text-view">
      {{ $t("trade_recharge_reach_level", { level: currentLevel }) }}
    </span>
    <span class="coin-price-text-view">
      {{
        $t("trade_recharge_reach_level_coin_price", {
          amount: levelInfo.coinsPrice,
        })
      }}
    </span>
    <span class="issue-tip-text-view">
      {{ $t("trade_recharge_reach_level_issue_awards_tip") }}
    </span>
    <div class="awards-grid-layout">
      <award-item-view
        v-for="(award, index) in levelInfo.awards"
        :key="index"
        :award="award"
      />
    </div>
    <span class="ok-button" @click="$emit('on-ok-click', currentLevel)">
      {{ $t("common_ok") }}
    </span>
  </div>
</template>
<script>
import _ from "lodash";
import AwardItemView from "@/trade/recharge/components/AwardItemView";

export default {
  name: "AchieveLevelDialog",
  components: {
    "award-item-view": AwardItemView,
  },
  props: {
    currentLevel: {
      type: Number,
      default: 0,
    },
  },
  computed: {
    levelInfo() {
      const levelInfos = this.$store.state.trade.rechargeLevelInfos;
      return _.find(levelInfos, (level) => level.level === this.currentLevel);
    },
  },
};
</script>
<style lang="scss" scoped>
@use "@/common/css/compat.scss" as compat;
@use "@/common/css/index.scss" as *;
@import "@/common/css/var.scss";

@include v-layout(achieve-level) {
  width: 606px;
  background: linear-gradient(180deg, #fff5e0 0%, #ffe3b9 100%);
  border-radius: 32px;
  padding: 48px;

  @include text-view(
    title,
    $font-size: 40px,
    $line-height: 56px,
    $font-color: #795228,
    $font-weight: $--font-weight-bold
  );

  @include text-view(
    coin-price,
    $font-size: 32px,
    $line-height: 38px,
    $font-color: #ffa12b,
    $font-weight: $--font-weight-regular
  ) {
    margin-top: 15px;
  }

  @include text-view(
    issue-tip,
    $font-size: 32px,
    $line-height: 38px,
    $font-color: #795228,
    $font-weight: $--font-weight-regular
  ) {
    margin-top: 8px;
  }

  @include grid-layout(awards, 144px 144px 144px, 202px, 16px, 8px) {
    margin-top: 32px;
  }

  .margin-start-16px {
    @include compat.margin-start(16px);
  }

  @include button(
    ok,
    $width: 400px,
    $height: 88px,
    $text-size: 32px,
    $text-color: $--color-white,
    $border-radius: 48px,
    $background: #f89a50
  ) {
    margin-top: 32px;
  }
}
</style>