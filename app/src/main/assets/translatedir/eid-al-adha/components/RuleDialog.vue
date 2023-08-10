<template>
  <div class="rule-dialog-v-layout">
    <div class="title-v-layout">
      <span class="title-text-view">{{ $t("common_rule") }}</span>
    </div>
    <div class="share-ratio-v-layout">
      <div class="title-h-layout">
        <span class="ranking-title-text-view">
          {{ $t("eid_al_adha_rule_ranking") }}
        </span>
        <span class="share-ratio-title-text-view">
          {{ $t("eid_al_adha_rule_share_ratio") }}
        </span>
      </div>
      <div
        class="item-h-layout"
        v-for="(rank, index) in excludeLastRankingInfos"
        :key="index"
      >
        <div
          :class="[
            'ranking-h-layout',
            isLastRankingIndex(index) ? 'start-bottom-corner' : '',
          ]"
        >
          <van-image
            v-if="index < 3"
            class="rank-num-image-view"
            :src="rankNumImgSrc(index + 1)"
          />
          <span class="rank-num-text-view">
            {{ $t("eid_al_adha_award_top_rank", { rank: rank.ranking }) }}
          </span>
        </div>
        <span
          :class="[
            'share-ratio-text-view',
            isLastRankingIndex(index) ? 'end-bottom-corner' : '',
          ]"
        >
          {{ `${rank.rate * 100}%` }}
        </span>
      </div>
    </div>
    <span class="divide-equally-tip-text-view">
      {{
        $t("eid_al_adha_rule_divide_equally_tip", {
          rate: `${lastRankingInfo.rate * 100}%`,
        })
      }}
    </span>
    <span class="ok-button" @click="$emit('on-ok-click')">
      {{ $t("common_ok") }}
    </span>
  </div>
</template>
<script>
import _ from "lodash";
import Vue from "vue";
import { Image as VanImage } from "vant";

Vue.use(VanImage);

export default {
  name: "RuleDialog",
  props: {
    rankingCoinsInfo: [],
  },
  methods: {
    rankNumImgSrc(rank) {
      return require(`../assets/eid_al_adha_rule_ranking_${rank}_ic.png`);
    },
    isLastRankingIndex(index) {
      return index == this.excludeLastRankingInfos.length - 1;
    },
  },
  computed: {
    excludeLastRankingInfos() {
      const rankingCoinsInfo = this.rankingCoinsInfo;
      return _.take(rankingCoinsInfo, rankingCoinsInfo.length - 1);
    },
    lastRankingInfo() {
      return _.last(this.rankingCoinsInfo);
    },
  },
};
</script>
<style lang="scss" scoped>
@use "@/common/css/compat.scss" as compat;
@use "@/common/css/index.scss" as *;
@import "@/common/css/var.scss";

@include v-layout(rule-dialog) {
  width: 606px;
  background: #ffffff;
  border-radius: 32px;
  border: 2px solid;
  padding: 0px 40px 48px 40px;

  @include v-layout(title) {
    width: 460px;
    height: 120px;
    background-image: url("../assets/eid_al_adha_get_award_title_bg.png");
    background-repeat: no-repeat;
    background-size: cover;

    @include text-view(
      title,
      $font-size: 40px,
      $line-height: 56px,
      $font-color: #ee5e09,
      $font-weight: $--font-weight-bold
    ) {
      margin-top: 48px;
    }
  }

  @include v-layout(share-ratio) {
    width: 481px;
    border-radius: 23px;
    border: 2px solid #ffe6b5;
    background-color: #e4e4e4;

    @include h-layout(title) {
      width: 100%;
      justify-content: space-between;
      @include text-view(
        ranking-title,
        $font-size: 24px,
        $line-height: 96px,
        $font-color: #724637,
        $font-weight: $--font-weight-medium,
        $text-align: center,
        $line-number: 1
      ) {
        width: 240px;
        background: #ffe6b5;
        border-radius: 23px 0px 0px 0px;
      }
      @include text-view(
        share-ratio-title,
        $font-size: 24px,
        $line-height: 96px,
        $font-color: #724637,
        $font-weight: $--font-weight-medium,
        $text-align: center,
        $line-number: 1
      ) {
        width: 240px;
        background: #ffe6b5;
        border-radius: 0px 23px 0px 0px;
        @include compat.margin-start(1px);
      }
    }
    @include h-layout(item) {
      width: 100%;
      margin-top: 1px;
      @include h-layout(ranking) {
        justify-content: center;
        width: 240px;
        height: 70px;
        background: $--color-white;
        @include image-view(rank-num, 40px, 40px);
        @include text-view(
          rank-num,
          $font-size: 24px,
          $line-height: 33px,
          $font-color: #ee5e09,
          $font-weight: $--font-weight-regular,
          $line-number: 1
        ) {
          @include compat.margin-start(8px);
        }
      }
      @include text-view(
        share-ratio,
        $font-size: 24px,
        $line-height: 70px,
        $font-color: #724637,
        $font-weight: $--font-weight-regular,
        $text-align: center,
        $line-number: 1
      ) {
        width: 240px;
        background: $--color-white;
        @include compat.margin-start(1px);
      }
      .start-bottom-corner {
        border-radius: 0px 0px 0px 23px;
      }
      .end-bottom-corner {
        border-radius: 0px 0px 23px 0px;
      }
    }
  }

  @include text-view(
    divide-equally-tip,
    $font-size: 28px,
    $line-height: 34px,
    $font-color: #724637,
    $font-weight: $--font-weight-regular
  ) {
    margin-top: 24px;
  }

  @include button(
    ok,
    $width: 400px,
    $height: 88px,
    $text-size: 32px,
    $text-color: $--color-white,
    $border-radius: 0px,
    $background: $--color-transparent
  ) {
    margin-top: 40px;
    background-image: url("../assets/eid_al_adha_get_award_button_bg.png");
    background-repeat: no-repeat;
    background-size: cover;
  }
}
</style>