<template>
  <div class="level-award-item-v-layout">
    <div class="title-h-layout">
      <div class="title-start-h-layout">
        <span class="level-text-view">{{ levelText }}</span>
        <span class="amount-text-view">{{ amountText }}</span>
      </div>
      <div class="title-end-h-layout">
        <span class="coin-price-title-text-view">
          {{ $t("trade_recharge_coin_price") }}
        </span>
        <span class="coin-price-amount-text-view">{{ coinPriceText }}</span>
      </div>
    </div>

    <div class="awards-v-layout">
      <span v-if="showOverAwardTip" class="over-award-tip-text-view">
        {{ $t("trade_recharge_over_award_tip") }}
      </span>
      <div class="awards-z-layout">
        <div class="awards-grid-layout">
          <award-item-view
            v-for="(award, index) in levelInfo.awards"
            :key="index"
            :award="award"
          />
        </div>
        <van-image
          v-if="isComplete"
          class="complete-image-view"
          :src="require('../assets/trade_recharge_complete_ic.png')"
        />
      </div>
    </div>
  </div>
</template>
<script>
import Vue from "vue";
import { Image as VanImage } from "vant";
import AwardItemView from "@/trade/recharge/components/AwardItemView";

Vue.use(VanImage);

export default {
  name: "LevelAwardItemView",
  components: {
    "award-item-view": AwardItemView,
  },
  props: {
    currentLevel: {
      type: Number,
      default: 0,
    },
    levelInfo: {
      type: Object,
      default() {
        return {
          level: 0,
          minAmount: 0,
          maxAmount: 0,
          coinsPrice: 0,
          awards: [],
        };
      },
    },
    showOverAwardTip: {
      type: Boolean,
      default: false,
    },
  },
  computed: {
    levelText() {
      return `Lv.${this.levelInfo.level}`;
    },
    amountText() {
      const { minAmount, maxAmount } = this.levelInfo;
      if (minAmount >= 20000) {
        return `>=20000$`;
      }

      return `${minAmount}-${maxAmount}$`;
    },
    coinPriceText() {
      return `${this.levelInfo.coinsPrice}/1$`;
    },
    isComplete() {
      return this.currentLevel >= this.levelInfo.level;
    },
  },
};
</script>
<style lang="scss" scoped>
@use "@/common/css/compat.scss" as compat;
@use "@/common/css/index.scss" as *;
@import "@/common/css/var.scss";

@include v-layout(level-award-item) {
  width: 674px;

  @include h-layout(title) {
    width: 674px;
    height: 78px;
    background-image: url("../assets/trade_recharge_award_title_bg.png");
    background-size: cover;
    background-repeat: no-repeat;
    justify-content: space-between;
    padding: 0px 25px;

    @include h-layout(title-start) {
      @include text-view(
        level,
        $font-size: 40px,
        $line-height: 40px,
        $font-color: $--color-white,
        $font-weight: $--font-weight-bold
      );

      @include text-view(
        amount,
        $font-size: 26px,
        $line-height: 40px,
        $font-color: $--color-white,
        $font-weight: $--font-weight-regular
      ) {
        @include compat.margin-start(28px);
      }
    }

    @include h-layout(title-end) {
      margin-top: 20px;
      @include text-view(
        coin-price-title,
        $font-size: 26px,
        $line-height: 31px,
        $font-color: $--color-white,
        $font-weight: $--font-weight-regular
      );
      @include text-view(
        coin-price-amount,
        $font-size: 26px,
        $line-height: 31px,
        $font-color: #f7ff11,
        $font-weight: $--font-weight-regular
      );
    }
  }

  @include v-layout(awards) {
    width: 670px;
    background: #fff5e0;
    border-radius: 0px 0px 16px 16px;
    border: 2px solid #efe7d2;

    @include text-view(
      over-award-tip,
      $font-size: 24px,
      $line-height: 33px,
      $font-color: #795228,
      $font-weight: $--font-weight-regular
    ) {
      width: 622px;
      margin-top: 24px;
    }

    @include z-layout(awards) {
      width: 670px;

      @include grid-layout(awards, 144px 144px 144px 144px, 202px, 16px, 0px) {
        position: relative;
        top: 24px;
        left: 23px;
        @include dir {
          left: unset;
          right: 23px;
        }
        margin-bottom: 30px;
      }

      @include image-view(complete, 160px, 120px) {
        position: absolute;
        bottom: 0;
        right: 0;
        @include dir {
          left: 0;
          right: unset;
        }
      }
    }
  }
}
</style>